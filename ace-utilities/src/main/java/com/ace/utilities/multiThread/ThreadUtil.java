package com.ace.utilities.multiThread;

import com.ace.utilities.RandomUtil;
import com.ace.utilities.SleepUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


/**
 * @Classname: ThreadUtil
 * @Date: 13/4/2024 4:10 pm
 * @Author: garlam
 * @Description:
 */


public class ThreadUtil extends Thread {
    private static final Logger log = LogManager.getLogger(ThreadUtil.class.getName());

    //https://blog.csdn.net/qq_40132294/article/details/134173984
    //继承java.lang.Thread类，
    //从这个类中实例化的对象代表线程，
    //启动一个新线程需要建立一个Thread实例

    public ThreadUtil(String name) {
        // 设置当前线程的名字
        this.setName(name);
    }

    @Override
    public void run() {
        //重写run方法实现自已业务类
        SleepUtil.sleep(RandomUtil.getRangeInt(1, 6));
        System.out.println("当前运行的线程名为： " + Thread.currentThread().getName());
    }

    public static void main(String[] args) throws Exception {
        new ThreadUtil("MyThread1: ").start();
        new ThreadUtil("MyThread2: ").start();
        new ThreadUtil("MyThread3: ").start();
        new ThreadUtil("MyThread4: ").start();
        new ThreadUtil("MyThread5: ").start();
        new ThreadUtil("MyThread6: ").start();
        new ThreadUtil("MyThread7: ").start();
        new ThreadUtil("MyThread8: ").start();
        new ThreadUtil("MyThread9: ").start();
    }

}

