package com.wechat.utils;

import com.wechat.pojo.user.SelfUser;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SelfUserUtil {
	public static SelfUser getSelfUser(String inputStream) {
		Map<String, String> map = xmlToMap(new ByteArrayInputStream(inputStream.getBytes()));
		return getSU(map);
	}

	private static SelfUser getSU(Map<String, String> map){
		SelfUser su = new SelfUser();
		String ToUserName = map.get("ToUserName");
		String FromUserName = map.get("FromUserName");
		String AgentID = map.get("AgentID");
		if (ToUserName != null && ToUserName.trim().length() > 0)
			su.setSelf(ToUserName);
		if (FromUserName != null && FromUserName.trim().length() > 0)
			su.setUser(FromUserName);
		if (AgentID != null && AgentID.trim().length() > 0)
			su.setAgentId(Integer.parseInt(AgentID));
		return su;
	}

	private static Map<String, String> xmlToMap(InputStream is) {
		// 将解析结果存储在HashMap中
		Map<String, String> data = new HashMap<String, String>();

		StringBuffer sb = new StringBuffer();
		InputStreamReader isr = null;
		BufferedReader br = null;
		try {
			isr = new InputStreamReader(is, "UTF-8");
			br = new BufferedReader(isr);

			String line = "";
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}
			String strXML = sb.toString();
			DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
			InputStream stream = new ByteArrayInputStream(strXML.getBytes("UTF-8"));
			org.w3c.dom.Document doc = documentBuilder.parse(stream);
			doc.getDocumentElement().normalize();
			NodeList nodeList = doc.getDocumentElement().getChildNodes();
			for (int index = 0; index < nodeList.getLength(); ++index) {
				Node node = nodeList.item(index);
				if (node.getNodeType() == Node.ELEMENT_NODE) {
					org.w3c.dom.Element element = (org.w3c.dom.Element) node;
					data.put(element.getNodeName(), element.getTextContent());
				}
			}
			stream.close();
			br.close();
			isr.close();
			is.close();
//			// 读取输入流
//			SAXReader reader = new SAXReader();
//			Document document = reader.read(request.getInputStream());
//			// 得到xml根元素
//			Element root = document.getRootElement();
//			// 得到根元素的所有子节点
//			List<Element> elementList = root.elements();
//
//			data = getAllElements(elementList, data);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

//		System.out.println("---MessageUtil.map: " + map.toString());

		return data;
	}


	private static Map<String, String> getAllElements(List<Element> ele, Map<String, String> map) {
		// 遍历所有子节点
		int i = 1;
		for (Element element : ele) {
			if (!map.containsKey(element.getName())) {
				map.put(element.getName(), element.getText());
			} else {
				map.put(element.getName() + i, element.getText());
				i++;
			}

			if (element.elements().size() > 0) {
				map = getAllElements(element.elements(), map);
			}
		}
		return map;
	}
}
