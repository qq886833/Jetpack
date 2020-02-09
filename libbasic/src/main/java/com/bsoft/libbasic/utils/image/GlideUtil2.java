package com.bsoft.libbasic.utils.image;


import android.widget.ImageView;
import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import com.bsoft.libbasic.constant.HttpConstants;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;


public class GlideUtil2 {
    //*******************************   普通头像 **********************

    /**
     * 获取头像
     *
     * @param object
     * @param imageView
     * @param id
     * @param def
     */
    public void loadHeadById(@NonNull Object object, @NonNull ImageView imageView, String id, @DrawableRes int def) {
        loadHeadById(object, imageView, id, imageView.getWidth(), def, def);
    }

    /**
     * 获取头像
     *
     * @param object
     * @param imageView
     * @param url
     * @param def
     */
    public void loadHeadByUrl(@NonNull Object object, @NonNull ImageView imageView, String url, @DrawableRes int def) {
        loadHeadByUrl(object, imageView, url, imageView.getWidth(), def, def);
    }

    /**
     * 获取头像
     *
     * @param object
     * @param imageView
     * @param id
     * @param width
     * @param loadRes
     * @param errorRes
     */
    public void loadHeadById(@NonNull Object object, @NonNull ImageView imageView,
                             String id, int width,
                             @DrawableRes int loadRes, @DrawableRes int errorRes) {
        loadHeadByUrl(object, imageView, ImageUrlUtil.getUrl(getImgUrl(), id), width, loadRes, errorRes);
    }

    /**
     * 获取头像
     *
     * @param object
     * @param imageView
     * @param url
     * @param width
     * @param loadRes
     * @param errorRes
     */
    public void loadHeadByUrl(@NonNull Object object, @NonNull ImageView imageView,
                              String url, int width,
                              @DrawableRes int loadRes, @DrawableRes int errorRes) {
        loadImageByUrl(object, imageView,
                ImageSizeUtil.getHeaderUrl(url, width),
                loadRes, errorRes);
    }

    //*******************************   圆形头像 **********************

    /**
     * 获取圆形头像
     *
     * @param object
     * @param imageView
     * @param id
     * @param def
     */
    public void loadHeadCircleById(@NonNull Object object, @NonNull ImageView imageView, String id, @DrawableRes int def) {
        loadHeadCircleById(object, imageView, id, imageView.getWidth(), def, def);
    }

    /**
     * 获取圆形头像
     *
     * @param object
     * @param imageView
     * @param url
     * @param def
     */
    public void loadHeadCircleByUrl(@NonNull Object object, @NonNull ImageView imageView, String url, @DrawableRes int def) {
        loadHeadCircleByUrl(object, imageView, url, imageView.getWidth(), def, def);
    }


    /**
     * 获取圆形头像
     *
     * @param object
     * @param imageView
     * @param id
     * @param width
     * @param loadRes
     * @param errorRes
     */
    public void loadHeadCircleById(@NonNull Object object, @NonNull ImageView imageView,
                                   String id, int width,
                                   @DrawableRes int loadRes, @DrawableRes int errorRes) {
        loadHeadCircleByUrl(object, imageView,
                ImageUrlUtil.getUrl(getImgUrl(), id), width,
                loadRes, errorRes);
    }

    /**
     * 获取圆形头像
     *
     * @param object
     * @param imageView
     * @param url
     * @param width
     * @param loadRes
     * @param errorRes
     */
    public void loadHeadCircleByUrl(@NonNull Object object, @NonNull ImageView imageView,
                                    String url, int width,
                                    @DrawableRes int loadRes, @DrawableRes int errorRes) {
        loadImageCircleByUrl(object, imageView,
                ImageSizeUtil.getHeaderUrl(url, width),
                loadRes, errorRes);
    }

    //*******************************   普通图片 **********************

    /**
     * 获取图片
     *
     * @param object
     * @param imageView
     * @param id
     * @param def
     */
    public void loadImageById(@NonNull Object object, @NonNull ImageView imageView, String id, @DrawableRes int def) {
        loadImageById(object, imageView, id, def, def);
    }

    /**
     * 获取图片
     *
     * @param object
     * @param imageView
     * @param url
     * @param def
     */
    public void loadImageByUrl(@NonNull Object object, @NonNull ImageView imageView, String url, @DrawableRes int def) {
        loadImageByUrl(object, imageView, url, def, def);
    }

    /**
     * @param object
     * @param imageView
     * @param id
     * @param loadRes
     * @param errorRes
     */
    public void loadImageById(@NonNull Object object, @NonNull ImageView imageView,
                              String id, @DrawableRes int loadRes, @DrawableRes int errorRes) {
        loadImageByUrl(object, imageView,
                ImageUrlUtil.getUrl(getImgUrl(), id),
                loadRes, errorRes);
    }

    /**
     * 获取图片（主）
     *
     * @param object
     * @param imageView
     * @param url
     * @param loadRes
     * @param errorRes
     */
    public void loadImageByUrl(@NonNull Object object, @NonNull ImageView imageView,
                               String url, @DrawableRes int loadRes, @DrawableRes int errorRes) {
        RequestOptions options = new RequestOptions()
                .placeholder(loadRes)// 正在加载中的图片
                .error(errorRes); // 加载失败的图片

        GlideUtil.getInstance(object)
                .load(url)
                .apply(options)
                .into(imageView);
    }

    //*******************************   圆形图片 **********************

    /**
     * 获取圆形图片
     *
     * @param object
     * @param imageView
     * @param id
     * @param def
     */
    public void loadImageCircleById(@NonNull Object object, @NonNull ImageView imageView, String id, @DrawableRes int def) {
        loadImageCircleById(object, imageView, id, def, def);
    }

    /**
     * 获取圆形图片
     *
     * @param object
     * @param imageView
     * @param url
     * @param def
     */
    public void loadImageCircleByUrl(@NonNull Object object, @NonNull ImageView imageView, String url, @DrawableRes int def) {
        loadImageCircleByUrl(object, imageView, url, def, def);
    }

    /**
     * 获取圆形图片
     *
     * @param object
     * @param imageView
     * @param id
     * @param loadRes
     * @param errorRes
     */
    public void loadImageCircleById(@NonNull Object object, @NonNull ImageView imageView,
                                    String id, @DrawableRes int loadRes, @DrawableRes int errorRes) {
        loadImageCircleByUrl(object, imageView,
                ImageUrlUtil.getUrl(getImgUrl(), id),
                loadRes, errorRes);
    }

    /**
     * 获取圆形图片（主）
     *
     * @param object
     * @param imageView
     * @param url
     * @param loadRes
     * @param errorRes
     */
    public void loadImageCircleByUrl(@NonNull Object object, @NonNull ImageView imageView,
                                     String url, @DrawableRes int loadRes, @DrawableRes int errorRes) {
        RequestOptions options = RequestOptions.bitmapTransform(new CircleCrop())
                .placeholder(loadRes)// 正在加载中的图片
                .error(errorRes); // 加载失败的图片

        GlideUtil.getInstance(object)
                .load(url)
                .apply(options)
                .into(imageView);
    }

    protected String getImgUrl(){
        return HttpConstants.HTTPIMGURL;
    }
}
