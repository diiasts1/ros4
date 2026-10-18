package com.restaurant.facade;

public class OrderFailedException extends Exception {
    public OrderFailedException(String message) {
        super(message);
    }
}
