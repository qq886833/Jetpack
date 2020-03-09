package com.bsoft.login.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.bsoft.libbasic.base.fragment.CoreFragment;
import com.bsoft.libbasic.context.ContextProvider;
import com.bsoft.libbasic.utils.ToastUtil;
import com.bsoft.libcommon.arouter.AppRouterService;
import com.bsoft.libcommon.arouter.RouteServiceManager;
import com.bsoft.libcommon.baseservices.ILoginService;
import com.bsoft.libcommon.commonaop.SingleClick;
import com.bsoft.libcommon.utils.KeyboardStatusDetector;
import com.bsoft.libcommon.utils.SoftKeyboardUtils;
import com.bsoft.libcommon.utils.proxsp.AppConfigHandler;
import com.bsoft.libcommon.utils.proxsp.LoginConfig;
import com.bsoft.login.R;
import com.bsoft.login.adapter.UserAdapter;
import com.bsoft.login.databinding.LoginLayoutPwdAccountBinding;

public class LoginPwdFragment extends CoreFragment implements View.OnClickListener {


    private LoginLayoutPwdAccountBinding mBinding;
    private EditText etUser;
    private String digitsTxt;
    private String digitsNumber;
    private UserAdapter userAdapter;
    private KeyboardStatusDetector keyboardStatusDetector;

    private static ILoginService mLoginService = RouteServiceManager.provide(ILoginService.class, AppRouterService.APP_LOGIN_SERVICE);

    public static LoginPwdFragment newInstance() {
        LoginPwdFragment fragment = new LoginPwdFragment();
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
        mBinding = LoginLayoutPwdAccountBinding.inflate(inflater, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

       // mBinding.tvLogin.setChangeAlphaWhenPress(true);  //代码控制透明的
        mBinding.tvLogin.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        mLoginService.checkLogin(true);
        LoginConfig config = AppConfigHandler.create(ContextProvider.get().getApplication(),LoginConfig.class);
        config.clear();
        ToastUtil.showShort(mLoginService.isLogin()+"==");
    }
});

    }



    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
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
        } else if ( view.getId() == R.id.tv_login) {
          //  Navigation.findNavController(getView()).navigate(R.id.action_loginFragment_to_frogetPwdFragment,bundle);


        }
    }
}
