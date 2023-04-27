package com.brain.domain;

import com.brain.domain.task.MyTask;
import com.brain.domain.task.Scene;
import com.brain.domain.equiptment.Equipment;
import com.brain.domain.task.ScheduledTask;

import java.util.*;

public class User {
    private String username;

    private String password;
    private int balance = 0;

    private ArrayList<ScheduledTask> taskArrayList = new ArrayList<>();

    private HashMap<String,Scene> sceneMap = new HashMap<>();

    private ArrayList<Equipment> uniqueEquipmentList = new ArrayList<>();



    public ArrayList<ScheduledTask> getTaskArrayList() {
        return taskArrayList;
    }

    public ArrayList<Equipment> getUniqueEquipmentList() {
        return uniqueEquipmentList;
    }

    public HashMap<String, Scene> getSceneMap() {
        return sceneMap;
    }

    public void setUniqueEquipmentList(ArrayList<Equipment> uniqueEquipmentList) {
        this.uniqueEquipmentList = uniqueEquipmentList;
    }

    public void setTaskArrayList(ArrayList<ScheduledTask> taskArrayList) {
        this.taskArrayList = taskArrayList;
    }


    public void setSceneMap(HashMap<String, Scene> sceneMap) {
        this.sceneMap = sceneMap;
    }






    public User() {
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;

    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int  getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {return true;}
        if (o == null || getClass() != o.getClass()) {return false;}
        User user = (User) o;
        return Objects.equals(username, user.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, password, balance);
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", balance=" + balance +
                ", uniqueEquipmentList=" + Arrays.toString(uniqueEquipmentList.toArray()) +
                '}';
    }

    /**
     * 上传文件的字符串
     *
     * @return {@link String}
     */
    public String toUpFile(){
        StringBuilder equipmentInfo = new StringBuilder();
        equipmentInfo.append("(");
        StringBuilder taskInfo = new StringBuilder();
        taskInfo.append("[");
        StringBuilder sceneInfo = new StringBuilder();
        sceneInfo.append("|");

        /** 获取设备信息 */
        getUniqueEquipmentList().stream().forEach(equipment->{
            try {
                Integer EquipmentId = (Integer) equipment.getClass().getMethod("getEquipmentId").invoke(equipment);
                equipmentInfo.append(String.valueOf(EquipmentId)+"、");
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        equipmentInfo.append(")");

        /** 获取任务信息 */
        getTaskArrayList().stream().forEach(task->{
            try{
                task.getClass().getMethod("cancel").invoke(task);
                String UpFileInfo = (String) task.getClass().getMethod("toUpFile").invoke(task);

                taskInfo.append(UpFileInfo+"&");
            }catch (Exception e){
                throw new RuntimeException(e);
            }
        });
        taskInfo.append("]");

        /** 获取场景信息 */
        getSceneMap().forEach((sceneName,scene)->{
            sceneInfo.append(scene.toUpFile() + "?");
        });
        sceneInfo.append("|");


        return username + "$" +
                password + "$" +
                balance + "$" +
                equipmentInfo.toString() + "$" +
                taskInfo.toString() + "$" +
                sceneInfo.toString() + "\n";
    }
}