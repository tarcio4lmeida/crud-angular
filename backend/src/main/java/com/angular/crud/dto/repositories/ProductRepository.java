package com.angular.crud.dto.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.angular.crud.entities.Product;


@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

}
