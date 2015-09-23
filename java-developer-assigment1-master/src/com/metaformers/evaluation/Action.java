/*
 * Actions: Read csv file, store in derby, select by PRODUCT_ID or DESCRIPTION
 */
package com.metaformers.evaluation;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Action {
	
	/**
	 * Read csv file
	 * @param fileName Name of the file
	 * @return Object list that contains each line
	 */
	@SuppressWarnings("resource")
	public static List<Product> readCSV( String fileName ) {
		List<Product> productList = new ArrayList<Product>();
		try {
			BufferedReader reader = new BufferedReader(new FileReader(fileName));
			reader.readLine(); // Ignore first line
			String line = null;
			while( (line=reader.readLine())!=null ) {	// Read each line
				String item[] = line.split(",");
				Product product = new Product();
				for (int i=0; i<item.length; i++) {
					switch(i) {
					case 0:
						product.setProductId(Integer.parseInt(item[i]));
						break;
					case 1:
						product.setManufacture(item[i]);
						break;
					case 2:
						product.setProductCode(item[i]);
						break;
					case 3:
						product.setPurchaseCost(Float.parseFloat(item[i]));
						break;
					case 4:
						product.setQuantityOnHand(Integer.parseInt(item[4]));
						break;
					case 5:
						product.setMarkup(Float.parseFloat(item[5]));
						break;
					case 6:
						if(item[6].equals("TRUE")) {
							product.setAvailable(true);
						} else {
							product.setAvailable(false);
						}
						break;
					case 7:
						product.setDescription(item[7]);
						break;
					default:
						break;
					}
				}
				
				productList.add(product);	// Add current Object to the List
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("File read!");
		return productList;
	}
	
	
	/**
	 * Insert products to the database
	 * @param productList products which should be inserted
	 * @param dataStore Reference of the database
	 * @return Number of lines inserted
	 * @throws SQLException 
	 */
	public static int storeObjects(List<Product> productList, DataStore dataStore) throws SQLException{
		int num = 0;
		String query;
		for(int i=0; i<productList.size(); i++) {
			query = "insert into PRODUCTS values ("
					+ productList.get(i).getProductId() + ", "
					+ "'" + productList.get(i).getManufacture() + "', "
					+ "'" + productList.get(i).getProductCode() + "', "
					+ productList.get(i).getPurchaseCost() + ", "
					+ productList.get(i).getQuantityOnHand() + ", "
					+ productList.get(i).getMarkup() + ", "
					+ productList.get(i).isAvailable() + ", "
					+ "'" + productList.get(i).getDescription() + "'"
					+")" ;
			num += dataStore.update(query);
		}
		return num;
	}
	
	/**
	 * Select objects from database by PRODUCT_ID
	 * @param productId
	 * @param dataStore Reference of the database
	 * @return Result List of "select" 
	 * @throws SQLException
	 */
	public static List<Product> selectById(int productId, DataStore dataStore) throws SQLException {
		List<Product> resultList = new ArrayList<Product>();
		String query = "select * from PRODUCTS where PRODUCT_ID = "+productId;
		ResultSet rs = dataStore.query(query);
		
		while(rs.next()) {
			Product product = new Product();
			product.setProductId(rs.getInt("PRODUCT_ID"));
			product.setManufacture(rs.getString("MANUFACTURER"));
			product.setProductCode(rs.getString("PRODUCT_CODE"));
			product.setPurchaseCost(rs.getFloat("PURCHASE_COST"));
			product.setQuantityOnHand(rs.getInt("QUANTITY_ON_HAND"));
			product.setMarkup(rs.getFloat("MARKUP"));
			product.setAvailable(rs.getBoolean("AVAILABLE"));
			product.setDescription(rs.getString("DESCRIPTION"));
			
			resultList.add(product);	// Add current object to the result
		}
		return resultList;
	}
	
	
	/**
	 * Select objects from database by DESCRIPTION
	 * @param description
	 * @param dataStore Reference of the database
	 * @return Result List of "select"
	 * @throws SQLException
	 */
	public static List<Product> selectByDescription( String description, DataStore dataStore) throws SQLException {
		List<Product> resultList = new ArrayList<Product>();
		String query = "select * from PRODUCTS where DESCRIPTION = " + "'"+ description + "'";
		ResultSet rs = dataStore.query(query);
		
		while(rs.next()) {
			Product product = new Product();
			product.setProductId(rs.getInt("PRODUCT_ID"));
			product.setManufacture(rs.getString("MANUFACTURER"));
			product.setProductCode(rs.getString("PRODUCT_CODE"));
			product.setPurchaseCost(rs.getFloat("PURCHASE_COST"));
			product.setQuantityOnHand(rs.getInt("QUANTITY_ON_HAND"));
			product.setMarkup(rs.getFloat("MARKUP"));
			product.setAvailable(rs.getBoolean("AVAILABLE"));
			product.setDescription(rs.getString("DESCRIPTION"));
			
			resultList.add(product); // Add current object to the result
		}
		return resultList;
	}
	
}
