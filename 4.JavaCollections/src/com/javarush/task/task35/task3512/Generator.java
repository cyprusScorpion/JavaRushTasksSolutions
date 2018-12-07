package com.javarush.task.task35.task3512;

public class Generator<T> {
    Class<T> aClass;

    public Generator(Class<T> aClass) {
        this.aClass = aClass;
    }

    T newInstance() {
        T t = null;
        try {
            t = aClass.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return t;
    }
}
