package com.example.aerospikedemo.repositories;


import com.example.aerospikedemo.entities.Food;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface FoodRepository extends JpaRepository<Food, Integer> {
    @Query("update Food food set food.deleted = true where food.id = :id")
    @Modifying
    @Transactional
    void deleteFoodById(@Param("id") Integer id);

    @Query("select food from Food food where food.deleted = false")
    List<Food> findAllByDeleted();

    @Query("select food from Food food where food.id = :id and food.deleted = false")
    Optional<Food> findFoodById(@Param("id") int id);
}
