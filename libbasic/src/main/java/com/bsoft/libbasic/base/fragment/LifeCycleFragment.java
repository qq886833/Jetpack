package com.bsoft.libbasic.base.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.bsoft.libbasic.utils.log.LogUtil;





public class LifeCycleFragment extends Fragment {
    public static final String TAG = "Fragment";



    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        LogUtil.i(TAG,"${this::class.simpleName} onAttach");
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogUtil.i(TAG,"${this::class.simpleName} onCreate");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        LogUtil.i(TAG,"${this::class.simpleName} onCreateView");
        return super.onCreateView(inflater, container, savedInstanceState);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        LogUtil.i(TAG,"${this::class.simpleName} onViewCreated");
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        LogUtil.i(TAG,"${this::class.simpleName} onActivityCreated");
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        LogUtil.i(TAG,"${this::class.simpleName} onViewStateRestored");
    }

    @Override
    public void onStart() {
        super.onStart();
        LogUtil.i(TAG,"${this::class.simpleName} onStart");
    }

    @Override
    public void onResume() {
        super.onResume();
        LogUtil.i(TAG,"${this::class.simpleName} onResume");
    }

    @Override
    public void onPause() {
        super.onPause();
        LogUtil.i(TAG,"${this::class.simpleName} onPause");
    }

    @Override
    public void onStop() {
        super.onStop();
        LogUtil.i(TAG,"${this::class.simpleName} onStop");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        LogUtil.i(TAG,"${this::class.simpleName} onDestroyView");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LogUtil.i(TAG,"${this::class.simpleName} onDestroy");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        LogUtil.i(TAG,"${this::class.simpleName} onDetach");
    }


    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        LogUtil.i(TAG,"${this::class.simpleName} onHiddenChanged");
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        LogUtil.i(TAG,"${this::class.simpleName} onActivityResult");
    }

    @Override
    public void onInflate(@NonNull Context context, @NonNull AttributeSet attrs, @Nullable Bundle savedInstanceState) {
        super.onInflate(context, attrs, savedInstanceState);
        LogUtil.i(TAG,"${this::class.simpleName} onInflate");
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        LogUtil.i(TAG,"${this::class.simpleName} onSaveInstanceState");
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        LogUtil.i(TAG,"${this::class.simpleName} onConfigurationChanged");
    }


}
