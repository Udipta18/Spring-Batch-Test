package com.example.batch.springBatch.listner;

import com.example.batch.springBatch.domain.Coffee;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ItemProcessListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CoffeeProcessorListener implements ItemProcessListener<Coffee,Coffee> {

    @Override
    public void beforeProcess(Coffee item) {
        log.info("Inside before process");
    }

    @Override
    public void afterProcess(Coffee item, Coffee result) {
        log.info("Inside after process");
    }


    @Override
    public void onProcessError(Coffee item, Exception e) {
        log.info("Inside error process");
    }
}
