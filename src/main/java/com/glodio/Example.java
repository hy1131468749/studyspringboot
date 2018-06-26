package com.glodio;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
@MapperScan(value="com.glodio.dao")
@ServletComponentScan(value="com.glodio.init")
public class Example {
	@RequestMapping("/")
	String home() {
		return "Hello World!";
	}
	
	@RequestMapping("/a")
	TestDate home2 () {
		//Map<String,Object> map = new HashMap<>();
		//map.put("date", new Date());
		TestDate t = new TestDate();
		t.setName("1aa");
		t.setDate(new Date());
		return t;
	}
	
	@RequestMapping("/testTime1")
	Map<String,Object> home3 (String time) {
		Map<String,Object> map = new HashMap<>();
		map.put("time", time);
		return map;
	}
	@RequestMapping("/testTime2")
	Map<String,Object> home4 (Date time) {
		Map<String,Object> map = new HashMap<>();
		map.put("time", time);
		return map;
	}
	
	public static void main(String[] args) {
		
		SpringApplication.run(Example.class, args);
	}

	class TestDate  {
		private String name;
		private Date date;
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public Date getDate() {
			return date;
		}
		public void setDate(Date date) {
			this.date = date;
		}
		
		
	}
	
}
