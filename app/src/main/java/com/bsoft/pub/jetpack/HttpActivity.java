package com.bsoft.pub.jetpack;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.collection.ArrayMap;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.bsoft.libbasic.constant.HttpConstants;
import com.bsoft.libbasic.utils.ToastUtil;
import com.bsoft.libcommon.arouter.CommonArouterGroup;
import com.bsoft.libcommon.utils.AppUtil;
import com.bsoft.libnet.api.NetDownLoadApi;
import com.bsoft.libnet.api.NetPostApi;
import com.bsoft.libnet.apiservice.DownloadService;
import com.bsoft.libnet.observer.BaseObserver;
import com.bsoft.libnet.observer.BaseObserver2;
import com.bsoft.libnet.observer.BaseObserverx;
import com.bsoft.libnet.utils.MD5;
import com.bsoft.libpay.PayManager;
import com.bsoft.libpay.PayResultListener;
import com.bsoft.libpay.dic.PayTypeDic;
import com.bsoft.libpay.model.PayResult;
import com.bsoft.libpay.widget.PayTypeLayout;
import com.bsoft.pub.jetpack.api.NewsApiInterface;
import com.bsoft.pub.jetpack.model.ConfigContentVo;
import com.bsoft.pub.jetpack.model.LoginResponse;
import com.bsoft.pub.jetpack.model.TransactionRecordVo;
import com.google.gson.Gson;
import com.jeremyliao.liveeventbus.LiveEventBus;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

import java.io.*;
import java.util.ArrayList;

import static com.bsoft.libpay.BasePayActivity.KEY_PAY_RESULT_NAME;


@Route(path = CommonArouterGroup.TEST_ACTIVITY)
public class HttpActivity extends AppCompatActivity {

    AppCompatActivity appCompatActivity;

