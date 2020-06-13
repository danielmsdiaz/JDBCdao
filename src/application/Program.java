package application;

import java.util.List;

import db.DB;
import model.dao.DaoFactory;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

public class Program {

	public static void main(String[] args) {
		
		SellerDao sellerDao = DaoFactory.createSellerDao();
		List<Seller> seller = sellerDao.findAll();
		for(int i = 0; i < seller.size(); i++) {
			System.out.println(seller.get(i));
		}
		DB.closeConnection();
		
		
	}

}
