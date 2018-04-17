package com.xiaoM.Common.Utils;

import java.util.List;

import com.xiaoM.Report.utils.TestListener;

public class UseDevices {
	static List<String> devices;
	static String device;
	
	public static synchronized String Device (){
		devices = TestListener.deviceLists;
		device = devices.get(0);
		devices.remove(0);
		return device;	
	}
}
