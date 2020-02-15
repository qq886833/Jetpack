package com.bsoft.login.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.Navigation;
import com.bsoft.libbasic.base.fragment.CoreFragment;
import com.bsoft.login.R;
import com.bsoft.login.databinding.LoginFragmentRegisterBinding;

public class RegisterFragment extends CoreFragment {

    private LoginFragmentRegisterBinding mBinding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = LoginFragmentRegisterBinding.inflate(inflater, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mBinding.btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle =     new Bundle();
                bundle.putString("arouter","1");
                Navigation.findNavController(getView()).navigate(R.id.action_registerFragment_to_frogetPwdFragment,bundle);

            }
        });
    }
}
