/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.rafirs.projectutsppk.mapper;

import com.rafirs.projectutsppk.dto.RoleDto;
import com.rafirs.projectutsppk.entity.Role;

/**
 *
 * @author RafiRS
 */

public class RoleMapper {
    public static Role mapToEntity(RoleDto roleDto) {
        return Role.builder()
            .id(roleDto.getId())
            .name(roleDto.getName())
            .build();
    }

    public static RoleDto mapToDto(Role role) {
        return RoleDto.builder()
            .id(role.getId())
            .name(role.getName())
            .build();
    }
}
