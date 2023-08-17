package hotel.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import hotel.jdbc.DBConn;
import hotel.vo.HotelFoodOrderVO;

//음식 주문 DB
public class HotelFoodOrderDAO { 
   private String query; 
   private Statement stmt;
   private PreparedStatement pstmt;   // 매개변수(ex.아이디, 이름)가 있는 경우에는 pstmt를 사용하는 것이 적합
   private ResultSet rs;   
   
   HotelFoodOrderVO ovo;
   HotelFoodDAO fdao = new HotelFoodDAO();
   List<HotelFoodOrderVO> ovolist;
   
   
/*
   //이거 뭐였지....?
   //카테고리별 음식목록 화면 출력 R - HotelFoodDAO의 메서드와 동일
   public List<HotelFoodVO> selectmenu(int category){
      fvolist = fdao.selectmenu(category);
      return fvolist;
   }
*/
   
   
   //main에서 전달받은 ovo에는 ordercode, 객실호수, 음식카테고리번호, 음식메뉴번호, 수량이 담겨있음
   public boolean insert(HotelFoodOrderVO ovo) {
      
      try {
         // insert 쿼리문
         query = "INSERT INTO HotelFoodOrder(foodOrderNo, rno, foodCategory, foodNum, foodName, foodAmount, price, ecode, foodDate)"
               + " VALUES (FOODORDER_SEQ.NEXTVAL,?,?,?,(SELECT foodName FROM HotelFood WHERE foodCategory=? and foodNum=?), ?, "
               + " (? * (SELECT price FROM HotelFood WHERE foodCategory=? and foodNum=?)), ? ,SYSDATE)";
         
         // DBConn에 싱글톤패턴으로 만들어둔 커넥션 사용
         // 커넥션을 변수에 담아서 쓸 것인지, 그 때 그 때 getConnection을 호출해서 사용할지는 자유
         pstmt = DBConn.getConnection().prepareStatement(query);      //prepareStatement는 쿼리를 미리 준비해뒀으니까 매개변수로 query받음
         pstmt.setInt(1, ovo.getRno());
         pstmt.setInt(2, ovo.getFoodCategory());
         pstmt.setInt(3, ovo.getFoodNum());
         pstmt.setInt(4, ovo.getFoodCategory());
         pstmt.setInt(5, ovo.getFoodNum());      // foodNum을 활용해서 foodName을 조회하는 과정
         pstmt.setInt(6, ovo.getFoodAmount());
         pstmt.setInt(7, ovo.getFoodAmount());
         pstmt.setInt(8, ovo.getFoodCategory());   //foodAmount 활용해서 price 조회하는 과정
         pstmt.setInt(9, ovo.getFoodNum());   //foodNum 활용해서 price 조회하는 과정
         pstmt.setString(10, ovo.getEcode());
         
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
   
   
   
   //음식 주문 내역 조회 (복수조회) R
   //selectAll에서 주문접수자는 join을 통해서 가져와야 함
   //조회할 내용 : 주문번호, 주문객실, 주문메뉴이름, 주문수량, 주문가격, 주문날짜, 주문접수자
   
   public List<HotelFoodOrderVO> selectAll(String ordercode){
      ovolist = new ArrayList<HotelFoodOrderVO>();   // 음식주문객체를 넣을 리스트
      
      try {
         // 전체조회 쿼리
         query = "SELECT o.foodOrderNo, o.rno, o.foodCategory, o.foodNum, o.foodName, o.foodAmount, o.price, o.ecode, o.foodDate, e.ename"
         	   + " FROM HotelFoodOrder o INNER JOIN HotelEmp e On o.ecode = e.ecode order by o.foodOrderNo asc";
         pstmt = DBConn.getConnection().prepareStatement(query);      // 바인딩할 게 없어서 Statement 사용하면 더 편함
         
         rs = pstmt.executeQuery();      // select이므로 ResultSet 객체 필요
      
         while(rs.next()) {            // rs에 담긴 레코드들이 존재한다면
            ovo = new HotelFoodOrderVO();      // rs가 있을 때에만 fvo 객체 생성
            ovo.setFoodOrderNo(rs.getInt("foodOrderNo"));
            ovo.setRno(rs.getInt("rno"));
            ovo.setFoodCategory(rs.getInt("foodCategory"));
            ovo.setFoodNum(rs.getInt("foodNum"));
            ovo.setFoodName(rs.getString("foodName"));
            ovo.setFoodAmount(rs.getInt("foodAmount"));
            ovo.setPrice(rs.getInt("price"));
            ovo.setEcode(rs.getString("ecode"));
            ovo.setFoodDate(rs.getDate("foodDate"));
            ovo.setEName(rs.getString("ename"));
            
            ovolist.add(ovo);      // List객체에 추가
         }
      } catch (SQLException e) {
         e.printStackTrace();
      }finally {
         DBConn.close(pstmt, rs);
      }
      
      return ovolist;
   }
   
   
   
   
   //음식 주문 개별조회 (단 건 조회) R
   //스캐너로 주문번호 입력받기 
   //주문메뉴이름, 주문접수자는 join을 통해서 가져와야 함
   public HotelFoodOrderVO selectOne(int orderNum) {

      // 준비물) 1. 셀렉트쿼리문 2.stmt 혹은 pstmt 객체 3. 바인딩 4. 쿼리 실행

      try {
         // select 쿼리문
         query = "SELECT o.foodOrderNo, o.rno, o.foodCategory, o.foodNum, o.foodName, o.foodAmount, o.price, o.ecode, o.foodDate, e.ename"
        	   + " FROM HotelFoodOrder o INNER JOIN HotelEmp e On o.ecode = e.ecode WHERE o.foodOrderNo=?";

         pstmt = DBConn.getConnection().prepareStatement(query);
         pstmt.setInt(1, orderNum); // 물음표 바인딩

         rs = pstmt.executeQuery(); // 쿼리실행 -> 받는 값이 있는 read이므로 executeQuery();

         // 레코드의 갯수만큼 반복할 거라서, rs 속에 여러 개가 있을 거라면 while문 사용/ 지금은 레코드 하나라서 if문 사용
         if (rs.next()) { // 조회된 레코드가 있다면 HotelFoodOrderVO 객체를 생성하여 해당 레코드값을 저장
            ovo = new HotelFoodOrderVO(); // rs가 있을 때에만 mvo 생성
            ovo.setFoodOrderNo(rs.getInt("foodOrderNo"));
            ovo.setRno(rs.getInt("rno"));
            ovo.setFoodCategory(rs.getInt("foodCategory"));
            ovo.setFoodNum(rs.getInt("foodNum"));
            ovo.setFoodName(rs.getString("foodName"));
            ovo.setFoodAmount(rs.getInt("foodAmount"));
            ovo.setPrice(rs.getInt("price"));
            ovo.setEcode(rs.getString("ecode"));
            ovo.setFoodDate(rs.getDate("foodDate"));
            ovo.setEName(rs.getString("ename"));
         }

      } catch (SQLException e) {
         e.printStackTrace();
      } finally {
         DBConn.close(pstmt, rs); // pstmt와 rs를 둘 다 닫아야 하므로 매개변수 두 개를 받는 close 메서드
      }
      return ovo; // 객체 반환
   }//selectOne end
   
   
   
   //음식 주문 내역 수정  (단건조회 이후) U
   //주문 내역 수정 시, 객실 호수, 메뉴카테고리, 메뉴번호, 주문수량 입력받을 것  (+ordercode)
   public boolean update (HotelFoodOrderVO ovo) {
   
      try {         
         query = "UPDATE HotelFoodOrder SET rno=?, foodCategory=?, foodNum=?, foodName=(SELECT foodName FROM HotelFood WHERE foodCategory=? and foodNum=?) , "
               + " foodAmount=?, price=(? * (SELECT price FROM HotelFood WHERE  foodCategory=? and foodNum=?)), ecode=? "
               + " WHERE foodOrderNo=?";
         
         // DBConn에 싱글톤패턴으로 만들어둔 커넥션 사용
         // 커넥션을 변수에 담아서 쓸 것인지, 그 때 그 때 getConnection을 호출해서 사용할지는 자유
         pstmt = DBConn.getConnection().prepareStatement(query);    //prepareStatement는 쿼리를 미리 준비해뒀으니까 매개변수로 query받음
         pstmt.setInt(1, ovo.getRno());
         pstmt.setInt(2, ovo.getFoodCategory());
         pstmt.setInt(3, ovo.getFoodNum());
         pstmt.setInt(4, ovo.getFoodCategory());
         pstmt.setInt(5, ovo.getFoodNum());
         pstmt.setInt(6, ovo.getFoodAmount());
         pstmt.setInt(7, ovo.getFoodAmount());
         pstmt.setInt(8, ovo.getFoodCategory());
         pstmt.setInt(9, ovo.getFoodNum());
         pstmt.setString(10, ovo.getEcode());
         pstmt.setInt(11, ovo.getFoodOrderNo());
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
      
   }//update end
   
   
   
   //음식 주문 내역 삭제 (단건조회 이후) D
   public boolean delete (int orderCategory, int orderNum) {
      try {
         
         // delete 쿼리문
         query = "DELETE FROM HotelFoodOrder WHERE foodCategory=? and foodOrderNo=?";
         
         // DBConn에 싱글톤패턴으로 만들어둔 커넥션 사용
         // 커넥션을 변수에 담아서 쓸 것인지, 그 때 그 때 getConnection을 호출해서 사용할지는 자유
         pstmt = DBConn.getConnection().prepareStatement(query);      // prepareStatement는 쿼리를 미리 준비해뒀으니까 매개변수로 query받음
         
         pstmt.setInt(1, orderCategory);
         pstmt.setInt(2, orderNum);
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
   }//delete end
   

}//class end