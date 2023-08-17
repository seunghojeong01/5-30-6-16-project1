package hotel.main; 

import java.util.List;
import java.util.Scanner;

import hotel.main.HotelMain;
import hotel.dao.HotelCustomerRequestDAO;
import hotel.vo.HotelCustomerRequestVO;

public class HotelCustomerRequestMain {

	private Scanner sc;
	private int input; // 메뉴선택시 번호입력
	private int num; // 요청사항 등록번호
//	private int category; //카테고리 번호
	private int rno; // 객실호수
	private String reqMessage; // 요청메시지
	private HotelCustomerRequestVO reqvo;
	private HotelCustomerRequestDAO reqdao;
	public static String ecode; // hotelmain에서 불러올 sessionid값(작성자 출력을 위함)
	public static String job; // hotelmain에서 불러올 sessionjob값(관리자 or 직원 메뉴를 가기위함)

//	public static void main(String[] args) {
//		HotelCustomerRequestMain reqmain = new HotelCustomerRequestMain();
//		ecode = "111111";
//		job="ADM";
//		reqmain.menu(ecode, job);
//}

	public HotelCustomerRequestMain() {
		reqvo = new HotelCustomerRequestVO();
		reqdao = new HotelCustomerRequestDAO();
		sc = new Scanner(System.in);
	}

	public void menu(String sessioncode, String sessionjob) { // 고객요청메뉴
		ecode = sessioncode;
		job = sessionjob;

		// 호텔메인객체생성
		HotelMain hotelmain = new HotelMain();

		System.out.println("==================================================================================================================================================");
		System.out.println("                                                                REQUEST");
		System.out.println("==================================================================================================================================================");

		System.out.println("1. 고객요청사항 등록");
		System.out.println("2. 고객요청사항 조회");
		System.out.println("3. 뒤로가기");
		System.out.println();
		System.out.print("   >> 선택: ");

		input = sc.nextInt();

		switch (input) {
		case 1: // 1 선택하면
			regist(); // COM_014 카테고리별 고객요청사항 등록 화면
			break;

		case 2: // 2 선택하면
			list(); // COM_015 카테고리별 고객요청사항 조회 화면
			break;

		case 3: // 3 선택하면
			// if문 넣기, job이 "ADM"이면, menuadmin(), "EMP"이면, menuemp()
			if (job.equals("ADM")) {
				hotelmain.menuadmin();
			} else if (job.equals("EMP")) {
				hotelmain.menuemp();
			}
			break;
		default: // 그 외의 경우에는 "1~3 중에 하나를 입력해주세요."를 출력하고
					// >>선택 : "을 출력
			System.out.print("   >> 1~3 중에 하나를 입력해주세요.");
			menu(ecode, job);
			break;
		}

	}

	public void regist() { // 고객요청사항 등록
		System.out.println("==================================================================================================================================================");
		System.out.println("                                                           REQUEST_REGIST");
		System.out.println("==================================================================================================================================================");

		System.out.println("1.어매니티	 2.수리  3.청소  4.기타");
		System.out.println(
				"==================================================================================================================================================");
		System.out.print("   >> 고객님이 요청하신 해당사항의 카테고리 번호를 입력하세요: ");

		reqvo.setCategory(sc.nextInt());
		System.out.println();

		System.out.print("   >> 고객님의 객실 호수를 입력하세요: ");
		reqvo.setRno(sc.nextInt());
		System.out.println();

		sc.nextLine();// 개행문자 제거
		System.out.print("   >> 요청사항을 입력하세요: ");
		reqvo.setReqMessage(sc.nextLine());
		System.out.println(
				"==================================================================================================================================================");

		reqvo.setEcode(ecode);

		boolean result = reqdao.insert(reqvo); // 전달이 잘 되었는지 출력

		if (result == true) {
			System.out.println("   >> 등록완료되었습니다."); // 고객요청사항 등록 실행성공하면 "등록완료되었습니다.",
			System.out.println("   >> 고객요청메뉴로 돌아갑니다."); // "고객요청메뉴로 돌아갑니다." 출력한 후
			menu(ecode, job); // 메뉴로 돌아가기
		} else {
			System.out.println("   >> 등록실패하였습니다."); // 실행실패하면 "등록실패하였습니다." 출력한 후
			regist(); // 등록화면 재출력
		}

	}

