package com.bsoft.libcommon.commonaop;

import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import androidx.annotation.Keep;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.reflect.Method;


/**
 * https://www.jianshu.com/p/a57fe84666be
 * date  : Created by jinyd on .
 * describe :
 */
@Keep
@Aspect
public class SingleClickAspect {

    /**
     * 定义切点，标记切点为所有被@SingleClick注解的方法
     * 注意：这里me.baron.test.annotation.SingleClick需要替换成
     * 你自己项目中SingleClick这个类的全路径哦
     */
    @Pointcut("execution(@com.bsoft.libcommon.commonaop.SingleClick * *(..))")
  //  @Pointcut("execution(* android.view.View.OnClickListener.onClick(..))")
    public void methodAnnotated() {}

    /**
     * 定义一个切面方法，包裹切点方法
     */
    @Around("methodAnnotated()")
    public void singleClickJoinPoint(ProceedingJoinPoint joinPoint) throws Throwable {
        // 取出方法的参数
        View view = null;
        for (Object arg : joinPoint.getArgs()) {
            if (arg instanceof View) {
                view = (View) arg;
                break;
            }
        }
        if (view == null) {
            return;
        }
        // 取出方法的注解
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();
        if (!method.isAnnotationPresent(SingleClick.class)) {
            return;
        }
        SingleClick singleClick = method.getAnnotation(SingleClick.class);
        if(singleClick!=null) {
            // 判断是否快速点击
            if (!isFastDoubleClick(view, singleClick.value())) {
                // 不是快速点击，执行原方法
                joinPoint.proceed();
            }
        }
    }





    /**
     * 最近一次点击的时间
     */
    private static long mLastClickTime;
    /**
     * 最近一次点击的控件ID
     */
    private static int mLastClickViewId;

    /**
     * 是否是快速点击
     *
     * @param v  点击的控件
     * @param intervalMillis  时间间期（毫秒）
     * @return  true:是，false:不是
     */
    public static boolean isFastDoubleClick(View v, long intervalMillis) {
        int viewId = v.getId();
//        long time = System.currentTimeMillis();
        long time = SystemClock.elapsedRealtime();
        long timeInterval = Math.abs(time - mLastClickTime);
        if (timeInterval < intervalMillis && viewId == mLastClickViewId) {
            Log.e("isFastDoubleClick", "true");
            return true;
        } else {
            mLastClickTime = time;
            mLastClickViewId = viewId;
            Log.e("isFastDoubleClick", "false");
            return false;
        }
    }
}
