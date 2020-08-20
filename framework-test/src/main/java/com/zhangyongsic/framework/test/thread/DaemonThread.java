package com.zhangyongsic.framework.test.thread;

import java.util.concurrent.TimeUnit;

/**
 * @program: framework
 * @description:
 * @author: zhang yong
 * @create: 2020/07/31
 */
public class DaemonThread {
    public static void main(String[] args) throws  InterruptedException{
        int i = 0;
        System.out.println(i);

        Thread thread = new Thread(()->{
            while (true){
                try {
                    Thread.sleep(1);
                    System.out.println("ni hao");
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
        });
        thread.setDaemon(true);
        thread.start();
        System.out.println(System.currentTimeMillis());
        Thread.sleep(2000L);
        System.out.println(System.currentTimeMillis());
    }
}
