import java.io.FileNotFoundException;
import java.io.IOException;

import com.csvreader.CsvReader;

public class pomoc {

	public static void main(String[] args) {
		try {
			
			CsvReader products = new CsvReader("pomoc.csv");
		
			products.readHeaders();

			while (products.readRecord())
			{
				String productID = products.get("ProductID");
				String productName = products.get("ProductName");
				String supplierID = products.get("SupplierID");
				String categoryID = products.get("CategoryID");
				String quantityPerUnit = products.get("QuantityPerUnit");
				String unitPrice = products.get("UnitPrice");
				String unitsInStock = products.get("UnitsInStock");
				String unitsOnOrder = products.get("UnitsOnOrder");
				String reorderLevel = products.get("ReorderLevel");
				String discontinued = products.get("Discontinued");
				
				// perform program logic here
				System.out.print(productID + ":" + productName+ ":" +supplierID+ ":" + categoryID+ ":" + quantityPerUnit+ ":");
				System.out.println(unitPrice + ":" + unitsInStock+ ":" + unitsOnOrder+ ":" + reorderLevel+ ":" + discontinued);
			}
	
			products.close();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

}