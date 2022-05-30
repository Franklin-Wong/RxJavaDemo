package com.integration.rxjavademo.observer_model;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Wang
 * @version 1.0
 * @date 2021/3/19 - 20:12
 * @Description com.integration.rxjavademo.observer_model
 */
public class WechatServerObsevable implements Observable {

    //
    private String message;
    /**
     * 观察者的容器  列表
     */
    private List<Observer> observers = new ArrayList<>();

    @Override
    public void addObserver(Observer observer) {

        observers.add(observer);

    }

    @Override
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObserver() {
        //遍历列表，然后通知所有的观察者
        for (Observer observer :
                observers) {
            //观察者就要更新消息
            observer.update(message);
        }

    }

    @Override
    public void pushMessage(String msg) {
        message = msg;
        //通知所有的观察者
        notifyObserver();
    }


}
