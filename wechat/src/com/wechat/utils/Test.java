package com.wechat.utils;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.wechat.corp.pojo.auth.login.Department;
import com.wechat.corp.pojo.res.manager.SetAgentBack;
import com.wechat.pojo.menu.MenuBack;

/**
 * 类名：Test.java <br>
 * 描述： <br>
 * 开发人员：廖日辰 <br>
 * 创建时间：2017年4月12日 下午2:35:01 <br>
 * 发布版本：V <br>
 */
public class Test {
	static Map<String, Data> datas;

	public static void main(String[] args) {
//		datas = new HashMap<String, Data>();
//		Gson gson = new Gson();
		//// Data data1 = new Data();
		//// data1.setId("id1111");
		//// data1.setSecret("secret1111");
		//// data1.setCorp(false);
		//// Data data2 = new Data();
		//// data2.setId("id2222");
		//// data2.setSecret("secret2222");
		//// data2.setCorp(true);
		//// Data data3 = new Data();
		//// data3.setId("id3333");
		//// data3.setSecret("secret3333");
		//// data3.setCorp(true);
		//// Data data4 = new Data();
		//// data4.setId("id4444");
		//// data4.setSecret("secret4444");
		//// data4.setCorp(false);
		//// datas.put("第一个", data1);
		//// datas.put("第二个", data2);
		//// datas.put("第三个", data3);
		//// datas.put("第四个", data4);
		// Gson gson = new Gson();
		//// String jsonStr = gson.toJson(datas);
		// String jsonStr =
		//// "{\"第二个\":{\"id\":\"id2222\",\"secret\":\"secret2222\",\"isCorp\":true},\"第四个\":{\"id\":\"id4444\",\"secret\":\"secret4444\",\"isCorp\":false},\"第三个\":{\"id\":\"id3333\",\"secret\":\"secret3333\",\"isCorp\":true},\"第一个\":{\"id\":\"id1111\",\"secret\":\"secret1111\",\"isCorp\":false}}";
		// datas = gson.fromJson(jsonStr, new TypeToken<Map<String,
		//// Data>>(){}.getType());
		//// System.out.println(jsonStr);
		// System.out.println(datas.get("第二个").getId());
		// String params =
		//// "第1个,id1111,secret1111,false;第2个,id2222,secret2222,true;第3个,id3333,secret3333,true;第4个,id4444,secret4444,false";
		// String params = "第1个,id1111,secret1111,false";
		// String[] one = params.split(";");
		// for (int i = 0; i < one.length; i++) {
		// System.out.println(one[i]);
		// String[] two = one[i].split(",");
		// Data data = new Data();
		// data.setId(two[1]);
		// data.setSecret(two[2]);
		// data.setCorp(Boolean.parseBoolean(two[3]));
		// datas.put(two[0], data);
		// }
		//
		// System.out.println(datas.get("第1个").getId() + "\t" +
		//// datas.get("第1个").getSecret() + "\t" + datas.get("第1个").isCorp());
		// System.out.println(datas.get("第2个").getId() + "\t" +
		//// datas.get("第2个").getSecret() + "\t" + datas.get("第2个").isCorp());
		// System.out.println(datas.get("第3个").getId() + "\t" +
		//// datas.get("第3个").getSecret() + "\t" + datas.get("第3个").isCorp());
		// System.out.println(datas.get("第4个").getId() + "\t" +
		//// datas.get("第4个").getSecret() + "\t" + datas.get("第4个").isCorp());
		// Department department = new Department();
		// department.setId("123");
		// department.setWritable("");

		// System.out.println(gson.toJson(department));

		// List<Integer> integers = new ArrayList<Integer>();
		// integers.add(1);
		// integers.add(2);
		// integers.add(3);
		// integers.add(4);
		// integers.add(5);
		// System.out.println(gson.toJson(integers));
		// CharSequence errcode = "errcode\":\"0";
		// SetAgentBack back = new SetAgentBack();
		// back.setErrcode("0");
		// back.setErrmsg("ok");
		// String json = gson.toJson(back);
		// System.out.println(json);
		// System.out.println(json.contains(errcode));

		// String[] subs = "123.246.32.46/25".split("/");
		// int[] gets = new int[4];
		// int[] masks = new int[4];
		// int subOne = Integer.parseInt(subs[1]);
		// int index = subOne/8;
		// int subLess = subOne%8;
		// String less = "";
		// for(int i = 0; i < index; i++)
		// masks[i] = 255;
		// for (int i = 0; i < 8; i++) {
		// int j = 0;
		// if(i<subLess)
		// j=1;
		// less = less + j;
		// }
		// int sublast = Integer.valueOf(less, 2);
		// masks[index] = sublast;
		// if (index < masks.length -1) {
		// for (int i = index +1; i < masks.length; i++)
		// masks[i] = 0;
		// }
		//
		// for (int i = 0; i < masks.length; i++) {
		// System.out.println(masks[i]);
		// }

		// String iiiii = "0123456789";
		// String one = iiiii.subSequence(2, 8).toString();
		// System.out.println(one);
		// System.out.println(iiiii);
		// String string1 = "01001011";
		// String string2 = "10110100";
		// System.out.println(string1&string2);
		// int i=235;
		// int j = 147;
		// System.out.println(i&j);
		// System.out.println(Integer.toBinaryString(i));
		// System.out.println(Integer.toBinaryString(j));

//		MenuBack back = new MenuBack();
//		back.setErrcode(0);
//		back.setErrmsg("OK");
//		updateParamas(back);
//		System.out.println(back.getErrcode() + "\n" + back.getErrmsg());
//		System.out.println(back);
//		hello(back);
		String url = "https://qyapi.weixin.qq.com/cgi-bin/media/get?access_token=4PmA6UbPU9opVxuU2E0WjHlizMZgKH-WCoQWGzVqiMQ9ZAM7trM5iDxCqPMT5Vml&media_id=3yKSd7Qn5v-Bm0PFIXbt5lFntAGc5Ux5kVdSSVoT7J4VbkIVIzAK3LflVsOrLc6M";
		down(url, "C:\\Users\\admin\\Desktop\\123456\\weixin.jpg");

	}

	public static boolean down(String reqUrl, String filePath) {
		int bytesum = 0;
	    int byteread = 0;
	    URL url = null;
		try {
		    url = new URL(reqUrl);
		} catch (MalformedURLException e1) {
		    e1.printStackTrace();
		    return false;
		}
	    try {
	        URLConnection conn = url.openConnection();
	        Map<String, List<String>> map = conn.getHeaderFields();
	        String s11 = conn.getHeaderFields().get("Content-disposition").get(0).split("\"")[1].split("\\.")[1];
	        System.out.println(s11);
	        
	        InputStream inStream = conn.getInputStream();
	        FileOutputStream fs = new FileOutputStream(filePath);

	        byte[] buffer = new byte[1204];  
	        while ((byteread = inStream.read(buffer)) != -1) {  
	            bytesum += byteread;
	            fs.write(buffer, 0, byteread);
	        }
	        return true;
	    } catch (FileNotFoundException e) {
	        e.printStackTrace();
	        return false;
	    } catch (IOException e) {
	        e.printStackTrace();
	        return false;
	    }
	}
	
	public static void updateParamas(MenuBack back) {
		back.setErrmsg(back.getErrmsg() + ",修改了");
	}
	
	public static void hello(MenuBack back) {
		MenuBack back2 = null;
		back2 = back;
//		System.out.println(back2);
	}
}
