package kr.co.sist.prj1.vo;

/**
 * �α����Ͽ��� �ʿ��� �����͸� �����Ͽ�
 * List�� �־��ֱ����� �̸� ������ VOŬ����
 * �����ʹ� �����ڵ�, key, query, ��������, �̿�ð�(h)���� ���еȴ�.
 * @author 0812 Ȳ��
 */
public class LogDataVO {
	private String code; //�����ڵ�
	private String key; //Ű��
//	private String query; //����
	private String browser; //��������
	private String hour; //�̿�ð�

//	public LogDataVO(String code, String key, String query, String browser, String hour) {
//		this.code = code;
//		this.key = key;
//		this.query = query;
//		this.browser = browser;
//		this.hour = hour;
//	}
	public LogDataVO(String code, String key, String browser, String hour) {
		this.code = code;
		this.key = key;
//		this.query = query;
		this.browser = browser;
		this.hour = hour;
	}

//	//�� Ȯ���� ���� toString
//	@Override
//	public String toString() {
//		return "{" + "code= " + code + ", key= " + key + ", query= " + query + 
//				", browser= " + browser + ", hour= " + hour + "}";
//	}
	//�� Ȯ���� ���� toString
	@Override
	public String toString() {
		return "{" + "code= " + code + ", key= " + key  + 
				", browser= " + browser + ", hour= " + hour + "}";
	}

	//@getter 
	public String getCode() {
		return code;
	}
	public String getKey() {
		return key;
	}
//	public String getQuery() {
//		return query;
//	}

	public String getBrowser() {
		return browser;
	}

	public String getHour() {
		return hour;
	}
}
