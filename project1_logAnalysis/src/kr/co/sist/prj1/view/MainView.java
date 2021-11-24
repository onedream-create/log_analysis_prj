package kr.co.sist.prj1.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.ScrollPane;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import kr.co.sist.prj1.evt.MainViewEvt;

@SuppressWarnings("serial")
public class MainView extends JDialog {

	private JButton jbtnView;
	private JButton jbtnReport;
	private JButton jbtnSelectFile;

	private JTextArea jtaLog;

	private JTextField jtfStartValue;
	private JTextField jtfEndValue;

	private JLabel jlbRange;
	public JLabel jlbFileName;

	public MainView(LoginView lv) {
		super(lv, "�α� �м�", true);

		// ���� ������ ���õǾ����� Ȯ���� �� �ִ� ��
		jlbFileName = new JLabel("���õ� ���� ����");

		// �� ������ �������� �󸶳� ������ �� �ִ��� �˷��ִ� ��
		jlbRange = new JLabel("������ �� �ִ� ������ ����");

		jbtnView = new JButton("View");
		jbtnReport = new JButton("Report");
		jbtnSelectFile = new JButton("���� ã��");

		jtaLog = new JTextArea();
		ScrollPane sp = new ScrollPane();
		sp.add(jtaLog);

		jtfStartValue = new JTextField(6);
		jtfEndValue = new JTextField(6);
		JLabel jlbTilde = new JLabel("~");
		
		JPanel jpRange = new JPanel();
		jpRange.setBackground(new Color(255, 245, 246));
		jpRange.add(jtfStartValue);
		jpRange.add(jlbTilde);
		jpRange.add(jtfEndValue);

		// �ٹ̱�
		jbtnView.setBackground(Color.pink);
		jbtnReport.setBackground(Color.pink);
		jbtnSelectFile.setBackground(Color.white);

		// ��Ʈ
		Font font = new Font("Times", Font.BOLD, 15);
		Font font2 = new Font("Times", Font.BOLD, 12);
		
		// ��Ʈ ����
		jbtnView.setFont(font);
		jbtnView.setForeground(Color.white);
		jbtnReport.setFont(font);
		jbtnReport.setForeground(Color.white);
		jbtnSelectFile.setFont(font2);
		jlbRange.setFont(font2);
		jlbFileName.setFont(font2);


		JPanel background = new JPanel();
		background.setBackground(new Color(255, 245, 246));
		background.setLayout(null);

		jlbRange.setBounds(55, 30, 200, 50);
		jbtnView.setBounds(350, 40, 90, 60);
		jbtnReport.setBounds(450, 40, 90, 60);
		jbtnSelectFile.setBounds(220, 40, 90, 30);
		jpRange.setBounds(30, 70, 200, 50);
		jlbFileName.setBounds(50, 130, 100, 20);
		sp.setBounds(50, 150, 500, 400);

		background.add(jbtnView);
		background.add(jbtnReport);
		background.add(jbtnSelectFile);
		background.add(jlbFileName);
		background.add(sp);
		background.add(jlbRange);
		background.add(jpRange);

		add(background);
		
		// ������Ʈ���� �̺�Ʈ ���
		// has a ������ Ŭ�������� �̺�Ʈ ó��
		MainViewEvt mve = new MainViewEvt(this, lv);// has a ����
		jbtnView.addActionListener(mve);
		jbtnReport.addActionListener(mve);
		jbtnSelectFile.addActionListener(mve);


		setBounds(lv.getX() + 100, lv.getY() + 100, 630, 600);

		setResizable(false);
		setVisible(true);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
	}

	public JButton getJbtnView() {
		return jbtnView;
	}

	public JButton getJbtnReport() {
		return jbtnReport;
	}

	public JButton getJbtnSelectFile() {
		return jbtnSelectFile;
	}

	public JTextArea getJtaLog() {
		return jtaLog;
	}

	public JTextField getJtfStartValue() {
		return jtfStartValue;
	}

	public JTextField getJtfEndValue() {
		return jtfEndValue;
	}

	public JLabel getJlbRange() {
		return jlbRange;
	}

	public JLabel getJlbFileName() {
		return jlbFileName;
	}
}// class
