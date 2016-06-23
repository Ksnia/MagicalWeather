package com.example.weather.connection.http;


import org.json.JSONObject;

import com.example.weather.connection.util.StringUtil;

import com.example.weather.data.net.DownloadInformation;


public class InternetRequestByHttpConnection {
	
	protected Request r = null;

	public InternetRequestByHttpConnection() {
		super();
		// TODO Auto-generated constructor stub
		r = new Request();
	}
	
	
	protected Response sendRequestInternal(String url) throws Throwable  {
		Response rsp = null;
		String attachData  = null;
		
		if (StringUtil.isNotNullAndEmpy(url)) {
			//r.setParameter(parameter);
			rsp = r.getResponse(url, attachData);
			
		}
		return rsp;
	}
	

	public DownloadInformation sendRequest(String url) {
		// TODO Auto-generated method stub
		DownloadInformation out = null;
		
		try {
			Response rsp = sendRequestInternal(url);
			JSONObject json = rsp == null? null : rsp.asJSONObject();
			String origin = rsp == null? null : rsp.asString();
			
			if (json != null ) {
				out = new DownloadInformation(json);
				out.setString(origin);
				setDownloadInformationDescription(rsp, out);
			}
				
			
		} catch (Throwable ex) {
			ex.printStackTrace();
			//AppstoreException.handlException(ex);
		}

		return out;
	}
	
	
	public void setDownloadInformationDescription(Response response, DownloadInformation info) {
		if (info != null && response != null) {
			for (String key : response.getHeaderFields().keySet()) {
				info.setDescription(key, response.getHeaderField(key));
			}
			info.setDescription(DownloadInformation.CONTENT_DECODING, response.getContentEncoding());
			info.setDescription(DownloadInformation.CONTENT_LENGTH, String.valueOf(response.getContentLength()));
			info.setDescription(DownloadInformation.CONTENT_TYPE, response.getContentType());
			info.setDescription(DownloadInformation.STATUS_CODE, String.valueOf(response.getStatusCode()));
			info.setDescription(DownloadInformation.STATUS_MESSAGE, String.valueOf(response.getStatusMessage()));
		}
	}

}
