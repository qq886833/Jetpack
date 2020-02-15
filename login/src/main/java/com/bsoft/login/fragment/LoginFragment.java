package com.bsoft.login.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import com.bsoft.libbasic.base.fragment.CoreFragment;
import com.bsoft.libbasic.utils.ToastUtil;
import com.bsoft.libcommon.arouter.CommonArouterGroup;
import com.bsoft.libcommon.livedatabus.LiveEventBusKey;
import com.bsoft.login.R;
import com.bsoft.login.databinding.LoginFragmentAccountLoginBinding;
import com.jeremyliao.liveeventbus.LiveEventBus;
import com.qmuiteam.qmui.util.QMUIViewHelper;

public class LoginFragment extends CoreFragment implements View.OnClickListener {

    private LoginFragmentAccountLoginBinding mBinding;


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
//        mBinding.btRegister.setOnClickListener(this);
//        mBinding.btForgetPwd.setOnClickListener(this);
//        mBinding.btLogin.setOnClickListener(this);
      //  initTopBar();
        mBinding.topbar.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.transparent));
        mBinding.topbar.setBottomDividerAlpha(0);
        mBinding.topbar.addLeftImageButton(R.drawable.icon_close_black, QMUIViewHelper.generateViewId()).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtil.showShort("111");
            }
        });


        mBinding.topbar.addRightTextButton("切换地址", QMUIViewHelper.generateViewId()).setOnClickListener(view1 ->
                {
                    CommonArouterGroup.gotoActivity(CommonArouterGroup.PATH_CHANGE_NET_ACTIVITY);
                }
              );
        mBinding.tvLogin.setOnClickListener(view1 ->
        {
            LiveEventBus.get(LiveEventBusKey.KEY_LOGIN).post(true);

        });
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
//        if (id == R.id.bt_register) {
//
//            Navigation.findNavController(getView()).navigate(R.id.action_loginFragment_to_registerFragment,new Bundle());
//        } else if (id == R.id.bt_forget_pwd) {
//            Bundle bundle =     new Bundle();
//            bundle.putString("arouter","2");
//            Navigation.findNavController(getView()).navigate(R.id.action_loginFragment_to_frogetPwdFragment,bundle);
//        } else if (id == R.id.bt_login) {
//            ARouter.getInstance().build(MAIN_TAB_ACTIVITY).navigation();
//            getActivity().finish();
//        }
    }
}
