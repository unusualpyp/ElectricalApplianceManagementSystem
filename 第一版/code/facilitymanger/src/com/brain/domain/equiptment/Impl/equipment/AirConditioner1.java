package com.brain.domain.equiptment.Impl.equipment;

import com.brain.domain.equiptment.Equipment;
import com.brain.domain.equiptment.Impl.category.AirConditioner;

import java.util.Objects;

/**
 * ClassName: AirConditioner1
 * Package: com.brain.pojo.Impl.equipment
 * Description:
 *
 * @Author 皮皮dog
 * @Create 2023/4/16 1:35
 * @Version 1.0
 */
public class AirConditioner1 extends AirConditioner implements Equipment {
    public static final String BRAND = "格力";

    public static final String EQUIPMENTNAME = "格力(GREE) 一级能效变频壁挂式空调";

    public int equipmentId;

    public int price = 3300;




    public AirConditioner1(int equipmentId) {
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

    /**
     * 超级强大空气供给
     *
     * @param mode 模式
     */
    public void superStrongAirSupply(int mode){
        if (getMode() < 3){
            setMode(3);
        }else{
            System.out.println(EQUIPMENTNAME+"当前开始超强送风了！");
        }
    }

    /**
     * 自我清洁
     */
    public void selfCleaning(){
       System.out.println(EQUIPMENTNAME+"开始执行自我清洗");
   }

    /**
     * 静音模式
     */
    public void muteMode(){
        if(getWorkMode() > 1) {
            setMode(1);
            System.out.println(EQUIPMENTNAME + "启动节能状态");
            System.out.println(EQUIPMENTNAME + "开始静音模式");
        }
    }
}
