package com.metaformers.evaluation;

import java.util.List;

public class Test {

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		
		// TODO Auto-generated method stub
		try(DataStore dataStore = new DataStore()) {
			
			// Read csv file
			List<Product> productList = Action.readCSV("products.csv");
			
			// Store Products in derby
			int num = Action.storeObjects(productList, dataStore);
			System.out.println(num + " new items have been inserted!");
			
			// Select product by PRODUCT_ID
			List<Product> result = Action.selectById(980001, dataStore);
			// print results
			for(int i=0; i<result.size(); i++) {
				StringBuffer sb = new StringBuffer();
				sb.append(result.get(i).getProductId()+", ");
				sb.append(result.get(i).getManufacture()+", ");
				sb.append(result.get(i).getProductCode()+", ");
				sb.append(result.get(i).getPurchaseCost()+", ");
				sb.append(result.get(i).getQuantityOnHand()+", ");
				sb.append(result.get(i).getMarkup()+", ");
				sb.append(result.get(i).isAvailable()+", ");
				sb.append(result.get(i).getDescription());
				System.out.println(sb);
			}
		}
		
	}

}
