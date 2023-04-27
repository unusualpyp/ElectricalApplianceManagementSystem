package com.brain.dao;



import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

/**
 * ClassName: OtherDao
 * Package: com.brain.dao
 * Description:
 *
 * @Author 皮皮dog
 * @Create 2023/4/19 0:45
 * @Version 1.0
 */
public class OtherDao {

    /**
     * 池数据获取设备id
     *
     * @return {@link HashSet}<{@link Integer}>
     */
    public static HashSet<Integer> getEquipmentIdPoolData(){
        /**1、创造一个HashSet对象 */
        HashSet<Integer> hashSet = new HashSet<>();


        //G:\Practice\facilitymanger\data\equipmentIdPoolInfo.txt
        //G:\Practice\facilitymanger\src\com\brain\dao\otherdao.java
        //路径不一定对，需要测试
        /**2、读取文件并写入对象中 */
        try (BufferedReader br = new BufferedReader(new FileReader("data/equipmentIdPoolInfo.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] valuesStr = line.split("、");
                //这边不进行验证了，因为是自己设定的
                for (int i = 0; i < valuesStr.length; i++) {
                    Integer valueInt = Integer.parseInt(valuesStr[i]);
                    hashSet.add(valueInt);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return hashSet;
    }

    /**
     * 提交池数据
     *
     * @param pool 池
     */
    public static void commitPoolData(HashSet<Integer> pool){
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("data/equipmentIdPoolInfo.txt"))) {
            for (Integer integer : pool) {
                String str = integer.toString() + "、";
                bw.write(str);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * 获得点卡映射
     *
     * @return {@link HashMap}<{@link String},{@link Integer}>
     */
    public static HashMap<String,Integer> getCardMap(){
        HashMap<String,Integer> map = new HashMap<String,Integer>();

        //读取文件
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader("data/card.txt"));
            String line = reader.readLine();
            while (line != null) {
                String[] words = line.split(":");
                String key = words[0];
                int value = Integer.parseInt(words[1]);
                map.put(key, value);
                line = reader.readLine();
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return map;
    }


    /**
     * 提交点卡映射
     *
     * @param cardMap
     */
    public static void commitCardMap(HashMap<String,Integer> cardMap){
        String filePath = "data/card.txt";


        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(filePath));
            for (String key : cardMap.keySet()) {
                String line = key + ":" + cardMap.get(key).toString();
                writer.write(line);
                writer.newLine();
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * 方法说明
     *
     * @param type 类型 1 表示 读取文件sceneMethod文件 2 表示读取taskMethod 3 表示读取paramsdict
     */
    public static HashMap<String, HashMap<String, String>> getMethod(int type){
        String path = "data/sceneMethod2Chinese.txt";
        if(type == 2){
            path = "data/sceneMethod2Chinese.txt";
        } else if (type == 3) {
            path = "data/paramsdict.txt";
        }
        // 创建 BufferedReader 对象以读取文件中的文本内容
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(path));
            // 定义变量来保存文本行和处理后的结果
            String line;
            HashMap<String, HashMap<String, String>> resultMap = new HashMap<>();

            // 逐行读取文件中的文本，并处理每行的数据
            while ((line = br.readLine()) != null) {
                // 按照 ":" 进行外层 HashMap 的键值对分割
                String[] parts = line.split(":");
                String outerKey = parts[0];
                if (!"{}".equals(parts[1])) {
                    // 构建内层 HashMap 并加入值
                    HashMap<String, String> innerHashMap = new HashMap<>();
                    String[] innerEntries = parts[1].replace("{", "").replace("}", "").split(",");
                    for (String entry : innerEntries) {
                        String[] parts2 = entry.split("&");
                        String innerKey = parts2[0];
                        String innerValue = parts2[1];
                        innerHashMap.put(innerKey, innerValue);
                    }
                    resultMap.put(outerKey, innerHashMap);
                }
            }
            br.close();
            return resultMap;
        } catch (IOException e) {
            throw new RuntimeException("文件不存在或路径错误");
        }

    }



}


    








