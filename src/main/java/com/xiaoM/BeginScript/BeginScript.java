package com.xiaoM.BeginScript;

import java.io.IOException;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.xiaoM.Common.Utils.Run;
import com.xiaoM.Common.Utils.UseDevices;
import com.xiaoM.Report.utils.TestListener;


public class BeginScript{
	
	@DataProvider(parallel = true)
	public Object[][]Testcases() throws IOException{
		return TestListener.RunCase;
	}

	@Test(dataProvider = "Testcases")
	public  void run(String CaseType,String CaseName) throws Exception{
		String getDevice = UseDevices.Device();
		Run run = new Run();
		run.testCase(CaseType,CaseName,getDevice);
	}
}
