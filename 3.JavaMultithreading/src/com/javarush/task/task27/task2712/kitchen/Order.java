package com.javarush.task.task27.task2712.kitchen;

import com.javarush.task.task27.task2712.ConsoleHelper;
import com.javarush.task.task27.task2712.Tablet;

import java.util.List;

public class Order {
    protected List<Dish> dishes;
    private final Tablet tablet;

    public Tablet getTablet() {
        return tablet;
    }

    public Order(Tablet tablet) {
        this.tablet = tablet;
        initDishes();
    }

    protected void initDishes() {
        dishes = ConsoleHelper.getAllDishesForOrder();
    }

    public int getTotalCookingTime() {
        int duration = 0;
        for (Dish dish: dishes
                ) {
            duration+=dish.getDuration();
        }
        return duration;
    }

    @Override
    public String toString() {
        if (dishes.size() < 1) return null;
        StringBuilder sb = new StringBuilder("Your order: [");
        for (Dish element : dishes) {
            sb.append(element.toString()).append(", ");
        }
        sb.delete(sb.lastIndexOf(","), sb.length());
        sb.append("]");
        sb.append(" of ").append(tablet.toString());

        return sb.toString();
    }
    public boolean isEmpty() {
        return (dishes.size() < 1);
    }

    public List<Dish> getDishes() {
        return dishes;
    }
}
