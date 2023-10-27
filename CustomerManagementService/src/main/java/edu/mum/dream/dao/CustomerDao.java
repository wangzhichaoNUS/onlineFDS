package edu.mum.dream.dao;

import edu.mum.dream.domain.Customer;


public interface CustomerDao extends GenericDao<Customer> {
    public boolean checkCusByEmailnPhon(String email,String phonenum);

    public void savecuss(String email,String phonenum,String userAvatar, String time);
}
