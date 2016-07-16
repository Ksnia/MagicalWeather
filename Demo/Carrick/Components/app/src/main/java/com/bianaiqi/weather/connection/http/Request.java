package com.bianaiqi.weather.connection.http;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;
import com.bianaiqi.weather.connection.util.StringUtil;

public class Request {
	
	protected static final int  UPLOAD_PACKAGE_UNIT_SIZE  = 1024 * 50;
	
	protected HttpURLConnection conn  = null;
	protected Map<String, String> parameter = null;
	
	public abstract class Log {
		public abstract void outputLog(String str, Object...objects) ;
		public abstract void outputException(String str, Throwable t, Object...objects) ;
	}
	
			
	
	private final Log DEFAULT_INNER_LOG = new Log() {
		
		@Override
		public void outputLog(String str, Object... objects) {
			// TODO Auto-generated method stub
			System.out.println(String.format(str, objects));
		}
		
		@Override
		public void outputException(String str, Throwable t, Object...objects) {
			// TODO Auto-generated method stub
			System.err.println(String.format(str, objects));
			t.printStackTrace(System.err);
		}
		
	};
	
	private Log log = DEFAULT_INNER_LOG;
	
	public void setLog(Log log) {
		this.log = log;
	}
	
	private void innerOutput(String str, Object...objects) {
		if (log != null) {
			log.outputLog(str, objects);
		}
	}
	private void innerOutput(String str, Throwable t, Object...objects) {
		if (log != null) {
			log.outputException(str, t, objects);
		}
	}
	
	public static final String HTTP_PARAM_KEY_METHOD = "METHOD";
	
	public enum HttpMethod {
		GET,
		POST,
		PUT,
		TRACE,
		DELETE
		
	};
	
	
	public static final String HTTP_PARAM_KEY_CONTENT_TYPE = "Content-Type";
	public static final String HTTP_PARAM_KEY_CONTENT_LENGTH = "Content-length";
	public static final String HTTP_PARAM_KEY_CONTENT_ENCODING = "encoding";
	public static final String HTTP_PARAM_KEY_USERAGENT = "User-Agent";
	
	public static final String HTTP_PARAM_VALUE_CONTENT_TYPE_FORM = "application/x-www-form-urlencoded";
	public static final String HTTP_PARAM_VALUE_CONTENT_TYPE_MULTIPART = "multipart/form-data";
	
	public static final String HTTP_PARAM_VALUE_CONTENT_ENCODING_DEFAULT = "utf-8";
	public static final String HTTP_PARAM_VALUE_CONTENT_TYP_DEFAULT = HTTP_PARAM_VALUE_CONTENT_TYPE_FORM;
	public static final String HTTP_PARAM_VALUE_CONTENT_LENGTH_DEFAULT = null;
	public static final HttpMethod HTTP_PARAM_VALUE_METHOD_DEFAULT = HttpMethod.GET; //HttpMethod.POST
	public static final String HTTP_PARAM_VALUE_USERAGENT_DEFAULT = 
			"Mozilla/5.0 (X11; U; Linux x86_64; en-US; rv:1.9.2.18) Gecko/20110628 Ubuntu/10.04 (lucid) Firefox/3.6.18";
	
	
	public Request() {
		
	}
	
	public void release() {
		if (conn != null) {
			conn.disconnect();
		}
	}
	
