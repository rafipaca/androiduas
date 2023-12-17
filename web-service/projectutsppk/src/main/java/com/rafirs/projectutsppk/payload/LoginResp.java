package com.rafirs.projectutsppk.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author RafiRS
 */

@Getter
@Setter
@AllArgsConstructor
public class LoginResp {
    private String email;
    private String accessToken;
}
