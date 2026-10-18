package com.restaurant.facade;

import java.util.HashMap;
import java.util.Map;

public class InventoryService {
    private final Map<String, Integer> stock = new HashMap<>();

    public InventoryService() {
        stock.put("bun", 5);
        stock.put("beef", 5);
        stock.put("dough", 5);
        stock.put("tomato", 10);
        stock.put("cheese", 6);
    }

    public void reserve(MenuItem dish) throws ServiceException {
        for (Map.Entry<String, Integer> need : dish.getRecipe().entrySet()) {
            if (stock.getOrDefault(need.getKey(), 0) < need.getValue()) {
                throw new ServiceException("Not enough " + need.getKey());
            }
        }
        for (Map.Entry<String, Integer> need : dish.getRecipe().entrySet()) {
            stock.merge(need.getKey(), -need.getValue(), Integer::sum);
        }
    }

    public void release(MenuItem dish) {
        for (Map.Entry<String, Integer> item : dish.getRecipe().entrySet()) {
            stock.merge(item.getKey(), item.getValue(), Integer::sum);
        }
    }
}
