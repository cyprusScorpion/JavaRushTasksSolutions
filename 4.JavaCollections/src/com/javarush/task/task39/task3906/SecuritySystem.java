package com.javarush.task.task39.task3906;

public class SecuritySystem implements Switchable {

    private static boolean on = false;

    public boolean isOn() {
        return on;
    }

    public static void turnOff() {
        System.out.println("SecuritySystem is turning off!");
        LightBulb.turnOn();
        on = false;
    }

    public static void turnOn() {
        System.out.println("SecuritySystem is turning on!");
        LightBulb.turnOff();
        on = true;
    }
}
