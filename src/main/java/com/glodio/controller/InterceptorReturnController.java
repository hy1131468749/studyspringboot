package com.glodio.controller;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.glodio.bean.User;

@Controller
@RequestMapping("/system")
public class InterceptorReturnController {
	
	@RequestMapping(produces = "text/html;charset=UTF-8" ,value = "/SessionTimeOut",method = RequestMethod.GET)
	public @ResponseBody void SessionTimeOut(HttpServletRequest request,HttpServletResponse response) throws Exception {
	    StringBuffer sbHtml = new StringBuffer();
	    sbHtml.append("<!doctype html><html><head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">");
	    //sbHtml.append("<title>安监系统</title>");
	    sbHtml.append("<link href='SPGD/../../public/themes/insdep/easyui.css' rel='stylesheet' type='text/css'>");
	    sbHtml.append("<link href='../public/themes/insdep/insdep_theme_default.css' rel='stylesheet' type='text/css'>");
	    sbHtml.append("<script type='text/javascript' src='SPGD/../../public/jquery.min.js'></script>");
		sbHtml.append("<script type='text/javascript' src='SPGD/../../public/jquery.easyui.min.js'></script>");


		sbHtml.append("<script type='text/javascript' src='SPGD/../../public/easyui-lang-zh_CN.js'></script>");

	    
	    sbHtml.append("<script language='javascript'>");
	    sbHtml.append("$(document).ready(function() {");
	    sbHtml.append("top.$.messager.alert('系统提示', '会话超时，请重新登录!', 'warning', function() {");
	    sbHtml.append("top.location.href=");
	    sbHtml.append("'../login.html'");
	    sbHtml.append("});");
	    sbHtml.append("});");
	    sbHtml.append("</script>");
	    sbHtml.append("<body ></body></html>");

    
	    response.setContentType("text/html;charset=UTF-8");// 解决中文乱码
		PrintWriter writer = response.getWriter();
		
		if(request.getSession().getAttribute("user") == null) {
			try {

				writer.write(sbHtml.toString());
				writer.flush();
				writer.close();
				} catch (Exception e) {
				e.printStackTrace();
				}
		}
		
		User user = (User)request.getSession().getAttribute("user");
		if(user != null) {
			try {
				writer.write(sbHtml.toString());
				writer.flush();
				writer.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		
		} else {
			try {
				writer.write(sbHtml.toString());
				writer.flush();
				writer.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	@RequestMapping("/ReturnDevicesChange")
	public @ResponseBody String ReturnDevicesChange(HttpServletRequest request) throws Exception {
		return "devices_change";
	}
	
	@RequestMapping("/ReturnColorsChange")
	public @ResponseBody String ReturnColorsChange(HttpServletRequest request) throws Exception {
		return "colors_change";
	}
}
