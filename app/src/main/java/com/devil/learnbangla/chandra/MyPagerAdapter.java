package com.devil.learnbangla.chandra;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class MyPagerAdapter extends FragmentStateAdapter {


    public MyPagerAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 1:
                return new FamilyFragment();
            case 2:
                return new PhraseFragment();
            case 3:
                return new ColorFragment();
            default:
                return new NumberFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 4;
    }
}
