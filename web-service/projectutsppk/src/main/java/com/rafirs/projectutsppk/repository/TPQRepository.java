/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.rafirs.projectutsppk.repository;

import com.rafirs.projectutsppk.entity.TPQ;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author RafiRS
 */

@Repository
public interface TPQRepository extends JpaRepository<TPQ, Long> {
    List<TPQ> findByNameContaining(String searchTerm);
    @Override
    Page<TPQ> findAll(Pageable pageable);
}
