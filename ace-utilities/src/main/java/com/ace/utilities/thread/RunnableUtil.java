package com.ace.utilities.thread;

import com.ace.utilities.RandomUtil;
import com.ace.utilities.SleepUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


/**
 * @Classname: RunnableUtil
 * @Date: 13/4/2024 4:15 pm
 * @Author: garlam
 * @Description:
 */


public class RunnableUtil implements Runnable{
    private static final Logger log = LogManager.getLogger(RunnableUtil.class.getName());

    //https://blog.csdn.net/qq_40132294/article/details/134173984
    //实现Runable接口
    //Thread是类，而Runnable是接口；
    //Thread本身是实现了Runnable接口的类。
    //我们知道“一个类只能有一个父类，
    //但是却能实现多个接口”，因此Runnable具有更好的扩展性；
    //如果我们定义的类相想要继承其他类（非Thread类）
    //并且还要当前定义的类实现多线程，
    //可以通过Runnable接口来实现。
    //此外，Runnable还可以用于“资源的共享”。
    //即，多个线程都是基于某一个Runnable对象建立的，
    //它们会共享Runnable对象上的资源。
    //实际上，Thread类就是实现了Runnable接口，
    //Thread类中的run()方法也是对Runnable接口中的run()方法的实现


    @Override
    public void run() {
        //重写run方法实现自已业务类
        SleepUtil.sleep(RandomUtil.getRangeInt(1, 6));
        System.out.println("当前运行的线程名为： " + Thread.currentThread().getName());
    }

    public static void main(String[] args) throws Exception {
        RunnableUtil runnable = new RunnableUtil();
        new Thread(runnable, "MyThread1").start();
        new Thread(runnable, "MyThread2").start();
        new Thread(runnable, "MyThread3").start();
        new Thread(runnable, "MyThread4").start();
        new Thread(runnable, "MyThread5").start();
        new Thread(runnable, "MyThread6").start();
        new Thread(runnable, "MyThread7").start();
        new Thread(runnable, "MyThread8").start();
        new Thread(runnable, "MyThread9").start();
    }
}

