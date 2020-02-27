package com.bsoft.libbasic.base.mvvm;

import androidx.fragment.app.Fragment;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

public abstract class BaseListFragmentMvvm<T, M extends ListViewModel<T>> extends Fragment implements OnRefreshListener, OnLoadMoreListener {
//    protected BasicLayoutRefreshRecycleviewBinding binding;
//    protected RecyclerView mRecyclerView;
//    protected SmartRefreshLayout mRefreshLayout;
//    protected EmptyView mEmptyView;
//    protected PagedListAdapter<T, RecyclerView.ViewHolder> adapter;
//    protected M mViewModel;
//    protected DividerItemDecoration decoration;
//
//    @SuppressLint("WrongConstant")
//    @Nullable
//    @Override
//    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        binding = BasicLayoutRefreshRecycleviewBinding.inflate(inflater, container, false);
//        binding.getRoot().setFitsSystemWindows(true);
//        mRecyclerView = binding.recyclerView;
//        mRefreshLayout = binding.refreshLayout;
//        mEmptyView = binding.emptyView;
//
//        mRefreshLayout.setEnableRefresh(true);
//        mRefreshLayout.setEnableLoadMore(true);
//        mRefreshLayout.setOnRefreshListener(this);
//        mRefreshLayout.setOnLoadMoreListener(this);
//
//        adapter = getAdapter();
//        mRecyclerView.setAdapter(adapter);
//        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
//        mRecyclerView.setItemAnimator(null);
//
//        //默认给列表中的Item 一个 10dp的ItemDecoration
//        decoration = new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL);
//        decoration.setDrawable(ContextCompat.getDrawable(getContext(), R.drawable.list_divider_15px));
//        mRecyclerView.addItemDecoration(decoration);
//
//        genericViewModel();
//        return binding.getRoot();
//
//    }
//
//
//    private void genericViewModel() {
//        //利用 子类传递的 泛型参数实例化出absViewModel 对象。
//        ParameterizedType type = (ParameterizedType) getClass().getGenericSuperclass();
//        Type[] arguments = type.getActualTypeArguments();
//        if (arguments.length > 1) {
//            Type argument = arguments[1];
//            Class modelClaz = ((Class) argument).asSubclass(ListViewModel.class);
//            mViewModel = (M) ViewModelProviders.of(this).get(modelClaz);
//
//            //触发页面初始化数据加载的逻辑
//            mViewModel.getPageData().observe(this, pagedList -> submitList(pagedList));
//
//            //监听分页时有无更多数据,以决定是否关闭上拉加载的动画
//            mViewModel.getBoundaryPageData().observe(this, hasData -> finishRefresh(hasData));
//        }
//    }
//
//    public void submitList(PagedList<T> result) {
//        //只有当新数据集合大于0 的时候，才调用adapter.submitList
//        //否则可能会出现 页面----有数据----->被清空-----空布局
//        if (result.size() > 0) {
//            adapter.submitList(result);
//        }
//        finishRefresh(result.size() > 0);
//    }
//
//    public void finishRefresh(boolean hasData) {
//        PagedList<T> currentList = adapter.getCurrentList();
//        hasData = hasData || currentList != null && currentList.size() > 0;
//        RefreshState state = mRefreshLayout.getState();
//        if (state.isFooter && state.isOpening) {
//            mRefreshLayout.finishLoadMore();
//        } else if (state.isHeader && state.isOpening) {
//            mRefreshLayout.finishRefresh();
//        }
//
//        if (hasData) {
//            mEmptyView.setVisibility(View.GONE);
//        } else {
//            mEmptyView.setVisibility(View.VISIBLE);
//        }
//    }
//
//    /**
//     * 因而 我们在 onCreateView的时候 创建了 PagedListAdapter
//     * 所以，如果arguments 有参数需要传递到Adapter 中，那么需要在getAdapter()方法中取出参数。
//     *
//     * @return
//     */
//    public abstract PagedListAdapter getAdapter();
}
