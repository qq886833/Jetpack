package com.bsoft.libcommon.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/**
 * Created by shengqf
 * Email : shengqf@bsoft.com.cn
 * date : 2019/4/3
 * describe :
 *
 mAdvertisementIv.post(() -> {
 String picPath = LocalData.getAdvertisementPicPath();
 int desireWidth = mAdvertisementIv.getWidth();
 int desireHeight = mAdvertisementIv.getHeight();
 Bitmap bitmap = BitmapUtil.getBitmap(picPath, desireWidth, desireHeight);
 mAdvertisementIv.setImageBitmap(bitmap);
 });
 *
 */
public class BitmapUtil {

    /**
     * 获取Bitmap（尺寸压缩，防止OOM）
     *
     * @param picPath      pic路径
     * @param desireWidth  目标bitmap的宽
     * @param desireHeight 目标bitmap的高
     * @return bitmap
     */
    public static Bitmap getBitmap(String picPath, int desireWidth, int desireHeight) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true; //bitmap不加载到内存
        options.inSampleSize = 1;
        BitmapFactory.decodeFile(picPath, options);
        int originWidth = options.outWidth;//获取原始图片宽
        int originHeight = options.outHeight;//获取原始图片高

        options.inJustDecodeBounds = false; //加载到内存
        options.inSampleSize = calInSampleSize(originWidth, originHeight, desireWidth, desireHeight);
        return BitmapFactory.decodeFile(picPath, options);
    }


    /**
     * 根据图片路径获取略缩图
     * @param picPath 图片路径
     * @param inSampleSize 原图缩放比例
     * @return
     */
    public static Bitmap getBitmap(String picPath, int inSampleSize) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = false; //加载到内存
        options.inSampleSize = inSampleSize;
        return BitmapFactory.decodeFile(picPath, options);
    }


    /**
     * 计算缩放比例
     *
     * @param originWidth  pic原始宽
     * @param originHeight pic原始高
     * @param desireWidth  pic目标宽
     * @param desireHeight pic目标高
     * @return 缩放比例
     */
    private static int calInSampleSize(int originWidth, int originHeight, int desireWidth, int desireHeight) {
        int sampleSize = 1;
        if (originWidth > desireWidth || originHeight > desireHeight) {
            int tempWidth = originWidth / 2;
            int tempHeight = originHeight / 2;
            while (tempWidth / sampleSize > desireWidth || tempHeight / sampleSize > desireHeight) {
                sampleSize *= 2;
            }
        }
        return sampleSize;
    }

}
