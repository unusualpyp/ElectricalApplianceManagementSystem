package com.brain.domain.task;

import com.brain.domain.equiptment.Equipment;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Date;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * ClassName: ScheduledTask
 * Package: com.brain.domain.task
 * Description:
 *
 * @Author 皮皮dog
 * @Create 2023/4/22 2:28
 * @Version 1.0
 */
public class ScheduledTask implements Task{

    private String taskName;

    private Date startTime;

    private long period;
    private Object targetInstance;
    private String targetMethodName;

    private ScheduledExecutorService executor;

    private boolean state = false; // 新增任务状态成员变量，默认为关闭状态

    private ScheduledFuture<?> scheduledFuture; // 新增ScheduledFuture成员变量，用于取消任务

    private Object[] args;

    public ScheduledTask(String taskName,Object targetInstance, String targetMethodName,Date startTime,long period,Object ...args) {
        this.taskName = taskName;
        this.startTime =startTime;
        this.period = period;
        this.targetInstance = targetInstance;
        this.targetMethodName = targetMethodName;
        this.args = args;
        executor = new ScheduledThreadPoolExecutor(1);
    }

    public void scheduleTask() {
        long delay = this.startTime.getTime() - System.currentTimeMillis();
        scheduledFuture = executor.scheduleAtFixedRate(new MyTask(), delay, this.period, TimeUnit.MILLISECONDS); // 使用scheduleAtFixedRate方法来执行定时任务
        state = true;  // 开启状态标记为true
    }


    public void cancel() {
        if (state) {  // 判断是否处于开启状态
            System.out.println("当前定时任务已关闭");
            scheduledFuture.cancel(false); // 取消任务
            executor.shutdownNow(); // 关闭线程池
            executor = new ScheduledThreadPoolExecutor(1); // 重新初始化executor
            state = false; // 标记状态为关闭
        } else {
            System.out.println("当前定时任务未启动");
        }
    }

    public void reScheduleTask() {
        cancel(); // 先将任务取消
        scheduleTask(); // 重新安排新的执行时间
    }

    public boolean getState() {
        return state; //获取当前任务状态
    }

    private class MyTask implements Runnable {

        public Class<?>[] getParameterTypes() {
            Class<?>[] parameterTypes = new Class<?>[args.length];
            for (int i = 0; i < args.length; i++) {
                parameterTypes[i] = args[i].getClass();
            }
            return parameterTypes;
        }

        @Override
        public void run() {
            try {
                Method method = targetInstance.getClass().getMethod(targetMethodName, getParameterTypes());
                method.invoke(targetInstance,args);
            } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
            state = false; // 任务执行完成后标记状态为关闭
        }
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public long getPeriod() {
        return period;
    }

    public void setPeriod(long period) {
        this.period = period;
    }

    public Object getTargetInstance() {
        return targetInstance;
    }

    public void setTargetInstance(Object targetInstance) {
        this.targetInstance = targetInstance;
    }

    public String getTargetMethodName() {
        return targetMethodName;
    }

    public void setTargetMethodName(String targetMethodName) {
        this.targetMethodName = targetMethodName;
    }


    public void setState(boolean state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "ScheduledTask{" +
                "taskName='" + taskName + '\'' +
                ", startTime=" + startTime +
                ", period=" + period +
                ", targetInstance=" + targetInstance +
                ", targetMethodName='" + targetMethodName + '\'' +
                '}';
    }

    public String toUpFile() {
        try {
            Equipment equipment = (Equipment) this.targetInstance;
            Integer id = (Integer) equipment.getClass().getMethod("getEquipmentId").invoke(equipment);
            String equipmentId = String.valueOf(id);
            return "(" +
                    this.getTaskName() + "," +
                    equipmentId + "," +
                    getTargetMethodName() + "," +
                    getPeriod() + "," +
                    getStartTime().getHours() + "," +
                    getStartTime().getMinutes() + "," +
                    getStartTime().getSeconds() + "," +
                    Arrays.toString(this.args) +
                    ")";
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
