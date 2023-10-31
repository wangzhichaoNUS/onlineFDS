package edu.mum.dream.service.impl;

import java.util.List;

import edu.mum.dream.dao.FoodItemDao;
import edu.mum.dream.domain.FoodItem;
import edu.mum.dream.service.FoodItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import edu.mum.dream.dao.OrderDao;
import edu.mum.dream.domain.Order;
import edu.mum.dream.service.OrderService;
@Service
@Transactional
public class FoodItemServiceImpl implements FoodItemService {
    @Autowired
    private FoodItemDao foodItemDao;

    public void save( FoodItem order) {
        foodItemDao.save(order);
    }

    public FoodItem update(FoodItem order) {
        return foodItemDao.update(order);
    }


    public List<FoodItem> findAll() {
        return (List<FoodItem>)foodItemDao.findAll();
    }


    public FoodItem findOne(Long id) {
        return foodItemDao.findOne(id);
    }



}
