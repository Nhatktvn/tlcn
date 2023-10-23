package com.nhomA.mockproject.controller;

import com.nhomA.mockproject.dto.CategoryDTO;
import com.nhomA.mockproject.service.CategoryService;
import com.nhomA.mockproject.service.UploadFileService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/category")
public class CategoryController {
    private final CategoryService categoryService;
    private final UploadFileService uploadFileService;

    public CategoryController(CategoryService categoryService, UploadFileService uploadFileService) {
        this.categoryService = categoryService;
        this.uploadFileService = uploadFileService;
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> getCategoryById(@PathVariable Long id){
        try {
            return new ResponseEntity<>(categoryService.getCategoryById(id),HttpStatus.OK);
        }catch (Exception ex){
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping
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
    @PostMapping
    public  ResponseEntity<?> createCategory(@RequestParam("image")MultipartFile multipartFile, @RequestParam("name") String name,
                                             @RequestParam("description") String description) throws IOException {
        String imageURL = uploadFileService.uploadFile(multipartFile);
        CategoryDTO categoryDTO = new CategoryDTO(name,description,imageURL);
        try {
            return new ResponseEntity<>(categoryService.createCategory(categoryDTO),HttpStatus.OK);
        }catch (Exception ex){
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> updateCategory (@PathVariable Long id,@RequestParam("image")MultipartFile multipartFile, @RequestParam("name") String name,
                                             @RequestParam("description") String description) throws IOException{
        String imageURL = uploadFileService.uploadFile(multipartFile);
        CategoryDTO categoryDTO = new CategoryDTO(name,description,imageURL);
        try {
            return new ResponseEntity<>(categoryService.updateCategoryById(id,categoryDTO),HttpStatus.OK);
        }catch (Exception ex){
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCategory(@PathVariable Long id){
        try {
            return new ResponseEntity<>(categoryService.deleteCategoryById(id),HttpStatus.OK);
        }catch (Exception ex){
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
