package com.brain.service;

import com.brain.dao.MarketDao;
import com.brain.dao.OtherDao;
import com.brain.dao.UserDao;
import com.brain.domain.Factory;
import com.brain.domain.task.*;
import com.brain.domain.equiptment.Equipment;
import com.brain.domain.User;

import javax.print.attribute.standard.MediaSize;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
存放service方法
 */
public class Service {
    private UserDao userDao = new UserDao();
    private MarketDao marketDao = new MarketDao();


    /** 设备的工厂类 */
    private Factory factory = new Factory();


    private User currentUser = null;




    /** todo 登录界面  */


    /**
     * 功能：登录传到用户名，和密码（检查账号密码是否正确）
     * 负责人：升
     * 参数：String password，String username
     * 返回值：boolean 返回验证是否成功
     * */
    public boolean logIn(String username, String password) {
        // 得到User对象
        User user = userDao.getUser(username);
        // 进行User对象空指针判断
        if(user == null){
            return false;
        }else{
            //存在该用户,直接判断密码就行了
            return password.equals(user.getPassword());
        }
    }


    /**
     * 获取当前用户 当登录成功之后使用
     *
     * @param userName 用户名
     */
    public User getCurrentUser(String userName){
        this.currentUser = userDao.getUser(userName);
        return this.currentUser;
    }


    /** todo 注册界面  */


    /**
     * 判断用户名是否重复了
     */
    public boolean userIsExist(String userName){
        return userDao.getUser(userName) == null ? false : true;
    }

    /**
     * 功能：检查用户名的合理性（六到十八位字符，数字/字母/下划线）
     * 责人：升
     * 参数：String username
     * 返回值：boolean
     * */
    public boolean usernameLegal(String username) {
        //a-zA-Z0-9_  可以简写成\w
        String regex = "^[\\w]{6,16}$";
        // 字母、数字、下划线，长度在6-16之间
        return username.matches(regex);
    }


    /**
     * 功能：检查密码的合理性(8到18位字符，有大小写字母和至少一个特殊字符）
     * 责人：升
     * 参数：String password
     * 返回值：boolean 状态，
     * */
    public boolean checkPassword(String password) {
        String regex = "(?=.*[@_])(?=.*[a-z])(?=.*[A-Z])[a-zA-Z\\d@_]{8,18}";
        Pattern pattern = java.util.regex.Pattern.compile(regex);
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }


    /** 将用户压入UserBox中 */
    public void addUser(String username, String password){
        userDao.modifyUser(2,new User(username,password));
    }



    /** todo 我的界面 */

    /** 暂时部分个人信息 */
    public String showPersonInfo(){
        String Info = currentUser.toString();
        return Info;
    }


    /** 充值判断 */
    public int cardNumberComparison(String card){
        HashMap<String, Integer> cardMap = OtherDao.getCardMap();
        Integer integer = cardMap.get(card);
        if(integer == null){
            return 0;
        }else {
            cardMap.remove(card);
            OtherDao.commitCardMap(cardMap);
            return integer;
        }
    }

    /** 充值 */
    public void addBalance(int money){
        int curMoney = currentUser.getBalance() + money;
        currentUser.setBalance(curMoney);
    }



    /** todo 商城界面 */

    /** 展示商品列表 */
    public void showGoods(){
        ArrayList<String> marketList = marketDao.getMarketList();
        for(String market : marketList){
            System.out.println(market);
        }
    }

    /** 判断当前下标是否越商品列表界 */
    public boolean subscriptJudgment(int index){
        ArrayList<String> marketList = marketDao.getMarketList();
        return index >= 1 && index <= marketList.size();
    }

    /** 获取当前商品的信息 */
    public String[] getGoodInfo(int index){
        ArrayList<String> marketList = marketDao.getMarketList();
        String curMarketInfo = marketList.get(index - 1);
        String[] goodInfo = curMarketInfo.split("\\t");
        return goodInfo;
    }


    /** 判断当前用户余额是否足够支付当前商品 */
    public boolean balanceJudge(int index){
        String priceStr = getGoodInfo(index)[1];
        int price = Integer.parseInt(priceStr);
        return currentUser.getBalance() > price;
    }

    /** 判断当前商品的库存是否完整 */
    public boolean inventoryInspection(int index){
        String inventoryStr = getGoodInfo(index)[2];
        int inventory =  Integer.parseInt(inventoryStr);
        return inventory > 0;
    }


