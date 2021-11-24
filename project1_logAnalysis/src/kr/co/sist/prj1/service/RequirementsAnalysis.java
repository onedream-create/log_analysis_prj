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

	public final static String COUNT_KEY_MODE = "Log�� �׸��߿� �ϳ��� Key�� Ƚ���� mapKey�� value������ ������";
	public final static String COUNT_BROWSER_MODE = "Log�� �׸��߿� �ϳ��� BROWSER�� Ƚ���� mapBrowser�� value������ ������";
	public final static String COUNT_TIME_MODE = "Log�� �׸��߿� �ϳ��� TIME�� Ƚ���� Map�� mapTime�� value������ ������";
	public final static String COUNT_RANGE_KEY_MODE = "������ �����Ͽ� Log�� �׸��߿� �ϳ��� Key�� Ƚ���� mapKeyRange�� value������ ������";

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

		// List�� �����͸� ������ ��ġ�ϴ� Map�� �з�
		for (int i = 0; i < listLog.size(); i++) {
			LogDataVO tempData = listLog.get(i);

			// VO�� key�׸��� null�� �ƴϸ� mapKey,mapKeyRange�� key�� ��� ������ Map�� Ű������ ���� �ȴ�.
			if (tempData.getKey() != null) {
				mapKey.put(tempData.getKey(), 0);
				mapKeyRange.put(tempData.getKey(), 0);
			}
			mapTime.put(tempData.getHour(), 0);
			mapBrowser.put(tempData.getBrowser(), 0);
		}
	}

	// 1. �ִٻ�� Ű�� �̸��� Ƚ���� ��ȯ�ϴ� �޼ҵ� ex) java xxȸ
	public String getMostUseKey() {
		countMapValue(mapKey, COUNT_KEY_MODE);
		return getMaxEntry(mapKey);
	}

	// 2. �������� ����Ƚ��, ������ ��ȯ�ϴ� �޼ҵ� ex) IE - xx (xx%) / Chrome - xx (xx%)
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
			result += (tempBrowser + " : " + tempValue + "ȸ, "+ getRatioRequestOccurrences(tempValue) + "%\n              ");
		}
		return result;
	}

	// 3. ���񽺸� ���������� ������ Ƚ��, ������ Ƚ���� ��ȯ�ϴ� �޼ҵ�
	public String getCountServiceResult() {
		int successCnt = getCountRequestOccurrences("200");
		int failCnt = getCountRequestOccurrences("404");

		return "����Ƚ�� : " + successCnt + ", ���� Ƚ�� : " + failCnt;
	}

	// 4. ��û�� ���� ���� �ð��� ��ȯ�ϴ� �޼ҵ� ex) xx��
	public String getMostRequestTime() {
		countMapValue(mapTime, COUNT_TIME_MODE);
		return getMaxEntry(mapTime);
	}

	// 5. ���������� ��û(403)�� �߻��� Ƚ��, ���� ��ȯ �޼ҵ�
	public String getCountUnusualRequest() {
		int cnt = getCountRequestOccurrences("403");
		double ratio = getRatioRequestOccurrences(cnt);

		return "�߻��� Ƚ�� : " + cnt + "ȸ, ���� : " + ratio + "%";
	}

	// 6. ��û�� ���� ����(500)�� �߻��� Ƚ��, ���� ��ȯ �޼ҵ�
	public String getCountError() {
		int cnt = getCountRequestOccurrences("500");
		double ratio = getRatioRequestOccurrences(cnt);

		return "�߻��� Ƚ�� : " + cnt + "ȸ, ���� : " + ratio + "%";
	}

	// �Էµ� ���ο� �ش��ϴ� ������ �ִٻ�� Ű�� �̸��� Ƚ�� ��ȯ �޼ҵ�
	public String getRangeMostUseKey() {
		countMapValue(mapKeyRange, COUNT_RANGE_KEY_MODE);
		return getMaxEntry(mapKeyRange);
	}

	//��忡 ���� Map���� �з��س��� �� key���� value(�ش� Ű�� ����)�� ī��Ʈ
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
	
	//Map���� �ִٻ�� Ű�� Ƚ�� ���ϱ�
	public String getMaxEntry(Map<String, Integer> map) {
		//Map�� value�߿� ���� ū ���� �޴´�.
		int maxValue = Collections.max(map.values());
		
		String maxEntry = "";

		//Map�� entry�߿� value�� maxValue�� ���ٸ� maxEntry ���ڿ��� �ش��ϴ� key�� value�� ������ ��´�. �ߺ����� ����� else.
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

	// ��û�߻� �ڵ忡 ���� Ƚ���� ��ȯ�ϴ� �޼ҵ�
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

	// ��û�߻��� ���� ������ ��ȯ�ϴ� �޼ҵ�
	public double getRatioRequestOccurrences(int cnt) {
		return (Math.round(((double) cnt / listLog.size() * 100) * 100) / 100.0);
	}
}
