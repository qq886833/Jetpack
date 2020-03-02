package com.bsoft.login.fragment;

import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextWatcher;
import android.text.method.DigitsKeyListener;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.EditText;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import com.bsoft.libbasic.base.fragment.CoreFragment;
import com.bsoft.libbasic.constant.HttpConstants;
import com.bsoft.libbasic.utils.DensityUtil;
import com.bsoft.libbasic.utils.ToastUtil;
import com.bsoft.libcommon.arouter.AppRouterService;
import com.bsoft.libcommon.arouter.CommonArouterGroup;
import com.bsoft.libcommon.arouter.RouteServiceManager;
import com.bsoft.libcommon.baseservices.ILoginService;
import com.bsoft.libcommon.commonaop.SingleClick;
import com.bsoft.libcommon.livedatabus.LiveEventBusKey;
import com.bsoft.libcommon.utils.KeyboardStatusDetector;
import com.bsoft.libcommon.utils.NavBarUtil;
import com.bsoft.libcommon.utils.SoftKeyboardUtils;
import com.bsoft.login.R;
import com.bsoft.login.adapter.UserAdapter;
import com.bsoft.login.databinding.LoginFragmentAccountLoginBinding;
import com.jeremyliao.liveeventbus.LiveEventBus;
import com.qmuiteam.qmui.util.QMUIViewHelper;

import java.util.ArrayList;

public class LoginFragment extends CoreFragment implements View.OnClickListener {


    private LoginFragmentAccountLoginBinding mBinding;
    private EditText etUser;
    private String digitsTxt;
    private String digitsNumber;
    private UserAdapter userAdapter;
    private KeyboardStatusDetector keyboardStatusDetector;

    private static ILoginService mLoginService = RouteServiceManager.provide(ILoginService.class, AppRouterService.APP_LOGIN_SERVICE);


