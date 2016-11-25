package com.monitoring.Settings;

import java.util.StringTokenizer;

import javax.imageio.spi.RegisterableService;

import com.monitoring.SiteData;
import com.monitoring.Utility.Registry.Regedit;

public class LoadData 
{

	
	public static boolean loadData()
	{
		
		//���� ������Ʈ�� ���� ���� ��� �ű� �� ����
		if ( !Regedit.isContains("refreshTime") )
			Regedit.addRegistry("refreshTime", "100000");
		else
		{
			String keyData = Regedit.getRegistry("refreshTime");
			long ln = Long.valueOf(keyData);
			
			Settings.refreshTime = ln;
			
		}
		
		if (!Regedit.isContains("rotateTime"))
			Regedit.addRegistry("rotateTime", "60000");
		else
		{
			String keyData = Regedit.getRegistry("rotateTime");
			long ln = Long.valueOf(keyData);
			
			Settings.rotateTime = ln;
			
		}
		
		//����Ʈ ����Ʈ�� ���� ��� false ��ȯ
		if(!Regedit.isContains("siteList"))
		{
			return false;
		}
		else
		{
			String keyData = Regedit.getRegistry("siteList");
			
			StringTokenizer tokenData = new StringTokenizer(keyData, "@@");
			while(tokenData.hasMoreTokens())
			{
				if(tokenData.hasMoreTokens())
				{
					String siteData = tokenData.nextToken();
					StringTokenizer siteTokenData = new StringTokenizer(siteData, "||");
					
					
					String siteName = siteTokenData.nextToken();
					String siteAddress = siteTokenData.nextToken();
					int zoom = Integer.valueOf(siteTokenData.nextToken());
					
					Settings.vec_Sites.add(new SiteData(siteName, siteAddress, zoom));
					
				}
			}
			
		}

		
		
		
		return true;
			
		
	}
	
	
	
	
	
}
