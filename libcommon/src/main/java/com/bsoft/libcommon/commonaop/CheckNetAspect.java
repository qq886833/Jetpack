package com.bsoft.libcommon.commonaop;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.View;
import android.widget.Toast;
import androidx.annotation.Keep;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

/**
 * Created by hcDarren on 2017/8/27.
 * 处理切点
 */
@Keep
@Aspect
public class CheckNetAspect {

    /**
     * 找到处理的切点
     * * *(..)  可以处理所有的方法
     */
    @Pointcut("execution(@com.bsoft.libcommon.commonaop.CheckNet * *(..))")
    public void checkNetBehavior() {

    }

    /**
     * 处理切面
     */
    @Around("checkNetBehavior()")
    public Object checkNet(ProceedingJoinPoint joinPoint) throws Throwable {

        // 做埋点  日志上传  权限检测（我写的，RxPermission , easyPermission） 网络检测
        // 网络检测
        // 1.获取 CheckNet 注解  NDK  图片压缩  C++ 调用Java 方法
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        CheckNet checkNet = signature.getMethod().getAnnotation(CheckNet.class);
        if (checkNet != null) {
            // 2.判断有没有网络  怎么样获取 context?
            Context context =null;
            Object object = joinPoint.getThis();// View Activity Fragment ； getThis() 当前切点方法所在的类
            context = getContext(object);
            if(context==null){  //  public void onClick(View v)  获取上下文
                for(Object arg : joinPoint.getArgs()){
                    if(arg instanceof View){
                        context=((View) arg).getContext();
                        break;
                    }
                }
            }
          //  Log.e("TAG", "checkNet"+isNetworkAvailable(context));
            if (context != null) {
                if (!isNetworkAvailable(context)) {
                    // 3.没有网络不要往下执行
                    Toast.makeText(context,"请检查您的网络", Toast.LENGTH_LONG).show();
                    return null;
                }
            }
        }
        return joinPoint.proceed();
    }

    /**
     * 通过对象获取上下文
     *
     * @param object
     * @return
     */
    private Context getContext(Object object) {
        if (object instanceof Activity) {
            return (Activity) object;
        } else if (object instanceof Fragment) {
            Fragment fragment = (Fragment) object;
            return fragment.getActivity();
        } else if (object instanceof View) {
            View view = (View) object;
            return view.getContext();
        }
        return null;
    }

    /**
     * 检查当前网络是否可用
     *
     * @return
     */
    private static boolean isNetworkAvailable(Context context) {
        // 获取手机所有连接管理对象（包括对wi-fi,net等连接的管理）
        ConnectivityManager connectivityManager = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            // 获取NetworkInfo对象
            NetworkInfo[] networkInfo = connectivityManager.getAllNetworkInfo();

            if (networkInfo != null && networkInfo.length > 0) {
                for (int i = 0; i < networkInfo.length; i++) {
                    // 判断当前网络状态是否为连接状态
                    if (networkInfo[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}

  /*
    /**
     * 这里定义了一个总的匹配规则，以后拦截的时候直接拦截log()方法即可，无须去重复写execution表达式
     *//*
    @Pointcut("@annotation(Axin)")
    public void log() {
    }

    @Before("log()&&@annotation(axin)")
    public void doBefore(JoinPoint joinPoint,Axin axin) {
        System.out.println("******拦截前的逻辑******");
        System.out.println("目标方法名为:" + joinPoint.getSignature().getName());
        System.out.println("目标方法所属类的简单类名:" + joinPoint.getSignature().getDeclaringType().getSimpleName());
        System.out.println("目标方法所属类的类名:" + joinPoint.getSignature().getDeclaringTypeName());
        System.out.println("目标方法声明类型:" + Modifier.toString(joinPoint.getSignature().getModifiers()));
        //获取传入目标方法的参数
        Object[] args = joinPoint.getArgs();
        for (int i = 0; i < args.length; i++) {
            System.out.println("第" + (i + 1) + "个参数为:" + args[i]);
        }
        System.out.println("被代理的对象:" + joinPoint.getTarget());
        System.out.println("代理对象自己:" + joinPoint.getThis());

        System.out.println("拦截的注解的参数：");
        System.out.println(axin.module());
        System.out.println(axin.desc());
    }

    @Around("log()&&@annotation(axin)")
    public Object doAround(ProceedingJoinPoint proceedingJoinPoint,Axin axin) throws Throwable {
        System.out.println("环绕通知：");
        System.out.println(axin.module());
        System.out.println(axin.desc());
        Object result = null;
        result = proceedingJoinPoint.proceed();
        return result;
    }

    @After("log()")
    public void doAfter() {
        System.out.println("******拦截后的逻辑******");
    }
}*/