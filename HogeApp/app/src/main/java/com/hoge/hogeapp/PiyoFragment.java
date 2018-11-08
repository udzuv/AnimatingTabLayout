package com.hoge.hogeapp;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;


public class PiyoFragment extends Fragment {
    private Button sampleButton, piyoButton;
    private ImageView imageView;


    public PiyoFragment() { }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_piyo, container, false);

        // activity sample
        sampleButton = view.findViewById(R.id.sampleButton);
        piyoButton = (Button) view.findViewById(R.id.piyoButton);
        imageView = (ImageView) view.findViewById(R.id.ic_android);

        sampleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PiyoFragment.this.getContext(), ScrollingActivity.class);
                startActivity(intent);
            }
        });

        piyoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startComposite();
            }
        });
        return view;
    }

    private void startScaling(){
        // ScaleAnimation(float fromX, float toX, float fromY, float toY, int pivotXType, float pivotXValue, int pivotYType, float pivotYValue)
        ScaleAnimation scaleAnimation = new ScaleAnimation(
                1.0f, 4.0f, 1.0f,4.0f,
                Animation.RELATIVE_TO_SELF,
                0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        scaleAnimation.setDuration(2000);
        scaleAnimation.setRepeatCount(0);
        scaleAnimation.setFillAfter(true);
        imageView.startAnimation(scaleAnimation);
    }

    private void startComposite() {
        // 【1】インスタンスを生成
        AnimationSet set = new AnimationSet(true);

        // 【2】基本のアニメーションを生成
        AlphaAnimation alpha = new AlphaAnimation(0.9f, 0.2f);
        RotateAnimation rotate = new RotateAnimation(0, 360, 50, 25);
        ScaleAnimation scale = new ScaleAnimation(0.1f, 1, 0.1f, 1);
        TranslateAnimation translate = new TranslateAnimation(50, 0, 200, 0);

        // 【3】生成したアニメーションを追加
        set.addAnimation(alpha);
        set.addAnimation(rotate);
        set.addAnimation(scale);
        set.addAnimation(translate);

        // 【4】アニメーション時間を設定して動作開始
        set.setDuration(3000);
        imageView.startAnimation(set);
    }
}
