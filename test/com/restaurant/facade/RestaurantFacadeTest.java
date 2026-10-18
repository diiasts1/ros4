package com.restaurant.facade;

/**
 * Clean tests (Clean Code, Ch. 9). F.I.R.S.T.:
 * Fast (in memory), Independent (new facade per test), Repeatable (no network),
 * Self-validating (pass/fail, exit code 1), Timely (written with the facade).
 * Each test: build - operate - check.
 */
public class RestaurantFacadeTest {
    private static final String GOOD_CARD = "4111111111111111";
    private static final String DECLINED_CARD = "0000111122223333";

    private interface Test { void run() throws Exception; }

    public static void main(String[] args) {
        int failed = 0;
        failed += run("orderSucceeds", () -> newFacade().orderDish("alice", "burger", GOOD_CARD));
        failed += run("unknownDishFails", () -> assertFails("sushi", GOOD_CARD, "No such dish"));
        failed += run("declinedCardFails", () -> assertFails("pizza", DECLINED_CARD, "Card declined"));
        failed += run("invalidCardFails", () -> assertFails("pizza", "123", "Invalid card"));
        failed += run("busyKitchenFails", RestaurantFacadeTest::busyKitchenFails);
        failed += run("failedPaymentGivesIngredientsBack", RestaurantFacadeTest::ingredientsAreReleased);
        System.out.println(failed == 0 ? "ALL TESTS PASSED" : failed + " TEST(S) FAILED");
        if (failed > 0) {
            System.exit(1);
        }
    }

    private static void busyKitchenFails() throws Exception {
        RestaurantFacade facade = newFacade();
        facade.orderDish("a", "burger", GOOD_CARD);
        facade.orderDish("b", "burger", GOOD_CARD);
        assertFails(facade, "burger", GOOD_CARD, "Kitchen is too busy");
    }

    private static void ingredientsAreReleased() throws Exception {
        RestaurantFacade facade = newFacade();
        for (int i = 0; i < 5; i++) {                    // stock holds only 5 buns
            assertFails(facade, "burger", DECLINED_CARD, "Card declined");
        }
        facade.orderDish("alice", "burger", GOOD_CARD);  // still possible: nothing leaked
    }

    private static RestaurantFacade newFacade() {
        return new RestaurantFacade();
    }

    private static void assertFails(String dish, String card, String message) throws Exception {
        assertFails(newFacade(), dish, card, message);
    }

    private static void assertFails(RestaurantFacade facade, String dish, String card, String message) throws Exception {
        try {
            facade.orderDish("test", dish, card);
        } catch (OrderFailedException e) {
            if (!e.getMessage().contains(message)) {
                throw new AssertionError("expected '" + message + "' but was '" + e.getMessage() + "'");
            }
            return;
        }
        throw new AssertionError("expected order to fail with: " + message);
    }

    private static int run(String name, Test test) {
        try {
            test.run();
            System.out.println("PASS " + name);
            return 0;
        } catch (Throwable e) {
            System.out.println("FAIL " + name + ": " + e.getMessage());
            return 1;
        }
    }
}
