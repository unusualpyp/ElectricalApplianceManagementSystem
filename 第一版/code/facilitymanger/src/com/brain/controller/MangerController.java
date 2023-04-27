package com.brain.controller;


import com.brain.domain.User;
import com.brain.domain.task.MyTask;
import com.brain.domain.task.Scene;
import com.brain.service.Service;

import javax.xml.transform.Source;
import java.util.*;

public class MangerController {
    private Service mangerService = new Service();
    private Scanner sc = new Scanner(System.in);

    private User curUser = null;


    /** todo 初始界面 */

    public void startview() {
        while (true) {
            System.out.println("--------欢迎来到智能家电管理系统--------");
            System.out.println("请输入你的选择：1.登录 2.注册 3.退出");

            String choice = sc.next();
            switch (choice) {

                case "1":
                    logIn();
                    break;

                case "2":
                    signIn();
                    break;

                case "3":
                    System.out.println("感谢您的使用");
                    try{
                        System.out.println("数据上传中");
                        mangerService.commit();
                    }catch(Exception e){
                        e.printStackTrace();
                    }finally {
                        //退出当前运行的虚拟机
                        System.exit(0);
                        break;
                    }
                default:
                    System.out.println("您的输入有误，请重新输入");
            }

        }
    }


    /** todo 登录界面 */
    /**
     * 负责人：升
     * 功能  ：登录界面
     * 参数  ：void
     * 返回值：void
     */
    public void logIn() {
        w:
        while (true) {
            try {
                System.out.println("欢迎来到登录界面--------------");
                System.out.println("是否退出当前界面 -1退出当前界面 其他表示继续界面");
                String exit = sc.next();
                if ("-1".equals(exit)) {
                    break;
                }
                System.out.println();
                System.out.println("请输入要登录的账号用户名");
                String name = sc.next();
                System.out.println("请输入要登录的账号密码");
                String pass = sc.next();
                boolean checkSuccess = mangerService.logIn(name, pass);
                if (checkSuccess) {
                    // 绑定当前用户
                    curUser = mangerService.getCurrentUser(name);
                    menueView();
                    break w;
                } else {
                    System.out.println("当前用户账号或密码不对");
                }
            } catch (Exception e) {
                System.out.println("当前输入格式有问题");
            }
        }
    }


    /** todo 注册界面 */
    /**
     * 负责人：升
     * 功能  ：注册界面
     * <p>
     * 参数  ：void
     * 返回值：void
     */
    public void signIn() {
        e:
        while (true) {
            try {
                System.out.println("是否退出当前界面 -1退出当前界面 其他表示继续界面");
                String exit = sc.next();
                if ("-1".equals(exit)) {
                    break;
                }
                System.out.println("请输入要注册的账号用户名");
                System.out.println("格式为 6-16位 选择范围在数字、字母、下划线中选择");
                String name = sc.next();
                if (!mangerService.usernameLegal(name)) {
                    System.out.println("当前用户的账号格式不对");
                    continue;
                }
                System.out.println("请输入要注册的账号密码");
                System.out.println("密码的格式为 8到18位字符，有大小写字母和至少一个特殊字符(@_)");
                String pass = sc.next();
                if (!mangerService.checkPassword(pass)) {
                    System.out.println("当前密码的账号格式不对");
                    continue;
                }
                System.out.println("请重新输入注册的密码");
                String pass2 = sc.next();
                if (!pass2.equals(pass)) {
                    System.out.println("确认密码的密码不对");
                    continue;
                }
                //注册成功，将用户信息放入UserBox中
                mangerService.addUser(name, pass);
                //去登录界面去登录主页
                break e;
            } catch (Exception e) {
                System.out.println("当前输入格式有问题");
            }
        }
        logIn();
    }


    /** todo 主页界面 */

    public void menueView() {
        mv:
        while (true) {
            System.out.println("--------欢迎进入主页--------");
            System.out.println("请输入你的选择：1.设备管理 2.任务管理 3.商城 4.我的 5.退出");

            String choice = sc.next();
            switch (choice) {

                case "1":
                    equipmentsSetUp();
                    break;

                case "2":
                    assigmentSetUp();
                    break;

                case "3":
                    marketView();
                    break;
                case "4":
                    myPage();
                    break;
                case "5":
                    break mv;
                default:
                    System.out.println("您的输入有误，请重新输入");
            }

        }
    }

