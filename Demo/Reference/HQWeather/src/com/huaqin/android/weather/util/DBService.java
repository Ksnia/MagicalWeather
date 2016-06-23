package com.huaqin.android.weather.util;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.huaqin.android.weather.model.City;
import com.huaqin.android.weather.model.CityWeather;
import com.huaqin.android.weather.model.Weather;

public class DBService extends SQLiteOpenHelper {

	private final static int DATABASE_VERSION = 1;
	private final static String DB_NAME = "weather3d.db";
	/** Common id */
	private final static String _ID = "_id";
	private final static String CITY_NAME = "city_name";
	private final static String CITY_WOEID = "city_woeid";
	/** Table weather */
	private final static String TABLE_NAME = "weather";
	private final static String CITY_PUBDATE = "city_pubdate";
	private final static String IMAGE_URL = "image_url";
	private final static String DAY = "day";
	private final static String DATE = "date";
	private final static String LOW_TEMP = "low_temp";
	private final static String HIGH_TEMP = "high_temp";
	private final static String TEXT = "text";
	private final static String CODE = "code";
	private final static String TEMP_FORMAT = "temp_format";
	private final static String SUNRISE = GlobalConstant.CityWeather.CITYWEATHER_FORECAST_SUNRISE;
	private final static String SUNSET = GlobalConstant.CityWeather.CITYWEATHER_FORECAST_SUNSET;
	private final static String HUMIDITY = GlobalConstant.CityWeather.CITYWEATHER_FORECAST_HUMIDITY;
	private final static String PRESSURE = GlobalConstant.CityWeather.CITYWEATHER_FORECAST_PRESSURE;

	/** Table cities */
	private final static String TABLE_CITY = "cities";
	private final static String CITY_COUNTRY = "city_country";
	private final static String CITY_MUNICIPALITY = "city_municipality";
	private final static String CITY_PREFECTURE = "city_prefecture";
	private final static String CITY_COUNTY = "city_county";

