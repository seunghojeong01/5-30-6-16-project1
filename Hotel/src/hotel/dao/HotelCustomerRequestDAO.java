package hotel.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import hotel.jdbc.DBConn;
import hotel.vo.HotelCustomerRequestVO;

public class HotelCustomerRequestDAO {

	private String query;
	private PreparedStatement pstmt;
	private ResultSet rs;
	List<HotelCustomerRequestVO> list;

	public boolean insert(HotelCustomerRequestVO reqvo) {
//	COM_014	카테고리별 고객요청사항 등록 화면
//	고객요청사항의 카테고리를 입력받고, 객실 호수와 한 줄 메모 형식으로 요청사항을 입력받아 추가한다.
		try {
			// insert 쿼리 넣기
			// 조인 쿼리 필요
			query = " INSERT INTO hotelreq (num, category, rno, reqMessage, ecode, reqDate) "
					+ " VALUES((SELECT NVL(MAX(num), 0)+1 FROM hotelreq WHERE category = ?), ?, ?, ?, ?, sysdate) ";
			pstmt = DBConn.getConnection().prepareStatement(query); // getConnection이 static이므로 참조변수 필요X
			pstmt.setInt(1, reqvo.getCategory());
			pstmt.setInt(2, reqvo.getCategory());
			pstmt.setInt(3, reqvo.getRno());
			pstmt.setString(4, reqvo.getReqMessage());
			pstmt.setString(5, reqvo.getEcode());

			int result = pstmt.executeUpdate();

			if (result == 1) { // 정상적으로 고객요청사항 등록 실행성공시 true 반환
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBConn.close(pstmt);
		}
		return false;// 그렇지 않으면 false 반환

	} // END insert()

	public List<HotelCustomerRequestVO> select(int category) {
// COM_015	카테고리별 고객요청사항 조회 화면
// 고객요청사항의 카테고리를 입력받고, 해당 카테고리의 고객요청사항을 조회한다.

		HotelCustomerRequestVO reqvo = null; // 지역변수 초기화

		// 해당레코드를 객체에 저장
		List<HotelCustomerRequestVO> reqvoList = new ArrayList<>();

		try {
			// selct 쿼리
			query = " SELECT HotelReq.*, HotelEmp.ename " + " FROM HotelReq "
					+ " JOIN HotelEmp ON HotelReq.ecode = HotelEmp.ecode " + " WHERE category=? " + " ORDER BY num ";
			pstmt = DBConn.getConnection().prepareStatement(query);
			pstmt.setInt(1, category);

			rs = pstmt.executeQuery();
			// 조회된 레코드가 있다면 true 반환 후 new객체 생성, 없다면 false 반환 후 null값으로 넘김(목록 조회시 무조건 거쳐가야함)
			// 레코드가 1개면 If, 여러개면 while문
			while (rs.next() == true) {
				reqvo = new HotelCustomerRequestVO(); // VO 객체 생성
				reqvo.setNum(rs.getInt("num"));
				reqvo.setCategory(rs.getInt("category"));
				reqvo.setRno(rs.getInt("rno"));
				reqvo.setReqMessage(rs.getString("reqMessage"));
				reqvo.setEname(rs.getString("ename"));
				reqvo.setReqDate(rs.getDate("reqDate"));
				reqvoList.add(reqvo);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBConn.close(pstmt, rs); // pstmt와 rs를 동시에 close 하기 위함
		}

		return reqvoList;
	}

	public HotelCustomerRequestVO select(int category, int num) {
// COM_015	카테고리별 고객요청사항 조회 화면
// 고객요청사항의 카테고리를 입력 후, 해당 카테고리의 등록번호 입력시 해당 요청사항 조회한다.

		HotelCustomerRequestVO reqvo = null; // 지역변수는 초기화 필요

		try {
			// select 쿼리 넣기
			query = " SELECT HotelReq.*, HotelEmp.ename " + " FROM HotelReq "
					+ " JOIN HotelEmp ON HotelReq.ecode = HotelEmp.ecode " + " WHERE category=? and num =? ";
			pstmt = DBConn.getConnection().prepareStatement(query); // 2. getconnection으로 받아온다
			pstmt.setInt(1, category);
			pstmt.setInt(2, num);
			rs = pstmt.executeQuery(); // 쿼리 결과 저장

			if (rs.next()) {
				reqvo = new HotelCustomerRequestVO();
				reqvo.setNum(rs.getInt("num"));
				reqvo.setCategory(rs.getInt("category"));
				reqvo.setRno(rs.getInt("rno"));
				reqvo.setReqMessage(rs.getString("reqMessage"));
				reqvo.setEname(rs.getString("ename"));
				reqvo.setReqDate(rs.getDate("reqDate"));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBConn.close(pstmt, rs);
		}

		return reqvo;
	}

	public boolean update(HotelCustomerRequestVO reqvo) {
//	COM_016	카테고리별 고객요청사항 수정 화면
//	고객요청사항 고유번호를 입력받고, 고객의 객실 호수를 입력 받은 후 고객요청목록의 고객 요청 사항을 수정한다.
		try {
			query = " UPDATE HotelReq SET category=?, rno=?, reqMessage=?, ecode=?, reqDate=SYSDATE "
					+ " WHERE category=? and num =? ";

			pstmt = DBConn.getConnection().prepareStatement(query); // getConnection이 static이므로 참조변수 필요X
			pstmt.setInt(1, reqvo.getCategory());
			pstmt.setInt(2, reqvo.getRno());
			pstmt.setString(3, reqvo.getReqMessage());
			pstmt.setString(4, reqvo.getEcode());
			pstmt.setInt(5, reqvo.getCategory());
			pstmt.setInt(6, reqvo.getNum());

			int result = pstmt.executeUpdate();

			if (result == 1) { // 정상적으로 회원정보 수정 성공시 true 반환
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBConn.close(pstmt);
		}
		return false;
	} // END update()

	public boolean delete(int category, int num) {
//	COM_017	카테고리별 고객요청사항 삭제 화면	
//	고객요청사항 고유번호를 입력받은 후, 고객요청목록의 고객 요청 사항을 삭제한다.	
		try {
			query = " DELETE HotelReq WHERE category=? and num =? ";
			pstmt = DBConn.getConnection().prepareStatement(query); // getConnection이 static이므로 참조변수 필요X
			pstmt.setInt(1, category);
			pstmt.setInt(2, num);

			int result = pstmt.executeUpdate();

			if (result == 1) { // 정상적으로 회원가입 성공시 true 반환
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBConn.close(pstmt);
		}
		return false; // 정상적으로 회원삭제 성공시 true 반환
		// 그렇지 않으면 false 반환

	} // END delete()

}