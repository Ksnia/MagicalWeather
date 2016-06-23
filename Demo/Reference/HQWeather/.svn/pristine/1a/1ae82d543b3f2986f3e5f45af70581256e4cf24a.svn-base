package com.huaqin.android.weather.ui;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SearchView.OnQueryTextListener;
import android.widget.TextView;

import com.huaqin.android.weather.R;
import com.huaqin.android.weather.logic.CityLoader;
import com.huaqin.android.weather.logic.CityLoader.WoeidTaskLoadListener;
import com.huaqin.android.weather.model.City;
import com.huaqin.android.weather.provider.WeatherUpdateService;
import com.huaqin.android.weather.util.DBService;
import com.huaqin.android.weather.util.GlobalConstant;
import com.huaqin.android.weather.util.ToastManager;
import com.huaqin.android.weather.util.PreferencesManager;

public class SearchActivity extends Activity implements WoeidTaskLoadListener,
		OnItemClickListener {

	private SearchView mSearchView;
	private ListView mListView;
	private LinearLayout mLayoutHint;
	private CityLoader mCityLoader;

	private List<City> mListResult = new ArrayList<City>();
	private CityAdapter mAdapter;
	private DBService mDb;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.search_activity);
		initUI();
	}

	private void initUI() {
		mCityLoader = new CityLoader(getApplicationContext(), this);
		mListView = (ListView) findViewById(R.id.search_lvew_result);
		mSearchView = (SearchView) findViewById(R.id.search_searchview);
		mLayoutHint = (LinearLayout) findViewById(R.id.search_llyout_searching);
		mSearchView.setOnQueryTextListener(new Listener());
		mListView.setOnItemClickListener(this);
		mDb = new DBService(getApplicationContext());
	}

	private void update() {
		if (mAdapter == null) {
			mAdapter = new CityAdapter();
			mListView.setAdapter(mAdapter);
		} else {
			mAdapter.notifyDataSetChanged();
		}
	}

	private class Listener implements OnQueryTextListener {

		@Override
		public boolean onQueryTextChange(String newText) {
			return false;
		}

		@Override
		public boolean onQueryTextSubmit(String query) {
			if (null != query && query.length() > 0) {
				mListResult.clear();
				// update
				update();
				// shown the search holding view
				mLayoutHint.setVisibility(View.VISIBLE);
				// start query
				mCityLoader.excute(query);
			} else {
				ToastManager
						.makeToast(GlobalConstant.Toast.TOAST_SEARCH_CONDITION_NULL);
			}
			return true;
		}

	}

	@Override
	public void onWoeidLoadFinished(List<City> result) {
		mLayoutHint.setVisibility(View.GONE);
		if (null != result) {
			mListResult.addAll(result);
			// update the search result list
			update();
		} else {
			ToastManager
					.makeToast(GlobalConstant.Toast.TOAST_SEARCH_RESULT_NULL);
		}
	}

	class CityAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			return mListResult.size();
		}

		@Override
		public Object getItem(int arg0) {
			return mListResult.get(arg0);
		}

		@Override
		public long getItemId(int arg0) {
			return arg0;
		}

		@Override
		public View getView(int position, View view, ViewGroup arg2) {
			LayoutInflater inflater = LayoutInflater
					.from(getApplicationContext());
			view = inflater.inflate(R.layout.search_result, null);
			TextView tvew = (TextView) view
					.findViewById(R.id.search_result_tvew);
			StringBuffer sb = new StringBuffer();
			sb.append(mListResult.get(position).getName());
			sb.append(" ");
			sb.append(mListResult.get(position).getCountry());
			sb.append(" ");
			sb.append(mListResult.get(position).getMunicipality());
			tvew.setText(sb.toString());
			return view;
		}
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View view, int position,
			long arg3) {
		if (mListResult != null && mListResult.size() > position) {
			Intent it = new Intent();
			it.putExtra(GlobalConstant.Result.RESULT, mListResult.get(position)
					.getName());
			PreferencesManager.setWoeid(mListResult.get(position).getWoeid());
			PreferencesManager.setCity(mListResult.get(position).getName());
			// insert to database
			mDb.insertCity(mListResult.get(position));
			// start update service
			Intent intent = new Intent(this, WeatherUpdateService.class);
			intent.setAction(GlobalConstant.IntentAction.ACTION_UPDATE);
			startService(intent);
			this.finish();
		}
	}
}
