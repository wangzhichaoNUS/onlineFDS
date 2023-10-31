package edu.mum.dream.service;

import java.util.List;

import edu.mum.dream.domain.*;


public interface FoodItemService {
    public void save(FoodItem order);
    public FoodItem update(FoodItem order);
    public List<FoodItem> findAll();
    public FoodItem findOne(Long id);

}