    /** todo 我的界面 */

    public void myPage() {
        mp:
        while (true) {
            System.out.println("--------欢迎进入我的界面--------");
            System.out.println("请输入你的选择：1.个人信息 2.充值 3.退出");

            String choice = sc.next();
            switch (choice) {

                case "1":
                    showPersonInfo();
                    break;
                case "2":
                    rechargeInterface();
                    break;
                case "3":
                    break mp;
                default:
                    System.out.println("您的输入有误，请重新输入");
            }
        }
    }


    /**
     * 负责人：升
     * 功能  ：调用service中的showPersonInfo（），打印展现该用户名
     * 参数  ：void
     * 返回值：void
     */
    public void showPersonInfo() {
        String userInfo = mangerService.showPersonInfo();
        System.out.println(userInfo);
    }

    /**
     * 充值界面
     */
    public void rechargeInterface() {
        while (true) {
            try {
                System.out.println("欢迎使用充值界面");
                System.out.println("是否退出当前界面 -1退出当前界面 0表示继续界面");
                String exit = sc.next();
                if ("-1".equals(exit)) {
                    break;
                }
                System.out.println("请输入点卡号");
                String card = sc.next();
                int money = mangerService.cardNumberComparison(card);
                if (money == 0) {
                    System.out.println("当前卡号不对");
                } else {
                    mangerService.addBalance(money);
                    break;
                }
            } catch (Exception e) {
                System.out.println("当前卡号输入格式有问题");
            }
        }
    }


    /** todo 商城界面 */

    public void marketView() {
        mk:
        while (true) {
            System.out.println("--------欢迎进入商城界面--------");
            System.out.println("当前商品列表为:");
            System.out.println("商品"+"\t"+"价格"+"\t"+"库存");
            mangerService.showGoods();
            System.out.println("请输入你的选择：1、选择当前列表下标购买(以1为底) 2.退出");

            String choice = sc.next();
            switch (choice) {
                case "1":
                    purchaseInterface();
                    break;
                case "2":
                    break mk;
                default:
                    System.out.println("您的输入有误，请重新输入");
            }

        }
    }

    public void purchaseInterface() {
        while (true) {
            try {
                System.out.println("欢迎使用购买界面");
                System.out.println("当前列表列表为");
                mangerService.showGoods();
                System.out.println("是否退出当前界面 -1退出当前界面 其他表示继续界面");
                String exit = sc.next();
                if ("-1".equals(exit)) {
                    break;
                }
                System.out.println("请输入下标");
                int index = sc.nextInt();
                boolean indexEffective = mangerService.subscriptJudgment(index);
                if (!indexEffective) {
                    System.out.println("当前下标越界了");
                    continue;
                }
                boolean balanceJudge = mangerService.balanceJudge(index);
                if (!balanceJudge) {
                    System.out.println("当前账户余额不足");
                }
                boolean inventory = mangerService.inventoryInspection(index);
                if (!inventory) {
                    System.out.println("当前商品库存不足");
                }
                mangerService.buy(index);
                System.out.println("恭喜你购买成功");
                break;
            } catch (Exception e) {
                System.out.println("输入格式有问题");
            }
        }
    }

    /** todo 设备管理界面 */
    public void equipmentsSetUp() {
        esu:
        while (true) {
            System.out.println("--------欢迎进入设备管理界面--------");
            System.out.println("当前您的设备有");
            System.out.println(mangerService.showEquipment());
            System.out.println("请输入你的选择：1.设备增加 2.设备删除 3.退出");

            String choice = sc.next();
            switch (choice) {
                case "1":
                    addEquipment();
                    break;
                case "2":
                    deleteEquipment();
                    break;
                case "3":
                    break esu;
                default:
                    System.out.println("您的输入有误，请重新输入");
            }
        }
    }


