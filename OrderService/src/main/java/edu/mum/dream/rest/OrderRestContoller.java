package edu.mum.dream.rest;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import edu.mum.dream.dao.FoodItemDao;
import edu.mum.dream.dao.impl.FoodItemDaoImpl;
import edu.mum.dream.domain.*;
import edu.mum.dream.service.FoodItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.orm.hibernate5.HibernateTemplate;

import edu.mum.dream.service.OrderService;
import edu.mum.dream.service.OrderServiceAmqp;

import javax.mail.Session;
import javax.transaction.Transaction;
import javax.validation.Valid;

@RestController
@RequestMapping("/api")
public class OrderRestContoller {

	private RestTemplate restTemplate;

	private OrderService orderService;

	private FoodItemService foodItemService;

	private FoodItemDaoImpl foodItemDao;

	private OrderServiceAmqp orderServiceAmpq;
	private String paymentManagmentUrl = "http://Payment-Microservice/payment/save";

	private String menuUrl= "http://Restaurant-Microservice/api/restaurants/foodmenu/";

	@Autowired
	public OrderRestContoller(RestTemplate restTemplate, OrderService orderService, OrderServiceAmqp orderServiceAmpq) {
		this.restTemplate = restTemplate;
		this.orderService = orderService;
		this.orderServiceAmpq = orderServiceAmpq;
	}

	@GetMapping("/restaurants")
	public Restaurant findallRestaurant() {
		Restaurant restaurant = restTemplate.getForObject("http://Restaurant-Microservice/api/restaurants/2/",
				Restaurant.class);
		return restaurant;
	}

	@RequestMapping(value = "/orders/create", method = RequestMethod.POST)
	public Order upload(@Valid @RequestBody Order order) {

		order.setId(null);

		orderService.save(order);

		/***
		 * check the validation of the payment by calling payment microservice send
		 * order information
		 *
		 ***/
		Payment payment = new Payment();
		payment.setId(0);
		payment.setOrderId(order.getId());
		payment.setTotalPrice(order.getTotalPrice());
		payment.setCustomerId(order.getUserName());
		Payment paymentreturn = restTemplate.postForObject(paymentManagmentUrl, payment, Payment.class);
		if(paymentreturn.getOrderStatus()==Payment.OrderStatus.Paid) {
			order.setOrderStatus(Order.OrderStatus.Paid);
			orderService.update(order);
			orderServiceAmpq.publish(order);
		}
		return order;
	}

//	@GetMapping("/restaurants/foodmenu/{foodmenuId}")
//	public FoodMenu getMenu(@PathVariable int foodmenuId) {
//		return foodMenuService.findOne((long) foodmenuId);
//	}

	@GetMapping("/orders/{foodId}/{userId}/{quantity}")
	public void upload(@PathVariable int foodId, @PathVariable int userId, @PathVariable int quantity) throws SQLException {
		menuUrl += foodId;

		HttpHeaders headers = new HttpHeaders();

		HttpEntity<Integer> requestEntity = new HttpEntity<>(foodId,headers);
//
		ResponseEntity<FoodMenu> callResuit = restTemplate.exchange(menuUrl, HttpMethod.GET ,requestEntity , FoodMenu.class);
		String name = callResuit.getBody().getFoodName();
		double price = callResuit.getBody().getFoodPrice();

//
		FoodItem fd = new FoodItem();
		fd.setFoodMenuID((long)foodId);
		fd.setQuantity(quantity);
		fd.setFoodName(name);
		fd.setFoodPrice(price);
		fd.setUser_id(String.valueOf(userId));
//		fd.setId(null);
////

		String url = "jdbc:mysql://sg-cdb-2hz2zv9f.sql.tencentcdb.com:63956/dreamorder?rewriteBatchedStatements=true&useUnicode=true&serverTimezone=Asia/Shanghai&autoReconnect=true&zeroDateTimeBehavior=convertToNull&allowMultiQueries=true&useSSL=true";
		String username = "root";
		String password = "Abc20010726";

		Connection connection = DriverManager.getConnection(url, username, password);
		if(connection!=null){
			System.out.println("连接成功");
		}
		String sql = "INSERT INTO food_item (foodmenu_id, food_name, food_price ,quantity ,user_id) VALUES (?, ?, ?, ? ,?)";
		PreparedStatement preparedStatement = connection.prepareStatement(sql);
		preparedStatement.setLong(1, fd.getFoodMenuID()); // 设置第一个参数的值
		preparedStatement.setString(2, fd.getFoodName());
		preparedStatement.setString(3, Double.toString(fd.getFoodPrice()));
		preparedStatement.setInt(4, fd.getQuantity());
		preparedStatement.setString(5, fd.getUser_id()); // 设置第二个参数的值

		int rowsAffected = preparedStatement.executeUpdate();
		System.out.println(rowsAffected);
		preparedStatement.close();
		connection.close();
	}


