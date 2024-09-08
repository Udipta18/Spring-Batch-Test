package com.example.batch.springBatch.processor;

import com.example.batch.springBatch.domain.Coffee;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Slf4j
@Service
public class CoffeeProcessor implements org.springframework.batch.item.ItemProcessor<Coffee,Coffee> {
    @Override
    public Coffee process(Coffee coffee) throws Exception {
        String brand = coffee.getBrand().toUpperCase();
        String origin = coffee.getOrigin().toUpperCase();
        String chracteristics = coffee.getCharacteristics().toUpperCase();
         String processed="Y";
       if(coffee.getProcessed().equalsIgnoreCase("N")){
           processed="Y";
       }

        Coffee transformedCoffee = new Coffee(brand, origin, chracteristics,processed);
        log.info("Converting ( {} ) into ( {} )", coffee, transformedCoffee);

        return transformedCoffee;
    }
}
