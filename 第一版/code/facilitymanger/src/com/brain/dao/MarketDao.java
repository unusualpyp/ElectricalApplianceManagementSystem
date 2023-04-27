package com.brain.dao;

import com.brain.domain.Factory;
import com.brain.domain.equiptment.Equipment;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class MarketDao {
    private ArrayList<String> marketList = new ArrayList<String>();

    private ArrayList<int[]> upperDownList = new ArrayList<int[]>();





    public MarketDao() {

        InitMarket();
    }

    /**
     * 初始化商城 信息
     */
    public void InitMarket() {
        marketList = new ArrayList<String>();
        //读取文件Market文件
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader("data/Market.txt"));
            String line = reader.readLine();
            while (line != null) {
                String[] words = line.split(",");
                String equipmentName = words[0];
                String price = words[1];
                //上下区间
                String[] range = words[2].replace("(","").replace(")","").split("\\&");
                int[] upperAndLowerBound = new int[]{Integer.parseInt(range[0]),Integer.parseInt(range[1])};
                // 库存计算
                int quantity = upperAndLowerBound[1] - upperAndLowerBound[0] - inventoryCalculation(Factory.equipmentIdPool,upperAndLowerBound[0],upperAndLowerBound[1]);

                String Info = equipmentName + "\t" + price +"\t"+ quantity;
                marketList.add(Info);
                upperDownList.add(upperAndLowerBound);
                line = reader.readLine();
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public int inventoryCalculation(HashSet<Integer> equipmentPool,int lower, int upper){
        int count = 0;
        for(int num : equipmentPool){
            if(num >= lower && num <= upper){
                count++;
            }
        }
        return count;
    }


    public ArrayList<String> getMarketList(){
        return marketList;
    }

    public ArrayList<int[]> getUpperDownList(){
        return upperDownList;
    }
}
