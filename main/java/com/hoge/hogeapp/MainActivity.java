package com.hoge.hogeapp;

import android.content.Context;
import android.content.Intent;
import android.databinding.BindingAdapter;
import android.databinding.DataBindingUtil;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.hoge.hogeapp.databinding.ItemTabBinding;
import com.hoge.hogeapp.ItemTabData;


public class MainActivity extends AppCompatActivity {
    public static final int tabLength = 4;
    private View[] tabIconView;
    private View tabView;
    private TabLayout tabLayout;
    private ViewGroup viewGroup;
    private LayoutInflater inflater;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public void onResume() {
        super.onResume();
        onCreateView();
    }

    public void onCreateView() {
        MainFragmentPagerAdapter adapter = new MainFragmentPagerAdapter(getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.pager);
//        viewPager.setOffscreenPageLimit(2);
        viewPager.setAdapter(adapter);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        // create tab
        TabLayout.Tab[] tab = new TabLayout.Tab[tabLength];
        inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        tabIconView = new View[tabLength];
        for(int i = 0; i < tabLength; i++) {
            tab[i] = tabLayout.getTabAt(i);
            switch (i) {
                case 0:
                    bindTabData(R.drawable.ic_cached, R.string.tab1);
                    break;
                case 1:
                    bindTabData(R.drawable.ic_alarm, R.string.tab2);
                    break;
                case 2:
                    bindTabData(R.drawable.ic_notifications, R.string.tab3);
                    break;
                case 3:
                    bindTabData(R.drawable.ic_android, R.string.tab4);
                    break;
                default:
                    break;
            }
            tab[i].setCustomView(tabView);
        }

        // get tabs instance
        viewGroup = (ViewGroup) tabLayout.getChildAt(0);
        for(int tabPosition = 0; tabPosition < tabLength; tabPosition++) {
            View childView = viewGroup.getChildAt(tabPosition);
            tabIconView[tabPosition] = (View) childView.findViewById(R.id.tab_icon);
        }


        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            Context context = getApplicationContext();
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                switch (position) {
                    case 0:
                        startTabAnimation(R.anim.roll_anim, tabIconView[0]);
                        break;
                    case 1:
                        startTabAnimation(R.anim.updown_anim, tabIconView[1]);
                        break;
                    case 2:
                        startTabAnimation(R.anim.invisible_anim, tabIconView[2]);
                        break;
                    case 3:
                        startCompositeAnimation(tabIconView[3]);
                        break;
                    default:
                        break;
                }
                Toast.makeText(context, "position :" + position + " tabSelected", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                switch (position) {
                    case 0:
                        restoreTabState(tabIconView[0]);
                        break;
                    case 1:
                        restoreTabState(tabIconView[1]);
                        break;
                    case 2:
                        restoreTabState(tabIconView[2]);
                        break;
                    case 3:
                        restoreTabState(tabIconView[3]);
                        break;
                    default:
                        break;
                }
                Toast.makeText(context, "position :" + position + " tabUnSelected", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                Toast.makeText(context, "position :" + position + " tabReSelected", Toast.LENGTH_SHORT).show();
            }
        });

        // animate tab1 on CreateView
        startTabAnimation(R.anim.roll_anim, tabIconView[0]);

//        // textView animation
//        View child2View = viewGroup.getChildAt(0);
//        TextView tabTitleView = (TextView) child2View.findViewById(R.id.tab_title);
//        Animation testAnimation = AnimationUtils.loadAnimation(MainActivity.this, R.anim.through_anim);
//        tabTitleView.startAnimation(testAnimation);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    // create tab dataBinding
    private void bindTabData(int drawableRoot, int stringRoot) {
        tabView = inflater.inflate(R.layout.item_tab, null);

        ItemTabBinding binding = ItemTabBinding.bind(tabView);
        Drawable drawable = ResourcesCompat.getDrawable(getResources(), drawableRoot, null);
        binding.setItemTabData(new ItemTabData(drawable, getString(stringRoot)));
    }

    // animation on tab selected
    private void startTabAnimation(int animRoot, View iconView) {
        Animation tabAnimation = AnimationUtils.loadAnimation(MainActivity.this, animRoot);
        iconView.startAnimation(tabAnimation);

        final View view = viewGroup.getChildAt(tabLayout.getSelectedTabPosition());
        view.setScaleX(1.25F);
        view.setScaleY(1.25F);
    }

    // animation on tab unselected
    private void restoreTabState(View iconView) {
        iconView.setAnimation(null);

        final View view = viewGroup.getChildAt(tabLayout.getSelectedTabPosition());
        view.setScaleX(1.0F);
        view.setScaleY(1.0F);
    }

    // composite animation
    private void startCompositeAnimation(View iconView) {
        AlphaAnimation alphaAnimation = new AlphaAnimation(0.9f, 0.2f);
        alphaAnimation.setRepeatCount(Animation.INFINITE);
        alphaAnimation.setRepeatMode(Animation.REVERSE);

        RotateAnimation rotateAnimation = new RotateAnimation(0, 360, 45, 45);
        rotateAnimation.setRepeatCount(Animation.INFINITE);
        rotateAnimation.setRepeatMode(Animation.RESTART);

        AnimationSet animationSet = new AnimationSet(false);

        animationSet.addAnimation(alphaAnimation);
        animationSet.addAnimation(rotateAnimation);
        animationSet.addAnimation(new ScaleAnimation(0.1f, 1, 0.1f, 1));
        animationSet.addAnimation(new TranslateAnimation(50, 0, 150, 0));

        animationSet.setDuration(3000);
        iconView.startAnimation(animationSet);

        final View view = viewGroup.getChildAt(tabLayout.getSelectedTabPosition());
        view.setScaleX(1.25F);
        view.setScaleY(1.25F);
    }

    public class ItemTabData {
        private Drawable tabIcon;
        private String tabTitle;

        private ItemTabData(Drawable tabIcon, String tabTitle) {
            this.tabIcon = tabIcon;
            this.tabTitle = tabTitle;
        }

        public Drawable getTabIcon() {
            return tabIcon;
        }
        public void setTabIcon(Drawable tabIcon) {
            this.tabIcon = tabIcon;
        }

        public String getTabTitle() {
            return tabTitle;
        }
        public void setTabTitle(String tabTitle) {
            this.tabTitle = tabTitle;
        }
    }

}
