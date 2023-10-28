package com.nhomA.mockproject.controller;

import com.nhomA.mockproject.dto.ProductRequestDTO;
import com.nhomA.mockproject.service.ProductService;
import com.nhomA.mockproject.service.UploadFileService;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping
@CrossOrigin
public class ProductController {
    private final ProductService productService;
    private final UploadFileService uploadFileService;

    public ProductController(ProductService productService, UploadFileService uploadFileService) {
        this.productService = productService;
        this.uploadFileService = uploadFileService;
    }

    @GetMapping("/product/search")
    public ResponseEntity<?> getProductById(@RequestParam("searchName") String searchName){
        try {
            return new ResponseEntity<>(productService.searchProduct(searchName), HttpStatus.OK);
        }catch (Exception ex){
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/product/{id}")
    public ResponseEntity<?> getProductById(@PathVariable Long id){
        try {
            return new ResponseEntity<>(productService.getProductById(id), HttpStatus.OK);
        }catch (Exception ex){
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/all-products")
    public ResponseEntity<?> getAllProduct(@RequestParam(value = "pageNo",defaultValue = "0")int pageNo,
                                            @RequestParam(value = "pageSize",defaultValue = "10")int pageSize,
                                            @RequestParam(value = "sortBy",defaultValue = "id")String sortBy,
                                            @RequestParam(value = "sortDir",defaultValue = "asc")String sorDir){
        try {
            return new ResponseEntity<>(productService.getAllProduct(pageNo,pageSize,sortBy,sorDir),HttpStatus.OK);
        }catch (Exception ex){
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/products-by-cate")
    public ResponseEntity<?> getAllProductByCateGory(@RequestParam("cateId") Long categoryId){
        try {
            return new ResponseEntity<>(productService.getProductsByCategory(categoryId),HttpStatus.OK);
        }catch (Exception ex){
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PostMapping("/admin/add-product")
    public  ResponseEntity<?> createProduct(Authentication authentication, @RequestParam("image")MultipartFile multipartFile, @RequestParam("name") String name,
                                            @RequestParam("category_id") Long categoryId, @RequestParam("available") int available,
                                            @RequestParam("discount") double discount, @RequestParam("price") double price, @RequestParam("description") String description) throws IOException {
        String username = authentication.getName();
        String imageURL = uploadFileService.uploadFile(multipartFile);
        ProductRequestDTO productRequestDTO = new ProductRequestDTO(name,categoryId,available,discount,price,imageURL,description);
        try {
            return new ResponseEntity<>(productService.createProduct(username, productRequestDTO),HttpStatus.OK);
        }catch (Exception ex){
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PutMapping("/admin/update-product/{id}")
    public ResponseEntity<?> updateProduct (Authentication authentication,@PathVariable Long id, @RequestParam("image")MultipartFile multipartFile, @RequestParam("name") String name,
                                            @RequestParam("category_id") Long categoryId, @RequestParam("available") int available,
                                            @RequestParam("discount") double discount, @RequestParam("price") double price, @RequestParam("description") String description) throws IOException {
        String username = authentication.getName();
        String imageURL = uploadFileService.uploadFile(multipartFile);
        ProductRequestDTO productRequestDTO = new ProductRequestDTO(name,categoryId,available,discount,price,imageURL,description);
        try {
            return new ResponseEntity<>(productService.updateProductById(username,id, productRequestDTO),HttpStatus.OK);
        }catch (Exception ex){
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @DeleteMapping("/admin/delete-product/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable("id") Long id){
        try {
            return new ResponseEntity<>(productService.deleteProductById(id),HttpStatus.OK);
        }catch (Exception ex){
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
