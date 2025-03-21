package com.leanhduc.springsecurityjwt.service;

import com.leanhduc.springsecurityjwt.entity.RoleEntity;
import com.leanhduc.springsecurityjwt.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Service
@RequiredArgsConstructor
public class RoleService {
    private final RoleRepository roleRepository;

    public RoleEntity getRoleByName(String name) {
        Optional<RoleEntity> roleEntityOptional = roleRepository.findByName(name);
        return roleEntityOptional.orElse(null);
    }
}
