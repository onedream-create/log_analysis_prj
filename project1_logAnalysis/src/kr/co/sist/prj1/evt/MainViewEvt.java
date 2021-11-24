package kr.co.sist.prj1.evt;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import kr.co.sist.prj1.service.RequirementsAnalysis;
import kr.co.sist.prj1.view.LoginView;
import kr.co.sist.prj1.view.MainView;
import kr.co.sist.prj1.vo.LogDataVO;

public class MainViewEvt implements ActionListener {

	private MainView mv;
	private LoginView lv;

	private List<LogDataVO> listLog;

	private File file;

	public MainViewEvt(MainView mv, LoginView lv) {
		this.mv = mv;
		this.lv = lv;
	}

	public void selectFile() {
		int sel = 0;
		JFileChooser fc = new JFileChooser();
		// Ȯ���� ���� log�� ���ϸ� ���� �����ϵ��� �Ѵ�.
		fc.setAcceptAllFileFilterUsed(false);
		fc.addChoosableFileFilter(new FileNameExtensionFilter("*.log", "log"));
		fc.setDialogTitle("������ �����ϼ���");
		fc.setCurrentDirectory(new File(System.getProperty("user.home") + "/Desktop"));
		sel = fc.showOpenDialog(mv);

		if (sel == JFileChooser.APPROVE_OPTION) {
			try {
				file = fc.getSelectedFile();
				setLogDataVO();
				mv.getJlbFileName().setText(file.getName());
			} catch (IOException ie) {
				ie.printStackTrace();
				JOptionPane.showMessageDialog(mv, "������ ó���� ���� �߻�");
				// MainView ����
				resetMainView();
			} finally {
				mv.getJtaLog().setText("");
			}
		}
	}

	public void setLogDataVO() throws IOException {
		listLog = new ArrayList<LogDataVO>();

		String str = "";
		String key = "";
//		String query = "�ʿ����";
		String hour = "";

		BufferedReader br = null;
		int cnt = 0;

		try {
			br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));