    public static LoginFragment newInstance(String actPath, Bundle dataBundle, boolean isSSO) {
        LoginFragment fragment = new LoginFragment();
        Bundle bundle = new Bundle();
       // bundle.putString(PATH, actPath);
      //  bundle.putBundle(PARAM, dataBundle);
      //  bundle.putBoolean(EXTRA_IS_SSO, isSSO);
        fragment.setArguments(bundle);
        return fragment;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = LoginFragmentAccountLoginBinding.inflate(inflater, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mBinding.ivMore.setOnClickListener(this);
        mBinding.ivUserclear.setOnClickListener(this);
        mBinding.ivSecret.setOnClickListener(this);
        //  initTopBar();
        mBinding.topbar.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.transparent));
        mBinding.topbar.setBottomDividerAlpha(0);
        mBinding.topbar.addLeftImageButton(R.drawable.icon_close_black, QMUIViewHelper.generateViewId()).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtil.showShort("111");
            }
        });

        if(HttpConstants.isDebug) {
            mBinding.topbar.addRightTextButton("切换地址", QMUIViewHelper.generateViewId()).setOnClickListener(view1 ->
                    {
                        CommonArouterGroup.gotoActivity(CommonArouterGroup.PATH_CHANGE_NET_ACTIVITY);
                    }
            );
        }
        mBinding.tvLogin.setOnClickListener(view1 ->
        {
            LiveEventBus.get(LiveEventBusKey.KEY_LOGIN).post(true);

        });

        mBinding.cbIfcansee.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (  mBinding.cbIfcansee.isChecked()) {
                    mBinding.etPwd.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                } else {
                    mBinding.etPwd.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }
                mBinding.etPwd.requestFocus();
                mBinding.etPwd.setSelection(  mBinding.etPwd.getText().length());

            }
        });

        digitsTxt = getString(R.string.digits_idcard);
        digitsNumber = getString(R.string.digits_number);
        etUser = mBinding.userInput.getEditText();
        etUser.setHint("请输入用户名");
        etUser.setFilters(new InputFilter[]{new InputFilter.LengthFilter(11)});
        etUser.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimensionPixelOffset(R.dimen.sp_14));
        etUser.setLines(1);
        etUser.setTextColor(ContextCompat.getColor(getActivity(), R.color.black));
        etUser.setHintTextColor(ContextCompat.getColor(getActivity(), R.color.color_666));
        etUser.setKeyListener(DigitsKeyListener.getInstance(digitsNumber));


        userAdapter = new UserAdapter(new UserAdapter.OnItemViewClickListener() {
            @Override
            public void onItemDelListener(String name) {
             //   LoginManager.getInstance().delUserList(name);
            }
        });


     //   ArrayList<String> userList = LoginManager.getInstance().getUserList();
        ArrayList<String> userList=new ArrayList<>();
        //医生端暂不需要多账号登录保留功能
        mBinding.ivMore.setVisibility(userList == null || userList.size() <= 1 ? View.GONE : View.VISIBLE);
        userAdapter.setData(userList);
        mBinding.userInput.setAdapter(userAdapter);
        mBinding.userInput.post(new Runnable() {
            @Override
            public void run() {
                userAdapter.setWidth(mBinding.userInput.getMeasuredWidth());

            }
        });

        etUser.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }

            @Override
            public void afterTextChanged(Editable arg0) {
                if (etUser.getText().toString().length() == 0) {
                    mBinding.ivUserclear.setVisibility(View.GONE);
                } else {
                    mBinding.ivUserclear.setVisibility(View.VISIBLE);
                }

            }
        });

        mBinding.mainView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View arg0, MotionEvent arg1) {
                SoftKeyboardUtils.hideSoftKeyboard(getActivity());
                return false;
            }
        });

        keyboardStatusDetector = new KeyboardStatusDetector();

        keyboardStatusDetector.registerFragment(this).setVisibilityListener(new KeyboardStatusDetector.KeyboardVisibilityListener() {
            @Override
            public void onVisibilityChanged(boolean keyboardVisible) {
                if (keyboardVisible) {
                    mBinding.layBottom.setVisibility(View.INVISIBLE);
                    mBinding.layTop.animate().translationY(DensityUtil.dip2px(80) / 2 * -1);
                } else {
                    mBinding.layTop.animate().translationY(0);
                    mBinding.layBottom.setVisibility(View.VISIBLE);
                }
            }
        });
        setBottomH();

        mBinding.tvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mLoginService.checkLogin(true);
            }
        });

    }



    void setBottomH() {
        int virtualBarHeight = NavBarUtil.getVirtualBarHeight(getActivity());
        if (virtualBarHeight > 0) {
            mBinding.layBottom.setPadding(mBinding.layBottom.getPaddingLeft(),
                    mBinding.layBottom.getPaddingTop(), mBinding.layBottom.getPaddingRight(),
                    mBinding.layBottom.getPaddingBottom() + virtualBarHeight);
        }
    }

    @Override
    @SingleClick
    public void onClick(View view) {
        int id = view.getId();
        if ( view.getId() == R.id.iv_userclear) {
            etUser.setText("");
            etUser.requestFocus();
            //showKeyboard(etUser);
            SoftKeyboardUtils.showSystemKeyBord(getActivity(),etUser);
            // Navigation.findNavController(getView()).navigate(R.id.action_loginFragment_to_registerFragment,new Bundle());
        } else if ( view.getId() == R.id.ivMore) {
            mBinding.userInput.performDropClick();
//            Bundle bundle =     new Bundle();
//            bundle.putString("arouter","2");
//            Navigation.findNavController(getView()).navigate(R.id.action_loginFragment_to_frogetPwdFragment,bundle);
        } else if ( view.getId() == R.id.bt_login) {
//            ARouter.getInstance().build(MAIN_TAB_ACTIVITY).navigation();
//            getActivity().finish();
        }
    }
}
