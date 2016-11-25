package com.monitoring.Settings;


/*
 * static으로 관리한다, 시발 메모리 부족해
 */
public class Settings 
{

	public static boolean isCurrentRefreshing = true;
	public static long refreshTime = 10000L;
	
	public static boolean isCurrentRotate = true;
	public static long rotateTime = 60000L;
	public static boolean isShowProgressBar = true;
	
	
	
}
