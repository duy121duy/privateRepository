package com.example.webservice.controller;

import com.example.webservice.entities.Brand;
import com.example.webservice.entities.Form;
import com.example.webservice.services.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/api/public/brand")
public class BrandController {
    private final BrandService brandService;

    @Autowired
    public BrandController(BrandService brandService) {
        this.brandService = brandService;
    }

    @GetMapping(value = "/all")
    public ResponseEntity<List<Brand>> getAllFoods() {
        List<Brand> brands = brandService.findAllBrands();
        return new ResponseEntity<>(brands, HttpStatus.OK);
    }

    @GetMapping(value = "/findPathVariable/{id}")
    public ResponseEntity<String> getBrandByIdPathVariable(@PathVariable("id") int id) {
        Brand brand = brandService.findBrandById(id);
        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(brand.toString());
    }

    @GetMapping(value = "/findRequestParam")
    public ResponseEntity<Brand> getBrandByIdRequestParam(@RequestParam(value = "id", required = true) int id) {
        Brand brand = brandService.findBrandById(id);
        return new ResponseEntity<>(brand, HttpStatus.OK);
    }

    @PostMapping("/addRequestBody")
    public ResponseEntity<Brand> addFoodRequestBody(@RequestBody Brand brand) {
        Brand newBrand = brandService.addBrand(brand);
        return new ResponseEntity<>(newBrand, HttpStatus.CREATED);
    }

    @GetMapping("/findHeader")
    public ResponseEntity<Brand> getBrandByIdHeader(@RequestHeader(value = "id") int id) throws IOException {
        Brand brand = brandService.findBrandById(id);
        return new ResponseEntity<>(brand, HttpStatus.OK);
    }

    @GetMapping("/findRequestBody")
    public ResponseEntity<Brand> getBrandByIdRequestBody(@RequestBody Form id) throws IOException {
        Brand brand = brandService.findBrandById(id.getId());
        return new ResponseEntity<>(brand, HttpStatus.OK);
    }

    @PostMapping("/addRequestPart")
    public ResponseEntity<Brand> addFoodRequestPart(@RequestPart(value = "name") String name) {
        Brand newBrand = brandService.addBrand(new Brand(0, name, false));
        return new ResponseEntity<>(newBrand, HttpStatus.CREATED);
    }

    @PostMapping("/addRequestBody1")
    public ResponseEntity<Brand> addFoodRequestBody1(@RequestBody MultiValueMap<String, String> name) {
        Brand newBrand = brandService.addBrand(new Brand(0, name.getFirst("name"), false));
        return new ResponseEntity<>(newBrand, HttpStatus.CREATED);
    }

}
