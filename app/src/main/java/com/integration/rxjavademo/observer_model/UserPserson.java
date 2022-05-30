package com.integration.rxjavademo.observer_model;

/**
 * @author Wang
 * @version 1.0
 * @date 2021/3/19 - 20:28
 * @Description com.integration.rxjavademo.observer_model
 *
 * 这是一个具体的观察者
 */
public class UserPserson implements Observer {
    //
    private String name;
    //
    private String message;

    public UserPserson(String name) {
        this.name = name;
    }

    @Override
    public void update(Object value) {
        //获取被观察者的消息
        this.message = (String) value;
        //读取消息
        readMessage();
    }

    private void readMessage() {

        System.out.println(name + " 收到消息： "+ message);
    }
}
