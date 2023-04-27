package com.brain.domain.equiptment.Impl.category;

import com.brain.domain.equiptment.Category;

/**
 * 窗帘
 * ClassName: Curtains
 * Package: com.brain.pojo.equiptment.Impl.category
 * Description:
 *
 * @Author 皮皮dog
 * @Create 2023/4/16 12:33
 * @Version 1.0
 * @date 2023/04/16
 */
public class Curtains implements Category {
    public static final String CATEGORY = "窗帘";



    @Override
    public String getCategoryName() {
        return null;
    }

    /** 状态  开关 */
    private boolean status = false;

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    /**
     * 关闭窗帘
     */
    public void closeTheCurtains(){
        if(this.status != false){
            this.status = false;
            System.out.println("窗帘开始关闭");
        }else{
            System.out.println("窗帘已经关闭");
        }
    }

    /**
     * 打开窗帘
     */
    public void openTheCurtains(){
        if(this.status != true){
            this.status = true;
            System.out.println("窗帘开始打开");
        }else{
            System.out.println("窗帘已经打开");
        }
    }

    /**
     * 转换开关
     */
    public void changeoverSwitch(){
        this.status = !this.status;
        if(this.status == false){
            System.out.println("窗帘开始关闭");
        }else{
            System.out.println("窗帘开始打开");
        }
    }
}
