package com.ansa.rest;


import com.ansa.configuration.xauth.SimpleUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequestMapping(value = "/user/photo")
public class UserProfileController {
    @Autowired
    private Environment env;

    @Value("${app.directory}")
    private String directory;

    @PostConstruct
    public void init(){
        System.out.println(directory);
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public ResponseEntity<?> uploadFile(@RequestParam("uploadfile") MultipartFile uploadfile){
        try {
            // Get the filename and build the local file path
            String filename = uploadfile.getOriginalFilename();
            //String directory = env.getProperty("paths.uploadedFiles");
            String filepath = Paths.get(directory, filename).toString();
            System.out.println(Paths.get(directory, filename).toFile().getAbsoluteFile());
            // Save the file locally
            BufferedOutputStream stream =
                    new BufferedOutputStream(new FileOutputStream(new File(filepath)));
            stream.write(uploadfile.getBytes());
            stream.close();
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ResponseEntity<byte[]> getPhoto(){

        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            SimpleUserDetails userDetails = (SimpleUserDetails)authentication.getPrincipal();
            Path path = Paths.get(directory, userDetails.getUsername());
            final HttpHeaders headers = new HttpHeaders();
            return new ResponseEntity<byte[]>(Files.readAllBytes(path), headers, HttpStatus.FOUND);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }


}
