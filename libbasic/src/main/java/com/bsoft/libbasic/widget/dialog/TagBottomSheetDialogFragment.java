package com.bsoft.libbasic.widget.dialog;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class TagBottomSheetDialogFragment extends BottomSheetDialogFragment {
   /* private RecyclerView recyclerView;
    private TagsAdapter tagsAdapter;
    private List<TagList> mTagLists = new ArrayList<>();
    private OnTagItemSelectedListener listener;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        View view = LayoutInflater.from(getContext()).inflate(R.layout.layout_tag_bottom_sheet_dialog, null, false);
        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        tagsAdapter = new TagsAdapter();
        recyclerView.setAdapter(tagsAdapter);

        dialog.setContentView(view);
        ViewGroup parent = (ViewGroup) view.getParent();
        BottomSheetBehavior<ViewGroup> behavior = BottomSheetBehavior.from(parent);
        behavior.setPeekHeight(PixUtils.getScreenHeight() / 3);
        behavior.setHideable(false);


        ViewGroup.LayoutParams layoutParams = parent.getLayoutParams();
        layoutParams.height = PixUtils.getScreenHeight() / 3 * 2;
        parent.setLayoutParams(layoutParams);

        queryTagList();
        return dialog;
    }

    private void queryTagList() {
        ApiService.get("/tag/queryTagList")
                .addParam("userId", UserManager.get().getUserId())
                .addParam("pageCount", 100)
                .addParam("tagId", 0).execute(new JsonCallback<List<TagList>>() {
            @Override
            public void onSuccess(ApiResponse<List<TagList>> response) {
                super.onSuccess(response);
                if (response.body != null) {
                    List<TagList> list = response.body;
                    mTagLists.addAll(list);
                    ArchTaskExecutor.getMainThreadExecutor().execute(new Runnable() {
                        @Override
                        public void run() {
                            tagsAdapter.notifyDataSetChanged();
                        }
                    });
                }
            }

            @Override
            public void onError(ApiResponse<List<TagList>> response) {
                ArchTaskExecutor.getMainThreadExecutor().execute(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getContext(), response.message, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    public void setOnTagItemSelectedListener(OnTagItemSelectedListener listener) {
        this.listener = listener;
    }


    class TagsAdapter extends RecyclerView.Adapter {

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            TextView textView = new TextView(parent.getContext());
            textView.setTextSize(13);
            textView.setTypeface(Typeface.DEFAULT_BOLD);
            textView.setTextColor(ContextCompat.getColor(parent.getContext(), R.color.color_000));
            textView.setGravity(Gravity.CENTER_VERTICAL);
            textView.setLayoutParams(new RecyclerView.LayoutParams(-1, PixUtils.dp2px(45)));

            return new RecyclerView.ViewHolder(textView) {
            };
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            TextView textView = (TextView) holder.itemView;
            TagList tagList = mTagLists.get(position);
            textView.setText(tagList.title);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        listener.onTagItemSelected(tagList);
                        dismiss();
                    }
                }
            });
        }

        @Override
        public int getItemCount() {
            return mTagLists.size();
        }
    }

    public interface OnTagItemSelectedListener {
        void onTagItemSelected(TagList tagList);
    }*/
}
