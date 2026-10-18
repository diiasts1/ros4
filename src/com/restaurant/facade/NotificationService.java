package com.restaurant.facade;

public class NotificationService {
    public void send(String customer, String message) {
        System.out.println("[Notify -> " + customer + "] " + message);
    }
}
