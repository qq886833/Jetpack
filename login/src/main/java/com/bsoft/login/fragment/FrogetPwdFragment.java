package com.bsoft.login.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.Navigation;
import com.bsoft.libbasic.base.fragment.CoreFragment;
import com.bsoft.libcommon.utils.StringUtil;
import com.bsoft.login.R;
import com.bsoft.login.databinding.LoginFragmentForgetPwdBinding;

public class FrogetPwdFragment extends CoreFragment {

    private LoginFragmentForgetPwdBinding mBinding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = LoginFragmentForgetPwdBinding.inflate(inflater, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mBinding.btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              String arouter= getArguments().getString("arouter");

                if(!StringUtil.isEmpty(arouter)&&arouter.equals("2")){
                    Navigation.findNavController(getView()).navigate(R.id.action_frogetPwdFragment_to_loginFragment,new Bundle());

                }else {
                    Navigation.findNavController(getView()).navigateUp();
                }

            }
        });
    }
}
