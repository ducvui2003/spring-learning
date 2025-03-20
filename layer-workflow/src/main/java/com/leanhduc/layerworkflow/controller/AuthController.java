package com.leanhduc.layerworkflow.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class AuthController {
    Logger logger = LoggerFactory.getLogger(AuthController.class);

    @GetMapping()
    public String index() {
        logger.info("Controller: running");
        return "index";
    }

    @GetMapping("/error")
    public String error() {
        throw new RuntimeException("error");
    }
}