    public void addEquipment() {

        System.out.println("-----欢迎来到增加设备界面-----");
        while (true) {
            try {
                System.out.println("是否退出 -1退出 其他表示继续界面");
                String exit = sc.next();
                if ("-1".equals(exit)) {
                    break;
                }
                System.out.println("请输入需要添加的设备id");
                Integer equipmentId = sc.nextInt();
                boolean checkEquipmentId = mangerService.checkEquipmentId(equipmentId);
                if (!checkEquipmentId) {
                    System.out.println("输入的设备ID需要大于0");
                    continue;
                }
                boolean haveMaster = mangerService.doesTheEquipmentHaveAPrimaryDetection(equipmentId);
                if (haveMaster) {
                    System.out.println("当前设备已经绑定");
                    continue;
                }
                mangerService.addEquipment(equipmentId);
                break;
            } catch (Exception e) {
                System.out.println("当前输入数据格式不对!");
            }
        }
    }

    public void deleteEquipment() {
        System.out.println("欢迎来到删除设备界面");
        System.out.println("当前设备有:");
        System.out.println(mangerService.showEquipment());
        System.out.println("----------------------------");
        System.out.println("可以使用 -1退出界面 其他表示继续界面");
        while (true) {
            try {
                System.out.println("是否退出 -1退出");
                String exit = sc.next();
                if ("-1".equals(exit)) {
                    break;
                }
                System.out.println("请输入设备数组的设备下标(以1起始)");
                Integer index = sc.nextInt();
                boolean equipmentIndexError = mangerService.equipmentIndexError(index);
                if (equipmentIndexError) {
                    System.out.println("当前下标越界");
                    continue;
                }
                mangerService.deleteEquipemnt(index);
                break;
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("输入数据类型不对");
            }
        }
    }


    /** todo 任务界面 */
    public void assigmentSetUp() {
        asu:
        while (true) {
            System.out.println("--------欢迎进入任务管理界面--------");
            System.out.println("请输入你的选择：1.定时任务 2.场景任务 3.退出");

            String choice = sc.next();
            switch (choice) {
                case "1":
                    timeTask();
                    break;
                case "2":
                    sceneTask();
                    break;
                case "3":
                    break asu;
                default:
                    System.out.println("您的输入有误，请重新输入");
            }
        }
    }

    /**
     * 负责人：皮皮
     * 功能  ：输入要设置的时间，传导给service完成定时任务
     * 参数  ：void
     * 返回值：void
     */
    public void timeTask() {

        tt:
        while (true) {
            System.out.println("--------欢迎进入时间任务管理界面--------");
            System.out.println("您当前的时间任务有:");
            System.out.println(mangerService.ShowTaskList());
            System.out.println("请输入你的选择：1.增加任务 2.任务修改 3.任务删除 4.退出");

            String choice = sc.next();
            switch (choice) {
                case "1":
                    addTimeTask();
                    break;
                case "2":
                    updateTimeTask();
                    break;
                case "3":
                    deleteTimeTask();
                case "4":
                    break tt;
                default:
                    System.out.println("您的输入有误，请重新输入");
            }
        }
    }

