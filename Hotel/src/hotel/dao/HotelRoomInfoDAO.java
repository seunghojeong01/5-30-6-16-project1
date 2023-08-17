package hotel.dao;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import hotel.jdbc.DBConn;
import hotel.vo.HotelRoomControlVO;
import hotel.vo.HotelRoomInfoVO;
import oracle.jdbc.driver.DBConversion;

public class HotelRoomInfoDAO {//
	PreparedStatement pstmt = null;
	Statement stmt = null;
	Connection con = DBConn.getConnection() ;
	ResultSet rs = null;
	String sql = null;
	private HotelRoomInfoVO hrivo;
	
	public int checkin(HotelRoomInfoVO hrivo,Date date){//체크인 접수 insert = vo 받아서 하는거
		int result=0;
		try {
			String query="insert into customer(rno,cname,cphone,cemail,in_date,out_date,rsv_total) "
					+ "values(?,?,?,?,sysdate,?,0)";
			pstmt = DBConn.getConnection().prepareStatement(query);			
			pstmt.setInt(1,hrivo.getRno());
			pstmt.setNString(2,hrivo.getCname());
			pstmt.setNString(3,hrivo.getCphone());
			pstmt.setNString(4,hrivo.getCemail());
			pstmt.setDate(5,date);
			result = pstmt.executeUpdate();
		}catch (SQLException e) {
			throw new RuntimeException(e.getMessage(), e);
		}finally{
			DBConn.close(pstmt);
		}		
		return result;
	}
	public int checkout(int rno) {//체크아웃 접수 delete int만 받아서 삭제
		int result=0;
		try {
			String query="delete from customer "
					+ "where rno=? ";
			pstmt = DBConn.getConnection().prepareStatement(query);
			pstmt.setInt(1,rno);
			result = pstmt.executeUpdate();

		}catch (SQLException e) {
			throw new RuntimeException(e.getMessage(), e);
		}finally{
			DBConn.close(pstmt);
		}		
		return result;
	}
	public ArrayList<HotelRoomInfoVO> check_roomlist() {//전체 객실 예약 정보 select
		ArrayList<HotelRoomInfoVO> check_roomList = new ArrayList<>();
		try {
			sql = "select r.rno,NVL(c.cname,'N/A'), "
					+ "nvl(to_char(c.in_date,'YYYY-MM-DD'),'0000-00-00'),"
					+ "nvl(to_char(c.out_date,'YYYY-MM-DD'),'0000-00-00'),r.room_clean, "
					+ "CASE r.check_in WHEN '0' THEN '공실' ELSE '재실' END,"
					+ "CASE r.dns_check WHEN '0' THEN 'X' ELSE 'O' END "
					+ "from customer c RIGHT OUTER JOIN roomhistory r "
					+ "on c.rno = r.rno order by rno asc ";//구문 채우기
			pstmt = DBConn.getConnection().prepareStatement(sql);
			rs = pstmt.executeQuery();
			while (rs.next() == true) {
				hrivo = new HotelRoomInfoVO();
				hrivo.setRno((rs.getInt(1)));	
				hrivo.setCname((rs.getString(2)));
				//hrivo.setCin_date((rs.getDate(3)));
				//hrivo.setCout_date((rs.getDate(4)));
				
				hrivo.setCin_date1((rs.getString(3)));
				hrivo.setCout_date1((rs.getString(4)));
				
				hrivo.setRoom_clean((rs.getString(5)));
				hrivo.setCheck_in((rs.getString(6)));
				hrivo.setDns_check((rs.getString(7)));
				check_roomList.add(hrivo);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBConn.close(pstmt,rs);
		}
		return check_roomList;
	}
	public ArrayList<HotelRoomInfoVO> check_room(int num) {//객실별 예약정보 조회
		ArrayList<HotelRoomInfoVO> checkcustomer = new ArrayList<>();
		try {
			sql = "select r.rno,NVL(c.cname,'N/A'), "
					+ "nvl(to_char(c.in_date,'YYYY-MM-DD'),'0000-00-00'),"
					+ "nvl(to_char(c.out_date,'YYYY-MM-DD'),'0000-00-00'),r.room_clean, "
					+ "CASE r.check_in WHEN '0' THEN '공실' ELSE '재실' END,"
					+ "CASE r.dns_check WHEN '0' THEN 'X' ELSE 'O' END "
					+ "from customer c RIGHT OUTER JOIN roomhistory r "
					+ "on c.rno = r.rno where r.rno=? ";//구문 채우기
			pstmt = DBConn.getConnection().prepareStatement(sql);
			pstmt.setInt(1,num);
			rs = pstmt.executeQuery();	
			
			while (rs.next() == true) {
				hrivo = new HotelRoomInfoVO();
				hrivo.setRno((rs.getInt(1)));	
				hrivo.setCname((rs.getString(2)));
				//hrivo.setCin_date((rs.getDate(3)));
				//hrivo.setCout_date((rs.getDate(4)));
				hrivo.setCin_date1((rs.getString(3)));
				hrivo.setCout_date1((rs.getString(4)));
				hrivo.setRoom_clean((rs.getString(5)));
				hrivo.setCheck_in((rs.getString(6)));
				hrivo.setDns_check((rs.getString(7)));
				
				checkcustomer.add(hrivo);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBConn.close(pstmt,rs);
		}
		return checkcustomer;	
	}
	
	public ArrayList<HotelRoomInfoVO> check_res() {//입실 가능한 객실 목록
		ArrayList<HotelRoomInfoVO> checkres = new ArrayList<>();
		try {
			sql = "select rno,CASE check_in WHEN '0' THEN '공실' ELSE '재실' END "
					+ " from roomhistory "
					+ "where room_clean='inspect' AND check_in='0' AND dns_check='0'";
			pstmt = DBConn.getConnection().prepareStatement(sql);
			rs = pstmt.executeQuery();
			while (rs.next() == true) {
				hrivo = new HotelRoomInfoVO();
				hrivo.setRno((rs.getInt(1)));	
				hrivo.setCheck_in((rs.getString(2)));	
				checkres.add(hrivo);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally { 
			DBConn.close(pstmt,rs);
		}
		return checkres;
	}
	public boolean room_change(int rno,int update_rno) {
		try {
			sql = "update customer set rno=? "
					+"where rno=?";
			pstmt = DBConn.getConnection().prepareStatement(sql);
			pstmt.setInt(1,update_rno);
			pstmt.setInt(2,rno);
			rs = pstmt.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally { 
			DBConn.close(pstmt,rs) ;
		}
		return true;
	}
	public ArrayList<HotelRoomInfoVO> checkout_select(int num) {//퇴실 처리 과정
		ArrayList<HotelRoomInfoVO> checkres = new ArrayList<>();
		try {
			sql = "select rno,cname,out_date,rsv_total from customer where rno=? ";
			pstmt = DBConn.getConnection().prepareStatement(sql);
			pstmt.setInt(1,num);
			rs = pstmt.executeQuery();
			while (rs.next() == true) {
				hrivo = new HotelRoomInfoVO();
				hrivo.setRno((rs.getInt(1)));	
				hrivo.setCname((rs.getString(2)));
				hrivo.setCout_date((rs.getDate(3)));	
				hrivo.setRoomservice_total((rs.getInt(4)));
				checkres.add(hrivo);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally { 
			DBConn.close(pstmt,rs);
		}
		return checkres;
	}
	
	
	public int clean_update(int rno,String str) {
		int result=0;
		try {
			sql = "update roomhistory set room_clean=? "
					+"where rno=?";
			pstmt = DBConn.getConnection().prepareStatement(sql);
			pstmt.setString(1,str);
			pstmt.setInt(2,rno);
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally { 
			DBConn.close(pstmt,rs) ;
		}
		return result;
	}
	
}
