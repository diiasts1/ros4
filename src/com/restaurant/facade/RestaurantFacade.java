package com.restaurant.facade;

public class RestaurantFacade {
    private final MenuService menu;
    private final InventoryService inventory;
    private final PaymentService payment;
    private final KitchenService kitchen;
    private final NotificationService notifications;

    public RestaurantFacade() {
        this(new MenuService(), new InventoryService(), new PaymentService(),
                new KitchenService(), new NotificationService());
    }

    public RestaurantFacade(MenuService menu, InventoryService inventory, PaymentService payment,
                            KitchenService kitchen, NotificationService notifications) {
        this.menu = menu;
        this.inventory = inventory;
        this.payment = payment;
        this.kitchen = kitchen;
        this.notifications = notifications;
    }

    public void orderDish(String customer, String dishName, String cardNumber) throws OrderFailedException {
        MenuItem dish = findDish(dishName);
        reserveIngredients(dish);
        String transactionId = chargeCard(dish, cardNumber);
        int minutes = cookDish(dish, transactionId);
        notifications.send(customer, "Your " + dishName + " will be ready in " + minutes + " min");
    }

    private MenuItem findDish(String dishName) throws OrderFailedException {
        try {
            return menu.find(dishName);
        } catch (ServiceException e) {
            throw new OrderFailedException(e.getMessage());
        }
    }

    private void reserveIngredients(MenuItem dish) throws OrderFailedException {
        try {
            inventory.reserve(dish);
        } catch (ServiceException e) {
            throw new OrderFailedException(e.getMessage());
        }
    }

    private String chargeCard(MenuItem dish, String cardNumber) throws OrderFailedException {
        try {
            return payment.charge(cardNumber, dish.getPrice());
        } catch (ServiceException e) {
            inventory.release(dish);
            throw new OrderFailedException(e.getMessage());
        }
    }

    private int cookDish(MenuItem dish, String transactionId) throws OrderFailedException {
        try {
            return kitchen.cook(dish);
        } catch (ServiceException e) {
            payment.refund(transactionId, dish.getPrice());
            inventory.release(dish);
            throw new OrderFailedException(e.getMessage());
        }
    }
}
