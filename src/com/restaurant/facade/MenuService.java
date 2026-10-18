package com.restaurant.facade;

import java.util.HashMap;
import java.util.Map;

public class MenuService {
    private final Map<String, MenuItem> dishes = new HashMap<>();

    public MenuService() {
        dishes.put("burger", new MenuItem("burger", 2500, 10, Map.of("bun", 1, "beef", 1, "cheese", 1)));
        dishes.put("pizza", new MenuItem("pizza", 3800, 15, Map.of("dough", 1, "tomato", 2, "cheese", 2)));
    }

    public MenuItem find(String name) throws ServiceException {
        MenuItem item = dishes.get(name);
        if (item == null) {
            throw new ServiceException("No such dish: " + name);
        }
        return item;
    }
}