	public Response getResponse(String strUrl, InputStream is) throws Throwable {
		
		Response ret = null;
		OutputStream os = null;
		
		try {

			URL url = new URL(strUrl);
			innerOutput("getResponse -- 1, url = %s", strUrl);
			
			conn = (HttpURLConnection)url.openConnection();
						
			String method = parameter == null || StringUtil.isNullOrEmpty(parameter.get(HTTP_PARAM_KEY_METHOD)) 
					? HTTP_PARAM_VALUE_METHOD_DEFAULT.toString() : parameter.get(HTTP_PARAM_KEY_METHOD) ;

			String ua = parameter == null || StringUtil.isNullOrEmpty(parameter.get(HTTP_PARAM_KEY_USERAGENT)) 
					? HTTP_PARAM_VALUE_USERAGENT_DEFAULT : parameter.get(HTTP_PARAM_KEY_USERAGENT) ;
					
			conn.setRequestMethod(method);			
			
			conn.setRequestProperty(HTTP_PARAM_KEY_USERAGENT, ua);
			if (HttpMethod.POST.equals(HttpMethod.valueOf(method)) ) {
				String contentType = parameter == null || StringUtil.isNullOrEmpty(parameter.get(HTTP_PARAM_KEY_CONTENT_TYPE)) 
						? HTTP_PARAM_VALUE_CONTENT_TYP_DEFAULT : parameter.get(HTTP_PARAM_KEY_CONTENT_TYPE) ;
				
				String contentLength = (parameter == null 
						|| StringUtil.isNullOrEmpty(parameter.get(HTTP_PARAM_KEY_CONTENT_LENGTH))
						|| !StringUtil.isNumber(parameter.get(HTTP_PARAM_KEY_CONTENT_LENGTH)))
						? HTTP_PARAM_VALUE_CONTENT_LENGTH_DEFAULT : parameter.get(HTTP_PARAM_KEY_CONTENT_LENGTH) ;
				
				conn.setRequestProperty(HTTP_PARAM_KEY_CONTENT_TYPE, contentType);	
				
				if (contentLength != null) {
					conn.setRequestProperty(HTTP_PARAM_KEY_CONTENT_LENGTH, contentLength);
				}
			}
			
			
			// POST 时才向 connection 发送数据
			conn.setDoOutput(HttpMethod.POST.toString().equals(method));
			// 接收是必须滴
			conn.setDoInput(true);
			
			innerOutput("getResponse -- 2 , method=%s, ua=%s,  ", method, ua);

			conn.connect();
			
			// 允许向connection输出且有数据才发送
			if (conn.getDoOutput() && is != null) {
				byte[] buffer = new byte[UPLOAD_PACKAGE_UNIT_SIZE]; 

				os = conn.getOutputStream();
				int actualSize = 0;
				long hasSentSize = 0;
				long contentLength = 
						parameter.get(HTTP_PARAM_KEY_CONTENT_LENGTH) == null? Long.MAX_VALUE :
						Long.parseLong(parameter.get(HTTP_PARAM_KEY_CONTENT_LENGTH));  
				
				while ((actualSize = is.read(buffer)) > 0) {
					hasSentSize += actualSize;
					Float	percent = 100 * ((float)hasSentSize / contentLength);
					
					DEFAULT_INNER_LOG.outputLog("post data: contentLength=%d hasSent=%d percent=%d％ currentUnit:%d"
							, contentLength
							, hasSentSize
							, percent.intValue()
							, actualSize);
					os.write(buffer, 0, actualSize);
				}
				
				os.flush();
				innerOutput("getResponse -- 3 , post status:%s contentLength =%d, finished:%d"
						, hasSentSize == contentLength? "OK" : "NG"
						, contentLength
						, hasSentSize);
			}
			
			ret = new Response(conn);
			
		} catch (Exception ex) {
			innerOutput("getResponse ocurred Exception", ex);
			if (conn != null) {
				conn.disconnect();
			}
			throw ex;
		} finally {
			if (os != null) {
				os.close();
			}
		}

		return ret;
	}
	
	public Response getResponse(String strUrl, String postData) throws Throwable {
		
		Response ret = null;
		
		ByteArrayInputStream bis = null;
		
		try {
			if (postData != null) {
				String encoding = parameter == null || StringUtil.isNullOrEmpty(parameter.get(HTTP_PARAM_KEY_CONTENT_ENCODING)) 
						? HTTP_PARAM_VALUE_CONTENT_ENCODING_DEFAULT : parameter.get(HTTP_PARAM_KEY_CONTENT_ENCODING) ;
				byte[] data = postData.getBytes(encoding);
				bis = new ByteArrayInputStream(data);
				parameter.put(HTTP_PARAM_KEY_CONTENT_LENGTH, String.valueOf(data.length));
			}
			ret = getResponse(strUrl, bis);
		} finally {
			if (bis != null) {
				bis.close();
			}
		}

		
		return ret;
	}

	public void setParameter(Map<String, String> parameter) {
		// TODO Auto-generated method stub
		this.parameter  = parameter;
	}
}
