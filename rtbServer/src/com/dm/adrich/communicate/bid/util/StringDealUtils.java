package com.dm.adrich.communicate.bid.util;

import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;
import org.apache.log4j.Logger;
import org.json.simple.JSONObject;

// Referenced classes of package com.dm.adrich.communicate.bid.util:
//            Base58

public class StringDealUtils {

	protected static final Logger log = Logger.getLogger(StringDealUtils.class);

	public static String dealDomainName(String domainName) {
		if (checkStrNull(domainName)) {
			domainName = Base58.encode(domainName.getBytes());
			return domainName;
		} else {
			return domainName;
		}
	}

	public static boolean checkStrNull(String str) {
		return str != null && !"".equals(str);
	}

	public static String getDomainName(String domainName) {
		if (checkStrNull(domainName)) {
			domainName = domainName.toLowerCase().replace("http://", "");
			int index = domainName.indexOf("/");
			if (index > 0)
				domainName = domainName.substring(0, index);
			return domainName;
		} else {
			return domainName;
		}
	}

	public static List listValueToString(List list) {
		List resList = new ArrayList();
		if (list != null) {
			for (int i = 0; i < list.size(); i++) {
				Object value = list.get(i);
				resList.add(value.toString());
			}

		}
		return resList;
	}

	public static void mapValueToString(Map resMap) {
		for (Iterator iterator = resMap.entrySet().iterator(); iterator.hasNext();) {
			java.util.Map.Entry entry = (java.util.Map.Entry) iterator.next();
			String key = (String) entry.getKey();
			Object value = entry.getValue();
			if (value instanceof List) {
				List list = (List) value;
				if (list != null && list.size() > 0) {
					for (int i = 0; i < list.size(); i++) {
						Map map = (Map) list.get(i);
						mapValueToString(map);
					}

				}
			} else {
				String valueStr = nullToString(value);
				resMap.put(key, valueStr);
			}
		}

	}

	public static String nullToString(Object str) {
		if (str != null)
			return String.valueOf(str).trim();
		else
			return "";
	}

	public static String ecodeUrl(String url) throws Exception {
		return URLEncoder.encode(url, "UTF-8");
	}

	public static String ecodeTwiceUrl(String url) throws Exception {
		return ecodeUrl(ecodeUrl(url));
	}

	public static void printUserLog(String userID, String isPC, String dmpCode, String sspCode, List tagList) {
		JSONObject json = new JSONObject();
		json.put("userID", userID);
		json.put("isPC", isPC);
		json.put("dmpCode", dmpCode);
		json.put("sspCode", sspCode);
		json.put("optTime", getCurrentDateTime());
		json.put("userAttributeList", tagList);
		log.info((new StringBuilder("otherUserInfo =")).append(json.toString()).toString());
	}

	public static String createReqDealStr(String userID, String isPC, String dealID, String sspCode, String requestID) {
		JSONObject json = new JSONObject();
		json.put("requestID", requestID);
		json.put("userID", userID);
		json.put("isPC", isPC);
		json.put("dealID", dealID);
		json.put("sspCode", sspCode);
		json.put("optTime", (new StringBuilder(String.valueOf(System.currentTimeMillis()))).toString());
		json.put("dataFlag", "1");
		return json.toJSONString();
	}

	public static String getCurrentDateTime() {
		return (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(new Date());
	}

}