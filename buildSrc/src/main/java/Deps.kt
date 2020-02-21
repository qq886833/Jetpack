import org.gradle.api.artifacts.dsl.RepositoryHandler
import java.net.URI

object Versions {
    const val target_sdk = 29
    const val min_sdk = 21
    const val build_tools = "29.0.2"
    const val version_code = 1
    const val version_name = "1.0.0"

    const val gradle = "3.4.2"
    const val kotlin = "1.3.61"
    const val core_ktx = "1.0.2"

    const val appcompat = "1.0.2"
    const val junit = "4.12"
    const val espresso_core = "3.2.0"
    const val runner = "1.2.0"

    const val arch_version = "2.2.0-alpha01"
    const val arch_room_version = "2.1.0-rc01"
    const val paging_version = "2.1.0"
    const val work_version = "2.1.0"

    const val constraint_layout = "1.1.3"

    const val glide = "4.9.0"
    const val glide_transformations = "4.0.0"

    const val retrofit = "2.6.0"
    const val okhttp_logging_interceptor = "3.9.0"
    const val rxjava = "2.1.4"
    const val rxandroid = "2.0.2"
    const val converter_fastjson = "2.1.0"

    const val timber = "4.7.1"
    const val material = "1.0.0-rc01"

    const val gson = "2.8.5"
    const val fastjson = "1.2.59"
    const val arouter_api = "1.5.0"
    const val arouter_compiler = "1.2.2"
    const val qmui = "2.0.0-alpha01"

    const val navigation = "2.1.0"
    const val smartrefresh = "1.1.0"
    const val multidex = "2.0.0"

    const val live_event_bus = "1.5.7"  //https://github.com/JeremyLiao/LiveEventBus
    const val banner = "androidx_v1.0.5"   //https://github.com/xiaohaibin/XBanner
    //ViewPager2：打造Banner控件     https://juejin.im/post/5e49163e6fb9a07cb96ae33d#heading-19
    const val baserecyclerview = "3.0.0-beta11"  //    https://github.com/CymChad/BaseRecyclerViewAdapterHelper

    const val aspectjrt = "1.8.9"
    const val filedownloader = "1.7.7"


}

object Deps {
    const val gradle_plugin = "com.android.tools.build:gradle:${Versions.gradle}"
    const val kotlin_stdlib = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${Versions.kotlin}"
    const val kotlin_plugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin}"
    const val kotlin_core = "androidx.core:core-ktx:${Versions.core_ktx}"

    const val appcompat = "androidx.appcompat:appcompat:${Versions.appcompat}"
    const val junit = "junit:junit:${Versions.junit}"
    const val runner = "androidx.test:runner:${Versions.runner}"
    const val espresso_core = "androidx.test.espresso:espresso-core:${Versions.espresso_core}"

    const val material = "com.google.android.material:material:${Versions.material}"
    const val constraint_layout = "androidx.constraintlayout:constraintlayout:${Versions.constraint_layout}"

    const val arch_lifecycle = "androidx.lifecycle:lifecycle-extensions:${Versions.arch_version}"
    const val arch_viewmodel = "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.arch_version}"
    const val arch_livedata = "androidx.lifecycle:lifecycle-livedata-ktx:${Versions.arch_version}"
    const val arch_runtime = "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.arch_version}"
    const val arch_room_runtime = "androidx.room:room-runtime:${Versions.arch_room_version}"
    const val arch_room_compiler = "androidx.room:room-compiler:${Versions.arch_room_version}"
    const val arch_room = "androidx.room:room-ktx:${Versions.arch_room_version}"
    const val paging_runtime = "androidx.paging:paging-runtime:${Versions.paging_version}"
    const val work_runtime = "androidx.work:work-runtime-ktx:${Versions.work_version}"

    const val glide_runtime = "com.github.bumptech.glide:glide:${Versions.glide}"
    const val glide_transformations = "jp.wasabeef:glide-transformations:${Versions.glide_transformations}"
    const val glide_compiler = "com.github.bumptech.glide:compiler:${Versions.glide}"

    const val multidex = "androidx.multidex:multidex:${Versions.multidex}"
    const val live_event_bus = "com.jeremyliao:live-event-bus-x:${Versions.live_event_bus}"

    const val retrofit_runtime = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
    const val retrofit_gson = "com.squareup.retrofit2:converter-gson:${Versions.retrofit}"
    const val converter_fastjson_android = "org.ligboy.retrofit2:converter-fastjson-android:${Versions.converter_fastjson}"
    const val retrofit_adapter_rxjava2 = "com.squareup.retrofit2:adapter-rxjava2:${Versions.retrofit}"
    const val okhttp_logging_interceptor = "com.squareup.okhttp3:logging-interceptor:${Versions.okhttp_logging_interceptor}"

    const val rxjava = "io.reactivex.rxjava2:rxjava:${Versions.rxjava}"
    const val rxandroid = "io.reactivex.rxjava2:rxandroid:${Versions.rxandroid}"

    const val timber = "com.jakewharton.timber:timber:${Versions.timber}"

    const val gson = "com.google.code.gson:gson:${Versions.gson}"
    const val fastjson = "com.alibaba:fastjson:${Versions.fastjson}"

    const val arouter_api = "com.alibaba:arouter-api:${Versions.arouter_api}"
    const val arouter_compiler = "com.alibaba:arouter-compiler:${Versions.arouter_compiler}"

    const val aspectjrt = "org.aspectj:aspectjrt:${Versions.aspectjrt}"
    const val filedownloader = "com.liulishuo.filedownloader:library:${Versions.filedownloader}"
    
    /*t腾讯qmui库*/
    const val qmui = "com.qmuiteam:qmui:${Versions.qmui}"

    const val arch = "com.qmuiteam:arch:${Versions.qmui}"
    const val arch_compiler = "com.qmuiteam:arch-compiler:${Versions.qmui}"



    //navigation导航
    const val navigation_fragment = "androidx.navigation:navigation-fragment:${Versions.navigation}"
    const val navigation_ui = "androidx.navigation:navigation-ui:${Versions.navigation}"


    //页面刷新组件
    const val smartrefreshlayout = "com.scwang.smartrefresh:SmartRefreshLayout:${Versions.smartrefresh}"
    const val smartrefreshheader = "com.scwang.smartrefresh:SmartRefreshHeader:${Versions.smartrefresh}"

    const val banner = "com.github.xiaohaibin:XBanner:${Versions.banner}"
    const val baserecyclerview = "com.github.CymChad:BaseRecyclerViewAdapterHelper:${Versions.baserecyclerview}"

    val addRepos: (handler: RepositoryHandler) -> Unit = {
        it.google()
        it.jcenter()
        it.maven { url = URI("https://oss.sonatype.org/content/repositories/snapshots") }
        it.maven { url = URI("https://jitpack.io")}
    }
}
