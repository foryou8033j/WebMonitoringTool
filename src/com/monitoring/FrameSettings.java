package com.monitoring;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JToggleButton;

import com.monitoring.Settings.Settings;

public class FrameSettings extends JFrame
{

	final long S = 1000L;
	final long M = 60000L;
	final long H = 360000L;
	
	JPanel panelButtons = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 5));
	
	JToggleButton toggleRefresh = new JToggleButton("새로고침 동작중");
	JCheckBox chkShowProgressbar = new JCheckBox("진행바 표시");
	
	
	String rateString[] = {"5초", "10초", "20초", "30초", "60초", "2분", "3분", "5분", "10분", "20분", "30분", "1시간", "2시간", "4시간", "8시간"};
	long rateLong[] = {5*S, 10*S, 20*S, 30*S, M, 2*M, 3*M, 5*M, 10*M, 20*M, 30*M, H, 2*H, 4*H, 8*H};
	JComboBox comboBoxRefreshRate = new JComboBox(rateString);
	
	String rotateString[] = {"끄기", "10초", "20초", "30초", "1분", "1분 30초", "2분", "3분", "5분", "10분", "20분", "30분", "40분", "1시간", "2시간", "4시간", "8시간", "12시간"};
	long ratateLong[] = {0, 10*S, 20*S, 30*S, M, M + 30*S, 2*M, 3*M, 5*M, 10*M, 20*M, 30*M, 40*M, H, 2*H, 4*H, 8*H, 12*H};
	JComboBox comboBoxRotateRate = new JComboBox(rotateString);
	
	public FrameSettings() 
	{
		super("환경 설정");
		setVisible(false);
		
		setSize(600, 400);
		setLocationByPlatform(true);
		setAlwaysOnTop(true);
		setResizable(false);
		setLayout(new BorderLayout());
		
		settingToggleRefreshButton();
		
		settingRefreshRateComboBox();
		
		settingRotateRateCombobox();
		
		settingShowProgressBarChk();
		
	}
	
	//새로고침 활성/ 비활성화 버튼
	private void settingToggleRefreshButton()
	{
		toggleRefresh.setSelected(Settings.isCurrentRefreshing);
		toggleRefresh.setForeground(Color.RED);
		toggleRefresh.setText("새로고침 동작중");
		toggleRefresh.setFont(new Font("맑은 고딕", Font.BOLD, 14));
		toggleRefresh.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(((JToggleButton)e.getSource()).isSelected())
				{
					((JToggleButton)e.getSource()).setForeground(Color.RED);
					((JToggleButton)e.getSource()).setText("새로고침 동작중");
					Settings.isCurrentRefreshing = true;
				}
				else
				{
					((JToggleButton)e.getSource()).setForeground(Color.BLACK);
					((JToggleButton)e.getSource()).setText("새로고침 중지");
					Settings.isCurrentRefreshing = false;
				}
				
			}
		});
		panelButtons.add(toggleRefresh);
	}
	
	//새로고침 주기 콤보박스
	private void settingRefreshRateComboBox()
	{
		//comboBoxRefreshRate.setSelectedItem(Settings.isCurrentRereshing);
		panelButtons.add(Box.createRigidArea(new Dimension(30, 0)));
		panelButtons.add(new JLabel("새로고침 주기 :"));
		panelButtons.add(comboBoxRefreshRate);
		
		comboBoxRefreshRate.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				
				int index = ((JComboBox)e.getSource()).getSelectedIndex();
				
				Settings.refreshTime = rateLong[index];
				
			}
		});
	}
	
	//화면 전환 주기 콤보박스
	private void settingRotateRateCombobox()
	{
		comboBoxRotateRate.setSelectedIndex(4);
		panelButtons.add(Box.createRigidArea(new Dimension(30, 0)));
		panelButtons.add(new JLabel("웹 페이지 전환 주기 :"));
		panelButtons.add(comboBoxRotateRate);
		
		comboBoxRotateRate.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				
				int index = ((JComboBox)e.getSource()).getSelectedIndex();
				
				if(index == 0)
					Settings.isCurrentRotate = false;
				else
				{
					Settings.isCurrentRotate = true;
					Settings.rotateTime = rateLong[index];
				}

			}
		});
	}
	
	//프로그래스 바 표시 체크박스
	private void settingShowProgressBarChk()
	{
		panelButtons.add(Box.createRigidArea(new Dimension(30, 0)));
		chkShowProgressbar.setSelected(Settings.isShowProgressBar);
		chkShowProgressbar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(((JCheckBox)e.getSource()).isSelected())
					Settings.isShowProgressBar = true;
				else
					Settings.isShowProgressBar = false;
			}
		});
		
		panelButtons.add(chkShowProgressbar);
		
		add(panelButtons, BorderLayout.NORTH);
	}
	
	
	
	
	
	
	
	
	
	
	

	
	
}
