package com.brain.domain.equiptment.Impl.equipment;

import com.brain.domain.equiptment.Equipment;
import com.brain.domain.equiptment.Impl.category.AirConditioner;

import java.util.Objects;

/**
 * ClassName: AirConditioner2
 * Package: com.brain.pojo.Impl.equipment
 * Description:
 *
 * @Author 皮皮dog
 * @Create 2023/4/16 2:06
 * @Version 1.0
 */
public class AirConditioner2 extends AirConditioner implements Equipment {
    public static final String BRAND = "海尔";

    public static final String EQUIPMENTNAME = "海尔(Haier) 智能巨匠4侧吹壁挂式空调";

    public int equipmentId;

    public int price = 3700;



    public AirConditioner2(int equipmentId) {
        this.equipmentId = equipmentId;
    }

    public int getEquipmentId() {
        return equipmentId;
    }

    public void setEquipmentId(int equipmentId) {
        this.equipmentId = equipmentId;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }



    @Override
    public String getBrandName() {
        return BRAND;
    }

    @Override
    public String getEquipmentName() {
        return EQUIPMENTNAME;
    }

    @Override
    public int getEquipmentPrice() {
        return this.price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {return true;}
        if (o == null || getClass() != o.getClass()) {return false;}
        AirConditioner1 that = (AirConditioner1) o;
        return price == that.price;
    }

    @Override
    public int hashCode() {
        return Objects.hash(price);
    }

    @Override
    public String toString() {
        return "AirConditioner1{" +
                "brand="+getBrandName()+
                "equipmentName="+getEquipmentName() +
                "equipmentId=" + equipmentId +
                ", price=" + price +
                ", airConditioningAngle=" + airConditioningAngle +
                '}';
    }

    /** 强大加热 */
    public void strongHeating(){
        if(getWorkMode() <3 || getMode()!=2){
            setMode(2);
            setWorkMode(3);
            System.out.println(EQUIPMENTNAME+"开启强制热功能");
        }
    }

    /** 强制冷却 */
    public void forcedCooling(){
        if(getMode() <3 || getWorkMode()!=1){
            setMode(3);
            setWorkMode(1);
            System.out.println(EQUIPMENTNAME+"开启强制制冷功能");
        }
    }

    /** 健康灭菌 */
    public void healthySterilization(){
        System.out.println("开始健康灭菌功能");
    }

}