    private TextView sample_text;
    private Button button, button2, button3, button4, button5,button6;
    private ImageView iv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_http);
        appCompatActivity=this;
        sample_text = findViewById(R.id.sample_text);
        button = findViewById(R.id.button);
        button3 = findViewById(R.id.button3);
        button2 = findViewById(R.id.button2);
        button4 = findViewById(R.id.button4);
        button5= findViewById(R.id.button5);
        button6= findViewById(R.id.button6);
        iv = findViewById(R.id.iv);
       // button2 = findViewById(R.id.button2);
        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
            //   login();
                loginT();
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                configTask();
            }
        });
        button3.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                getList();
            }
        });
        button4.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                getUpLoad();
           //     getPayType();
            }
        });
        button5.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                getDownLoad();
            }
        });


        PayTypeLayout payTypeLayout = findViewById(R.id.paytype_layout);
        payTypeLayout.takePayType("fcfe2049-2681-4bfd-a038-216baea6da1f","1","0.01","1");



        PayManager payUtil = new PayManager(appCompatActivity, payResultListener);
        button6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // Toast.makeText(HttpActivity.this, payTypeLayout.getPayType(), Toast.LENGTH_LONG).show();
                String s=  "{\"appid\":\"wxdb5302652abbee2d\",\"partnerid\":\"1359139002\",\"prepayid\":\"wx200932471245149ed8ed7e311743694600\",\"package\":\"Sign=WXPay\",\"noncestr\":\"erm7IwDg9y6UJOQJ\",\"timestamp\":\"1579483967\",\"sign\":\"38F2CEF55263FBD93242F913C2473661\"}";

                if(TextUtils.isEmpty(payTypeLayout.getPayType())){
                    ToastUtil.showLong("请先选择支付方式");
               }else{
                  //  payUtil.pay(PayTypeDic.TYPE_WEIXIN, s);
                      payUtil.pay(PayTypeDic.TYPE_ALI, "alipay_sdk=alipay-sdk-java-dynamicVersionNo&app_id=2018080160806862&biz_content=%7B%22body%22%3A%22%E5%A4%8D%E8%AF%8A%E9%85%8D%E8%8D%AF%22%2C%22goods_type%22%3A%221%22%2C%22out_trade_no%22%3A%22338d3d2308a54e1daa17544b7c0c00c0%22%2C%22product_code%22%3A%22bsoftmobile%40qq.com%22%2C%22seller_id%22%3A%22bsoftmobile%40qq.com%22%2C%22store_id%22%3A%222088121633623155%22%2C%22subject%22%3A%22%E5%A4%8D%E8%AF%8A%E9%85%8D%E8%8D%AF%22%2C%22timeout_express%22%3A%223m%22%2C%22total_amount%22%3A%220.01%22%7D&charset=utf-8&format=json&method=alipay.trade.app.pay&notify_url=%E5%95%86%E6%88%B7%E5%A4%96%E7%BD%91%E5%8F%AF%E4%BB%A5%E8%AE%BF%E9%97%AE%E7%9A%84%E5%BC%82%E6%AD%A5%E5%9C%B0%E5%9D%80&sign=JlCLUMy7dOmRnLwAwzRJoas8wO%2FiSo3Puzvo29KO5e4e%2BIj0%2Fy3Y51YgqJrrQsmCuVIRghFHsRbtG8p6FP5MtXaiQ30e1hXOpqazOVvTkhG7PcD%2BThmAmuYd34QlVwvoUVdrZKbUfKEV02NEUmP3qVnK6om37DWBuNT0NaCCIjINUfkmngd4usYbIFICn8gpurnIvtK0QhMf8J%2FEYgKmvZFxtuenJgTtvtY0XmZJjUyDvjVSxjFQ5dJ7rRXs1GPHX514L4jKUU1MYCJgbG3aKR4CeDfCDeSsznhK6B6QvImowf3JqwEgkRuZYshL7%2BEbX%2FM1qOuu%2BwhbuVSVTsXxuw%3D%3D&sign_type=RSA2&timestamp=2020-01-20+09%3A08%3A17&version=1.0");

                }


            }
        });

    }
    private PayResultListener payResultListener = new PayResultListener() {
        @Override
        public void start(String payType, String appId, String payInfo) {

        }

        @Override
        public void success(String payType, PayResult resultVo) {
            // Log.e("DemoPayActivity;success;payType=" + payType);
            taskQueryPay("338d3d2308a54e1daa17544b7c0c00c0");
        }

        @Override
        public void error(String payType, PayResult resultVo) {
            //  Log.e("DemoPayActivity;error;payType=" + payType);
            ToastUtil.showLong(resultVo.getMsg());
        }

        @Override
        public void cancel(String payType, PayResult resultVo) {
            // Log.e("DemoPayActivity;cancel;payType=" + payType);
            ToastUtil.showLong(resultVo.getMsg());
        }
    };
    public void taskQueryPay(String tradeNo) {
        ArrayList<Object> list = new ArrayList<Object>();
        ArrayMap<String, String> head = new ArrayMap<>();
        ArrayMap<String, Object> body = new ArrayMap<String, Object>();
        head.put(HttpConstants.Head_Id, "hcn.payTradeV2");
        head.put(HttpConstants.Head_Method, "notifyPayResult");

        body.put("tradeNo",tradeNo);
        list.add(body);

        NetPostApi.getInstance().post(HttpConstants.REQUEST_URL,head, list, String.class, new BaseObserver<String>() {
            @Override
            public void onHandlePrePare(Disposable d) {
                //   showLoadingDialog();
            }

            @Override
            protected void onHandleSuccess(String value) {
                LiveEventBus.get(KEY_PAY_RESULT_NAME).post(value);
            }

            @Override
            protected void onHandleError(Throwable e) {

                //  dismissLoadingDialog();
                ToastUtil.showLong(e.getMessage());
            }

            @Override
            protected void onHandleComplete() {
                // dismissLoadingDialog();
            }

        });



    }
    private void taskPayOrder(final String orderNo, final String curPayType, final String price) {


        ArrayList<Object> list = new ArrayList<Object>();
        ArrayMap<String, String> head = new ArrayMap<>();
        ArrayMap<String, Object> body = new ArrayMap<String, Object>();
        head.put(HttpConstants.Head_Id, "hcn.payTradeV2");
        head.put(HttpConstants.Head_Method, "getOrgPayInfo");

        body.put("hospitalCode","fcfe2049-2681-4bfd-a038-216baea6da1f");
        body.put("busType","1");
        body.put("totalFee","0.01");
        body.put("optType","1");
        list.add(body);
       /*
       NetPostApi.post(baseActivity, head, body, String.class, new BaseObserver<String>() {
            @Override
            public void onHandlePrePare(Disposable d) {
                showLoadingDialog();
            }

            @Override
            protected void onHandleSuccess(String value) {

                if (value != null) {
                    PayManager payManager = new PayManager(baseActivity, new PayResultListener() {
                        @Override
                        public void start(String payType, String appId, String payInfo) {

                        }

                        @Override
                        public void success(String payType, PayResult resultVo) {
                            taskQueryPay(orderNo);

                        }

                        @Override
                        public void error(String payType, PayResult resultVo) {

                        }

                        @Override
                        public void cancel(String payType, PayResult resultVo) {

                        }

                        @Override
                        public void unknow(String payType, PayResult resultVo) {
                            taskQueryPay(orderNo);
                        }
                    });
                    PayWayVo payWayVo = payWayList.get(payWayList.indexOf(new PayWayVo(curPayType)));
                    if(payWayVo != null && payWayVo.ifJbf()){
                        payManager.pay(PayTypeDic.TYPE_HBPAY, value);
                    }else
                    if (TextUtils.equals(curPayType, PayNetTypeDic.ALI_PAY)) {
                        payManager.pay(PayTypeDic.TYPE_ALI, value);
                    } else if (TextUtils.equals(curPayType, PayNetTypeDic.WE_CHAT_PAY)) {
                        payManager.pay(PayTypeDic.TYPE_WEIXIN, value);
                    }
                }else {
                    taskQueryPay(orderNo);
                }
            }

            @Override
            protected void onHandleError(String errorType, String msg) {
                if(TextUtils.equals("1001", errorType)){
                    taskQueryPay(orderNo);
                }else if(TextUtils.equals("1002", errorType)){
                    ToastUtil.toast(msg);
                }else {
                    showErrorToast(errorType, msg);
                }
            }

            @Override
            protected void onHandleComplete() {
                dismissLoadingDialog();
            }
        });*/
    }










    private void getUpLoad() {
        ArrayMap<String, String> head = new ArrayMap<>();
       // head.put(HttpConstants.Head_Token, AccountSharpref.getInstance().getAccessToken());

        ArrayMap<String, String> param = new ArrayMap<>();

        ArrayList<String> filePath = new ArrayList<>();
  //      filePath.add(ImageUrlUtil.getRealPathFromUri(activity, uri));
      //  NetUpLoadApi.getInstance().upload();
//        UploadManager.getInstance().upload(MyFragment.this, NetConstants.httpApiUrl + "file/header",
//                head, param, filePath, HeadResponse.class, new BaseUpLoadObserver<HeadResponse>() {
//                    @Override
//                    protected void onHandlePrePare(Disposable disposable) {
//                        showLoadingDialog();
//
//                    }
//
//                    @Override
//                    protected void onHandleUploading(Uploading uploading) {
//
//                    }
//
//                    @Override
//                    protected void onHandleSuccess(HeadResponse coreResponse) {
//                        UserInfoVo infoVo = LocalDataUtil.getInstance().getUserInfo();
//                        infoVo.avatar = coreResponse.getDetails();
//                        LocalDataUtil.getInstance().setUserInfo(infoVo);
//                        WiseCommonGlideUtil.getInstance().loadHeadCircleById(MyFragment.this, ivHead, infoVo.avatar,
//                                R.mipmap.wise_common_head_def);
//                    }
//
//                    @Override
//                    protected void onHandleError(String s, String s1) {
//
//                    }
//
//                    @Override
//                    protected void onHandleComplete() {
//                        dismissLoadingDialog();
//                    }
//                });

    }

    private void getDownLoad() {
      //  String picUrl = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1529936880735&di=b77f8f4559636a1afb04fe46b6b2604b&imgtype=0&src=http%3A%2F%2Fg.hiphotos.baidu.com%2Fimage%2Fpic%2Fitem%2F4610b912c8fcc3cec52444bf9e45d688d53f2051.jpg";
        String picUrl="timg?image&quality=80&size=b9999_10000&sec=1529936880735&di=b77f8f4559636a1afb04fe46b6b2604b&imgtype=0&src=http%3A%2F%2Fg.hiphotos.baidu.com%2Fimage%2Fpic%2Fitem%2F4610b912c8fcc3cec52444bf9e45d688d53f2051.jpg";
        NetDownLoadApi.getService(DownloadService.class).downloadFile(picUrl).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())

                .subscribe(new Observer<ResponseBody>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ResponseBody responseBody) {

                        File file = new File(AppUtil.getStoreImageDir("ad") + "ad.png");
                        InputStream inputStream = null;
                        OutputStream outputStream = null;

                        try {
                            inputStream = responseBody.byteStream();
                            outputStream = new FileOutputStream(file);

                            byte[] fileReader = new byte[4096];

                            while (true) {
                                int byteCount = inputStream.read(fileReader);
                                if (byteCount == -1) {
                                    break;
                                }
                                outputStream.write(fileReader, 0, byteCount);
                            }
                            outputStream.flush();
                        } catch (IOException e) {
                            e.printStackTrace();
                        } finally {
                            try {
                                if (inputStream != null) {
                                    inputStream.close();
                                }
                                if (outputStream != null) {
                                    outputStream.close();
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onComplete() {
                        Bitmap bm = BitmapFactory.decodeFile(AppUtil.getStoreImageDir("ad") + "ad.png");
                      //  ImageView ivAd = findViewById(R.id.ivAd);
                        iv.setImageBitmap(bm);
                    }
                });
    }
    private void configTask() {
        ArrayList<Object> list = new ArrayList<Object>();
        ArrayMap<String, String> head = new ArrayMap<>();
        ArrayMap<String, Object> body = new ArrayMap<String, Object>();
        head.put(HttpConstants.Head_Id, "hcn.baseLevelConfigService");
        head.put(HttpConstants.Head_Method, "getCopywriterByCode");
        list.add(body);

        body.put("orgId", "");
        body.put("tenantId", "hcn.dongtai");
        body.put("configurationId", "0119-02");


        NetPostApi.getInstance().post(HttpConstants.REQUEST_URL, head, list, ConfigContentVo.class, new BaseObserver<ConfigContentVo>() {

            @Override
            public void onHandlePrePare(Disposable d) {

            }

            @Override
            protected void onHandleSuccess(ConfigContentVo value) {

                Log.d("SUCCESS", "ConfigContentVo:" + value.toString());

                sample_text.setText(value.getCopywriterTitle());
            }

            @Override
            protected void onHandleError(Throwable e) {
                Toast.makeText(HttpActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("Error", "Message:" + e.getMessage());
            }


            @Override
            protected void onHandleComplete() {

            }
        });


    }


    private void loginT(){
        ArrayMap<String, Object> body = new ArrayMap<String, Object>();

        body.put("tenantId", "hcn.dongtai");
        body.put("loginName", "13972626226");
        body.put("rid", "patient");
        body.put("forAccessToken", true);
        body.put("pwd", MD5.getMD5("123456"));  // 123456
        body.put("loginType", "1");
        NetPostApi.getInstance().getService(NewsApiInterface.class).getNewsChannels( "login", new ArrayMap<String, String>(), body)
                .compose(NetPostApi.getInstance().applySchedulers(new BaseObserverx<LoginResponse>() {

                    @Override
                    public void onHandlePrePare(Disposable d) {

                    }

                    @Override
                    protected void onHandleSuccess(LoginResponse value) {

                        Log.e("MainActivity", new Gson().toJson(value));
                        sample_text.setText(value.getProperties().get("accessToken"));
                    }

                    @Override
                    protected void onHandleError(Throwable e) {
                        Log.e("Error", "Message:" + e.getMessage());
                    }

                    @Override
                    protected void onHandleComplete() {

                    }
                }));

    }



    private void login() {

        ArrayMap<String, Object> body = new ArrayMap<String, Object>();

        body.put("tenantId", "hcn.dongtai");
        body.put("loginName", "13972626226");
        body.put("rid", "patient");
        body.put("forAccessToken", true);
        body.put("pwd", MD5.getMD5("123456"));  // 123456
        body.put("loginType", "1");

        NetPostApi.getInstance().post("login", new ArrayMap<String, String>(), body, LoginResponse.class, new BaseObserver2<LoginResponse>() {

            @Override
            public void onHandlePrePare(Disposable d) {

            }

            @Override
            protected void onHandleSuccess(LoginResponse value) {

                Log.d("SUCCESS", "LoginUserVo:" + value.toString());

                sample_text.setText(value.getProperties().get("accessToken"));

            }

            @Override
            protected void onHandleError(Throwable e) {
                Log.e("Error", "Message:" + e.getMessage());
                Toast.makeText(HttpActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }


            @Override
            protected void onHandleComplete() {

            }
        });


    }

    private void getList() {
        ArrayList<Object> list = new ArrayList<Object>();
        ArrayMap<String, String> head = new ArrayMap<>();
        ArrayMap<String, Object> body = new ArrayMap<String, Object>();
        head.put(HttpConstants.Head_Id, "hcn.payTrade");
        head.put(HttpConstants.Head_Method, "getPayTradeHistory");
        list.add(1);
        list.add(5);
        NetPostApi.getInstance().postList(HttpConstants.REQUEST_URL, head, list, TransactionRecordVo.class, new BaseObserver<ArrayList<TransactionRecordVo>>() {

            @Override
            public void onHandlePrePare(Disposable d) {
                //    showLoadingDialog();
                //   sample_text.setText("hhxxxxxxxxxxxhhhh");
            }

            @Override
            protected void onHandleSuccess(ArrayList<TransactionRecordVo> value) {
                // ToastUtil.showLong(value.size()+"  =="+ JSONArray.toJSON(value));
                sample_text.setText(value.size() + "==");
            }


            @Override
            protected void onHandleError(Throwable e) {
                Log.e("Error", "Message:"+e.getCause() + e.getMessage());
                Toast.makeText(HttpActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }


            @Override
            protected void onHandleComplete() {

            }
        });

    }

}
