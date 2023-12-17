package com.rafirs.projectutsppk.dto;

import java.util.Set;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

/**
 *
 * @author RafiRS
 */

@Data
@Builder
public class UserDto {
    private Long id;

    @NotEmpty
    private String name;
    
    @Size(min = 9, max = 9, message = "NIM must be exactly 9 characters long")
    @Pattern(regexp = "\\d{9}", message = "NIM must consist of 9 digits")
    @NotEmpty
    private String nim;

    @Email
    @NotEmpty
    private String email;

    @NotEmpty
    private String password;

    private Set<RoleDto> roles;
}
