package com.huaqin.android.weather.util;

import com.huaqin.android.weather.R;

import android.content.Context;
import android.content.res.Resources;
import android.widget.Toast;

public class ToastManager {

	private static ToastManager mInstance;
	private static Context mContext;

	public static ToastManager getInstance(Context context) {
		if (mInstance == null) {
			mInstance = new ToastManager();
			mContext = context;
		}
		return mInstance;
	}

	public static void makeToast(int iType) {
		switch (iType) {
			case GlobalConstant.Toast.TOAST_NOT_INITILIZE: {
				Toast.makeText(mContext,
						getResources().getString(R.string.toast_choice_city),
						Toast.LENGTH_SHORT).show();
			}
			case GlobalConstant.Toast.TOAST_SEARCH_CONDITION_NULL: {
				Toast.makeText(
						mContext,
						getResources().getString(
								R.string.toast_search_condition_null),
						Toast.LENGTH_SHORT).show();
				break;
			}
			case GlobalConstant.Toast.TOAST_SEARCH_RESULT_NULL: {
				Toast.makeText(
						mContext,
						getResources().getString(
								R.string.toast_search_result_null),
						Toast.LENGTH_SHORT).show();
				break;
			}
			case GlobalConstant.Toast.TOAST_IS_DEFAULT: {
				Toast.makeText(mContext,
						getResources().getString(R.string.toast_is_default),
						Toast.LENGTH_SHORT).show();
				break;
			}
			default: {
				break;
			}
		}
	}

	private static Resources getResources() {
		return mContext.getApplicationContext().getResources();
	}

	private ToastManager() {
	}
}
