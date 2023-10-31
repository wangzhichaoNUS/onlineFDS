package edu.mum.dream.rest;

import java.util.List;

import javax.validation.Valid;

import edu.mum.dream.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.mum.dream.dto.CustomerDTO;
import edu.mum.dream.service.AddressService;
import edu.mum.dream.service.CustomerService;
import edu.mum.dream.service.FoodMenuService;

@RestController
@RequestMapping("/customers")
public class CustomerController {

	@Autowired
	CustomerService customerService;

	@Autowired
	AddressService addressService;

	@Autowired
	FoodMenuService creditService;

//	@PostMapping("/signup")
//	public void save(@Valid @RequestBody Customer customer) {
//		customerService.save(customer);
//	}
//	@GetMapping("/signup/{userName}/{userPass}/{userAvatar}")
//	public ResponseEntity<CustomResponse> savecus(@PathVariable String userName, @PathVariable String userPass, @PathVariable String userAvatar) {
//		String time = "1";
//		customerService.savecus(userName,userPass,userAvatar,time);
//		CustomResponse response = new CustomResponse("成功", 1);
//		return ResponseEntity.ok(response);
//	}

	@PostMapping("/signup")
	public ResponseEntity<CustomResponse> savecus(@Valid @RequestBody User user) {
		String userName,userPass,userAvatar;
		String time = "1";
		userName = user.getUserName();
		userPass = user.getUserPass();
		userAvatar = user.getUserAvatar();
		customerService.savecus(userName,userPass,userAvatar,time);
		CustomResponse response = new CustomResponse("成功", 1);
		return ResponseEntity.ok(response);
	}

//	@PostMapping("/signup")
//	public void memberRegistration(@Valid @RequestBody CustomerDTO customerData) {
//		Address addressObject = customerData.getAddress();
//		//addressService.save(addressObject);
//
//		Customer customerObject = customerData.getCustomer();
//		customerObject.setAddress(addressObject);
//		//customerService.save(customerData.getCustomer());
//
//		CreditCard creditCardObject = customerData.getCreditCard();
//		creditCardObject.setCardHolder(customerObject);
//		creditService.save(creditCardObject);
//	}
	@GetMapping(value = "/check-card/{customerId}")
    public CreditCard findCreditCard(@PathVariable long customerId) {
        return creditService.findByCardHolder(customerId);
    }

//	@GetMapping(value = "/signin/{email}/{phonenum}")
//	public boolean checkone(@PathVariable String email,@PathVariable String phonenum) {
//		return customerService.checkCusByEmailnPhone(email,phonenum);
//	}

	@PostMapping(value = "/signin")
	public ResponseEntity<CustomResponse> checkone(@Valid @RequestBody User user) {
		if(customerService.checkCusByEmailnPhone(user.getUserName(),user.getUserPass()))
		{
			CustomResponse response = new CustomResponse("成功", 1);
			response.setUser(customerService.findOneUser(user.getUserName(),user.getUserPass()));
			response.setAccess_token("111");
			return ResponseEntity.ok(response);
		}else {
			CustomResponse response = new CustomResponse("失败", 0);
			return ResponseEntity.ok(response);
		}
	}

//	@PutMapping("/update")
//	public Customer update(@RequestBody Customer customer) {
//		return customerService.update(customer);
//	}

	@PutMapping("/update")
	public void update(@Valid @RequestBody User user) {
		Customer customer = new Customer();
		customer.setId(Long.parseLong(user.getId()));
		Address ad = new Address();
		ad.setStreet(user.getUserAddress());
//		customer.getAddress().setStreet(user.getUserAddress());
////		customer.setAddress(customer.getAddress());
		customer.setEmail(user.getUserName());
		customer.setFirstName(user.getUserPhone());
		customer.setPhoneNumber(user.getUserPass());
		customerService.update(customer);
	}

	@GetMapping("/getall")
	public List<Customer> findAll() {
		return customerService.findAll();
	}

	@GetMapping("/{id}")
	public Customer findOne(@PathVariable Long id) {
		return customerService.findOne(id);
	}

}
