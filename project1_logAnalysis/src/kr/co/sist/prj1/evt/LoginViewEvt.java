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

		// ���� ȸ������
		mapMember = new HashMap<String, String>();
		mapMember.put("administrator", "12345");
		mapMember.put("admin", "1234");
		mapMember.put("root", "1111");
	}

	//ȸ������
	public void certificationMember() {
		String id = lv.getJtfId().getText();
		String pw = getStringPassward(lv.getJpfPw());

		// �α��� â���� �Է��� ���̵� map�� Ű���� �����ϴ� ���.
		if (mapMember.containsKey(id)) {
			// ���̵�(key)�� value�� �Է��� ��й�ȣ�� �������
			if (mapMember.get(id).equals(pw)) { 	
				new MainView(lv); 
			} else { // ��й�ȣ�� Ʋ���� ���
				JOptionPane.showMessageDialog(lv, "��й�ȣ�� �߸� �ԷµǾ����ϴ�.");
			} 
		} else { // ���̵� �������� ���� ���
			JOptionPane.showMessageDialog(lv, "���̵� �߸� �ԷµǾ����ϴ�.");
		} 
	}
	
	//��й�ȣ�� String��ȯ
	public String getStringPassward(JPasswordField pf) {
		String pw=new String(pf.getPassword());
		return pw;
	}
	
	@Override
	public void actionPerformed(ActionEvent ae) {
		certificationMember(); //ȸ������
	} 
}
