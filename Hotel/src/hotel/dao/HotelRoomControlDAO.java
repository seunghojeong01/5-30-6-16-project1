package hotel.dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import hotel.jdbc.DBConn;
import hotel.vo.HotelRoomControlVO;

public class HotelRoomControlDAO {
	PreparedStatement pstmt = null;
	Statement stmt = null;
	Connection con = DBConn.getConnection();
	ResultSet rs = null;
	String sql = null;
	private HotelRoomControlVO hrcvo;
	
	public ArrayList<HotelRoomControlVO> room_list() {//객실 전체 목록 조회
		ArrayList<HotelRoomControlVO> RoomList = new ArrayList<>();
		try {
			sql = "select * from room";//구문 채우기		
			//stmt = con.createStatement();
			//rs = stmt.executeQuery(sql);
			pstmt = DBConn.getConnection().prepareStatement(sql);
			rs = pstmt.executeQuery();			
			while (rs.next() == true) {
				hrcvo = new HotelRoomControlVO();
				hrcvo.setRno((rs.getInt("rno")));
				hrcvo.setRoom_price((rs.getInt("room_price")));
				hrcvo.setRoom_type((rs.getString("room_type")));
				hrcvo.setMax_value((rs.getInt("max_value")));
				RoomList.add(hrcvo);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBConn.close(pstmt,rs);
		}
//		System.out.println(RoomList);
		return RoomList;
	}	
	public ArrayList<HotelRoomControlVO> search_room(int num) {//개별 객실 정보 조회 - select 1개만\
		ArrayList<HotelRoomControlVO> searchList = new ArrayList<>();
		try {
			sql = "select * from room where rno=?";//구문 채우기		
			pstmt = DBConn.getConnection().prepareStatement(sql);
			pstmt.setInt(1,num);
			rs = pstmt.executeQuery();	
			
			while (rs.next() == true) {
				hrcvo = new HotelRoomControlVO();
				hrcvo.setRno((rs.getInt("rno")));
				hrcvo.setRoom_price((rs.getInt("room_price")));
				hrcvo.setRoom_type((rs.getString("room_type")));
				hrcvo.setMax_value((rs.getInt("max_value")));
				searchList.add(hrcvo);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBConn.close(pstmt,rs);
		}
		return searchList;
	}
	public boolean update_room(int rno, int room_price,int max_value) {	//객실 정보 수정
		int result=0;
		try {
			String query="update room set rno=?,room_price=?,max_value=? "
					+ "where rno=?";
			pstmt = DBConn.getConnection().prepareStatement(query);
			pstmt.setInt(1,rno);
			pstmt.setInt(2,room_price);
			pstmt.setInt(3,max_value);
			pstmt.setInt(4,rno);
			//pstmt.setNString(1,);
			result = pstmt.executeUpdate();
			if (result == 1) {
				System.out.println("success");
			} else {
				System.out.println("falid");
			}

		}catch (SQLException e) {
			throw new RuntimeException(e.getMessage(), e);
		}finally{
			DBConn.close(pstmt);
		}		
		return true;
	}
	
	public int insert_room(HotelRoomControlVO hrcvo) {//객실 추가
		int result=0;
		try {
			String query="insert into room(rno,room_type,room_price,max_value) "
					+ "values((select max(rno) from room)+1,?,?,?)";
			pstmt = DBConn.getConnection().prepareStatement(query);			
			pstmt.setNString(1, hrcvo.getRoom_type());
			pstmt.setInt(2, hrcvo.getRoom_price());
			pstmt.setInt(3, hrcvo.getMax_value());
			result = pstmt.executeUpdate();
			if (result == 1) {
				System.out.println("success");
			} else {
				System.out.println("falid");
			}
		}catch (SQLException e) {
			throw new RuntimeException(e.getMessage(), e);
		}finally{
			DBConn.close(pstmt);
		}		
		return result;
	}
	public boolean delete_room(int rno) {//객실 삭제  입력값 1개
		int result=0;
		try {
			String query="delete from room "
					+ "where rno=? ";
			pstmt = DBConn.getConnection().prepareStatement(query);
			pstmt.setInt(1,rno);
			result = pstmt.executeUpdate();
			if (result == 1) {
				System.out.println("success");
			} else {
				System.out.println("falid");
			}

		}catch (SQLException e) {
			throw new RuntimeException(e.getMessage(), e);
		}finally{
			DBConn.close(pstmt);
		}		
		return true;
	}
}
