package com.restaurant.facade;

public class Main {
    private static final String GOOD_CARD = "4111111111111111";

    public static void main(String[] args) {
        RestaurantFacade restaurant = new RestaurantFacade();

        order(restaurant, "alice", "burger", GOOD_CARD);              // success
        order(restaurant, "bob", "pizza", GOOD_CARD);                 // success
        order(restaurant, "carol", "burger", GOOD_CARD);              // kitchen busy -> refund
        order(restaurant, "dave", "pizza", "0000111122223333");       // card declined
        order(restaurant, "erin", "sushi", GOOD_CARD);                // unknown dish
    }

    private static void order(RestaurantFacade restaurant, String customer, String dish, String card) {
        try {
            restaurant.orderDish(customer, dish, card);
        } catch (OrderFailedException e) {
            System.out.println("Order failed for " + customer + ": " + e.getMessage());
        }
    }
}
