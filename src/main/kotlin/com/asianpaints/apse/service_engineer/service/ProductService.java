package com.asianpaints.apse.service_engineer.service;

import com.asianpaints.apse.service_engineer.domain.entity.ProductMaster;
import com.asianpaints.apse.service_engineer.repository.ProductMasterRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private ProductMasterRepository productMasterRepository;

    public ProductService(ProductMasterRepository productMasterRepository){
        this.productMasterRepository = productMasterRepository;
    }

    public List<ProductMaster> getAllProducts(){
        return productMasterRepository.findAll();
    }
}
