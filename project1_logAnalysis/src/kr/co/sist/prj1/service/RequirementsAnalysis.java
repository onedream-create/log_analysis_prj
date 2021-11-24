package kr.co.sist.prj1.service;

import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import kr.co.sist.prj1.view.MainView;
import kr.co.sist.prj1.vo.LogDataVO;

public class RequirementsAnalysis {

	public final static String COUNT_KEY_MODE = "Log의 항목중에 하나인 Key의 횟수를 mapKey의 value값으로 누적합";
	public final static String COUNT_BROWSER_MODE = "Log의 항목중에 하나인 BROWSER의 횟수를 mapBrowser의 value값으로 누적합";
	public final static String COUNT_TIME_MODE = "Log의 항목중에 하나인 TIME의 횟수를 Map의 mapTime의 value값으로 누적합";
	public final static String COUNT_RANGE_KEY_MODE = "범위를 지정하여 Log의 항목중에 하나인 Key의 횟수를 mapKeyRange의 value값으로 누적합";

	private MainView mv;

	private List<LogDataVO> listLog;

	private Map<String, Integer> mapKey;
	private Map<String, Integer> mapKeyRange;
	private Map<String, Integer> mapBrowser;
	private Map<String, Integer> mapTime;

	public RequirementsAnalysis(List<LogDataVO> listLog, MainView mv) {
		this.listLog = listLog;
		this.mv = mv;

		mapKey = new HashMap<String, Integer>();
		mapKeyRange = new HashMap<String, Integer>();
		mapBrowser = new HashMap<String, Integer>();
		mapTime = new HashMap<String, Integer>();

		// List의 데이터를 각각의 일치하는 Map에 분류
		for (int i = 0; i < listLog.size(); i++) {
			LogDataVO tempData = listLog.get(i);

			// VO의 key항목이 null이 아니면 mapKey,mapKeyRange에 key의 모든 종류가 Map의 키값으로 들어가게 된다.
			if (tempData.getKey() != null) {
				mapKey.put(tempData.getKey(), 0);
				mapKeyRange.put(tempData.getKey(), 0);
			}
			mapTime.put(tempData.getHour(), 0);
			mapBrowser.put(tempData.getBrowser(), 0);
		}
	}

	// 1. 최다사용 키의 이름과 횟수를 반환하는 메소드 ex) java xx회
	public String getMostUseKey() {
		countMapValue(mapKey, COUNT_KEY_MODE);
		return getMaxEntry(mapKey);
	}

	// 2. 브라우저별 접속횟수, 비율을 반환하는 메소드 ex) IE - xx (xx%) / Chrome - xx (xx%)
	public String getRatioBrowser() {
		Set<String> setTempBrowser = mapBrowser.keySet();
		Iterator<String> iterBrowser = setTempBrowser.iterator();
		
		int tempValue = 0;
		String tempBrowser = "";
		String result = "";
		
		countMapValue(mapBrowser, COUNT_BROWSER_MODE);
		
		while (iterBrowser.hasNext()) {
			tempBrowser = iterBrowser.next();
			tempValue = mapBrowser.get(tempBrowser);
			result += (tempBrowser + " : " + tempValue + "회, "+ getRatioRequestOccurrences(tempValue) + "%\n              ");
		}
		return result;
	}

	// 3. 서비스를 성공적으로 수행한 횟수, 실패한 횟수를 반환하는 메소드
	public String getCountServiceResult() {
		int successCnt = getCountRequestOccurrences("200");
		int failCnt = getCountRequestOccurrences("404");

		return "성공횟수 : " + successCnt + ", 실패 횟수 : " + failCnt;
	}

	// 4. 요청이 가장 많은 시간을 반환하는 메소드 ex) xx시
	public String getMostRequestTime() {
		countMapValue(mapTime, COUNT_TIME_MODE);
		return getMaxEntry(mapTime);
	}

