package com.example.webservice.services.impl;

import com.example.webservice.entities.Brand;
import com.example.webservice.repositories.BrandRepository;
import com.example.webservice.services.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class BrandServiceImpl implements BrandService {
    private final BrandRepository brandRepository;

    @Autowired
    public BrandServiceImpl(BrandRepository brandRepository) {
        this.brandRepository = brandRepository;
    }

    @Override
    public List<Brand> findAllBrands() {
        return brandRepository.findAllByDeleted();
    }

    @Override
    public Brand addBrand(Brand brand) {
        brand.setDeleted(false);
        return brandRepository.save(brand);
    }

    @Override
    public Brand findBrandById(int id) {
        return brandRepository.findBrandById(id).
                orElseThrow(() -> new EntityNotFoundException());
    }
}
