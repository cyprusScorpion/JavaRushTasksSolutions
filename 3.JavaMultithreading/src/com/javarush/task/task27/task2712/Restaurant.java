package com.javarush.task.task27.task2712;

import com.javarush.task.task27.task2712.kitchen.Cook;
import com.javarush.task.task27.task2712.kitchen.Order;
import com.javarush.task.task27.task2712.kitchen.Waiter;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

public class Restaurant {
    private final static int ORDER_CREATING_INTERVAL = 100;
    private final static LinkedBlockingQueue<Order> orderQueue = new LinkedBlockingQueue<>();

    public static void main(String[] args) {
            Waiter firstWaiter = new Waiter();
            //OrderManager orderManager = new OrderManager();
            List<Tablet> tablets = new ArrayList<>();
            for (int i = 1; i <= 5; i++) {
                Tablet tablet = new Tablet(i);
                tablet.setQueue(orderQueue);
                tablets.add(tablet);
            }
            Cook amigo = new Cook("Amigo");
            amigo.setQueue(orderQueue);
            Cook balaabo = new Cook("Balaabo");
            balaabo.setQueue(orderQueue);
//            StatisticManager.getInstance().register(amigo);
//            StatisticManager.getInstance().register(balaabo);

            for (Tablet tablet : tablets) {
                //tablet.addObserver(orderManager);
                //tablet.addObserver(orderManager);
                amigo.addObserver(firstWaiter);
                balaabo.addObserver(firstWaiter);
            }

            Thread createOrderThread = new Thread(
                    new RandomOrderGeneratorTask(tablets, ORDER_CREATING_INTERVAL));
            Thread thread = new Thread(createOrderThread);
            Thread threadAmigo = new Thread(amigo);
            Thread threadBalaabo = new Thread(balaabo);
            thread.start();
            //createOrderThread.start();

            threadAmigo.start();
            threadBalaabo.start();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
            }
            threadAmigo.interrupt();
            threadBalaabo.interrupt();
            thread.interrupt();
            createOrderThread.interrupt();

            DirectorTablet directorTablet = new DirectorTablet();
            directorTablet.printAdvertisementProfit();
            directorTablet.printCookWorkloading();
            directorTablet.printActiveVideoSet();
            directorTablet.printArchivedVideoSet();
    }
}
