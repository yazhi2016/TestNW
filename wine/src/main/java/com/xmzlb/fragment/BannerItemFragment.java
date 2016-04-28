package com.xmzlb.fragment;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import java.util.ArrayList;

import com.xmzlb.model.Player;
import com.xmzlb.wine.R;
import com.xinbo.utils.UILUtils;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

/**
 * A simple {@link Fragment} subclass.
 */
@SuppressLint("ValidFragment")
public class BannerItemFragment extends Fragment {

    private int position;
    private ImageView image;
    private View layout;
    ArrayList<Player> imaList = new ArrayList<Player>();
    boolean isGetData = false;
    private Intent intent;

    public BannerItemFragment(int position) {
        this.position = position;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (layout == null) {
            layout = inflater.inflate(R.layout.fragment_banner_item, container, false);
            image = (ImageView) layout.findViewById(R.id.imageView1);

            BannerBroadcast bannerBroadcast = new BannerBroadcast();
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction("banner");
            getActivity().registerReceiver(bannerBroadcast, intentFilter);
        }
        return layout;
    }


    public void changePosition(int num) {
        position = num;
        Intent intent2 = new Intent();
        intent2.setAction("banner");
        getActivity().sendBroadcast(intent2);
    }

    class BannerBroadcast extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (imaList.size() != 0) {
                UILUtils.displayImageNoAnim(imaList.get(position).getPhoto().getUrl(), image);
            }
        }
    }

}
