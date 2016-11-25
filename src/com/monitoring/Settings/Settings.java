package com.monitoring.Settings;

import java.util.Vector;

import com.monitoring.SiteData;

/*
 * static으로 관리한다, 시발 메모리 부족해
 */
public class Settings 
{

	public static boolean isCurrentRefreshing = true;
	public static long refreshTime = 10000;
	
	public static boolean isCurrentRotate = true;
	public static long rotateTime = 60000;
	
	public static boolean isShowProgressBar = true;
	
	public static Vector<SiteData> vec_Sites = new Vector<SiteData>();
	
	
}
