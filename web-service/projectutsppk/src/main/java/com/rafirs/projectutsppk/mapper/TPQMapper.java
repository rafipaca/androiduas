/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.rafirs.projectutsppk.mapper;

import com.rafirs.projectutsppk.dto.TPQDto;
import com.rafirs.projectutsppk.entity.TPQ;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author RafiRS
 */

public class TPQMapper {

        public static TPQDto mapToDto(TPQ tpq) {
        TPQDto tpqDto = TPQDto.builder()
                .id(tpq.getId())
                .name(tpq.getName())
                .alamat(tpq.getAlamat())
                .nokontak(tpq.getNokontak())
                .createdAt(tpq.getCreatedAt())
                .updatedAt(tpq.getUpdatedAt())
                .build();
        return tpqDto;
    }

    public static List<TPQDto> mapToTPQDtoList(List<TPQ> tpqs) {
        return tpqs.stream()
                .map(TPQMapper::mapToDto)
                .collect(Collectors.toList());
    }

    public static TPQ mapToEntity(TPQDto tpqDto) {
        TPQ tpq = TPQ.builder()
                .id(tpqDto.getId())
                .name(tpqDto.getName())
                .alamat(tpqDto.getAlamat())
                .nokontak(tpqDto.getNokontak())
                .createdAt(tpqDto.getCreatedAt())
                .updatedAt(tpqDto.getUpdatedAt())
                .build();
        return tpq;
    }
}
