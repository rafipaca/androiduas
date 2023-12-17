/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.rafirs.projectutsppk.service.impl;

import com.rafirs.projectutsppk.dto.TPQDto;
import com.rafirs.projectutsppk.entity.TPQ;
import com.rafirs.projectutsppk.mapper.TPQMapper;
import com.rafirs.projectutsppk.repository.TPQRepository;
import com.rafirs.projectutsppk.service.TPQService;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author RafiRS
 */

@Service
public class TPQServiceImpl implements TPQService{
    @Autowired
    private TPQRepository tpqRepository;

    @Override
    public List<TPQDto> getTPQs() {
        List<TPQ> tpqs = tpqRepository.findAll();
        List<TPQDto> tpqDtos = tpqs.stream()
                .map((tpq) -> (TPQMapper.mapToDto(tpq)))
                .collect(Collectors.toList());
        return tpqDtos;
    }


    @Override
    public List<TPQDto> searchTPQs(String searchTerm) {
        List<TPQ> tpqs = tpqRepository.findByNameContaining(searchTerm);
        return TPQMapper.mapToTPQDtoList(tpqs);
    }

    @Override
    public int countTPQs() {
        return getTPQs().size();
    }

    @Override
    public TPQDto getTPQ(Long tpqId) {
        Optional<TPQ> tpq = tpqRepository.findById(tpqId);
        if (tpq.isPresent()) {
            return TPQMapper.mapToDto(tpq.get());
        } else {
            return null;
        }
    }
    
    @Override
    public TPQDto updateTPQ(TPQDto tpq) {
        TPQ result = tpqRepository.save(TPQMapper.mapToEntity(tpq));
        return TPQMapper.mapToDto(result);
    }

    @Override
    public TPQDto createTPQ(TPQDto tpq) {
        TPQ result = tpqRepository.save(TPQMapper.mapToEntity(tpq));
        return TPQMapper.mapToDto(result);
    }
    
    @Override
    public boolean deleteTPQ(Long tpqId) {
        tpqRepository.deleteById(tpqId);  
        return true;
    }

}