    /** 当前商品购买 */
    public void buy(int index){
        ArrayList<int[]> upperDownList = marketDao.getUpperDownList();
        while(true){
            int[] range = upperDownList.get(index-1);
            int equipmentId = new Random().nextInt(range[1] - range[0] + 1) + range[0];
            //判断当前索引是否在equipmentPool中
            boolean contains = Factory.equipmentIdPool.contains(equipmentId);
            if(!contains){
                Equipment equipment = factory.createObject(equipmentId);
                //equipmentPool已经添加该id,下面将equipmentPool文件进行更新
                OtherDao.commitPoolData(Factory.equipmentIdPool);
                // 将值进行更新
                Factory.equipmentIdPool = OtherDao.getEquipmentIdPoolData();
                //对用户金额进行更改
                currentUser.setBalance(currentUser.getBalance() - Integer.parseInt(getGoodInfo(index)[1]));
                //对用户设备列表进行更新
                currentUser.getUniqueEquipmentList().add(equipment);
                //库存更新
                marketDao.InitMarket();
                break;
            }
        }
    }


    /** todo 设备管理界面 */

    /** 得到设备的地址 */
    public ArrayList<Equipment> getEquipmentList() {
        return currentUser.getUniqueEquipmentList();
    }

    /** 展示设备列表 */
    public String showEquipment() {
        StringBuilder equipList = new StringBuilder();
        for (Equipment equip :getEquipmentList()){
            equipList.append(equip.toString() + "\n");
        }
        return equipList.toString();
    }


    /** 判断设备下标是否越界 */
    public boolean equipmentIndexError(Integer id){
        System.out.println("当前有"+getEquipmentList().size()+"个设备");
        return id < 1  || id > getEquipmentList().size();
    }

    /** 对于输入id进行判断 */
    public boolean checkEquipmentId(Integer id){
        return id > 0;
    }

    /** 当前设备是否有主 */
    public boolean doesTheEquipmentHaveAPrimaryDetection(Integer id){
        return Factory.equipmentIdPool.contains(id);
    }

    /** 增加设备 */
    public void addEquipment(Integer equipmentId){
        /** 创造一个设备 */
        Equipment equipment = factory.createObject(equipmentId);
        //提交
        OtherDao.commitPoolData(Factory.equipmentIdPool);
        // 将值进行更新
        Factory.equipmentIdPool = OtherDao.getEquipmentIdPoolData();
        /** 将设备放入用户的设备列表 */
        currentUser.getUniqueEquipmentList().add(equipment);
    }

