package com.bsoft.libcommon.common;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.bsoft.libbasic.base.activity.CoreActivity;
import com.bsoft.libcommon.R;
import com.bsoft.libcommon.common.model.ChoiceItem;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class DicChoiceActivity extends CoreActivity {
    /*Default*/
    public static final String INTENT_TITLE = "title";
    public static final String INTENT_DATA_LIST = "dataList";
    public static final String INTENT_RESULT = "dic_choice_result";

    /*Flag*/
    private ArrayList<ChoiceItem> dataList;
    private String title;
    private ChoiceItem choiceResult;

    /*View*/
    private RecyclerView recyclerView;
    private DicViewAdapter adapter;
    private DividerItemDecoration decoration;
    public static void appStart(Activity activity, String title
            , ArrayList<ChoiceItem> dataList, ChoiceItem choiceResult, int resultCode) {
        Intent intent = new Intent(activity, DicChoiceActivity.class);
        intent.putExtra(INTENT_TITLE, title);
        intent.putExtra(INTENT_DATA_LIST, dataList);
        intent.putExtra(INTENT_RESULT, choiceResult);
        activity.startActivityForResult(intent, resultCode);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.common_activity_dic_choice);
        parseIntent();
        //initTopBar();
        initLayout();

    }

    private void parseIntent() {
        if (getIntent() != null) {
            title = getIntent().getExtras().getString(INTENT_TITLE);
            dataList = (ArrayList<ChoiceItem>) getIntent().getSerializableExtra(INTENT_DATA_LIST);
            choiceResult = (ChoiceItem) getIntent().getSerializableExtra(INTENT_RESULT);

            if (null != choiceResult) {
                int index = -1;
                if ((index = dataList.indexOf(choiceResult)) != -1) {
                    dataList.get(index).setChoice(true);
                }
            }
        }
    }

//    @Override
//    protected void initTopBar() {
//        super.initTopBar();
//        mTopBar.setTitle(title);
//    }

    protected void initLayout() {

        initRecyclerView();
    }



    private void initRecyclerView() {
        recyclerView = findViewById(R.id.mRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        //默认给列表中的Item 一个 10dp的ItemDecoration
        decoration = new DividerItemDecoration(this, LinearLayoutManager.VERTICAL);
        decoration.setDrawable(ContextCompat.getDrawable(this, R.drawable.list_divider_1px));
        recyclerView.addItemDecoration(decoration);
        adapter = new DicViewAdapter(dataList);
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

                choiceResult = dataList.get(position);
                choiceResult.setChoice(true);
                for (ChoiceItem item : dataList) {
                    if (!choiceResult.equals(item)) {
                        item.setChoice(false);
                    }
                }
                adapter.notifyDataSetChanged();

                getIntent().putExtra(INTENT_RESULT, choiceResult);
                setResult(RESULT_OK, getIntent());
                finish();
            }
        });

    }



    static class DicViewAdapter extends BaseQuickAdapter<ChoiceItem, BaseViewHolder> {

        public DicViewAdapter(List<ChoiceItem> data) {
            super(R.layout.common_item_singechoice, data);

        }

        @Override
        protected void convert(@NotNull BaseViewHolder helper, ChoiceItem item) {
            ImageView ivSelect = helper.getView(R.id.ivSelect);
            helper.setText(R.id.tvName, item.getItemName());
            ivSelect.setSelected(item.isChoice());


        }
    }
}

