package com.kh.finalproject.finalProject.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.stereotype.Service;

import com.kh.finalproject.finalProject.vo.info2item;
import com.kh.finalproject.finalProject.vo.infoitem;

@Service
public class ApiService {
	  public ArrayList<infoitem> getInfoItemList(String title) {
	        ArrayList<infoitem> list = new ArrayList<>();

	        try {
	            String serviceKey = "344i8CUjOIbhJn%2FoD9CjHIThno6Ad8hP1mv2iZbzhh1LV2vyDkaSjsDmFG%2Fr3nG7fg6iFvSHjx4VVpz0bUQ%2FQA%3D%3D";
	            int numOfRows = 100;
	            String itemName = title;
	            String type = "json";

	            StringBuffer sb = new StringBuffer(
	                    "http://apis.data.go.kr/1471000/DrbEasyDrugInfoService/getDrbEasyDrugList?");

	            sb.append("serviceKey=" + serviceKey);
	            sb.append("&numOfRows=" + numOfRows);
	            sb.append("&itemName=" + URLEncoder.encode(itemName, "UTF-8"));
	            sb.append("&type=" + type);

	            URL url = new URL(sb.toString());

	            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	            conn.connect();

	            BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
	            StringBuffer newResult = new StringBuffer();

	            String line;
	            while ((line = rd.readLine()) != null) {
	                newResult.append(line);
	            }

	            rd.close();
	            conn.disconnect();

	            JSONParser jp = new JSONParser();
	            JSONObject job = (JSONObject) jp.parse(newResult.toString());
	            JSONArray array = (JSONArray) ((JSONObject) job.get("body")).get("items");

	            for (int i = 0; i < array.size(); i++) {
	                JSONObject temp = (JSONObject) array.get(i);

	                String entpName = (String) temp.get("entpName");
	                String itemName1 = (String) temp.get("itemName");
	                String efcyQesitm = (String) temp.get("efcyQesitm");
	                String itemImage = (String) temp.get("itemImage");
	                String depositMethodQesitm = (String) temp.get("depositMethodQesitm");

	                infoitem m = new infoitem(entpName, itemName1, efcyQesitm, itemImage, depositMethodQesitm);
	                list.add(m);
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	        }

	        return list;
	    }
	  
	  public ArrayList<info2item> getInfo2ItemList(String title) {
	        ArrayList<info2item> list = new ArrayList<>();

	        try {
	            String serviceKey = "344i8CUjOIbhJn%2FoD9CjHIThno6Ad8hP1mv2iZbzhh1LV2vyDkaSjsDmFG%2Fr3nG7fg6iFvSHjx4VVpz0bUQ%2FQA%3D%3D";
	            String item_name = title;
	            String type = "json";

	            StringBuffer sb = new StringBuffer(
	                    "https://apis.data.go.kr/1471000/DrugPrdtPrmsnInfoService05/getDrugPrdtPrmsnDtlInq04?");
	            sb.append("serviceKey=" + serviceKey);
	            sb.append("&item_name=" + URLEncoder.encode(item_name, "UTF-8"));
	            sb.append("&type=" + type);

	            URL url = new URL(sb.toString());
	            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	            conn.connect();

	            BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
	            StringBuffer newResult = new StringBuffer();

	            String line;
	            while ((line = rd.readLine()) != null) {
	                newResult.append(line);
	            }

	            rd.close();
	            conn.disconnect();

	            JSONParser jp = new JSONParser();
	            JSONObject job = (JSONObject) jp.parse(newResult.toString());
	            JSONArray array = (JSONArray) ((JSONObject) job.get("body")).get("items");

	            for (int i = 0; i < array.size(); i++) {
	                JSONObject temp = (JSONObject) array.get(i);
	                String ITEM_NAME = (String) temp.get("ITEM_NAME");
	                String ENTP_NAME = (String) temp.get("ENTP_NAME");
	                String EE_DOC_DATA = (String) temp.get("EE_DOC_DATA");

	                // 정규 표현식을 사용하여 원하는 형태로 EE_DOC_DATA 가공
	                Pattern pattern = Pattern.compile("[가-힣]+");
	                Matcher matcher = pattern.matcher(EE_DOC_DATA);

	                StringBuffer stringBuffer = new StringBuffer();
	                while (matcher.find()) {
	                    stringBuffer.append(matcher.group());
	                    stringBuffer.append(" ");
	                }
	                EE_DOC_DATA = stringBuffer.toString().substring(4);

	                String UD_DOC_DATA = (String) temp.get("UD_DOC_DATA");
	                String NB_DOC_DATA = (String) temp.get("NB_DOC_DATA");

	                info2item m = new info2item(ITEM_NAME, ENTP_NAME, EE_DOC_DATA, UD_DOC_DATA, NB_DOC_DATA);
	                list.add(m);
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	        }

	        return list;
	    }
}
