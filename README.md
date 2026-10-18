# Assignment 4 — Facade Pattern: Restaurant Ordering System

Java 11+. Package: `com.restaurant.facade`. Based on Lecture 6.

## Idea
Ordering a dish needs five services in the correct order, and the previous steps must be undone when one fails.
`RestaurantFacade.orderDish(customer, dish, card)` gives the client one simple call.

## Components (Lecture 6)
| Component | Classes |
| Facade | `RestaurantFacade` |
| Complex subsystem | `MenuService`, `InventoryService`, `PaymentService`, `KitchenService`, `NotificationService` (+ `MenuItem`, `ServiceException`) |
| Client | `Main` (uses only the facade) |

```
Main (Client) -> RestaurantFacade -> MenuService
                                  -> InventoryService
                                  -> PaymentService
                                  -> KitchenService
                                  -> NotificationService
```

## Pattern rules
- Subsystem classes do not know about the facade (no back-references).
- Subsystem classes are public: advanced clients can use them directly (escape hatch).
- The client never creates or calls subsystem classes.

## Order of work in `orderDish`
find dish -> reserve ingredients -> charge card -> cook -> notify.
Card fails: ingredients are released. Kitchen busy: money is refunded and ingredients are released.

## Principles (Lecture 6)
1. Keep facades focused (single responsibility, no god object)
2. Provide escape hatches (public subsystem, components passed via constructor)
3. Design for common use cases (one method: order a dish)
4. Document the abstraction (Javadoc on the facade)
5. Principle of Least Knowledge (facade talks only to its own components)
6. Clean tests, Ch. 9: F.I.R.S.T., Build-Operate-Check
Also: meaningful names, no leaking of subsystem details (client sees only `OrderFailedException`), error handling with rollback, no magic numbers.

## Run
```
javac -d out $(find src test -name '*.java')
java -cp out com.restaurant.facade.Main
java -cp out com.restaurant.facade.RestaurantFacadeTest
```
