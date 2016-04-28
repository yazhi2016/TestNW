package com.xmzlb.wine;

import java.util.ArrayList;

import com.xmzlb.fragment.BannerItemFragment3;
import com.xmzlb.util.ViewPagerIndicator2;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * A simple {@link Fragment} subclass.
 *
 */
@SuppressLint("ValidFragment")
public class PagerFragment extends Fragment {

	private PagerBannerAdapter pagerBannerAdapter;
	private Handler mHandler = new Handler();
	boolean finish = false;
	private ViewPager pagerBanner;
	int BANNER_COUNT = 4 * 100000;
	private ViewPagerIndicator2 indicator;
	ArrayList<String> adList = new ArrayList<>();
	boolean isDrag;
	View view = null;

	public PagerFragment(ArrayList<String> adList) {
		this.adList = adList;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		if (view == null) {
			view = inflater.inflate(R.layout.fragment_pager, container, false);
			// 广告轮播
			indicator = (ViewPagerIndicator2) view.findViewById(R.id.indicator_detailact);
			indicator.changeNum(adList.size());
			pagerBanner = (ViewPager) view.findViewById(R.id.pager_banner_detail);
			FragmentManager fm = getChildFragmentManager();
			if (adList.size() <= 1) {
				BANNER_COUNT = 1;
				pagerBanner.setCurrentItem(0);
			} else {
				pagerBanner.setCurrentItem(BANNER_COUNT / 2);
			}
			pagerBannerAdapter = new PagerBannerAdapter(fm);
			pagerBanner.setAdapter(pagerBannerAdapter);
			pagerBanner.setOnPageChangeListener(new MyPageChangeListener());
			if (adList.size() > 1) {
				autoScroll();
			}
		}
		return view;
	}

	class PagerBannerAdapter extends FragmentPagerAdapter {
		public PagerBannerAdapter(FragmentManager fm) {
			super(fm);
		}

		public Fragment getItem(int position) {
			if (adList.size() == 0) {
				return new BannerItemFragment3("", "");
			} else if (adList.size() == 1) {
				String adCode = adList.get(0);
				return new BannerItemFragment3(adCode, "");
			} else {
				position %= adList.size();
				return new BannerItemFragment3(adList.get(position), "");
			}
		}

		@Override
		public int getCount() {
			return BANNER_COUNT;
		}
	}

	class MyPageChangeListener implements OnPageChangeListener {
		public void onPageScrollStateChanged(int state) {
			switch (state) {
			case ViewPager.SCROLL_STATE_DRAGGING:
				isDrag = true;
				break;
			case ViewPager.SCROLL_STATE_IDLE:
				isDrag = false;
				break;
			case ViewPager.SCROLL_STATE_SETTLING:
				isDrag = false;
				break;

			default:
				break;
			}
		}

		public void onPageScrolled(int position, float arg1, int arg2) {
			indicator.move(arg1, position %= adList.size());
		}

		public void onPageSelected(int position) {
			if (adList.size() != 0) {
				// text_title_banner_homefra.setText(imaList.get(position).getTitle());
			}
		}
	}

	@Override
	public void onPause() {
		super.onPause();
		finish = true;
	}

	@Override
	public void onResume() {
		super.onResume();
		finish = false;
	}

	private void autoScroll() {
		// 自动滚动Pager
		mHandler.postDelayed(new Runnable() {
			public void run() {
				int item = pagerBanner.getCurrentItem();
				// 设置自动滚动，可以设置是否有动画
				if (!isDrag && (adList.size() != 0)) {
					Log.e("==", "run");
					// 设置ViewPager滑动
					if (finish) {
						return;
					}
					pagerBanner.setCurrentItem(item + 1);
				}
				mHandler.postDelayed(this, 5000);
			}
		}, 5000);
	}
}
