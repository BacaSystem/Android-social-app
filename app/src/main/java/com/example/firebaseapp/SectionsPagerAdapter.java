package com.example.firebaseapp;

import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

class SectionsPagerAdapter extends FragmentPagerAdapter {
    public SectionsPagerAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {

        switch (position)
        {
            case 2:
                AccountFragment accountFragment = new AccountFragment();
                return  accountFragment;
            case 1:
                SwipeFragment swipeFragment = new SwipeFragment();
                return  swipeFragment;
            case 0:
                ChatsFragment chatsFragment = new ChatsFragment();
                return  chatsFragment;

                default:
                    return null;
        }
    }

    @Override
    public int getCount() {
        return 3;
    }


    public CharSequence getPageTitle(int position) {
        switch (position)
        {
            case 2:
                return "ACCOUNT";
            case 1:
                return  "SWIPE";
            case 0:
                return "CHATS";
                default:
                    return null;
        }
    }
}
