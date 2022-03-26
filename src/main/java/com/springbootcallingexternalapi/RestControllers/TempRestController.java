package com.springbootcallingexternalapi.RestControllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TempRestController {
    @RequestMapping("/hello")
    public String hello() {
        return "Hello World";
    }
}
