package com.springbootcallingexternalapi.LeagueOfLegends.Security.RestControllers;



import com.springbootcallingexternalapi.LeagueOfLegends.Security.Enums.RoleName;
import com.springbootcallingexternalapi.LeagueOfLegends.Security.JWT.JwtProvider;
import com.springbootcallingexternalapi.LeagueOfLegends.Security.Models.Role;
import com.springbootcallingexternalapi.LeagueOfLegends.Security.Models.User;
import com.springbootcallingexternalapi.LeagueOfLegends.Security.Services.RoleService;
import com.springbootcallingexternalapi.LeagueOfLegends.Security.Services.UserService;
import com.springbootcallingexternalapi.LeagueOfLegends.Security.dto.JwtDto;
import com.springbootcallingexternalapi.LeagueOfLegends.Security.dto.LoginUser;
import com.springbootcallingexternalapi.LeagueOfLegends.Security.dto.Message;
import com.springbootcallingexternalapi.LeagueOfLegends.Security.dto.NewUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping("/auth")
@CrossOrigin
public class UserController {

    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    UserService userService;
    @Autowired
    RoleService roleService;
    @Autowired
    JwtProvider jwtProvider;

    @PostMapping("/newUser")
    public ResponseEntity<?> newUser(@Valid @RequestBody NewUser newUser, BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            return new ResponseEntity(new Message("incomplete information, it is needed in this order: name, userName, email, password"), HttpStatus.BAD_REQUEST);
        }
        if (userService.existsByUserName(newUser.getUserName())) {
            return new ResponseEntity(new Message("Username already registered"),HttpStatus.BAD_REQUEST);
        }
        if (userService.existsByEmail(newUser.getEmail())) {
            return new ResponseEntity(new Message("Email already registered"),HttpStatus.BAD_REQUEST);
        }

        User user =
                new User(newUser.getName(),
                        newUser.getUserName(),
                        newUser.getEmail(),
                        passwordEncoder.encode(newUser.getPassword()));
        Set<Role> roles = new HashSet<>();
        roles.add(roleService.getByRoleName(RoleName.ROLE_USER).get());
        if (newUser.getRoles().contains("admin")) {
            roles.add(roleService.getByRoleName(RoleName.ROLE_ADMIN).get());
        }
        user.setRoles(roles);
        userService.save(user);
        return new ResponseEntity(new Message("New registered user"),HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<JwtDto> login(@Valid @RequestBody LoginUser loginUser, BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            return new ResponseEntity(new Message("incomplete information, it is needed in this order: userName, password"), HttpStatus.BAD_REQUEST);
        }
        Authentication authentication =
                authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginUser.getUserName(),loginUser.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtProvider.generateToken(authentication);
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        JwtDto jwtDto = new JwtDto(jwt,userDetails.getUsername(),userDetails.getAuthorities());

        return new ResponseEntity<>(jwtDto,HttpStatus.OK);

    }
}
