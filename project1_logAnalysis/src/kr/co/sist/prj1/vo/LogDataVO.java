package kr.co.sist.prj1.vo;

/**
 * 로그파일에서 필요한 데이터만 추출하여
 * List에 넣어주기위해 미리 선언한 VO클래스
 * 데이터는 응답코드, key, query, 사용브라우저, 이용시간(h)으로 구분된다.
 * @author 0812 황건
 */
public class LogDataVO {
	private String code; //응답코드
	private String key; //키값
//	private String query; //쿼리
	private String browser; //사용브라우저
	private String hour; //이용시간

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

//	//값 확인을 위한 toString
//	@Override
//	public String toString() {
//		return "{" + "code= " + code + ", key= " + key + ", query= " + query + 
//				", browser= " + browser + ", hour= " + hour + "}";
//	}
	//값 확인을 위한 toString
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
