package edu.mum.dream.domain;

import java.io.Serializable;


public class FoodItem  implements Serializable{

	private static final long serialVersionUID = 1L;

	private Long Id;
	private Long foodMenuID;
	private String foodName;
	private double foodPrice;
	private int quantity;

	private int user_id;
	

	public Long getFoodMenuID() {
		return foodMenuID;
	}

	public void setFoodMenuID(Long foodMenuID) {
		this.foodMenuID = foodMenuID;
	}

	public Long getId() {
		return Id;
	}

	public void setId(Long id) {
		Id = id;
	}

	public String getFoodName() {
		return foodName;
	}

	public void setFoodName(String foodName) {
		this.foodName = foodName;
	}


	public double getFoodPrice() {
		return foodPrice;
	}

	public void setFoodPrice(double foodPrice) {
		this.foodPrice = foodPrice;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}
}
