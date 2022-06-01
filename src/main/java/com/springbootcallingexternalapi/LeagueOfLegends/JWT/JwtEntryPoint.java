package com.springbootcallingexternalapi.LeagueOfLegends.JWT;

import static javax.servlet.http.HttpServletResponse.SC_UNAUTHORIZED;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.Collections;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

@Component
public class JwtEntryPoint implements AuthenticationEntryPoint {

  private final static Logger logger = LoggerFactory.getLogger(JwtEntryPoint.class);

  @Override
  public void commence(HttpServletRequest request, HttpServletResponse response,
      AuthenticationException e) throws IOException {
    logger.error("fail method commence");
    response.setStatus(SC_UNAUTHORIZED);
    response.setContentType(MediaType.APPLICATION_JSON_VALUE);
    byte[] body = new ObjectMapper().writeValueAsBytes(
        Collections.singletonMap("message", "Unauthorized"));
    response.getOutputStream().write(body);
  }
}
