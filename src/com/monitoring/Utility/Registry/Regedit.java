package com.monitoring.Utility.Registry;

import java.util.prefs.Preferences;

public class Regedit 
{

	static Preferences userRootPrefs = Preferences.userRoot();
	
	
	public static boolean isContains(String key)
	{
		return userRootPrefs.get(key, null) != null;
	}
	
	public static void addRegistry(String key, String value)
	{
		try
		{
			if(isContains(key))
				userRootPrefs.put(key, value);
			else
				userRootPrefs.put(key, value);
		}catch (Exception e)
		{
			
		}
	}
	
	public static String getRegistry(String key)
	{
		try
		{
			return userRootPrefs.get(key, "");
		}catch (Exception e)
		{
			
		}
		return null;
	}
	
	
}
