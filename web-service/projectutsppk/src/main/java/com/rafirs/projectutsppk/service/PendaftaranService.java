
package com.rafirs.projectutsppk.service;

import com.rafirs.projectutsppk.dto.PendaftaranDto;
import java.util.List;

/**
 *
 * @author RafiRS
 */

public interface PendaftaranService {
    public List<PendaftaranDto> getPendaftarans();
    public List<PendaftaranDto> getPendaftarans(long id);
    public PendaftaranDto getPendaftaran(Long pendaftaranId);
    public PendaftaranDto updatePendaftaran(PendaftaranDto pendaftaran);
    public boolean deletePendaftaran(Long pendaftaranId);
    public PendaftaranDto createPendaftaran(PendaftaranDto pendaftaran);
    public void approvePendaftaran(PendaftaranDto pendaftaranDto);
    public int getJmlPendaftaran();
}
