package com.bsoft.login.activity;

import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

/**
 * Created by shiyagang on 2019/10/23.
 */

public class LoginActivity extends AppCompatActivity {
    TextView success;
    TextView fail;

 /*   @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
        initListener();
    }


    private void initListener(){
        success.setOnClickListener(this);
        fail.setOnClickListener(this);
    }

    private void initView(){
        success = findViewById(R.id.success);
        fail = findViewById(R.id.fail);
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.fail){
            loginFail();
        }else if (id == R.id.success){
            loginSuccess();
        }
    }

    *//**
     * 登录失败
     *//*
    private void loginFail(){
        Intent mIntents = new Intent();
        mIntents.putExtra("login_status", false);
        setResult(RESULT_OK, mIntents);
        finish();
    }


    *//**
     * 登录成功
     *//*
    private void loginSuccess(){
        Intent mIntents = new Intent();
        mIntents.putExtra("login_status", true);
        setResult(RESULT_OK, mIntents);
        finish();
    }*/
}
