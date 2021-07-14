package com.example.chapter3.homework;


import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;

import java.util.ArrayList;
import java.util.List;

public class PlaceholderFragment extends Fragment {

    private LottieAnimationView animation;
    // private TextView textView;
    private RecyclerView recyclerView;
    private MyAdapter myAdapter;
    private Context context;
    private List<String> list = new ArrayList<>();
    private AnimatorSet set;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO ex3-3: 修改 fragment_placeholder，添加 loading 控件和列表视图控件
        View v = inflater.inflate(R.layout.fragment_placeholder, container, false);
        animation = v.findViewById(R.id.loading);
        recyclerView = v.findViewById(R.id.recycler);
//        textView = v.findViewById(R.id.textView);
//        textView.setVisibility(View.INVISIBLE);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setVisibility(View.INVISIBLE);
        myAdapter = new MyAdapter();
        recyclerView.setAdapter(myAdapter);
        for (int i = 0; i <= 20; i++) {
            list.add("test"+i);
        }
        myAdapter.addList(list);
        myAdapter.notifyDataSetChanged();

        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        getView().postDelayed(new Runnable() {
            @Override
            public void run() {
                // 这里会在 5s 后执行
                // TODO ex3-4：实现动画，将 lottie 控件淡出，列表数据淡入
                ObjectAnimator animator1 = ObjectAnimator.ofFloat(animation, "Alpha", 1.0f, 0.0f);
                animator1.setDuration(1000);
                ObjectAnimator animator2 = ObjectAnimator.ofFloat(recyclerView, "Alpha", 0.0f, 1.0f);
                animator2.setDuration(1000);
                recyclerView.setVisibility(View.VISIBLE);
                set = new AnimatorSet();
                set.playTogether(animator1, animator2);
                set.start();
            }
        }, 5000);
    }
}
