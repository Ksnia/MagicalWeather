package com.huaqin.android.weather.ui;

import java.util.List;
import java.util.Vector;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.huaqin.android.weather.R;
import com.huaqin.android.weather.model.City;
import com.huaqin.android.weather.util.DBService;
import com.huaqin.android.weather.util.GlobalConstant;
import com.huaqin.android.weather.util.PreferencesManager;
import com.huaqin.android.weather.util.ToastManager;

/**
 * @Description The class to manage the cities
 * @author MG.Rong
 * @version 1.0
 * @date 2013-8-22
 */
public class CityManagerFragment extends CommonFragment implements
		OnItemLongClickListener {

	/** View declare */
	private View mRootView;
	private GridView mGvewCity;
	private ListView mLvewCity;

	/** Objects declare */
	private Vector<City> mListCities = new Vector<City>();
	private CityAdapter mCityAdapter;
	private DBService mDb;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mRootView = inflater.inflate(R.layout.citymanager_fragment, container,
				false);
		return mRootView;
	}

	@Override
	protected void createViewsAndObject() {
		// bind view and create obejects
		mGvewCity = (GridView) mRootView
				.findViewById(R.id.citymanager_gvew_manage);
		mLvewCity = (ListView) mRootView
				.findViewById(R.id.citymanager_lvew_manage);
		// see @GlobalConstant.CityManager
		if (GlobalConstant.CityManager.CITYMANAGER_VIEW_AS_LIST) {
			mLvewCity.setVisibility(View.VISIBLE);
		} else {
			mGvewCity.setVisibility(View.VISIBLE);
		}
		// initialize objects
		mDb = new DBService(getActivity().getApplicationContext());
	}

	@Override
	protected void loadDataAndShown() {
		CityLoader loader = new CityLoader();
		loader.execute("");
	}

	@Override
	public boolean onItemLongClick(AdapterView<?> arg0, View view,
			final int iPisition, long arg3) {
		// to make sure the last one is add button
		if (iPisition == mListCities.size() - 1) {
			return true;
		}
		// make a toast to hint current city is default city
		if (PreferencesManager.getWoeid().equals(
				mListCities.get(iPisition).getWoeid())) {
			ToastManager.makeToast(GlobalConstant.Toast.TOAST_IS_DEFAULT);
			return true;
		}
		// show single choice dialog
		new AlertDialog.Builder(getActivity())
				.setTitle(
						getResources()
								.getText(R.string.setting_setdefault_city))
				.setMessage(mListCities.get(iPisition).getName())
				.setPositiveButton(
						getResources().getText(R.string.setting_positive),
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								// close the AlertDialog
								if (dialog != null){
									dialog.dismiss();
								}
								// set default city
								PreferencesManager.setWoeid(mListCities.get(
										iPisition).getWoeid());
								PreferencesManager.setCity(mListCities.get(
										iPisition).getName());
								// refresh the screen
								update();
								// refresh widget
								Intent intent = new Intent();
								intent.setAction(GlobalConstant.BroadCastAction.WEATHER_UPDATE_CITY);
								getActivity().sendBroadcast(intent);
							}
						})
				.setNegativeButton(
						getResources().getText(R.string.setting_negative),
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {

							}
						}).show();
		return true;
	}

	private void update() {
		if (mCityAdapter == null) {
			mCityAdapter = new CityAdapter();
			// see @GlobalConstant.CityManager
			if (GlobalConstant.CityManager.CITYMANAGER_VIEW_AS_LIST) {
				mLvewCity.setAdapter(mCityAdapter);
				mLvewCity.setOnItemLongClickListener(this);
			} else {
				mGvewCity.setAdapter(mCityAdapter);
				mGvewCity.setOnItemLongClickListener(this);
			}
		}
		mCityAdapter.notifyDataSetChanged();
	}

	private void searchCity() {
		Intent intent = new Intent();
		intent.setClass(getActivity(), SearchActivity.class);
		getActivity().startActivity(intent);
	}

	/**
	 * @Description:Inner class of GridView adapter
	 * @author MG.Rong
	 */
	private class CityAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			return mListCities.size();
		}

		@Override
		public Object getItem(int iPosition) {
			return mListCities.get(iPosition);
		}

		@Override
		public long getItemId(int iPosition) {
			return iPosition;
		}

		@Override
		public View getView(int iPosition, View view, ViewGroup arg2) {
			ViewHolder holder;
			if (view == null) {
				// see @GlobalConstant.CityManager
				if (GlobalConstant.CityManager.CITYMANAGER_VIEW_AS_LIST) {
					view = LayoutInflater.from(getActivity()).inflate(
							R.layout.citymanager_lvew_item_view, null);
				} else {
					view = LayoutInflater.from(getActivity()).inflate(
							R.layout.citymanager_gvew_item_view, null);
					AbsListView.LayoutParams param = new AbsListView.LayoutParams(
							android.view.ViewGroup.LayoutParams.MATCH_PARENT,
							getResources().getDimensionPixelSize(
									R.dimen.citymanager_gvew_item_height));
					view.setLayoutParams(param);
				}
				holder = new ViewHolder();
				holder.tvewCity = (TextView) view
						.findViewById(R.id.citymanager_gvew_item_tvew_city);
				holder.ilyoutAddCity = (LinearLayout) view
						.findViewById(R.id.citymanager_gvew_item_lyout_add);
				holder.iVewDeleteCity = (ImageView) view
						.findViewById(R.id.citymanager_gvew_item_ivew_delete);
				holder.iVewDefault = (ImageView) view
						.findViewById(R.id.citymanager_gvew_item_ivew_default);
				view.setTag(holder);
			} else {
				holder = (ViewHolder) view.getTag();
			}

			// bind data
			if (iPosition == mListCities.size() - 1) {
				// there is no valid data and then the view show added
				holder.ilyoutAddCity.setVisibility(View.VISIBLE);
				holder.ilyoutAddCity.setOnClickListener(new ItemClick(
						iPosition, ItemClick.TYPE_ADD));
				holder.tvewCity.setVisibility(View.GONE);
				holder.iVewDeleteCity.setVisibility(View.GONE);
				holder.iVewDefault.setVisibility(View.GONE);
			} else {
				// set city name visible
				holder.tvewCity.setVisibility(View.VISIBLE);
				holder.tvewCity.setText(mListCities.get(iPosition).getName());
				holder.iVewDeleteCity.setVisibility(View.VISIBLE);
				holder.iVewDeleteCity.setOnClickListener(new ItemClick(
						iPosition, ItemClick.TYPE_DELETE));
				holder.ilyoutAddCity.setVisibility(View.GONE);
				// if current city is default city shown the flag
				if (PreferencesManager.getWoeid().equals(
						mListCities.get(iPosition).getWoeid())) {
					holder.iVewDefault.setVisibility(View.VISIBLE);
				} else {
					holder.iVewDefault.setVisibility(View.GONE);
				}
			}
			return view;
		}
	}

	/**
	 * @Description:Inner class of view click
	 * @author MG.Rong
	 */
	private class ItemClick implements OnClickListener {

		public static final int TYPE_ADD = 0;
		public static final int TYPE_DELETE = 1;
		private int type = -1;
		private int index = 0;

		public ItemClick(int index, int type) {
			this.type = type;
			this.index = index;
		}

		@Override
		public void onClick(View v) {
			switch (type) {
				case TYPE_ADD: { // add city
					searchCity();
					break;
				}
				case TYPE_DELETE: { // delete city
					City city = mListCities.get(index);
					// clear database
					if (mDb != null) {
						mDb.clearDbByCity(mListCities.get(index).getWoeid());
					}
					// remove the city
					mListCities.remove(index);
					// update screen
					update();
					// set default selected city
					if (city != null) {
						// current delete city is default city
						if (PreferencesManager.getWoeid().equals(
								city.getWoeid())) {
							// if city list is null
							if (mListCities.size() == 1) {
								PreferencesManager.setWoeid("");
								PreferencesManager.setCity("");
							} else if (mListCities.size() > 1) {
								// select the last city of list as default city
								PreferencesManager.setWoeid(mListCities.get(
										mListCities.size() - 2).getWoeid());
								PreferencesManager.setCity(mListCities.get(
										mListCities.size() - 2).getName());
							}
						}
					}
					break;
				}
			}
		}
	}

	/**
	 * @Description:Inner class to load cities data
	 * @author MG.Rong
	 */
	private class CityLoader extends AsyncTask<String, Integer, List<City>> {

		@Override
		protected List<City> doInBackground(String... params) {
			List<City> listCites = mDb.queryCities();
			return listCites;
		}

		@Override
		protected void onPostExecute(List<City> result) {
			super.onPostExecute(result);
			// clear catch data
			mListCities.clear();
			// add the result to list
			if (result != null && result.size() > 0) {
				mListCities.addAll(result);
			}
			// to make sure the last one data is null and the icon shown as
			// added
			mListCities.add(new City());
			// update screen
			update();
		}
	}

	static class ViewHolder {
		TextView tvewCity;
		LinearLayout ilyoutAddCity;
		ImageView iVewDeleteCity;
		ImageView iVewDefault;
	}
}
