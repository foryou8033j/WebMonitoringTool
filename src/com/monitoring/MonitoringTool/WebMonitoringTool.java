package com.monitoring.MonitoringTool;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import com.monitoring.WebFrame;

public class WebMonitoringTool {

	public static void main(String[] args) 
	{
   
		try
		{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			
			SwingUtilities.invokeLater(new WebFrame());
		}catch (Exception e)
		{
			System.exit(0);
		}
		

    }

}
	