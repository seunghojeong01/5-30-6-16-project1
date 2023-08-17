package hotel.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;

import hotel.jdbc.DBConn;
import hotel.vo.HotelNoticeVO;

//공지사항에 대한 정보를 실제적으로 생성, 조회, 수정, 삭제 하는 액세스 클래스
public class HotelNoticeDAO {

	private String query;
	private PreparedStatement pstmt;
	private ResultSet rs;

	List<HotelNoticeVO> list;
	HotelNoticeVO noticevo = new HotelNoticeVO();

	public List<HotelNoticeVO> select() {
//	EMP_003	공지사항 전체목록 화면 (직원용)
//	ADM_012	공지사항 전체목록 화면(관리자용)
//	공지사항 전체 목록을 화면에 출력받는다.

		HotelNoticeVO noticevo = null; // 지역변수 초기화

		// 해당레코드를 객체에 저장
		List<HotelNoticeVO> noticevoList = new ArrayList<>(); // 데이터 값이 null 일 수 있음.

		try {
			// select 쿼리
			query = " SELECT HotelNotice.*, HotelEmp.ename "
					+ " FROM HotelNotice "
					+ " JOIN HotelEmp ON HotelNotice.ecode = HotelEmp.ecode "
					+ " ORDER BY num DESC ";
			pstmt = DBConn.getConnection().prepareStatement(query);
			
			rs = pstmt.executeQuery(); //쿼리 결과 저장
			
			//조회된 레코드가 있다면 true 반환 후 new객체 생성, 없다면 false 반환 후 null값으로 넘김(목록 조회시 무조건 거쳐가야함)
			//레코드가 1개면 If, 여러개면 while문
			while(rs.next()==true) { 
				noticevo = new HotelNoticeVO(); //VO 객체 생성
				noticevo.setNum(rs.getInt("num"));
				noticevo.setTitle(rs.getString("title"));
				noticevo.setEname(rs.getString("ename"));
				noticevo.setNoticeDate(rs.getDate("noticeDate"));			
				
				noticevoList.add(noticevo); //List 객체에 추가
				
			}//while 구문 end

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBConn.close(pstmt, rs); // pstmt와 rs를 동시에 close 하기 위함
		}

		return noticevoList;
	}

	public HotelNoticeVO select(int num) { // DB쿼리 짤 때 sessioncode값 불러오기
//	COM_012	공지사항 상세보기 화면 (직원용)
//	ADM_013	공지사항 상세보기 화면 (관리자용)
//	공지사항의 공지번호(PK)를 입력 받아 글의 상세 내용을 출력하도록 한다
		
		HotelNoticeVO noticevo = null; //지역변수는 초기화 필요
		
		try {
			// select 쿼리 넣기
			query = " SELECT HotelNotice.*, HotelEmp.ename "
					+ " FROM HotelNotice "
					+ " JOIN HotelEmp ON HotelNotice.ecode = HotelEmp.ecode "
					+ " WHERE num =? ";
			pstmt = DBConn.getConnection().prepareStatement(query); //2. getconnection으로 받아온다
			pstmt.setInt(1, num); //	3. mno 바인딩해서 가져온다.		
			rs = pstmt.executeQuery(); //4. 쿼리 결과 저장

			if (rs.next()) { 			
				noticevo = new HotelNoticeVO(); //VO 객체 생성
				noticevo.setTitle(rs.getString("title"));
				noticevo.setContent(rs.getString("content"));
				noticevo.setEname(rs.getString("ename"));
				noticevo.setNoticeDate(rs.getDate("noticeDate"));			
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBConn.close(pstmt, rs);
		}

		return noticevo;
	}//END select()

	public boolean insert(HotelNoticeVO noticevo) {
//	ADM_014	공지사항 등록 화면
//	 제목과 내용을 입력하여 게시글을 등록한다.
		try {
			// insert 쿼리 넣기
			
			query = " INSERT INTO HotelNotice (num, title, content, ecode, noticeDate) "
					+ " VALUES((SELECT COALESCE(MAX(num) + 1, 1) FROM HotelNotice), ?, ?, ?, sysdate) ";
			
			pstmt = DBConn.getConnection().prepareStatement(query); // getConnection이 static이므로 참조변수 필요X
			pstmt.setString(1, noticevo.getTitle());
			pstmt.setString(2, noticevo.getContent());
			pstmt.setString(3, noticevo.getEcode());
			
			int result = pstmt.executeUpdate();

			if (result == 1) { // 정상적으로 공지사항 등록 실행성공시 true 반환
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBConn.close(pstmt);
		}
		return false;// 그렇지 않으면 false 반환
						
	} //END insert()

	public boolean update(HotelNoticeVO noticevo) {
//	ADM_015	공지사항 수정화면
//	공지사항의 제목과 내용을 수정할 수 있도록 한다.
		try {
			query = " UPDATE HotelNotice SET title=?, content=?, ecode=?, noticeDate=sysdate WHERE num=? ";

			pstmt = DBConn.getConnection().prepareStatement(query); // getConnection이 static이므로 참조변수 필요X
			pstmt.setString(1, noticevo.getTitle());
			pstmt.setString(2, noticevo.getContent());
			pstmt.setString(3, noticevo.getEcode());
			pstmt.setInt(4, noticevo.getNum());
			
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
	} //END update()

	public boolean delete(int num) {
//	 ADM_016 공지사항 삭제화면 
//	 공지사항의 게시물을 삭제할 수 있도록 한다.
		try {
			query = " DELETE HotelNotice WHERE num = ? ";
			pstmt = DBConn.getConnection().prepareStatement(query); // getConnection이 static이므로 참조변수 필요X
			pstmt.setInt(1, num);
			
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

	} //END delete()

}