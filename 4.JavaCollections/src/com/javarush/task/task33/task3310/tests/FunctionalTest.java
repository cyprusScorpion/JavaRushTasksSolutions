package com.javarush.task.task33.task3310.tests;

import com.javarush.task.task33.task3310.Shortener;
import com.javarush.task.task33.task3310.strategy.*;
import org.junit.Assert;
import org.junit.Test;

import static com.javarush.task.task33.task3310.Helper.generateRandomString;

public class FunctionalTest {

    public void testStorage(Shortener shortener) {
        String s1 = generateRandomString();
        String s2 = generateRandomString();
        String s3 = s1;
        long idOne = shortener.getId(s1);
        long idTwo = shortener.getId(s2);
        long idThree = shortener.getId(s3);
        Assert.assertNotEquals(idTwo, idOne);
        Assert.assertNotEquals(idTwo, idThree);
        Assert.assertEquals(idOne, idThree);

        String stringGet1 = shortener.getString(idOne);
        String stringGet2 = shortener.getString(idTwo);
        String stringGet3 = shortener.getString(idThree);
        Assert.assertEquals(s1, stringGet1);
        Assert.assertEquals(s2, stringGet2);
        Assert.assertEquals(s3, stringGet3);
    }

    @Test
    public void testHashMapStorageStrategy() {
        HashMapStorageStrategy hashMapStorageStrategy = new HashMapStorageStrategy();
        Shortener shortener = new Shortener(hashMapStorageStrategy);
        testStorage(shortener);
    }

    @Test
    public void testOurHashMapStorageStrategy() {
        OurHashMapStorageStrategy ourHashMapStorageStrategy = new OurHashMapStorageStrategy();
        Shortener shortener = new Shortener(ourHashMapStorageStrategy);
        testStorage(shortener);
    }

    @Test
    public void testFileStorageStrategy() {
        FileStorageStrategy fileStorageStrategy = new FileStorageStrategy();
        Shortener shortener = new Shortener(fileStorageStrategy);
        testStorage(shortener);
    }

    @Test
    public void testHashBiMapStorageStrategy() {
        HashBiMapStorageStrategy hashBiMapStorageStrategy = new HashBiMapStorageStrategy();
        Shortener shortener = new Shortener(hashBiMapStorageStrategy);
        testStorage(shortener);
    }

    @Test
    public void testDualHashBidiMapStorageStrategy() {
        DualHashBidiMapStorageStrategy dualHashBidiMapStorageStrategy = new DualHashBidiMapStorageStrategy();
        Shortener shortener = new Shortener(dualHashBidiMapStorageStrategy);
        testStorage(shortener);
    }

    @Test
    public void testOurHashBiMapStorageStrategy() {
        OurHashMapStorageStrategy ourHashMapStorageStrategy = new OurHashMapStorageStrategy();
        Shortener shortener = new Shortener(ourHashMapStorageStrategy);
        testStorage(shortener);
    }
}
