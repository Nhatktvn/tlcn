package com.nhomA.mockproject.controller;

import com.nhomA.mockproject.dto.AddressDTO;
import com.nhomA.mockproject.service.AddressService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user/address")
@CrossOrigin
public class AddressController {
    private final AddressService addressService;

    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }

    @GetMapping
    public ResponseEntity<?> getAllAddress (Authentication authentication){
        try{
            String username = authentication.getName();
            return new ResponseEntity<> (addressService.getAllAddress(username), HttpStatus.OK);
        }catch (Exception ex){
            return new ResponseEntity<> (ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PostMapping
    public ResponseEntity<?> createAddress (Authentication authentication, @RequestBody AddressDTO addressDTO){
        try{
            String username = authentication.getName();
            return new ResponseEntity<> (addressService.createAddress(username,addressDTO), HttpStatus.CREATED);
        }catch (Exception ex){
            return new ResponseEntity<> (ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> updateAddressById (Authentication authentication,@PathVariable("id") Long id, @RequestBody AddressDTO addressDTO){
        try{
            return new ResponseEntity<> (addressService.updateAddressById(id,addressDTO), HttpStatus.OK);
        }catch (Exception ex){
            return new ResponseEntity<> (ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAddressById (Authentication authentication,@PathVariable("id") Long id){
        try{
            return new ResponseEntity<> (addressService.deleteAddressById(id), HttpStatus.OK);
        }catch (Exception ex){
            return new ResponseEntity<> (ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
