package com.example.batch.springBatch.repos;

import com.example.batch.springBatch.domain.PostGresTest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface PostGresRepo extends JpaRepository<PostGresTest,Integer> {
}
