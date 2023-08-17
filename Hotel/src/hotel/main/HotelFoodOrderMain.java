package hotel.main;

import java.util.List;
import java.util.Scanner;

import hotel.dao.HotelFoodDAO;
import hotel.dao.HotelFoodOrderDAO;
import hotel.vo.HotelFoodOrderVO;
import hotel.vo.HotelFoodVO;

public class HotelFoodOrderMain {
   
//   //프로그램 메인
//   HotelMain hotelMain = new HotelMain();

   //필드
   private Scanner sc;
   private HotelFoodOrderVO ovo;
   private HotelFoodDAO fdao;
   private HotelFoodOrderDAO odao;
   private int input;
   private boolean check;
   private List<HotelFoodOrderVO> ovolist;

   
   public static String orderjob;      // 관리자메뉴 or 직원메뉴로의 판별을 위함 : sessionjob
   public static String ordercode;    // sessionid = sessioncode
      
   
   // 생성자 - 호텔메인에서 갖다 쓸 것
   public HotelFoodOrderMain() {
      sc = new Scanner(System.in);
      fdao = new HotelFoodDAO();
      odao = new HotelFoodOrderDAO();
   }
   
 
   
   
   //'음식주문관리'메뉴 : hotelMain호출용
   public void menu(String sessionjob, String sessioncode) {
	   HotelMain hmain=new HotelMain();
      orderjob = sessionjob;      //관리자메뉴로 갈지, 직원메뉴로 갈지
      ordercode = sessioncode;   //누가 주문을 받았는지
      
      
      System.out.println("==================================================================================================================================================");
      System.out.println("                                                                   음식 주문");
      System.out.println("==================================================================================================================================================");
      System.out.println("   1. 음식 주문 추가 ");
      System.out.println("   2. 음식 주문 목록 조회");
      // 승호님 메서드명 반영해야 함==================================================
      if(orderjob.equals("ADM")) {      // 승호님 메서드명 반영해야 함
      System.out.println("   3. 뒤로가기");
      System.out.println();
      System.out.print("   >> 선택 : ");
      input = sc.nextInt();
      
      switch (input) {
      case 1: add(); break;
      case 2: viewAll(); break;
      case 3: hmain.menuadmin(); break;
      default: System.out.println("   >> 잘못된 입력입니다. 1~3번 메뉴를 선택하세요.");
             menu(orderjob, ordercode);   //잘못 입력했으니까 다시 입력하도록
         break;
      }//switch end
      }else if(orderjob.equals("EMP")) {
         System.out.println("   3. 뒤로가기");
         System.out.println();
         System.out.print("   >> 선택 : ");
         input = sc.nextInt();
         
         switch (input) {
         case 1: add(); break;
         case 2: viewAll(); break;
         case 3: hmain.menuemp(); break;
         default: System.out.println("   >> 잘못된 입력입니다. 1~3번 메뉴를 선택하세요.");
                menu(orderjob, ordercode);   //잘못 입력했으니까 다시 입력하도록
            break;
         }//switch end
         
      }// else if end
      
   }//menu end

   //음식 주문 추가  -> 스캐너로 입력받기 C s
   //객실호수,카테고리,메뉴 입력받기
   //add: 객실, 카테고리까지만 입력받는 메서드
   private void add() {
      
		System.out.println("==================================================================================================================================================");
		System.out.println("                                                                  주문 추가");
		System.out.println("==================================================================================================================================================");
  
      System.out.print("   >> 객실 호수 (ex.1001) : ");
      int rno = sc.nextInt();
      
      System.out.println();
      System.out.println("(카테고리) 1.한식   | 2.양식   | 3.조식   | 4.음료   | 5. 음식 주문 메뉴로 이동 (이전화면)");
      System.out.println();
      System.out.print("   >> 카테고리 선택 : ");
      int foodCategory = sc.nextInt();
      
      
      
      //이 이후로 객실 호수rno, 카테고리번호foodCategory, 현재 ordercode를 넘겨야 해
      
      switch (foodCategory) {
      case 1: case 2 : case 3 : case 4 : 
         order(ordercode, rno, foodCategory); break;      // 여기서 매개변수 input-> foodCategory
      case 5: menu(orderjob, ordercode); break;
      default: System.out.println("   >> 잘못된 입력입니다. 1~5번 메뉴를 선택하세요.");
             add();   //잘못 입력했으니까 다시 입력하도록
         break;
      }//switch end
      
   }//add end

   
   
