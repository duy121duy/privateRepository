package com.example.webservice.services;

import com.example.webservice.entities.Brand;

import java.util.List;

public interface BrandService {
    List<Brand> findAllBrands();

    Brand findBrandById(int id);

    Brand addBrand(Brand brand);
}