	public void list() { // 고객요청사항 조회
//		고객요청사항의 카테고리를 입력받고, 해당 카테고리의 고객요청사항을 조회한다.
		System.out.println("==================================================================================================================================================");
		System.out.println("                                                             REQUEST_LIST");
		System.out.println("==================================================================================================================================================");

		System.out.println("1.어매니티	 2.수리  3.청소  4.기타");
		System.out.println(
				"==================================================================================================================================================");
		System.out.print("   >> 조회 할 고객요청사항의 카테고리 번호를 입력하세요: ");
		int category = sc.nextInt();

		List<HotelCustomerRequestVO> reqvoList = reqdao.select(category);

//		if(reqvo != null ) {
//			//결과 반환
//			System.out.println("-------------------------------------------------------------------------------------");
//			System.out.println(" 등록번호 | 카테고리항목 | 객실 호수 | 요청사항 | 작성자 | 요청일 ");
//			System.out.println(reqvo.getNum() + "|" + reqvo.getCategory() + "|" + reqvo.getRno() + "|"
//					+ reqvo.getReqMessage() + "|" + reqvo.getEcode()+ "|" + reqvo.getReqDate());
//			System.out.println("-------------------------------------------------------------------------------------");
//		}

		if (reqvoList.size() > 0) { // 등록된 카테고리가 있으면 화면에 표시
			System.out.println("==================================================================================================================================================");
			System.out.printf("%-8s | %-13s | %-8s | %-21s | %-10s | %s%n",
			        "등록번호", "카테고리항목", "객실 호수", "요청사항", "작성자", "요청일");
			System.out.println("--------------------------------------------------------------------------------------------------------------------------------------------------");
//	
//			for (int i = 0; i < reqvoList.size(); i++) {
//			    reqvo = reqvoList.get(i);
//			    System.out.printf("\n%-10d  %-10d  %-10d  %-25s  %-10s  %-12s\n",
//			            reqvo.getNum(), reqvo.getCategory(), reqvo.getRno(), reqvo.getReqMessage(), reqvo.getEcode(), reqvo.getReqDate());
//			}

			for (int i = 0; i < reqvoList.size(); i++) {
				reqvo = reqvoList.get(i);
				System.out.printf("%-10d | %-15d | %-10d | %-20s | %-10s | %s%n",
				        reqvo.getNum(), reqvo.getCategory(), reqvo.getRno(), reqvo.getReqMessage(), reqvo.getEname(), reqvo.getReqDate());
			}

			System.out.println(
					"==================================================================================================================================================");
			System.out.println("1. 고객요청사항 수정");
			System.out.println("2. 고객요청사항 삭제");
			System.out.println("3. 고객요청메뉴");
			System.out.println();
			System.out.print("   >> 선택: ");
			input = sc.nextInt();

			switch (input) {
			case 1: // 1 선택하면
				modify(); // COM_016 카테고리별 고객요청사항 수정 화면
				break;

			case 2: // 2 선택하면
				remove(); // COM_017 카테고리별 고객요청사항 삭제 화면
				break;

			case 3: // 3 선택하면
				menu(ecode, job);
				break;
			default: // 그 외의 경우에는 "1~3 중에 하나를 입력해주세요."를 출력하고
				// list()재출력
				System.out.println("   >> 1~3 중에 하나를 입력해주세요.");
				list();
				break;
			}

		} else {
			System.out.println(
					"==================================================================================================================================================");
			System.out.println("   >> 등록된 요청사항이 없습니다.");
			System.out.println("   >> 고객요청메뉴로 돌아갑니다.");
			menu(ecode, job);
			System.out.println(
					"==================================================================================================================================================");

		}

	}

