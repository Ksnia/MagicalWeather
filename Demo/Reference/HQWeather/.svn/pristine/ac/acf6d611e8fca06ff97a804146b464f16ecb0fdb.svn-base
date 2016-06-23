package com.huaqin.android.weather.ui;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnPreDrawListener;
import android.view.Window;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;

import com.huaqin.android.weather.R;
import com.huaqin.android.weather.model.OnPanelStatusChangedListener;
import com.huaqin.android.weather.util.DeviceParams;
import com.huaqin.android.weather.util.GlobalConstant.TabPager;
import com.huaqin.android.weather.util.GlobalConstant.TabState;
import com.huaqin.android.weather.util.LogEx;

/***
 * The application entrance
 * 
 * @author MG.Rong
 * @Date:2013/08/09
 */
public class MainActivity extends Activity implements
		GestureDetector.OnGestureListener, OnPanelStatusChangedListener,
		OnTouchListener, OnClickListener {
	/** expansion speeds */
	private final static int INT_SPEED = 20;

	private RelativeLayout mLayoutRoot;
	/** The left operation view */
	private LinearLayout mLayoutOperation;
	/** The right setting view */
	private LinearLayout mLayoutSetting;

	private Button mBtnTemp;
	private Button mBtnTrend;
	private Button mBtnCityManager;
	private Button mBtnSetting;
	private Button mBtnAbout;

	/** Fragment */
	private TemperatureFragment mTempFragment;
	private TrendFragment mTrendFragment;
	private CityManagerFragment mCityManagerFragment;
	private SettingFragment mSettingFragment;
	private AboutFragment mAboutFragment;

	/** Object */
	private GestureDetector mGestureDetector;

	private boolean mHasMeasured = false;
	private int mLayoutOperation_width, mLayoutSetting_width = 0;
	/** expansion limits */
	private int mMaxWidth = 0;
	private boolean mIsScrolling = false;
	private float mScrollX;
	private int mCurrentTab = TabState.DEFAULT;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// remove title bar
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		setContentView(R.layout.main);
		// initialize the screen
		initUI();
		// create fragments
		createViewsAndFragments(savedInstanceState);
	}

	@Override
	protected void onStart() {
		super.onStart();
	}

	@Override
	protected void onResume() {
		super.onResume();
		LogEx.d("[onResume]");
		// Current tab may have changed since the last onSaveInstanceState().
		// Make sure
		// the actual contents match the tab.
		updateFragmentsVisibility();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.main_btn_temperature: { // Temperature
			if (mCurrentTab != TabState.TEMPERATURE) {
				mCurrentTab = TabState.TEMPERATURE;
				updateFragmentsVisibility();
				setOperationViewClosed();
			}
			break;
		}
		case R.id.main_btn_trend: { // Trend
			if (mCurrentTab != TabState.TREND) {
				mCurrentTab = TabState.TREND;
				updateFragmentsVisibility();
				setOperationViewClosed();
			}
			break;
		}
		case R.id.main_btn_citymanage: { // City Manager
			if (mCurrentTab != TabState.CITYMANAGER) {
				mCurrentTab = TabState.CITYMANAGER;
				updateFragmentsVisibility();
				setOperationViewClosed();
			}
			break;
		}
		case R.id.main_btn_setting: { // Setting
			if (mCurrentTab != TabState.SETTING) {
				mCurrentTab = TabState.SETTING;
				updateFragmentsVisibility();
				setOperationViewClosed();
			}
			break;
		}
		case R.id.main_btn_about: { // About
			if (mCurrentTab != TabState.ABOUT) {
				mCurrentTab = TabState.ABOUT;
				updateFragmentsVisibility();
				setOperationViewClosed();
			}
			break;
		}
		default: { // The others
			LogEx.d("Unknow id " + v.getId());
			break;
		}
		}
	}

	@Override
	public boolean onDown(MotionEvent e) {
		mScrollX = 0;
		mIsScrolling = false;
		return true;
	}

	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
			float velocityY) {
		return false;
	}

	@Override
	public void onLongPress(MotionEvent e) {

	}

	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
			float distanceY) {
		LogEx.d("[onScroll] distanceX = " + distanceX + "   distanceY = "
				+ distanceY);
		mIsScrolling = true;
		// distanceX:left is positive number,right is negative number
		mScrollX += distanceX;
		LayoutParams lp = (LayoutParams) mLayoutSetting.getLayoutParams();
		lp.leftMargin = lp.leftMargin - (int) mScrollX;
		if (lp.leftMargin <= 0) {// expansion
			mIsScrolling = false;
			lp.leftMargin = 0;
			onPanelOpened();// open
		}
		if (lp.leftMargin >= mMaxWidth) {// shrink
			mIsScrolling = false;
			lp.leftMargin = mMaxWidth;
			onPanelClosed();// close
		}
		LogEx.d("[onScroll] leftMargin = " + lp.leftMargin);
		mLayoutSetting.setLayoutParams(lp);
		mLayoutSetting.invalidate();
		return true;
	}

	@Override
	public void onShowPress(MotionEvent e) {

	}

	@Override
	public boolean onSingleTapUp(MotionEvent e) {
		LayoutParams lp = (LayoutParams) mLayoutSetting.getLayoutParams();

		if (lp.leftMargin >= mMaxWidth) { // close
			new AsynMove().execute(new Integer[] { -INT_SPEED });
		} else if (lp.leftMargin >= 0) { // open
			new AsynMove().execute(new Integer[] { INT_SPEED });
		}
		return true;
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		if (event.getAction() == MotionEvent.ACTION_UP && mIsScrolling) {
			LayoutParams lp = (LayoutParams) mLayoutSetting.getLayoutParams();
			if (lp.leftMargin >= (mMaxWidth >> 1)) {// din't to go a half
				new AsynMove().execute(new Integer[] { INT_SPEED });
			} else {
				new AsynMove().execute(new Integer[] { -INT_SPEED });
			}
		}
		return mGestureDetector.onTouchEvent(event);
	}

	@Override
	public void onPanelOpened() {

	}

	@Override
	public void onPanelClosed() {

	}

	private void initUI() {

		mBtnTemp = getView(R.id.main_btn_temperature);
		mBtnTrend = getView(R.id.main_btn_trend);
		mBtnCityManager = getView(R.id.main_btn_citymanage);
		mBtnSetting = getView(R.id.main_btn_setting);
		mBtnAbout = getView(R.id.main_btn_about);
		mBtnTemp.setOnClickListener(this);
		mBtnTrend.setOnClickListener(this);
		mBtnCityManager.setOnClickListener(this);
		mBtnSetting.setOnClickListener(this);
		mBtnAbout.setOnClickListener(this);

		// initialize View
		mLayoutOperation = getView(R.id.main_layout_operation);
		mLayoutSetting = getView(R.id.main_layout_setting);
		mLayoutRoot = getView(R.id.main_layout_root);
		// set root view LayoutParams
		FrameLayout.LayoutParams lp = (android.widget.FrameLayout.LayoutParams) mLayoutRoot
				.getLayoutParams();
		lp.width = (int) (DeviceParams.getDeviceWidth() * 1.75);
		mLayoutRoot.setLayoutParams(lp);
		// set setting view LayoutParams
		LayoutParams lpSetting = (LayoutParams) mLayoutSetting
				.getLayoutParams();
		lpSetting.width = (int) (DeviceParams.getDeviceWidth());
		mLayoutSetting.setLayoutParams(lpSetting);
		// set touch listener
		mLayoutSetting.setOnTouchListener(this);
		mLayoutOperation.setOnTouchListener(this);
		// initialize GestureDetector instance
		mGestureDetector = new GestureDetector(this, this);
		mGestureDetector.setIsLongpressEnabled(false);
		// initialize ViewTree observer
		ViewTreeObserver observer = mLayoutSetting.getViewTreeObserver();
		observer.addOnPreDrawListener(new OnPreDrawListener() {

			@Override
			public boolean onPreDraw() {
				if (!mHasMeasured) {
					mHasMeasured = true;
					// get measure width
					mLayoutOperation_width = mLayoutOperation
							.getMeasuredWidth();
					mLayoutSetting_width = mLayoutSetting.getMeasuredWidth();
					mMaxWidth = mLayoutOperation_width - mLayoutSetting_width;
				}
				return true;
			}
		});
	}

	private void createViewsAndFragments(Bundle savedState) {
		final FragmentManager fragmentManager = getFragmentManager();

		// Hide all tabs (the current tab will later be reshown once a tab is
		// selected)
		final FragmentTransaction transaction = fragmentManager
				.beginTransaction();
		// Create the fragments and add as children of the view pager.
		// The pager adapter will only change the visibility; it'll never
		// create/destroy fragments.
		// However, if it's after screen rotation, the fragments have been
		// re-created by
		// the fragment manager, so first see if there're already the target
		// fragments existing.
		mTempFragment = (TemperatureFragment) fragmentManager
				.findFragmentByTag(TabPager.TEMPERATURE_TAG);
		mTrendFragment = (TrendFragment) fragmentManager
				.findFragmentByTag(TabPager.TREND_TAG);
		mCityManagerFragment = (CityManagerFragment) fragmentManager
				.findFragmentByTag(TabPager.CITYMANAGER_TAG);
		mSettingFragment = (SettingFragment) fragmentManager
				.findFragmentByTag(TabPager.SETTING_TAG);
		mAboutFragment = (AboutFragment) fragmentManager
				.findFragmentByTag(TabPager.ABOUT_TAG);

		if (mAboutFragment == null) {
			LogEx.d("Create Fragment");
			// create fragment
			mTempFragment = new TemperatureFragment();
			mTrendFragment = new TrendFragment();
			mCityManagerFragment = new CityManagerFragment();
			mSettingFragment = new SettingFragment();
			mAboutFragment = new AboutFragment();
			// add to transaction
			transaction.add(R.id.main_layout_setting, mTempFragment,
					TabPager.TEMPERATURE_TAG);
			transaction.add(R.id.main_layout_setting, mTrendFragment,
					TabPager.TREND_TAG);
			transaction.add(R.id.main_layout_setting, mCityManagerFragment,
					TabPager.CITYMANAGER_TAG);
			transaction.add(R.id.main_layout_setting, mSettingFragment,
					TabPager.SETTING_TAG);
			transaction.add(R.id.main_layout_setting, mAboutFragment,
					TabPager.ABOUT_TAG);
		}
		// Hide all fragments for now. We adjust visibility when we get
		// onSelectedTabChanged()
		transaction.hide(mTempFragment);
		transaction.hide(mTrendFragment);
		transaction.hide(mCityManagerFragment);
		transaction.hide(mSettingFragment);
		transaction.hide(mAboutFragment);

		transaction.commitAllowingStateLoss();
		fragmentManager.executePendingTransactions();
		invalidateOptionsMenu();
	}

	private void updateFragmentsVisibility() {
		LogEx.d("[updateFragmentsVisibility] mCurrentTab = " + mCurrentTab);
		// set View visibility
		switch (mCurrentTab) {

		}
		FragmentManager fragmentManager = getFragmentManager();
		FragmentTransaction ft = fragmentManager.beginTransaction();
		// Hide all fragments for now. We adjust visibility when we get
		// onSelectedTabChanged()
		hideFragment(ft, mTempFragment);
		hideFragment(ft, mTrendFragment);
		hideFragment(ft, mCityManagerFragment);
		hideFragment(ft, mSettingFragment);
		hideFragment(ft, mAboutFragment);
		// set fragment visibility
		switch (mCurrentTab) {
		case TabState.TEMPERATURE: {
			showFragment(ft, mTempFragment);
			break;
		}
		case TabState.TREND: {
			showFragment(ft, mTrendFragment);
			break;
		}
		case TabState.CITYMANAGER: {
			showFragment(ft, mCityManagerFragment);
			break;
		}
		case TabState.SETTING: {
			showFragment(ft, mSettingFragment);
			break;
		}
		case TabState.ABOUT: {
			showFragment(ft, mAboutFragment);
			break;
		}
		default: {
			break;
		}
		}
		// When switching tabs, we need to invalidate options menu, but
		// executing a
		// fragment transaction does it implicitly. We don't have to call
		// invalidateOptionsMenu
		// manually.
		LogEx.d("[updateFragmentsVisibility] ft.isEmpty() = " + ft.isEmpty());
		if (!ft.isEmpty()) {
			ft.commitAllowingStateLoss();
			fragmentManager.executePendingTransactions();
		}
	}

	private void setOperationViewClosed() {
		LayoutParams lp = (LayoutParams) mLayoutSetting.getLayoutParams();
		mIsScrolling = false;
		lp.leftMargin = 0;
		mLayoutSetting.setLayoutParams(lp);
		mLayoutSetting.invalidate();
	}

	/**
	 * Convenient version of {@link FragmentManager#findFragmentById(int)},
	 * which throws an exception if the fragment doesn't exist.
	 */
	@SuppressWarnings("unchecked")
	public <T extends Fragment> T getFragment(int id) {
		T result = (T) getFragmentManager().findFragmentById(id);
		if (result == null) {
			throw new IllegalArgumentException("fragment 0x"
					+ Integer.toHexString(id) + " doesn't exist");
		}
		return result;
	}

	/**
	 * Convenient version of {@link #findViewById(int)}, which throws an
	 * exception if the view doesn't exist.
	 */
	@SuppressWarnings("unchecked")
	public <T extends View> T getView(int id) {
		T result = (T) findViewById(id);
		if (result == null) {
			throw new IllegalArgumentException("view 0x"
					+ Integer.toHexString(id) + " doesn't exist");
		}
		return result;
	}

	protected static void showFragment(FragmentTransaction ft, Fragment f) {
		if ((f != null) && f.isHidden())
			ft.show(f);
	}

	protected static void hideFragment(FragmentTransaction ft, Fragment f) {
		if ((f != null) && !f.isHidden())
			ft.hide(f);
	}

	class AsynMove extends AsyncTask<Integer, Integer, Void> {

		@Override
		protected Void doInBackground(Integer... params) {
			int times;
			if (mMaxWidth % Math.abs(params[0]) == 0)
				times = mMaxWidth / Math.abs(params[0]);
			else
				times = mMaxWidth / Math.abs(params[0]) + 1;

			for (int i = 0; i < times; i++) {
				publishProgress(params);
				try {
					Thread.sleep(Math.abs(params[0]));
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			return null;
		}

		@Override
		protected void onProgressUpdate(Integer... params) {
			LayoutParams lp = (LayoutParams) mLayoutSetting.getLayoutParams();
			if (params[0] < 0) {
				lp.leftMargin = Math.max(lp.leftMargin + params[0], 0);
			} else {
				lp.leftMargin = Math.min(lp.leftMargin + params[0], mMaxWidth);
			}

			if (lp.leftMargin <= 0) { // expansion
				onPanelOpened();
			} else if (lp.leftMargin >= mMaxWidth) {// shrink
				onPanelClosed();
			}
			LogEx.d("[onProgressUpdate] leftMargin = " + lp.leftMargin);
			mLayoutSetting.setLayoutParams(lp);
		}

	}
}
