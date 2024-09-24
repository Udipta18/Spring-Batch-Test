package com.example.batch.springBatch.listner;

import com.example.batch.springBatch.domain.Coffee;
import com.example.batch.springBatch.repos.CoffeeRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ItemProcessListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CoffeeProcessorListener implements ItemProcessListener<Coffee,Coffee> {
    

   private final CoffeeRepo coffeeRepo;
    @Autowired
    public CoffeeProcessorListener(CoffeeRepo coffeeRepo) {
        this.coffeeRepo = coffeeRepo;
    }

    @Override
    public void beforeProcess(Coffee item) {
        log.info("Inside before process");
    }

    @Override
    //@Transactional
    public void afterProcess(Coffee item, Coffee result) {
        coffeeRepo.save(result);
        log.info("Inside after process");
    }


    @Override
    public void onProcessError(Coffee item, Exception e) {
        log.info("Inside error process");
    }
}
