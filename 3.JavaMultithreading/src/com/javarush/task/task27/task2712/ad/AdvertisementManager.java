package com.javarush.task.task27.task2712.ad;

import com.javarush.task.task27.task2712.ConsoleHelper;
import com.javarush.task.task27.task2712.statistic.StatisticManager;
import com.javarush.task.task27.task2712.statistic.event.VideoSelectedEventDataRow;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


public class AdvertisementManager {
    private final AdvertisementStorage storage = AdvertisementStorage.getInstance();
    private int timeSeconds;

    public AdvertisementManager(int timeSeconds) {
        this.timeSeconds = timeSeconds;
    }

    public void processVideos() {
        Collections.sort(storage.list(), new Comparator<Advertisement>() {
            @Override
            public int compare(Advertisement o1, Advertisement o2) {
                int result = Long.compare(o1.getAmountPerOneDisplaying(), o2.getAmountPerOneDisplaying());
                if (result != 0)
                    return -result;

//                long oneSecondCost1 = o1.getAmountPerOneDisplaying() * 1000 / o1.getDuration();
//                long oneSecondCost2 = o2.getAmountPerOneDisplaying() * 1000 / o2.getDuration();
//                    result = Long.compare(oneSecondCost1, oneSecondCost2);
//                    if (result != 0)
//                        return result;
                int duration1 = o1.getDuration();
                int duration2 = o2.getDuration();
                result = Integer.compare(duration1, duration2);
                if (result != 0)
                    return -result;
                int countVideo1 = o1.getHits();
                int countVideo2 = o2.getHits();
                return Integer.compare(countVideo1, countVideo2);
            }
        });

        int timeLeft = timeSeconds;
        ArrayList<Advertisement> optimalVideoSet = new ArrayList<>();
        long amount = 0;
        int totalDuration = 0;
        for (Advertisement advertisement : storage.list()) {
            if (timeLeft < advertisement.getDuration()) {
                continue;
            }
            if (advertisement.getHits() <= 0) {
                continue;
            }
            timeLeft -= advertisement.getDuration();
            advertisement.revalidate();
            optimalVideoSet.add(advertisement);
            amount += advertisement.getAmountPerOneDisplaying();
            totalDuration += advertisement.getDuration();
        }
        StatisticManager.getInstance().register(new VideoSelectedEventDataRow(
                optimalVideoSet,
                amount,
                totalDuration));
        for (Advertisement advertisement : optimalVideoSet) {
            ConsoleHelper.writeMessage(advertisement.getName() + " is displaying... "
                    + advertisement.getAmountPerOneDisplaying() + ", "
                    + advertisement.getAmountPerOneDisplaying() * 1000 / advertisement.getDuration());
        }

        if (timeLeft == timeSeconds) {
            throw new NoVideoAvailableException();
        }
    }
}
