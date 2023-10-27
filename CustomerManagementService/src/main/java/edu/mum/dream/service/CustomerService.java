package edu.mum.dream.service;

import java.util.List;
import edu.mum.dream.domain.Customer;
import org.springframework.web.bind.annotation.PathVariable;

public interface CustomerService {
	public void save(Customer customer);
	public Customer update(Customer customer);
	public List<Customer> findAll();
	public Customer findOne(Long id);

	public void savecus(String email,String phonenum ,String userAvatar,String time);

	public boolean checkCusByEmailnPhone(String email,String phonenum);

}
