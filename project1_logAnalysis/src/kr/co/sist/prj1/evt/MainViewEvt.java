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
		// 확장자 명이 log인 파일만 선택 가능하도록 한다.
		fc.setAcceptAllFileFilterUsed(false);
		fc.addChoosableFileFilter(new FileNameExtensionFilter("*.log", "log"));
		fc.setDialogTitle("파일을 선택하세요");
		fc.setCurrentDirectory(new File(System.getProperty("user.home") + "/Desktop"));
		sel = fc.showOpenDialog(mv);

		if (sel == JFileChooser.APPROVE_OPTION) {
			try {
				file = fc.getSelectedFile();
				setLogDataVO();
				mv.getJlbFileName().setText(file.getName());
			} catch (IOException ie) {
				ie.printStackTrace();
				JOptionPane.showMessageDialog(mv, "데이터 처리에 문제 발생");
				// MainView 리셋
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
//		String query = "필요없음";
		String hour = "";

		BufferedReader br = null;
		int cnt = 0;

		try {
			br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));

			String[] tempArr = null;
			while ((str = br.readLine()) != null) {
				try {
					// []을 기준으로 문자열을 나누어 배열에 저장
					tempArr = str.substring(1, str.length() - 1).split("\\]\\[");

					// 시간에서 시 만을 가져와 저장.
					hour = tempArr[3].substring(11, 13);

					// "key="을 포함할때만 키와 쿼리를 나타내는 요소에서 키만을 가져와 할당.
					if (tempArr[1].contains("key=")) {
						key = tempArr[1].substring(tempArr[1].indexOf("=") + 1, tempArr[1].indexOf("&"));
					} else {
						key = null;
					}

					// VO 생성, list 추가
//					listLog.add(new LogDataVO(tempArr[0], key, query, tempArr[2], hour));
					listLog.add(new LogDataVO(tempArr[0], key, tempArr[2], hour));
				} catch (ArrayIndexOutOfBoundsException aioobe) {
					aioobe.printStackTrace();
					cnt++;
				}
			}
			if (cnt != 0) {
				JOptionPane.showMessageDialog(mv, "불필요한 값이 포함되어있습니다. 제외한 라인 수 : " + cnt + "개");
			}
			// 로그 파일의 범위를 보여줌
			mv.getJlbRange().setText("1 ~ " + String.valueOf(listLog.size()));
		} finally {
			if (br != null) {
				br.close();
			}
		}
	}

	public void view() throws NumberFormatException {
		// 7번 요구사항을 위한 범위값 저장
		int startNum = Integer.parseInt(mv.getJtfStartValue().getText());
		int endNum = Integer.parseInt(mv.getJtfEndValue().getText());

		// 범위값이 적절하지 않을때 메세지다이얼로그
		if ((startNum < 1) || (startNum > endNum) || (endNum > listLog.size())) {
			JOptionPane.showMessageDialog(mv, "범위를 다시 입력해주세요");
			mv.getJtfStartValue().setText("");
			mv.getJtfEndValue().setText("");
		} else {
			// 요구사항처리 객체 생성
			RequirementsAnalysis ra = new RequirementsAnalysis(listLog, mv);

			// 로그를 생성한 날짜 및 시간을 보여주기 위한 작업.
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String strTime = sdf.format(new Date(System.currentTimeMillis()));

			// 로그의 내용을 append.
			mv.getJtaLog().append("===============================================\n");
			mv.getJtaLog().append("  파일명 (" + file.getName().substring(0, file.getName().indexOf("."))
					+ ")   log 생성된 날짜 " + strTime + "\n");
			mv.getJtaLog().append("===============================================\n");
			mv.getJtaLog().append("  1번) 최다사용 키의 이름과 횟수 \n");
			mv.getJtaLog().append("         >> " + ra.getMostUseKey() + "\n");
			mv.getJtaLog().append("-------------------------------------------------------------------------------\n");
			mv.getJtaLog().append("  2번) 브라우저별 접속횟수, 비율\n");
			mv.getJtaLog().append("         >> " + ra.getRatioBrowser() + "\n");
			mv.getJtaLog().append("-------------------------------------------------------------------------------\n");
			mv.getJtaLog().append("  3번) 서비스를 성공적으로 수행한 횟수, 실패(404) 횟수\n");
			mv.getJtaLog().append("         >> " + ra.getCountServiceResult() + "\n");
			mv.getJtaLog().append("-------------------------------------------------------------------------------\n");
			mv.getJtaLog().append("  4번) 요청이 가장 많은 시간 [시간 : 횟수] => [" + ra.getMostRequestTime() + "] \n");
			mv.getJtaLog().append("-------------------------------------------------------------------------------\n");
			mv.getJtaLog().append("  5번) 비정상적인 요청(403)이 발생한 횟수, 비율\n");
			mv.getJtaLog().append("         >> " + ra.getCountUnusualRequest() + "\n");
			mv.getJtaLog().append("-------------------------------------------------------------------------------\n");
			mv.getJtaLog().append("  6번) 요청에 대한 에러(500)가 발생한 횟수, 비율\n");
			mv.getJtaLog().append("         >> " + ra.getCountError() + "\n");
			mv.getJtaLog().append("-------------------------------------------------------------------------------\n");
			mv.getJtaLog().append(
					"  7번) 입력되는 라인에 해당하는 정보출력(" + String.valueOf(startNum) + " ~ " + String.valueOf(endNum) + ")번째 \n");
			mv.getJtaLog().append("        라인에 해당하는 정보 중 최다 사용키의 이름과 횟수\n");
			mv.getJtaLog().append("         >> " + ra.getRangeMostUseKey() + "\n");
		}
	}

	public void resetMainView() {
		mv.getJlbRange().setText("설정할 수 있는 범위값 없음");
		mv.getJlbFileName().setText("");
		listLog = null;
		file = null;
	}

	public void report() throws IOException {
		BufferedWriter bw = null;

		String data = mv.getJtaLog().getText();

		String longTypeTime = String.valueOf(System.currentTimeMillis());

		File dir = new File("C:\\dev\\report");

		// 경로에 report라는 폴더가 존재하지 않으면 폴더를 생성한다.
		if (!dir.exists()) {
			dir.mkdirs();
		}

		// 파일생성
		File file = new File(dir.getAbsolutePath() + "/report_" + longTypeTime + ".dat");
		try {
			// 스트림 목적지 파일에 연결
			bw = new BufferedWriter(new FileWriter(file));
			// 내용 입력
			bw.write(data);
			// 스트림에 기록된 내용을 목적지 파일로 분출
			bw.flush();
		} finally {
			// 스트림 연결 끊기 (스트림에 기록된 내용이 목적지로 분출되고 연결이 끊어진다.)
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
			// 선택된 파일이 있을때에만 view Method 호출.
			if (file != null) {
				try {
					view();
				} catch (NumberFormatException nfe) {
					JOptionPane.showMessageDialog(mv, "숫자만 입력해주세요");
				}
			} else {
				JOptionPane.showMessageDialog(mv, "파일을 선택해주세요");
			}
			mv.getJtfStartValue().setText("");
			mv.getJtfEndValue().setText("");
		}

		if (ae.getSource() == mv.getJbtnReport()) {
			if (!lv.getJtfId().getText().equals("root")) {
				// view method가 호출된 이후에만 report Method 호출.
				if (!(mv.getJtaLog().getText().equals(""))) {
					try {
						report();
						JOptionPane.showMessageDialog(mv, "리포트 작성 완료");
						mv.getJtaLog().setText("");
					} catch (IOException ie) {
						JOptionPane.showMessageDialog(mv, "파일저장과정에 오류가 발생하였습니다.");
						ie.printStackTrace();
					}
				} else {
					JOptionPane.showMessageDialog(mv, "View 버튼을 먼저 눌러주세요");
				}
			} else {
				JOptionPane.showMessageDialog(mv, "Report할 권한이 없습니다");
			}
		}
	}
}
