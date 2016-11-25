package com.monitoring.MonitoringTool;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import com.monitoring.SiteData;
import com.monitoring.WebFrame;
import com.monitoring.Settings.LoadData;
import com.monitoring.Settings.Settings;

public class WebMonitoringTool {

	public static void main(String[] args) 
	{
   
		try
		{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		}catch (Exception e)
		{
			System.exit(0);
		}
		
		SwingUtilities.invokeLater(new WebFrame());
		

    }

}
	