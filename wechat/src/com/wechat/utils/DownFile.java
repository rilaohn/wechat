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
	    	String dirPath = null;
	    	String[] dirs = null;
			String lastStr;
	    	if (filePath.indexOf("\\") != -1) {
				dirs = filePath.split("\\\\");
			} else if (filePath.indexOf("/") != -1) {
				dirs = filePath.split("/");
			}
			lastStr = dirs[dirs.length - 1];
			dirPath = filePath.substring(0, filePath.length() - lastStr.length());
			
	    	File dir = new File(dirPath);
	    	if (!dir.exists() && !dir.isDirectory()) 
	    		dir.mkdirs();
	        URLConnection conn = url.openConnection();
	        Map<String, List<String>> map = conn.getHeaderFields();
	        String fileFormat = null;
	        if (map.containsKey("Content-disposition")) {
	        	fileFormat = map.get("Content-disposition").get(0).split("\"")[1].split("\\.")[1];
			} else if (map.containsKey("Content-Type")) {
				fileFormat = map.get("Content-Type").get(0).split("/")[1];
			} else {
				fileFormat = "jpg";
			}
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
