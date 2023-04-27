package com.brain.domain.equiptment.Impl.category;

import com.brain.domain.equiptment.Category;

/**
 * 冰箱
 * ClassName: Refrigerator
 * Package: com.brain.pojo.Impl.category
 * Description:
 *
 * @Author 皮皮dog
 * @Create 2023/4/15 23:37
 * @Version 1.0
 * @date 2023/04/15
 */
public class Refrigerator implements Category {
    public static final String CATEGORY = "冰箱";

    /** 模式 1表示节能模式 、2表示正常模式 3表示超频模式 */
    public int mode = 2;

    /** 状态 false表示关机 true表示开机 */
    public boolean status = false;


    public int getMode() {
        return mode;
    }

    public void setMode(int mode) {
        this.mode = mode;
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    /**
     * 温度控制
     * 将温度控制在一个稳定的温度
     */
    public void temperatureControl(){
        System.out.println("冰箱温度控制稳定");
        System.out.println("----------------------------------");
        System.out.println("当前上冰箱的温度为 -5℃");
        System.out.println("当前下冰箱的温度为 4℃");
    }

    /**
     * 灭菌 功能
     *
     */
    public void sterilization(){
        System.out.println("冰箱开始灭菌,请稍等。。。");
        System.out.println("。。。。。。。");
        System.out.println("冰箱灭菌完成。");
    }


    @Override
    public String toString() {
        return "Refrigerator{" +
                "category="+ CATEGORY +
                "mode=" + mode +
                ", status=" + status +
                '}';
    }

    @Override
    public String getCategoryName() {
        return CATEGORY;
    }
}
