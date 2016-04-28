package com.xmzlb.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import com.facebook.drawee.view.SimpleDraweeView;
import com.xmzlb.wine.NewDetailActivity;
import com.xmzlb.wine.R;
import com.xmzlb.zyzutil.YazhiUtils;

/**
 * A simple {@link Fragment} subclass.
 *
 */
@SuppressLint("ValidFragment")
public class BannerItemFragment2 extends Fragment {
	private SimpleDraweeView image;
	private String imageUrl="";
	String goods_id="";

	
	public BannerItemFragment2() {
		
	}
	
	public BannerItemFragment2(String imageUrl, String goods_id) {
		this.imageUrl = imageUrl;
		this.goods_id = goods_id;
	}

	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View layout = inflater.inflate(R.layout.fragment_banner_item2, container, false);
		image = (SimpleDraweeView) layout.findViewById(R.id.imageView1);
		if (imageUrl.isEmpty()) {
			image.setImageResource(R.drawable.white);
		} else {
//			UILUtils.displayImageNoAnim(imageUrl, image);
			YazhiUtils.setImage(imageUrl, image);
		}
		image.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (!goods_id.isEmpty()) {
					Intent intent = new Intent(getActivity(), NewDetailActivity.class);
					intent.putExtra("goods_id", goods_id);
					startActivity(intent);
				}
			}
		});
		return layout;
	}

}
