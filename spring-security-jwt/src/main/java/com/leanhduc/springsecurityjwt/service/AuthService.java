package com.leanhduc.springsecurityjwt.service;

import com.leanhduc.springsecurityjwt.config.UserDetailsService;
import com.leanhduc.springsecurityjwt.entity.RoleEntity;
import com.leanhduc.springsecurityjwt.entity.UserEntity;
import com.leanhduc.springsecurityjwt.repository.UserRepository;
import com.leanhduc.springsecurityjwt.util.JwtUtil;
import com.leanhduc.springsecurityjwt.util.UserToken;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
@Data
public class AuthService {
    private final UserRepository userRepository;
    @Qualifier("accessTokenUtil")
    private final JwtUtil accessTokenJwtUtil;
    @Qualifier("refreshTokenUtil")
    private final JwtUtil refreshTokenJwtUtil;
    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;
    private final RoleService roleService;

    public void register(String email, String password) throws Exception {
        if (this.userRepository.existsByEmail(email)) {
            throw new Exception("Email has exits");
        }
        String hashingPassword = passwordEncoder.encode(password);
        RoleEntity role = roleService.getRoleByName("USER");
        userRepository.save(UserEntity.builder()
                .email(email)
                .password(hashingPassword)
                .role(role)
                .build());
    }

    public UserToken login(String email, String password) throws Exception {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        if (userDetails == null) {
            throw new UsernameNotFoundException(email);
        }

        String accessToken = accessTokenJwtUtil.generateToken(email);
        String refreshToken = refreshTokenJwtUtil.generateToken(email);
        return new UserToken(accessToken, refreshToken);
    }
}