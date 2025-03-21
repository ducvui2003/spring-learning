package com.leanhduc.springsecurityjwt.config;

import com.leanhduc.springsecurityjwt.entity.UserEntity;
import com.leanhduc.springsecurityjwt.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {
    private final UserRepository userRepository;
    private final Logger logger = Logger.getLogger(UserDetailsService.class.getName());

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserEntity> userEntityOptional = userRepository.findByEmail(username);
        logger.info("loadUserByUsername: " + userEntityOptional.isPresent());
        if (userEntityOptional.isPresent()) {
            UserEntity userEntity = userEntityOptional.get();
            Set<SimpleGrantedAuthority> roles = Set.of(new SimpleGrantedAuthority("ROLE_" + userEntity.getRole()));
            return new User(userEntity.getEmail(), userEntity.getPassword(), roles);
        }
        return null;
    }
}
