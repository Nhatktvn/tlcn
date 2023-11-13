package com.nhomA.mockproject.controller;

import com.nhomA.mockproject.dto.CategoryDTO;
import com.nhomA.mockproject.exception.CategoryNotFoundException;
import com.nhomA.mockproject.service.CategoryService;
import com.nhomA.mockproject.service.UploadFileService;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping
@CrossOrigin
public class CategoryController {
    private final CategoryService categoryService;
    private final UploadFileService uploadFileService;

    public CategoryController(CategoryService categoryService, UploadFileService uploadFileService) {
        this.categoryService = categoryService;
        this.uploadFileService = uploadFileService;
    }
    @GetMapping("/category/{id}")
    public ResponseEntity<?> getCategoryById(@PathVariable Long id){
        try {
            return new ResponseEntity<>(categoryService.getCategoryById(id),HttpStatus.OK);
        }
        catch (CategoryNotFoundException ex){
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }
        catch (Exception ex){
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/category")
    public ResponseEntity<?> getAllCategory(@RequestParam(value = "pageNo",defaultValue = "0")int pageNo,
                                            @RequestParam(value = "pageSize",defaultValue = "10")int pageSize,
                                            @RequestParam(value = "sortBy",defaultValue = "id")String sortBy,
                                            @RequestParam(value = "sortDir",defaultValue = "asc")String sorDir){
        try {
            return new ResponseEntity<>(categoryService.getAllCategory(pageNo,pageSize,sortBy,sorDir),HttpStatus.OK);
        }catch (Exception ex){
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PostMapping("/admin/create-category")
    public  ResponseEntity<?> createCategory(Authentication authentication, @RequestParam("image")MultipartFile multipartFile, @RequestParam("name") String name,
                                             @RequestParam("description") String description) throws IOException {
        String username = authentication.getName();
        String imageURL = uploadFileService.uploadFile(multipartFile);
        CategoryDTO categoryDTO = new CategoryDTO(name,description,imageURL);
        try {
            return new ResponseEntity<>(categoryService.createCategory(categoryDTO,username),HttpStatus.OK);
        }catch (Exception ex){
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PutMapping("/admin/update-category/{id}")
    public ResponseEntity<?> updateCategory (Authentication authentication,@PathVariable Long id,@RequestParam("image")MultipartFile multipartFile, @RequestParam("name") String name,
                                             @RequestParam("description") String description) throws IOException{
        String username = authentication.getName();
        String imageURL = uploadFileService.uploadFile(multipartFile);
        CategoryDTO categoryDTO = new CategoryDTO(name,description,imageURL);
        try {
            return new ResponseEntity<>(categoryService.updateCategoryById(username,id,categoryDTO),HttpStatus.OK);
        }
        catch (AuthenticationException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.UNAUTHORIZED);
        }
        catch (ExpiredJwtException ex){
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.UNAUTHORIZED);
        }
        catch (AccessDeniedException ex){
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.FORBIDDEN);
        }
        catch (Exception ex){
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @DeleteMapping("/admin/delete-category/{id}")
    public ResponseEntity<?> deleteCategory(@PathVariable Long id){
        try {
            return new ResponseEntity<>(categoryService.deleteCategoryById(id),HttpStatus.OK);
        }
        catch (AuthenticationException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.UNAUTHORIZED);
        }
        catch (ExpiredJwtException ex){
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.UNAUTHORIZED);
        }
        catch (AccessDeniedException ex){
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.FORBIDDEN);
        }
        catch (Exception ex){
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
