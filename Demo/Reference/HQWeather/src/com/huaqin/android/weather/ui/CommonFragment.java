package com.huaqin.android.weather.ui;

import android.app.Fragment;
import android.os.Bundle;

public abstract class CommonFragment extends Fragment {

	protected abstract void createViewsAndObject();

	protected abstract void loadDataAndShown();

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		// crate view
		createViewsAndObject();
	}

	@Override
	public void onResume() {
		super.onResume();
		// load data
		loadDataAndShown();
	}
}
