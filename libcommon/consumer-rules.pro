# keep annotated by NotProguard
-keep @top.andnux.proguard.annotation.Keep class * {*;}
-keep class * {
    @top.andnux.proguard.annotation.Keep <fields>;
}
-keepclassmembers class * {
    @top.andnux.proguard.annotation.Keep <methods>;
}


# 不混淆拥有以下注解的方法
-keepclassmembers class * {
    @com.bsoft.libcommon.commonaop.permission.annotation.PermissionCancel <methods>;
    @com.bsoft.libcommon.commonaop.permission.annotation.PermissionDenied <methods>;
    @com.bsoft.libcommon.commonaop.permission.annotation.PermissionNeed <methods>;
}
