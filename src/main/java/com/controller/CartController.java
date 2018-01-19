package com.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.backend.DAO.AddressDAO;
import com.backend.DAO.CartDAO;
import com.backend.DAO.ProductDAO;
import com.backend.model.Cart;
import com.backend.model.Product;



public class CartController {

	
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
    @RequestMapping(value="/AddToCart/{id}")
    public String addProductToCart(@PathVariable("id") int id, HttpSession session,Model model,RedirectAttributes attributes)
    {
    	int userId = (Integer) session.getAttribute("userid");
    	int q=1;
    	if (cartDAO.getitem(id, userId) != null) {
			Cart item = cartDAO.getitem(id, userId);
			
			item.setProductQuantity(item.getProductQuantity() + q);
			
			Product p = productDAO.findByPID(id);
			
			System.out.println(item);
			item.setProductPrice(p.getPrice());
			item.setSubTotal(item.getProductQuantity() *p.getPrice());
			cartDAO.saveProductToCart(item);
			attributes.addFlashAttribute("ExistingMessage",  p.getPname() +"is already exist");
	
			return "redirect:/";
		} else {
			Cart item = new Cart();
			Product p = productDAO.findByPID(id);
			item.setProductid(p.getPid());
			item.setProductName(p.getPname());
			item.setUserId(userId);
			item.setProductQuantity(q);
			item.setStatus("C");
			item.setSubTotal(q * p.getPrice());
			item.setProductPrice(p.getPrice());
			cartDAO.saveProductToCart(item);
			attributes.addFlashAttribute("SuccessMessage", "Item"+p.getPname()+" has been deleted Successfully");
			return "redirect:/";
		}
    	
    }




    @RequestMapping("viewcart")
	public String viewCart(Model model, HttpSession session) {
    	
	
		model.addAttribute("CartList", cartDAO.listCart());
		 if(cartDAO.cartsize((Integer) session.getAttribute("userid"))!=0){
			
			model.addAttribute("CartPrice", cartDAO.CartPrice((Integer) session.getAttribute("userid")));
		} else {
			model.addAttribute("EmptyCart", "true");
		}
		model.addAttribute("IfViewCartClicked", "true");
		return "CartPage";
	}



	@RequestMapping("editCart/{cartid}")
	public String editorder(@PathVariable("cartid") int cartid, @RequestParam("quantity") int q, HttpSession session) 
	{
	
	
		Cart cart = cartDAO.editCartById(cartid);
		Product p = productDAO.findByPID(cart.getProductid());
		cart.setProductQuantity(q);
	
		cart.setSubTotal(q * p.getPrice());
		cartDAO.saveProductToCart(cart);
		session.setAttribute("cartsize", cartDAO.cartsize((Integer) session.getAttribute("userid")));
		return "redirect:/viewcart";
	}
    
    
    
    
@RequestMapping(value="removeCart/{id}")
public String deleteorder(@PathVariable("id") int id, HttpSession session) {
	cartDAO.removeCartById(id);
	session.setAttribute("cartsize", cartDAO.cartsize((Integer) session.getAttribute("userid")));
	return "redirect:/viewcart";
}


@RequestMapping("continue_shopping")
public String continueshopping()
{
return "redirect:/";	




}

}
