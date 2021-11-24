package kr.co.sist.prj1.evt;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JOptionPane;
import javax.swing.JPasswordField;

import kr.co.sist.prj1.view.LoginView;
import kr.co.sist.prj1.view.MainView;

public class LoginViewEvt implements ActionListener {
	private LoginView lv;

	private Map<String, String> mapMember;

	public LoginViewEvt(LoginView lv) {
		this.lv = lv;

		// 기존 회원정보
		mapMember = new HashMap<String, String>();
		mapMember.put("administrator", "12345");
		mapMember.put("admin", "1234");
		mapMember.put("root", "1111");
	}

	//회원인증
	public void certificationMember() {
		String id = lv.getJtfId().getText();
		String pw = getStringPassward(lv.getJpfPw());

		// 로그인 창에서 입력한 아이디가 map의 키값에 존재하는 경우.
		if (mapMember.containsKey(id)) {
			// 아이디(key)의 value가 입력한 비밀번호가 같은경우
			if (mapMember.get(id).equals(pw)) { 	
				new MainView(lv); 
			} else { // 비밀번호만 틀렸을 경우
				JOptionPane.showMessageDialog(lv, "비밀번호가 잘못 입력되었습니다.");
			} 
		} else { // 아이디가 존재하지 않을 경우
			JOptionPane.showMessageDialog(lv, "아이디가 잘못 입력되었습니다.");
		} 
	}
	
	//비밀번호값 String변환
	public String getStringPassward(JPasswordField pf) {
		String pw=new String(pf.getPassword());
		return pw;
	}
	
	@Override
	public void actionPerformed(ActionEvent ae) {
		certificationMember(); //회원인증
	} 
}
