package com.asianpaints.apse.service_engineer.controller;

import com.asianpaints.apse.service_engineer.domain.entity.ProductMaster;
import com.asianpaints.apse.service_engineer.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1/product")
@RequiredArgsConstructor
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping()
    public ResponseEntity<Object> getAllProducts(){
        List<ProductMaster> products = productService.getAllProducts();
        return ResponseEntity.ok(products);
    }

}
