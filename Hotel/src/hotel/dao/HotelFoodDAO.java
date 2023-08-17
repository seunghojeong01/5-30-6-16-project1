package hotel.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import hotel.jdbc.DBConn;
import hotel.vo.HotelFoodVO;

// 음식 메뉴 DB
public class HotelFoodDAO {
   
   
   private String query;
   private Statement stmt;
   private PreparedStatement pstmt;   // 매개변수(ex.아이디, 이름)가 있는 경우에는 pstmt를 사용하는 것이 적합
   private ResultSet rs;
   
   // List<HotelFoodVO> fvolist;
   HotelFoodVO fvo;
   List<HotelFoodVO> fvolist;
   
   
   //음식 메뉴 추가 C
   public boolean insert(HotelFoodVO fvo) {
      //매개변수로 넘어온 fvo는 메뉴번호가 없는 상태 - 메뉴번호(PK)는 시퀀스로 처리
      
      try {
         // insert 쿼리문
          query = "INSERT INTO HotelFood(foodNum, foodCategory, foodName, price) VALUES ((SELECT NVL(MAX(foodnum), 0)+1 FROM hotelfood WHERE foodcategory = ?), ? , ? , ? )";

            // DBConn에 싱글톤패턴으로 만들어둔 커넥션 사용
            // 커넥션을 변수에 담아서 쓸 것인지, 그 때 그 때 getConnection을 호출해서 사용할지는 자유
            pstmt = DBConn.getConnection().prepareStatement(query);      //prepareStatement는 쿼리를 미리 준비해뒀으니까 매개변수로 query받음
            pstmt.setInt(1, fvo.getFoodCategory());
            pstmt.setInt(2, fvo.getFoodCategory());
            pstmt.setString(3, fvo.getFoodName());
            pstmt.setInt(4, fvo.getPrice());
         int execute = pstmt.executeUpdate();   // executeUpdate가 잘 되면, 성공 실행 건수 1이 반환됨
         if(execute==1) {
            return true;
         }
      } catch (SQLException e) {
         e.printStackTrace();
      }finally {
         DBConn.close(pstmt);
      }
      return false;
   }
   
   
   //음식 메뉴 전체 조회
   public List<HotelFoodVO> selectAll(){
      
      List<HotelFoodVO> fvolist = new ArrayList<HotelFoodVO>();   // 음식메뉴객체를 넣을 리스트
      HotelFoodVO fvo = null;
      
      try {
         // 전체조회 쿼리
         query = "SELECT * FROM HotelFood order by foodCategory asc, foodNum asc";
         pstmt = DBConn.getConnection().prepareStatement(query);      // 바인딩할 게 없어서 Statement 사용하면 더 편함
         
         rs = pstmt.executeQuery();      // select이므로 ResultSet 객체 필요
      
         while(rs.next()) {            // rs에 담긴 레코드들이 존재한다면
            fvo = new HotelFoodVO();      // rs가 있을 때에만 fvo 객체 생성
            fvo.setFoodNum(rs.getInt("foodNum"));
            fvo.setFoodCategory(rs.getInt("foodCategory"));
            fvo.setFoodName(rs.getString("foodname"));
            fvo.setPrice(rs.getInt("price"));   // 테이블의 컬럼명과 정확히 일치해야 함
            
            fvolist.add(fvo);      // List객체에 추가
         }
      } catch (SQLException e) {
         e.printStackTrace();
      }finally {
         DBConn.close(pstmt, rs);
      }
      
      return fvolist;
   }
   
   
   
   //메인에서 전달받은 카테고리번호에 맞는 맞는 해당 메뉴 목록 조회 (복수조회) R
   //쿼리문 작성 시, where조건으로 category번호에 제약을 줘야 함
   public List<HotelFoodVO> selectmenu(int category){
      fvolist = new ArrayList<HotelFoodVO>();   // 음식메뉴객체를 넣을 리스트 준비
      
      
      
      try {
         // 전체조회 쿼리

         rs = null;
         
         query = "SELECT * FROM HotelFood WHERE foodCategory=? ORDER BY foodnum ASC";
         pstmt = DBConn.getConnection().prepareStatement(query);      // 바인딩할 게 없어서 Statement 사용하면 더 편함
         pstmt.setInt(1, category);
         
         rs = pstmt.executeQuery();      // select이므로 ResultSet 객체 필요
      
         while(rs.next()) {            // rs에 레코드가 존재한다면
            
            fvo = new HotelFoodVO();      // rs가 있을 때에만 fvo 객체 생성
            fvo.setFoodNum(rs.getInt("foodNum"));
            fvo.setFoodCategory(rs.getInt("foodCategory"));
            fvo.setFoodName(rs.getString("foodName"));
            fvo.setPrice(rs.getInt("price"));   // 테이블의 컬럼명과 정확히 일치해야 함
            
            fvolist.add(fvo);      // List객체에 추가
         }
      } catch (SQLException e) {
         e.printStackTrace();
      }finally {
         DBConn.close(pstmt, rs);
      }
      
      return fvolist;
   }
   
   
   
   
   
