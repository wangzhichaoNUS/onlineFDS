package edu.mum.dream.dao;



import java.util.List;

import edu.mum.dream.domain.Order;

public interface OrderDao extends GenericDao<Order> {

	public List<Order> findOneByUserId(Long id);
	public List<Order> findAllSubSelect();

	public List<Order> findafByUserId(Long userId);

	public List<Order> findwcByUserId(Long userId);

	public List<Order> findwpByUserId(Long userId);
}
