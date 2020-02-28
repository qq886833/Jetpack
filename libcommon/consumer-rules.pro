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



##################### bean ######################
# Explicitly preserve all serialization members. The Serializable interface
# is only a marker interface, so it wouldn't save them.
-keepclassmembers class * implements java.io.Serializable {
    static final long serialVersionUID;
    private static final java.io.ObjectStreamField[] serialPersistentFields;
    private void writeObject(java.io.ObjectOutputStream);
    private void readObject(java.io.ObjectInputStream);
    java.lang.Object writeReplace();
    java.lang.Object readResolve();
}

-keep public class * implements java.io.Serializable {*;}
-dontobfuscate
-dontoptimize

-keepclassmembers enum * {
 public static **[] values();
 public static ** valueOf(java.lang.String);
}

################## arouter ################
-keep public class com.alibaba.android.arouter.routes.**{*;}
-keep public class com.alibaba.android.arouter.facade.**{*;}
-keep class * implements com.alibaba.android.arouter.facade.template.ISyringe{*;}

# 如果使用了 byType 的方式获取 Service，需添加下面规则，保护接口
-keep interface * implements com.alibaba.android.arouter.facade.template.IProvider

# 如果使用了 单类注入，即不定义接口实现 IProvider，需添加下面规则，保护实现
# -keep class * implements com.alibaba.android.arouter.facade.template.IProvider

