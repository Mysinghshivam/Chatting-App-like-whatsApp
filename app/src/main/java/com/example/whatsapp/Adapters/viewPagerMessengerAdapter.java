package com.example.whatsapp.Adapters;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.whatsapp.CallFragment;
import com.example.whatsapp.ChatFragment;
import com.example.whatsapp.StatusFragment;

public class viewPagerMessengerAdapter extends FragmentPagerAdapter {

    public viewPagerMessengerAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        if (position == 0){
            ChatFragment cf = new ChatFragment();
            return cf;
        }else if(position == 1){
            StatusFragment sf = new StatusFragment();
            return sf;
        }else{
            CallFragment caf = new CallFragment();
            return caf;
        }
    }

    @Override
    public int getCount() {
        return 3;//number of tabs
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        if (position == 0){
            return "CHATS";
        }else if(position == 1){
            return "STATUS";
        }else {
            return "CALLS";
        }
    }
}


