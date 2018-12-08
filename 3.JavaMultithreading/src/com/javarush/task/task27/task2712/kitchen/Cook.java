package com.javarush.task.task27.task2712.kitchen;

import com.javarush.task.task27.task2712.ConsoleHelper;
import com.javarush.task.task27.task2712.statistic.StatisticManager;
import com.javarush.task.task27.task2712.statistic.event.CookedOrderEventDataRow;

import java.util.Observable;
import java.util.concurrent.LinkedBlockingQueue;

public class Cook extends Observable implements Runnable {
    private String name;
    private boolean busy;
    private LinkedBlockingQueue<Order> queue;

    public Cook(String name) {
        this.name = name;
    }

    public void setQueue(LinkedBlockingQueue<Order> queue) {
        this.queue = queue;
    }

//    public String getName() {
//        return name;
//    }

    public boolean isBusy() {
        return busy;
    }

    @Override
    public String toString() {
        return name;
    }

    public void startCookingOrder(Order order) {
        busy = true;
        StatisticManager statisticManager = StatisticManager.getInstance();
        CookedOrderEventDataRow cookedOrderEventDataRow = new CookedOrderEventDataRow(
                order.getTablet().toString(),
                name,
                order.getTotalCookingTime() * 60,
                order.getDishes());
        statisticManager.register(cookedOrderEventDataRow);
        //statisticManager.register(this);
        ConsoleHelper.writeMessage("Start cooking - " + order +
                ", cooking time " + order.getTotalCookingTime() + "min");
        try {
            Thread.sleep(order.getTotalCookingTime() * 10);
        } catch (InterruptedException e) {
        }
        setChanged();
        notifyObservers(order);
        busy = false;
    }

    @Override
    public void run() {
        //Set<Cook> cooks = StatisticManager.getInstance().getCooks();
        while (true) {
            try {
                startCookingOrder(queue.take());
            } catch (InterruptedException e) {
            }
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (busy && queue.isEmpty()) break;
        }

//        thread.setDaemon(true);
//        thread.start();
    }

//    public void update(Observable tablet, Object arg) {
//        Order order = (Order) arg;
//        ConsoleHelper.writeMessage(
//                "Start cooking - " + order + ", cooking time " + order.getTotalCookingTime() + "min");
//        StatisticManager.getInstance().register(new CookedOrderEventDataRow(
//                tablet.toString(),
//                name,
//                order.getTotalCookingTime() * 60,
//                order.getDishes()));
//        setChanged();
//        notifyObservers(order);
//    }
}
