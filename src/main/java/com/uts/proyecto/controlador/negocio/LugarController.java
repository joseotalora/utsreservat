/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.uts.proyecto.controlador.negocio;

import com.uts.proyecto.modelos.negocio.Lugar;
import com.uts.proyecto.servicios.negocio.FirebaseFileService;
import com.uts.proyecto.servicios.negocio.LugarService;
import com.uts.proyecto.utils.Util;
import java.util.List;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author otalo
 */
@RestController
@RequestMapping(Util.WEB_SERVER_BASE_API_URL + "/lugar")
public class LugarController {

    @Autowired
    private LugarService lugarService;

    @Autowired
    private FirebaseFileService firebaseFileService;
    
    @GetMapping("/findAll")
    public List<Lugar> findByAll(HttpServletResponse response) {

        List<Lugar> lugares = null;
        try {
            lugares = lugarService.findAll();
            response.setStatus(HttpServletResponse.SC_OK);
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return null;
        }
        return lugares;
    }

    @GetMapping("/byPropietario/{idPropietario}")
    public List<Lugar> findByAll(@PathVariable Long idPropietario, HttpServletResponse response) {

        List<Lugar> lugar = null;
        try {
            lugar = lugarService.findByPropietario(idPropietario);
            response.setStatus(HttpServletResponse.SC_OK);
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return null;
        }
        return lugar;
    }

    @GetMapping("/byCategoria/{idCategoria}")
    public List<Lugar> findByCategoria(@PathVariable Long idCategoria, HttpServletResponse response) {

        List<Lugar> lugar = null;
        try {
            lugar = lugarService.findByCategoria(idCategoria);
            response.setStatus(HttpServletResponse.SC_OK);
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return null;
        }
        return lugar;
    }

    @GetMapping("/findReservas/{idLugar}")
    public Lugar findReservasAll(@PathVariable Long idLugar, HttpServletResponse response) {

        Lugar lugar;
        try {
            lugar = lugarService.findByReservas(idLugar);
            if (lugarService == null) {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                return null;
            }
            response.setStatus(HttpServletResponse.SC_OK);
            return lugar;
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return null;
        }
    }

    @GetMapping("/byId/{idLugar}")
    public Lugar findById(@PathVariable Long idLugar, HttpServletResponse response) {

        Lugar lugar;
        try {
            lugar = lugarService.findById(idLugar);
            if (lugarService == null) {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                return null;
            }
            response.setStatus(HttpServletResponse.SC_OK);
            return lugar;
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return null;
        }
    }

    @PostMapping
    public Lugar createLugar(@RequestBody Lugar lugar, HttpServletResponse response) {
        try {
            Lugar saveLugar = lugarService.create(lugar);
            if (saveLugar == null) {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                return null;
            }

            response.setStatus(HttpServletResponse.SC_CREATED);
            return saveLugar;
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
        return null;
    }

    @PutMapping("/{idLugar}")
    @Transactional
    public Lugar updateStatus(@PathVariable Long idLugar, @RequestBody Lugar lugar, HttpServletResponse response) {
        try {
            Lugar foundlugar = lugarService.update(idLugar, lugar);
            if (foundlugar == null) {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                return null;
            }
            response.setStatus(HttpServletResponse.SC_OK);
            return foundlugar;
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return null;
        }
    }

    @DeleteMapping("{idLugar}")
    @Transactional
    public void deleteLugar(@PathVariable Long idLugar, HttpServletResponse response) {

        try {
            Long resp = lugarService.delete(idLugar);
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

    @DeleteMapping("/eliminarImagen/{idImg}")
    @Transactional
    public void deleteImagen(@PathVariable Long idImg, HttpServletResponse response) {

        try {
            Long resp = lugarService.deleteImg(idImg);
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

    @PostMapping(value = "/guardarImagen/{idLg}", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public int create(@RequestParam(name = "file") MultipartFile file, @PathVariable Long idLg, HttpServletResponse response) {
        int i = 0;
        try {
            if(file.getSize() > 0){                
             String fileName = firebaseFileService.saveTest("place/", file, idLg);
             response.setStatus(HttpServletResponse.SC_OK);
             i = 1;
            }else {
             response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
             i = 0;
            }
            // do whatever you want with that
        } catch (Exception e) {            
            i = 0;
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }       
        return i;
    }
}
