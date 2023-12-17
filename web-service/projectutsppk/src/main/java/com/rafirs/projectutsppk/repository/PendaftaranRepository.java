/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.rafirs.projectutsppk.repository;

import com.rafirs.projectutsppk.entity.Pendaftaran;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author RafiRS
 */

@Repository
public interface PendaftaranRepository extends JpaRepository<Pendaftaran, Long> {

}
