package com.bsoft.login.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.bsoft.libbasic.base.fragment.CoreFragment;
import com.bsoft.libcommon.arouter.AppRouterService;
import com.bsoft.libcommon.arouter.RouteServiceManager;
import com.bsoft.libcommon.baseservices.ILoginService;
import com.bsoft.libcommon.utils.KeyboardStatusDetector;
import com.bsoft.login.adapter.UserAdapter;
import com.bsoft.login.databinding.LoginLayoutCodeAccountBinding;

public class LoginCodeFragment extends CoreFragment  {


    private LoginLayoutCodeAccountBinding mBinding;
    private EditText etUser;
    private String digitsTxt;
    private String digitsNumber;
    private UserAdapter userAdapter;
    private KeyboardStatusDetector keyboardStatusDetector;

    private static ILoginService mLoginService = RouteServiceManager.provide(ILoginService.class, AppRouterService.APP_LOGIN_SERVICE);

    public static LoginCodeFragment newInstance() {
        LoginCodeFragment fragment = new LoginCodeFragment();
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
        mBinding = LoginLayoutCodeAccountBinding.inflate(inflater, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
      //  initTab();

    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

}
