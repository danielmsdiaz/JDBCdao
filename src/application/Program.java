package application;

import java.util.Date;
import java.util.List;

import db.DB;
import model.dao.DaoFactory;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

public class Program {

	public static void main(String[] args) {
		
		SellerDao sellerDao = DaoFactory.createSellerDao();
		Seller seller = new Seller(null, "pedro", "pedro@hotmail.com", new Date(), 3000.0, new Department(1, null));
		seller = sellerDao.findById(1);
		sellerDao.deleteById(2);
		
		
	}

}
