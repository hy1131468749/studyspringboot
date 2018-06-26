package com.glodio.controller;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/system/logs")
public class LogFileController {

	@RequestMapping("/getLog")
	@ResponseBody
	public void updateDeviceData(HttpServletResponse response) throws Exception {
		String strResult = "";
		PrintWriter pWriter = response.getWriter();
		
		String strLogPath = this.getClass().getResource("/").getPath();
		int position = strLogPath.indexOf("WEB-INF");
		strLogPath = strLogPath.substring(0, position);
		strLogPath = strLogPath+"logs/dev.log";
		//System.out.println(position);
		//System.out.println(strLogPath);
		
		File file = new File(strLogPath);
		BufferedReader buffReader = null;
		if(!file.exists()) {
			strResult = "Not Current date data!";
		}
		else {
			buffReader = new BufferedReader(new FileReader(file));
			String strLine = null;
			while((strLine = buffReader.readLine()) != null) {
				//System.out.println(strLine);
				strResult += strLine;
				strResult += "\n";
			}
		}

		pWriter.write(strResult);
	}
}
