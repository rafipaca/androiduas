package com.rafirs.projectutsppk.service.impl;

import com.rafirs.projectutsppk.dto.RoleDto;
import com.rafirs.projectutsppk.dto.UserDto;
import com.rafirs.projectutsppk.entity.ERole;
import com.rafirs.projectutsppk.entity.Role;
import com.rafirs.projectutsppk.entity.User;
import com.rafirs.projectutsppk.mapper.RoleMapper;
import com.rafirs.projectutsppk.mapper.UserMapper;
import com.rafirs.projectutsppk.repository.RoleRepository;
import com.rafirs.projectutsppk.repository.UserRepository;
import com.rafirs.projectutsppk.service.UserService;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 *
 * @author RafiRS
 */

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder encoder;

    @Override
    public UserDto createUser(UserDto userDto) {
        Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                .orElseThrow(() -> new RuntimeException("Default role not found")); 
        RoleDto userRoleDto = RoleMapper.mapToDto(userRole);
        Set<RoleDto> roles = Collections.singleton(userRoleDto);

        userDto.setRoles(roles);
        userDto.setPassword(encoder.encode(userDto.getPassword()));
        userRepository.save(UserMapper.mapToEntity(userDto));
        return userDto;
    }

    @Override
    public UserDto updateUser(UserDto userDto) {
        userRepository.save(UserMapper.mapToEntity(userDto));
        return userDto;
    }

    @Override
    public boolean deleteUser(Long id) {
        userRepository.deleteById(id);
        return true;
    }

    @Override
    public UserDto getUser(Long id) {
        Optional<User> user = userRepository.findById(id);

        return user.isPresent() ? UserMapper.mapToDto(user.get()) : null;
    }

    @Override
    public UserDto getUserByEmail(String email) {
        Optional<User> user = userRepository.findByEmail(email);

        return user.isPresent() ? UserMapper.mapToDto(user.get()) : null;
    }

    @Override
    public List<UserDto> getAllUsers() {
        List<User> users = userRepository.findAll();

        return users.stream()
            .map(user -> UserMapper.mapToDto(user))
            .collect(Collectors.toList());
    }

    @Override
    public boolean hasRole(UserDto user, ERole role) {
        boolean found = false;

        for (RoleDto roleDto : user.getRoles()) {
            if (roleDto.getName() == role) {
                found = true;
            }
        }

        return found;
    }
}

