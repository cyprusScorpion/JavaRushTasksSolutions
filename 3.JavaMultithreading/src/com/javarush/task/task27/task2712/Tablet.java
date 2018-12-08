package com.javarush.task.task27.task2712;

import com.javarush.task.task27.task2712.ad.AdvertisementManager;
import com.javarush.task.task27.task2712.ad.NoVideoAvailableException;
import com.javarush.task.task27.task2712.kitchen.Order;
import com.javarush.task.task27.task2712.kitchen.TestOrder;
import com.javarush.task.task27.task2712.statistic.StatisticManager;
import com.javarush.task.task27.task2712.statistic.event.NoAvailableVideoEventDataRow;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Tablet {
    final int number;
    private static Logger logger = Logger.getLogger(Tablet.class.getName());
    private LinkedBlockingQueue<Order> queue;

    public Tablet(int number) {
        this.number = number;
    }

    public void setQueue(LinkedBlockingQueue<Order> queue) {
        this.queue = queue;
    }

    public Order createOrder() {
        Order order;
        try {
            order = new Order(this);
            if (!order.isEmpty()) {
                ConsoleHelper.writeMessage(order.toString());
                queue.put(order);
                AdvertisementManager manager = new AdvertisementManager(order.getTotalCookingTime() * 60);
                try {
                    //setChanged();
                    //notifyObservers(order);
                    manager.processVideos();
                } catch (NoVideoAvailableException e) {
                    StatisticManager.getInstance().register(new NoAvailableVideoEventDataRow(
                            order.getTotalCookingTime() * 60));
                    logger.log(Level.INFO, "No video is available for the order " + order);
                }
            }
        }
        catch (Exception e) {
            logger.log(Level.SEVERE, "Console is unavailable.");
            return null;
        }
        return order;
    }

    public void createTestOrder() {
        TestOrder testOrder;
        try {
            testOrder = new TestOrder(this);
            if (!testOrder.isEmpty()) {
                ConsoleHelper.writeMessage(testOrder.toString());
                queue.put(testOrder);
                AdvertisementManager manager = new AdvertisementManager(testOrder.getTotalCookingTime() * 60);
                try {
                    //setChanged();
                    //notifyObservers(testOrder);
                    //Thread.sleep(100);
                    manager.processVideos();
                } catch (NoVideoAvailableException e) {
                    StatisticManager.getInstance().register(new NoAvailableVideoEventDataRow(
                            testOrder.getTotalCookingTime() * 60));
                    test(Level.INFO, "No video is available for the order " + testOrder);
                }
            }
        }
        catch (Exception e) {
            logger.log(Level.SEVERE, "Console is unavailable.");
            //return null;
        }
        //return testOrder;
    }

    private void test(Level info, String s) {
        logger.log(info, s);
    }

    @Override
    public String toString() {
        return "Tablet{" +
                "number=" + number +
                '}';
    }


}
