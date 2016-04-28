package com.xmzlb.util;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.yuchen.lib.R;

public class ViewPagerIndicator extends View {
	private static int CX ;
	private static  int CY ;
	private static float radiusSize = 10;
	//间距
	private static float juli = 10;
	private Paint paint;
	private Paint paint2;
	private Paint paint3;
	private int offset;
	private int layout_position;
	private int indicatorCount;
	int num = 1;

	@Override
	protected void onDraw(Canvas canvas) {
		if (CX == 0){
			int width = getWidth();
			//调整Y轴高度
			CY = getHeight() / 2;
			switch (layout_position) {
			case 0:// 居中
				CX = (int) (width/2 - (indicatorCount - 1) * 1.5 * juli);
				break;
			case 1:// 居左
				CX = (int) (2 * radiusSize);
				break;
			case 2:// 居右
				CX = (int) (width - indicatorCount * 3 * radiusSize);
				break;

			default:
				break;
			}
		}
		for (int i = 0; i < indicatorCount; i++) {
			canvas.drawCircle(CX + i * 3 * juli, CY, radiusSize, paint);
		}
		
		canvas.drawCircle(CX + offset, CY, radiusSize, paint3);
	}

	public void move(float percent, int position) {
//		offset = (int) ((percent + position) * 3 * juli);
		offset = (int) ((position) * 3 * juli);
		invalidate();
	}

	private void initPaint() {
		paint = new Paint(Paint.ANTI_ALIAS_FLAG);
		paint.setColor(Color.parseColor("#ffffff"));
		paint.setTextSize(26);
		paint3 = new Paint(Paint.ANTI_ALIAS_FLAG);
		paint3.setColor(Color.parseColor("#AA8D31"));
//		paint3.setColor(Color.parseColor("#00000"));
		paint3.setTextSize(26);
//		paint2 = new Paint(Paint.ANTI_ALIAS_FLAG);
//		paint2.setColor(Color.BLACK);
//		paint2.setStyle(Style.STROKE);// 绘制空心
//		paint2.setStrokeWidth(2);
//		paint2.setTextSize(26);
	}

	/**
	 * 修改指示器数量
	 * @param num 数量
     */
	public void changeNum(int num) {
		this.num = num;
		invalidate();
	}

	public ViewPagerIndicator(Context context, AttributeSet attrs) {
		super(context, attrs);
		TypedArray array = context.obtainStyledAttributes(attrs,
				R.styleable.ViewPagerIndicator);
		radiusSize = array.getDimension(
				//半径
				R.styleable.ViewPagerIndicator_radiusSize, 7);
		layout_position = array.getInt(R.styleable.ViewPagerIndicator_layout_position, 0);
		//个数
		indicatorCount = array.getInteger(R.styleable.ViewPagerIndicator_indicatorCount, num);
		array.recycle();// 释放内存
		initPaint();
	}

}
