package com.brain.domain.equiptment.Impl.equipment;

import com.brain.domain.equiptment.Equipment;
import com.brain.domain.equiptment.Impl.category.Curtains;

import java.util.Objects;

/**
 * ClassName: Curtains1
 * Package: com.brain.pojo.equiptment.Impl.equipment
 * Description:
 *
 * @Author 皮皮dog
 * @Create 2023/4/16 12:40
 * @Version 1.0
 */
public class Curtains1 extends Curtains implements Equipment {
    public static final String BRAND = "广联达";

    public static final String EQUIPMENT_NAME = "广联达智能窗帘";

    private int price = 1500;

    public int equipmentId;



    public Curtains1(int equipmentId) {
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
    public boolean equals(Object o) {
        if (this == o) {return true;}
        if (o == null || getClass() != o.getClass()) {return false;}
        Curtains1 curtains1 = (Curtains1) o;
        return price == curtains1.price;
    }

    @Override
    public int hashCode() {
        return Objects.hash(price);
    }

    @Override
    public String toString() {
        return "Curtains1{" +
                "brand="+getBrandName()+
                "equipmentName="+getEquipmentName() +
                "price=" + price +
                ", equipmentId=" + equipmentId +
                '}';
    }

    /**
     * 静音模式
     */
    public void muteMode(){
        System.out.println(EQUIPMENT_NAME + "开启极静音开关模式");
    }



}
