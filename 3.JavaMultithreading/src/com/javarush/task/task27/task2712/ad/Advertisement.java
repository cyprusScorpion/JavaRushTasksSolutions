package com.javarush.task.task27.task2712.ad;

public class Advertisement {
    private Object content; // видео
    private String name;
    private long initialAmount; //начальная сумма, стоимость рекламы в копейках.
    private int hits; // количество оплаченных показов
    private int duration; //продолжительность в секундах
    Advertisement advertisement;


    public int getHits() {
        return hits;
    }

    private long amountPerOneDisplaying;

    public Advertisement(Object content, String name, long initialAmount, int hits, int duration) {
        this.content = content;
        this.name = name;
        this.initialAmount = initialAmount;
        this.hits = hits;
        this.duration = duration;
        if (hits > 0)
            amountPerOneDisplaying = initialAmount / hits;
    }

    public String getName() {
        return name;
    }

    public int getDuration() {
        return duration;
    }

    public long getAmountPerOneDisplaying() {
        return amountPerOneDisplaying;
    }

    public long getInitialAmount() {
        return initialAmount;
    }

    public long getAmountPerSecond() {
        double a = Double.parseDouble(amountPerOneDisplaying + "");
        double b = Double.parseDouble(duration + "");
        if (b!=0) return (long) (a / b * 1000);
        else return 0;
    }

    public void revalidate(){
        if(hits<=0) throw new UnsupportedOperationException();
        hits--;
    }

//    @Override
//    public String toString() {
////        StringBuilder sb = new StringBuilder();
////        sb.append(name).append(" is displaying... ")
////                .append(amountPerOneDisplaying).append(", ")
////                .append(getAmountPerSecond());
////        //sb.append(name);
//        return  advertisement.getName() + " is displaying... "
//                + advertisement.getAmountPerOneDisplaying() + ", "
//                + advertisement.getAmountPerSecond();
//    }
}

