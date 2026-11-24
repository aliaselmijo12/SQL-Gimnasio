package com.emiliocordova.sutouikugym.controller;

import com.emiliocordova.sutouikugym.model.Socio;
import com.emiliocordova.sutouikugym.service.SocioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/socio")
public class SocioController {

    private final SocioService socioService;

    public SocioController(SocioService socioService) {
        this.socioService = socioService;
    }

    @GetMapping
    public List<Socio> listarTodos() {
        return socioService.listarTodos();
    }

    @GetMapping("/{id}")
    public Socio obtenerPorId(@PathVariable Long id) {
        return socioService.buscarPorId(id);
    }

    @PostMapping
    public ResponseEntity<Socio> crear(@RequestBody Socio socio) {
        Socio creado = socioService.crear(socio);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    @PutMapping("/{id}")
    public Socio actualizar(@PathVariable Long id, @RequestBody Socio socio) {
        return socioService.actualizar(id, socio);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        socioService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/activos")
    public List<Socio> listarActivos() {
        return socioService.listarActivos();
    }

    @GetMapping("/plan/{plan}")
    public List<Socio> buscarPorPlan(@PathVariable String plan) {
        return socioService.buscarPorPlan(plan);
    }

    @GetMapping("/ingresos")
    public double obtenerIngresos() {
        return socioService.calcularIngresos();
    }

    @GetMapping("/ingresos-por-plan")
    public Map<String, Double> obtenerIngresosPorPlan() {
        return socioService.calcularIngresosPorPlan();
    }

    @PutMapping("/{id}/activar")
    public Socio activar(@PathVariable Long id) {
        return socioService.activar(id);
    }

    @PutMapping("/{id}/desactivar")
    public Socio desactivar(@PathVariable Long id) {
        return socioService.desactivar(id);
    }
}