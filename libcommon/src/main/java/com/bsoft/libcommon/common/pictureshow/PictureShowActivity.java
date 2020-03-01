package com.bsoft.libcommon.common.pictureshow;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import com.bsoft.libbasic.base.activity.CoreActivity;
import com.bsoft.libbasic.utils.DensityUtil;
import com.bsoft.libcommon.R;
import com.bsoft.libcommon.common.pictureshow.fresco.view.ImageLoadingDrawable;
import com.bsoft.libcommon.common.pictureshow.fresco.zoomable.ZoomableDraweeView;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.drawable.ScalingUtils;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/11/30.
 * 使用fresco实现图片预览
 */
public class PictureShowActivity extends CoreActivity {
    /*Default*/
    public static final String DATA_LIST = "imgList";
    public static final String DEFAULT_INDEX = "index";
    /*Util*/
    private PagerAdapter adapter;
    /*Flag*/
    private int defPosition = 0;
    private ArrayList<String> imageList;
    int size;
    /*View*/
    private ViewPager vp;

    public static void appStart(@NonNull Context context, ArrayList<String> urls, int defPosition) {
        if (urls == null || urls.isEmpty()) {
            return;
        }

        Intent intent = new Intent(context, PictureShowActivity.class);
        intent.putExtra(PictureShowActivity.DATA_LIST, urls);
        intent.putExtra(PictureShowActivity.DEFAULT_INDEX, defPosition);
        context.startActivity(intent);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.common_activity_picture_show);
        parseIntent();
        initTopBar();
        initLayout();
        setViewpager();
    }




    private void parseIntent() {
        if (getIntent() != null) {
            imageList = (ArrayList<String>) getIntent().getSerializableExtra(DATA_LIST);
            defPosition = getIntent().getIntExtra(DEFAULT_INDEX, 0);
        }

        if (imageList == null) {
            imageList = new ArrayList<>();
            imageList.add("http://null");
        }

        size = imageList.size();
    }


    protected void initLayout() {

        vp = findViewById(R.id.viewPager);
        mTopBar.setBackgroundColor(ContextCompat.getColor(this, R.color.transparent));
        if (size > 1) {
            mTopBar.setTitle(getString(R.string.common_big_picture) + "（" + (defPosition + 1) + "/" + size + "）");
        } else {
            mTopBar.setTitle(getString(R.string.common_big_picture));
        }
    }

    private void setViewpager() {

        // 根据图片，创建子view
        adapter = new MyViewPage(activity, imageList);
        vp.setAdapter(adapter);
        vp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (size > 1)
                    mTopBar.setTitle(getString(R.string.common_big_picture) + "（" + (defPosition + 1) + "/" + size + "）");
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        vp.setCurrentItem(defPosition);
    }


    static class MyViewPage extends PagerAdapter {
        private boolean mAllowSwipingWhileZoomed = true;
        CoreActivity baseActivity;
        ArrayList<String> imageList;

        public MyViewPage(Context context, ArrayList<String> imageList) {
            baseActivity = (CoreActivity) context;
            this.imageList = imageList;
        }

        public void setAllowSwipingWhileZoomed(boolean allowSwipingWhileZoomed) {
            mAllowSwipingWhileZoomed = allowSwipingWhileZoomed;
        }

        public boolean allowsSwipingWhileZoomed() {
            return mAllowSwipingWhileZoomed;
        }

        public void toggleAllowSwipingWhileZoomed() {
            mAllowSwipingWhileZoomed = !mAllowSwipingWhileZoomed;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ZoomableDraweeView view = getZoomableDraweeView(imageList.get(position), position, container.getContext());
            container.addView(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            return view;
        }

        @NonNull
        private ZoomableDraweeView getZoomableDraweeView(String url, int position, Context baseContext) {
            ZoomableDraweeView zoomableDraweeView = new ZoomableDraweeView(baseContext);
            zoomableDraweeView.setAllowTouchInterceptionWhileZoomed(mAllowSwipingWhileZoomed);
            GenericDraweeHierarchy hierarchy =
                    new GenericDraweeHierarchyBuilder(baseContext.getResources())
                            .setActualImageScaleType(ScalingUtils.ScaleType.FIT_CENTER)
                            .setProgressBarImage(
                                    new ImageLoadingDrawable(DensityUtil.dip2px(baseContext, 30),
                                            DensityUtil.dip2px(baseContext, 5),
                                            0xFFadadad, 0xFF0EB6D2
                                    )
                            )
                            .setFadeDuration(500)
                            .setFailureImage(ContextCompat.getDrawable(baseContext, R.mipmap.icon_pic_def_holder))
                            .setRetryImage(ContextCompat.getDrawable(baseContext, R.mipmap.icon_pic_def_holder))
                            .build();

            zoomableDraweeView.setHierarchy(hierarchy);
            zoomableDraweeView.setTapListener(createTapListener(zoomableDraweeView, position));
            if (url.startsWith("http")) {
                zoomableDraweeView.setController(Fresco.newDraweeControllerBuilder()
                        .setUri(Uri.parse(url))
                        .build());
            } else {
                zoomableDraweeView.setController(Fresco.newDraweeControllerBuilder()
                        .setUri(Uri.parse("file:///" + url))
                        .build());
            }
            return zoomableDraweeView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
//            container.removeView((View) object);
            ZoomableDraweeView zoomableDraweeView = (ZoomableDraweeView) object;
            zoomableDraweeView.setController(null);
        }

        @Override
        public int getItemPosition(Object object) {
            // We want to create a new view when we call notifyDataSetChanged() to have the correct behavior
            return POSITION_NONE;
        }

        @Override
        public boolean isViewFromObject(View view, Object obj) {
            return view == obj;
        }

        @Override
        public int getCount() {
            return imageList.size();
        }

        private GestureDetector.SimpleOnGestureListener createTapListener(final ZoomableDraweeView view, final int position) {
            return new GestureDetector.SimpleOnGestureListener() {
                @Override
                public void onLongPress(MotionEvent e) {
                    Log.d("MyPagerAdapter", "onLongPress: " + position);
                }


                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return super.onSingleTapUp(e);
                }

            };
        }
    }
}
