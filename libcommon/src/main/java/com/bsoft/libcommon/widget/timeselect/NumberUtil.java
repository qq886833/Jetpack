package com.bsoft.libcommon.widget.timeselect;

import android.text.TextUtils;

/**
 * Created by dongrong.fu on 2019/10/8
 */
public class NumberUtil {

    public static String[] buildNumberArr(int start, int limit, int step){
        int size = (limit - start) / step + 1;
        String[] resultArr = new String[size];
        int numberInt = start;
        for(int i = 0;i < size;i++){
            resultArr[i] = String.valueOf(numberInt);
            numberInt += step;
        }
        return resultArr;
    }

    public static String[] buildTimeNumberArr(int start, int limit, int step){
        int size = (limit - start) / step + 1;
        String[] resultArr = new String[size];
        int numberInt = start;
        for(int i = 0;i < size;i++){
            String tempString = String.valueOf(numberInt);
            if(tempString.length() == 1){
                tempString = "0".concat(tempString);
            }
            resultArr[i] = tempString;
            numberInt += step;
        }
        return resultArr;
    }

    public static String[] buildNumberArr(String start, String limit, String step){
        try{
            return buildNumberArr(Integer.parseInt(start), Integer.parseInt(limit), Integer.parseInt(step));
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public static int getDefaultPosition(String[] arr, String number){
        if(arr == null || TextUtils.isEmpty(number)) return 0;
        for(int i = 0;i < arr.length;i++){
            if(number.equals(arr[i])){
                return i;
            }
        }
        return 0;
    }

}