	public DBService(Context context) {
		super(context, DB_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		String sql = "create table " + TABLE_NAME + " (" + _ID
				+ " integer primary key autoincrement, " + CITY_NAME
				+ " text, " + DAY + " text, " + DATE + " text, " + LOW_TEMP
				+ " integer, " + HIGH_TEMP + " integer, " + TEXT + " text, "
				+ CODE + " integer, " + TEMP_FORMAT + " text, " + IMAGE_URL
				+ " text, " + SUNRISE + " text, " + SUNSET + " text, "
				+ HUMIDITY + " text, " + PRESSURE + " text, " + CITY_PUBDATE
				+ " text, " + CITY_WOEID + " text" + ")";
		String sqlCity = "create table " + TABLE_CITY + " (" + _ID
				+ " integer primary key autoincrement, " + CITY_NAME
				+ " text, " + CITY_COUNTRY + " text, " + CITY_MUNICIPALITY
				+ " text, " + CITY_PREFECTURE + " text, " + CITY_COUNTY
				+ " text, " + CITY_WOEID + " text" + ")";
		db.execSQL(sql);
		db.execSQL(sqlCity);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

	}

	/**
	 * @Description query all database cities data
	 * @return cities
	 */
	public List<City> queryCities() {
		SQLiteDatabase db = this.getReadableDatabase();
		List<City> listCities = new ArrayList<City>();
		City city = null;
		String sql = "select * from cities";
		Cursor cursor = db.rawQuery(sql, null);
		if (cursor != null) {
			while (cursor.moveToNext()) {
				city = new City();
				city.setName(cursor.getString(cursor.getColumnIndex(CITY_NAME)));
				city.setWoeid(cursor.getString(cursor
						.getColumnIndex(CITY_WOEID)));
				city.setCountry(cursor.getString(cursor
						.getColumnIndex(CITY_COUNTRY)));
				city.setMunicipality(cursor.getString(cursor
						.getColumnIndex(CITY_MUNICIPALITY)));
				city.setPrefecture(cursor.getString(cursor
						.getColumnIndex(CITY_PREFECTURE)));
				city.setCounty(cursor.getString(cursor
						.getColumnIndex(CITY_COUNTY)));
				listCities.add(city);
			}
			cursor.close();
			db.close();
		}
		return listCities;
	}

	/**
	 * @Description To judge weather the city is exist in database
	 * @param woeid city woeid
	 * @return the query result
	 */
	private boolean isCityExists(String strWoeid) {
		SQLiteDatabase db = this.getReadableDatabase();
		String sql = "select * from cities where city_woeid=?";
		Cursor cursor = db.rawQuery(sql, new String[] { strWoeid });
		if (cursor != null && cursor.moveToFirst()) {
			return true;
		}
		return false;
	}

	/**
	 * @Description Query the city's weather data by city Woeid
	 * @param woeid city woeid
	 * @return Cursor returns a Cursor over the result set
	 */
	public Cursor queryCityWeather(String woeid) {
		SQLiteDatabase db = this.getReadableDatabase();
		String sql = "select * from weather where city_woeid = '" + woeid + "'";
		Cursor cursor = db.rawQuery(sql, null);
		return cursor;
	}

	/**
	 * @Description query by provided city woeid and data and returns a Weather
	 *              object over the result set.
	 * @param woeid The city woeid
	 * @param date Date format like "d MMM yyyy"
	 * @return The Weather over the result
	 */
	public Weather queryCityWeatherDay(String woeid, String date) {
		SQLiteDatabase db = this.getReadableDatabase();
		Weather weather = null;
		String sql = "select * from weather where city_woeid = '" + woeid
				+ "' and date = '" + date + "'";
		Cursor cursor = db.rawQuery(sql, null);
		// make sure is date data is not in Database,query default one to shown
		if (cursor == null || cursor.getCount() == 0) {
			cursor = queryCityWeather(woeid);
		}
		if (cursor != null) {
			if (cursor.moveToNext()) {
				weather = new Weather();
				weather.setCode(cursor.getInt(cursor.getColumnIndex(CODE)));
				weather.setDate(cursor.getString(cursor.getColumnIndex(DATE)));
				weather.setDay(cursor.getString(cursor.getColumnIndex(DAY)));
				weather.setHighTemp(cursor.getInt(cursor
						.getColumnIndex(HIGH_TEMP)));
				weather.setLowTemp(cursor.getInt(cursor
						.getColumnIndex(LOW_TEMP)));
				weather.setText(cursor.getString(cursor.getColumnIndex(TEXT)));
				weather.setTempFormat(cursor.getString(cursor
						.getColumnIndex(TEMP_FORMAT)));
				// get weather detail
				weather.setSunrise(cursor.getString(cursor
						.getColumnIndex(SUNRISE)));
				weather.setSunset(cursor.getString(cursor
						.getColumnIndex(SUNSET)));
				weather.setHumidity(cursor.getString(cursor
						.getColumnIndex(HUMIDITY)));
				weather.setPressure(cursor.getString(cursor
						.getColumnIndex(PRESSURE)));
				weather.setPubDate(cursor.getString(cursor
						.getColumnIndex(CITY_PUBDATE)));
			}
			cursor.close();
			db.close();
		}
		return weather;
	}

	public List<Weather> queryCityWeathers(String woeid) {
		SQLiteDatabase db = this.getReadableDatabase();
		List<Weather> list = new ArrayList<Weather>();
		String sql = "select * from weather where city_woeid = '" + woeid + "'";
		Cursor cursor = db.rawQuery(sql, null);
		// make sure is date data is not in Database,query default one to shown
		if (cursor == null || cursor.getCount() == 0) {
			cursor = queryCityWeather(woeid);
		}
		if (cursor != null) {
			while (cursor.moveToNext()) {
				Weather weather = new Weather();
				weather.setCode(cursor.getInt(cursor.getColumnIndex(CODE)));
				weather.setDate(cursor.getString(cursor.getColumnIndex(DATE)));
				weather.setDay(cursor.getString(cursor.getColumnIndex(DAY)));
				weather.setHighTemp(cursor.getInt(cursor
						.getColumnIndex(HIGH_TEMP)));
				weather.setLowTemp(cursor.getInt(cursor
						.getColumnIndex(LOW_TEMP)));
				weather.setText(cursor.getString(cursor.getColumnIndex(TEXT)));
				weather.setTempFormat(cursor.getString(cursor
						.getColumnIndex(TEMP_FORMAT)));
				// get weather detail
				weather.setSunrise(cursor.getString(cursor
						.getColumnIndex(SUNRISE)));
				weather.setSunset(cursor.getString(cursor
						.getColumnIndex(SUNSET)));
				weather.setHumidity(cursor.getString(cursor
						.getColumnIndex(HUMIDITY)));
				weather.setPressure(cursor.getString(cursor
						.getColumnIndex(PRESSURE)));
				weather.setPubDate(cursor.getString(cursor
						.getColumnIndex(CITY_PUBDATE)));
				list.add(weather);
			}
			cursor.close();
			db.close();
		}
		return list;
	}

	public long insertCityWeather(CityWeather weather) {
		SQLiteDatabase db = this.getReadableDatabase();
		if (weather != null && weather.getWeather().size() > 0) {
			// before insert clear the old data
			clearWeatherByCity(weather.getWoeid());
			// insert the new data
			for (Weather state : weather.getWeather()) {
				ContentValues cv = new ContentValues();
				// put ContentValues
				cv.put(CITY_NAME, weather.getCityName());
				cv.put(CITY_WOEID, weather.getWoeid());
				cv.put(IMAGE_URL, weather.getImgUrl());
				cv.put(CITY_PUBDATE, weather.getPubDate());
				cv.put(SUNRISE, weather.getSunrise());
				cv.put(SUNSET, weather.getSunset());
				cv.put(HUMIDITY, weather.getHumidity());
				cv.put(PRESSURE, weather.getPressure());
				cv.put(DAY, state.getDay());
				cv.put(DATE, state.getDate());
				cv.put(LOW_TEMP, state.getLowTemp());
				cv.put(HIGH_TEMP, state.getHighTemp());
				cv.put(TEXT, state.getText());
				cv.put(CODE, state.getCode());
				cv.put(TEMP_FORMAT, state.getTempFormat());
				// insert data
				db.insert(TABLE_NAME, null, cv);
			}
		}
		return -1;
	}

	public long insertCity(City city) {
		SQLiteDatabase db = this.getReadableDatabase();
		if (city != null) {
			// if city is exist does't to insert
			if (isCityExists(city.getWoeid())) {
				return -1;
			}
			ContentValues cv = new ContentValues();
			// put ContentValues
			cv.put(CITY_NAME, city.getName());
			cv.put(CITY_WOEID, city.getWoeid());
			cv.put(CITY_COUNTRY, city.getCountry());
			cv.put(CITY_MUNICIPALITY, city.getMunicipality());
			cv.put(CITY_PREFECTURE, city.getPrefecture());
			cv.put(CITY_COUNTY, city.getCounty());
			// insert data
			db.insert(TABLE_CITY, null, cv);
		}
		return -1;
	}

	/**
	 * Clear Weather data from database by city woeid
	 * 
	 * @param strWoeid
	 */
	private void clearWeatherByCity(String strWoeid) {
		SQLiteDatabase db = this.getReadableDatabase();
		db.delete(TABLE_NAME, CITY_WOEID + "=?", new String[] { strWoeid });
	}

	/**
	 * Clear City data from database by city woeid
	 * 
	 * @param strWoeid city woeid
	 */
	public void clearDbByCity(String strWoeid) {
		SQLiteDatabase db = this.getReadableDatabase();
		db.delete(TABLE_CITY, CITY_WOEID + "=?", new String[] { strWoeid });
		db.delete(TABLE_NAME, CITY_WOEID + "=?", new String[] { strWoeid });
	}

	/**
	 * Clear all database datas
	 */
	public void clearAllDb() {
		SQLiteDatabase db = this.getReadableDatabase();
		db.delete(TABLE_NAME, null, null);
		db.delete(TABLE_CITY, null, null);
	}
}
