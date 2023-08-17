package hotel.main;

import java.util.List;
import java.util.Scanner;

import hotel.dao.HotelFoodDAO;
import hotel.vo.HotelFoodVO;

public class HotelFoodMain {
   //필드
   private Scanner sc;
   private HotelFoodVO fvo;
   private HotelFoodDAO fdao;
   private int input;
   
   //생성자 - 호텔메인에서 갖다 쓸 것
   public HotelFoodMain() {
      sc = new Scanner(System.in);
      fdao = new HotelFoodDAO();
   }
   
   
   
   
   //메인메서드 한번 실행해볼게? ---------------------------테스트용 메인메서드
   public static void main(String[] args) {
      HotelFoodMain hotelFoodMain = new HotelFoodMain();
      hotelFoodMain.menu();
   }
  
   
   
   //'음식메뉴관리' 메뉴
   public void menu() {
      
      //프로그램 메인
      HotelMain hotelMain = new HotelMain();
      
      do {
    	  System.out.println("==================================================================================================================================================");
         System.out.println("                                                                 음식 메뉴");
         System.out.println("==================================================================================================================================================");
         
         System.out.println(" 1. 음식 메뉴 추가 ");
         System.out.println(" 2. 카테고리별 음식메뉴 조회");
         System.out.println(" 3. 뒤로가기");
         System.out.println();
         System.out.print("   >> 선택 : ");
         input = sc.nextInt();
         
         switch (input) {
         case 1: add(); break;
         case 2: viewCategory(); break;
         case 3: hotelMain.menuadmin(); break;
         default: System.out.println("   >> 잘못된 입력입니다. 1~3번 메뉴를 선택하세요.");
         System.out.println();
         }//switch end
      }while(input < 1 || input > 3);
   }//menu end
   


   //음식 메뉴 추가 C
   private void add() {
      HotelFoodVO fvo = new HotelFoodVO();
      
      System.out.println("==================================================================================================================================================");
      System.out.println("                                                          음식 메뉴 추가");
      System.out.println("==================================================================================================================================================");
      
      System.out.println("(카테고리) 1.한식   | 2.양식   | 3.조식   | 4.음료");
      System.out.println();
      System.out.print("   >> 추가할 메뉴의 카테고리 : ");
      fvo.setFoodCategory(sc.nextInt());
      System.out.print("   >> 메뉴명 : ");
      sc.nextLine();      // 버퍼 처리
      fvo.setFoodName(sc.nextLine());
      System.out.print("   >> 해당 메뉴의 가격 : ");
      fvo.setPrice(sc.nextInt());
      
      //메뉴번호(PK)는 시퀀스로 처리할 거라서 입력받지 않음

      if(fdao.insert(fvo)) {      // true면,
         System.out.println("   >> 해당 메뉴가 추가되었습니다.");
      }else{
         System.out.println("   >> 메뉴 추가에 실패했습니다.");
      };
      
      menu();   // 메뉴 추가 후, '음식 메뉴 관리' 메뉴로 돌아감
      
   }//add end
   
   

