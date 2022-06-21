/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.uts.proyecto.servicios.negocio;

import com.uts.proyecto.modelos.negocio.CategoriaServicio;
import com.uts.proyecto.repositorios.negocio.CategoriaServicioRepository;
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
public class CategoriaServicioService {

    @Autowired
    private CategoriaServicioRepository categoriaServicioRepository;

    public List<CategoriaServicio> findAll() throws Exception {

        List<CategoriaServicio> categoriaServicio = categoriaServicioRepository.findAll();
        if (categoriaServicio == null) {
            return null;
        }
        categoriaServicio.sort(Comparator.comparing(m -> m.getCategory()));
        return categoriaServicio;

    }

    public CategoriaServicio findById(Long idCategoriaServicio) throws Exception {

        Optional<CategoriaServicio> categoriaEstado = categoriaServicioRepository.findById(idCategoriaServicio);
        if (categoriaEstado == null) {
            return null;
        }
        return categoriaEstado.get();
    }

    public CategoriaServicio create(CategoriaServicio categoriaEstado) throws Exception {
        categoriaEstado.setCategory(categoriaEstado.getCategory().toUpperCase());
        categoriaEstado.setDescription(categoriaEstado.getDescription().toUpperCase());
        CategoriaServicio savecaCategoriaServicio = categoriaServicioRepository.saveAndFlush(categoriaEstado);
        return savecaCategoriaServicio;
    }

    public CategoriaServicio update(Long idCategoriaServicio, CategoriaServicio categoriaEstado) throws Exception {

        Optional<CategoriaServicio> foundCategoriaServicio = categoriaServicioRepository.findById(idCategoriaServicio);
        if (foundCategoriaServicio == null) {
            return null;
        }

        CategoriaServicio updatedcaCategoriaServicio = foundCategoriaServicio.get();
        updatedcaCategoriaServicio.setCategory(categoriaEstado.getCategory().toUpperCase());
        updatedcaCategoriaServicio.setDescription(categoriaEstado.getDescription().toUpperCase());

        CategoriaServicio savecaCategoriaServicio = categoriaServicioRepository.saveAndFlush(updatedcaCategoriaServicio);

        return savecaCategoriaServicio;
    }

    public Long delete(Long idCategoriaServicio) throws Exception {
        categoriaServicioRepository.deleteById(idCategoriaServicio);
        return 0L;
    }
}
