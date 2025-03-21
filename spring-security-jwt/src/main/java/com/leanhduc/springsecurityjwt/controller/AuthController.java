package com.leanhduc.springsecurityjwt.controller;

import com.leanhduc.springsecurityjwt.dto.AuthResDto;
import com.leanhduc.springsecurityjwt.dto.RegisterReqDto;
import com.leanhduc.springsecurityjwt.service.AuthService;
import com.leanhduc.springsecurityjwt.util.UserToken;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @GetMapping("/login")
    @ResponseBody
    public AuthResDto login(@RequestBody RegisterReqDto body) throws Exception {
        UserToken userToken = authService.login(body.email(), body.password());
        return new AuthResDto(userToken.getAccessToken(), userToken.getRefreshToken());
    }

    @GetMapping("/register")
    @ResponseBody
    public void register(@RequestBody RegisterReqDto body) throws Exception {
        authService.register(body.email(), body.password());
    }

}
