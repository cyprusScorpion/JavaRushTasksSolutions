package com.javarush.task.task27.task2712;

import com.javarush.task.task27.task2712.kitchen.Dish;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class ConsoleHelper {
    private static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    public static List<Dish> getAllDishesForOrder() {
        List<Dish> list = new ArrayList<>();
        while (true) {
            writeMessage(Dish.allDishesToString());
            writeMessage("Введите название блюда для заказа:");
            String input = readString();
            if (input.equals("exit")) break;
            Dish dish = null;

            for (Dish element: Dish.values()) {
                if (element.toString().equals(input)) {dish = element;}
            }

            if (dish !=null) list.add(dish);
            else {writeMessage("Извините, такого блюда в меню нет.");}
        }

        return list;
    }

    public static void writeMessage(String message) {
        System.out.println(message);
    }

    public static String readString() {

        while (true) {
            try {
                String input = reader.readLine();
                return input;
            } catch (IOException e) {
                writeMessage("Ошибка ввода данных");
                e.printStackTrace();
            }
        }
    }
}
