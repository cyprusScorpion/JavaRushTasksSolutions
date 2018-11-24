package com.javarush.task.task39.task3906;

public interface Switchable {

    boolean isOn();

    static void turnOff() {}

    static void turnOn() {}
}
