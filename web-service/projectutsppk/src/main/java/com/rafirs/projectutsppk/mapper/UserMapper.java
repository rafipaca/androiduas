/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.rafirs.projectutsppk.mapper;

import com.rafirs.projectutsppk.dto.RoleDto;
import com.rafirs.projectutsppk.dto.UserDto;
import com.rafirs.projectutsppk.entity.Role;
import com.rafirs.projectutsppk.entity.User;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 *
 * @author RafiRS
 */

public class UserMapper {
    public static User mapToEntity(UserDto userDto) {
        Set<Role> roles = new HashSet<>();
        if (userDto.getRoles() != null) {
            roles = userDto.getRoles().stream()
                .map(role -> RoleMapper.mapToEntity(role))
                .collect(Collectors.toSet());
        }

        return User.builder()
            .id(userDto.getId())
            .name(userDto.getName())
            .nim(userDto.getNim())
            .email(userDto.getEmail())
            .password(userDto.getPassword())
            .roles(roles)
            .build();
    }

    public static UserDto mapToDto(User user) {
        Set<RoleDto> roleDtos = new HashSet<>();
        if (user.getRoles() != null) {
            roleDtos = user.getRoles().stream()
                .map(role -> RoleMapper.mapToDto(role))
                .collect(Collectors.toSet());
        }

        return UserDto.builder()
            .id(user.getId())
            .name(user.getName())
            .nim(user.getNim())
            .email(user.getEmail())
            .password(user.getPassword())
            .roles(roleDtos)
            .build();
    }
}