   //메인에서 메뉴번호 전달받아서 단 건 조회 R
   public HotelFoodVO selectfvo(int menuCategory, int menuNum) {
      HotelFoodVO fvo = null;   // HotelFoodVO의 객체 메모리 미리 준비
      
      // 준비물) 1. 셀렉트쿼리문  2.stmt 혹은 pstmt 객체 3. 바인딩  4. 쿼리 실행      
      
      try {
         // select 쿼리문
         query = "SELECT * FROM HotelFood WHERE foodCategory=? and foodNum=?";
         
         pstmt = DBConn.getConnection().prepareStatement(query);
         pstmt.setInt(1, menuCategory);   // 물음표 바인딩
         pstmt.setInt(2, menuNum);
         
         rs = pstmt.executeQuery();   // 쿼리실행 -> 받는 값이 있는 read이므로 executeQuery();
         
         
         // 레코드의 갯수만큼 반복할 거라서, rs 속에 여러 개가 있을 거라면 while문 사용/ 지금은 레코드 하나라서 if문 사용
         if(rs.next()) {      // 조회된 레코드가 있다면 MemberVO 객체를 생성하여 해당 레코드값을 저장
            fvo = new HotelFoodVO();      // rs가 있을 때에만 mvo 생성
            fvo.setFoodNum(rs.getInt("foodNum"));
            fvo.setFoodCategory(rs.getInt("foodCategory"));
            fvo.setFoodName(rs.getString("foodname"));
            fvo.setPrice(rs.getInt("price"));   // 테이블의 컬럼명과 정확히 일치해야 함
         }
         
      } catch (SQLException e) {
         e.printStackTrace();
      }finally {
         DBConn.close(pstmt, rs);   // pstmt와 rs를 둘 다 닫아야 하므로 매개변수 두 개를 받는 close 메서드
      }      
      return fvo;   // 객체 반환
      
   }//selectfvo end
   
   
   //음식 메뉴 수정 U
   //(스캐너로 메뉴번호 입력받아서 단 건 조회 후, 수정)
   public boolean update(HotelFoodVO fvo) {
      
      try {
         query = "UPDATE HotelFood SET foodName=?, price=? WHERE foodCategory=? and  foodNum=?";
         
         // DBConn에 싱글톤패턴으로 만들어둔 커넥션 사용
         // 커넥션을 변수에 담아서 쓸 것인지, 그 때 그 때 getConnection을 호출해서 사용할지는 자유
         pstmt = DBConn.getConnection().prepareStatement(query);    //prepareStatement는 쿼리를 미리 준비해뒀으니까 매개변수로 query받음
         pstmt.setString(1, fvo.getFoodName());
         pstmt.setInt(2, fvo.getPrice());
         pstmt.setInt(3, fvo.getFoodCategory());
         pstmt.setInt(4, fvo.getFoodNum());
         
         int execute = pstmt.executeUpdate();   // executeUpdate가 잘 되면, 성공 실행 건수 1이 반환됨
         if(execute==1) {
            return true;
         }
         
      } catch (SQLException e) {
         e.printStackTrace();
        }finally {
         DBConn.close(pstmt);
        }   
      
      return false;
   }
   
   
   
   //음식 메뉴 삭제 D
   //(스캐너로 메뉴번호 입력받아서 단 건 조회 후, 삭제)
   public boolean delete(int menuCategory, int menuNum) {
      
      try {
         // delete 쿼리문
         query = "DELETE FROM HotelFood WHERE foodCategory=? and foodNum=?";
         
         // DBConn에 싱글톤패턴으로 만들어둔 커넥션 사용
         // 커넥션을 변수에 담아서 쓸 것인지, 그 때 그 때 getConnection을 호출해서 사용할지는 자유
         pstmt = DBConn.getConnection().prepareStatement(query);      // prepareStatement는 쿼리를 미리 준비해뒀으니까 매개변수로 query받음
         
         pstmt.setInt(1, menuCategory);
         pstmt.setInt(2, menuNum);

         int execute = pstmt.executeUpdate(); // executeUpdate가 잘 되면, 성공 실행 건수 1이 반환됨
         if (execute == 1) {
            return true;
         }
      }catch (SQLException e) {
         e.printStackTrace();
      }finally {
         DBConn.close(pstmt);
      } 
      
      return false;
   }
   
   

}