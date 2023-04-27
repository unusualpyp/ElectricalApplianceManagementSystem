package com.brain.domain.equiptment.Impl.category;

import com.brain.domain.equiptment.Category;

/**
 * ClassName: AirConditioner
 * Package: com.brain.pojo.Impl.category
 * Description:
 *
 * @Author 皮皮dog
 * @Create 2023/4/16 1:28
 * @Version 1.0
 */
public class AirConditioner implements Category {
    public static final String CATEGORY = "空调";

    @Override
    public String getCategoryName() {
        return CATEGORY;
    }

    /** 温度 */
    private int temperature = 23;
    /** 模式 1、制冷 2、制热 3、保湿 4、干燥  */
    private int workMode = 1;

    /** 工作模式  1 、节能模式  2、正常模式  3 超频模式*/
    private int mode = 1;

    /** 状态  false 关 true 开*/
    private boolean status = false;

    /** 空调角度 */
    public int airConditioningAngle = 45;


    public int getTemperature() {
        return temperature;
    }

    public void setTemperature(int temperature) {
        this.temperature = temperature;
    }

    public int getMode() {
        return mode;
    }

    public void setMode(int mode) {
        this.mode = mode;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public int getWorkMode() {
        return workMode;
    }

    public void setWorkMode(int workMode) {
        this.workMode = workMode;
    }

    public int getAirConditioningAngle() {
        return airConditioningAngle;
    }

    public void setAirConditioningAngle(int airConditioningAngle) {
        this.airConditioningAngle = airConditioningAngle;
    }

    @Override
    public String toString() {
        return "AirConditioner{" +
                "temperature=" + temperature +
                ", mode=" + mode +
                ", status=" + status +
                '}';
    }
}