	@GetMapping("/orders/find/{userId}")
	public List<FoodItem> findByUserid(@PathVariable int userId) throws SQLException {
		String url = "jdbc:mysql://sg-cdb-2hz2zv9f.sql.tencentcdb.com:63956/dreamorder?rewriteBatchedStatements=true&useUnicode=true&serverTimezone=Asia/Shanghai&autoReconnect=true&zeroDateTimeBehavior=convertToNull&allowMultiQueries=true&useSSL=true";
		String username = "root";
		String password = "Abc20010726";

		List<FoodItem> foodItemList = new ArrayList<>();

		Connection connection = DriverManager.getConnection(url, username, password);
		if(connection!=null){
			System.out.println("连接成功");
		}

		String sql = "SELECT * FROM food_item WHERE user_id = ?";
		PreparedStatement preparedStatement = connection.prepareStatement(sql);
		preparedStatement.setInt(1, userId);

		try (ResultSet resultSet = preparedStatement.executeQuery()) {
			while (resultSet.next()) {
				// 处理查询结果
				FoodItem foodItem = new FoodItem();
				foodItem.setId(resultSet.getLong("FOODITEM_ID"));
				foodItem.setFoodMenuID(resultSet.getLong("FOODMENU_ID"));
				foodItem.setFoodName(resultSet.getString("food_name"));
				foodItem.setFoodPrice(resultSet.getDouble("food_price"));
				foodItem.setQuantity(resultSet.getInt("quantity"));
				foodItem.setUser_id(resultSet.getString("user_id"));
				foodItemList.add(foodItem);
			}
		}
		preparedStatement.close();
		connection.close();

		return foodItemList;
	}

	@GetMapping("/orders/delete/{userId}")
	public void deleteByUserid(@PathVariable int userId) throws SQLException {
		String url = "jdbc:mysql://sg-cdb-2hz2zv9f.sql.tencentcdb.com:63956/dreamorder?rewriteBatchedStatements=true&useUnicode=true&serverTimezone=Asia/Shanghai&autoReconnect=true&zeroDateTimeBehavior=convertToNull&allowMultiQueries=true&useSSL=true";
		String username = "root";
		String password = "Abc20010726";
		Connection connection = DriverManager.getConnection(url, username, password);
		if(connection!=null){
			System.out.println("就快删除了");
		}
		String sql = "DELETE FROM food_item WHERE user_id = ?";
		PreparedStatement preparedStatement = connection.prepareStatement(sql);
		preparedStatement.setInt(1, userId);
//
		preparedStatement.executeUpdate();
		preparedStatement.close();
		connection.close();
	}

	@GetMapping("/orders/deleteOne/{foodItemId}/{userId}")
	public void deleteByitemid(@PathVariable int foodItemId,@PathVariable int userId) throws SQLException {
		String url = "jdbc:mysql://sg-cdb-2hz2zv9f.sql.tencentcdb.com:63956/dreamorder?rewriteBatchedStatements=true&useUnicode=true&serverTimezone=Asia/Shanghai&autoReconnect=true&zeroDateTimeBehavior=convertToNull&allowMultiQueries=true&useSSL=true";
		String username = "root";
		String password = "Abc20010726";
		Connection connection = DriverManager.getConnection(url, username, password);
		String sql = "DELETE FROM food_item WHERE fooditem_id = ?";
		PreparedStatement preparedStatement = connection.prepareStatement(sql);
		preparedStatement.setInt(1, foodItemId);
		preparedStatement.executeUpdate();
		preparedStatement.close();
		connection.close();
	}




	/***
	 * get All orders API
	 * 
	 *
	 ***/
	@GetMapping("/orders")
	public List<Order> findallOrder() {
		return orderService.findAllSubSelect();
	}

	/***
	 * get all order By orderId
	 * 
	 *
	 ***/

	@GetMapping("/orders/{orderId}")
	public Order getOrderByOrderID(@PathVariable int orderId) {
		return orderService.findOne((long) orderId);
	}

	/***
	 * get all order By UserID
	 * 
	 *
	 ***/
	
	
	@GetMapping("/{userId}/orders")
	public List<Order> getOrderByUserID(@PathVariable int userId) {
		return orderService.findOneByUserId((long) userId);
	}

	@GetMapping("/alltypeorders/{userId}")
	public List<List<Order>> getAllTypeOrderByUserID(@PathVariable int userId) {
//		OderResponse res = new OderResponse(getOrderByUserID(userId),orderService.findwpByUserId((long)userId),orderService.findwcByUserId((long)userId),orderService.findafByUserId((long)userId));
//		res.setAlloders(getOrderByUserID(userId));
//		res.setWaitpayoders(orderService.findwpByUserId((long)userId));
//		res.setWaitcommentoders(orderService.findwcByUserId((long)userId));
//		res.setAfteroders(orderService.findafByUserId((long)userId));
		List<List<Order>> res = new ArrayList<>();
		res.add(orderService.findwpByUserId((long)userId));
		res.add(orderService.findwcByUserId((long)userId));
		res.add(orderService.findafByUserId((long)userId));
		return res;
	}





}
