package com.rafirs.projectutsppk.service;

import java.util.List;

import com.rafirs.projectutsppk.dto.UserDto;
import com.rafirs.projectutsppk.entity.ERole;

public interface UserService {
    public UserDto createUser(UserDto userDto);
    public UserDto updateUser(UserDto userDto);
    public boolean deleteUser(Long id);
    public List<UserDto> getAllUsers();
    public UserDto getUser(Long id);
    public UserDto getUserByEmail(String email);
    public boolean hasRole(UserDto user, ERole role);
}