    /**
     * 负责人：皮皮
     * 功能  ：时间任务相关功能
     * 参数  ：void
     * 返回值：void
     */
    public void addTimeTask() {
        System.out.println("欢迎来到增加任务界面");
        System.out.println("----------------------------");
        System.out.println("可以使用 -1退出界面 ");
        while (true) {
            try {
                System.out.println("是否退出 -1退出");
                String exit = sc.next();
                if ("-1".equals(exit)) {
                    break;
                }
                System.out.print("请输入任务名:");
                String taskName = sc.next();
                boolean nameExist = mangerService.taskNameExist(taskName);
                if (nameExist) {
                    System.out.println("任务名重复，请重新输入");
                    continue;
                }
                System.out.println("当前设备有:");
                String equipmentListShow = mangerService.showEquipment();
                System.out.println(equipmentListShow);
                System.out.println("请选择设备下标（以1为底）");
                int equipId = sc.nextInt();
                if (mangerService.equipmentIndexError(equipId)) {
                    System.out.println("设备下标出现了问题");
                    continue;
                }
                System.out.println("当前设备的方法有");
                mangerService.showEquipmentMethod(equipId, 2);
                System.out.println("请输入选择的方法:");
                String methodName = sc.next();
                if(!mangerService.methodExists(equipId,2,methodName)){
                    System.out.println("输入的方法不对");
                    continue;
                }else{
                    methodName = mangerService.getRealMethod(equipId,2,methodName);
                }
                System.out.println("当前方法的参数为:");
                boolean paramsExist = mangerService.getMethodParams(methodName);
                String param = null;
                if (paramsExist) {
                    System.out.println("请选择参数");
                    param = sc.next();
                    if(!mangerService.paramExist(param)){
                        System.out.println("当前参数不存在");
                        continue;
                    }else{
                        param = mangerService.getRealParam(methodName,param);
                    }
                }

                System.out.println("请指定开始时间");
                System.out.print("小时(24小时制):");
                Integer hour = sc.nextInt();
                System.out.print("分钟");
                Integer minute = sc.nextInt();
                System.out.print("秒");
                Integer second = sc.nextInt();

                //判断时间是否合理
                boolean timeIsReasonable = mangerService.TimeIsReasonable(hour, minute, second);
                if (!timeIsReasonable) {
                    System.out.println("您设置的时间存在问题");
                    continue;
                }
                System.out.println("设置间隔时长");
                long intervalMillis = sc.nextLong();

                mangerService.addIntervalTask(taskName, equipId, methodName, intervalMillis, hour, minute, second,param);
                System.out.println("创建成功!");
                break;
            } catch (Exception e) {
                System.out.println("输入的值不合法");
            }
        }
    }


    /**
     * 负责人：皮皮
     * 功能  ：删除任务界面
     * 参数  ：void
     * 返回值：void
     */
    public void deleteTimeTask() {
        System.out.println("欢迎来到删除任务界面");
        System.out.println("----------------------------");
        System.out.println("当前任务有:");
        System.out.println(mangerService.ShowTaskList());
        System.out.println("可以使用 -1退出界面 ");
        System.out.println("------------------------------");
        System.out.println("请选择要修改任务的下标 以1为底");
        Integer index = sc.nextInt();
        mangerService.deleteTimeTask(index);
        System.out.println("删除成功");
    }

    /**
     * 负责人：皮皮
     * 功能  ：时间任务相关功能
     * 参数  ：void
     * 返回值：void
     */
    public void updateTimeTask() {
        System.out.println("欢迎来到修改任务界面");
        System.out.println("----------------------------");
        System.out.println("当前任务有:");
        System.out.println(mangerService.ShowTaskList());
        System.out.println("可以使用 -1退出界面 ");
        while (true) {
            try {
                System.out.println("是否退出 -1退出");
                String exit = sc.next();
                if ("-1".equals(exit)) {
                    break;
                }
                System.out.println("请选择要修改任务的下标 以1为底");
                Integer index = sc.nextInt();

                System.out.print("请输入任务名:");
                String taskName = sc.next();
                boolean nameExist = mangerService.taskNameExist(taskName);
                if (nameExist) {
                    System.out.println("任务名重复，请重新输入");
                    continue;
                }
                System.out.println("请指定开始时间");
                System.out.print("小时(24小时制):");
                Integer hour = sc.nextInt();
                System.out.print("分钟");
                Integer minute = sc.nextInt();
                System.out.println("秒");
                Integer second = sc.nextInt();

                //判断时间是否合理
                boolean timeIsReasonable = mangerService.TimeIsReasonable(hour, minute, second);
                if (!timeIsReasonable) {
                    System.out.println("您设置的时间存在问题");
                    continue;
                }

                System.out.println("请设置间隔时间(格式为 秒数)");
                long intervalMillis = sc.nextLong();

                System.out.println("当前设备有");
                System.out.println(mangerService.showEquipment());
                System.out.println("请选择设备下标（以1为底）");
                int equipId = sc.nextInt();
                if (mangerService.equipmentIndexError(equipId)) {
                    System.out.println("设备下标出现了问题");
                    continue;
                }
                System.out.println("当前设备的方法有");
                mangerService.showEquipmentMethod(equipId, 2);
                System.out.println("请输入选择的方法:");
                String methodName = sc.next();
                if(!mangerService.methodExists(equipId,2,methodName)){
                    System.out.println("输入的方法不对");
                    continue;
                }else{
                    methodName = mangerService.getRealMethod(equipId,2,methodName);
                }
                System.out.println("当前方法的参数为:");
                boolean paramsExist = mangerService.getMethodParams(methodName);
                String param = null;
                if (paramsExist) {
                    System.out.println("请选择参数");
                    param = sc.next();
                    if(!mangerService.paramExist(param)){
                        System.out.println("当前参数不存在");
                        continue;
                    }else{
                        param = mangerService.getRealParam(methodName,param);
                    }
                }



                mangerService.updateIntervalTask(index, taskName, equipId, methodName, intervalMillis, hour, minute, second,param);


                System.out.println("修改成功!");
            } catch (Exception e) {
                System.out.println("输入的值不合法");
            }
        }

    }


