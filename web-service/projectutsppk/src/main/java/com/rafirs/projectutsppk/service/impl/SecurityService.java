package com.rafirs.projectutsppk.service.impl;

import com.rafirs.projectutsppk.dto.UserDto;
import com.rafirs.projectutsppk.entity.ERole;
import com.rafirs.projectutsppk.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author RafiRS
 */

@Service("securityService")
public class SecurityService {


    @Autowired
    private UserService userService;
    

    public boolean hasRole(Long userId, String role) {
        UserDto user = userService.getUser(userId);
        return userService.hasRole(user, ERole.valueOf(role));
    }

}
