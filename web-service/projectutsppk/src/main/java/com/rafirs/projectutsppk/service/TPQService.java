package com.rafirs.projectutsppk.service;

import com.rafirs.projectutsppk.dto.TPQDto;
import java.util.List;

public interface TPQService {
    public List<TPQDto> getTPQs();
    public List<TPQDto> searchTPQs(String searhTerm);
    public TPQDto getTPQ(Long tpqId);
    public TPQDto updateTPQ(TPQDto tpq);
    public TPQDto createTPQ(TPQDto tpq);
    public int countTPQs();
    public boolean deleteTPQ(Long tpqId);
}
