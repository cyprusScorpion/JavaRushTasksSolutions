package com.javarush.task.task39.task3906;

public class LightBulb implements Switchable {

    private static boolean on = false;

    public boolean isOn() {
        return on;
    }

    public static void turnOff() {
        System.out.println("The light is shining!");
        on = false;
    }

    public static void turnOn() {
        System.out.println("The light has been turned off!");
        on = true;
    }
}
