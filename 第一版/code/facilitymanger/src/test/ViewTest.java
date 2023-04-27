package test;

import com.brain.controller.MangerController;
import com.brain.domain.Factory;
import com.brain.domain.equiptment.Equipment;
import com.brain.domain.task.ScheduledTask;
import org.junit.Test;

import java.util.*;


/**
 * ClassName: ViewTest
 * Package: test
 * Description:
 *
 * @Author 皮皮dog
 * @Create 2023/4/19 16:19
 * @Version 1.0
 */
public class ViewTest {
    @Test
    public void test(){
        MangerController mangerController = new MangerController();
        mangerController.startview();
    }



    @Test
    public void test3(){
        Calendar startTime = Calendar.getInstance();
        startTime.set(Calendar.HOUR_OF_DAY, 3); // 设置任务开始执行的时间为每天的12点
        startTime.set(Calendar.MINUTE, 23);
        startTime.set(Calendar.SECOND, 20);
        startTime.set(Calendar.MILLISECOND, 0);
        long period = 24 * 60 * 60 * 1000; // 执行周期为1天
        Equipment equipment = new Factory().createObject(1232);
        ScheduledTask task = new ScheduledTask("1",equipment, "setMute",startTime.getTime(),period);

        task.scheduleTask(); // 启动定时任务
        try {
            Thread.sleep(5 *  6000); // 等待5秒后关闭任务
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        task.cancel(); // 关闭定时任务
    }



}

