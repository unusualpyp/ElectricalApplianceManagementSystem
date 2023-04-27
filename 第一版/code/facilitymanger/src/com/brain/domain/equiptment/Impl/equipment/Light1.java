package com.brain.domain.equiptment.Impl.equipment;

import com.brain.domain.equiptment.Equipment;
import com.brain.domain.equiptment.Impl.category.Light;

import java.util.Objects;

/**
 * ClassName: Light1
 * Package: com.brain.pojo.Impl.equipment
 * Description:
 *
 * @Author 皮皮dog
 * @Create 2023/4/16 2:25
 * @Version 1.0
 */
public class Light1 extends Light implements Equipment {
    public static final String BRAND = "飞利浦";

    public static final String EQUIPMENT_NAME = "飞利浦智能落地灯";


    public int price = 1500;

    public int equipmentId;



    /** 光亮度 百制 */
    public int lightBrightness = 50;

    /** 灯光颜色 */
    public String lightColor = "白黄色";


    public Light1(int equipmentId) {
        this.equipmentId = equipmentId;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getEquipmentId() {
        return equipmentId;
    }

    public void setEquipmentId(int equipmentId) {
        this.equipmentId = equipmentId;
    }


    public int getLightBrightness() {
        return lightBrightness;
    }

    /**
     * 设置灯光亮度
     *
     * @param lightBrightness 光亮度
     */
    public void setLightBrightness(int lightBrightness) {
        this.lightBrightness = lightBrightness;
    }

    public String getLightColor() {
        return lightColor;
    }

    /**
     * 设置灯光颜色
     *
     * @param lightColor 灯光颜色
     */
    public void setLightColor(String lightColor) {
        this.lightColor = lightColor;
    }

    @Override
    public String toString() {
        return "Light1{" +
                "brand="+getBrandName()+
                "equipmentName="+getEquipmentName() +
                "price=" + price +
                ", equipmentId=" + equipmentId +
                ", status=" + status +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {return true;}
        if (o == null || getClass() != o.getClass()) {return false;}
        Light1 light1 = (Light1) o;
        return price == light1.price;
    }

    @Override
    public int hashCode() {
        return Objects.hash(price);
    }

    @Override
    public String getBrandName() {
        return BRAND;
    }

    @Override
    public String getEquipmentName() {
        return EQUIPMENT_NAME;
    }

    @Override
    public int getEquipmentPrice() {
        return this.price;
    }

    public void automaticColorAdjustment(double voice){
        if (voice < 30){
            setLightBrightness(10);
            setLightColor("浅紫色");
        } else if (voice < 60) {
            setLightBrightness(30);
            setLightColor("白黄色");
        } else if (voice < 100) {
            setLightBrightness(60);
            setLightColor("白色");
        }else{
            setLightBrightness(70);
            setLightColor("黄色");
        }
    }


}
