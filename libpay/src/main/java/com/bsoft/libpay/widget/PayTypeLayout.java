package com.bsoft.libpay.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.collection.ArrayMap;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.bsoft.libbasic.constant.HttpConstants;
import com.bsoft.libnet.api.NetPostApi;
import com.bsoft.libnet.observer.BaseObserver;
import com.bsoft.libpay.BasePayActivity;
import com.bsoft.libpay.R;
import com.bsoft.libpay.model.PayTypeVo;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import io.reactivex.disposables.Disposable;

import java.util.ArrayList;
import java.util.List;

public class PayTypeLayout  extends LinearLayout {

    private Context mContext;
    private BasePayActivity mActivity;
    public List<PayTypeVo> getPayTypeList() {
        return mList;
    }

    private List<PayTypeVo> mList = new ArrayList<PayTypeVo>();
    private int mSelectPositon=-1 ;
    private boolean mIsViewShown;
    private PayTypeAdapter mPayTypeAdapter;
    private List<String> mPayTypeList;
    private Disposable mDisposable;

    public PayTypeLayout(Context context) {
        this(context, null);
    }

    public PayTypeLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);

    }

    public PayTypeLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        init();
    }
    private void init() {
        View rootView =  LayoutInflater.from(mContext).inflate(R.layout.pay_layout_pay_type_select, this);
       // inflate(mContext, R.layout.pay_layout_pay_type_select, this);
        mPayTypeList = new ArrayList<>();
        setAppPayTypeData();
        RecyclerView recyclerView = rootView.findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));

       // mPayTypeAdapter.addChildClickViewIds(R.id.img, R.id.tweetName, R.id.tweetText);
        mPayTypeAdapter = new PayTypeAdapter(mList);
        mPayTypeAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                PayTypeVo item = (PayTypeVo) adapter.getItem(position);
                for(PayTypeVo vo : (List<PayTypeVo> )adapter.getData()){
                    vo.isSelected = false;
                }
                item.isSelected = true;
                mSelectPositon=position;
                mPayTypeAdapter.notifyDataSetChanged();
            }
        });

        recyclerView.setAdapter(mPayTypeAdapter);
        //防止RecyclerView截断纵向滑动(也可在RecyclerView的属性中添加android:nestedScrollingEnabled="false")
        recyclerView.setNestedScrollingEnabled(false);
    }

    private void setAppPayTypeData() {
        mPayTypeList.add("2");
        mPayTypeList.add("3");
        mPayTypeList.add("4");
        mPayTypeList.add("6-2");
        mPayTypeList.add("6-3");
        mPayTypeList.add("6-4");
    }


    //        hospitalCode	String		Y	医院代码(健康城市传orgId)
//        busType	String		Y	业务类型id
//             1.预约挂号 2.诊间支付 3.门诊账户充值 4.住院预缴金 5.出院结算 6.图文咨询(在线问诊) 7.复诊配药 8复诊处方支付 9云诊室支付 10病历复印 11 体检预约  12 家医签约
//        totalFee	double		N	支付金额
//        optType	String		N	1 充值 2 支付

   // public void takePayType(BasePayActivity activity,String orgId, String busType, String price, String optType) {
   public void takePayType(String orgId, String busType, String price, String optType) {
   //     this.mActivity=activity;
        ArrayList<Object> list = new ArrayList<Object>();
        ArrayMap<String, String> head = new ArrayMap<>();
        ArrayMap<String, Object> body = new ArrayMap<String, Object>();
        head.put(HttpConstants.Head_Id, "hcn.payTradeV2");
        head.put(HttpConstants.Head_Method, "getOrgPayInfo");

        body.put("hospitalCode",orgId);
        body.put("busType",busType);
        body.put("totalFee",price);
        body.put("optType",optType);
        list.add(body);



        NetPostApi.getInstance().postList(HttpConstants.REQUEST_URL, head, list, PayTypeVo.class, new BaseObserver<ArrayList<PayTypeVo>>() {

            @Override
            public void onHandlePrePare(Disposable d) {
                if(mActivity!=null){
                    mActivity.showLoadingDialog();
                }

                mDisposable = d;
            }

            @Override
            protected void onHandleSuccess(ArrayList<PayTypeVo> value) {
                Log.e("mIsViewShown" ,mIsViewShown+"==");
                if (mIsViewShown && (value != null && value.size()>0 )) {
                    mList.clear();
                    for (PayTypeVo payTypeVo : value) {
                        if (mPayTypeList.contains(payTypeVo.payType)) {
                            mList.add(fillPayTypeIcon(payTypeVo));
                        }
                    }
                    mPayTypeAdapter.notifyDataSetChanged();
                }


            }

            @Override
            protected void onHandleError(Throwable e) {
                if(mActivity!=null){
                    mActivity.dismissLoadingDialog();
                }
                Toast.makeText(mContext, e.getMessage(), Toast.LENGTH_SHORT).show();
            }


            @Override
            protected void onHandleComplete() {
                if(mActivity!=null){
                    mActivity.dismissLoadingDialog();
                }
            }
        });



    }


    /**
     * 填充payType的icon图标
     *
     * @param payTypeVo
     */
    private PayTypeVo fillPayTypeIcon(PayTypeVo payTypeVo) {
        switch (payTypeVo.payType) {
            case "2":
                payTypeVo.payIcon = R.drawable.pay_icon_ali;
                break;
            case "3":
                payTypeVo.payIcon = R.drawable.pay_icon_wechat;
                break;
            case "4":
                payTypeVo.payIcon = R.drawable.pay_icon_bank;
                break;
            case "6-2":
                payTypeVo.payIcon = R.drawable.pay_icon_ali;
                break;
            case "6-3":
                payTypeVo.payIcon = R.drawable.pay_icon_wechat;
                break;
            case "6-4":
                payTypeVo.payIcon = R.drawable.pay_icon_bank;
                break;

        }
        return payTypeVo;
    }


    public String getPayType() {

        return  (mSelectPositon!=-1)?mList.get(mSelectPositon).payType:null;
    }

    public PayTypeVo getPayTypeVo() {
        return  (mSelectPositon!=-1)?mList.get(mSelectPositon):null;
    }
    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (mDisposable != null && !mDisposable.isDisposed()) {
            mDisposable.dispose();
            if(mActivity!=null){
                mActivity=null;
            }
        }
    }

    @Override
    protected void onWindowVisibilityChanged(int visibility) {
        super.onWindowVisibilityChanged(visibility);

        if (visibility == View.VISIBLE) {
            mIsViewShown = true;
        } else {
            mIsViewShown = false;
        }

    }


}
