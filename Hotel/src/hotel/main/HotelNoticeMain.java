package hotel.main;

import java.util.List;
import java.util.Scanner;

import hotel.dao.HotelNoticeDAO;
import hotel.vo.HotelNoticeVO;

public class HotelNoticeMain {

	private Scanner sc; 
	private int input; // 메뉴선택시 번호입력
	private HotelNoticeVO noticevo;
	private HotelNoticeDAO noticedao;
	private String title; // 제목
	private String content; // 내용
	public static String ecode; // hotelmain에서 불러올 sessionid값(작성자 출력을 위함)
	public static String job;

//	public static void main(String[] args) {
//		HotelNoticeMain noticemain = new HotelNoticeMain();
//		noticemain.listadm();
//	}

	public HotelNoticeMain() {
		noticedao = new HotelNoticeDAO();
		noticevo = new HotelNoticeVO();
		//ecode="111111"; //111111 고정값????
		//job="ADM";
		sc = new Scanner(System.in);

	}

	public void listemp() {
//		EMP_003	공지사항 전체목록 화면 (직원용)

		// 호텔메인객체생성
		HotelMain hotelmain = new HotelMain();
		
		System.out.println("==================================================================================================================================================");
		System.out.println("                                                                   NOTICE(EMP)");
		System.out.println("==================================================================================================================================================");

		List<HotelNoticeVO> noticevoList = noticedao.select();

		if (noticevoList.size() > 0) { // 등록된 공지사항이 있으면 화면에 표시

			System.out.println("등록번호  |제목 			|작성자   |작성일 ");
			System.out.println("--------------------------------------------------------------------------------------------------------------------------------------------------");
			
			for (HotelNoticeVO noticevo : noticevoList) { // noticevoList에서 noticevo값이 없을 때까지 출력
				System.out.printf("%d   \t%s  \t\t %s " + "  " + "%s\n",
						noticevo.getNum(), noticevo.getTitle(), noticevo.getEname(), noticevo.getNoticeDate());
			}
//
//			for (HotelNoticeVO noticevo : noticevoList) { // noticevoList에서 noticevo값이 없을 때까지 출력
//				System.out.println(noticevo.getNum() + "|" + noticevo.getTitle() + "|" + noticevo.getEname() + "|"
//						+ noticevo.getNoticeDate());
//			}
//			
			System.out.println("==================================================================================================================================================");
			System.out.println("   1. 상세보기");
			System.out.println("   2. 뒤로가기");
			System.out.println();
			System.out.print("   >> 선택: ");
			
			input = sc.nextInt();
			
			switch (input) {
			case 1: // 1 선택하면
				detailemp(); // COM_012 공지사항 상세보기 (직원용)
				break;
				
			case 2: // 2 선택하면
				// 직원메뉴(승호)
				hotelmain.menuemp();
				break;
			default: // 그 외의 경우에는 "1~2 중에 하나를 입력해주세요."를 출력하고
				// >>선택 : "을 출력
				System.out.println("   >> 1~2 중에 하나를 입력해주세요.");
				listemp();
				break;
			}

		} else {
			System.out.println("   >> 등록된 공지사항이 없습니다.");
			System.out.println("   >> 직원메뉴로 돌아갑니다.");
			hotelmain.menuemp();
		}


	}

	public void detailemp() {
//		COM_012	공지사항 상세보기 (직원용)
		System.out.println("==================================================================================================================================================");
		System.out.println("                                                           NOTICE_DETAIL(EMP)");
		System.out.println("==================================================================================================================================================");

		System.out.print("   >> 보고 싶은 게시물 번호를 입력하세요: ");
		// 보고싶은 게시물 번호 입력받기
		int num = sc.nextInt();

		// HotelNoticeVO 클래스의 객체를 참조하는 변수 'noticevo'를 생성하여, 결과로 반환된 공지사항 정보를 저장
		// 데이터 액세스 객체 (DAO)인 noticedao를 사용하여 'num(공지사항의 고유번호나 식별자)'값을 가진 공지사항을 조회하는 메서드
		// 호출
		HotelNoticeVO noticevo = noticedao.select(num);

		if (noticevo != null) {
			// 결과 반환
			System.out.println("==================================================================================================================================================");
			System.out.println("   제목: " + noticevo.getTitle());
			System.out.println("   내용");
			System.out.println(noticevo.getContent());
			System.out.println("   작성자: " + noticevo.getEname());
			System.out.println("   작성일: " + noticevo.getNoticeDate());
			System.out.println("==================================================================================================================================================");
			System.out.println("1. 전체목록");
			System.out.print("   >> 선택: ");

			input = sc.nextInt();

			switch (input) {
			case 1:
				listemp();
				break;

			default:// 그 외의 경우에는 "1을 입력해주세요."를 출력하고
				// >>선택 : "을 출력
				System.out.println("   >> 1을 입력해주세요.");
				detailemp();
				break;
			}
		} else {
			System.out.println("   >> 해당 번호로 등록된 게시물이 없습니다.");
			detailemp();
		}
	}

