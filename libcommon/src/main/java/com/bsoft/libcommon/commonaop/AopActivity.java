package com.bsoft.libcommon.commonaop;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

//Aop集成   https://blog.csdn.net/freak_csh/article/details/89705422
public class AopActivity extends AppCompatActivity {


    private static final String TAG = "MainActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // setContentView(R.layout.activity_main);

    }

    @Async
    public void readFile(View view) {
        Log.e(TAG, "读取文件:" + Thread.currentThread().toString());
        showResult();
    }

    @Async
    public void writeFile(View view) {
        //在子线程
        Log.e(TAG, "写入文件:" + Thread.currentThread().toString());
        //在主线程
        showResult();
    }

    @Main
    public void showResult() {
        Toast.makeText(this, "获得结果,更新UI" + Thread.currentThread().toString(), Toast.LENGTH_SHORT)
                .show();
    }


    public void getResult(View view) {
        sum(1, 20);
    }

    @Logger(Log.ERROR)
    private int sum(int i, int j) {
        int result = i + j;
        Log.e("asdsad","sadasdsasdas");
        return result;
    }


}