    /**
     * 负责人：阿源
     * 功能  ：输入场景名字，然后传导给service层中的sceneTask（），在dao中实现 创建hashmap放入文件
     * 参数  ：void
     * 返回值：void
     */
    public void sceneTask() {

        st:
        while (true) {
            System.out.println("--------欢迎进入场景任务管理界面--------");
            showSceneTask();
            System.out.println("请输入你的选择：1.增加场景 2.场景修改 3.场景删除 4.退出");

            String choice = sc.next();
            switch (choice) {
                case "1":
                    addSceneTask();
                    break;
                case "2":
                    updateSceneTask();
                    break;
                case "3":
                    deleteSceneTask();
                case "4":
                    break st;
                default:
                    System.out.println("您的输入有误，请重新输入");
            }

        }
    }

    /**
     * 负责人：阿源
     * 功能  ：展示目前已经存在的全部SceneTask
     * 参数  ：void
     * 返回值：void
     */
    public void showSceneTask() {
//        获取当前用户所有场景
        HashMap<String, Scene> map = mangerService.getSceneMap();
        System.out.println("已有场景及其设备");
//        遍历所有场景
        for (String key : map.keySet()) {
//            输出场景名
            System.out.print(key + ": ");
//            输出设备名
            Scene scene = map.get(key);
            List<MyTask> arr = scene.getTaskList();
            for (int i = 0; i < arr.size(); i++) {
                MyTask t = arr.get(i);
                System.out.print(t.getEquipment());
                if(i != arr.size() - 1) {System.out.print(",");}
            }
            System.out.println();
        }
    }

    /**
     * 负责人：阿源
     * 功能  ：sceneTask相关任务
     * 参数  ：void
     * 返回值：void
     */
    public void deleteSceneTask() {
        showSceneTask();
        System.out.println("请输入要删除的场景名：");
        String sceneName = sc.next();
        //调用Service层方法删除
        mangerService.deleteSceneTask(sceneName);
    }

