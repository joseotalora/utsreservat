/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.uts.proyecto.servicios.negocio;

import com.uts.proyecto.modelos.negocio.Propietario;
import com.uts.proyecto.repositorios.negocio.PropietarioRepository;
import static com.uts.proyecto.utils.AuthUtil.generateSha512;
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
public class PropietarioService {

    @Autowired
    private PropietarioRepository propietarioRepository;

    public Propietario create(Propietario propietario) throws Exception {

        propietario.getEmail().toLowerCase();
        String autoPasstword = "12345";

        if (propietario.getPassword()!= null) {
            propietario.setPassword(generateSha512(propietario.getPassword()));
        } else {
            propietario.setPassword(generateSha512(autoPasstword));
        }

        Propietario savedPropietario = propietarioRepository.saveAndFlush(propietario);

        if (savedPropietario == null) {
            return null;
        }

        return savedPropietario;
    }

    public Propietario findById(Long idPropietario) throws Exception {

        Optional<Propietario> propietario = propietarioRepository.findById(idPropietario);
        if (propietario.get() == null) {
            return null;
        }
        return propietario.get();
    }

    public Propietario update(Long idPropietario, Propietario propietario) throws Exception {

        Optional<Propietario> foundPropietario = propietarioRepository.findById(idPropietario);
        if (foundPropietario == null) {
            return null;
        }

        Propietario updatedPropietario = foundPropietario.get();
        updatedPropietario.setNombres(propietario.getNombres().toUpperCase());
        updatedPropietario.setTelefono(propietario.getTelefono().toUpperCase());
        updatedPropietario.setEmail(propietario.getEmail().toLowerCase());
        if (propietario.getPassword()!= null) {
            updatedPropietario.setPassword(generateSha512(propietario.getPassword()));
        } else {
            updatedPropietario.setPassword(generateSha512(foundPropietario.get().getPassword()));
        }

        Propietario savePropietario = propietarioRepository.saveAndFlush(updatedPropietario);

        if (savePropietario == null) {
            return null;
        }

        return savePropietario;
    }

}
