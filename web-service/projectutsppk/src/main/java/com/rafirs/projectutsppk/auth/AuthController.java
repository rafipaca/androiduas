package com.rafirs.projectutsppk.auth;



import com.rafirs.projectutsppk.dto.UserDto;
import com.rafirs.projectutsppk.payload.ErrorResponseAPI;
import com.rafirs.projectutsppk.payload.LoginReq;
import com.rafirs.projectutsppk.payload.LoginResp;
import com.rafirs.projectutsppk.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;



@RestController
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager authManager;

    @Autowired
    private JwtUtils jwtUtils;

    @Operation(summary = "Otentikasi user untuk mendapatkan token jwt.")
    @ApiResponses(
        value = {
            @ApiResponse(responseCode = "200", description = "otentikasi berhasil", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = LoginResp.class))
            }),
            @ApiResponse(responseCode = "401", description = "email atau password salah", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseAPI.class))
            })
        }
    )
    @PostMapping("/login")
    private ResponseEntity<?> login(@RequestBody @Valid LoginReq request) {
        Authentication authentication = authManager.authenticate(
            new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

        String accessToken = jwtUtils.generateJwtToken(authentication);
        LoginResp response = new LoginResp(request.getEmail(), accessToken);

        return ResponseEntity.ok().body(response);
    }

    @Operation(summary = "Membuat akun user baru.")
    @ApiResponses(
        value = {
            @ApiResponse(responseCode = "201", description = "berhasil membuat akun baru", content = @Content)
        }
    )
    @PostMapping("/register")
    private ResponseEntity<?> register(@RequestBody @Valid UserDto request) {
        userService.createUser(request);
        return ResponseEntity.status(201).build();
    }
}
