package com.brain.dao;

import com.brain.domain.Factory;
import com.brain.domain.task.*;
import com.brain.domain.User;
import com.brain.domain.equiptment.Equipment;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

import static com.sun.xml.internal.bind.WhiteSpaceProcessor.replace;

/*
这类是将dateClass层中的数据进行变换的，去适应service层的方法
 */
public class UserDao {

    private  HashMap<String, User> userBox = new HashMap<>();

    public UserDao(){
        initUser();
    }


    /*
     * 功能：将底层文件中的用户数据读到缓存集合userBox中，方便系统使用用户数据
     * 负责人：lico
     * 参数：无
     * 放回值：无
    * */
    public void initUser(){
        Factory factory = new Factory();

        String fileName = "data/user.txt";
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null && !("").equals(line)) {
                // $符需要转义
                String[] splitValues = line.split("\\$");
                //字符串解析完毕，创建User对象，并放入userBox集合
                User user = new User(splitValues[0], splitValues[1]);
                user.setBalance(Integer.parseInt(splitValues[2]));

                /** 设备列表 */
                if(!splitValues[3].equals("()")){
                    //创建一个新的数组存放设备
                    ArrayList<Equipment> equipmentArrayList = new ArrayList<>();

                    //对于信息进行处理
                    String equipmentList =  splitValues[3].replace("(","").replace(")","");
                    String[] equipmentId = equipmentList.split("、");

                    for (String id : equipmentId){
                        Equipment equipment = factory.createObject(Integer.parseInt(id));
                        equipmentArrayList.add(equipment);
                    }
                    user.setUniqueEquipmentList(equipmentArrayList);
                }

                /** 任务列表 */
                if(!splitValues[4].equals("[]")){

                    ArrayList<ScheduledTask> taskArrayList = new ArrayList<>();
                    //得到任务列表
                    String[] taskList = splitValues[4].split("\\&");

                    for (String task : taskList) {
                        String[] taskArgs = task.replace("(", "").replace(")", "").split(",");
                        if (taskArgs.length == 8) {
                            String taskName = taskArgs[0];
                            Equipment equipment = new Factory().createObject(Integer.parseInt(taskArgs[1]));
                            String methodName = taskArgs[2];
                            long intervalMillis = Long.parseLong(taskArgs[3]);
                            Integer hour = Integer.parseInt(taskArgs[4]);
                            Integer minute = Integer.parseInt(taskArgs[5]);
                            Integer second = Integer.parseInt(taskArgs[6]);
                            //这边定死为数字类型
                            String argValue = null;
                            if(!taskArgs[7].equals("[null]")){
                                argValue = taskArgs[7].replace("[", "").replace("]", "").split(",")[0];
                            }

                            Calendar startTime = Calendar.getInstance();
                            startTime.set(Calendar.HOUR_OF_DAY, hour); // 设置任务开始执行的时间为每天的12点
                            startTime.set(Calendar.MINUTE, minute);
                            startTime.set(Calendar.SECOND,second);
                            startTime.set(Calendar.MILLISECOND, 0);
                            Date time = startTime.getTime();

                            ScheduledTask scheduledTask = new ScheduledTask(taskName,equipment, methodName,time,intervalMillis,argValue);
                            scheduledTask.scheduleTask();
                            taskArrayList.add(scheduledTask);
                        }
                    }
                    user.setTaskArrayList(taskArrayList);
                }


                /** 场景HashMap */
                if(!splitValues[5].equals("||")){
                    HashMap<String,Scene> sceneMap = new HashMap<String,Scene>();

                    String[] scenes = splitValues[5].replace("|", "").split("\\?");
                    for(String scene : scenes){
                        scene = scene.replace("{","").replace("}", "");
                        String[] sceneInfo = scene.split(":");
                        String sceneName = sceneInfo[0];
                        String[] taskListStr = sceneInfo[1].replace("[", "").replace("]", "").split("\\&");
                        List<MyTask> taskList = new ArrayList<>();
                        for(String task : taskListStr){
                            String[] taskArgs = task.replace("(","").replace(")","").split(",");
                            String taskName = taskArgs[0];
                            Equipment equipment = new Factory().createObject(Integer.parseInt(taskArgs[1]));
                            String methodName = taskArgs[2];
                            String argValue = taskArgs[3].replace("+", "").split(",")[0];
                            if(!argValue.equals("null")){
                                MyTask myTask = new MyTask(taskName, equipment, methodName, argValue);
                                taskList.add(myTask);
                            }else{
                                MyTask myTask = new MyTask(taskName, equipment, methodName);
                                taskList.add(myTask);
                            }

                        }
                        Scene scene1 = new Scene(sceneName,taskList);
                        sceneMap.put(sceneName,scene1);
                    }
                    user.setSceneMap(sceneMap);
                }

                userBox.put(user.getUsername(),user);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

//    public Equipment getEquipmentById(User user,int id){
//        ArrayList<Integer> ids = new ArrayList<>();
//        user.getUniqueEquipmentList().stream().forEach(i -> {
//            try {
//                Integer equipmentId = (Integer) i.getClass().getMethod("getEquipmentId").invoke(i);
//                ids.add(equipmentId);
//            }catch (Exception e){
//                throw new RuntimeException(e);
//            }
//        });
//        int count = 0;
//        for (int i : ids){
//            if(i == id ){
//                return user.getUniqueEquipmentList().get(i);
//            }
//            count++;
//        }
//        throw new RuntimeException("当前下标不对");
//    }

    /*
     * 功能：将缓存集合中的数据写入文件
     * 负责人：阿源
     * 参数：无
     * 返回值：无
     * */
    public void commit(){
        /** todo 提交格式为
         * username$password$balance$
         * 太长了 分行显示 但是内容是一行的
         * --------------------------------
         * 设备列表
         * (1、4、6、8、90) 直接记录id就行了
         * -----------------------------------------
         * (taskName,equipmentId,methodName,intervalMillis,hour,minute,second,[...args]),
         * 这里面的arg就是补充，前面是固定的,这是一个intervalTask任务的对象,
         * 对于DurationIntervalTask任务则是
         * (taskName,equipmentId,methodName,intervalMillis,hour,minute,second,durationMillis,[...args]),
         * 所以当读取进来的时候需要进行数组长度判断
         * 任务之间使用&进行分割
         * -----------------------------------------
         * 场景 记录格式为
         * {sceneName:taskList}
         * 其中taskList的格式为
         * [(taskName,equipmentId,methodName,+...args+)&(...)]
         * 场景之间使用 ?进行分割 最后使用||合并记录
         * */
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("data/User.txt"))) {
            for (User user : userBox.values()) {
                //将user对象中的定时任务这些进行关闭
                user.getTaskArrayList().forEach(task->{
                    try {
                        task.getClass().getMethod("cancel").invoke(task);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                });
                // 将user对象转换为字符串
                String fileContent = user.toUpFile();
                bw.write(fileContent);
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    /*
     *功能：从缓存集合userBox中查询系统中的一个用户
     *责人：
     *参数：String username
     *返回值:User对象
     * */
    public User getUser(String username){
        return userBox.get(username);
    }

    /**
     * 功能：往userBox缓存中放入或删除一个user，以达到修改
     * 责人：
     * 参数：Integer type类型, User user user对象
     * 放回值：无
     * */
    public void modifyUser(Integer type,User user){
        /** 删除用户或者添加用户 直接在UserBox 中删除或添加
         * 之后按照格式将 信息重新写入文件中 */
        //1 表示 删除
        if(type == 1){
            userBox.remove(user.getUsername());
        } else if (type == 2) {
            userBox.put(user.getUsername(),user);
        }

    }



}
