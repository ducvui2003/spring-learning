package com.leanhduc.websocket;

import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController()
@RequestMapping("/token")
public class Controller {

    @GetMapping()
    public ResponseEntity<String> generate() {
        var token = UUID.randomUUID().toString();
        return ResponseEntity.ok(token);
    }
}
