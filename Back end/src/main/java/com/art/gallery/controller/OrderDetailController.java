package com.art.galley.controller;

import java.util.List;

import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.art.galley.entity.Order;
import com.art.galley.entity.OrderDetail;
import com.art.galley.service.OrderDetailService;
import com.art.galley.service.OrderService;

@RequestMapping("orders")
@Controller
public class OrderDetailController {
	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private OrderDetailService orderDetailService;
	
	@Autowired
	private OrderService orderService;
	
	@GetMapping("/my-orders")
	String showCustomerOrders(HttpServletRequest request, Order order, HttpSession session, Model model) {
		try {
			session = request.getSession(false);
			String customerEmail = (String) session.getAttribute("email");
			if (customerEmail == null) {
				return "redirect:/home";
			}
			Set<Long> orderIds = orderService.getOrderIdByEmail(customerEmail);
			List<OrderDetail> ordersList = orderDetailService.getAllOrdersOrderId(orderIds, customerEmail);
			log.info("orderIds size = "+orderIds.size());
			log.info("ordersList size = "+ordersList.size());
			model.addAttribute("orders", ordersList);
			return "my-orders";
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("error", "e");
			return "my-orders";
		}
	}

}