	public void listadm(String code,String ejob) {
//		ADM_012	공지사항 전체목록 화면(관리자용)
		ecode = code;
		job = ejob;
		// 호텔메인객체생성
		HotelMain hotelmain = new HotelMain();

		System.out.println("==================================================================================================================================================");
		System.out.println("                                                                  NOTICE(ADMIN)");
		System.out.println("==================================================================================================================================================");

		List<HotelNoticeVO> noticevoList = noticedao.select();

		if (noticevoList.size() > 0) { // 등록된 공지사항이 있으면 화면에 표시

			System.out.println("등록번호  |제목 			|작성자   |작성일 ");
			System.out.println("--------------------------------------------------------------------------------------------------------------------------------------------------");

			for (HotelNoticeVO noticevo : noticevoList) { // noticevoList에서 noticevo값이 없을 때까지 출력
//				System.out.println(noticevo.getNum() + "|" + noticevo.getTitle() + "|" + noticevo.getEcode() + "|"
//						+ noticevo.getNoticeDate());
				System.out.printf("%d   \t%s  \t\t %s " + "  " + "%s\n",
						noticevo.getNum(), noticevo.getTitle(), noticevo.getEname(), noticevo.getNoticeDate());
			}

			System.out.println("==================================================================================================================================================");
			System.out.println("   1. 상세보기");
			System.out.println("   2. 등록하기");
			System.out.println("   3. 뒤로가기");
			System.out.println();
			System.out.print("   >> 선택: ");
			
			input = sc.nextInt();
			
			switch (input) {
			case 1: // 1 선택하면
				detailadm(); // ADM_013 공지사항 상세보기 화면(관리자용)
				break;
				
			case 2: // 2 선택하면
				regist(); // ADM_014 공지사항 등록 화면
				break;
				
			case 3: // 3 선택하면
				// "ADM"이면, menuadmin(), "EMP"이면, menuemp()
				hotelmain.menuadmin();
				break;
			default: // 그 외의 경우에는 "1~3 중에 하나를 입력해주세요."를 출력하고
				// >>선택 : "을 출력
				System.out.print("   >> 1~3 중에 하나를 입력해주세요.");
				listadm(ecode,job);
				break;
			}
			
		} else {
			System.out.println("   >> 등록된 공지사항이 없습니다.");
			
			System.out.println("   1. 등록하기");
			System.out.println("   2. 관리자메뉴");
			System.out.println();
			System.out.print("   >> 선택: ");
			
			input = sc.nextInt();
			
			switch (input) {
			case 1: // 1 선택하면
				regist(); // ADM_014 공지사항 등록 화면
				break;
				
			case 2: // 2 선택하면
				// "ADM"이면, menuadmin(), "EMP"이면, menuemp()
				hotelmain.menuadmin();
				break;
				
			default: // 그 외의 경우에는 "1~3 중에 하나를 입력해주세요."를 출력하고
				// >>선택 : "을 출력
				System.out.print("   >> 1~2 중에 하나를 입력해주세요.");
				listadm(ecode,job);
				break;
			}
		}

	}

	public void detailadm() {
//		ADM_013	공지사항 상세보기 화면(관리자용)

		System.out.println("==================================================================================================================================================");
		System.out.println("                                                          NOTICE_DETAIL(ADMIN)");
		System.out.println("==================================================================================================================================================");


		System.out.print("   >> 보고 싶은 게시물 번호를 입력하세요: ");
		// 보고싶은 게시물 번호 입력받기
		int num = sc.nextInt();

		// HotelNoticeVO 클래스의 객체를 참조하는 변수 'noticevo'를 생성하여, 결과로 반환된 공지사항 정보를 저장
		// 데이터 액세스 객체 (DAO)인 noticedao를 사용하여 'num(공지사항의 고유번호나 식별자)'값을 가진 공지사항을 조회하는 메서드
		// 호출
		noticevo = noticedao.select(num);

		if (noticevo != null) {
			// 결과 반환
			System.out.println("==================================================================================================================================================");
			System.out.println("   제목: " + noticevo.getTitle());
			System.out.println("   내용");
			System.out.println(noticevo.getContent());
			System.out.println("   작성자: " + noticevo.getEname());
			System.out.println("   작성일: " + noticevo.getNoticeDate());
			System.out.println("==================================================================================================================================================");
			System.out.println("   1. 수정하기");
			System.out.println("   2. 삭제하기");
			System.out.println("   3. 전체목록");
			System.out.println();
			System.out.print("   >> 선택: ");

			input = sc.nextInt();

			switch (input) {
			case 1: // 1 선택하면
				modify(num); // ADM_015 공지사항 수정화면
				break;

			case 2: // 2 선택하면
				remove(num); // ADM_016 공지사항 삭제화면
				break;

			case 3: // 3 선택하면
				listadm(ecode,job); // ADM_012 공지사항 전체목록 화면(관리자용)
				break;
			default: // 그 외의 경우에는 "1~3 중에 하나를 입력해주세요."를 출력하고
						// >>선택 : "을 출력
				System.out.println("   >> 1~3 중에 하나를 입력해주세요.");
				detailadm();
				break;
			}
		} else {
			System.out.println("   >> 해당 번호로 등록된 게시물이 없습니다.");
			detailadm();
		}
	}

