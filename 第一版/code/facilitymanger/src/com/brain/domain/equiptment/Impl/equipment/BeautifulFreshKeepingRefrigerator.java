package com.brain.domain.equiptment.Impl.equipment;

import com.brain.domain.equiptment.Equipment;
import com.brain.domain.equiptment.Impl.category.Refrigerator;

import java.util.Objects;

/**
 * ClassName: BeautifulFreshKeepingRefrigerator
 * Package: com.brain.pojo.Impl.equipment
 * Description:
 *
 * @Author 皮皮dog
 * @Create 2023/4/16 0:29
 * @Version 1.0
 */
public class BeautifulFreshKeepingRefrigerator extends Refrigerator implements Equipment {

    public static final String BRANDNAME = "美的";

    public static final String EQUIPMENTNAME = "美的保鲜冰箱";

    public int price = 3200;

    public double topTemperature = 4.0;

    public double bottomTemperature = -5.0;

    public int equipmentId;



    public BeautifulFreshKeepingRefrigerator(int equipmentId) {
        this.equipmentId = equipmentId;
    }


    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public double getTopTemperature() {
        return topTemperature;
    }

    public void setTopTemperature(double topTemperature) {
        this.topTemperature = topTemperature;
    }

    public double getBottomTemperature() {
        return bottomTemperature;
    }

    public void setBottomTemperature(double bottomTemperature) {
        this.bottomTemperature = bottomTemperature;
    }

    public int getEquipmentId() {
        return equipmentId;
    }

    public void setEquipmentId(int equipmentId) {
        this.equipmentId = equipmentId;
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) {return true;}
        if (o == null || getClass() != o.getClass()) {return false;}
        BeautifulFreshKeepingRefrigerator that = (BeautifulFreshKeepingRefrigerator) o;
        return price == that.price;
    }

    @Override
    public int hashCode() {
        return Objects.hash(price);
    }

    @Override
    public String toString() {
        return "BeautifulFreshKeepingRefrigerator{" +
                "brand="+getBrandName()+
                "equipmentName="+getEquipmentName() +
                "price=" + price +
                ", equipmentId=" + equipmentId +
                ", mode=" + mode +
                ", status=" + status +
                '}';
    }

    @Override
    public String getBrandName() {
        return BRANDNAME;
    }

    @Override
    public String getEquipmentName() {
        return EQUIPMENTNAME;
    }

    @Override
    public int getEquipmentPrice() {
        return this.price;
    }

    /**
     * 多级温度控制
     *
     * @param temperature 温度
     */
    public void multistageTemperatureControl(Integer temperature){
        if( temperature <= -10) {
            setBottomTemperature(temperature);
            System.out.println(EQUIPMENTNAME+"冰箱下半冰箱温度设置为"+temperature);
        }else{
            setTopTemperature(temperature);
            System.out.println(EQUIPMENTNAME+"冰箱上半冰箱温度设置为"+temperature);
        }
    }



}
