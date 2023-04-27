package com.brain.domain.equiptment.Impl.equipment;

import com.brain.domain.equiptment.Equipment;
import com.brain.domain.equiptment.Impl.category.Refrigerator;

import java.util.Objects;

/**
 * 海尔中门冰箱
 * ClassName: HaierHalfRefrigerator
 * Package: com.brain.pojo.Impl.Equipment
 * Description:
 *
 * @Author 皮皮dog
 * @Create 2023/4/15 23:57
 * @Version 1.0
 * @date 2023/04/15
 */
public class HaierHalfRefrigerator extends Refrigerator implements Equipment {

    public static final String BRANDNAME = "海尔";
    public static final String EQUIPMENTNAME = "海尔中门冰箱";

    public int price = 3200;

    public double topTemperature = 4.0;

    public double bottomTemperature = -5.0;

    public int equipmentId;

    private boolean mute = false;



    public HaierHalfRefrigerator(int equipmentId) {
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

    public double getTopTemperature() {
        return topTemperature;
    }

    public void setTopTemperature(double topTemperature) {
        this.topTemperature = topTemperature;
    }

    public double getBottomTemperature() {
        return bottomTemperature;
    }

    public void setEquipmentId(int equipmentId) {
        this.equipmentId = equipmentId;
    }





    public int getEquipmentId() {
        return this.equipmentId;
    }



    public void setBottomTemperature(double bottomTemperature) {
        this.bottomTemperature = bottomTemperature;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {return true;}
        if (o == null || getClass() != o.getClass()) {return false;}
        HaierHalfRefrigerator that = (HaierHalfRefrigerator) o;
        return price == that.price;
    }

    @Override
    public int hashCode() {
        return Objects.hash(price);
    }

    @Override
    public String toString() {
        return "HaierHalfRefrigerator{" +
                "brand="+getBrandName()+
                "equipmentName="+getEquipmentName() +
                "price=" + price +
                ", equipmentId=" + equipmentId +
                ", mode=" + mode +
                ", status=" + status +
                '}';
    }

    /**
     * 设置静音
     */
    public void setMute(){
        if(getMode() == 2){
            setMode(1);
            System.out.println(EQUIPMENTNAME+"模式更改为节能模式");
            System.out.println(EQUIPMENTNAME+"已经进入静音模式");
        }
    }

    public boolean isMuted(){
        return this.mute;
    }


    /**
     * 创建冰块(制作冰淇淋)
     */
    public void createIceCute(){
        int mode = getMode();
        if(mode == 1){
            setBottomTemperature(-10.0);
            System.out.println(EQUIPMENTNAME+"下冰箱已经调整值-10℃");
            System.out.println("冰块预计10分钟制作完成");
        }else{
            setBottomTemperature(-20.0);
            System.out.println(EQUIPMENTNAME+"下冰箱已经调整值-10℃");
            System.out.println("冰块预计5分钟制作完成");
        }
    }


    /**
     * 除霜
     */
    public void defrosting(){
        System.out.println(EQUIPMENTNAME+"冰箱已经开始除霜");
        System.out.println("预计20分钟完成");
    }

}
