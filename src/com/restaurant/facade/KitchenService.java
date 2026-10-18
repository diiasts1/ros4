package com.restaurant.facade;

public class KitchenService {
    private static final int MAX_DISHES_IN_QUEUE = 2;
    private int waitingMinutes = 0;
    private int dishesInQueue = 0;

    public int cook(MenuItem dish) throws ServiceException {
        if (dishesInQueue >= MAX_DISHES_IN_QUEUE) {
            throw new ServiceException("Kitchen is too busy");
        }
        dishesInQueue++;
        waitingMinutes += dish.getCookMinutes();
        System.out.println("[Kitchen] Cooking " + dish.getName());
        return waitingMinutes;
    }
}
