package com.example.weather.data.net;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;
import com.example.weather.connection.util.ObjectCast;
import com.example.weather.connection.util.StringUtil;

import android.os.Parcel;



public  class DownloadInformation extends JsonAdapter {
	
	protected InputStream inputStream = null;
	protected String string = null;
	
	protected Map<String, String> descriptions = new HashMap<String, String>();
	
	public static final String CONTENT_TYPE = "CONTENT_TYPE";
	public static final String CONTENT_LENGTH = "CONTENT_LENGTH";
	public static final String CONTENT_DECODING = "CONTENT_DECODING";
	public static final String STATUS_CODE = "STATUS_CODE";
	public static final String STATUS_MESSAGE = "STATUS_MESSAGE";
	
	public static final String ERROR_CODE = "errCd";
	public static final String ERROR_CODE_NONE = "0";
	public static final String ERROR_MESSAGE = "errMsg";
	
	
	public String getDescription(String key) {
		return getDescription(key, String.class);
	}
	
	public <T> T getDescription(String key, Class<T> clazz) {
		T o = null;
		String value = null;
		if (descriptions != null) {
			value = descriptions.get(key);
		}
		
		if (value != null) {
			o = (T)ObjectCast.castStringToObject(clazz, value, null);
		}
		return o ;
	}
	
	public void setDescription(String key, String value) {
		if (descriptions != null) {
			value = descriptions.put(key, value);
		}
	}
	
	public InputStream getInputStream() {
		return inputStream;
	}
	
	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}
	
	/**
	public String getString() {
		return string;
	}**/
	
	public String getString() {
		String ret = null;
		// TODO Auto-generated method stub
		if (internalObject != null) {
			ret = internalObject.toString();
		}
		return ret;
	}
	
	
	
	public void setString(String str) {
		string  = str;
	}
	
	public DownloadInformation() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public DownloadInformation(JSONObject internalObject) {
		super(internalObject);
		// TODO Auto-generated constructor stub

		
	}
	public void setInnerObject(JSONObject json) {
		this.internalObject = json;
		// TODO Auto-generated method stub

	}

	

	public String getErrCode() {
		// TODO Auto-generated method stub

		String HTTP_STATUS_CODE = getDescription(DownloadInformation.STATUS_CODE);
				 
		return "200".equals(HTTP_STATUS_CODE)? 	this.optString(ERROR_CODE) : HTTP_STATUS_CODE;
	}


	public String getErrMessage() {
		// TODO Auto-generated method stub
		String HTTP_STATUS_MESSAGE = getDescription(DownloadInformation.STATUS_MESSAGE);
		return "OK".equals(HTTP_STATUS_MESSAGE)? this.optString(ERROR_MESSAGE) : HTTP_STATUS_MESSAGE;
}


	public boolean hasError() {
		// TODO Auto-generated method stub
		String errCd = getErrCode();
		// errcd 不为空， 且不为 0，表明有 error
		return StringUtil.isNotNullAndEmpy(errCd) && !ERROR_CODE_NONE.equals(errCd);
	}

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		// TODO Auto-generated method stub
		
	}




	
	
	
}
