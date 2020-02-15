package com.bsoft.login.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.bsoft.login.R;

import java.util.ArrayList;

public class UserAdapter extends BaseAdapter {

    private ArrayList<String> data = new ArrayList<>();
    UserAdapter.OnItemViewClickListener listener;
    int width;

    public UserAdapter(UserAdapter.OnItemViewClickListener listener) {
        this.listener = listener;
    }

    public void setData(ArrayList<String> data) {
        this.data.clear();
        if (data != null)
            this.data.addAll(data);
        notifyDataSetChanged();
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void del(int pos) {
        this.data.remove(pos);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int i) {
        return data.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int position, View view, ViewGroup viewGroup) {
        view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.login_item_item_login_user, null);
//            view.setLayoutParams(new LinearLayout.LayoutParams(width, LinearLayout.LayoutParams.WRAP_CONTENT));
        view.setLayoutParams(new android.widget.AbsListView.LayoutParams(width, LinearLayout.LayoutParams.WRAP_CONTENT));
        TextView tvName = (TextView) view.findViewById(R.id.tvName);
        ImageView ivDel = (ImageView) view.findViewById(R.id.ivDel);

        tvName.setText(data.get(position));

        ivDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null) listener.onItemDelListener(data.get(position));
                del(position);
            }
        });
        return view;
    }

    public interface OnItemViewClickListener {
        void onItemDelListener(String name);
    }
}
