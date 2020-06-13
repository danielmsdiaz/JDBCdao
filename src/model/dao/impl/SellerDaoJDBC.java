package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.print.attribute.HashAttributeSet;

import db.DB;
import db.DbException;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

public class SellerDaoJDBC implements SellerDao {

	private Connection conn;

	public SellerDaoJDBC(Connection conn) {
		this.conn = conn;
	}

	@Override
	public void insert(Seller seller) {
		// TODO Auto-generated method stub

	}

	@Override
	public void update(Seller seller) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteById(Integer id) {
		// TODO Auto-generated method stub

	}

	@Override
	public Seller findById(Integer id) {
		PreparedStatement st = null;
		ResultSet rs = null;

		// comandos sql gravados numa string
		String query = "SELECT seller.*,department.Name as DepName " + "FROM seller INNER JOIN department "
				+ "ON seller.DepartmentId = department.Id " + "WHERE seller.Id = ? ";
		try {

			st = conn.prepareStatement(query); // filtro realizado
			st.setInt(1, id); // mudando o valor de ? pelo paramentro
			rs = st.executeQuery(); // executando a mudanca

			if (rs.next() == true) { // checando se houve algum resultado

				// o result set tem formato de tabela, entao tem q transformar em objeto

				Department dep = instantiateDepartment(rs);
				Seller seller = instantiateSeller(rs, dep);
				return seller;
			} else {
				return null;
			}
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}

	}

	private Seller instantiateSeller(ResultSet rs, Department dep) throws SQLException {
		Seller seller = new Seller();
		seller.setId(rs.getInt("Id"));
		seller.setName(rs.getString("Name"));
		seller.setEmail(rs.getString("Email"));
		seller.setBirthDate(rs.getDate("BirthDate"));
		seller.setBaseSalary(rs.getDouble("BaseSalary"));
		seller.setDepartment(dep);
		return seller;
	}

	private Department instantiateDepartment(ResultSet rs) throws SQLException {
		Department dep = new Department();
		dep.setId(rs.getInt("DepartmentId"));
		dep.setName(rs.getString("DepName"));
		return dep;

	}

	@Override
	public List<Seller> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Seller> findByDepartment(Department dep) {
		PreparedStatement st = null;
		ResultSet rs = null;
		String query = "SELECT seller.*,department.Name as DepName " 
						+ "FROM seller INNER JOIN department "
						+ "ON seller.DepartmentId = department.Id " 
						+ "WHERE DepartmentId = ? "
						+ "ORDER BY Name ";
		try {
			st = conn.prepareStatement(query);
			st.setInt(1, dep.getId());
			rs = st.executeQuery();
			ArrayList<Seller> sList = new ArrayList<>();
			Map<Integer, Department> map = new HashMap<>();

			while (rs.next() == true) {
				Department depAux = map.get(rs.getInt("DepartmentId"));
				if(depAux == null) {
				dep = instantiateDepartment(rs);
				map.put(rs.getInt("DepartmentId"), dep);
				}
				Seller seller = instantiateSeller(rs, dep);
				sList.add(seller);
				
			}
			return sList;
			
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}

	}
}
