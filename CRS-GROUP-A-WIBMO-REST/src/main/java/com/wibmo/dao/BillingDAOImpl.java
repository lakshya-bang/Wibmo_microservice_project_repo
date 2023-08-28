/**@author mourila.vatsav
 * 
 */
package com.wibmo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.springframework.stereotype.Repository;

import com.wibmo.dto.PaymentDebitDTO;
import com.wibmo.dto.PaymentNetBankingDTO;
import com.wibmo.dto.PaymentUPIDTO;
import com.wibmo.entity.Bill;
import com.wibmo.enums.PaymentMode;
import com.wibmo.utils.DBUtils;

/**
 * 
 */
@Repository
public class BillingDAOImpl implements BillingDAO{
	
	private Logger logger = LogManager.getLogger(BillingDAOImpl.class);
	
	@Override
	public Bill findByStudentId(Integer studentId) {
		// TODO Auto-generated method stub
		Bill bill = new Bill();
		
		String sql = "SELECT * FROM user.billing "
				+ "WHERE student_id = ?";
		
		Connection conn = DBUtils.getConnection();
		try {
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, studentId);
			
			ResultSet rs = stmt.executeQuery();
			
			if(rs.next()) {
				bill.setBillId(rs.getInt("bill_id"));
				bill.setStudentId(rs.getInt("student_id"));
				bill.setCourseRegId(rs.getInt("course_reg_id"));
				bill.setTotalAmount(rs.getInt("total_amount"));
				bill.setAmountPending(rs.getInt("amount_pending"));
			}
			
		} catch (SQLException e) {
			logger.error(e.getMessage());
		}
		return bill;
	}
	
	@Override
	public boolean verifyDebitDetails(PaymentDebitDTO paymentDebitDTO,Integer studentId) {
		// TODO Auto-generated method stub
		String sql=null;
		
		sql = "Select * FROM user.payment_debit";
		Connection conn = DBUtils.getConnection();
		try {
			PreparedStatement stmt = conn.prepareStatement(sql);
			
			ResultSet rs = stmt.executeQuery();
			
			while(rs.next()) {
				if(paymentDebitDTO.getDebitAccountNumber().equalsIgnoreCase(rs.getString("debit_account_number"))&&
						paymentDebitDTO.getDebitExpiryDate().equalsIgnoreCase(rs.getString("debit_expiry_date"))&&
						paymentDebitDTO.getDebitCvv().equals(rs.getInt("debit_cvv"))) {
					return true;
				}
				
			}
			
		} catch (SQLException e) {
			logger.error(e.getMessage());
		}
		
		return false;
	}
	
	@Override
	public boolean verifyNetBankingDetails(PaymentNetBankingDTO paymentNetBankingDTO, Integer studentId) {
		// TODO Auto-generated method stub
		String sql=null;
		
		sql = "Select * FROM user.payment_net_banking";
		Connection conn = DBUtils.getConnection();
		try {
			PreparedStatement stmt = conn.prepareStatement(sql);
			
			ResultSet rs = stmt.executeQuery();
			
			while(rs.next()) {
				if(paymentNetBankingDTO.getUserName().equals(rs.getString("net_banking_username"))&&
						paymentNetBankingDTO.getPassword().equals(rs.getString("net_banking_password"))) {
					return true;
				}
				
			}
			
		} catch (SQLException e) {
			logger.error(e.getMessage());
		}
		return false;
	}
	
	@Override
	public boolean verifyUPIDetails(PaymentUPIDTO paymentUPIDTO, Integer studentId) {
		// TODO Auto-generated method stub
		String sql=null;
		
		sql = "Select * FROM user.payment_upi";
		Connection conn = DBUtils.getConnection();
		try {
			PreparedStatement stmt = conn.prepareStatement(sql);
			
			ResultSet rs = stmt.executeQuery();
			
			while(rs.next()) {
				if(paymentUPIDTO.getUpiId().equals(rs.getString("upi_id"))) {
					return true;
				}
				
			}
		} catch (SQLException e) {
			logger.error(e.getMessage());
		}
		return false;
	}
	
	@Override
	public boolean payBill(Integer studentId) {
		// TODO Auto-generated method stub
		String sql;
		sql = "UPDATE user.billing SET amount_pending=0 WHERE student_id=?";
		Connection conn = DBUtils.getConnection();
		try {
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, studentId);
			
			int rs = stmt.executeUpdate();
			
			return rs>0;
		} catch (SQLException e) {
			logger.error(e.getMessage());
		}
		return false;
	}
	
}
