package com.bsoft.libnet.environment;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import androidx.annotation.IdRes;
import com.bsoft.libbasic.constant.HttpConstants;
import com.bsoft.libbasic.utils.ExitUtil;
import com.bsoft.libnet.R;
import com.bsoft.libnet.model.NetAddressVo;
import com.bsoft.libnet.model.NetRadio;
import com.bsoft.libnet.utils.NetEnvironmentUtil;

import java.util.ArrayList;


/**
 * create change_net chenkai 20170904
 */



public class ChangeNetActivity extends Activity {

    private NetAddressVo originVo;
    private ArrayList<NetAddressVo> netAddressVos;
    private ArrayList<NetRadio> radioButtons = new ArrayList<>();
    private NetAddressVo curAddressVo;
    /*View*/
    private TextView tvInternalVersion;
    private ImageView ivBack;
    private TextView tvComfirm;
    private RadioGroup changeItems;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.networklib_activity_change_net);
        //default
        netAddressVos = NetEnvironmentUtil.getNetEnvironments(this);
        originVo = NetEnvironmentUtil.getCurEnvironment(this);

        initLayout();

        tvInternalVersion.setText("内部版本号：" + HttpConstants.versionName+"~~"+ HttpConstants.versionCode);
        updateVo(originVo);
        //动态添加选项
        radioButtons.clear();
        if (netAddressVos != null && !netAddressVos.isEmpty()) {
            for (int i = 0; i < netAddressVos.size(); i++) {
                NetAddressVo vo = netAddressVos.get(i);
                if (vo != null) {
                    addRadioBtn(i, vo);
                }
            }
        }

        changeItems.setOnCheckedChangeListener(checkedChangeListener);
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        tvComfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (originVo != null && curAddressVo != null
                        && (!TextUtils.equals(originVo.getEnvironment(), curAddressVo.getEnvironment()))) {
                    NetEnvironmentUtil.setEnvironment(ChangeNetActivity.this, curAddressVo.getEnvironment());

                    ExitUtil.exitApp();
                } else {
                    finish();
                }
            }
        });
    }



    protected void initLayout() {

        ivBack = findViewById(R.id.ivBack);
        tvComfirm = findViewById(R.id.tvComfirm);
        tvInternalVersion = findViewById(R.id.tvInternalVersion);

        changeItems = findViewById(R.id.changeItems);
    }

    private void updateVo(NetAddressVo addressVo) {
        if (addressVo == null) {
            return;
        }
        curAddressVo = addressVo;

    }

    private void addRadioBtn(int id, NetAddressVo addressVo) {
        if (addressVo == null) {
            return;
        }
        RadioButton radioButton = new RadioButton(this);
        NetRadio netRadio = new NetRadio();

        radioButton.setId(id);
        RadioGroup.LayoutParams layoutParams = new RadioGroup.LayoutParams(RadioGroup.LayoutParams.WRAP_CONTENT
                , RadioGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(15, 15, 15, 15);
        radioButton.setLayoutParams(layoutParams);
        radioButton.setText(addressVo.getEnvironmentText() + "   " + addressVo.getHttpApiUrl()
                + "\n " + addressVo.getHttpDownloadUrl()
                + "\n " + addressVo.getHttpImgUrl());
        radioButton.setTextSize(14);
        //radioButton.setButtonDrawable(android.R.color.transparent);//隐藏单选圆形按钮
        radioButton.setGravity(Gravity.LEFT);
        radioButton.setPadding(15, 15, 15, 15);
        if (originVo != null && TextUtils.equals(originVo.getEnvironment(), addressVo.getEnvironment())) {
            radioButton.setChecked(true);
        } else {
            radioButton.setChecked(false);
        }
        netRadio.addressVo = addressVo;
        netRadio.radioButton = radioButton;
        radioButtons.add(netRadio);
        changeItems.addView(radioButton);
    }

    private RadioGroup.OnCheckedChangeListener checkedChangeListener = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
            NetRadio radio = radioButtons.get(i);
            if (radio != null) {
                updateVo(radio.addressVo);
            }

        }
    };
}
