package hotel.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import hotel.jdbc.DBConn;
import hotel.vo.HotelEmpVO;


public class HotelEmpDAO {
	private String query; //쿼리문 저장 필드
	private PreparedStatement pstmt;
	private ResultSet rs;
	Connection con = DBConn.getConnection() ;
	
	
	
	public List<HotelEmpVO> emplist() { //직원 전체 리스트
		List<HotelEmpVO> evolist=new ArrayList<>();
		HotelEmpVO evo =null;
		try {
			query="SELECT * FROM HotelEmp ";	
			pstmt=DBConn.getConnection().prepareStatement(query);
			
			rs=pstmt.executeQuery();			
			while(rs.next()==true) {//조회된 레코드가 있다면 MemberVo 객체 생성하여 해당 레코드 값 저장
				evo=new HotelEmpVO();
				evo.setEcode(rs.getString("ecode"));
				evo.setEname(rs.getString("ename"));
				evo.setEage(rs.getInt("eage"));
				evo.setEphone(rs.getString("ephone"));
				evo.setEjob(rs.getString("ejob"));
				
				
				//List객체에 추가
				evolist.add(evo);
			}
			} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			DBConn.close(pstmt,rs);
		}
	
		return evolist;
	}
	
	public HotelEmpVO empdetail(String ecode) {//직원 상세보기
		HotelEmpVO evo=null;
		try {
			query="SELECT * FROM HotelEmp WHERE ecode=?";	
			pstmt=DBConn.getConnection().prepareStatement(query);
			pstmt.setString(1, ecode);
			rs=pstmt.executeQuery();			
			if(rs.next()==true) {//조회된 레코드가 있다면 MemberVo 객체 생성하여 해당 레코드 값 저장
				evo=new HotelEmpVO();
				evo.setEcode(rs.getString("ecode"));
				evo.setEname(rs.getString("ename"));
				evo.setEage(rs.getInt("eage"));
				evo.setEphone(rs.getString("ephone"));
				evo.setEemail(rs.getString("eemail"));
				evo.setEjob(rs.getString("ejob"));
				evo.setEgender(rs.getString("egender"));
			}
			} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			DBConn.close(pstmt,rs);
		}
		return evo;
	}
	
	
	public String login(String ecode,String ephone) {//로그인
		String job1=null;
		try {
			query="SELECT ejob FROM HotelEmp WHERE ecode=? AND ephone=?";	
			pstmt=DBConn.getConnection().prepareStatement(query);
			pstmt.setString(1, ecode);
			pstmt.setString(2, ephone);
			rs=pstmt.executeQuery();			
			if(rs.next()==true) {//조회된 레코드가 있다면 MemberVo 객체 생성하여 해당 레코드 값 저장
				if(rs.getString(1).equals("ADM")) {
					job1= "ADM";
				}else if(rs.getString(1).equals("EMP")){
					job1= "EMP";
				}	
			}else {
				job1="a";
			}
			} catch (SQLException e) {
			e.printStackTrace();
				
		}finally {
			DBConn.close(pstmt,rs);
		}
		return job1;
	}
	
	public int empinsert(HotelEmpVO evo) {//직원 정보 추가
		int result=0; 
		try {
	         query = "INSERT INTO HotelEmp VALUES(?,?,?,?,?,?,?)";
	         
	         pstmt = DBConn.getConnection().prepareStatement(query);
	         pstmt.setString(1, evo.getEcode());
	         pstmt.setString(2, evo.getEname());
	         pstmt.setInt(3, evo.getEage());
	         pstmt.setString(4, evo.getEgender());
	         pstmt.setString(5, evo.getEemail());
	         pstmt.setString(6, evo.getEphone()); 
	         pstmt.setString(7, evo.getEjob());
	         
	         result = pstmt.executeUpdate();
	      } 
		 	catch (SQLException e) {
		 		 e.printStackTrace();
	      } finally {
	         DBConn.close(pstmt);
	      }
		return result;
	}
	
	public boolean empupdate(HotelEmpVO evo) {//직원 정보 업데이트
		try {
			query="UPDATE HotelEmp SET ename=?,eage=?,eemail=?,ephone=? WHERE ecode=?";	
			pstmt=DBConn.getConnection().prepareStatement(query);
			pstmt.setString(1, evo.getEname());
			pstmt.setInt(2, evo.getEage());
			pstmt.setString(3, evo.getEemail());
			pstmt.setString(4, evo.getEphone());
			pstmt.setString(5, evo.getEcode());
			
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
	
	public boolean empdelete(String ecode) {//직원 정보 삭제
		try {
			query="DELETE FROM HotelEmp WHERE ecode=?";	
			pstmt=DBConn.getConnection().prepareStatement(query);
			pstmt.setString(1, ecode);
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