	public void modify() { // 고객요청사항 수정
		System.out.println("==================================================================================================================================================");
		System.out.println("                                                             REQUEST_MODIFY");
		System.out.println("==================================================================================================================================================");

		System.out.println("1.어매니티	 2.수리  3.청소  4.기타");
		System.out.println(
				"==================================================================================================================================================");
		System.out.print("   >> 수정할 고객요청사항의 카테고리 번호를 입력하세요: ");
		int category = sc.nextInt();
		System.out.println();

		List<HotelCustomerRequestVO> reqvoList = reqdao.select(category);

		System.out.print("   >> 수정할 고객요청사항의 등록번호를 입력하세요: ");
		num = sc.nextInt();

		reqvo = reqdao.select(category, num);

		if (reqvo != null) {
			// 결과 반환
			System.out.println("==================================================================================================================================================");
			System.out.printf("등록번호  |카테고리항목|객실 호수	|요청사항		|작성자	 |요청일	");

			System.out.printf("\n%d   \t%d  \t%d   \t%s  \t\t%s " + "  " + "%s\n", reqvo.getNum(), reqvo.getCategory(),
					reqvo.getRno(), reqvo.getReqMessage(), reqvo.getEname(), reqvo.getReqDate());

			System.out.println("==================================================================================================================================================");
		}

		System.out.println("1.어매니티	 2.수리  3.청소  4.기타");
		System.out.println("==================================================================================================================================================");
		System.out.print("   >> 변경하려는 카테고리 번호를 입력하세요: ");

		reqvo.setCategory(sc.nextInt());
		System.out.println();

		System.out.print("   >> 수정할 고객님의 객실 호수를 입력하세요: ");
		rno = sc.nextInt();
		System.out.println();

		sc.nextLine();
		System.out.print("   >> 수정할 요청사항을 입력하세요: ");
		reqMessage = sc.nextLine();

		HotelCustomerRequestVO reqvo = new HotelCustomerRequestVO();
		reqvo.setNum(num);
		reqvo.setCategory(category);
		reqvo.setRno(rno);
		reqvo.setReqMessage(reqMessage);
		reqvo.setEcode(ecode);

		boolean result = reqdao.update(reqvo);

		if (result == true) {
			System.out.println("   >> 수정완료되었습니다.");
			System.out.println("   >> 고객요청메뉴로 돌아갑니다.");
			menu(ecode, job);
		} else {
			System.out.println("   >> 수정실패하였습니다.");
			modify();
		}

	}

	public void remove() { // 고객요청사항 삭제
		System.out.println("==================================================================================================================================================");
		System.out.println("                                                              REQUEST_REMOVE");
		System.out.println("==================================================================================================================================================");

		System.out.print("   >> 삭제할 고객요청사항의 카테고리 번호를 입력하세요: ");
		int category = sc.nextInt();
		System.out.println();

		List<HotelCustomerRequestVO> reqvoList = reqdao.select(category);

		System.out.print("   >> 삭제할 고객요청사항의 등록번호를 입력하세요: ");
		num = sc.nextInt();

		reqvo = reqdao.select(category, num);

		if (reqvo != null) {
			// 결과 반환
			System.out.println("==================================================================================================================================================");
			System.out.printf("등록번호  |카테고리항목|객실 호수	|요청사항		|작성자	 |요청일	");

			System.out.printf("\n%d   \t%d  \t%d   \t%s  \t\t%s " + "  " + "%s\n", reqvo.getNum(), reqvo.getCategory(),
					reqvo.getRno(), reqvo.getReqMessage(), reqvo.getEname(), reqvo.getReqDate());

			System.out.println("==================================================================================================================================================");
		}

		System.out.println("   >> 해당 요청사항을 삭제하시겠습니까?(Y/N): ");
		System.out.println(
				"==================================================================================================================================================");
		// y or Y입력 후 실행성공
		String input = sc.next();
		if (input.equalsIgnoreCase("Y")) {

			boolean result = reqdao.delete(category, num);

			System.out.println("   >> 삭제완료되었습니다.");
			System.out.println("   >> 고객요청메뉴로 돌아갑니다.");
			menu(ecode, job);
		} else if (input.equalsIgnoreCase("N")) {
			// n or N입력 후 실행성공
			System.out.println("   >> 고객요청메뉴로 돌아갑니다.");
			menu(ecode, job);
		} else
			// 실행실패
			System.out.println("   >> 삭제실패하였습니다.");
		System.out.println("   >> (Y/N) 중에 다시 입력해주세요. ");
		remove(); // 재출력
	}

}