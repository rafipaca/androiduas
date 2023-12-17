package com.rafirs.projectutsppk.controller;

import com.rafirs.projectutsppk.dto.UserDto;
import com.rafirs.projectutsppk.exception.WrongPasswordException;
import com.rafirs.projectutsppk.payload.ChangePasswordRequest;
import com.rafirs.projectutsppk.service.UserService;
import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;

/**
 *
 * @author RafiRS
 */

@RestController
public class ProfileController {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder encoder;
    
    @Operation(summary = "Melihat data user yang terotentikasi.")
    @ApiResponses(
        value = {
            @ApiResponse(responseCode = "200", description = "data user", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = UserDto.class))
            })
        }
    )
    @GetMapping("/profile")
    public ResponseEntity<?> getProfile(Principal principal) {
        UserDto user = userService.getUserByEmail(principal.getName());
        return ResponseEntity.ok().body(user);
    }

    @Operation(summary = "Mengubah data user yang terotentikasi. Hanya dapat mengubah nama dan NIM.")
    @ApiResponses(
        value = {
            @ApiResponse(responseCode = "200", description = "data user yang telah diperbarui", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = UserDto.class))
            })
        }
    )
    @PutMapping("/profile")
    public ResponseEntity<?> editProfile(Principal principal, @RequestBody @Valid UserDto request) {
        UserDto user = userService.getUserByEmail(principal.getName());
        user.setName(request.getName());
        user.setNim(request.getNim());

        userService.updateUser(user);
        return ResponseEntity.ok().body(user);
    }

    @Operation(summary = "Mengubah password dari user yang terotentikasi.")
    @ApiResponses(
        value = {
            @ApiResponse(responseCode = "200", description = "data user yang telah diperbarui", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = UserDto.class))
            })
        }
    )
    @PutMapping("/profile/password")
    public ResponseEntity<?> changePassword(Principal principal, @RequestBody @Valid ChangePasswordRequest request) {
        UserDto user = userService.getUserByEmail(principal.getName());
        if (!encoder.matches(request.getOldPassword(), user.getPassword())) {
            throw new WrongPasswordException();
        }

        user.setPassword(encoder.encode(request.getNewPassword()));
        userService.updateUser(user);
        return ResponseEntity.ok().body(user);
    }

    @Operation(summary = "Menghapus akun user yang terotentikasi.")
    @ApiResponses(
        value = {
            @ApiResponse(responseCode = "200", description = "berhasil menghapus akun", content = @Content)
        }
    )
    @DeleteMapping("/profile")
    public ResponseEntity<?> deleteProfile(Principal principal) {
        UserDto user = userService.getUserByEmail(principal.getName());
        userService.deleteUser(user.getId());

        return ResponseEntity.ok().build();
    }

}