   //카테고리별 메뉴 주문 화면
   //주문접수자코드, 객실호수, 음식카테고리 입력받음
   public void order(String ordercode, int rno, int foodCategory) {
 
		// 해당 카테고리에 있는 음식메뉴들을 담은 리스트 : fvolist
		List<HotelFoodVO> fvolist = fdao.selectmenu(foodCategory);
		
		 String output = "";
	      switch (foodCategory) {
	         case 1: output = "한식"; break;
	         case 2: output = "양식"; break;
	         case 3: output = "조식"; break;
	         case 4: output = "음료"; break;
	      }
		
		if(fvolist.size()>0) {	
			
			System.out.println("==================================================================================================================================================");
	         System.out.println("                                                         ["+ output +"]의 메뉴 조회");
	         System.out.println("==================================================================================================================================================");
	         
			
			// fvolist를 출력
			   for(int i=0; i<fvolist.size(); i++) {
		            System.out.println(fvolist.get(i).getFoodNum() + ". " + fvolist.get(i).getFoodName() + " : " + fvolist.get(i).getPrice() + "원");   //리스트 속 HotelFoodVO를 꺼내라
		         }//for end
			   
		// 방번호, 카테고리 받고, 메뉴번호, 주문수량 입력받기 
			      
			   System.out.print("   >> 주문할 메뉴 번호 : ");
			   int foodNum = sc.nextInt();
			   
			   System.out.print("   >> 주문 수량 : ");
			   int foodAmount = sc.nextInt();
			   
			   
			   ovo = new HotelFoodOrderVO(rno, foodCategory, foodNum, foodAmount, ordercode);
			   
			   boolean check = odao.insert(ovo);
			   
			   orderCheck(check);
		
			   
		}else {		// memolise.size 가 0이라면
			System.out.println("   >> 등록된 메뉴가 없습니다.");
		}
		menu(orderjob, ordercode);			// 전체메모 리스트 출력 후 메모메인으로 돌아감
      
   } //order end
      
   
   
  
   //음식 주문 완료 알림 화면
   public void orderCheck(boolean check) {
      if(check==true) {
         System.out.println("   >> 음식 주문이 완료되었습니다.");
      }else {
         System.out.println("   >> 음식 주문에 실패했습니다.");
      }
      
      // 메뉴 추가 (음식 추가 주문 or 목록 보기 : 화면 레이아웃 참조)
      System.out.println("==================================================================================================================================================");
      System.out.println("   1. 주문 추가하기 ");
      System.out.println("   2. 주문 종료하기");
      System.out.println();
      System.out.print("   >> 선택 : ");
      input = sc.nextInt();
      
      switch (input) {
      case 1: add(); break;
      case 2: menu(orderjob, ordercode); break;
      default: System.out.println("   >> 잘못된 입력입니다. 메뉴로 돌아갑니다.");
             menu(orderjob, ordercode);   //잘못 입력했으니까 다시 입력하도록
         break;
      }//switch end
   }//orderCheck end
   
   
   
