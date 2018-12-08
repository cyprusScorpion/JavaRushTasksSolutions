package com.javarush.task.task27.task2712.statistic;

import com.javarush.task.task27.task2712.ConsoleHelper;
import com.javarush.task.task27.task2712.kitchen.Cook;
import com.javarush.task.task27.task2712.statistic.event.CookedOrderEventDataRow;
import com.javarush.task.task27.task2712.statistic.event.EventDataRow;
import com.javarush.task.task27.task2712.statistic.event.EventType;
import com.javarush.task.task27.task2712.statistic.event.VideoSelectedEventDataRow;

import java.text.SimpleDateFormat;
import java.util.*;

public class StatisticManager
{
    private static StatisticManager instance = new StatisticManager();
    private StatisticStorage statisticStorage = new StatisticStorage();
    //private Set<Cook> cooks = new HashSet<>();

    private StatisticManager(){}
    public static StatisticManager getInstance()
    {
        return instance;
    }
    public void register(EventDataRow data){
        if (data != null) statisticStorage.put(data);
    }
//    public void register(Cook cook){
//        cooks.add(cook);
//    }

//    public Set<Cook> getCooks() {
//        return cooks;
//    }

    public TreeMap<String, Long> getAdsRevenueByDate(){
        final SimpleDateFormat format = new SimpleDateFormat("dd-MMM-yyyy");
        TreeMap<String, Long> result = new TreeMap<>(new Comparator<String>()
        {
            @Override
            public int compare(String o1, String o2)
            {
                try
                {
                    Date date1 = format.parse(o1);
                    Date date2 = format.parse(o2);
                    if (date1.equals(date2)) return 0;
                    return date1.after(date2)?-1:1;
                } catch (Exception e){
                    ConsoleHelper.writeMessage("incorrect date was written, check StatisticManager:getAdsRevenueByDate");
                }
                return 0;
            }
        });
        List<VideoSelectedEventDataRow> shownAdsList = (List<VideoSelectedEventDataRow>)(List<?>) statisticStorage.getDataByType(EventType.SELECTED_VIDEOS);
        for (VideoSelectedEventDataRow videoData : shownAdsList){
            long revenue = videoData.getAmount();
            String date = format.format(videoData.getDate());
            if (result.containsKey(date))
                result.put(date, result.get(date) + revenue);
            else
                result.put(date, revenue);
        }
        return result;
    }
    public TreeMap<String, TreeMap<String,Integer>> getDateNameWorkingTime(){
        final SimpleDateFormat format = new SimpleDateFormat("dd-MMM-yyyy");
        TreeMap<String, TreeMap<String,Integer>> result = new TreeMap<>(new Comparator<String>()
        {
            @Override
            public int compare(String o1, String o2)
            {
                try
                {
                    if (o1.equals(o2)) return 0;
                    Date date1 = format.parse(o1);
                    Date date2 = format.parse(o2);
                    return date1.after(date2)?-1:1;
                } catch (Exception e){
                    ConsoleHelper.writeMessage("incorrect date was written, check StatisticManager:getDateNameWorkingTime");
                }
                return 0;
            }
        });
        List<CookedOrderEventDataRow> cookedOrders = (List<CookedOrderEventDataRow>)(List<?>) statisticStorage.getDataByType(EventType.COOKED_ORDER);
        for (CookedOrderEventDataRow cookedOrderData : cookedOrders){
            String date = format.format(cookedOrderData.getDate());
            String cook = cookedOrderData.getCookName();
            Integer time = cookedOrderData.getTime();
            if (result.containsKey(date)){
                TreeMap<String, Integer> workingTimeByCook = result.get(date);
                if (workingTimeByCook.containsKey(cook))
                    workingTimeByCook.put(cook, workingTimeByCook.get(cook)+time);
                else
                    workingTimeByCook.put(cook, time);
            }
            else {
                TreeMap<String, Integer> workingTimeByCook = new TreeMap<>();
                result.put(date, workingTimeByCook);
                workingTimeByCook.put(cook, time);
            }
        }
        return result;
    }
    private class StatisticStorage{
        private Map<EventType, List<EventDataRow>> events = new HashMap<>();
        private StatisticStorage()
        {
            for (EventType type: EventType.values())
                events.put(type,new ArrayList<EventDataRow>());
        }
        private void put(EventDataRow data){
            events.get(data.getType()).add(data);
        }
        private List<EventDataRow> getDataByType(EventType type){
            return events.get(type);
        }
    }
}
