package com.restaurant.facade;

public class PaymentService {
    private static final int CARD_LENGTH = 16;
    private int lastTransaction = 1000;

    public String charge(String cardNumber, int amount) throws ServiceException {
        if (cardNumber == null || cardNumber.length() != CARD_LENGTH) {
            throw new ServiceException("Invalid card number");
        }
        if (cardNumber.startsWith("0000")) {
            throw new ServiceException("Card declined");
        }
        lastTransaction++;
        System.out.println("[Payment] Charged " + amount + " KZT (TX-" + lastTransaction + ")");
        return "TX-" + lastTransaction;
    }

    public void refund(String transactionId, int amount) {
        System.out.println("[Payment] Refunded " + amount + " KZT (" + transactionId + ")");
    }
}
