package com.rafirs.projectutsppk.payload;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

/**
 *
 * @author RafiRS
 */

@Data
public class LoginReq {
    @NotEmpty
    @Email
    private String email;

    @NotEmpty
    private String password;
}
