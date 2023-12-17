
package com.rafirs.projectutsppk.service.impl;

import com.rafirs.projectutsppk.dto.PendaftaranDto;
import com.rafirs.projectutsppk.entity.Pendaftaran;
import com.rafirs.projectutsppk.mapper.PendaftaranMapper;
import java.util.List;
import java.util.stream.Collectors;
import com.rafirs.projectutsppk.repository.PendaftaranRepository;
import com.rafirs.projectutsppk.service.PendaftaranService;
import com.rafirs.projectutsppk.service.TPQService;
import java.util.ArrayList;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author RafiRS
 */

@Service
public class PendaftaranServiceImpl implements PendaftaranService{
    @Autowired
    private PendaftaranRepository pendaftaranRepository;
    @Autowired
    private TPQService tpqService;

    @Override
    public List<PendaftaranDto> getPendaftarans() {
        List<Pendaftaran> pendaftarans = pendaftaranRepository.findAll();
        List<PendaftaranDto> pendaftaranDtos = pendaftarans.stream()
                .map((pendaftaran) -> (PendaftaranMapper.mapToDto(pendaftaran)))
                .collect(Collectors.toList());
        return pendaftaranDtos;
    }
    
    @Override
    public List<PendaftaranDto> getPendaftarans(long id) {
        List<Pendaftaran> pendaftarans = pendaftaranRepository.findAll();
        List<PendaftaranDto> pendaftaranDtos = new ArrayList<>();
        for (Pendaftaran pendaftaran : pendaftarans) {
            if (pendaftaran.getUser().getId() == id) {
                PendaftaranDto pendaftaranDto = PendaftaranMapper.mapToDto(pendaftaran);
                pendaftaranDtos.add(pendaftaranDto);
            }
        }
        return pendaftaranDtos;
    }
    
    @Override
    public int getJmlPendaftaran(){
        int count = 0;
        List<PendaftaranDto> pendaftaranDtos = getPendaftarans();
        for (PendaftaranDto pendaftaranDto : pendaftaranDtos){
            if (pendaftaranDto.isStatusPendaftaran())
                count++;
        }
        return count;
    }

    @Override
    public void approvePendaftaran(PendaftaranDto pendaftaranDto){
        pendaftaranDto.setStatusPendaftaran(true);
        updatePendaftaran(pendaftaranDto);
    }

    @Override
    public PendaftaranDto getPendaftaran(Long pendaftaranId) {
        Optional<Pendaftaran> pendaftaran = pendaftaranRepository.findById(pendaftaranId);
        if (pendaftaran.isPresent()) {
            return PendaftaranMapper.mapToDto(pendaftaran.get());
        } else {
            return null;
        }
    }

    @Override
    public PendaftaranDto updatePendaftaran(PendaftaranDto pendaftaran) {
        Pendaftaran result = pendaftaranRepository.save(PendaftaranMapper.mapToEntity(pendaftaran));
        return PendaftaranMapper.mapToDto(result);
    }

    @Override
    public boolean deletePendaftaran(Long id) {
        pendaftaranRepository.deleteById(id);
        return true;
    }

    @Override
    public PendaftaranDto createPendaftaran(PendaftaranDto pendaftaran) {
        Pendaftaran result = pendaftaranRepository.save(PendaftaranMapper.mapToEntity(pendaftaran));
        return PendaftaranMapper.mapToDto(result);
    }
}
