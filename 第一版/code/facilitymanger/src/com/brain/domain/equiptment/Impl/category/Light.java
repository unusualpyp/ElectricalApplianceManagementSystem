package com.brain.domain.equiptment.Impl.category;

import com.brain.domain.equiptment.Category;

/**
 * ClassName: light
 * Package: com.brain.pojo.Impl.category
 * Description:
 *
 * @Author 皮皮dog
 * @Create 2023/4/16 2:23
 * @Version 1.0
 */
public class Light implements Category {
    public static final String CATEGORY = "灯";


    /** 状态  false 关闭 true 开启*/
    public boolean status = false;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public void reverseStatus(){
        this.status = !this.status;
    }

    @Override
    public String toString() {
        return "Light{" +
                "status=" + status +
                '}';
    }

    @Override
    public String getCategoryName() {
        return CATEGORY;
    }
}
