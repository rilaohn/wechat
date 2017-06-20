package com.wechat.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;

public class DownFile {
	public static String down(String reqUrl, String filePath) {
		int bytesum = 0;
	    int byteread = 0;
	    URL url = null;
		try {
		    url = new URL(reqUrl);
		} catch (MalformedURLException e1) {
		    e1.printStackTrace();
		    return null;
		}
	    try {
	    	File dir = new File(filePath);
	    	if (!dir.exists() && !dir.isDirectory()) 
	    		dir.mkdirs();
	        URLConnection conn = url.openConnection();
	        Map<String, List<String>> map = conn.getHeaderFields();
	        String fileFormat = conn.getHeaderFields().get("Content-disposition").get(0).split("\"")[1].split("\\.")[1];
	        filePath = filePath + "." + fileFormat;
	        InputStream inStream = conn.getInputStream();
	        FileOutputStream fs = new FileOutputStream(filePath);

	        byte[] buffer = new byte[1204];  
	        while ((byteread = inStream.read(buffer)) != -1) {  
	            bytesum += byteread;
	            fs.write(buffer, 0, byteread);
	        }
	        return filePath;
	    } catch (FileNotFoundException e) {
	        e.printStackTrace();
	        return null;
	    } catch (IOException e) {
	        e.printStackTrace();
	        return null;
	    }
	}
}
