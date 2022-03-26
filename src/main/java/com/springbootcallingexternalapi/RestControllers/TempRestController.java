package com.springbootcallingexternalapi.RestControllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
public class TempRestController {

    Logger logger = LoggerFactory.getLogger(TempRestController.class);

    @RequestMapping("/hello")
    public String hello() {
        return "Hello World";
    }

    @RequestMapping(value =  "/call-riot/{account}",
                    method = RequestMethod.GET,
                    produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> callRiot(@PathVariable String account) {
        try {
            String uri = "https://la1.api.riotgames.com/lol/summoner/v4/summoners/by-name/" + account;

            logger.info(uri);
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.add("X-Riot-Token", "");

            HttpEntity<String> entity = new HttpEntity<>("body", headers);
            ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.GET, entity, String.class);
            logger.info("RESULT", response.getBody());
            return response;
        } catch (Exception e) {
            logger.info(e.getMessage());
            return new ResponseEntity<String>("Nombre de usuario incorrecto, revisar", HttpStatus.BAD_REQUEST);
        }
    }
}
