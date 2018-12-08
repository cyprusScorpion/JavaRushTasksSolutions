package com.javarush.task.task27.task2712;

import com.javarush.task.task27.task2712.ad.Advertisement;
import com.javarush.task.task27.task2712.ad.StatisticAdvertisementManager;
import com.javarush.task.task27.task2712.statistic.StatisticManager;

import java.util.Map;
import java.util.TreeMap;

public class DirectorTablet {
    StatisticManager statisticManager = StatisticManager.getInstance();

    public void printAdvertisementProfit(){
        TreeMap<String,Long> adsRevenueByDate = statisticManager.getAdsRevenueByDate();
        Long total = 0L;
        for (Map.Entry<String,Long> entry : adsRevenueByDate.entrySet()){
            String date = entry.getKey();
            Double profitInRubles = entry.getValue()/100.0;
            ConsoleHelper.writeMessage(String.format("%s - %.2f", date, profitInRubles));
            total += entry.getValue();
        }
        ConsoleHelper.writeMessage(String.format("%s - %.2f", "Total", total/100.0));
        ConsoleHelper.writeMessage("");
    }
    public void printCookWorkloading(){
        TreeMap<String, TreeMap<String,Integer>> cookWorkloading = statisticManager.getDateNameWorkingTime();
        for (String date: cookWorkloading.keySet()){
            ConsoleHelper.writeMessage(date);
            Map<String, Integer> workingTimeByCook = cookWorkloading.get(date);
            for (String cook: workingTimeByCook.keySet()){
                int workingTime = workingTimeByCook.get(cook);
                ConsoleHelper.writeMessage(String.format("%s - %s min", cook, workingTime/60));
            }
            ConsoleHelper.writeMessage("");
        }
    }
    public void printActiveVideoSet(){
        for (Advertisement ad : StatisticAdvertisementManager.getInstance().getActiveVideoSet()) {
            ConsoleHelper.writeMessage(ad.getName() + " - " + ad.getHits());
        }
    }

    public void printArchivedVideoSet(){
        for (Advertisement ad : StatisticAdvertisementManager.getInstance().getArchiveVideoSet()) {
            ConsoleHelper.writeMessage(ad.getName());
        }
    }
}
