package com.example.batch.springBatch.repos;

import com.example.batch.springBatch.domain.Coffee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CoffeeRepo extends JpaRepository<Coffee,String> {
}
