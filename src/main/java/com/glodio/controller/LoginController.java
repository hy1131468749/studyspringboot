package com.glodio.controller;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.glodio.bean.User;

import com.glodio.service.IUserService;

@Controller

public class LoginController {
	
    @Resource  
    private IUserService iUserService;
  
   
    
	@RequestMapping("/DoLogin") //url  
	public @ResponseBody Map<String, Object> DoLogin(HttpServletRequest request,User user){ 
		Map<String, Object> dataMap = new HashMap<>();
		
	    String info = loginUser(user);  

	    // shiro登录成功，返回字符串参数"SUCCESS"
	    if ("SUCCESS".equals(info)) {  
	       User queryBean = iUserService.queryBean(user.getUsername());
	       if(queryBean != null) {
	    	   queryBean.setLastTime(new Timestamp(System.currentTimeMillis()));
	    	   if(iUserService.updateBean(queryBean) == 1) {
	    	       dataMap.put("result", true);
	    	       //设置用户对象session
	    	       request.getSession().setAttribute("user", queryBean);
	    	   }
	    	   else {
				   dataMap.put("result", false);
	    	   }
	       }
	       else {
			   dataMap.put("result", false);
	       }
	    }
	    else{  
		   dataMap.put("result", false);
	    }

	    return dataMap;
	}
	  
	@RequestMapping("/logout")
	@ResponseBody
	public Map<String, Object> logout(HttpServletRequest request,HttpServletResponse response) throws IOException{  
		Map<String, Object> dataMap = new HashMap<>();
	    /*Subject subject = SecurityUtils.getSubject();  
	    if (subject != null) {  
	        try{  
	            subject.logout();
	            dataMap.put("result", true);
	        }catch(Exception e){ 
	        	e.printStackTrace();
	        	dataMap.put("result", false);
	        }  
	    }  */
	    
	    return dataMap;
	}  
	  
	private String loginUser(User user) {  
	        if (isRelogin(user)) return "SUCCESS";//如果已经登陆，无需重新登录  
	          
	        return shiroLogin(user);//调用shiro的登陆验证  
	}
	
	private String shiroLogin(User user) {  
	    //组装token，包含用户名称、密码等  
	   /* UsernamePasswordToken token = new UsernamePasswordToken(user.getUsername(), user.getPassword().toCharArray(), null);   
	    token.setRememberMe(true);  
	      
	    // shiro登陆验证  
	    try {  
	        SecurityUtils.getSubject().login(token);  
	    } catch (UnknownAccountException ex) {  
	        return "用户不存在或者密码错误！";  
	    } catch (IncorrectCredentialsException ex) {  
	        return "用户不存在或者密码错误！";  
	    } catch (AuthenticationException ex) {  
	        return ex.getMessage(); //自定义报错信息  
	    } catch (Exception ex) {  
	        ex.printStackTrace();  
	        return "内部错误，请重试！";  
	    }  */
	    return "SUCCESS";  
	}  
	  
	private boolean isRelogin(User user) {  
	    /*Subject us = SecurityUtils.getSubject();  
	        if (us.isAuthenticated()) {  
	           return true;//参数未改变，无需重新登录，默认为已经登录成功  
	        }  
	        */
	        return false;
	}

}
