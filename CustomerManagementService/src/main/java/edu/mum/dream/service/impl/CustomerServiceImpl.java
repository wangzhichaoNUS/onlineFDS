package edu.mum.dream.service.impl;

import java.util.List;

import edu.mum.dream.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import edu.mum.dream.dao.CustomerDao;
import edu.mum.dream.domain.CreditCard;
import edu.mum.dream.domain.Customer;
import edu.mum.dream.service.CustomerService;

@Service
@Transactional 
public class CustomerServiceImpl implements CustomerService {
	@Autowired
	CustomerDao customerDao;

	@Override
	public void save(Customer customer) {
		customerDao.save(customer);
	}

	@Override
	public Customer update(Customer customer) {
		return customerDao.update(customer);
	}

	@Override
	public List<Customer> findAll() {
		return (List<Customer>) customerDao.findAll();
	}

	@Override
	public Customer findOne(Long id) {
		return customerDao.findOne(id);
	}


	@Override
	public void savecus(String email, String phonenum,String userAvatar, String time) {
		customerDao.savecuss(email,phonenum,userAvatar,time);
	}

	@Override
	public boolean checkCusByEmailnPhone(String email, String phonenum) {
		return customerDao.checkCusByEmailnPhon(email,phonenum);
	}

	public User findOneUser(String email, String phonenum) {
		return customerDao.findCusByEmailNPhone(email,phonenum);
	}

}


