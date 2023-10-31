package edu.mum.dream.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import edu.mum.dream.dao.FoodItemDao;
import edu.mum.dream.domain.FoodItem;
import org.springframework.stereotype.Repository;

import edu.mum.dream.dao.OrderDao;
import edu.mum.dream.domain.Order;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@Repository
public class FoodItemDaoImpl extends GenericDaoImpl<FoodItem> implements FoodItemDao {
    @PersistenceContext
    protected EntityManager entityManager;

    public FoodItemDaoImpl() {
        super.setDaoType(FoodItem.class);
    }
    String url = "jdbc:mysql://sg-cdb-2hz2zv9f.sql.tencentcdb.com:63956/dreamorder?rewriteBatchedStatements=true&useUnicode=true&serverTimezone=Asia/Shanghai&autoReconnect=true&zeroDateTimeBehavior=convertToNull&allowMultiQueries=true&useSSL=true";
    String username = "root";
    String password = "Abc20010726";

    public int findOnea(FoodItem fd) throws SQLException {
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
        preparedStatement.close();
        connection.close();

        return rowsAffected;
    }


}
