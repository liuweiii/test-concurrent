package org.liuwei.test.common;

/**
 * Created by Administrator on 2017/1/28.
 */
public class Result {
    public static void succeed(String message){
        System.out.println("[OK] "+message);
    }

    public static void failed(String message){
        System.out.println("[FAILED] "+message);
    }
}
