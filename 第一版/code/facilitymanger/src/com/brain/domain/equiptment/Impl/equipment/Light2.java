package com.brain.domain.equiptment.Impl.equipment;

import com.brain.domain.equiptment.Equipment;
import com.brain.domain.equiptment.Impl.category.Light;

/**
 * ClassName: Light2
 * Package: com.brain.pojo.Impl.equipment
 * Description:
 *
 * @Author 皮皮dog
 * @Create 2023/4/16 2:38
 * @Version 1.0
 */
public class Light2 extends Light implements Equipment {
    public static final String BRAND = "Yeelight";

    public static final String EQUIPMENT_NAME = "Yeelight智能吸顶灯";


    public int price = 400;

    public int equipmentId;



    public Light2(int equipmentId) {
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

    @Override
    public String toString() {
        return "Light2{" +
                "brand="+getBrandName()+
                "equipmentName="+getEquipmentName() +
                "price=" + price +
                ", equipmentId=" + equipmentId +
                ", status=" + status +
                '}';
    }
}
