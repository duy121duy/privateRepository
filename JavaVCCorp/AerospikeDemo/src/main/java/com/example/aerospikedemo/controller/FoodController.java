package com.example.aerospikedemo.controller;

import com.example.aerospikedemo.entities.Food;
import com.example.aerospikedemo.exception.NotExistException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.aerospikedemo.services.FoodService;

import java.util.List;

@RestController
@RequestMapping("/food")
public class FoodController {
    private final Logger LOG = LoggerFactory.getLogger(FoodController.class);

    private final FoodService foodService;

    @Autowired
    public FoodController(FoodService foodService) {
        this.foodService = foodService;
    }

    @GetMapping(value = "/all")
    public ResponseEntity<List<Food>> getAllFoods() {
        List<Food> foods = foodService.findAllFoods();
        return new ResponseEntity<>(foods, HttpStatus.OK);
    }

    @GetMapping(value = "/find/{id}")
    @Cacheable(value = "test", key = "#id")
    public Food getFoodById(@PathVariable("id") int id) {
        LOG.info("Getting food with ID {}.", id);
        return foodService.findFoodById(id);
    }

    @PostMapping("/add")
    public ResponseEntity<Food> addFood(@RequestBody Food food) {
        Food newFood = foodService.addFood(food);
        return new ResponseEntity<>(newFood, HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public ResponseEntity<Food> updateFood(@RequestBody Food food) {
        Food updateFood = foodService.updateFood(food);
        return new ResponseEntity<>(updateFood, HttpStatus.OK);
    }

    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<?> deleteFoodById(@PathVariable(value = "id") int id) {
        foodService.deleteFoodById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(value = "/evaluate/{id}")
    public ResponseEntity<String> evaluateById(@PathVariable(value = "id") int id) throws NotExistException {
        String evaluateFoodString = foodService.evaluateFoodById(id);
        return new ResponseEntity<>(evaluateFoodString, HttpStatus.OK);
    }

    public Food getFood() {
        return foodService.findFoodById(1);
    }

}