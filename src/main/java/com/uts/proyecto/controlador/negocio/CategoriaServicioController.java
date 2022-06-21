/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.uts.proyecto.controlador.negocio;

import com.uts.proyecto.modelos.negocio.CategoriaServicio;
import com.uts.proyecto.servicios.negocio.CategoriaServicioService;
import com.uts.proyecto.utils.Util;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author otalo
 */
@RestController
@RequestMapping(Util.WEB_SERVER_BASE_API_URL + "/categoriaServicio")
public class CategoriaServicioController {

    @Autowired
    private CategoriaServicioService categoriaServicioService;

    @GetMapping()
    public List<CategoriaServicio> findByAll(HttpServletResponse response) {

        List<CategoriaServicio> categoriaEstados = null;
        try {
            categoriaEstados = categoriaServicioService.findAll();
            response.setStatus(HttpServletResponse.SC_OK);
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return null;
        }
        return categoriaEstados;
    }

    @GetMapping("/{idCategoriaServicio}")
    public CategoriaServicio findById(@PathVariable Long idCategoriaServicio, HttpServletResponse response) {

        CategoriaServicio categoriaEstado;
        try {
            categoriaEstado = categoriaServicioService.findById(idCategoriaServicio);
            if (categoriaServicioService == null) {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                return null;
            }
            response.setStatus(HttpServletResponse.SC_OK);
            return categoriaEstado;
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return null;
        }
    }

    @PostMapping
    public CategoriaServicio createCategoriaServicio(@RequestBody CategoriaServicio categoriaEstado, HttpServletResponse response) {
        try {
            CategoriaServicio savecategoriaEstado = categoriaServicioService.create(categoriaEstado);
            if (savecategoriaEstado == null) {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                return null;
            }

            response.setStatus(HttpServletResponse.SC_CREATED);
            return savecategoriaEstado;
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
        return null;
    }

    @PutMapping("/{idCategoriaServicio}")
    @Transactional
    public CategoriaServicio updateStatus(@PathVariable Long idCategoriaServicio, @RequestBody CategoriaServicio categoriaEstado, HttpServletResponse response) {
        try {
            CategoriaServicio foundedcaCategoriaServicio = categoriaServicioService.update(idCategoriaServicio, categoriaEstado);
            if (foundedcaCategoriaServicio == null) {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                return null;
            }
            response.setStatus(HttpServletResponse.SC_OK);
            return foundedcaCategoriaServicio;
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return null;
        }
    }

    @DeleteMapping("{idCategoriaServicio}")
    @Transactional
    public void deleteCategoriaServicio(@PathVariable Long idCategoriaServicio, HttpServletResponse response) {

        try {
            Long resp = categoriaServicioService.delete(idCategoriaServicio);
            if (resp != 1) {
                response.setStatus(HttpServletResponse.SC_OK);
            } else {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}
