package com.rafirs.projectutsppk.dto;

import lombok.Builder;
import lombok.Data;
import com.rafirs.projectutsppk.entity.ERole;

/**
 *
 * @author RafiRS
 */

@Data
@Builder
public class RoleDto {
    private Long id;
    private ERole name;
}
