package edu.mum.dream.rest;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import edu.mum.dream.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import edu.mum.dream.service.RestaurantService;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/api")
public class RestaurantRestContoller {
	String menuUrl="http://Customer-service/addresses/";

	private RestaurantService restaurantService;

	private RestTemplate restTemplate;
	
	@Autowired
	public RestaurantRestContoller(RestaurantService therestaurantService) {
		restaurantService = therestaurantService;
	}

	@GetMapping("/restaurants")
	public List<Restaurant> findall() {
		return restaurantService.findAll();
	}

	@GetMapping("/restaurants/{restaurantId}")
	public Restaurant getResturant(@PathVariable int restaurantId) {
		return restaurantService.findOne((long) restaurantId);
	}

	@RequestMapping(value = "/restaurant/{restaurantId}/menu")
    public List<FoodMenu> showMenuByRestaurantId(@PathVariable int restaurantId) {
        return restaurantService.showFoodMenu((long)restaurantId);
//		return restaurantService.showFoodMenu((long)1);
    }

	@RequestMapping(value = "/restaurants", method = RequestMethod.POST)
	public Restaurant upload(@RequestBody Restaurant restaurant) {
		restaurant.setId(null);
		restaurantService.save(restaurant);
		return restaurant;
	}

	@GetMapping("/search/{context}")
	public List<Restaurant> getOrderByUserID(@PathVariable String context) throws SQLException {
		List<Restaurant> restaurants = new ArrayList<>();
		String url = "jdbc:mysql://sg-cdb-2hz2zv9f.sql.tencentcdb.com:63956/dreamrestaurant?rewriteBatchedStatements=true&useUnicode=true&serverTimezone=Asia/Shanghai&autoReconnect=true&zeroDateTimeBehavior=convertToNull&allowMultiQueries=true&useSSL=true";
		String username = "root";
		String password = "Abc20010726";
		Connection connection = DriverManager.getConnection(url, username, password);
		String sql = "SELECT * FROM restaurant WHERE restaurant_name LIKE '%"+context+ "%' ";
		PreparedStatement preparedStatement = connection.prepareStatement(sql);


		try (ResultSet resultSet = preparedStatement.executeQuery()) {
			while (resultSet.next()) {
				// 处理查询结果
				Restaurant temp = new Restaurant();
				Integer id = resultSet.getInt("address_id");
//				menuUrl += id;
//				HttpHeaders headers = new HttpHeaders();
//				HttpEntity<Integer> requestEntity = new HttpEntity<>(id,headers);
//				ResponseEntity<Address> callResuit = restTemplate.exchange(menuUrl, HttpMethod.GET ,requestEntity , Address.class);
//				temp.setAddress(callResuit.getBody());
				temp.setBusiness(resultSet.getString("business"));
				temp.setCoverImg(resultSet.getString("cover_img"));
				temp.setId(resultSet.getLong("restaurant_id"));
				temp.setRestaurantName(resultSet.getString("restaurant_name"));
				temp.setRestaurantCatalog(resultSet.getString("restaurant_catalog"));
//				temp.setFoodMenu();
				temp.setPhoneNumber(resultSet.getString("phone_number"));
				restaurants.add(temp);
			}
		}
		preparedStatement.close();
		connection.close();
		return restaurants;
	}
}
