package com.bsoft.libcommon.widget.flowlayout;

public class Test {


/*
https://www.jianshu.com/p/b3c574f7976d

 <com.zhy.view.flowlayout.TagFlowLayout
    android:id="@+id/id_flowlayout"
    zhy:max_select="-1"
    android:layout_width="fill_parent"
    android:layout_height="wrap_content"
    android:padding="20dp">
    </com.zhy.view.flowlayout.TagFlowLayout>



    mFlowLayout = (TagFlowLayout) view.findViewById(R.id.id_flowlayout);
        mFlowLayout.setMaxSelectCount(3);
        mFlowLayout.setAdapter(new TagAdapter<String>(mVals)
    {

        @Override
        public View getView(FlowLayout parent, int position, String s)
        {
            TextView tv = (TextView) mInflater.inflate(R.layout.tv,
                    mFlowLayout, false);
            tv.setText(s);
            return tv;
        }
    });


         mFlowLayout.setOnTagClickListener(new TagFlowLayout.OnTagClickListener()
    {
        @Override
        public boolean onTagClick(View view, int position, FlowLayout parent)
        {
            Toast.makeText(getActivity(), mVals[position], Toast.LENGTH_SHORT).show();
            //view.setVisibility(View.GONE);
            return true;
        }
    });


        mFlowLayout.setOnSelectListener(new TagFlowLayout.OnSelectListener()
    {
        @Override
        public void onSelected(Set<Integer> selectPosSet)
        {
            getActivity().setTitle("choose:" + selectPosSet.toString());
        }
    });


*/


}
