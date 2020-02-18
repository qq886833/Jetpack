package com.bsoft.libcommon.commonaop.permission.aop;


import android.content.Context;
import android.util.Log;
import com.bsoft.libbasic.context.ContextProvider;
import com.bsoft.libcommon.commonaop.permission.PermissionRequestActivity;
import com.bsoft.libcommon.commonaop.permission.annotation.PermissionCancel;
import com.bsoft.libcommon.commonaop.permission.annotation.PermissionDenied;
import com.bsoft.libcommon.commonaop.permission.annotation.PermissionNeed;
import com.bsoft.libcommon.commonaop.permission.util.PermissionUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class PermissionAspect {

    private Context context;

    // 定位 PermissionNeed 注解，并获取注解对象
    @Pointcut("execution(@com.bsoft.libcommon.commonaop.permission.annotation.PermissionNeed * *(..)) && @annotation(permissionNeed)")
    public void requestPermission(PermissionNeed permissionNeed) {

    }

    @Around("requestPermission(permissionNeed)")
    public void aroundJoinPoint(final ProceedingJoinPoint joinPoint, PermissionNeed permissionNeed) {
        final Object obj = joinPoint.getThis();

        if (obj instanceof Context) {
            context = (Context) obj;
        } else {
            Object[] args = joinPoint.getArgs();
            if (args.length > 0) {
                // 非静态方法且第一个参数为 context
                if (args[0] instanceof Context) {
                    context = (Context) args[0];
                } else {
                    context = ContextProvider.get().getApplication();
                }
            } else {
                context =  ContextProvider.get().getApplication();
            }
        }

        if (context == null || permissionNeed == null) {
            Log.e("leo", "aroundJoinPoint: 条件不满足，直接返回");
            return;
        }

        PermissionRequestActivity.startPermissionRequest(context, permissionNeed.value(),
                permissionNeed.requestCode(), new IPermission() {
                    @Override
                    public void onPermissionGranted() {
                        // 直接调用执行方法
                        try {
                            joinPoint.proceed();
                        } catch (Throwable throwable) {
                            throwable.printStackTrace();
                        }
                    }

                    @Override
                    public void onPermissionDenied(int requestCode) {
                        PermissionUtil.invokAnnotation(obj, PermissionDenied.class, requestCode);
                    }

                    @Override
                    public void onPermissionCanceled(int requestCode) {
                        PermissionUtil.invokAnnotation(obj, PermissionCancel.class, requestCode);
                    }
                });
    }


}
