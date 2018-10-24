package com.adrich.tools;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Random;
/**
 * 随机互评选人程序
* <p>Title: </p> 
* <p>Description: </p> 
*
* @Author:wang_baoquan
* @date 2018年4月3日 上午10:58:49
 */
public class EachEvaluate {

	private static final String names = "王宝泉,谭海峰,汤彪,马强,晁补,张耀华";
	private static final String[] nameArr = names.split(",");
	
	public static void main(String[] args) {
		Map<String,String> result = null;
		while(true){
			result = select();
			boolean checkEachEva = checkEachEva(result);
			if(result.size()==nameArr.length && checkEachEva){
				break;
			}
		}
		Iterator<String> iter = result.keySet().iterator();
		while(iter.hasNext()){
			String key = iter.next();
			System.out.println(key+" --> "+result.get(key));
		}
	}

	private static Map<String,String> select(){
		Map<String,String> result = new HashMap<>();
		LinkedList<String> evaPersonList = new LinkedList<>();
		for(String name:nameArr){
			evaPersonList.add(name);
		}
		for(int i = 0;i<nameArr.length;i++){
			String evaPerson = "";
			Random random = new Random();
			while(true){
				int index = random.nextInt(evaPersonList.size());
				evaPerson = evaPersonList.get(index);
				if(!nameArr[i].equals(evaPerson)){
					evaPersonList.remove(index);
					result.put(nameArr[i],evaPerson);
					break;
				}
				if(i == nameArr.length-1 && evaPersonList.size() == 1){
					break;//出现自己互评，需要重新选择
				}
			}
		}
		return result;
	}
	/**
	 * 互评校验
	 * @param map
	 * @return
	 */
	private static boolean checkEachEva(Map<String,String> map){
		Iterator<String> iter = map.keySet().iterator();
		while(iter.hasNext()){
			String key = iter.next();
			String val = map.get(key);
			if(key.equals(map.get(val))){
				return false;
			}
		}
		return true;
	}
	
	
	private static int drew(int who){

		//打乱人头
		int personCount = nameArr.length;
		Random random = new Random(System.currentTimeMillis());
		Map<Integer,String> kv = new HashMap<Integer,String>();
		random.nextInt(personCount);
		//摸球
		
		//是否是自己，不是返回，是重新再来
	}
	
	
}