    /**
     * 负责人：阿源
     * 功能  ：sceneTask相关任务
     * 参数  ：void
     * 返回值：void
     */
    public void updateSceneTask() {
        showSceneTask();
        HashMap<String, Scene> map = mangerService.getSceneMap();
        while (true) {
            System.out.println("是否退出 -1退出 其他表示继续界面");
            String exit = sc.next();
            if ("-1".equals(exit)) {
                break;
            }
            System.out.println("请输入场景名");
            String sceneName = sc.next();
            if (map.containsKey(sceneName)) {
                while (true) {
                    System.out.println("是否退出 -1退出 其他表示继续界面");
                    String exit2 = sc.next();
                    if ("-1".equals(exit2)) {
                        break;
                    }
                    System.out.println("当前的场景的任务列表为");
                    mangerService.showSceneTaskList(sceneName);
                    System.out.println("请输入需要修改的任务下标(以1为底)");
                    int index = sc.nextInt();
                    //判断下标
                    if (mangerService.indexError(sceneName, index)) {
                        System.out.println("下标出现问题");
                        continue;
                    }
                    System.out.println("请输入任务名");
                    String taskName = sc.next();
                    System.out.println(mangerService.showEquipment());
                    System.out.println("请选择设备下标（以1为底）");
                    int equipId = sc.nextInt();
                    if (mangerService.equipmentIndexError(equipId)) {
                        System.out.println("设备下标出现了问题");
                        continue;
                    }
                    //方法名展示
                    System.out.println("当前设备的方法有");
                    mangerService.showEquipmentMethod(equipId, 1);
                    System.out.println("请输入选择的方法:");
                    String methodName = sc.next();
                    if(!mangerService.methodExists(equipId,2,methodName)){
                        System.out.println("输入的方法不对");
                        continue;
                    }else{
                        methodName = mangerService.getRealMethod(equipId,2,methodName);
                    }
                    System.out.println("当前方法的参数为:");
                    boolean paramsExist = mangerService.getMethodParams(methodName);
                    String param = null;
                    if (paramsExist) {
                        System.out.println("请选择参数");
                        param = sc.next();
                        if(!mangerService.paramExist(param)){
                            System.out.println("当前参数不存在");
                            continue;
                        }else{
                            param = mangerService.getRealParam(methodName,param);
                        }
                    }
                    System.out.println(1);
                        //参数设置
                    mangerService.updateSceneTaskList(sceneName, index,taskName , equipId, methodName,param);
                    System.out.println("场景修改成功");
                }
            } else {
                System.out.println("场景名不存在，请重新输入：");
                continue;
            }
        }
    }

    /**
     * 负责人：阿源
     * 功能  ：sceneTask相关任务
     * 参数  ：void
     * 返回值：void
     */
    public void addSceneTask() {

        while (true) {
            System.out.println("欢迎来到 添加场景界面");
            System.out.println("---------------------------------");
            System.out.println("是否退出 -1退出 其他表示继续界面");
            String exit = sc.next();
            if ("-1".equals(exit)) {
                break;
            }
            System.out.println("请输入要添加的场景名：");
            String sceneName = sc.next();
            if (mangerService.sceneTaskExist(sceneName) != null) {
                System.out.println("场景名重复,请重新输入");
            } else {
                ArrayList<MyTask> arr = new ArrayList<>();
                while (true) {
                    System.out.println("是否退出 -1退出 其他表示继续界面");
                    String exit2 = sc.next();
                    if ("-1".equals(exit2)) {
                        break;
                    }
                    try {
                        System.out.println("请输入任务名");
                        String taskName = sc.next();
                        System.out.println(mangerService.showEquipment());
                        System.out.println("请选择设备下标（以1为底）");
                        int equipId = sc.nextInt();
                        if (mangerService.equipmentIndexError(equipId)) {
                            System.out.println("设备下标出现了问题");
                            continue;
                        }
                        //方法名展示
                        System.out.println("当前设备的方法有");

                        mangerService.showEquipmentMethod(equipId, 1);
                        System.out.println("请输入选择的方法:");
                        String methodName = sc.next();
                        if(!mangerService.methodExists(equipId,2,methodName)){
                            System.out.println("输入的方法不对");
                            continue;
                        }else{
                            methodName = mangerService.getRealMethod(equipId,2,methodName);
                        }
                        System.out.println("当前方法的参数为:");
                        boolean paramsExist = mangerService.getMethodParams(methodName);
                        String param = null;
                        if (paramsExist) {
                            System.out.println("请选择参数");
                            param = sc.next();
                            if(!mangerService.paramExist(param)){
                                System.out.println("当前参数不存在");
                                continue;
                            }else{
                                param = mangerService.getRealParam(methodName,param);
                            }
                        }
                        MyTask newTask = mangerService.getNewTask(taskName, equipId, methodName, param);
                        if(newTask != null){
                            arr.add(newTask);
                            System.out.println("任务添加成功");
                        }
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
                if (arr.size() >= 1) {
                    mangerService.addScene(sceneName, arr);
                    System.out.println("场景添加成功");
                }else{
                    System.out.println("场景添加失败");
                }
            }
        }
    }
}
