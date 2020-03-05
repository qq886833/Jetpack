package com.bsoft.libcommon.utils;

import androidx.core.content.ContextCompat;
import com.bsoft.libbasic.context.ContextProvider;

/**
 * 应用模块:
 * <p>
 * 类描述:
 * <p>
 *
 * @author darryrzhoong
 * @since 2020-02-26       ColorUtils.getColor(this, R.color.main_bottom_check_color))
 *
 */
public class ColorUtils {
    public static int getColor(int colorId){
       return ContextCompat.getColor(ContextProvider.get().getContext(),colorId);
    }
}
