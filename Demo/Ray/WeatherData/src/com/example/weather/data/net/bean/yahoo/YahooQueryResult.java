package com.example.weather.data.net.bean.yahoo;

import java.util.List;

public class YahooQueryResult {

	int count;
	String created;
	String lang;
	Result results;

	public static class Wind { 
		int chill;
		int direction;
		String speed;
		public int getChill() {
			return chill;
		}
		public void setChill(int chill) {
			this.chill = chill;
		}
		public int getDirection() {
			return direction;
		}
		public void setDirection(int direction) {
			this.direction = direction;
		}
		public String getSpeed() {
			return speed;
		}
		public void setSpeed(String speed) {
			this.speed = speed;
		}
		
		
	}

	public static class Location {
		String city;
		String country;
		String region;
		public String getCity() {
			return city;
		}
		public void setCity(String city) {
			this.city = city;
		}
		public String getCountry() {
			return country;
		}
		public void setCountry(String country) {
			this.country = country;
		}
		public String getRegion() {
			return region;
		}
		public void setRegion(String region) {
			this.region = region;
		}
		
		
	}

	public static class Atmosphere {
		int humidity;
		float pressure;
		int rising;
		float visibility;
		public int getHumidity() {
			return humidity;
		}
		public void setHumidity(int humidity) {
			this.humidity = humidity;
		}
		public float getPressure() {
			return pressure;
		}
		public void setPressure(float pressure) {
			this.pressure = pressure;
		}
		public int getRising() {
			return rising;
		}
		public void setRising(int rising) {
			this.rising = rising;
		}
		public float getVisibility() {
			return visibility;
		}
		public void setVisibility(float visibility) {
			this.visibility = visibility;
		}

	}

	public static class Astronomy {
		String sunrise;
		String sunset;
		public String getSunrise() {
			return sunrise;
		}
		public void setSunrise(String sunrise) {
			this.sunrise = sunrise;
		}
		public String getSunset() {
			return sunset;
		}
		public void setSunset(String sunset) {
			this.sunset = sunset;
		}
		
		
	}

	public static class Image {
		String title;
		int width;
		int height;
		String link;
		String url;
		public String getTitle() {
			return title;
		}
		public void setTitle(String title) {
			this.title = title;
		}
		public int getWidth() {
			return width;
		}
		public void setWidth(int width) {
			this.width = width;
		}
		public int getHeight() {
			return height;
		}
		public void setHeight(int height) {
			this.height = height;
		}
		public String getLink() {
			return link;
		}
		public void setLink(String link) {
			this.link = link;
		}
		public String getUrl() {
			return url;
		}
		public void setUrl(String url) {
			this.url = url;
		}
		
		
		
	}

	public static class Condition {
		int code;
		String date;
		int temp;
		String text;
		
		public int getCode() {
			return code;
		}
		public void setCode(int code) {
			this.code = code;
		}
		public String getDate() {
			return date;
		}
		public void setDate(String date) {
			this.date = date;
		}
		public int getTemp() {
			return temp;
		}
		public void setTemp(int temp) {
			this.temp = temp;
		}
		public String getText() {
			return text;
		}
		public void setText(String weather) {
			this.text = weather;
		}
		@Override
		public String toString() {
			return "Condition [code=" + code + ", date=" + date + ", temp="
					+ temp + ", text=" + text + "]";
		}
		
		
	}

	public static class ForecastItem {
		int code;
		String date;
		String day;
		int high;
		int low;
		String text;
		public int getCode() {
			return code;
		}
		public void setCode(int code) {
			this.code = code;
		}
		public String getDate() {
			return date;
		}
		public void setDate(String date) {
			this.date = date;
		}
		public String getDay() {
			return day;
		}
		public void setDay(String day) {
			this.day = day;
		}
		public int getHigh() {
			return high;
		}
		public void setHigh(int high) {
			this.high = high;
		}
		public int getLow() {
			return low;
		}
		public void setLow(int low) {
			this.low = low;
		}
		public String getText() {
			return text;
		}
		public void setText(String weather) {
			this.text = weather;
		}
		
		
	}

	public static class Guid {
		boolean isPermaLink;

		public boolean isPermaLink() {
			return isPermaLink;
		}

		public void setPermaLink(boolean isPermaLink) {
			this.isPermaLink = isPermaLink;
		}
		
		

	}

