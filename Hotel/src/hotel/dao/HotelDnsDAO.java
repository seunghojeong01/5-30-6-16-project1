package hotel.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import hotel.jdbc.DBConn;
import hotel.vo.HotelDnsVO;

public class HotelDnsDAO {
	private String query; //쿼리문 저장 필드
	private PreparedStatement pstmt;
	private ResultSet rs;
	Connection con = DBConn.getConnection() ;
	
	public List<HotelDnsVO> dnslist() {
		List<HotelDnsVO> dvolist=new ArrayList<>();
		HotelDnsVO dvo =null;
		try {
			query="SELECT d.rno, d.dreason, e.ename, d.dnsdate"
					+ " FROM HotelDns d INNER JOIN HotelEmp e"
					+ " ON d.ecode = e.ecode";	
			pstmt=DBConn.getConnection().prepareStatement(query);
			
			rs=pstmt.executeQuery();			
			while(rs.next()==true) {//조회된 레코드가 있다면 MemberVo 객체 생성하여 해당 레코드 값 저장
				dvo=new HotelDnsVO();
				dvo.setDreason(rs.getString("dreason"));
				dvo.setDnsdate(rs.getDate("dnsdate"));
				dvo.setEname(rs.getString("ename"));
				dvo.setRno(rs.getInt("rno"));
				//List객체에 추가
				dvolist.add(dvo);
			}
			} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			DBConn.close(pstmt,rs);
		}
		return dvolist;
	}
	
	public HotelDnsVO dnsdetail(int dno) {
		HotelDnsVO dvo =null;
		try {
			query="SELECT d.rno, d.dreason,d.ecode, e.ename, d.dnsdate"
					+ " FROM HotelDns d INNER JOIN HotelEmp e ON d.ecode = e.ecode WHERE rno=?";	
			pstmt=DBConn.getConnection().prepareStatement(query);
			pstmt.setInt(1, dno);
			rs=pstmt.executeQuery();			
			if(rs.next()==true) {//조회된 레코드가 있다면 MemberVo 객체 생성하여 해당 레코드 값 저장
				dvo=new HotelDnsVO();
				dvo.setDreason(rs.getString("dreason"));
				dvo.setEcode(rs.getString("ecode"));
				dvo.setEname(rs.getString("ename"));
				dvo.setDnsdate(rs.getDate("dnsdate"));
				dvo.setRno(rs.getInt("rno"));
				
			}
			} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			DBConn.close(pstmt,rs);
		}
		return dvo;
	}
	
	public boolean dnsinsert(HotelDnsVO dvo) {//직원 정보 추가
		boolean boo=false;
	      try {
	         query = "INSERT INTO HotelDns (dreason, dnsdate, ecode, rno) VALUES (?, SYSDATE, ?, ?)";
	         
	         pstmt = DBConn.getConnection().prepareStatement(query);
	         pstmt.setString(1, dvo.getDreason());
	         pstmt.setString(2, dvo.getEcode());
	         pstmt.setInt(3, dvo.getRno());
	        
	         
	         int result = pstmt.executeUpdate();
	         if(result ==1 ) {
	            boo= true;
	         } else {
	        	 boo=false;
	         }
	      } catch (SQLException e) {
	    	  if (e.getErrorCode() == 1) {
	              System.out.println("이미 등록된 방 호수입니다.");
	          } else {
	              e.printStackTrace();
	          } 
	      } finally {
	         DBConn.close(pstmt);
	      }
		return boo;
	}
	
	public boolean dnsupdate(HotelDnsVO dvo) {//직원 정보 업데이트
		try {
			query="UPDATE HotelDns SET rno=?,dreason=?,ecode=?,dnsdate=SYSDATE WHERE rno=?";	
			pstmt=DBConn.getConnection().prepareStatement(query);
			pstmt.setInt(1, dvo.getRno());
			pstmt.setString(2, dvo.getDreason());
			pstmt.setString(3, dvo.getEcode());
			pstmt.setInt(4, dvo.getRno());
			
			
			 int result = pstmt.executeUpdate();
	         if(result !=0) {
	            return true;
	         } else {
	            return false;
	         }
	      } catch (SQLException e) {
	         e.printStackTrace();
	      } finally {
	         DBConn.close(pstmt);
	      }
		return true;
	}
	public boolean dnsdelete(int rno) {//직원 정보 삭제
		try {
			query="DELETE FROM HotelDns WHERE rno=?";	
			pstmt=DBConn.getConnection().prepareStatement(query);
			pstmt.setInt(1, rno);
			int result = pstmt.executeUpdate();
	        if(result !=0) {
	            return true;
	         } else {
	            return false;
	         }
		}catch (SQLException e) {
	         e.printStackTrace();
	      } finally {
	         DBConn.close(pstmt);
	      }
		return true;
	}
}