package com.integration.rxjavademo.observer_model;

/**
 * @author Wang
 * @version 1.0
 * @date 2021/3/19 - 20:10
 * @Description com.integration.rxjavademo.observer_model
 */
public interface Observable {

    void addObserver(Observer observer);

    void removeObserver(Observer observer);

    void notifyObserver();

    /**
     * 发布消息
     */
    void pushMessage(String msg);


}
