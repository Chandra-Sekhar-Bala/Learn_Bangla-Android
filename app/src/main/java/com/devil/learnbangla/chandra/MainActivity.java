package com.devil.learnbangla.chandra;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {
    private TextView number,family,color,phrase;
    
    
    TabLayout tabLayout;
    ViewPager2 viewpager;
    FragmentStateAdapter adapter;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        number = findViewById(R.id.numbers);
//        color = findViewById(R.id.color);
//        family = findViewById(R.id.family);
//        phrase = findViewById(R.id.phrase);
//
//        number.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//               Intent n = new Intent(MainActivity.this,NumberActivity.class);
//               MainActivity.this.startActivity(n);
//            }
//        });
//        family.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//               Intent n = new Intent(MainActivity.this,FamilyActivity.class);
//               MainActivity.this.startActivity(n);
//            }
//        });
//        color.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//               Intent n = new Intent(MainActivity.this, ColorActivity.class);
//               MainActivity.this.startActivity(n);
//            }
//        });
//        phrase.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//               Intent n = new Intent(MainActivity.this, PhraseActivity.class);
//               MainActivity.this.startActivity(n);
//            }
//        });

        viewpager = findViewById(R.id.viewpager);
        tabLayout = findViewById(R.id.tabLayout);
        
         FragmentManager fm = getSupportFragmentManager();
          adapter  = new MyPagerAdapter(fm,getLifecycle());
          viewpager.setAdapter(adapter);
          tabLayout.addTab(tabLayout.newTab().setText("Number"));
          tabLayout.addTab(tabLayout.newTab().setText("Family"));
          tabLayout.addTab(tabLayout.newTab().setText("Phrase"));
          tabLayout.addTab(tabLayout.newTab().setText("color"));

          tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
              @Override
              public void onTabSelected(TabLayout.Tab tab) {
                  viewpager.setCurrentItem((tab.getPosition()));
              }

              @Override
              public void onTabUnselected(TabLayout.Tab tab) {

              }

              @Override
              public void onTabReselected(TabLayout.Tab tab) {

              }
          });

          viewpager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
              @Override
              public void onPageSelected(int position) {
                  tabLayout.selectTab(tabLayout.getTabAt(position));
              }
          });

    }
}