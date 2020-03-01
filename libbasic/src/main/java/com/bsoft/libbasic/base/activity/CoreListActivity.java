package com.bsoft.libbasic.base.activity;

import android.os.Bundle;
import androidx.annotation.Nullable;
import com.bsoft.libbasic.R;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

public abstract class CoreListActivity extends CoreLoadViewActivity {

    /*Default*/
    protected static final int FIRST_PAGE = 1;
    /*Util*/
    /*Flag*/
    protected int pageNo = FIRST_PAGE;
    protected int pageSize = 20;

    private boolean refreshEnable = true;
    private boolean loadMoreEnable = true;
    /*View*/
    protected SmartRefreshLayout mSmartRefreshLayout;

    protected abstract void onLoadMoreView();
    protected abstract void onLoadRefreshView();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    protected void initLayout() {
        mSmartRefreshLayout = findViewById(R.id.mSmartRefreshLayout);
        if (mSmartRefreshLayout != null) {
            // mSmartRefreshLayout.setEnableLoadMore(false);
            //  mSmartRefreshLayout.setEnableRefresh(false);
            mSmartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
                @Override
                public void onRefresh(RefreshLayout refreshlayout) {
                    pageNo = FIRST_PAGE;
                    mSmartRefreshLayout.setNoMoreData(false);
                    onLoadRefreshView();
                }
            });
            mSmartRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
                @Override
                public void onLoadMore(RefreshLayout refreshLayout) {
                    pageNo++;
                    onLoadMoreView();
                }
            });
        }
    }
    public void setRefreshEnable(boolean enable) {
        if (mSmartRefreshLayout != null) {
            refreshEnable = enable;
            mSmartRefreshLayout.setEnableRefresh(enable);
        }
    }

    public void setLoadMoreEnable(boolean enable) {
        if (mSmartRefreshLayout != null) {
            loadMoreEnable = enable;
            mSmartRefreshLayout.setEnableLoadMore(enable);
        }
    }
    public void setAutoRefresh(boolean enable) {
        if (mSmartRefreshLayout != null) {
            if (enable){
                //触发自动刷新
                mSmartRefreshLayout.setEnableRefresh(enable);
                mSmartRefreshLayout.autoRefresh();
            }
        }
    }

    /**
     * no more data
     */
    public void finishLoadMoreWithNoMoreData() {
        if (mSmartRefreshLayout != null) {
            mSmartRefreshLayout.finishLoadMoreWithNoMoreData();
        }
    }

    public boolean isRefreshEnable() {
        return refreshEnable;
    }

    public boolean isLoadMoreEnable() {
        return loadMoreEnable;
    }

    public boolean isFirstPage(){
        return pageNo == FIRST_PAGE;
    }

}
