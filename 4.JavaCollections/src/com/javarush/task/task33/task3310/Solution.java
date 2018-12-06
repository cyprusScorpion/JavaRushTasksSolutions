package com.javarush.task.task33.task3310;

import com.javarush.task.task33.task3310.strategy.HashMapStorageStrategy;
import com.javarush.task.task33.task3310.strategy.StorageStrategy;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class Solution {
    public static void main(String[] args) {
        HashMapStorageStrategy strategy = new HashMapStorageStrategy();
        testStrategy(strategy, 10000);
    }

    public static Set<Long> getIds(Shortener shortener, Set<String> strings) {
        Set<Long> setId = new HashSet<>();
        for (String string : strings) {
            setId.add(shortener.getId(string));
        }
        return setId;
    }

    public static Set<String> getStrings(Shortener shortener, Set<Long> keys) {
        Set<String> setString = new HashSet<>();
        for (Long key : keys) {
            setString.add(shortener.getString(key));
        }
        return setString;
    }

    public static void testStrategy(StorageStrategy strategy, long elementsNumber) {
        Helper.printMessage(strategy.getClass().getSimpleName());
        Set<String> testStringSet = new HashSet<>();
        for (int i = 0; i < elementsNumber; i++) {
             testStringSet.add(Helper.generateRandomString());
        }
        Shortener shortener = new Shortener(strategy);
        Date dateStart = new Date();
        getIds(shortener, testStringSet);
        Date dateFinish = new Date();
        Long timeDelay = dateFinish.getTime() - dateStart.getTime();
        Helper.printMessage(timeDelay.toString());
        Date dateStartTwo = new Date();
        getStrings(shortener, getIds(shortener, testStringSet));
        Date dateFinishTwo = new Date();
        Long timeDelayTwo = dateFinishTwo.getTime() - dateStartTwo.getTime();
        Helper.printMessage(timeDelayTwo.toString());
        if (testStringSet.equals(getStrings(shortener,
                getIds(shortener, testStringSet)))) {
            Helper.printMessage("Тест пройден.");
        } else {
            Helper.printMessage("Тест не пройден.");
        }
    }
}
