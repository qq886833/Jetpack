package com.bsoft.libcommon.widget.viewpager;

public class Test {




/*



private MainTabAdapter adapter;
    */
/*Flag*//*

    private ArrayList<Fragment> fragments;
    */
/*View*//*

    private TabLayout tabLayout;
    private ViewPager viewpager;
    private ImageView unreadWait;
    private ImageView unreadIng;

    public static void appStart(Context context) {
        Intent intent = new Intent(context, InquiryPicMainActivity.class);
        if (!(context instanceof Activity)){
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inquirydoc_activity_inquiry_pic_main);
        initLayout();
    }

    @Override
    protected void onRefreshView() {
    }

    @Override
    protected void initLayout() {
        super.initLayout();

        tabLayout = findViewById(R.id.tab);
        viewpager = findViewById(R.id.viewpager);

        initTab();
    }

    private void initTab() {
        fragments = new ArrayList<>();

        //TabLayout
        //待接诊
        TabLayout.Tab wait = tabLayout.newTab();
        wait.setCustomView(R.layout.inquirydoc_tab_main);
        TextView waitTitle = wait.getCustomView().findViewById(R.id.tvTitle);
        unreadWait = wait.getCustomView().findViewById(R.id.ivUnread);
        waitTitle.setText(getString(R.string.inquirydoc_tab_title_inquiry_wait));
        tabLayout.addTab(wait);
        //进行中
        TabLayout.Tab ing = tabLayout.newTab();
        ing.setCustomView(R.layout.inquirydoc_tab_main);
        TextView tabFirstTitle = ing.getCustomView().findViewById(R.id.tvTitle);
        unreadIng = ing.getCustomView().findViewById(R.id.ivUnread);
        tabFirstTitle.setText(getString(R.string.inquirydoc_tab_title_inquiry_start));
        tabLayout.addTab(ing);
        //已结束
        TabLayout.Tab finish = tabLayout.newTab().setText(getString(R.string.inquirydoc_tab_title_inquiry_end));
        tabLayout.addTab(finish);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewpager.setCurrentItem(tab.getPosition(), true);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


        //ViewPager
        fragments.add(InquiryPicListFragment.newInstance(InquiryViewTypeDic.VIEW_TYPE_WAIT));
        fragments.add(InquiryPicListFragment.newInstance(InquiryViewTypeDic.VIEW_TYPE_ON_TIME));
        fragments.add(InquiryPicListFragment.newInstance(InquiryViewTypeDic.VIEW_TYPE_END));

        adapter = new MainTabAdapter(activity.getSupportFragmentManager(), fragments);
        viewpager.setOffscreenPageLimit(fragments.size());
        viewpager.setAdapter(adapter);

        viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                tabLayout.setScrollPosition(position, positionOffset, true);
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    //InquiryPicListFragment 回调
    @Override
    public void onUnreadShow(int viewType, boolean show) {
        if (viewType == InquiryViewTypeDic.VIEW_TYPE_WAIT) {
            unreadWait.setVisibility(show ? View.VISIBLE : View.GONE);
        } else if (viewType == InquiryViewTypeDic.VIEW_TYPE_ON_TIME) {
            unreadIng.setVisibility(show ? View.VISIBLE : View.GONE);
        }
    }

*/






}
