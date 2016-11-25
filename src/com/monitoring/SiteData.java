package com.monitoring;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;

public class SiteData implements ActionListener
{

	WebPanel webPanel = null;
	
	public String siteName = null;
	public String siteAddress = null;
	public int zoom = 0;
	
	public SiteData()
	{
		siteName = "siteName";
		siteAddress = "siteAddress";
		zoom = 0;
		
		webPanel = new WebPanel("about:page", "", 0);
	}
	
	public SiteData(String siteName, String siteAddress, int zoom)
	{
		this.siteName = siteName;
		this.siteAddress = siteAddress;
		this.zoom = zoom;
		
		webPanel = new WebPanel(siteName, siteAddress, zoom);
	}
	
	public JPanel getPanel()
	{
		return webPanel.getPanel();
	}
	
	public void reData(String siteName, String siteAddress, int zoom)
	{
		if(webPanel != null)
		{
			this.siteName = siteName;
			this.siteAddress = siteAddress;
			this.zoom = zoom;
			
			webPanel.reloadData(siteName, siteAddress, zoom);
		}
	}
	
	public void removeThis()
	{
		webPanel.removeThis();
		webPanel.timer.stop();
		webPanel.interrupt();
		webPanel = null;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) 
	{
	
		
		
	}
}
	
	