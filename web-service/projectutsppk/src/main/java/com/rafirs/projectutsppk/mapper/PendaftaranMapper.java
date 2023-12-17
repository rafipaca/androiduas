/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.rafirs.projectutsppk.mapper;


import com.rafirs.projectutsppk.dto.PendaftaranDto;
import com.rafirs.projectutsppk.entity.Pendaftaran;
import java.util.Date;

/**
 *
 * @author RafiRS
 */

public class PendaftaranMapper {
    public static PendaftaranDto mapToDto(Pendaftaran pendaftaran) {
        PendaftaranDto pendaftaranDto = PendaftaranDto.builder()
                .id(pendaftaran.getId())
                .user(UserMapper.mapToDto(pendaftaran.getUser()))
                .tpq(TPQMapper.mapToDto(pendaftaran.getTpq()))
                .statusPendaftaran(pendaftaran.isStatusPendaftaran())
                .createdAt(pendaftaran.getCreatedAt())
                .updatedAt(pendaftaran.getUpdatedAt())
                .build();
        return pendaftaranDto;
    }

    public static Pendaftaran mapToEntity(PendaftaranDto pendaftaranDto) {
        Pendaftaran pendaftaran = Pendaftaran.builder()
                .id(pendaftaranDto.getId())
                .user(UserMapper.mapToEntity(pendaftaranDto.getUser()))
                .tpq(TPQMapper.mapToEntity(pendaftaranDto.getTpq()))
                .statusPendaftaran(pendaftaranDto.isStatusPendaftaran())
                .createdAt((Date) pendaftaranDto.getCreatedAt())
                .updatedAt((Date) pendaftaranDto.getUpdatedAt())
                .build();
        return pendaftaran;
    }
}