   //음식 주문 내역 조회 (전체조회) R
   private void viewAll() {
      
	   System.out.println("==================================================================================================================================================");
		System.out.println("                                                             주문 내역 조회");
		System.out.println("==================================================================================================================================================");
 
	   System.out.println("     주문번호    |    주문객실    |\t\t\t주문메뉴이름\t\t|    주문수량    |    주문가격    |    주문날짜    |    주문접수자");
      
      //odao 통해서 출력 좌라락 하고나서는 
      ovolist = odao.selectAll(ordercode);      //selectAll에서 주문메뉴이름, 주문접수자는 join을 통해서 가져와야 해..?
      
      if(ovolist.size()>0) {         //list에 vo가 있다면
    	  System.out.println("==================================================================================================================================================");
         //list 속 메뉴 차례차례 뽑기
//         for(int i=0; i<ovolist.size(); i++) {
//            //어떻게
//            //출력할 건지
//            //양식 고민 
//            System.out.println("           "+ ovolist.get(i).getFoodOrderNo() +"\t\t\t"+ ovolist.get(i).getRno() +"   \t\t"+ ovolist.get(i).getFoodName() 
//                                 +"\t\t\t    "+ovolist.get(i).getFoodAmount() + "\t\t\t" + ovolist.get(i).getPrice() + "\t     " + ovolist.get(i).getFoodDate() 
//                                 +"\t\t  " +ovolist.get(i).getEName());
//         }//for end
         
         
         
         for (int i = 0; i < ovolist.size(); i++) {
              System.out.printf("%9d  %14d  %30s  %22d  %15d  %15s  %12s\n",
                      ovolist.get(i).getFoodOrderNo(), ovolist.get(i).getRno(),
                      ovolist.get(i).getFoodName(), ovolist.get(i).getFoodAmount(),
                      ovolist.get(i).getPrice(), ovolist.get(i).getFoodDate(),
                      ovolist.get(i).getEName());
          }
         
         
         // 위에서 주문내역 쫙 출력했으니, 메뉴로 돌아갈 건지 수정 및 삭제를 할 것인지 묻기
         System.out.println("==================================================================================================================================================");
         System.out.println("   1. 주문 수정");
         System.out.println("   2. 주문 삭제");
         System.out.println("   3. 음식 주문 관리 화면으로 이동");
         System.out.println("==================================================================================================================================================");
         System.out.print("   >> 선택 : ");
         input = sc.nextInt();

         switch (input) {
         case 1:
            modify(); break;
         case 2:
            remove();   break;
         case 3:
            menu(orderjob, ordercode); break;
         default:
            System.out.println("   >> 잘못된 입력입니다. 음식 주문 관리 화면으로 이동합니다.");
            menu(orderjob, ordercode); // 메뉴 말고, 입력을 다시 받을 수 있도록 do while 사용 시도해보기
            break;
         }
         
         
      }else {
         System.out.println("   >> 해당 카테고리에 메뉴가 존재하지 않습니다.");
         menu(orderjob, ordercode);
      }
      
   }//viewAll end 
   
   
   
   
   //음식 주문 개별조회 (단 건 조회) R
   //스캐너로 주문번호 입력받기 
   private HotelFoodOrderVO viewOne() {
      int orderNum = sc.nextInt();
      HotelFoodOrderVO ovo = odao.selectOne(orderNum);
      
      if(ovo != null) {      // 메뉴번호에 해당하는 메뉴가 있을 경우 출력
    	  System.out.println("==================================================================================================================================================");
    	  System.out.printf("\t%-12s |\t%-12s |\t%-32s |\t%-12s |\t%-12s |\t%-12s |\t%-12s%n",
    		        "주문번호", "주문객실", "주문메뉴이름", "주문수량", "주문가격", "주문날짜", "주문접수자");
    	  System.out.println("==================================================================================================================================================");

         
    	  System.out.printf("\t%-12d |\t%-12d |\t%-32s |\t%-12d |\t%-12d |\t%-12s |\t%-12s%n",
    		        ovo.getFoodOrderNo(), ovo.getRno(),
    		        ovo.getFoodName(), ovo.getFoodAmount(),
    		        ovo.getPrice(), ovo.getFoodDate(),
    		        ovo.getEName());
         
//         System.out.println("           "+ ovo.getFoodOrderNo() +"\t\t\t"+ ovo.getRno() +"   \t\t"+ ovo.getFoodName() 
//               +"\t\t\t    "+ovo.getFoodAmount() + "\t\t\t" + ovo.getPrice() + "\t     " + ovo.getFoodDate() 
//               +"\t\t  " +ovo.getEName());
      }
      return ovo;
   }//viewOne end
   
   
   
