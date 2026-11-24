package com.emiliocordova.sutouikugym.repository;

import com.emiliocordova.sutouikugym.model.Socio;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface SocioRepository extends JpaRepository<Socio, Long> {
    List<Socio> findByPlanIgnoreCase(String plan);
}