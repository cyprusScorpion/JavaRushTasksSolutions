package com.javarush.task.task27.task2712.ad;

import java.util.ArrayList;
import java.util.List;

public class AdvertisementStorage {

    private final List<Advertisement> videos = new ArrayList<>();

    public void add(Advertisement advertisement) {
        videos.add(advertisement);
    }

    public List<Advertisement> list() {
        return videos;
    }

    private static AdvertisementStorage ourInstance = new AdvertisementStorage();

    public static AdvertisementStorage getInstance() {

        return ourInstance;
    }

    private AdvertisementStorage() {
        Object someContent = new Object();
        add(new Advertisement(someContent, "Второе Русское видео", 2303, 4, 4 * 60));
        add(new Advertisement(someContent, "First", 1523, 3, 3 * 60));
        add(new Advertisement(someContent, "Second", 5, 2, 1 * 60));
        add(new Advertisement(someContent, "3", 99, 2, 3 * 60));
        add(new Advertisement(someContent, "4", 99, 10, 2 * 60));
        add(new Advertisement(someContent, "Fife", 2506, 3, 3 * 60));
        add(new Advertisement(someContent, "6", 2506, 3, 3 * 60));
        add(new Advertisement(someContent, "7", 400, 1, 3 * 60));
        add(new Advertisement(someContent, "8", 500, 1, 2 * 60));
        add(new Advertisement(someContent, "10", 400, 2, 3 * 60));
        add(new Advertisement(someContent, "11", 350, 100, 3 * 60)); // 3 min
        add(new Advertisement(someContent, "12", 1500, 10, 2 * 60)); //15 min
        add(new Advertisement(someContent, "13", 4600, 2, 10 * 60));
        add(new Advertisement(someContent, "Русское видео", 2303, 4, 4 * 60));
    }
}
