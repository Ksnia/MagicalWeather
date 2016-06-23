package com.huaqin.android.weather.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.util.Xml;

import com.huaqin.android.weather.model.City;
import com.huaqin.android.weather.model.CityWeather;
import com.huaqin.android.weather.model.Weather;

public class XmlPullParserHelper {

	public static List<City> getCity(InputStream in, String inputEncoding) {
		if (in == null) {
			return null;
		}
		// declare the store object
		List<City> list = new ArrayList<City>();
		XmlPullParser xmlPullParser = Xml.newPullParser();
		int enventType = 0;
		City city = null;

		try {
			xmlPullParser.setInput(in, inputEncoding);
			enventType = xmlPullParser.getEventType();
		} catch (XmlPullParserException e) {
			e.printStackTrace();
			return list;
		}

		while (enventType != XmlPullParser.END_DOCUMENT) {
			switch (enventType) {
				case XmlPullParser.START_TAG:
					String tag = xmlPullParser.getName();
					if (tag.equalsIgnoreCase(GlobalConstant.City.CITY_RESULT_START)) {
						city = new City();
					} else if (city != null) {
						// parse after tag
						if (tag.equalsIgnoreCase(GlobalConstant.City.CITY_WOEID)) {
							try {
								city.setWoeid(xmlPullParser.nextText());
							} catch (XmlPullParserException e) {
								e.printStackTrace();
							} catch (IOException e) {
								e.printStackTrace();
							}
						} else if (tag
								.equalsIgnoreCase(GlobalConstant.City.CITY_NAME)) {
							try {
								city.setName(xmlPullParser.nextText());
							} catch (XmlPullParserException e) {
								e.printStackTrace();
							} catch (IOException e) {
								e.printStackTrace();
							}
						} else if (tag
								.equalsIgnoreCase(GlobalConstant.City.CITY_COUNTRY)) {
							try {
								city.setCountry(xmlPullParser.nextText());
							} catch (XmlPullParserException e) {
								e.printStackTrace();
							} catch (IOException e) {
								e.printStackTrace();
							}
						} else if (tag
								.equalsIgnoreCase(GlobalConstant.City.CITY_MUNICIPALITY)) {
							try {
								city.setMunicipality(xmlPullParser.nextText());
							} catch (XmlPullParserException e) {
								e.printStackTrace();
							} catch (IOException e) {
								e.printStackTrace();
							}
						} else if (tag
								.equalsIgnoreCase(GlobalConstant.City.CITY_PREFECURE)) {
							try {
								city.setMunicipality(xmlPullParser.nextText());
							} catch (XmlPullParserException e) {
								e.printStackTrace();
							} catch (IOException e) {
								e.printStackTrace();
							}
						} else if (tag
								.equalsIgnoreCase(GlobalConstant.City.CITY_COUNTY)) {
							try {
								city.setCounty(xmlPullParser.nextText());
							} catch (XmlPullParserException e) {
								e.printStackTrace();
							} catch (IOException e) {
								e.printStackTrace();
							}
						}
					}
					break;
				case XmlPullParser.END_TAG:
					if (xmlPullParser.getName().equalsIgnoreCase(
							GlobalConstant.City.CITY_RESULT_START)
							&& city != null) {
						list.add(city);
						city = null;
					}
					break;
				default:
					break;
			}
			try {
				enventType = xmlPullParser.next();
			} catch (XmlPullParserException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		try {
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return list;
	}

	public static CityWeather getCityWeather(InputStream in,
			String inputEncoding) {
		if (in == null) {
			return null;
		}
		XmlPullParser xmlPullParser = Xml.newPullParser();
		int enventType = 0;
		CityWeather cityWeather = null;
		try {
			xmlPullParser.setInput(in, inputEncoding);
			enventType = xmlPullParser.getEventType();
		} catch (XmlPullParserException e) {
			e.printStackTrace();
			return cityWeather;
		}

		while (enventType != XmlPullParser.END_DOCUMENT) {
			switch (enventType) {
				case XmlPullParser.START_TAG: {
					String tag = xmlPullParser.getName();
					LogEx.d("tag = " + tag);
					if (tag.equalsIgnoreCase(GlobalConstant.CityWeather.CITYWEATHER_RESULT_START)) {
						cityWeather = new CityWeather();
					} else if (cityWeather != null) {
						if (tag.equalsIgnoreCase(GlobalConstant.CityWeather.CITYWEATHER_FORECAST_ATOMSPHERE)) {
							// get the atmosphere
							// get humidity
							cityWeather
									.setHumidity(xmlPullParser
											.getAttributeValue(
													null,
													GlobalConstant.CityWeather.CITYWEATHER_FORECAST_HUMIDITY));
							// get pressure
							cityWeather
									.setPressure(xmlPullParser
											.getAttributeValue(
													null,
													GlobalConstant.CityWeather.CITYWEATHER_FORECAST_PRESSURE));
						} else if (tag
								.equalsIgnoreCase(GlobalConstant.CityWeather.CITYWEATHER_FORECAST_ASTRONOMY)) {
							// get the atmosphere
							// get sunrise
							cityWeather
									.setSunrise(xmlPullParser
											.getAttributeValue(
													null,
													GlobalConstant.CityWeather.CITYWEATHER_FORECAST_SUNRISE));
							// get sunset
							cityWeather
									.setSunset(xmlPullParser
											.getAttributeValue(
													null,
													GlobalConstant.CityWeather.CITYWEATHER_FORECAST_SUNSET));
						} else if (tag
								.equalsIgnoreCase(GlobalConstant.CityWeather.CITYWEATHER_PUBDATE)) {
							// get the pub date
							try {
								cityWeather
										.setPubDate(xmlPullParser.nextText());
							} catch (XmlPullParserException e) {
								e.printStackTrace();
							} catch (IOException e) {
								e.printStackTrace();
							}
						} else if (tag
								.equalsIgnoreCase(GlobalConstant.CityWeather.CITYWEATHER_DESCRIPTION)) {
							/**
							 * // get the image URL of the weather try { String
							 * description = xmlPullParser.nextText(); if
							 * (description != null && description.length() > 0)
							 * { // Original results like //
							 * "<img src="http://l.
							 * yimg.com/a/i/us/we/52/28.gif"/><br/>
							 * " String[] results = description.split("\""); if
							 * (results != null && results.length > 2) {
							 * cityWeather.setImgUrl(results[1]); } } } catch
							 * (XmlPullParserException e) { e.printStackTrace();
							 * } catch (IOException e) { e.printStackTrace(); }
							 */
						} else if (tag
								.equalsIgnoreCase(GlobalConstant.CityWeather.CITYWEATHER_FORECAST)) {
							// get the weather information
							Weather weather = new Weather();
							String strTemp = "";
							int iTemp = 0;
							weather.setDay(xmlPullParser
									.getAttributeValue(
											null,
											GlobalConstant.CityWeather.CITYWEATHER_FORECAST_DAY));
							weather.setDate(xmlPullParser
									.getAttributeValue(
											null,
											GlobalConstant.CityWeather.CITYWEATHER_FORECAST_DATE));
							// get low temperature
							strTemp = xmlPullParser
									.getAttributeValue(
											null,
											GlobalConstant.CityWeather.CITYWEATHER_FORECAST_LOW);
							try {
								iTemp = Integer.parseInt(strTemp);
							} catch (Exception e) {
								e.printStackTrace();
							}
							weather.setLowTemp(iTemp);
							// get high temperature
							strTemp = xmlPullParser
									.getAttributeValue(
											null,
											GlobalConstant.CityWeather.CITYWEATHER_FORECAST_HIGH);
							try {
								iTemp = Integer.parseInt(strTemp);
							} catch (Exception e) {
								e.printStackTrace();
							}
							weather.setHighTemp(iTemp);
							weather.setText(xmlPullParser
									.getAttributeValue(
											null,
											GlobalConstant.CityWeather.CITYWEATHER_FORECAST_TEXT));
							// get code
							strTemp = xmlPullParser
									.getAttributeValue(
											null,
											GlobalConstant.CityWeather.CITYWEATHER_FORECAST_CODE);
							try {
								iTemp = Integer.parseInt(strTemp);
							} catch (Exception e) {
								e.printStackTrace();
							}
							weather.setCode(iTemp);
							weather.setTempFormat(PreferencesManager
									.getTemperatureFormat());
							// add to the storage list
							cityWeather.setWeather(weather);
						}
					}
					break;
				}
				case XmlPullParser.END_TAG: {
					break;
				}
				default: {
					break;
				}
			}
			try {
				enventType = xmlPullParser.next();
			} catch (XmlPullParserException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		try {
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return cityWeather;
	}
}
