package com.theironyard.controllers;

import com.theironyard.entities.AnonFile;
import com.theironyard.services.AnonFileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

/**
 * Created by jessicahuffstutler on 11/18/15.
 */
@RestController
public class AnonUploadController {
    @Autowired
    AnonFileRepository files;

    @RequestMapping("/files")
    public List<AnonFile> getFiles() { //no git parameters needed
        return (List<AnonFile>) files.findAll();
    }

    @RequestMapping("/upload")
    public void upload(HttpServletResponse response, MultipartFile file, boolean isPermanent, String comment) throws IOException { //we called it name="file" in html so we need MultipartFile file
        File f = File.createTempFile("file", file.getOriginalFilename(), new File("public"));
        FileOutputStream fos = new FileOutputStream(f);
        fos.write(file.getBytes());

        AnonFile anonFile = new AnonFile();
        anonFile.originalName = file.getOriginalFilename();
        anonFile.name = f.getName();//name on the server, where we're saving it
        anonFile.comment = comment;
        anonFile.isPermanent = isPermanent;
        files.save(anonFile);

        List<AnonFile> afl = (List<AnonFile>) files.findAll();

        ArrayList<AnonFile> npfl = new ArrayList(); //list of non permanent images

        for (AnonFile p : afl) {
            if(!p.isPermanent) {
                npfl.add(p);
            }
        }

        if (npfl.size() > 3) {
            for (int i = 0; i < npfl.size() - 3; i++) {
                AnonFile anonFileToRemove = npfl.get(0);
                files.delete(anonFileToRemove.id);
                File tempFile = new File("public", anonFileToRemove.name);
                tempFile.delete();
            }
        }

        response.sendRedirect("/");
    }
}