package com.integration.rxjavademo.serializable;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

/**
 * @author Wang
 * @version 1.0
 * @date 2021/6/18 - 14:11
 * @Description com.integration.rxjavademo.serializable
 */
public class SerializableUtils<T> {

    synchronized public static <T> T writeObject(String path) {

        return null;
    }

    synchronized public static boolean saveObject(Object obj, String path) {
        if (obj == null) {
            return false;
        }

        ObjectOutputStream oos = null;
        try {// 创建序列化流对象
            oos = new ObjectOutputStream(new FileOutputStream(path)); //序列化
            oos.writeObject(obj);
            oos.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (oos != null) {
                try {// 释放资源
                    oos.close();
                } catch (IOException e) {
                    e.printStackTrace();

                }

            }
        }
        return false;
    }
}
