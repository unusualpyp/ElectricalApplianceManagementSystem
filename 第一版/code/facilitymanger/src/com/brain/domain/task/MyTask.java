package com.brain.domain.task;

import com.brain.domain.User;
import com.brain.domain.equiptment.Equipment;

import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * 任务模版
 * ClassName: MyTask
 * Package: com.brain.pojo
 * Description:
 *
 * @Author 皮皮dog
 * @Create 2023/4/16 11:49
 * @Version 1.0
 * @date 2023/04/16
 */
public  class MyTask  {
    private String taskName;

    private Equipment equipment;
    private String methodName;
    private Object[] args;


    public MyTask(){

    }

    /**
     * 我任务
     *
     * @param equipment  设备
     * @param methodName 方法名称
     * @param args       arg游戏
     * @param taskName   任务名称
     */
    public MyTask(String taskName,Equipment equipment, String methodName, Object... args) {
        this.taskName = taskName;
        this.equipment = equipment;
        this.methodName = methodName;
        this.args = args;
    }

    public Equipment getEquipment() {
        return equipment;
    }
    public void setEquipment(Equipment equipment) {
        this.equipment = equipment;
    }
    public String getMethodName() {
        return methodName;
    }
    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }
    public Object[] getArgs() {
        return args;
    }
    public void setArgs(Object[] args) {
        this.args = args;
    }
    public void execute() {
        try {
            Method method = equipment.getClass().getMethod(methodName, getParameterTypes());
            method.invoke(equipment, args);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public Class<?>[] getParameterTypes() {
        Class<?>[] parameterTypes = new Class<?>[args.length];
        for (int i = 0; i < args.length; i++) {
            parameterTypes[i] = args[i].getClass();
        }
        return parameterTypes;
    }


    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }


    @Override
    public String toString() {
        return "MyTask{" +
                "taskName='" + taskName + '\'' +
                ", equipment=" + equipment + '\'' +
                ", methodName='" + methodName + '\'' +
                ", args=" + Arrays.toString(args) +
                '}';
    }

    public String toUpFile(){
        try{

            Equipment equipment =  this.getEquipment();
            Integer id = (Integer) equipment.getClass().getMethod("getEquipmentId").invoke(equipment);
            String equipmentId = String.valueOf(id);
            return "(" +
                    taskName + "," +
                    equipmentId + "," +
                    getMethodName() + "," +
                    Arrays.toString(getArgs()).replace("[","+").replace(")","+") + "," +
                    ")";
        }catch(Exception e){
            throw new RuntimeException(e);
        }
    }
}
