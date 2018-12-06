package com.javarush.task.task33.task3310.tests;

import com.javarush.task.task33.task3310.Helper;
import com.javarush.task.task33.task3310.Shortener;
import com.javarush.task.task33.task3310.strategy.HashBiMapStorageStrategy;
import com.javarush.task.task33.task3310.strategy.HashMapStorageStrategy;
import org.junit.Assert;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class SpeedTest {

    public long getTimeForGettingIds(Shortener shortener, Set<String> strings, Set<Long> ids) {
        long tStart = new Date().getTime();
        for (String s : strings) {
            ids.add(shortener.getId(s));
        }
        long tFinish = new Date().getTime() - tStart;
        return tFinish;
    }

    public long getTimeForGettingStrings(Shortener shortener, Set<Long> ids, Set<String> strings) {
        long tStart = new Date().getTime();
        for (Long id : ids) {
            strings.add(shortener.getString(id));
        }
        long tFinish = new Date().getTime() - tStart;
        return tFinish;
    }

    public void testHashMapStorage() {
        Shortener shortener1 = new Shortener(new HashMapStorageStrategy());
        Shortener shortener2 = new Shortener(new HashBiMapStorageStrategy());
        Set<String> origStrings = new HashSet<>();
        Set<Long> idOne = new HashSet<>();
        Set<Long> idTwo = new HashSet<>();
        for (int i = 0; i < 10_000; i++) {
             origStrings.add(Helper.generateRandomString());
        }
        long t1 = getTimeForGettingIds(shortener1, origStrings, idOne);
        long t2 = getTimeForGettingIds(shortener2, origStrings, idTwo);
        Assert.assertTrue(t1 > t2);

        long t3 = getTimeForGettingStrings(shortener1, idOne, new HashSet<>());
        long t4 = getTimeForGettingStrings(shortener2, idTwo, new HashSet<>());
        Assert.assertEquals(t3, t4, 30);
    }
}
