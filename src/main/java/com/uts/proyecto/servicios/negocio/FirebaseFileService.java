/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.uts.proyecto.servicios.negocio;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Bucket;
import com.google.cloud.storage.Storage;
import com.google.firebase.cloud.StorageClient;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;
import com.google.cloud.storage.StorageOptions;
import com.uts.proyecto.modelos.negocio.Imagenes;
import com.uts.proyecto.modelos.negocio.Lugar;
import com.uts.proyecto.repositorios.negocio.ImagenesRepository;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author otalo
 */
@Service
public class FirebaseFileService {

    @Autowired
    Properties properties;

    @Autowired
    ImagenesRepository imagenesRepository;

    private Storage storage;

    @EventListener
    public void init(ApplicationReadyEvent event) {
        // initialize Firebase
        try {
            ClassPathResource serviceAccount = new ClassPathResource("firebase.json");
            storage = StorageOptions.newBuilder().
                    setCredentials(GoogleCredentials.fromStream(serviceAccount.getInputStream())).
                    setProjectId("utsreservatfire").build().getService();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }    

    public String saveTest(String folder, MultipartFile file, Long idLg) throws IOException {
        String imageName = generateFileName(file.getOriginalFilename());
        Map<String, String> map = new HashMap<>();
        map.put("firebaseStorageDownloadTokens", imageName);
        BlobId blobId = BlobId.of(properties.bucketName, folder + imageName);
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId)
                .setMetadata(map)
                .setContentType(file.getContentType())
                .build();
        storage.create(blobInfo, file.getInputStream());      
       

        Imagenes img = new Imagenes();
        img.setName(imageName);
        Lugar lg = new Lugar();
        lg.setId(idLg);
        img.setLugar(lg);        
        Imagenes imgs = imagenesRepository.saveAndFlush(img);
        return folder + imageName;
    }
    
   public void delete(String name) throws IOException {
        Bucket bucket = StorageClient.getInstance().bucket();
        if (StringUtils.isEmpty(name)) {
            throw new IOException("invalid file name");
        }
        Blob blob = bucket.get(name);
        if (blob == null) {
            throw new IOException("file not found");
        }
        blob.delete();
    }

    @Data
    @Configuration
    @ConfigurationProperties(prefix = "firebase")
    public class Properties {

        private String projectName;
        private String configFile;
        private String bucketName;
        private String imageUrl;
    }

    private String generateFileName(String originalFileName) {
        return UUID.randomUUID().toString() + "." + getExtension(originalFileName);
    }

    private String getExtension(String originalFileName) {
        return StringUtils.getFilenameExtension(originalFileName);
    }

}
