package com.scanbook.view.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.scanbook.GlApplication;
import com.scanbook.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jay on 15/2/27.
 */
public class GuidePageActivity extends FragmentActivity {

    private ViewPager mVp;
    private List<Fragment> mFgPages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_guidepage);
        mVp=(ViewPager)findViewById(R.id.vp_guidepage);
        mFgPages = new ArrayList<Fragment>();
        Fragment pag1= new GuidePageFragment(1);
        Fragment pag2= new GuidePageFragment(2);
        Fragment pag3= new GuidePageFragment(3);
        Fragment pag4= new GuidePageFragment(4);
        mFgPages.add(pag1);
        mFgPages.add(pag2);
        mFgPages.add(pag3);
        mFgPages.add(pag4);

        mVp.setAdapter(new MyFragmentPagerAdapter(getSupportFragmentManager(), mFgPages));
        mVp.setCurrentItem(0);
    }



    class MyFragmentPagerAdapter extends FragmentPagerAdapter {
        private List<Fragment> list;
        public MyFragmentPagerAdapter(FragmentManager fm,List<Fragment> pages) {
            super(fm);
            this.list = pages;
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Fragment getItem(int arg0) {
            return list.get(arg0);
        }
    }

    @SuppressLint("ValidFragment")
    class GuidePageFragment extends Fragment {
        private int index;

        public GuidePageFragment(int index){
            this.index=index;
        }

        @Override
        public View onCreateView(LayoutInflater inflater,
                                 ViewGroup container,
                                 Bundle savedInstanceState) {
            View v= LayoutInflater.from(getActivity()).inflate(R.layout.fragment_guidepage, container,false);
            RelativeLayout mRl=(RelativeLayout)v.findViewById(R.id.rl_guidepage_back);
            Button btn=(Button)v.findViewById(R.id.btn_guidepage_start);
            if(index==1){
                mRl.setBackgroundResource(R.drawable.page1);
            }else if(index==2){
                mRl.setBackgroundResource(R.drawable.page2);
            }else if(index==3){
                mRl.setBackgroundResource(R.drawable.page3);
            }else{
                mRl.setBackgroundResource(R.drawable.page4);
                btn.setVisibility(View.VISIBLE);
                btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View arg0) {
                        Intent intent=new Intent(getActivity(), MainActivity.class);
                        GlApplication.setisFirst(false);
                        startActivity(intent);
                        getActivity().finish();
                    }
                });
            }
            return v;
        }
    }


}