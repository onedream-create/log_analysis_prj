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

	private JButton jbtnLogin; // �α��� ��ư

	private JTextField jtfId; // ���̵� �Է� �ʵ�
	private JPasswordField jpfPw; // ��й�ȣ �Է� �ʵ�

	private JLabel imgLabel;
	private JLabel jlblID;
	private JLabel jlblPassword;
	
	public LoginView() {
		super("�α���");

		// ��ư, ��, �ؽ�Ʈ ���� �� ����
		jtfId = new JTextField();
		@SuppressWarnings("unused")
		TextHintEvt idHint = new TextHintEvt(jtfId, "���̵�"); // HintEvt ���� ���� ��������

		jpfPw = new JPasswordField();
		@SuppressWarnings("unused")
		TextHintEvt pwHint = new TextHintEvt(jpfPw, "��й�ȣ"); // HintEvt ���� ���� ��������
		jpfPw.setEchoChar('*');

		jbtnLogin = new JButton("Login");
		
		jlblID = new JLabel("I D");
		jlblPassword = new JLabel("PW");

		// ������
		// ������ ����
		ImageIcon icon = new ImageIcon("project1_logAnalysis/image/sist.png");
		ImageIcon ii = new ImageIcon("project1_logAnalysis/image/background.png");

		
		// �� ����
		imgLabel = new JLabel();

		// �󺧿� ������(�̹���)����
		imgLabel.setIcon(icon);
		// �� ����(ũ��, ����..)
		imgLabel.setBounds(110, 110, 400, 180); 

		// �����ӿ� ������Ʈ �߰�
		getContentPane().add(imgLabel);
		
		// ��Ʈ
		Font font = new Font("Times", Font.BOLD, 15);
		
		//�ٹ̱�
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
		
		// ��ġ
		jbtnLogin.setBounds(300, 250, 100, 70);
		jtfId.setBounds(100, 250, 180, 35);
		jpfPw.setBounds(100, 290, 180, 35);
		jlblID.setBounds(70, 250, 90, 35);
		jlblPassword.setBounds(70, 290, 90, 35);

		// �����ӿ� �߰�
		background.add(jpfPw);
		background.add(jtfId);
		background.add(jbtnLogin);
		background.add(jlblID);
		background.add(jlblPassword);
		
		add(background);

		// �̺�Ʈ ����
		LoginViewEvt lve = new LoginViewEvt(this);
		jbtnLogin.addActionListener(lve); // �α��� ��ư �̺�Ʈ �߰�

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
