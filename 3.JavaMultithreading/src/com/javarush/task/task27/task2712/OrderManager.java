package com.javarush.task.task27.task2712;

import com.javarush.task.task27.task2712.kitchen.Cook;
import com.javarush.task.task27.task2712.kitchen.Order;
import com.javarush.task.task27.task2712.statistic.StatisticManager;

import java.util.Observable;
import java.util.Observer;
import java.util.Set;
import java.util.concurrent.LinkedBlockingQueue;

public class OrderManager implements Observer {
    private LinkedBlockingQueue<Order> orderQueue = new LinkedBlockingQueue<>();

    public OrderManager() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                Set<Cook> cooks = StatisticManager.getInstance().getCooks();
                while (true) {
                    if (!orderQueue.isEmpty()) {
                        for (Cook cook : cooks) {
                            if (!cook.isBusy()) {
                                Order order = orderQueue.poll();
                                if (order != null)
                                    cook.startCookingOrder(order);
                            }
                            if (orderQueue.isEmpty()) {
                                try {
                                    Thread.sleep(10);
                                } catch (InterruptedException e) {
                                }
                                break;
                            }
                        }
                    }
                }

//                try {
//                    Set<Cook> cooks = StatisticManager.getInstance().getCooks();
//                    while (true) {
//                        for (Cook cook : cooks) {
//                            if (!cook.isBusy() && !orderQueue.isEmpty()) {
//                                cook.startCookingOrder(orderQueue.poll());
//                            }
//                            if (cook.isBusy() & !orderQueue.isEmpty()) {
//                                continue;
//                            }
//                            if (orderQueue.isEmpty()) {
//                                break;
//                            }
//                        }
//                        Thread.sleep(10);
//                    }
//
//                } catch (Exception e) {
//                    Thread.currentThread().interrupt();
//                }


//                while (true) {
//                    for (Cook cook : cooks) {
//                        if (!cook.isBusy()) {
//                            while (true) {
//                                if (!orderQueue.isEmpty()) {
//                                    cook.startCookingOrder(orderQueue.poll());
//                                }
//                                try {
//                                    Thread.sleep(10);
//                                } catch (InterruptedException e) {
//                                }
//                            }
//                        }
//                    }
//                }
            }
        });
        thread.setDaemon(true);
        thread.start();
    }

    @Override
    public void update(Observable o, Object arg) {
        orderQueue.offer((Order) arg);
        orderQueue.size();
    }
}
