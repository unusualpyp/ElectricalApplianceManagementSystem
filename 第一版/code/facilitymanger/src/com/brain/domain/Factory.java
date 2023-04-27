package com.brain.domain;

import com.brain.dao.OtherDao;
import com.brain.domain.equiptment.Category;
import com.brain.domain.equiptment.Equipment;
import com.brain.domain.equiptment.Impl.equipment.*;

import java.util.HashSet;

/**
 * 工厂
 * <p>
 * ClassName: Factoty
 * Package: com.brain.pojo
 * Description:
 *
 * @Author 皮皮dog
 * @Create 2023/4/16 0:45
 * @Version 1.0
 * @date 2023/04/16
 */
public class Factory {

    public static HashSet<Integer> equipmentIdPool = OtherDao.getEquipmentIdPoolData();

    public Equipment createObject(int equipmentId){
        if (equipmentId > 0) {
            if (equipmentId < 10000) {
                equipmentIdPool.add(equipmentId);
                return new HaierHalfRefrigerator(equipmentId);
            } else if (equipmentId < 20000) {
                equipmentIdPool.add(equipmentId);
                return new BeautifulFreshKeepingRefrigerator(equipmentId);
            } else if (equipmentId < 30000) {
                equipmentIdPool.add(equipmentId);
                return new AirConditioner1(equipmentId);
            } else if (equipmentId < 40000) {
                equipmentIdPool.add(equipmentId);
                return new AirConditioner2(equipmentId);
            } else if (equipmentId < 45000) {
                equipmentIdPool.add(equipmentId);
                return new Light1(equipmentId);
            } else if (equipmentId < 50000) {
                equipmentIdPool.add(equipmentId);
                return new Light2(equipmentId);
            } else if (equipmentId < 55000) {
                equipmentIdPool.add(equipmentId);
                return new Curtains1(equipmentId);
            } else if (equipmentId < 60000) {
                equipmentIdPool.add(equipmentId);
                return new Curtains2(equipmentId);
            } else {
                return null;
            }
        } else {
            return null;
        }
    }
}
