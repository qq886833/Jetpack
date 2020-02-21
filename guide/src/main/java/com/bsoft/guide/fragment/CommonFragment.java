package com.bsoft.guide.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.bsoft.guide.R;
import com.bsoft.guide.activity.GuideActivity;
import com.bsoft.libbasic.utils.EffectUtil;


/**
 *
 */

public class CommonFragment extends Fragment {
    View mainView;
    TextView tvTitle, tvSubTitle;
    ImageView iv;
    View ivIn;
    
    public static Fragment getInstance(int imgRes, String title, String subTitle, boolean isEnd){
        CommonFragment fragment = new CommonFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("imgRes", imgRes);
        bundle.putString("title", title);
        bundle.putString("subTitle", subTitle);
        bundle.putBoolean("isEnd", isEnd);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mainView = inflater.inflate(R.layout.guide_fragment_guide, container, false);
        return mainView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tvTitle = view.findViewById(R.id.tvTitle);
        tvSubTitle = view.findViewById(R.id.tvSubTitle);
        
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        iv = (ImageView) mainView.findViewById(R.id.iv);
        ivIn = mainView.findViewById(R.id.ivIn);

        Bundle bundle = getArguments();
        if (bundle != null) {
            int imgRes = bundle.getInt("imgRes", 0);
            boolean isEnd = bundle.getBoolean("isEnd", false);
            iv.setImageResource(imgRes);
            ivIn.setVisibility(isEnd ? View.VISIBLE : View.GONE);
            tvTitle.setText(bundle.getString("title"));
            tvSubTitle.setText(bundle.getString("subTitle"));
        }

        

        EffectUtil.addClickEffect(ivIn);
        ivIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((GuideActivity)getActivity()).goIn();
            }
        });
    }
}
