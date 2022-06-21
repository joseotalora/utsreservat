/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.uts.proyecto.servicios.negocio;

import com.uts.proyecto.modelos.negocio.Imagenes;
import com.uts.proyecto.repositorios.negocio.ImagenesRepository;
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
public class ImagenesService {

    @Autowired
    private ImagenesRepository imagenesRepository;

    @Autowired
    private FirebaseFileService firebaseFileService;

    public List<Imagenes> findAll() throws Exception {

        List<Imagenes> imagenes = imagenesRepository.findAll();
        if (imagenes == null) {
            return null;
        }
        imagenes.sort(Comparator.comparing(m -> m.getName()));
        return imagenes;
    }

    public Imagenes findById(Long idImagenes) throws Exception {

        Optional<Imagenes> imagenes = imagenesRepository.findById(idImagenes);
        if (imagenes == null) {
            return null;
        }
        return imagenes.get();
    }

    public Imagenes create(Imagenes imagenes) throws Exception {
        imagenes.setName(imagenes.getName());
        imagenes.setLugar(imagenes.getLugar());
        Imagenes saveImagenes = imagenesRepository.saveAndFlush(imagenes);
        return saveImagenes;
    }

    public Long delete(String name, Long id) throws Exception {

        try {
            imagenesRepository.deleteById(id);
            firebaseFileService.delete(name);
            return 0L;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return 1L;
    }
}
