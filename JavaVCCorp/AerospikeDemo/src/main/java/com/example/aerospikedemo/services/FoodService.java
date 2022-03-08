package com.example.aerospikedemo.services;


import com.example.aerospikedemo.entities.Food;
import com.example.aerospikedemo.exception.NotExistException;

import java.util.List;

public interface FoodService {
    Food addFood(Food food);

    List<Food> findAllFoods();

    void deleteFoodById(int id);

    String evaluateFoodById(int id) throws NotExistException;

    Food findFoodById(int id) throws NotExistException;

    Food updateFood(Food food);
}
