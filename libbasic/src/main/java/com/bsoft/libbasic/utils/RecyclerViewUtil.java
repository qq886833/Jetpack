package com.bsoft.libbasic.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.bsoft.libbasic.R;
import com.bsoft.libbasic.utils.recyclerviewDivider.HorizontalDividerItemDecoration;
import com.bsoft.libbasic.utils.recyclerviewDivider.VerticalDividerItemDecoration;

public class RecyclerViewUtil {


    public static void initLinearV(Context baseContext, RecyclerView recyclerview) {
        initLinearV(baseContext, recyclerview, R.color.transparent, R.dimen.dp_0, R.dimen.dp_0, R.dimen.dp_0);
    }

    public static void initLinearV(Context baseContext, RecyclerView recyclerview,
                                   int color, int size) {
        initLinearV(baseContext, recyclerview, color, size, R.dimen.dp_0, R.dimen.dp_0);
    }

    @SuppressLint("WrongConstant")
    public static void initLinearV(Context baseContext, RecyclerView recyclerview,
                                   int color, int size, int marginLeft, int marginRight) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(baseContext);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerview.setLayoutManager(linearLayoutManager);
        recyclerview.addItemDecoration(
                new HorizontalDividerItemDecoration.Builder(baseContext)
                        .color(ContextCompat.getColor(baseContext, color))
                        .sizeResId(size)
                        .marginResId(marginLeft, marginRight)
                        .build());
    }

    public static void initLinearH(Context baseContext, RecyclerView recyclerview) {
        initLinearH(baseContext, recyclerview, R.color.transparent, R.dimen.dp_0, R.dimen.dp_0, R.dimen.dp_0);
    }

    public static void initLinearH(Context baseContext, RecyclerView recyclerview,
                                   int color, int size) {
        initLinearH(baseContext, recyclerview, color, size, R.dimen.dp_0, R.dimen.dp_0);
    }

    public static void initLinearH(Context baseContext, RecyclerView recyclerview,
                                   int color, int size, int marginLeft, int marginRight) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(baseContext);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerview.setLayoutManager(linearLayoutManager);
        recyclerview.addItemDecoration(
                new VerticalDividerItemDecoration.Builder(baseContext)
                        .color(ContextCompat.getColor(baseContext, color))
                        .sizeResId(size)
                        .marginResId(marginLeft, marginRight)
                        .build());
    }

    public static void initGrid(Context baseContext, RecyclerView rv, int gridColumn,
                                int color, int size, int marginLeft, int marginRight) {
        initGrid(baseContext, rv, gridColumn,
                color, size, marginLeft, marginRight,
                color, size, marginLeft, marginRight
        );
    }

    public static void initGrid(Context baseContext, RecyclerView rv, int gridColumn,
                                int color, int size) {
        initGrid(baseContext, rv, gridColumn,
                color, size, R.dimen.dp_0, R.dimen.dp_0,
                color, size, R.dimen.dp_0, R.dimen.dp_0
        );
    }

    public static void initGrid(Context baseContext, RecyclerView rv, int gridColumn) {
        initGrid(baseContext, rv, gridColumn,
                R.color.transparent, R.dimen.dp_0, R.dimen.dp_0, R.dimen.dp_0,
                R.color.transparent, R.dimen.dp_0, R.dimen.dp_0, R.dimen.dp_0
        );
    }

    /**
     * @param baseContext
     * @param rv
     * @param gridColumn
     * @param colorH
     * @param sizeH
     * @param marginLeftH
     * @param marginRightH
     * @param colorV
     * @param sizeV
     * @param marginLeftV
     * @param marginRightV
     */
    public static void initGrid(Context baseContext, RecyclerView rv, int gridColumn,
                                int colorH, int sizeH, int marginLeftH, int marginRightH,
                                int colorV, int sizeV, int marginLeftV, int marginRightV
    ) {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(baseContext, gridColumn);
//        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
//            @Override
//            public int getSpanSize(int position) {
//                return 3 - position % 3;
//            }
//        });
        rv.setLayoutManager(gridLayoutManager);
        rv.addItemDecoration(
                new HorizontalDividerItemDecoration.Builder(baseContext)
                        .color(ContextCompat.getColor(baseContext, colorH))
                        .sizeResId(sizeH)
                        .marginResId(marginLeftH, marginRightH)
                        .build());
        rv.addItemDecoration(
                new VerticalDividerItemDecoration.Builder(baseContext)
                        .color(ContextCompat.getColor(baseContext, colorV))
                        .sizeResId(sizeV)
                        .marginResId(marginLeftV, marginRightV)
                        .showLastDivider()
                        .build());
    }

}
