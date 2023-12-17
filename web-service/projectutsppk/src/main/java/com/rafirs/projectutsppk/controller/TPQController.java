package com.rafirs.projectutsppk.controller;



import com.rafirs.projectutsppk.dto.TPQDto;
import com.rafirs.projectutsppk.service.UserService;
import com.rafirs.projectutsppk.service.TPQService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import java.security.Principal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;

/**
 *
 * @author RafiRS
 */

@Controller
@SessionAttributes("name")
public class TPQController {
    @Autowired
    private UserService userService;

    @Autowired
    private TPQService tpqService;

    @Operation(summary = "Melihat semua tpq yang ada di dalam sistem.")
    @ApiResponses(
        value = {
            @ApiResponse(responseCode = "200", description = "semua data tpq", content = {
                @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = TPQDto.class)))
            })
        }
    )
    @GetMapping("/admin/tpq-view")
    @Secured("ROLE_ADMIN")
    public ResponseEntity<?> getAll(Principal principal) {
        List<TPQDto> tpqs = tpqService.getTPQs();
        return ResponseEntity.ok().body(tpqs);
    }
    
    @Operation(summary = "Melihat semua tpq yang ada di dalam sistem.")
    @ApiResponses(
        value = {
            @ApiResponse(responseCode = "200", description = "semua data tpq", content = {
                @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = TPQDto.class)))
            })
        }
    )
    @GetMapping("/tpq-view")
    @Secured("ROLE_USER")
    public ResponseEntity<?> getAlll(Principal principal) {
        List<TPQDto> tpqs = tpqService.getTPQs();
        return ResponseEntity.ok().body(tpqs);
    }

    @Operation(summary = "Membuat tpq oleh admin")
    @ApiResponses(
        value = {
            @ApiResponse(responseCode = "201", description = "berhasil membuat tpq", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = TPQDto.class))
            })
        }
    )
    @PostMapping("/tpq-create")
    @Secured("ROLE_ADMIN")
    public ResponseEntity<?> create(Principal principal, @RequestBody @Valid TPQDto tpq) {
        TPQDto result = tpqService.createTPQ(tpq);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @Operation(summary = "Menghapus tpq dengan id tertentu.")
    @ApiResponses(
        value = {
            @ApiResponse(responseCode = "200", description = "berhasil menghapus tpq", content = @Content)
        }
    )
    @DeleteMapping("/tpq-delete/{id}")
    @Secured({"ROLE_ADMIN"})
    public ResponseEntity<?> destroy(@Parameter(description = "id dari tpq yang ingin dihapus") @PathVariable("id") Long tpqId) {
        tpqService.deleteTPQ(tpqId);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Mengubah isi data tpq yang sudah ada. Hanya dapat mengubah deskripsi, status penyelesaian, tanggal petpq, serta tipe masalah.")
    @ApiResponses(
        value = {
            @ApiResponse(responseCode = "200", description = "berhasil mengubah tpq", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = TPQDto.class))
            })
        }
    )
    @PutMapping("/tpq-update/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or @securityService.canEditTPQ(principal.id, #tpqId)")
    public ResponseEntity<?> edit(@Parameter(description = "id dari tpq yang ingin diubah") @PathVariable("id") Long tpqId, @RequestBody @Valid TPQDto request) {
        TPQDto tpq = tpqService.getTPQ(tpqId);
        tpq.setAlamat(request.getAlamat());
        tpq.setName(request.getName());
        tpq.setNokontak(request.getNokontak());

        tpq = tpqService.updateTPQ(tpq);
        return ResponseEntity.ok().body(tpq);
    }
}
