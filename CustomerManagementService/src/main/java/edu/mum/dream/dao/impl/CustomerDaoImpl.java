package edu.mum.dream.dao.impl;

import org.springframework.stereotype.Repository;

import edu.mum.dream.dao.CustomerDao;
import edu.mum.dream.domain.Customer;

@Repository
public class CustomerDaoImpl extends GenericDaoImpl<Customer> implements CustomerDao{

	public CustomerDaoImpl() {
		super.setDaoType(Customer.class);
	}

	@Override
	public boolean checkCusByEmailnPhon(String email, String phonenum) {
		String hql = "SELECT COUNT(c) FROM Customer c WHERE c.email = :email AND c.phoneNumber = :phonenum";
		Long count = (Long) entityManager.createQuery(hql)
				.setParameter("email", email)
				.setParameter("phonenum", phonenum)
				.getSingleResult();
		return count > 0;
	}

	@Override
	public void savecuss(String email, String phonenum,String userAvatar, String time) {
		Customer customer = new Customer();
		customer.setEmail(email);
		customer.setPhoneNumber(phonenum);
		customer.setLastName(userAvatar);
		entityManager.persist(customer);
	}
}
