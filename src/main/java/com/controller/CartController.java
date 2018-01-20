package com.controller;

import java.security.Principal;



import javax.servlet.http.HttpSession;



import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestParam;

import org.springframework.web.servlet.ModelAndView;

import org.springframework.web.servlet.mvc.support.RedirectAttributes;


import com.backend.DAO.*;
import com.backend.model.*;


@Controller

public class CartController 

{


	@Autowired

	CartDAO cartDAO;

	

	@Autowired

	ProductDAO productDAO;

	

	@Autowired

	AddressDAO addressDAO;

	

	

	@RequestMapping(value="/prodDetails/{pid}")

	public ModelAndView prodDet(@PathVariable("pid")int pid)

	{

		ModelAndView mv= new ModelAndView();

		Product p=productDAO.findByPID(pid);

		mv.addObject("product",p);

		mv.setViewName("prodDetails");

		

		return mv;

		

	}

    @RequestMapping(value="/addToCart/{id}")

    public String addProductToCart(@PathVariable("id") int id, HttpSession session,Model model,RedirectAttributes attributes)

    {

    	Principal principal= SecurityContextHolder.getContext().getAuthentication();

    	String email = (String) session.getAttribute("email");

    	int q=1;

    	if (cartDAO.getitem(id, email) != null) {

			Cart item = cartDAO.getitem(id,email);

			

			item.setProductQuantity(item.getProductQuantity() + q);

			

			Product p = productDAO.findByPID(id);

			

			System.out.println(item);

			item.setProductPrice(p.getPrice());

			item.setSubTotal(item.getProductQuantity() *p.getPrice());

			cartDAO.saveProductToCart(item);

			attributes.addFlashAttribute("ExistingMessage",  p.getPname() +"is already exist");

	

			return "redirect:/";

		} else {

			Cart cart = new Cart();

			Product p = productDAO.findByPID(id);

			cart.setProductid(p.getPid());

			cart.setProductName(p.getPname());

			cart.setEmail(principal.getName());

			cart.setProductQuantity(q);

			cart.setStatus("C");

			cart.setSubTotal(q * p.getPrice());

			cart.setProductPrice(p.getPrice());

			cartDAO.saveProductToCart(cart);

			attributes.addFlashAttribute("SuccessMessage", "Item"+p.getPname()+" has been deleted Successfully");

			return "redirect:/";

		}

    	

    }









    @RequestMapping("viewcart")

	public String viewCart(Model model, HttpSession session) {

    	

	

		model.addAttribute("CartList", cartDAO.listCart());

		 if(cartDAO.cartsize((String) session.getAttribute("email"))!=0){

			

			model.addAttribute("CartPrice", cartDAO.CartPrice((String) session.getAttribute("email")));

		} else {

			model.addAttribute("EmptyCart", "true");

		}

		model.addAttribute("IfViewCartClicked", "true");

		return "CartPage";

	}







	@RequestMapping("editCart/{cartid}")

	public String editorder(@PathVariable("cartid") int cartid, @RequestParam("Quantity") int q, HttpSession session) 

	{

	

	

		Cart cart = cartDAO.editCartById(cartid);

		Product p = productDAO.findByPID(cart.getProductid());

		cart.setProductQuantity(q);

	

		cart.setSubTotal(q * p.getPrice());

		cartDAO.saveProductToCart(cart);

		session.setAttribute("cartsize", cartDAO.cartsize((String) session.getAttribute("email")));

		return "redirect:/viewcart";

	}

    

    

    

    

@RequestMapping(value="removeCart/{id}")

public String deleteorder(@PathVariable("id") int id, HttpSession session) {

	cartDAO.removeCartById(id);

	session.setAttribute("cartsize", cartDAO.cartsize((String) session.getAttribute("email")));

	return "redirect:/viewcart";

}





@RequestMapping("continue_shopping")

public String continueshopping()

{

return "redirect:/";	









}


	}

