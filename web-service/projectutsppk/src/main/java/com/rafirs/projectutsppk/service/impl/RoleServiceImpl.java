package com.rafirs.projectutsppk.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rafirs.projectutsppk.dto.RoleDto;
import com.rafirs.projectutsppk.entity.ERole;
import com.rafirs.projectutsppk.entity.Role;
import com.rafirs.projectutsppk.mapper.RoleMapper;
import com.rafirs.projectutsppk.repository.RoleRepository;
import com.rafirs.projectutsppk.service.RoleService;

/**
 *
 * @author RafiRS
 */

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public RoleDto getRoleByName(String roleName) {
        Optional<Role> role = roleRepository.findByName(ERole.valueOf(roleName));
        
        return role.isPresent() ? RoleMapper.mapToDto(role.get()) : null;
    }
    
}