	// 5. 비정상적인 요청(403)이 발생한 횟수, 비율 반환 메소드
	public String getCountUnusualRequest() {
		int cnt = getCountRequestOccurrences("403");
		double ratio = getRatioRequestOccurrences(cnt);

		return "발생한 횟수 : " + cnt + "회, 비율 : " + ratio + "%";
	}

	// 6. 요청에 대한 에러(500)가 발생한 횟수, 비율 반환 메소드
	public String getCountError() {
		int cnt = getCountRequestOccurrences("500");
		double ratio = getRatioRequestOccurrences(cnt);

		return "발생한 횟수 : " + cnt + "회, 비율 : " + ratio + "%";
	}

	// 입력된 라인에 해당하는 정보중 최다사용 키의 이름과 횟수 반환 메소드
	public String getRangeMostUseKey() {
		countMapValue(mapKeyRange, COUNT_RANGE_KEY_MODE);
		return getMaxEntry(mapKeyRange);
	}

	//모드에 따라 Map으로 분류해놓은 각 key값에 value(해당 키의 갯수)를 카운트
	public void countMapValue(Map<String, Integer> map, String mode) {
		String tempKey = "";
		int startIdx = 0;
		int endIdx = listLog.size();

		switch (mode) {
		case COUNT_KEY_MODE: 
			for (int i = startIdx; i < endIdx; i++) {
				if (listLog.get(i).getKey() == null) {
					continue;
				}
				tempKey = listLog.get(i).getKey();
				map.put(tempKey, map.get(tempKey) + 1);
			} break;
		case COUNT_BROWSER_MODE:
			for (int i = startIdx; i < endIdx; i++) {
				tempKey = listLog.get(i).getBrowser();
				map.put(tempKey, map.get(tempKey) + 1);
			} break;
		case COUNT_TIME_MODE: 
			for (int i = startIdx; i < endIdx; i++) {
				tempKey = listLog.get(i).getHour();
				map.put(tempKey, map.get(tempKey) + 1);
			} break;
		case COUNT_RANGE_KEY_MODE:
			startIdx = Integer.parseInt(mv.getJtfStartValue().getText()) - 1;
			endIdx = Integer.parseInt(mv.getJtfEndValue().getText());

			for (int i = startIdx; i < endIdx; i++) {
				if (listLog.get(i).getKey() == null) {
					continue;
				}
				tempKey = listLog.get(i).getKey();
				map.put(tempKey, map.get(tempKey) + 1);
			} break;
		}
	}
	
	//Map에서 최다사용 키와 횟수 구하기
	public String getMaxEntry(Map<String, Integer> map) {
		//Map의 value중에 가장 큰 값을 받는다.
		int maxValue = Collections.max(map.values());
		
		String maxEntry = "";

		//Map의 entry중에 value가 maxValue와 같다면 maxEntry 문자열에 해당하는 key와 value의 정보를 담는다. 중복값을 대비해 else.
		for (Map.Entry<String, Integer> entry : map.entrySet()) {
			if ((entry.getValue()) == (maxValue)) {
				if (maxEntry.equals("")) {
					maxEntry = entry.getKey() + " : " + entry.getValue();
				} else {
					maxEntry += ", " + entry.getKey() + " : " + entry.getValue();
				}
			}
		}
		return maxEntry;
	}

	// 요청발생 코드에 대한 횟수를 반환하는 메소드
	public int getCountRequestOccurrences(String code) {
		String tempCode = "";
		int cnt = 0;

		for (LogDataVO tempData : listLog) {
			tempCode = tempData.getCode();
			if (tempCode.equals(code)) {
				cnt++;
			}
		}
		return cnt;
	}

	// 요청발생에 대한 비율을 반환하는 메소드
	public double getRatioRequestOccurrences(int cnt) {
		return (Math.round(((double) cnt / listLog.size() * 100) * 100) / 100.0);
	}
}
