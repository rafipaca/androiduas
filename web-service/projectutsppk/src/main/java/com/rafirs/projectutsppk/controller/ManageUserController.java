package com.rafirs.projectutsppk.controller;

import com.rafirs.projectutsppk.dto.RoleDto;
import com.rafirs.projectutsppk.dto.UserDto;
import com.rafirs.projectutsppk.service.RoleService;
import com.rafirs.projectutsppk.service.UserService;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

/**
 *
 * @author RafiRS
 */

@RestController
public class ManageUserController {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Operation(summary = "Menampilkan seluruh user yang terdaftar dalam sistem.")
    @ApiResponses(
        value = {
            @ApiResponse(responseCode = "200", description = "seluruh data user", content = {
                @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = UserDto.class)))
            })
        }
    )
    @GetMapping("/user")
    @Secured("ROLE_ADMIN")
    public ResponseEntity<?> getAllUsers() {
        List<UserDto> users = userService.getAllUsers();
        return ResponseEntity.ok().body(users);
    }
    
    @Operation(summary = "Menetapkan role ke user tertentu.")
    @ApiResponses(
        value = {
            @ApiResponse(responseCode = "200", description = "data user yang diperbarui", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = UserDto.class))
            })
        }
    )
    @PostMapping("/user/{userId}/role/{role}")
    @Secured("ROLE_ADMIN")
    public ResponseEntity<?> assignRole(
        @Parameter(description = "user yang akan menerima role baru") @PathVariable("userId") Long id,
        @Parameter(description = "role yang ingin ditambahkan ke user") @PathVariable("role") String role
    ) {
        UserDto user = userService.getUser(id);
        RoleDto userRole = roleService.getRoleByName(role);

        user.getRoles().add(userRole);
        userService.updateUser(user);

        return ResponseEntity.ok().body(user);
    }

    @Operation(summary = "Mencabut role dari user tertentu.")
    @ApiResponses(
        value = {
            @ApiResponse(responseCode = "200", description = "data user yang diperbarui", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = UserDto.class))
            })
        }
    )
    @DeleteMapping("/user/{userId}/role/{role}")
    @Secured("ROLE_ADMIN")
    public ResponseEntity<?> dismissRole(
        @Parameter(description = "user yang ingin dicabut role-nya") @PathVariable("userId") Long id,
        @Parameter(description = "role yang akan dicabut") @PathVariable("role") String role
    ) {
        UserDto user = userService.getUser(id);
        RoleDto userRole = roleService.getRoleByName(role);

        user.getRoles().remove(userRole);
        userService.updateUser(user);

        return ResponseEntity.ok().body(user);
    }


    @Operation(summary = "Menghapus user tertentu.")
    @ApiResponses(
        value = {
            @ApiResponse(responseCode = "200", description = "berhasil menghapus user", content = @Content)
        }
    )
    @DeleteMapping("/user/{userId}")
    @Secured("ROLE_ADMIN")
    public ResponseEntity<?> deleteAccount(@PathVariable("userId") Long userId) {
        userService.deleteUser(userId);
        return ResponseEntity.ok().build();
    }
}
