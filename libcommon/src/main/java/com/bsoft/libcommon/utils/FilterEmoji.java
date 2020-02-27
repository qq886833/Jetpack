package com.bsoft.libcommon.utils;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.Toast;

public class FilterEmoji implements TextWatcher {
    private EditText et;
    private Context context;
    public FilterEmoji(EditText et, Context context) {
        this.et = et;
        this.context = context;
    }


    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        //输入的类容
        CharSequence input = s.subSequence(start, start + count);
        // 退格
        if (count == 0) return;
        //如果输入的类容包含有Emoji
        if (isEmojiCharacter(input)){
            Toast.makeText(context.getApplicationContext(), "请不要输入emoji表情符号", Toast.LENGTH_SHORT).show();
            //那么就去掉
            et.setText(removeEmoji(s));
            //最后光标移动到最后
            et.setSelection(et.getText().toString().length());
        }


    }

    @Override
    public void afterTextChanged(Editable s){
    }

    //去除字符串中的Emoji表情
    private String removeEmoji(CharSequence source) {
        String result = "";
        for (int i = 0; i < source.length(); i++) {
            char c = source.charAt(i);
            if (isEmojiCharacter(c))
            {
                continue;
            }
            result += c;
        }
        return result;
    }

    //判断一个字符串中是否包含有Emoji表情
    private boolean isEmojiCharacter(CharSequence input) {
        for (int i = 0; i < input.length(); i++)
        {
            if (isEmojiCharacter(input.charAt(i)))
            {
                return true;
            }
        }
        return false;
    }

    //是否是Emoji表情
    public static boolean isEmojiCharacter(char codePoint) {
        // Emoji 范围
        boolean isScopeOf = (codePoint == 0x0) || (codePoint == 0x9) || (codePoint == 0xA) || (codePoint == 0xD)
                || ((codePoint >= 0x20) && (codePoint <= 0xD7FF) && (codePoint != 0x263a))
                || ((codePoint >= 0xE000) && (codePoint <= 0xFFFD))
                || ((codePoint >= 0x10000) && (codePoint <= 0x10FFFF));
        return !isScopeOf;
    }
}
