package com.example.weather.data.net.bean.baidu;

import java.util.List;

public class BaiduOriginData {

	int error;
	String status;
	String date;
    List<BaiduOriginResult> results;
	public int getError() {
		return error;
	}
	public void setError(int error) {
		this.error = error;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public List<BaiduOriginResult> getResults() {
		return results;
	}
	public void setResults(List<BaiduOriginResult> results) {
		this.results = results;
	}

}
