package com.bsoft.libbasic.thirdpart.fastsharedpreferences;

import android.content.SharedPreferences;

import java.io.Serializable;

/**
 * Created by liaohailiang on 2018/10/25.
 */
public interface EnhancedSharedPreferences extends SharedPreferences {

    Serializable getSerializable(String key, @androidx.annotation.Nullable Serializable defValue);
}