   //음식 주문 내역 수정  (단건조회 이후) U
   //주문 내역의 내용(수량 등) 수정
	private void modify() {

		System.out.print("   >> 수정하려는 주문 번호 : ");

		// viewOne에서 메뉴번호 입력받고, fvo 출력 후 vo객체 담아옴
		HotelFoodOrderVO ovo = viewOne();

		if (ovo != null) { // 메뉴번호에 맞는 ovo가 있다면

			System.out.print("   >> 객실 호수 (ex.1001) : ");
			int rno = sc.nextInt();

			System.out.println();
			System.out.println("(카테고리) 1.한식   | 2.양식   | 3.조식   | 4.음료   | 5. 음식 주문 메뉴로 이동 (이전화면)");
			System.out.println();
			System.out.print("   >> 카테고리 선택 : ");
			int foodCategory = sc.nextInt();

			List<HotelFoodVO> fvolist = fdao.selectmenu(foodCategory);

			// 카테고리에 맞는 메뉴 보여주고, 메뉴번호 입력받기
			String output = "";
			switch (foodCategory) {
			case 1:
				output = "한식";
				break;
			case 2:
				output = "양식";
				break;
			case 3:
				output = "조식";
				break;
			case 4:
				output = "음료";
				break;
			}

			// 그 카테고리에 맞는 메뉴가 담긴 리스트를 뿌려야 해

			System.out.println("==================================================================================================================================================");
			System.out.println("                                                          ["+ output + "]의 메뉴 조회");
			System.out.println("==================================================================================================================================================");

			// fvolist를 출력
			for (int i = 0; i < fvolist.size(); i++) {
				System.out.println(fvolist.get(i).getFoodNum() + ". " + fvolist.get(i).getFoodName() + " : "
						+ fvolist.get(i).getPrice() + "원"); // 리스트 속 HotelFoodVO를 꺼내라
			} // for end

			// 방번호, 카테고리 받고, 메뉴번호, 주문수량 입력받기

			System.out.print("   >> 주문할 메뉴 번호 : ");
			int foodNum = sc.nextInt();

			System.out.print("   >> 주문 수량 : ");
			int foodAmount = sc.nextInt();

			ovo.setRno(rno);
			ovo.setFoodCategory(foodCategory);
			ovo.setFoodNum(foodNum);
			ovo.setFoodAmount(foodAmount);
			ovo.setEcode(ordercode);

			boolean result = odao.update(ovo);

			if (result) {
				System.out.println("   >> 성공적으로 수정되었습니다.");
			} else {
				System.out.println("   >> 수정에 실패하였습니다.");
			}
			  menu(orderjob, ordercode); 

		} else {
			System.out.println("   >> 해당 주문 번호가 존재하지 않습니다.");
			  menu(orderjob, ordercode); 
		}

	}// modify end
   
   //음식 주문 내역 삭제 (단건조회 이후) D
	private void remove() {
		// odao.delete(input);

		System.out.println("   >> 삭제하려는 주문 번호 : ");
		// viewOne에서 메뉴번호 입력받고, fvo 출력 후 vo객체 담아옴
		HotelFoodOrderVO ovo = viewOne();

		if (ovo != null) { // 메뉴번호에 맞는 ovo가 있다면

			System.out.print("   >> 해당 요청사항을 삭제하시겠습니까?(Y/N): ");
			String yorn = sc.next();

			if (yorn.equalsIgnoreCase("Y")) {

				boolean result = odao.delete(ovo.getFoodCategory(), ovo.getFoodOrderNo());
				if (result) {
					System.out.println("   >> 성공적으로 삭제되었습니다.");
				} else {
					System.out.println("   >> 삭제에 실패하였습니다.");
				}
			}else {
				System.out.println("   >> 삭제를 취소하였습니다.");
			}

			System.out.println("==================================================================================================================================================");
			menu(orderjob, ordercode); // 삭제 후, '음식주문관리' 메뉴로 이동

		} else { // 메뉴번호에 맞는 ovo가 없다면
			System.out.println("   >> 해당 메뉴가 존재하지 않습니다.");
			menu(orderjob, ordercode);
		}

	}// remove end

}