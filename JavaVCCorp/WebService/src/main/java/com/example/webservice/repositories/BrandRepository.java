package com.example.webservice.repositories;

import com.example.webservice.entities.Brand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BrandRepository extends JpaRepository<Brand, Integer> {
    @Query("select brand from Brand brand where brand.deleted = false")
    List<Brand> findAllByDeleted();

    @Query("select brand from Brand brand where brand.id = :id")
    Optional<Brand> findBrandById(@Param("id") int id);
}