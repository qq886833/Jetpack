package com.bsoft.pub.jetpack

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bsoft.libcommon.localdata.AccountSharpref
import com.bsoft.libmain.MainTabActivity
import kotlinx.android.synthetic.main.activity_main.*

//scrcpy
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Example of a call to a native method
        sample_text.text = stringFromJNI()

        // Toast.makeText(HttpActivity.this, payTypeLayout.getPayType(), Toast.LENGTH_LONG).show();
//                if(TextUtils.isEmpty(payTypeLayout.getPayType())){
//                    ToastUtil.showLong("请先选择支付方式");
//               }else{
//                    payUtil.pay(PayTypeDic.TYPE_ALI, "");
//                }
    //   startActivity(Intent(this, HttpActivity::class.java))
        AccountSharpref.getInstance().setLoginState(false)
        startActivity(Intent(this, MainTabActivity::class.java))

    }

    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    external fun stringFromJNI(): String

    companion object {

        // Used to load the 'native-lib' library on application startup.
        init {
            System.loadLibrary("native-lib")
        }
    }
}