	public static class Item {
		String title;
		long latitude;
		long longtitude;
		String link;
		String pubDate;
		Condition condition;
		List<ForecastItem> forecast;
		String description;
		Guid guid;
		public String getTitle() {
			return title;
		}
		public void setTitle(String title) {
			this.title = title;
		}
		public long getLatitude() {
			return latitude;
		}
		public void setLatitude(long latitude) {
			this.latitude = latitude;
		}
		public long getLongtitude() {
			return longtitude;
		}
		public void setLongtitude(long longtitude) {
			this.longtitude = longtitude;
		}
		public String getLink() {
			return link;
		}
		public void setLink(String link) {
			this.link = link;
		}
		public String getPubDate() {
			return pubDate;
		}
		public void setPubDate(String pubDate) {
			this.pubDate = pubDate;
		}
		public Condition getCondition() {
			return condition;
		}
		public void setCondition(Condition condition) {
			this.condition = condition;
		}
		public List<ForecastItem> getForecast() {
			return forecast;
		}
		public void setForecast(List<ForecastItem> forecast) {
			this.forecast = forecast;
		}
		public String getDescription() {
			return description;
		}
		public void setDescription(String description) {
			this.description = description;
		}
		public Guid getGuid() {
			return guid;
		}
		public void setGuid(Guid guid) {
			this.guid = guid;
		}
		
	}

	public static class Channel {
		Units units;
		String title;
		String link;
		String description;
		String language;
		String lastBuildDate;
		int ttl;
		Location location;
		Wind wind;
		Atmosphere atmosphere;
		Astronomy astronomy;
		Image image;
		Item item;
		
		public Units getUnits() {
			return units;
		}
		public void setUnits(Units units) {
			this.units = units;
		}
		public String getTitle() {
			return title;
		}
		public void setTitle(String title) {
			this.title = title;
		}
		public String getLink() {
			return link;
		}
		public void setLink(String link) {
			this.link = link;
		}
		public String getDescription() {
			return description;
		}
		public void setDescription(String description) {
			this.description = description;
		}
		public String getLanguage() {
			return language;
		}
		public void setLanguage(String language) {
			this.language = language;
		}
		public String getLastBuildDate() {
			return lastBuildDate;
		}
		public void setLastBuildDate(String lastBuildDate) {
			this.lastBuildDate = lastBuildDate;
		}
		public int getTtl() {
			return ttl;
		}
		public void setTtl(int ttl) {
			this.ttl = ttl;
		}
		public Location getLocation() {
			return location;
		}
		public void setLocation(Location location) {
			this.location = location;
		}
		public Wind getWind() {
			return wind;
		}
		public void setWind(Wind wind) {
			this.wind = wind;
		}
		public Atmosphere getAtmosphere() {
			return atmosphere;
		}
		public void setAtmosphere(Atmosphere atmos) {
			this.atmosphere = atmos;
		}
		public Astronomy getAstronomy() {
			return astronomy;
		}
		public void setAstronomy(Astronomy astr) {
			this.astronomy = astr;
		}
		public Image getImage() {
			return image;
		}
		public void setImage(Image image) {
			this.image = image;
		}
		public Item getItem() {
			return item;
		}
		public void setItem(Item item) {
			this.item = item;
		}
		
	}

	public static class Units {
		String distance;
		String pressure;
		String speed;
		String temperature;
		
		public String getDistance() {
			return distance;
		}
		public void setDistance(String distance) {
			this.distance = distance;
		}
		public String getPressure() {
			return pressure;
		}
		public void setPressure(String pressure) {
			this.pressure = pressure;
		}
		public String getSpeed() {
			return speed;
		}
		public void setSpeed(String speed) {
			this.speed = speed;
		}
		public String getTemperature() {
			return temperature;
		}
		public void setTemperature(String temperature) {
			this.temperature = temperature;
		}
		
		
	}

	public static class Result {
		Channel channel;

		public Channel getChannel() {
			return channel;
		}

		public void setChannel(Channel channel) {
			this.channel = channel;
		}
	

	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public String getCreatedTime() {
		return created;
	}

	public void setCreated(String created) {
		this.created = created;
	}

	public String getLang() {
		return lang;
	}

	public void setLang(String lang) {
		this.lang = lang;
	}

	public Result getResults() {
		return results;
	}

	public void setResults(Result results) {
		this.results = results;
	}

	
	
	
	
}
