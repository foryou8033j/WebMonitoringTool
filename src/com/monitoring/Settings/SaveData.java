package com.monitoring.Settings;

import com.monitoring.SiteData;
import com.monitoring.Utility.Registry.Regedit;

public class SaveData 
{

	
	public static void saveData()
	{
		
		Regedit.addRegistry("refreshTime", String.valueOf(Settings.refreshTime));
		
		Regedit.addRegistry("rotateTime", String.valueOf(Settings.rotateTime));
		
		if( Settings.vec_Sites.size() < 1)
			return;
		
		String siteData = getStringData(0);
		
		int size = Settings.vec_Sites.size();
		
		for(int i=1; i<size; i++)
		{
			siteData = siteData.concat("@@");
			siteData = siteData.concat(getStringData(i));
		}
		
		Regedit.addRegistry("siteList", siteData);
		
		
	}
	
	private static String getStringData(int index)
	{
		SiteData tmp = Settings.vec_Sites.get(index);
		
		String siteName = tmp.siteName;
		String siteAddress = tmp.siteAddress;
		int zoom = tmp.zoom;
		
		String piece = new String(siteName + "|" +  siteAddress + "|" + String.valueOf(zoom));
		
		return piece;
	}
	
}
