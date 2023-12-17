/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.rafirs.projectutsppk.dto;

import jakarta.validation.constraints.NotEmpty;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

/**
 *
 * @author RafiRS
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TPQDto {
    private Long id;
    @NotEmpty(message = "Name diperlukan.")
    private String name;
    @NotEmpty(message = "alamat diperlukan.")
    private String alamat;
    @NotEmpty(message = "no handphone / kontak diperlukan.")
    private String nokontak;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private Date createdAt;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private Date updatedAt;
}