    /** 删除设备 */
    public void deleteEquipemnt(Integer index){
        Equipment equipment = currentUser.getUniqueEquipmentList().get(index-1);
        try {
            Integer getEquipmentId = (Integer) equipment.getClass().getMethod("getEquipmentId").invoke(equipment);
            Factory.equipmentIdPool.remove(getEquipmentId);
            //提交
            OtherDao.commitPoolData(Factory.equipmentIdPool);
            // 将值进行更新
            Factory.equipmentIdPool = OtherDao.getEquipmentIdPoolData();

            marketDao.InitMarket();
            /** 删除当前用户的指定设备 */
            currentUser.getUniqueEquipmentList().remove(index-1);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    /** todo 任务界面 */

    /** 定时任务 */


    public HashMap<String, HashMap<String, String>> getMethod(int type){
        HashMap<String, HashMap<String, String>> equipmentMethodMap = OtherDao.getMethod(type);
        return equipmentMethodMap;
    }

    /** 得到真实类名 */
    public String finalClasName(int type,int equipmentId){
        HashMap<String, HashMap<String, String>> method = getMethod(type);
        //得到当前设备
        Equipment equipment = getEquipmentList().get(equipmentId-1);
        //使用反射去拿到类名
        String name = equipment.getClass().getName();
        String finalName = name.substring(name.lastIndexOf(".")+1);
        return finalName;
    }

    /** 展示设备方法 */
    public void showEquipmentMethod(int equipmentId,int type){
        HashMap<String, HashMap<String, String>> method = getMethod(type);
        String finalName = finalClasName(type, equipmentId);
        //得到方法
        Set<String> methodNameSet = method.get(finalName).keySet();
        Object[] methodName = methodNameSet.toArray();
        System.out.println(Arrays.toString(methodName));

    }

    /** 方法是否存在 */
    public boolean methodExists(int equipmentId,int type,String methodName){
        HashMap<String, HashMap<String, String>> method = getMethod(type);
        String finalClasName = finalClasName(type, equipmentId);
        return method.get(finalClasName).containsKey(methodName);
    }

    /**
     * 获得真正方法
     */
    public String getRealMethod(int equipmentId,int type,String methodName){
        HashMap<String, HashMap<String, String>> method = getMethod(type);
        String finalClasName = finalClasName(type, equipmentId);
        String RealMethod = method.get(finalClasName).get(methodName);
        return RealMethod;
    }

    /**
     * 参数存在
     */
    public boolean paramExist(String methodName){
        HashMap<String, HashMap<String, String>> method = OtherDao.getMethod(3);
        return method.get(methodName).containsKey(methodName);
    }

    public String getRealParam(String methodName,String param){
        HashMap<String, HashMap<String, String>> method = OtherDao.getMethod(3);
        String realParam = method.get(methodName).get(param);
        return realParam;
    }


    /** 场景任务 */

    /** 得到场景HashMap */
    public HashMap<String,Scene> getSceneMap(){
        return currentUser.getSceneMap();
    }

    /** 得到指定场景的任务列表*/
    public List<MyTask> getSceneTaskList(String sceneName){
        Scene scene = getSceneMap().get(sceneName);
        List<MyTask> taskList = scene.getTaskList();
        return taskList;
    }
    /** 展示指定场景的任务列表 */
    public void showSceneTaskList(String sceneName){
        List<MyTask> taskList = getSceneTaskList(sceneName);
        taskList.forEach(task ->{
            System.out.println(task.toString());
        });
    }

    /** 下标判断 */
    public boolean indexError(String sceneName,int index){
        List<MyTask> taskList = getSceneTaskList(sceneName);
        return index < 1  || index > taskList.size();
    }

    /** 指定场景任务列表修改 */
    public void updateSceneTaskList(String sceneName,int taskIndex,String taskName,int equipmentIndex,String methodName,Object ...args){
        List<MyTask> sceneTaskList = getSceneTaskList(sceneName);
        //删除原本任务
        sceneTaskList.remove(taskIndex-1);
        MyTask myTask = getNewTask(taskName, equipmentIndex, methodName, args);
        //将新的任务放到原本的场景中
        sceneTaskList.add(taskIndex-1,myTask);
    }

    public MyTask getNewTask(String taskName,int equipmentIndex,String methodName,Object ...args) {
        //获取设备本体
        Equipment equipment = getEquipmentList().get(equipmentIndex - 1);
        HashMap<String, HashMap<String, String>> method = getMethod(2);
//        //使用反射去拿到类名
//        String name = equipment.getClass().getName();
//        name = name.substring(name.lastIndexOf(".")+1);
        //获取真实参数
//        String paramValue = getMethod(3).get(methodName).get(args);
        //创造一个新的任务
        MyTask myTask = new MyTask(taskName, equipment, methodName, args);
        return myTask;
    }

    /**
     负责人：阿源
     功能  ：根据场景的名字查找是否存在
     参数  ：String scenename
     返回值：存在返回scene，否则返回null；
     */
    public Scene sceneTaskExist(String scenename){
        //获取当前用户的Scene哈希表
        HashMap<String,Scene> map = currentUser.getSceneMap();
        //如果存在该场景
        //            返回
        //            返回空
        return map.getOrDefault(scenename, null);
    }

    /**
     负责人：阿源
     功能  ：删除场景任务
     参数  ：String scenename
     返回值：void

     */
    public void deleteSceneTask(String sceneName){
        //  获取场景哈希表
        HashMap<String,Scene> map = currentUser.getSceneMap();
        map.remove(sceneName);
        System.out.println("场景删除成功");
    }

    public boolean getMethodParams(String methodName){
        HashMap<String, HashMap<String, String>> ParamsMap = OtherDao.getMethod(3);
        if(ParamsMap.get(methodName) != null){
            System.out.println("当前参数为");
            ParamsMap.get(methodName).keySet().forEach(key -> {
                System.out.print(key + "\t");
            });
            return true;
        }else{
            System.out.println("没有参数");
            return false;
        }
    }

    public void addScene(String sceneName,List<MyTask> taskList){
        currentUser.getSceneMap().put(sceneName,new Scene(sceneName,taskList));
    }




    /**
     * 显示任务列表
     *
     * @return {@link String}
     */
    public String ShowTaskList(){
        StringBuilder listStr = new StringBuilder();
        currentUser.getTaskArrayList().stream().forEach(element->{
                    String elementInfo = null;
                    try {
                        elementInfo = (String) element.getClass().getMethod("toString").invoke(element);
                        listStr.append(elementInfo+"\n");
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
        });
        return listStr.toString();
    }

    /**
     * 判断任务名称是否存在
     *
     * @param taskName 任务名称
     * @return boolean
     */
    public boolean taskNameExist(String taskName){
        ArrayList<ScheduledTask> taskArrayList = currentUser.getTaskArrayList();
        for (Task task : taskArrayList){
            String taskName1 = null;
            try {
                taskName1 = (String) task.getClass().getMethod("getTaskName").invoke(task);
                if (taskName1.equals(taskName)){
                    return true;
                }
                return false;
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return false;
    }



    /**
     * 时间是否合理
     *
     * @param hour   小时
     * @param minute 一分钟
     * @param second 第二个
     * @return boolean
     */
    public boolean TimeIsReasonable(Integer hour,Integer minute,Integer second){
        if(hour> 24 ||hour<0 || minute >60 || minute <0 || second > 60 || second <0){
            return false;
        }
        return true;
    }



    /**
     * 添加间隔任务
     *
     * @param timeTaskName    时间任务名称
     * @param equipmentIndex     设备数组索引
     * @param equipmentMethod 设备方法
     * @param intervalMillis  间隔,米尔斯
     * @param hourOfDay       小时一天
     * @param minute          一分钟
     * @param second          第二个
     * @param args            arg游戏
     */
    public void addIntervalTask(String timeTaskName, int equipmentIndex,String equipmentMethod,long intervalMillis,int hourOfDay, int minute, int second, Object... args ) {
        Equipment equipment = getEquipmentList().get(equipmentIndex-1);
        /** 创造对象 */

        Date startTime = calendar2Time(hourOfDay, minute, second);
        ScheduledTask task = new ScheduledTask(timeTaskName,equipment, equipmentMethod,startTime,intervalMillis,args);
        task.scheduleTask();
        /** 添加任务至用户中 */
        currentUser.getTaskArrayList().add(task);
    }

    public Date calendar2Time(int hourOfDay,int minute,int second){
        Calendar startTime = Calendar.getInstance();
        startTime.set(Calendar.HOUR_OF_DAY, hourOfDay); // 设置任务开始执行的时间为每天的12点
        startTime.set(Calendar.MINUTE, minute);
        startTime.set(Calendar.SECOND,second);
        startTime.set(Calendar.MILLISECOND, 0);
        return startTime.getTime();
    }



    /**
     * 删除时间任务
     *
     * @param index 指数
     *//*
        负责人：皮皮
        功能  ：
        参数  ：String
        返回值：void

        */
    public void deleteTimeTask(Integer index) {
        try {
            /**将定时任务的线程关闭*/
            Method method = currentUser.getTaskArrayList().get(index-1).getClass().getMethod("cancel");
            method.invoke(currentUser.getTaskArrayList().get(index-1));
            /**删除定时任务*/
            currentUser.getTaskArrayList().remove(index-1);
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    /**
     * 更新间隔任务
     *
     * @param index           指数
     * @param timeTaskName    时间任务名称
     * @param equipmentIndex    设备数组索引
     * @param equipmentMethod 设备方法
     * @param intervalMillis  间隔,米尔斯
     * @param hourOfDay       小时一天
     * @param minute          一分钟
     * @param second          第二个
     * @param args            arg游戏
     *//*
        负责人：皮皮
        功能
        参数  ：String
        返回值：void

        */
    public void updateIntervalTask(Integer index,String timeTaskName, int equipmentIndex,String equipmentMethod,long intervalMillis,int hourOfDay, int minute, int second, Object... args ) {
        try {
            deleteTimeTask(index);

            Equipment equipment = getEquipmentList().get(equipmentIndex-1);
            ScheduledTask task = null;

            /** 创造对象 */
            Date time = calendar2Time(hourOfDay, minute, second);
            task = new ScheduledTask(timeTaskName, equipment, equipmentMethod,time,intervalMillis,args);
            task.scheduleTask();
            /** 添加任务至用户中 */
            currentUser.getTaskArrayList().add(task);
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }


    /**
        负责人：皮皮
        功能  ：查找是否存在
        参数  ：String
        返回值：DurationTask

        */
    public boolean timeTaskExist(String timeTaskName) {
        List<String> taskName = new ArrayList<String>();
        currentUser.getTaskArrayList().forEach(task->{
            Method method = null;
            try {
                method = task.getClass().getMethod("getTaskName");
                taskName.add((String) method.invoke(task));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        return taskName.contains(timeTaskName);
    }



    public void commit(){
        userDao.commit();
    }
}


