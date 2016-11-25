package com.monitoring;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;
import java.util.function.LongBinaryOperator;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

import com.monitoring.Settings.LoadData;
import com.monitoring.Settings.Settings;

public class WebFrame extends JFrame implements Runnable, ActionListener {

	int MAX_SITES = 1;
	int lastStartIndex = 1;

	JButton buttonSetting = new JButton("Settings");
	JPanel panel = new JPanel();

	FrameSettings frameSettings = null;

	Timer timer = new Timer(100, this);

	long curTime = System.currentTimeMillis();
	long compTime = System.currentTimeMillis();

	public WebFrame() {
		super("정보보호 통합 모니터링 도구");

		frameSettings = new FrameSettings(this);
		
		if(!LoadData.loadData())
			frameSettings.setVisible(true);

		setSize(600, 600);
		setLocationByPlatform(true);

		setLayout(new BorderLayout());
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		panel.setLayout(new GridLayout(2, 5));

		buttonSetting.addActionListener(this);

		add("Center", panel);
		add("South", buttonSetting);

	}

	@Override
	public void run() {

		setVisible(true);

		printPanelSite();

		timer.start();

	}

	public void printPanelSite() {
		
		int size = Settings.vec_Sites.size();
		panel.removeAll();
		
		if(size < 2)
		{

			if(size == 0)
			{
				panel.revalidate();
				revalidate();
				return;
			}
			panel.add(Settings.vec_Sites.get(0).getPanel());
			Settings.vec_Sites.get(0).webPanel.panel.revalidate();
			Settings.vec_Sites.get(0).webPanel.view.revalidate();
			panel.repaint();
			panel.revalidate();
			revalidate();
			return;
		}
		
		if(size == 2)
			lastStartIndex = 0;
		
		int i = lastStartIndex+1;
		
		while(true)
		{

			if(i == size)
			{
				i = 0;
				continue;
			}
			
			try
			{
				panel.add(Settings.vec_Sites.get(i).getPanel());
			}catch (Exception e)
			{
				
			}
			
			Settings.vec_Sites.get(i).webPanel.panel.revalidate();
			Settings.vec_Sites.get(i).webPanel.view.revalidate();
			
			
			
			System.out.print(i + " ");
			i++;
			
			if(i == lastStartIndex+1) 
				break;
		}
		
		System.out.println("");
		

		lastStartIndex++;
		
		if(lastStartIndex > size-1)
			lastStartIndex = 0;
		
		panel.revalidate();
		revalidate();
	}
	

	public void rotatePanel() {

		curTime = System.currentTimeMillis();
		if ((curTime - compTime) > Settings.rotateTime) 
		{
			
			printPanelSite();
			
			compTime = System.currentTimeMillis();
		}

	}
	
	
	public void removeWebPanel()
	{
		//panel.removeAll();
		panel.revalidate();
		panel.repaint();
		revalidate();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		if (Settings.isCurrentRotate && Settings.isCurrentRefreshing)
			rotatePanel();

		if (e.getSource() == buttonSetting) {
			if (!frameSettings.isVisible())
				frameSettings.setVisible(true);
			else
				frameSettings.setVisible(false);

		}

	}

}
