package com.nhomA.mockproject.controller;

import com.nhomA.mockproject.dto.IdentificationDTO;
import com.nhomA.mockproject.service.IdentificationService;
import com.nhomA.mockproject.service.UploadFileService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;

@RestController
@RequestMapping("/identification")
@CrossOrigin
public class IdentificationController {
    private final IdentificationService identificationService;
    private final UploadFileService uploadFileService;

    public IdentificationController(IdentificationService identificationService, UploadFileService uploadFileService) {
        this.identificationService = identificationService;
        this.uploadFileService = uploadFileService;
    }

    @GetMapping
    public ResponseEntity<?> getIdentification(Authentication authentication)
    {
        String username = authentication.getName();
        try{
            return new ResponseEntity<> (identificationService.getIdentification(username), HttpStatus.OK);
        }catch (Exception ex){
            return new ResponseEntity<> (ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping
    public ResponseEntity<?> updateIdentification(Authentication authentication, @RequestParam("image") MultipartFile multipartFile,
                                                  @RequestParam("fullName") String fullName, @RequestParam("birthDate") LocalDate birthDate,
                                                  @RequestParam("phone") String phone, @RequestParam("email") String email
                                                  ) throws IOException {
        String imageURL = uploadFileService.uploadFile(multipartFile);
        String username = authentication.getName();
        IdentificationDTO identificationDTO = new IdentificationDTO(fullName,birthDate,phone,email,imageURL);
        try{
            return new ResponseEntity<> (identificationService.updateIdentification(username,identificationDTO), HttpStatus.OK);
        }catch (Exception ex){
            return new ResponseEntity<> (ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
