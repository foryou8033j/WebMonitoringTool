package com.monitoring;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

import com.monitoring.Settings.Settings;

public class WebFrame extends JFrame implements Runnable, ActionListener
{
	
	int MAX_SITES = 1;
	int lastStartIndex = 1;
	
	JButton buttonSetting = new JButton("Settings");
	JPanel panel = new JPanel();
	
	FrameSettings frameSettings = null;
	
	Vector<WebPanel> webPanel;
	
	Timer timer = new Timer(100, this);
	
	long curTime = System.currentTimeMillis();
	long compTime = System.currentTimeMillis();
	
	public WebFrame()
	{
		super("정보보호 통합 모니터링 도구");
		
		frameSettings = new FrameSettings();
		webPanel = new Vector<WebPanel>();
		
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
	public void run() 
	{
		
		setVisible(true);
		
		webPanel.add(new WebPanel("해군복지포탈", "http://welfare.navy.mil.kr/"));
		webPanel.add(new WebPanel("해군사관학교", "http://www.navy.ac.kr/"));
		webPanel.add(new WebPanel("해군교육사령부", "http://www.edunavy.mil.kr/"));
		webPanel.add(new WebPanel("해병대사령부", "http://www.rokmc.mil.kr/"));
		webPanel.add(new WebPanel("해군 모바일", "http://m.navy.mil.kr/main.jsp"));
		
		webPanel.add(new WebPanel("대한민국 해군", "http://www.navy.mil.kr/"));
		webPanel.add(new WebPanel("해군 군수사령부", "http://www.navylogicom.mil.kr/"));
		webPanel.add(new WebPanel("제주민군복합항", "http://jejunbase.navy.mil.kr/"));
		webPanel.add(new WebPanel("해병대 홈페이지", "http://m.rokmc.mil.kr/"));
		webPanel.add(new WebPanel("해군 모집", "http://www.navy.mil.kr/enlist/main.jsp"));
		
		for(WebPanel wpanel:webPanel)
		{
			if(wpanel == null) break;
			try
			{
				panel.add(wpanel.getPanel());
			}catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		
		timer.start();
		
	}
	
	
	private void rotatePanel()
	{
		

		curTime = System.currentTimeMillis();
		if((curTime - compTime) > Settings.rotateTime)
		{
			System.out.println("rotate");
			
			panel.removeAll();
			
			int lastIndex = webPanel.indexOf(webPanel.lastElement());
			
			for(int i=lastStartIndex; i<lastIndex+2; i++)
			{
				if(i == lastIndex+1)
					i = 0;
				
				panel.add(webPanel.elementAt(i).getPanel());
				System.out.println("panel " + i  + "added!");
				
				if(i == lastStartIndex - 1)
					break;
				
			}
			
			System.out.println("");
			
			panel.revalidate();
			revalidate();
			
			compTime = System.currentTimeMillis();
			lastStartIndex++;
			if(lastStartIndex == lastIndex+1)
				lastStartIndex = 1;
		}
		
		
	}

	@Override
	public void actionPerformed(ActionEvent e) 
	{
		if(Settings.isCurrentRotate && Settings.isCurrentRefreshing)
			rotatePanel();
		
		if(e.getSource() == buttonSetting)
		{
			if(!frameSettings.isVisible())
				frameSettings.setVisible(true);
			else
				frameSettings.setVisible(false);
				
		}
		
	}
	
	
	

}


