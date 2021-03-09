package data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.Local;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import beans.Order;

@Stateless
@Local(DataAccessInterface.class)
@LocalBean
public class OrdersDataService implements DataAccessInterface {
	

	public OrdersDataService() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public List<Order> findAll() {
		
		System.out.println("---->Entering OrdersDataService.findAll()...");
		
		List<Order> orders = new ArrayList<Order>(); 


		//create a Connection as a local variable 
		Connection conn = null;

		// db url & login credentials needed to create the connection
		String url = "jdbc:postgresql://localhost:5432/postgres";
		String username = "postgres";
		String password = "root";

		// sql query to the db
		String sql = "SELECT * FROM testapp.ORDERS";

		try {
			// connect to the db
			conn = DriverManager.getConnection(url, username, password);

			// output if connection was successful
			System.out.println("The connection in findAll() was success!");

			// create the connection statement
			Statement stmt = conn.createStatement();

			// execute sql query
			ResultSet rs = stmt.executeQuery(sql);

			// use a while loop to go through the result set
			while (rs.next()) {

				// add each row in the result set as a new order
				orders.add(new Order(rs.getInt("ID"), rs.getString("ORDER_NO"), rs.getString("PRODUCT_NAME"), rs.getFloat("PRICE"),
						rs.getInt("QUANTITY")));

			}

			// close the connection
			conn.close();
			System.out.println("Connection was closed in OrdersDataService.findAll()");



		} catch (SQLException e) {
			// output if connection was successful
			System.out.println("The connection in findAll() failed!");

			e.printStackTrace();
		}
		
		System.out.println("---->Leaving OrdersDataService.findAll()...");

		//return the orders list 
		return orders; 
	}

}
