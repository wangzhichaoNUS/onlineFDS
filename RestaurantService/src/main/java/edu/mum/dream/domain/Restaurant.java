package edu.mum.dream.domain;


import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "Restaurant")
public class Restaurant {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "RESTAURANT_ID")
	private Long Id;
	
	@NotNull
	@Size(min = 4, max = 32, message = "{Size.restaurantName}")
	private String restaurantName;

	@NotNull
	@Size(min = 4, max = 32, message = "{Size.restaurantName}")
	private String phoneNumber;

	@NotNull
	@Size(min = 4, max = 32, message = "{Size.restaurantName}")
	private String business;

	@NotNull
	private String coverImg ;
	
	@NotNull
	@Size(min = 4, max = 32, message = "{Size.restaurantCatalog}")
	private String restaurantCatalog;
	
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@org.hibernate.annotations.Fetch(org.hibernate.annotations.FetchMode.SUBSELECT) 
	@JoinColumn(name = "restaurant_id")
	private List<FoodMenu> foodMenu ;

	
	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "ADDRESS_ID")
	Address address;

	public Restaurant() {
		
	}

	public Long getId() {
		return Id;
	}

	public void setId(Long id) {
		Id = id;
	}

	public String getRestaurantName() {
		return restaurantName;
	}

	public void setRestaurantName(String restaurantName) {
		this.restaurantName = restaurantName;
	}

	public String getRestaurantCatalog() {
		return restaurantCatalog;
	}

	public void setRestaurantCatalog(String restaurantCatalog) {
		this.restaurantCatalog = restaurantCatalog;
	}

	public List<FoodMenu> getFoodMenu() {
		return foodMenu;
	}

	public void setFoodMenu(List<FoodMenu> foodMenu) {
		this.foodMenu = foodMenu;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getBusiness() {
		return business;
	}

	public void setBusiness(String business) {
		this.business = business;
	}

	public String getCoverImg() {
		return coverImg;
	}

	public void setCoverImg(String coverImg) {
		this.coverImg = coverImg;
	}
}
