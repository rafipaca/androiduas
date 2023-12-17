package com.rafirs.projectutsppk.controller;

import com.rafirs.projectutsppk.dto.PendaftaranDto;
import com.rafirs.projectutsppk.dto.UserDto;
import com.rafirs.projectutsppk.service.PendaftaranService;
import com.rafirs.projectutsppk.service.TPQService;
import com.rafirs.projectutsppk.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import java.security.Principal;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

/**
 *
 * @author RafiRS
 */

@RestController
@RequestMapping
@SessionAttributes("name")
public class PendaftaranController {
    @Autowired
    private UserService userService;
    @Autowired
    private PendaftaranService pendaftaranService;
    @Autowired
    private TPQService tpqService;
    

    @Operation(summary = "Melihat User yang sudah mendaftar, ini hanya bisa diakses oleh Role Admin.")
    @ApiResponses(
        value = {
            @ApiResponse(responseCode = "200", description = "semua data pendaftaran", content = {
                @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = PendaftaranDto.class)))
            })
        }
    )
    @GetMapping("/pendaftaran")
    @Secured({"ROLE_ADMIN"})
    public ResponseEntity<?> getAll(Principal principal) {
        List<PendaftaranDto> pendaftarans = pendaftaranService.getPendaftarans();
        return ResponseEntity.ok().body(pendaftarans);
    }
    @Operation(summary = "Melihat User yang sudah mendaftar dengan id tertentu, ini hanya bisa diakses oleh Role Admin.")
    @ApiResponses(
        value = {
            @ApiResponse(responseCode = "200", description = "semua data pendaftaran", content = {
                @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = PendaftaranDto.class)))
            })
        }
    )    
    @GetMapping("/pendaftaran/{id}")
    @Secured({"ROLE_ADMIN"})
    public ResponseEntity<List<PendaftaranDto>> getPendaftaranByUserId(@PathVariable("id") Long userId) {
        List<PendaftaranDto> pendaftaranDtos = pendaftaranService.getPendaftarans(userId);
        return ResponseEntity.ok().body(pendaftaranDtos);
    }

    
    
    
    @Operation(summary = "Mengajukan Pendaftaran Mentor TPQ")
    @ApiResponses(
        value = {
            @ApiResponse(responseCode = "201", description = "berhasil membuat pendaftaran", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = PendaftaranDto.class))
            })
        }
    )
    @PostMapping("/pendaftaran")
    @Secured("ROLE_USER")
    public ResponseEntity<?> create(Principal principal, @RequestBody @Valid PendaftaranDto pendaftaran) {
        UserDto pendaftar = userService.getUserByEmail(principal.getName());
        pendaftaran.setUser(pendaftar);

        PendaftaranDto result = pendaftaranService.createPendaftaran(pendaftaran);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }
    
    @Operation(summary = "Menghapus pendaftaran dengan id tertentu.")
    @ApiResponses(
        value = {
            @ApiResponse(responseCode = "200", description = "berhasil menghapus pendaftaran", content = @Content)
        }
    )
    @DeleteMapping("/pendaftaran/{id}")
    @Secured({"ROLE_ADMIN"})
    public ResponseEntity<?> destroy(@Parameter(description = "id dari pendaftaran yang ingin dihapus") @PathVariable("id") Long pendaftaranId) {
        pendaftaranService.deletePendaftaran(pendaftaranId);
        return ResponseEntity.ok().build();
    }
    
    @Operation(summary = "Approve pendaftaran calon mentor TPQ")
    @ApiResponses(
        value = {
            @ApiResponse(responseCode = "200", description = "data user yang telah diperbarui", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = UserDto.class))
            })
        }
    )
    @PutMapping("/pendaftaran/approve/{id}")
    @Secured({"ROLE_ADMIN"})
    public ResponseEntity<?> approvePendaftaran(@PathVariable("id") Long pendaftaranId) {
        PendaftaranDto pendaftaran = pendaftaranService.getPendaftaran(pendaftaranId);
    
        pendaftaran.setStatusPendaftaran(true); // Ubah status persetujuan menjadi "true" (disetujui).
        pendaftaranService.updatePendaftaran(pendaftaran);

        return ResponseEntity.ok().body("Pendaftaran telah disetujui");
}


}