			String[] tempArr = null;
			while ((str = br.readLine()) != null) {
				try {
					// []�� �������� ���ڿ��� ������ �迭�� ����
					tempArr = str.substring(1, str.length() - 1).split("\\]\\[");

					// �ð����� �� ���� ������ ����.
					hour = tempArr[3].substring(11, 13);

					// "key="�� �����Ҷ��� Ű�� ������ ��Ÿ���� ��ҿ��� Ű���� ������ �Ҵ�.
					if (tempArr[1].contains("key=")) {
						key = tempArr[1].substring(tempArr[1].indexOf("=") + 1, tempArr[1].indexOf("&"));
					} else {
						key = null;
					}

					// VO ����, list �߰�
//					listLog.add(new LogDataVO(tempArr[0], key, query, tempArr[2], hour));
					listLog.add(new LogDataVO(tempArr[0], key, tempArr[2], hour));
				} catch (ArrayIndexOutOfBoundsException aioobe) {
					aioobe.printStackTrace();
					cnt++;
				}
			}
			if (cnt != 0) {
				JOptionPane.showMessageDialog(mv, "���ʿ��� ���� ���ԵǾ��ֽ��ϴ�. ������ ���� �� : " + cnt + "��");
			}
			// �α� ������ ������ ������
			mv.getJlbRange().setText("1 ~ " + String.valueOf(listLog.size()));
		} finally {
			if (br != null) {
				br.close();
			}
		}
	}

	public void view() throws NumberFormatException {
		// 7�� �䱸������ ���� ������ ����
		int startNum = Integer.parseInt(mv.getJtfStartValue().getText());
		int endNum = Integer.parseInt(mv.getJtfEndValue().getText());

		// �������� �������� ������ �޼������̾�α�
		if ((startNum < 1) || (startNum > endNum) || (endNum > listLog.size())) {
			JOptionPane.showMessageDialog(mv, "������ �ٽ� �Է����ּ���");
			mv.getJtfStartValue().setText("");
			mv.getJtfEndValue().setText("");
		} else {
			// �䱸����ó�� ��ü ����
			RequirementsAnalysis ra = new RequirementsAnalysis(listLog, mv);

			// �α׸� ������ ��¥ �� �ð��� �����ֱ� ���� �۾�.
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String strTime = sdf.format(new Date(System.currentTimeMillis()));

			// �α��� ������ append.
			mv.getJtaLog().append("===============================================\n");
			mv.getJtaLog().append("  ���ϸ� (" + file.getName().substring(0, file.getName().indexOf("."))
					+ ")   log ������ ��¥ " + strTime + "\n");
			mv.getJtaLog().append("===============================================\n");
			mv.getJtaLog().append("  1��) �ִٻ�� Ű�� �̸��� Ƚ�� \n");
			mv.getJtaLog().append("         >> " + ra.getMostUseKey() + "\n");
			mv.getJtaLog().append("-------------------------------------------------------------------------------\n");
			mv.getJtaLog().append("  2��) �������� ����Ƚ��, ����\n");
			mv.getJtaLog().append("         >> " + ra.getRatioBrowser() + "\n");
			mv.getJtaLog().append("-------------------------------------------------------------------------------\n");
			mv.getJtaLog().append("  3��) ���񽺸� ���������� ������ Ƚ��, ����(404) Ƚ��\n");
			mv.getJtaLog().append("         >> " + ra.getCountServiceResult() + "\n");
			mv.getJtaLog().append("-------------------------------------------------------------------------------\n");
			mv.getJtaLog().append("  4��) ��û�� ���� ���� �ð� [�ð� : Ƚ��] => [" + ra.getMostRequestTime() + "] \n");
			mv.getJtaLog().append("-------------------------------------------------------------------------------\n");
			mv.getJtaLog().append("  5��) ���������� ��û(403)�� �߻��� Ƚ��, ����\n");
			mv.getJtaLog().append("         >> " + ra.getCountUnusualRequest() + "\n");
			mv.getJtaLog().append("-------------------------------------------------------------------------------\n");
			mv.getJtaLog().append("  6��) ��û�� ���� ����(500)�� �߻��� Ƚ��, ����\n");
			mv.getJtaLog().append("         >> " + ra.getCountError() + "\n");
			mv.getJtaLog().append("-------------------------------------------------------------------------------\n");
			mv.getJtaLog().append(
					"  7��) �ԷµǴ� ���ο� �ش��ϴ� �������(" + String.valueOf(startNum) + " ~ " + String.valueOf(endNum) + ")��° \n");
			mv.getJtaLog().append("        ���ο� �ش��ϴ� ���� �� �ִ� ���Ű�� �̸��� Ƚ��\n");
			mv.getJtaLog().append("         >> " + ra.getRangeMostUseKey() + "\n");
		}
	}

	public void resetMainView() {
		mv.getJlbRange().setText("������ �� �ִ� ������ ����");
		mv.getJlbFileName().setText("");
		listLog = null;
		file = null;
	}

	public void report() throws IOException {
		BufferedWriter bw = null;

		String data = mv.getJtaLog().getText();

		String longTypeTime = String.valueOf(System.currentTimeMillis());

		File dir = new File("C:\\dev\\report");

		// ��ο� report��� ������ �������� ������ ������ �����Ѵ�.
		if (!dir.exists()) {
			dir.mkdirs();
		}

		// ���ϻ���
		File file = new File(dir.getAbsolutePath() + "/report_" + longTypeTime + ".dat");
		try {
			// ��Ʈ�� ������ ���Ͽ� ����
			bw = new BufferedWriter(new FileWriter(file));
			// ���� �Է�
			bw.write(data);
			// ��Ʈ���� ��ϵ� ������ ������ ���Ϸ� ����
			bw.flush();
		} finally {
			// ��Ʈ�� ���� ���� (��Ʈ���� ��ϵ� ������ �������� ����ǰ� ������ ��������.)
			if (bw != null) {
				bw.close();
			}
		}
	}

	@Override
	public void actionPerformed(ActionEvent ae) {
		if (ae.getSource() == mv.getJbtnSelectFile()) {
			selectFile();
		}

		if (ae.getSource() == mv.getJbtnView()) {
			mv.getJtaLog().setText("");
			// ���õ� ������ ���������� view Method ȣ��.
			if (file != null) {
				try {
					view();
				} catch (NumberFormatException nfe) {
					JOptionPane.showMessageDialog(mv, "���ڸ� �Է����ּ���");
				}
			} else {
				JOptionPane.showMessageDialog(mv, "������ �������ּ���");
			}
			mv.getJtfStartValue().setText("");
			mv.getJtfEndValue().setText("");
		}

		if (ae.getSource() == mv.getJbtnReport()) {
			if (!lv.getJtfId().getText().equals("root")) {
				// view method�� ȣ��� ���Ŀ��� report Method ȣ��.
				if (!(mv.getJtaLog().getText().equals(""))) {
					try {
						report();
						JOptionPane.showMessageDialog(mv, "����Ʈ �ۼ� �Ϸ�");
						mv.getJtaLog().setText("");
					} catch (IOException ie) {
						JOptionPane.showMessageDialog(mv, "������������� ������ �߻��Ͽ����ϴ�.");
						ie.printStackTrace();
					}
				} else {
					JOptionPane.showMessageDialog(mv, "View ��ư�� ���� �����ּ���");
				}
			} else {
				JOptionPane.showMessageDialog(mv, "Report�� ������ �����ϴ�");
			}
		}
	}
}
