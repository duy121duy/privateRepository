package com.example.aerospikedemo.services.impl;

import com.example.aerospikedemo.entities.Food;
import com.example.aerospikedemo.exception.NotExistException;
import com.example.aerospikedemo.services.FoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.aerospikedemo.repositories.FoodRepository;

import java.util.Date;
import java.util.List;


@Service
public class FoodServiceImpl implements FoodService {
    private final FoodRepository foodRepository;

    @Autowired
    public FoodServiceImpl(FoodRepository foodRepository) {
        this.foodRepository = foodRepository;
    }

    @Override
    public Food addFood(Food food) {
        food.setDeleted(false);
        return foodRepository.save(food);
    }

    @Override
    public List<Food> findAllFoods() {
        return foodRepository.findAllByDeleted();
    }

    @Override
    public Food updateFood(Food food) {
        return foodRepository.save(food);
    }

    @Override
    public Food findFoodById(int id) throws NotExistException {
        return foodRepository.findFoodById(id).
                orElseThrow(() -> new NotExistException("Food by id " + id + "was not found"));
    }

    @Override
    public void deleteFoodById(int id) {
        foodRepository.deleteFoodById(id);
    }

    @Override
    public String evaluateFoodById(int id) throws NotExistException {
        Food food = foodRepository.findFoodById(id).
                orElseThrow(() -> new NotExistException("Food by id " + id + "was not found"));
        Date currentDate = new Date();
        if (food.getInventoryNumber() > 0 && food.getExpireDate().compareTo(currentDate) < 0) {
            return "SELL HARDLY";
        } else {
            return "NO EVALUATE";
        }
    }
}
