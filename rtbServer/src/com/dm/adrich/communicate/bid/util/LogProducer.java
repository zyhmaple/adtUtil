/*jadclipse*/// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.

package com.dm.adrich.communicate.bid.util;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.log4j.Logger;
import org.json.simple.JSONObject;

// Referenced classes of package com.dm.adrich.communicate.bid.util:
//            KafkaUtil, CacheUtil

public class LogProducer extends Thread {

	protected static final Logger log = Logger.getLogger(LogProducer.class);
	private String message;
	private int flag;
	private boolean isEStats;
	
	public LogProducer(String message, int flag, boolean isEStats) throws Exception {
		this.message = null;
		this.flag = 0;
		this.isEStats = false;
		this.message = message;
		this.flag = flag;
		this.isEStats = isEStats;
	}

	public void run() {
		if (1 == flag)
			reqSend(message);
		else if (2 == flag)
			respSend(message);
	}

	public void reqSend(String msg) {
		try {
			ProducerRecord data = new ProducerRecord("reqSendTopic", msg.getBytes("UTF-8"));
			KafkaUtil.INSTANCE.getLogProducer().send(data);
			log.info((new StringBuilder("isEStats = ")).append(isEStats).toString());
			if (isEStats) {
				ProducerRecord data2 = new ProducerRecord("statsGPRSendTopic", msg.getBytes("UTF-8"));
				KafkaUtil.INSTANCE.getLogProducer().send(data2);
			}
			log.info((new StringBuilder("sendReqData = ")).append(msg).toString());
		} catch (Exception e) {
			log.error((new StringBuilder("logSend = ")).append(e.getMessage()).toString(), e);
			e.printStackTrace();
		}
	}

	public void respSend(String msg) {
		try {
			String extArray[] = msg.split(":");
			String planID = extArray[0];
			String impAdID = extArray[1];
			String cityName = extArray[2];
			String userID = extArray[3];
			String isBanner = extArray[4];
			String siteName = extArray[5];
			String requestID = extArray[6];
			String msID = extArray[7];
			String sspCode = extArray[8];
			String platform = extArray[9];
			String requestTime = extArray[10];
			JSONObject messageObj = new JSONObject();
			appendMessage(messageObj, "planID", extArray[0]);
			appendMessage(messageObj, "impAdID", extArray[1]);
			appendMessage(messageObj, "cityName", extArray[2]);
			appendMessage(messageObj, "userID", extArray[3]);
			appendMessage(messageObj, "isBanner", extArray[4]);
			appendMessage(messageObj, "requestID", extArray[6]);
			appendMessage(messageObj, "materialID", extArray[7]);
			appendMessage(messageObj, "sspCode", extArray[8]);
			appendMessage(messageObj, "isPC", extArray[9]);
			appendMessage(messageObj, "requestTime", extArray[10]);
			appendMessage(messageObj, "dealID", extArray[11]);
			String orderID = CacheUtil.INSTANCE.getEhcache(planID, "orderID");
			appendMessage(messageObj, "orderID", orderID);
			String sendData = messageObj.toJSONString();
			ProducerRecord data = new ProducerRecord("respSendTopic", sendData.getBytes("UTF-8"));
			KafkaUtil.INSTANCE.getLogProducer().send(data);
			if (isEStats) {
				JSONObject messageObj2 = new JSONObject();
				appendMessage(messageObj2, "requestID", extArray[6]);
				appendMessage(messageObj2, "userID", extArray[3]);
				appendMessage(messageObj2, "sspCode", extArray[8]);
				appendMessage(messageObj2, "isPC", extArray[9]);
				appendMessage(messageObj2, "optTime", extArray[10]);
				appendMessage(messageObj2, "dealID", extArray[11]);
				appendMessage(messageObj2, "dataFlag", "2");
				String sendData2 = messageObj2.toJSONString();
				ProducerRecord data2 = new ProducerRecord("statsGPRSendTopic", sendData2.getBytes("UTF-8"));
				KafkaUtil.INSTANCE.getLogProducer().send(data2);
			}
			log.info((new StringBuilder("sendRespData = ")).append(sendData).toString());
		} catch (Exception e) {
			log.error((new StringBuilder("logSend = ")).append(e.getMessage()).toString(), e);
			e.printStackTrace();
		}
	}

	private void appendMessage(JSONObject messageObj, String msgKey, String msgValue) {
		if (isNoNull(msgValue))
			messageObj.put(msgKey, msgValue);
	}

	private boolean isNoNull(String str) {
		return str != null && !"".equals(str) && !"null".equals(str);
	}

}


/*
	DECOMPILATION REPORT

	Decompiled from: C:\Users\zyh\git\bid\rtbServer\rtbServer.jar
	Total time: 107 ms
	Jad reported messages/errors:
	Exit status: 0
	Caught exceptions:
*/