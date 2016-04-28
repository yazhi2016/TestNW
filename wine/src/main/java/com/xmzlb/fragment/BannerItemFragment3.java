package com.xmzlb.fragment;

import com.xmzlb.wine.NewDetailActivity;
import com.xmzlb.wine.R;
import com.xinbo.utils.UILUtils;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;

/**
 * A simple {@link Fragment} subclass.
 *
 */
@SuppressLint("ValidFragment")
public class BannerItemFragment3 extends Fragment {
	private ImageView image;
	private String imageUrl;
	String goods_id;

	public BannerItemFragment3(String imageUrl, String goods_id) {
		this.imageUrl = imageUrl;
		this.goods_id = goods_id;
	}

	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View layout = inflater.inflate(R.layout.fragment_banner_item3, container, false);
		image = (ImageView) layout.findViewById(R.id.imageView1);
		if (imageUrl.isEmpty()) {
			image.setImageResource(R.drawable.white);
		} else {
			UILUtils.displayImageNoAnim(imageUrl, image);
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
