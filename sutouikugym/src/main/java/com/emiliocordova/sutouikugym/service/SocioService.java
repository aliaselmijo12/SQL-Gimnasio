package com.emiliocordova.sutouikugym.service;

import com.emiliocordova.sutouikugym.model.Socio;
import com.emiliocordova.sutouikugym.repository.SocioRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class SocioService {

    private final SocioRepository socioRepository;

    public SocioService(SocioRepository socioRepository) {
        this.socioRepository = socioRepository;
    }

    public List<Socio> listarTodos() {
        return socioRepository.findAll();
    }

    public Socio buscarPorId(Long id) {
        return socioRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Socio no encontrado"));
    }

    public Socio crear(Socio socio) {
        socio.setId(null);
        return socioRepository.save(socio);
    }

    public Socio actualizar(Long id, Socio datos) {
        Socio socio = buscarPorId(id);
        socio.setNombre(datos.getNombre());
        socio.setPlan(datos.getPlan());
        socio.setMensualidad(datos.getMensualidad());
        socio.setActivo(datos.isActivo());
        return socioRepository.save(socio);
    }

    public void eliminar(Long id) {
        Socio socio = buscarPorId(id);
        socioRepository.delete(socio);
    }

    public List<Socio> listarActivos() {
        return socioRepository.findAll().stream()
                .filter(Socio::isActivo)
                .toList();
    }

    public List<Socio> buscarPorPlan(String plan) {
        return socioRepository.findByPlanIgnoreCase(plan);
    }

    public double calcularIngresos() {
        return socioRepository.findAll().stream()
                .filter(Socio::isActivo)
                .mapToDouble(Socio::getMensualidad)
                .sum();
    }

    public Map<String, Double> calcularIngresosPorPlan() {
        return socioRepository.findAll().stream()
                .filter(Socio::isActivo)
                .collect(Collectors.groupingBy(
                        Socio::getPlan,
                        Collectors.summingDouble(Socio::getMensualidad)
                ));
    }

    public Socio activar(Long id) {
        Socio socio = buscarPorId(id);
        if (socio.isActivo()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "El socio ya está activo");
        }
        socio.setActivo(true);
        return socioRepository.save(socio);
    }

    public Socio desactivar(Long id) {
        Socio socio = buscarPorId(id);
        if (!socio.isActivo()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "El socio ya está inactivo");
        }
        socio.setActivo(false);
        return socioRepository.save(socio);
    }
}