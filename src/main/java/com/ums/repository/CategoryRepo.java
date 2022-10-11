package com.ums.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ums.entity.Categories;
public interface CategoryRepo extends JpaRepository<Categories, Integer> {

}
