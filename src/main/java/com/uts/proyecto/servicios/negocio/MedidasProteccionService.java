/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.uts.proyecto.servicios.negocio;

import com.uts.proyecto.modelos.negocio.MedidasProteccion;
import com.uts.proyecto.repositorios.negocio.MedidasProteccionRepository;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author otalo
 */
@Service
@Transactional
public class MedidasProteccionService {

    @Autowired
    private MedidasProteccionRepository medidasProteccionRepository;

    public List<MedidasProteccion> findAll() throws Exception {

        List<MedidasProteccion> categoriaServicio = medidasProteccionRepository.findAll();
        if (categoriaServicio == null) {
            return null;
        }
        categoriaServicio.sort(Comparator.comparing(m -> m.getMedida()));
        return categoriaServicio;

    }

    public MedidasProteccion findById(Long idMedidasProteccion) throws Exception {

        Optional<MedidasProteccion> categoriaEstado = medidasProteccionRepository.findById(idMedidasProteccion);
        if (categoriaEstado == null) {
            return null;
        }
        return categoriaEstado.get();
    }

    public MedidasProteccion create(MedidasProteccion medidasProteccion) throws Exception {
        medidasProteccion.setMedida(medidasProteccion.getMedida().toUpperCase());
        MedidasProteccion savecaMedidasProteccion = medidasProteccionRepository.saveAndFlush(medidasProteccion);
        return savecaMedidasProteccion;
    }

    public MedidasProteccion update(Long idMedidasProteccion, MedidasProteccion medidasProteccion) throws Exception {

        Optional<MedidasProteccion> foundMedidasProteccion = medidasProteccionRepository.findById(idMedidasProteccion);
        if (foundMedidasProteccion == null) {
            return null;
        }

        MedidasProteccion updatedcaMedidasProteccion = foundMedidasProteccion.get();
        updatedcaMedidasProteccion.setMedida(medidasProteccion.getMedida().toUpperCase());

        MedidasProteccion savecaMedidasProteccion = medidasProteccionRepository.saveAndFlush(updatedcaMedidasProteccion);

        return savecaMedidasProteccion;
    }

    public Long delete(Long idMedidasProteccion) throws Exception {
        medidasProteccionRepository.deleteById(idMedidasProteccion);
        return 0L;
    }
}