   //스캐너로 카테고리 입력받아서, 해당 메뉴 목록 조회 (복수조회) R
   public void viewCategory() {      // HotelFoodOrderMain에서도 호출하려고 public 사용
      
      int category;
      
      System.out.println("==================================================================================================================================================");
      System.out.println("                                                          카테고리별 메뉴 조회");
      System.out.println("==================================================================================================================================================");
       do {
      System.out.println("(카테고리) 1.한식   | 2.양식   | 3.조식   | 4.음료");
      System.out.println();
      System.out.print("   >> 조회하려는 카테고리 번호 : ");
      category = sc.nextInt();
      }while(!(category==1 || category==2 || category==3 || category==4));

      
      //category번호에 맞는 fvolist 뽑아오기
      List<HotelFoodVO> fvolist = fdao.selectmenu(category);
      
      //출력할 때, 사용자가 무슨 카테고리를 선택했는지 보여주기 위해서
      String output = "";
      switch (category) {
         case 1: output = "한식"; break;
         case 2: output = "양식"; break;
         case 3: output = "조식"; break;
         case 4: output = "음료"; break;
      }
      
      if(fvolist.size()>0) {         //list에 vo가 있다면
         System.out.println();
         System.out.println("==================================================================================================================================================");
         System.out.println("                                                          ["+ output +"]의 메뉴 조회");
         System.out.println("==================================================================================================================================================");
         
         //list 속 메뉴 차례차례 뽑기
         for(int i=0; i<fvolist.size(); i++) {
            System.out.println(fvolist.get(i).getFoodNum() + ". " + fvolist.get(i).getFoodName() + " : " + fvolist.get(i).getPrice() + "원");   //리스트 속 HotelFoodVO를 꺼내라
         }//for end
         
         // 카테고리별 메뉴를 출력했으니, 메뉴로 돌아갈 건지 수정 및 삭제를 할 것인지 묻기
         System.out.println();
         System.out.println("▶▷▶▷▶▷");
         System.out.println(" 1. 음식 메뉴 수정");
         System.out.println(" 2. 음식 메뉴 삭제");
         System.out.println(" 3. 음식 메뉴 관리 화면으로 이동");
         System.out.println();
         System.out.print("   >> 선택 : ");
         input = sc.nextInt();
         
         switch (input) {
         case 1: modify(); break;
         case 2: remove(); break;
         case 3: menu(); break;
         default: System.out.println(" >> 잘못된 입력입니다.");
            menu();      //메뉴 말고, 입력을 다시 받을 수 있도록 do while 사용 시도해보기
            break;
         }
      }else {
         System.out.println("   >> 해당 카테고리에 메뉴가 존재하지 않습니다.");
         System.out.println();
         menu();
      }
      
      
      
   }// viewCategory end
   
   

   
   //음식 메뉴 수정 U
   //(스캐너로 메뉴번호 입력받아서 단 건 조회 후, 수정)
   public void modify() {
	   System.out.println();
	   System.out.println("(카테고리) 1.한식   | 2.양식   | 3.조식   | 4.음료");
      System.out.print("   >> 수정하려는 메뉴의 카테고리 번호 : ");
      //viewOne에서 메뉴번호 입력받고, fvo 출력 후 vo객체 담아옴
      
      HotelFoodVO fvo = viewOne();      
      
      if(fvo!=null) {
      
      // 카테고리별 메뉴 조회를 타고 메뉴 수정에 들어왔으므로, 카테고리는 수정되지 않는 걸로 가정
      System.out.print("   >> 변경할 메뉴이름: ");
      sc.nextLine();
      fvo.setFoodName(sc.nextLine());
      System.out.print("   >> 변경할 가격: ");
      fvo.setPrice(sc.nextInt());
      
      if(fdao.update(fvo)) {
         System.out.println("   >> 메뉴가 성공적으로 수정되었습니다.");
      }else {
         System.out.println("   >> 메뉴수정에 실패하였습니다.");
      }
      menu();      // 수정 후, '음식메뉴관리' 메뉴로 이동
      
      }else {      // 메뉴번호에 맞는 fvo가 없다면
         System.out.println("   >> 해당 메뉴가 존재하지 않습니다.");
         menu();
      }
   }//modify end 
   
   
   
   //음식 메뉴 삭제 D
   //(스캐너로 메뉴번호 입력받아서 단 건 조회 후, 삭제)
	public void remove() {
		System.out.println("   >> 삭제하려는 메뉴의 카테고리 번호 : ");
		// viewOne에서 메뉴번호 입력받고, fvo 출력 후 vo객체 담아옴
		HotelFoodVO fvo = viewOne();

		// 여기서 삭제할 건지 말 건지 한번 체크

		if (fvo != null) { // 메뉴번호에 맞는 fvo가 있다면

			System.out.print("   >> 해당 요청사항을 삭제하시겠습니까?(Y/N): ");
			String yorn = sc.next();

			if (yorn.equalsIgnoreCase("Y")) {

				boolean result = fdao.delete(fvo.getFoodCategory(), fvo.getFoodNum());

				if (result) {
					System.out.println("   >> 성공적으로 삭제되었습니다.");
				} else {
					System.out.println("   >> 삭제에 실패하였습니다.");
				}
			} else { // Y가 아니라면
				System.out.println("   >> 삭제를 취소합니다.");
			}
			menu(); // 삭제 후, '음식메뉴관리' 메뉴로 이동
		} else { // 메뉴번호에 맞는 fvo가 있다면
			System.out.println("   >> 해당 메뉴가 존재하지 않습니다.");
			menu();
		}
	}// remove end
   
   
   
   //스캐너로 메뉴번호 입력받아서 단 건 조회 R   -> 수정 및 삭제에 쓰임
   private HotelFoodVO viewOne() {
      int menuCategory = sc.nextInt();
      System.out.print("   >> 메뉴 번호 : ");
      int menuNum = sc.nextInt();
      HotelFoodVO fvo = fdao.selectfvo(menuCategory, menuNum);
      
      if(fvo != null) {      // 메뉴번호에 해당하는 메뉴가 있을 경우 출력
         System.out.println(fvo.getFoodNum() + ". " + fvo.getFoodName() + " : " + fvo.getPrice() + "원");
      }
      
      return fvo;
   }//viewOne end
   
   
   
}