package com.jqsoft.nursing.di.ui.selectadress.views;

/*
 * Copyright (C) 2013 Andreas Stuetz <andreas.stuetz@gmail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.jqsoft.nursing.R;
import com.jqsoft.nursing.di.ui.selectadress.callback.TabOnClickListener;

import java.util.Locale;


public class PagerSlidingTabStrip extends HorizontalScrollView {

	public interface IconTabProvider {
		public int getPageIconResId(int position);
	}

	// @formatter:off
	private static final int[] ATTRS = new int[] { android.R.attr.textSize,
			android.R.attr.textColor };
	// @formatter:on

	private LinearLayout.LayoutParams defaultTabLayoutParams;
	private LinearLayout.LayoutParams expandedTabLayoutParams;

	public OnPageChangeListener delegatePageListener;

	private LinearLayout tabsContainer;
	// private ViewPager pager;

	private int tabCount;

	private int currentPosition = 0;
	private float currentPositionOffset = 0f;

	private Paint rectPaint;
	private Paint dividerPaint;

	private int indicatorColor = 0xFF666666;
	private int underlineColor = 0x1A000000;
	private int dividerColor = 0x1A000000;

	private boolean shouldExpand = false;
	private boolean textAllCaps = true;

	private int scrollOffset = 52;
	private int indicatorHeight = 8;
	private int underlineHeight = 2;
	private int dividerPadding = 12;
	private int tabPadding = 24;
	private int dividerWidth = 1;

	private int tabTextSize = 12;
	private int tabTextColor = 0xFF999999;
	private int defulatColor = 0xFFFF5263;
	private Typeface tabTypeface = null;
	private int tabTypefaceStyle = Typeface.BOLD;

	private int tabBackgroundResId = R.drawable.background_tab;

	private Locale locale;
	private  boolean isFrist=true;
	private String[] tabs;
	private TabOnClickListener mTabOnClickListener;

	public PagerSlidingTabStrip(Context context) {
		this(context, null);
	}

	public PagerSlidingTabStrip(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public void setCurrentPosition(int currentPosition) {
		setSelectTextColor(currentPosition);
		this.currentPosition = currentPosition;
	}

	public void setSelectedColor(int color) {
		defulatColor = color;
	}
	@SuppressWarnings("ResourceType")
	public PagerSlidingTabStrip(Context context, AttributeSet attrs,
                                int defStyle) {
		super(context, attrs, defStyle);
		setBackgroundColor(Color.WHITE);
		setFillViewport(true);
		setWillNotDraw(false);

		tabsContainer = new LinearLayout(context);
		tabsContainer.setOrientation(LinearLayout.HORIZONTAL);
		tabsContainer.setLayoutParams(new LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		addView(tabsContainer);

		DisplayMetrics dm = getResources().getDisplayMetrics();

		scrollOffset = (int) TypedValue.applyDimension(
				TypedValue.COMPLEX_UNIT_DIP, scrollOffset, dm);
		indicatorHeight = (int) TypedValue.applyDimension(
				TypedValue.COMPLEX_UNIT_DIP, indicatorHeight, dm);
		underlineHeight = (int) TypedValue.applyDimension(
				TypedValue.COMPLEX_UNIT_DIP, underlineHeight, dm);
		dividerPadding = (int) TypedValue.applyDimension(
				TypedValue.COMPLEX_UNIT_DIP, dividerPadding, dm);
		tabPadding = (int) TypedValue.applyDimension(
				TypedValue.COMPLEX_UNIT_DIP, tabPadding, dm);
		dividerWidth = (int) TypedValue.applyDimension(
				TypedValue.COMPLEX_UNIT_DIP, dividerWidth, dm);
		tabTextSize = (int) TypedValue.applyDimension(
				TypedValue.COMPLEX_UNIT_SP, tabTextSize, dm);

		// get system attrs (android:textSize and android:textColor)

		TypedArray a = context.obtainStyledAttributes(attrs, ATTRS);

		tabTextSize = a.getDimensionPixelSize(0, tabTextSize);
		tabTextColor = a.getColor(1, tabTextColor);

		a.recycle();

		// get custom attrs

		a = context.obtainStyledAttributes(attrs,
				R.styleable.PagerSlidingTabStrip);

		indicatorColor = a.getColor(
				R.styleable.PagerSlidingTabStrip_pstsIndicatorColor,
				indicatorColor);
		underlineColor = a.getColor(
				R.styleable.PagerSlidingTabStrip_pstsUnderlineColor,
				underlineColor);
		dividerColor = a
				.getColor(R.styleable.PagerSlidingTabStrip_pstsDividerColor,
						dividerColor);
		indicatorHeight = a.getDimensionPixelSize(
				R.styleable.PagerSlidingTabStrip_pstsIndicatorHeight,
				indicatorHeight);
		underlineHeight = a.getDimensionPixelSize(
				R.styleable.PagerSlidingTabStrip_pstsUnderlineHeight,
				underlineHeight);
		dividerPadding = a.getDimensionPixelSize(
				R.styleable.PagerSlidingTabStrip_pstsDividerPadding,
				dividerPadding);
		tabPadding = a.getDimensionPixelSize(
				R.styleable.PagerSlidingTabStrip_pstsTabPaddingLeftRight,
				tabPadding);
		tabBackgroundResId = a.getResourceId(
				R.styleable.PagerSlidingTabStrip_pstsTabBackground,
				tabBackgroundResId);
		shouldExpand = a
				.getBoolean(R.styleable.PagerSlidingTabStrip_pstsShouldExpand,
						shouldExpand);
		scrollOffset = a
				.getDimensionPixelSize(
						R.styleable.PagerSlidingTabStrip_pstsScrollOffset,
						scrollOffset);
		textAllCaps = a.getBoolean(
				R.styleable.PagerSlidingTabStrip_pstsTextAllCaps, textAllCaps);

		a.recycle();

		rectPaint = new Paint();
		rectPaint.setAntiAlias(true);
		rectPaint.setStyle(Style.FILL);

		dividerPaint = new Paint();
		dividerPaint.setAntiAlias(true);
		dividerPaint.setStrokeWidth(dividerWidth);

		defaultTabLayoutParams = new LinearLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
		expandedTabLayoutParams = new LinearLayout.LayoutParams(0,
				LayoutParams.MATCH_PARENT, 1.0f);

		if (locale == null) {
			locale = getResources().getConfiguration().locale;
		}
	}

	// 设置标签列表
	public void setTabsText(String[] tabs) {
		this.tabs = tabs;
		notifyDataSetChanged();
	}

	public String[] getTabs() {
		return tabs;
	}

	public void setOnPageChangeListener(OnPageChangeListener listener) {
		this.delegatePageListener = listener;
	}

	public void notifyDataSetChanged() {

		tabsContainer.removeAllViews();

		tabCount = tabs.length;

		for (int i = 0; i < tabCount; i++) {
			addTextTab(i, tabs[i]);
		}

		updateTabStyles();

		// getViewTreeObserver().addOnGlobalLayoutListener(new
		// OnGlobalLayoutListener() {
		//
		// @SuppressWarnings("deprecation")
		// @SuppressLint("NewApi")
		// @Override
		// public void onGlobalLayout() {
		//
		// if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
		// getViewTreeObserver().removeGlobalOnLayoutListener(this);
		// } else {
		// getViewTreeObserver().removeOnGlobalLayoutListener(this);
		// }
		//
		// //currentPosition = pager.getCurrentItem();
		// scrollToChild(currentPosition, 0);
		// }
		// });

	}

	private void addTextTab(final int position, String title) {

		TextView tab = new TextView(getContext());
		tab.setText(title);
		tab.setGravity(Gravity.CENTER);
		tab.setSingleLine();

		addTab(position, tab);
	}

	// private void addIconTab(final int position, int resId) {
	//
	// ImageButton tab = new ImageButton(getContext());
	// tab.setImageResource(resId);
	//
	// addTab(position, tab);
	//
	// }
	// 设置点击事件
	public void setTabOnClickListener(TabOnClickListener listener) {
		this.mTabOnClickListener = listener;
	}

	private void addTab(final int position, final View tab) {
		tab.setFocusable(true);
		tab.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				mTabOnClickListener.onClick(tab, position);
			}
		});

		tab.setPadding(tabPadding, 0, tabPadding, 0);
		tabsContainer
				.addView(tab, position, shouldExpand ? expandedTabLayoutParams
						: defaultTabLayoutParams);
	}

	@SuppressLint("NewApi")
	private void updateTabStyles() {

		for (int i = 0; i < tabCount; i++) {

			View v = tabsContainer.getChildAt(i);

			v.setBackgroundResource(tabBackgroundResId);

			if (v instanceof TextView) {

				TextView tab = (TextView) v;
				tab.setTextSize(TypedValue.COMPLEX_UNIT_PX, tabTextSize);
				tab.setTypeface(tabTypeface, tabTypefaceStyle);
				if (i == 0) {
					tab.setTextColor(defulatColor);
				} else {
					tab.setTextColor(tabTextColor);
				}

				// setAllCaps() is only available from API 14, so the upper case
				// is made manually if we are on a
				// pre-ICS-build

				if (textAllCaps) {
					if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
						tab.setAllCaps(true);
					} else {
						tab.setText(tab.getText().toString()
								.toUpperCase(locale));
					}
				}
			}
		}


	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		if (isInEditMode() || tabCount == 0) {
			return;
		}

		final int height = getHeight();

		// draw indicator line

		rectPaint.setColor(indicatorColor);

		// default: line below current tab
		View currentTab = tabsContainer.getChildAt(currentPosition);
		float lineLeft = currentTab.getLeft();
		float lineRight = currentTab.getRight();
		if (isFrist){
			scrollTo(tabsContainer.getRight(),0);
			isFrist=false;
		}
		// if there is an offset, start interpolating left and right coordinates
		// between current and next tab
		if (currentPositionOffset > 0f && currentPosition < tabCount - 1) {

			View nextTab = tabsContainer.getChildAt(currentPosition + 1);
			final float nextTabLeft = nextTab.getLeft();
			final float nextTabRight = nextTab.getRight();

			lineLeft = (currentPositionOffset * nextTabLeft + (1f - currentPositionOffset)
					* lineLeft);
			lineRight = (currentPositionOffset * nextTabRight + (1f - currentPositionOffset)
					* lineRight);
		}

		canvas.drawRect(lineLeft, height - indicatorHeight, lineRight, height,
				rectPaint);

		// draw underline

		rectPaint.setColor(underlineColor);
		canvas.drawRect(0, height - underlineHeight, tabsContainer.getWidth(),
				height, rectPaint);

		// draw divider

		dividerPaint.setColor(dividerColor);
		for (int i = 0; i < tabCount - 1; i++) {
			View tab = tabsContainer.getChildAt(i);
			canvas.drawLine(tab.getRight(), dividerPadding, tab.getRight(),
					height - dividerPadding, dividerPaint);
		}
	}
	public LinearLayout getTabsContainer(){
		return tabsContainer;

	}
	private void setSelectTextColor(int position) {
		for (int i = 0; i < tabCount; i++) {
			View view = tabsContainer.getChildAt(i);
			if (view instanceof ImageButton) {
			} else if (view instanceof TextView) {
				TextView tabTextView = (TextView) view;
				if (position == i) {
					if (position>1){
						int right =tabsContainer.getRight()/tabsContainer.getChildCount()*(position-1);
						scrollTo(right,0);
					}else {
                        scrollTo(0,0);
                    }
					tabTextView.setTextColor(defulatColor);

				} else {
					tabTextView.setTextColor(tabTextColor);

				}
			} else if (view instanceof RelativeLayout) {
				View viewText = ((RelativeLayout) view).getChildAt(0);
				TextView tabTextView = (TextView) viewText;
				if (viewText instanceof TextView) {
					if (position == i) {
						if (position>1){
							int right =tabsContainer.getRight()/tabsContainer.getChildCount()*(position-1);
							scrollTo(right,0);
						}else {
							scrollTo(0,0);
						}
						tabTextView.setTextColor(defulatColor);
					} else {
						tabTextView.setTextColor(tabTextColor);
					}
				}
			}
		}
		invalidate();
	}

	public void setIndicatorColor(int indicatorColor) {
		this.indicatorColor = indicatorColor;
		invalidate();
	}

	public void setIndicatorColorResource(int resId) {
		this.indicatorColor = getResources().getColor(resId);
		invalidate();
	}

	public int getIndicatorColor() {
		return this.indicatorColor;
	}

	public void setIndicatorHeight(int indicatorLineHeightPx) {
		this.indicatorHeight = indicatorLineHeightPx;
		invalidate();
	}

	public int getIndicatorHeight() {
		return indicatorHeight;
	}

	public void setUnderlineColor(int underlineColor) {
		this.underlineColor = underlineColor;
		invalidate();
	}

	public void setUnderlineColorResource(int resId) {
		this.underlineColor = getResources().getColor(resId);
		invalidate();
	}

	public int getUnderlineColor() {
		return underlineColor;
	}

	public void setDividerColor(int dividerColor) {
		this.dividerColor = dividerColor;
		invalidate();
	}

	public void setDividerColorResource(int resId) {
		this.dividerColor = getResources().getColor(resId);
		invalidate();
	}

	public int getDividerColor() {
		return dividerColor;
	}

	public void setUnderlineHeight(int underlineHeightPx) {
		this.underlineHeight = underlineHeightPx;
		invalidate();
	}

	public int getUnderlineHeight() {
		return underlineHeight;
	}

	public void setDividerPadding(int dividerPaddingPx) {
		this.dividerPadding = dividerPaddingPx;
		invalidate();
	}

	public int getDividerPadding() {
		return dividerPadding;
	}

	public void setScrollOffset(int scrollOffsetPx) {
		this.scrollOffset = scrollOffsetPx;
		invalidate();
	}

	public int getScrollOffset() {
		return scrollOffset;
	}

	public void setShouldExpand(boolean shouldExpand) {
		this.shouldExpand = shouldExpand;
		requestLayout();
	}

	public boolean getShouldExpand() {
		return shouldExpand;
	}

	public boolean isTextAllCaps() {
		return textAllCaps;
	}

	public void setAllCaps(boolean textAllCaps) {
		this.textAllCaps = textAllCaps;
	}

	public void setTextSize(int textSizePx) {
		this.tabTextSize = textSizePx;
		updateTabStyles();
	}

	public int getTextSize() {
		return tabTextSize;
	}

	public void setTextColor(int textColor) {
		this.tabTextColor = textColor;
		updateTabStyles();
	}

	public void setTextColorResource(int resId) {
		this.tabTextColor = getResources().getColor(resId);
		updateTabStyles();
	}

	public int getTextColor() {
		return tabTextColor;
	}

	public void setTypeface(Typeface typeface, int style) {
		this.tabTypeface = typeface;
		this.tabTypefaceStyle = style;
		updateTabStyles();
	}

	public void setTabBackground(int resId) {
		this.tabBackgroundResId = resId;
	}

	public int getTabBackground() {
		return tabBackgroundResId;
	}

	public void setTabPaddingLeftRight(int paddingPx) {
		this.tabPadding = paddingPx;
		updateTabStyles();
	}

	public int getTabPaddingLeftRight() {
		return tabPadding;
	}

	@Override
	public void onRestoreInstanceState(Parcelable state) {
		SavedState savedState = (SavedState) state;
		super.onRestoreInstanceState(savedState.getSuperState());
		currentPosition = savedState.currentPosition;
		requestLayout();
	}

	@Override
	public Parcelable onSaveInstanceState() {
		Parcelable superState = super.onSaveInstanceState();
		SavedState savedState = new SavedState(superState);
		savedState.currentPosition = currentPosition;
		return savedState;
	}

	static class SavedState extends BaseSavedState {
		int currentPosition;

		public SavedState(Parcelable superState) {
			super(superState);
		}

		private SavedState(Parcel in) {
			super(in);
			currentPosition = in.readInt();
		}

		@Override
		public void writeToParcel(Parcel dest, int flags) {
			super.writeToParcel(dest, flags);
			dest.writeInt(currentPosition);
		}

		public static final Creator<SavedState> CREATOR = new Creator<SavedState>() {
			@Override
			public SavedState createFromParcel(Parcel in) {
				return new SavedState(in);
			}

			@Override
			public SavedState[] newArray(int size) {
				return new SavedState[size];
			}
		};
	}

}
