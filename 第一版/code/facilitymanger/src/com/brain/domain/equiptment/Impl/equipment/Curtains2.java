package com.brain.domain.equiptment.Impl.equipment;

import com.brain.domain.equiptment.Equipment;
import com.brain.domain.equiptment.Impl.category.Curtains;

import java.util.Objects;

/**
 * ClassName: Curtains2
 * Package: com.brain.pojo.equiptment.Impl.equipment
 * Description:
 *
 * @Author 皮皮dog
 * @Create 2023/4/16 12:45
 * @Version 1.0
 */
public class Curtains2 extends Curtains implements Equipment {
    public static final String BRAND = "小蚁";

    public static final String EQUIPMENT_NAME = "小蚁智能窗帘";

    private int price = 1000;

    public int equipmentId;


    /** 遮阳模式 */
    public boolean maskingMode = false;

    public Curtains2(int equipmentId) {
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

    public boolean getMaskingMode() {
        return maskingMode;
    }

    public void setMaskingMode(boolean maskingMode) {
        this.maskingMode = maskingMode;
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
        Curtains2 Curtains2 = (Curtains2) o;
        return price == Curtains2.price;
    }

    @Override
    public int hashCode() {
        return Objects.hash(price);
    }

    @Override
    public String toString() {
        return "Curtains2{" +
                "brand="+getBrandName()+
                "equipmentName="+getEquipmentName() +
                "price=" + price +
                ", equipmentId=" + equipmentId +
                '}';
    }

    /** 遮阳布开始 */
    public void sunshadeClothStart(){
        if(getMaskingMode() ==false){
            setMaskingMode(true);
            System.out.println(EQUIPMENT_NAME+ "遮阳布打开");
        }else{
            System.out.println(EQUIPMENT_NAME+ "遮阳布关闭");
        }
    }

}
