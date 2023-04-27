package com.brain.domain.equiptment;

/**
 * ClassName: Equipment
 * Package: com.brain.pojo
 * Description:
 *    电器产品的特色功能总类
 * @Author 皮皮dog
 * @Create 2023/4/15 23:11
 * @Version 1.0
 */
public interface Equipment {
    /**
     * 得到品牌名称
     *
     * @return {@link String}
     */
    public String getBrandName();

    /**
     * 得到设备名称
     *
     * @return {@link String}
     */
    public String getEquipmentName();

    /**
     * 得到设备价格
     *
     * @return int
     */
    public int getEquipmentPrice();





}