	public void regist() {
//		ADM_014	공지사항 등록 화면

		System.out.println("==================================================================================================================================================");
		System.out.println("                                                               NOTICE_REGIST(ADMIN)");
		System.out.println("==================================================================================================================================================");

		
		sc.nextLine();// 개행문자 제거
		System.out.println("   >> 제목: ");
		title = sc.nextLine();

		System.out.println("   >> 내용: ");
		content = sc.nextLine();

		noticevo.setEcode(ecode);

		System.out.println("==================================================================================================================================================");
		
		noticevo.setTitle(title);
		noticevo.setContent(content);

		boolean result = noticedao.insert(noticevo); // 전달이 잘 되었는지 출력

		if (result == true) {
			System.out.println("   >> 등록완료되었습니다."); // 공지사항 등록 실행성공하면 "등록완료되었습니다.",
			System.out.println("   >> 목록으로 돌아갑니다."); // "목록으로 돌아갑니다." 출력한 후
			listadm(ecode,job); // 관리자전체목록
		} else {
			System.out.print("   >> 등록실패하였습니다."); // 실행실패하면 "등록실패하였습니다." 출력한 후
			regist(); // 등록화면 재출력
		}
	}

	public void modify(int num) {//ecode 넘어와야됨
//		ADM_015	공지사항 수정화면

		System.out.println("==================================================================================================================================================");
		System.out.println("                                                          NOTICE_MODIFY(ADMIN)");
		System.out.println("==================================================================================================================================================");
		noticevo.setEcode(ecode);
		noticevo.setNum(num);
		sc.nextLine();
		System.out.println("   >> 제목: ");
		noticevo.setTitle(sc.nextLine());

		System.out.println("   >> 수정할 내용: ");
		noticevo.setContent(sc.nextLine());
		System.out.println("==================================================================================================================================================");

		boolean result = noticedao.update(noticevo); // 전달이 잘 되었는지 출력

		if (result == true) {
			System.out.println("   >> 수정완료되었습니다."); // 공지사항 수정 실행성공하면 "수정완료되었습니다.",
			System.out.println("   >> 목록으로 돌아갑니다."); // "목록으로 돌아갑니다." 출력한 후
			listadm(ecode,job); // 관리자전체목록
		} else {
			System.out.println(result);
			System.out.println("   >> 수정실패하였습니다."); // 실행실패하면 "수정실패하였습니다." 출력한 후
			modify(num); // 수정화면 재출력
		}
	}

	public void remove(int num) {
//		ADM_016	공지사항 삭제화면
		System.out.println("==================================================================================================================================================");
		System.out.println("                                                              NOTICE_REMOVE(ADMIN)");
		System.out.println("==================================================================================================================================================");

		noticevo.setNum(num);

		System.out.println("   >> 해당 게시물을 삭제하시겠습니까?(Y/N): ");

		// y or Y입력 후 실행성공
		String input = sc.next();
		if (input.equalsIgnoreCase("Y")) { // equalsIgnoreCase: 대소문자 구분없음, equals: 대소문자 구분함

			boolean result = noticedao.delete(num);
			if (result = true) {
				System.out.println("   >> 삭제완료되었습니다.");
				System.out.println("   >> 목록으로 돌아갑니다.");
				listadm(ecode,job);
			} else {
				// 실행실패
				System.out.print("   >> 삭제실패하였습니다.");
				System.out.println("   >> (Y/N) 중에 다시 입력해주세요. ");
				remove(num); // 재출력
			}
		} else if (input.equalsIgnoreCase("N")) {
			// n or N입력 후 실행성공
			System.out.println("   >> 목록으로 돌아갑니다.");
			listadm(ecode,job);
		}
	}
}