/**
 * 
 */
package com.wibmo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.wibmo.bean.Notification;
import com.wibmo.constant.SQLConstants;
import com.wibmo.utils.DBUtils;

/**
 * 
 */
public class NotificationDAOImpl implements NotificationDAO{
	
	private static volatile NotificationDAOImpl instance = null;
	
	private NotificationDAOImpl() {}
	
	public static NotificationDAOImpl getInstance() {
        if (instance == null) {
            synchronized (NotificationDAOImpl.class) { //It's a synchronized object that will thread safe.
                instance = new NotificationDAOImpl();
            }
        }
        return instance;
    }


	@Override
	public void create(Notification notification) {
		
		Connection conn = DBUtils.getConnection();
		String sql = SQLConstants.NOTIFICATION_CREATE;
		try{
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, notification.getNotificationUserId());
			stmt.setString(2, notification.getNotificationMessage());
			stmt.executeUpdate(sql);
		}
		catch(SQLException se){
			se.printStackTrace();
		 }catch(Exception e){
			e.printStackTrace();
		 }
		
	}

	@Override
	public ArrayList<Notification> fetchByUserId(Integer userId) {
		ArrayList<Notification> notifications = new ArrayList<>();
		Connection conn = DBUtils.getConnection();
		String sql = SQLConstants.NOTIFICATION_FETCH_BY_ID;
		try{
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, userId);
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next()){
				Notification notification;
				notification = new Notification(rs.getInt("notification_id"), 
												rs.getInt("notification_to_user"),
												rs.getString("notification_status"),
												rs.getString("notification_message"));
				notifications.add(notification);
			}
		}
		catch(SQLException se){
			se.printStackTrace();
		 }catch(Exception e){
			e.printStackTrace();
		 }
		return notifications;
	}

	@Override
	public void updateStatus(ArrayList<Notification> notifications) {
		Connection conn = DBUtils.getConnection();
		String sql = SQLConstants.NOTIFICATION_UPDATE_STATUS;
		for(Notification notification : notifications){
			try {
				PreparedStatement stmt = conn.prepareStatement(sql);
				stmt.setInt(1, notification.getNotificationId());
				stmt.executeUpdate();
			}
			catch(SQLException se){
			se.printStackTrace();
		 	}catch(Exception e){
			e.printStackTrace();
		 	}
		}
	}
	
}
