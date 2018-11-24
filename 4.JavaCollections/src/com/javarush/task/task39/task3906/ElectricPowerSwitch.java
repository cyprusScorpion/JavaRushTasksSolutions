package com.javarush.task.task39.task3906;

import static com.javarush.task.task39.task3906.SecuritySystem.turnOff;
import static com.javarush.task.task39.task3906.SecuritySystem.turnOn;

public class ElectricPowerSwitch {
    private static Switchable switchable = new SecuritySystem();

    public static void press() {
        System.out.println("Pressed the power switch.");
        if (switchable.isOn()) {
            turnOff();

        } else {
            turnOn();
        }
    }
}
