package com.monitoring;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.ListCellRenderer;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

import com.monitoring.Settings.SaveData;
import com.monitoring.Settings.Settings;

public class FrameSettings extends JFrame implements ActionListener {

	final long S = 1000L;
	final long M = 60000L;
	final long H = 360000L;

	JPanel panelButtons = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 5));

	JToggleButton toggleRefresh = new JToggleButton("새로고침 동작중");
	JCheckBox chkShowProgressbar = new JCheckBox("진행바 표시");

	JButton helpButton = null;
	
	String rateString[] = { "5초", "10초", "20초", "30초", "60초", "2분", "3분", "5분", "10분", "20분", "30분", "1시간", "2시간",
			"4시간", "8시간" };
	long rateLong[] = { 5 * S, 10 * S, 20 * S, 30 * S, M, 2 * M, 3 * M, 5 * M, 10 * M, 20 * M, 30 * M, H, 2 * H, 4 * H,
			8 * H };
	JComboBox comboBoxRefreshRate = new JComboBox(rateString);

	String rotateString[] = { "끄기", "10초", "20초", "30초", "1분", "1분 30초", "2분", "3분", "5분", "10분", "20분", "30분", "40분",
			"1시간", "2시간", "4시간", "8시간", "12시간" };
	long rotateLong[] = { 0, 10 * S, 20 * S, 30 * S, M, M + 30 * S, 2 * M, 3 * M, 5 * M, 10 * M, 20 * M, 30 * M, 40 * M,
			H, 2 * H, 4 * H, 8 * H, 12 * H };
	JComboBox comboBoxRotateRate = new JComboBox(rotateString);

	JPanel panelList = new JPanel(new BorderLayout(0, 5));

	JPanel panelButtonList = new JPanel(new FlowLayout(FlowLayout.RIGHT));
	JButton buttonPlus = new JButton("페이지 추가");
	JButton buttonReset = new JButton("모두 삭제");
	JButton buttonUp = new JButton("▲");
	JButton buttonDown = new JButton("▼");

	
	JList listWebsites = new JList(Settings.vec_Sites);
	JScrollPane scrollListWeb = new JScrollPane(listWebsites, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
			JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

	
	
	boolean isShowSidePanel = false;
	JPanel panelSideInfo = new JPanel();
	
	JTextField tfSiteName = new JTextField(SwingConstants.CENTER);
	JTextField tfSiteAddress = new JTextField(SwingConstants.CENTER);
	
	String zoomAry[] = {"-10", "-9", "-8", "-7", "-6", "-5", "-4", "-3", "-2", "-1", "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10"};
	
	JComboBox comboBoxZoom = new JComboBox(zoomAry);
	
	JButton buttonSaveData = new JButton("적  용");
	JButton buttonRemove = new JButton("삭  제");
	
	WebFrame webFrame = null;
	
	public FrameSettings(WebFrame webFrame) {
		
		super("환경 설정");
		setVisible(false);

		this.webFrame = webFrame;

		setSize(800, 400);
		setLocationByPlatform(true);
		setAlwaysOnTop(true);
//		/setResizable(false);
		setLayout(new BorderLayout(0, 5));

		settingToggleRefreshButton();

		settingRefreshRateComboBox();

		settingRotateRateCombobox();

		settingShowProgressBarChk();

		settingWebListPanel();

		settingSidePanel();
		
	}

	// 새로고침 활성/ 비활성화 버튼
	private void settingToggleRefreshButton() {
		toggleRefresh.setSelected(Settings.isCurrentRefreshing);
		toggleRefresh.setForeground(Color.RED);
		toggleRefresh.setText("새로고침 동작중");
		toggleRefresh.setFont(new Font("맑은 고딕", Font.BOLD, 14));
		toggleRefresh.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (((JToggleButton) e.getSource()).isSelected()) {
					((JToggleButton) e.getSource()).setForeground(Color.RED);
					((JToggleButton) e.getSource()).setText("새로고침 동작중");
					Settings.isCurrentRefreshing = true;
				} else {
					((JToggleButton) e.getSource()).setForeground(Color.BLACK);
					((JToggleButton) e.getSource()).setText("새로고침 중지");
					Settings.isCurrentRefreshing = false;
				}

			}
		});
		panelButtons.add(toggleRefresh);
	}

	// 새로고침 주기 콤보박스
	private void settingRefreshRateComboBox() {
		
		int index = 0;
		for(Long ln:rateLong)
		{
			if(ln == null) break;
			if(Settings.refreshTime == ln)
				break;
			index++;
		}
		
		comboBoxRefreshRate.setSelectedIndex(index);
		
		panelButtons.add(Box.createRigidArea(new Dimension(30, 0)));
		panelButtons.add(new JLabel("새로고침 주기 :"));
		panelButtons.add(comboBoxRefreshRate);

		comboBoxRefreshRate.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				int index = ((JComboBox) e.getSource()).getSelectedIndex();

				Settings.refreshTime = rateLong[index];
				
				SaveData.saveData();

			}
			
			
		});
	}

	// 화면 전환 주기 콤보박스
	private void settingRotateRateCombobox() {
		
		
		int index = 0;
		for(Long ln:rotateLong)
		{
			if(ln == null) break;
			if(Settings.rotateTime == ln)
				break;
			index++;
		}
		
		comboBoxRotateRate.setSelectedIndex(index);
		panelButtons.add(Box.createRigidArea(new Dimension(30, 0)));
		panelButtons.add(new JLabel("웹 페이지 전환 주기 :"));
		panelButtons.add(comboBoxRotateRate);

		comboBoxRotateRate.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				int index = ((JComboBox) e.getSource()).getSelectedIndex();

				if (index == 0)
				{
					Settings.isCurrentRotate = false;
					Settings.rotateTime = rotateLong[index];
				}
				else {
					Settings.isCurrentRotate = true;
					Settings.rotateTime = rotateLong[index];
				}

				SaveData.saveData();
				
			}
		});
	}

	// 프로그래스 바 표시 체크박스
	private void settingShowProgressBarChk() {
		panelButtons.add(Box.createRigidArea(new Dimension(30, 0)));
		chkShowProgressbar.setSelected(Settings.isShowProgressBar);
		chkShowProgressbar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (((JCheckBox) e.getSource()).isSelected())
					Settings.isShowProgressBar = true;
				else
					Settings.isShowProgressBar = false;
			}
		});

		panelButtons.add(chkShowProgressbar);
		
		helpButton = new JButton("?");
		panelButtons.add(helpButton);
		helpButton.addActionListener(this);

		add(panelButtons, BorderLayout.NORTH);
	}

	//리스트 패널 
	private void settingWebListPanel() {
		Font buttonFont = new Font("맑은 고딕", Font.BOLD, 12);

		buttonPlus.setFont(buttonFont);
		buttonReset.setFont(buttonFont);
		buttonUp.setFont(buttonFont);
		buttonDown.setFont(buttonFont);

		buttonPlus.addActionListener(this);
		buttonReset.addActionListener(this);
		buttonUp.addActionListener(this);
		buttonDown.addActionListener(this);
		
		buttonUp.setEnabled(false);
		buttonDown.setEnabled(false);

		panelButtonList.add(buttonPlus);
		panelButtonList.add(buttonReset);
		
		panelButtonList.add(buttonUp);
		panelButtonList.add(buttonDown);
		
		panelList.add(panelButtonList, BorderLayout.NORTH);

		panelList.add(scrollListWeb, BorderLayout.CENTER);

		listWebsites.setVisibleRowCount(5);
		listWebsites.setEnabled(true);
		listWebsites.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		listWebsites.setCellRenderer(new CellRenderer());
		
		listWebsites.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseExited(MouseEvent e) {

				
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				
				if (e.getClickCount() == 1) 
				{
					panelSideInfo.setVisible(true);
					buttonUp.setEnabled(true);
					buttonDown.setEnabled(true);
					
				    int index = listWebsites.locationToIndex(e.getPoint());
				    SiteData entry = (SiteData) listWebsites.getSelectedValue();
				    
				    setSidePanelBorder(entry.siteName);
				    setSidePanelInformation(entry);
				    
				}
			}
		});

		add(panelList, BorderLayout.CENTER);
	}
	
	private void settingSidePanel()
	{
		Font font = new Font("맑은 고딕", Font.BOLD, 13);
		
		add(panelSideInfo, BorderLayout.EAST);
		panelSideInfo.setPreferredSize(new Dimension(200, 600));
		panelSideInfo.setBorder(BorderFactory.createTitledBorder(null, "temp", TitledBorder.CENTER, TitledBorder.DEFAULT_JUSTIFICATION, new Font("맑은 고딕", Font.BOLD, 13)));
		panelSideInfo.setVisible(false);
		
		panelSideInfo.add(Box.createRigidArea(new Dimension(220, 30)));
		
		JLabel tmpLabel = new JLabel("사이트 이름");
		tmpLabel.setFont(font);
		panelSideInfo.add(tmpLabel);
		tfSiteName.setPreferredSize(new Dimension(170, 20));
		panelSideInfo.add(tfSiteName);
		
		panelSideInfo.add(Box.createRigidArea(new Dimension(220, 10)));
		JLabel tmpLabel2 = new JLabel("사이트 주소");
		tmpLabel2.setFont(font);
		panelSideInfo.add(tmpLabel2);
		tfSiteAddress.setPreferredSize(new Dimension(170, 20));
		panelSideInfo.add(tfSiteAddress);
		
		panelSideInfo.add(Box.createRigidArea(new Dimension(220, 10)));
		comboBoxZoom.setSelectedIndex(10);
		panelSideInfo.add(new JLabel("확대 : "));
		panelSideInfo.add(comboBoxZoom);
		
		comboBoxZoom.setSelectedIndex(20);
		
		panelSideInfo.add(Box.createRigidArea(new Dimension(220, 10)));
		
		panelSideInfo.add(buttonSaveData);
		buttonSaveData.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				
				SiteData entry = (SiteData) listWebsites.getSelectedValue();
				
				entry.reData(tfSiteName.getText(), tfSiteAddress.getText(), Integer.valueOf((String)comboBoxZoom.getSelectedItem()));
				
				setSidePanelBorder(entry.siteName);
				
				listWebsites.setCellRenderer(new CellRenderer());
				webFrame.printPanelSite();
				
				SaveData.saveData();
			}
		});
		
		panelSideInfo.add(Box.createRigidArea(new Dimension(220, 10)));
		panelSideInfo.add(buttonRemove);
		buttonRemove.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				
				SiteData entry = (SiteData) listWebsites.getSelectedValue();
				int selIndex = listWebsites.getSelectedIndex();
				Settings.vec_Sites.remove(entry);
				Settings.vec_Sites.removeElement(entry);
				
				entry.removeThis();
				entry = null;
				
				if(!(selIndex + 1 > Settings.vec_Sites.size()-1))
				{
					
					SiteData entryNew = (SiteData) listWebsites.getSelectedValue();
					int selIndexNew = listWebsites.getSelectedIndex();
					
					setSidePanelBorder(entryNew.siteName);
				    setSidePanelInformation(entryNew);
				}
				else
				{
					listWebsites.setSelectedIndex(selIndex-1);
					SiteData entryNew = (SiteData) listWebsites.getSelectedValue();
					int selIndexNew = listWebsites.getSelectedIndex();
					
					setSidePanelBorder(entryNew.siteName);
				    setSidePanelInformation(entryNew);
				}
				
				listWebsites.setCellRenderer(new CellRenderer());
				webFrame.printPanelSite();
				
				SaveData.saveData();
			}
		});
		
	}
	
	private void setSidePanelBorder(String title)
	{
		panelSideInfo.setBorder(BorderFactory.createTitledBorder(null, title, TitledBorder.CENTER, TitledBorder.DEFAULT_JUSTIFICATION, new Font("맑은 고딕", Font.BOLD, 15)));
	}
	
	private void setSidePanelInformation(SiteData entry)
	{
		tfSiteName.setText(entry.siteName);
		tfSiteName.setHorizontalAlignment(JTextField.CENTER);
		
		tfSiteAddress.setText(entry.siteAddress);
		tfSiteAddress.setHorizontalAlignment(JTextField.CENTER);
		
		comboBoxZoom.setSelectedItem(String.valueOf(entry.zoom));
		
	}

	@Override
	public void actionPerformed(ActionEvent arg0) 
	{
		if (arg0.getSource() == buttonPlus) 
		{
			//웹 페이지 추가 작업
			Settings.vec_Sites.add(new SiteData("빈 페이지", "about:blank", 0));
			listWebsites.setCellRenderer(new CellRenderer());
			webFrame.printPanelSite();
			
			SaveData.saveData();
			
		} else if (arg0.getSource() == buttonReset) 
		{
			for(SiteData site:Settings.vec_Sites)
			{
				if(site == null) break;
				site.removeThis();
				site = null;
			}
			
			Settings.vec_Sites.removeAllElements();
			
			listWebsites.setCellRenderer(new CellRenderer());
			webFrame.printPanelSite();
			webFrame.removeWebPanel();
			
			panelSideInfo.setVisible(false);
			
			SaveData.saveData();
		}
		
		else if(arg0.getSource() == buttonUp)
		{
			
			SiteData entry = (SiteData) listWebsites.getSelectedValue();
			
			int selIndex = listWebsites.getSelectedIndex();
			
			if(!(selIndex - 1 < 0))
			{
				SiteData tmpData = Settings.vec_Sites.get(selIndex-1);
				Settings.vec_Sites.set(selIndex-1, Settings.vec_Sites.get(selIndex));
				Settings.vec_Sites.set(selIndex, tmpData);
				
				listWebsites.setSelectedIndex(selIndex-1);
				
				listWebsites.setCellRenderer(new CellRenderer());
				webFrame.printPanelSite();
				
				SaveData.saveData();
			}
		}
		
		else if(arg0.getSource() == buttonDown)
		{
			SiteData entry = (SiteData) listWebsites.getSelectedValue();
			
			int selIndex = listWebsites.getSelectedIndex();
			
			if(!(selIndex + 1 > Settings.vec_Sites.size()-1))
			{
				SiteData tmpData = Settings.vec_Sites.get(selIndex+1);
				Settings.vec_Sites.set(selIndex+1, Settings.vec_Sites.get(selIndex));
				Settings.vec_Sites.set(selIndex, tmpData);
				
				listWebsites.setSelectedIndex(selIndex+1);
				
				listWebsites.setCellRenderer(new CellRenderer());
				webFrame.printPanelSite();
				
				SaveData.saveData();
			}
		}
		
		else if(arg0.getSource() == helpButton)
		{
			String message = "<HTML><CENTER><H4>"
					+ "웹페이지 통합 모니터링 도구 1.0v<BR>"
					+ "20161126<BR>"
					+ "626th<BR>"
					+ "</HTML>";
			
			
			JOptionPane.showMessageDialog(this, message, "Help", JOptionPane.INFORMATION_MESSAGE);
		}

	}
	
	class CellRenderer extends JPanel implements ListCellRenderer 
	{
		
		private final Color HIGHLIGHT_COLOR = new Color(167,219,216	);

		JLabel labelIndex = new JLabel("index");
		JLabel labelWebName = new JLabel("Web Site Name");
		JLabel labelWebAddress = new JLabel("Web Site Address");
		
		JLabel labelZoom = new JLabel("zoom");
		
		boolean hasSelected = false;
		
		
		public CellRenderer() 
		{
			Font font = new Font("맑은 고딕", Font.PLAIN, 14);

			setPreferredSize(new Dimension(200, 20));
			
			setLayout(new GridLayout(1, 8, 0, 0));
			
			
			add(labelIndex);
			//add(Box.createRigidArea(new Dimension(50,0)));
			add(labelWebName);
			add(Box.createRigidArea(new Dimension(0,0)));
			//add(Box.createRigidArea(new Dimension(30,0)));
			add(labelWebAddress);
			add(Box.createRigidArea(new Dimension(0,0)));
			//add(Box.createRigidArea(new Dimension(50,0)));
			add(labelZoom);
			

			labelIndex.setFont(font);
			labelWebName.setFont(font);
			labelWebAddress.setFont(font);
			labelZoom.setFont(font);
			
			
			//setLayout(new FlowLayout(FlowLayout.LEFT));
			//setLayout(new BoxLayout(this, 0));
			
			
			hasSelected = false;
			
			setOpaque(true);
		}
		
		public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) 
		{
			
			SiteData entry = (SiteData) value;
			
			labelIndex.setText(String.valueOf(index + 1));
			labelWebName.setText(entry.siteName);
			labelWebAddress.setText(entry.siteAddress);
			labelZoom.setText("배율 : " + String.valueOf(entry.zoom));
			
			if (isSelected)
			{
				setBackground(HIGHLIGHT_COLOR);
				setForeground(new Color(224,228,204));
			}
			else 
			{
				setBackground(Color.white);
				setForeground(Color.black);
			}
			
			return this;
		}

	}

}

