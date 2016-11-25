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
	
	JToggleButton toggleRefresh = new JToggleButton("���ΰ�ħ ������");
	JCheckBox chkShowProgressbar = new JCheckBox("����� ǥ��");
	
	
	String rateString[] = {"5��", "10��", "20��", "30��", "60��", "2��", "3��", "5��", "10��", "20��", "30��", "1�ð�", "2�ð�", "4�ð�", "8�ð�"};
	long rateLong[] = {5*S, 10*S, 20*S, 30*S, M, 2*M, 3*M, 5*M, 10*M, 20*M, 30*M, H, 2*H, 4*H, 8*H};
	JComboBox comboBoxRefreshRate = new JComboBox(rateString);
	
	String rotateString[] = {"����", "10��", "20��", "30��", "1��", "1�� 30��", "2��", "3��", "5��", "10��", "20��", "30��", "40��", "1�ð�", "2�ð�", "4�ð�", "8�ð�", "12�ð�"};
	long ratateLong[] = {0, 10*S, 20*S, 30*S, M, M + 30*S, 2*M, 3*M, 5*M, 10*M, 20*M, 30*M, 40*M, H, 2*H, 4*H, 8*H, 12*H};
	JComboBox comboBoxRotateRate = new JComboBox(rotateString);
	
	public FrameSettings() 
	{
		super("ȯ�� ����");
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
	
	//���ΰ�ħ Ȱ��/ ��Ȱ��ȭ ��ư
	private void settingToggleRefreshButton()
	{
		toggleRefresh.setSelected(Settings.isCurrentRefreshing);
		toggleRefresh.setForeground(Color.RED);
		toggleRefresh.setText("���ΰ�ħ ������");
		toggleRefresh.setFont(new Font("���� ���", Font.BOLD, 14));
		toggleRefresh.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(((JToggleButton)e.getSource()).isSelected())
				{
					((JToggleButton)e.getSource()).setForeground(Color.RED);
					((JToggleButton)e.getSource()).setText("���ΰ�ħ ������");
					Settings.isCurrentRefreshing = true;
				}
				else
				{
					((JToggleButton)e.getSource()).setForeground(Color.BLACK);
					((JToggleButton)e.getSource()).setText("���ΰ�ħ ����");
					Settings.isCurrentRefreshing = false;
				}
				
			}
		});
		panelButtons.add(toggleRefresh);
	}
	
	//���ΰ�ħ �ֱ� �޺��ڽ�
	private void settingRefreshRateComboBox()
	{
		//comboBoxRefreshRate.setSelectedItem(Settings.isCurrentRereshing);
		panelButtons.add(Box.createRigidArea(new Dimension(30, 0)));
		panelButtons.add(new JLabel("���ΰ�ħ �ֱ� :"));
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
	
	//ȭ�� ��ȯ �ֱ� �޺��ڽ�
	private void settingRotateRateCombobox()
	{
		comboBoxRotateRate.setSelectedIndex(4);
		panelButtons.add(Box.createRigidArea(new Dimension(30, 0)));
		panelButtons.add(new JLabel("�� ������ ��ȯ �ֱ� :"));
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
	
	//���α׷��� �� ǥ�� üũ�ڽ�
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
