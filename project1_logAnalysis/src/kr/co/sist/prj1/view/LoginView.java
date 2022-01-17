package kr.co.sist.prj1.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import kr.co.sist.prj1.evt.LoginViewEvt;
import kr.co.sist.prj1.evt.TextHintEvt;

@SuppressWarnings("serial")
public class LoginView extends JFrame {

	private JButton jbtnLogin; // 로그인 버튼

	private JTextField jtfId; // 아이디 입력 필드
	private JPasswordField jpfPw; // 비밀번호 입력 필드

	private JLabel imgLabel;
	private JLabel jlblID;
	private JLabel jlblPassword;
	
	public LoginView() {
		super("로그인");

		// 버튼, 라벨, 텍스트 정의 및 생성
		jtfId = new JTextField();
		@SuppressWarnings("unused")
		TextHintEvt idHint = new TextHintEvt(jtfId, "아이디"); // HintEvt 직접 구현 하지않음

		jpfPw = new JPasswordField();
		@SuppressWarnings("unused")
		TextHintEvt pwHint = new TextHintEvt(jpfPw, "비밀번호"); // HintEvt 직접 구현 하지않음
		jpfPw.setEchoChar('*');

		jbtnLogin = new JButton("Login");
		
		jlblID = new JLabel("I D");
		jlblPassword = new JLabel("PW");

		// 디자인
		// 아이콘 생성
		ImageIcon icon = new ImageIcon("project1_logAnalysis/image/sist.png");
		ImageIcon ii = new ImageIcon("project1_logAnalysis/image/background.png");

		
		// 라벨 생성
		imgLabel = new JLabel();

		// 라벨에 아이콘(이미지)설정
		imgLabel.setIcon(icon);
		// 라벨 설정(크기, 정렬..)
		imgLabel.setBounds(110, 110, 400, 180); 

		// 프레임에 컴포넌트 추가
		getContentPane().add(imgLabel);
		
		// 폰트
		Font font = new Font("Times", Font.BOLD, 15);
		
		//꾸미기
		jlblID.setForeground(Color.pink);
		jlblPassword.setForeground(Color.pink);
		jbtnLogin.setBackground(Color.pink);
		jbtnLogin.setForeground(Color.white);
		jbtnLogin.setFont(font);
		
		JPanel background = new JPanel() {
			public void paintComponent(Graphics g) {
				g.drawImage(ii.getImage(), 0, 0, null);
				setOpaque(false);
				super.paintComponent(g);
			}
		};
		
		background.setLayout(null);
		
		// 배치
		jbtnLogin.setBounds(300, 250, 100, 70);
		jtfId.setBounds(100, 250, 180, 35);
		jpfPw.setBounds(100, 290, 180, 35);
		jlblID.setBounds(70, 250, 90, 35);
		jlblPassword.setBounds(70, 290, 90, 35);

		// 프레임에 추가
		background.add(jpfPw);
		background.add(jtfId);
		background.add(jbtnLogin);
		background.add(jlblID);
		background.add(jlblPassword);
		
		add(background);

		// 이벤트 연결
		LoginViewEvt lve = new LoginViewEvt(this);
		jbtnLogin.addActionListener(lve); // 로그인 버튼 이벤트 추가

		setBounds(100, 100, 500, 500);
		setResizable(false);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public JTextField getJtfId() {
		return jtfId;
	}

	public JPasswordField getJpfPw() {
		return jpfPw;
	}

}
