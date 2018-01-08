package com.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;


import com.backend.DAO.UserDAO;
import com.backend.DAO.CategoryDAOImpl;
import com.backend.DAO.ProductDAOImpl;
import com.backend.model.User;


@Controller
public class IndexController 
{
	@Autowired
  UserDAO userDAO;
	@Autowired
	ProductDAOImpl  productDAOImpl;
	@Autowired
	CategoryDAOImpl categoryDAOImpl;
	
	
	@RequestMapping("/")
	public String index()
	{
	
		return "index";
	}
	@RequestMapping(value="/goToRegister",method=RequestMethod.GET)
	public ModelAndView goTORegister()
	{
		ModelAndView mv= new ModelAndView();
		mv.addObject("user",new User());

		mv.setViewName("Register");
		return mv;
		
		
	}
	@RequestMapping(value="/saveUser",method=RequestMethod.POST)
	public ModelAndView saveUserData(@ModelAttribute("user")User user,BindingResult result)
	{
		ModelAndView mv=new ModelAndView();
		if(result.hasErrors())
		{
			mv.setViewName("Register");
			return mv;
		}
		else{
			user.setRole("ROLE_USER");
			userDAO.saveUser(user);
			mv.setViewName("index");
			}
		return mv;
	}
	
	@RequestMapping(value="/productCustList")
	public ModelAndView getCustTable(@RequestParam("cid")int cid)
	{
		ModelAndView mv=new ModelAndView();
		mv.addObject("prodList",productDAOImpl.getProdByCatId(cid));
		mv.setViewName("productCustList");
		return mv;
	}
	@ModelAttribute
	public void getData(Model m)
	{
		m.addAttribute("catList",categoryDAOImpl.retrieve());
	}
	@RequestMapping(value="/goTologin",method=RequestMethod.GET)
	public ModelAndView goTOLogin()
	{
		ModelAndView mv= new ModelAndView();

		mv.setViewName("Login");
		return mv;
		
		
	}
	@RequestMapping("/userLogged")
	public String underlogged()
	{
		return "redirect:/index";
	}
	
	@RequestMapping("/error")
	public String underError()
	{
		return "error";
	}
	
	@RequestMapping("/reLogin")
	public String relogin()
	{
		return "redirect:/goToLogin";
	}

	

}