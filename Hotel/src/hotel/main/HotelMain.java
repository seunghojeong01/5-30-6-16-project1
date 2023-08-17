package hotel.main;

import java.util.List;
import java.util.Scanner;

import hotel.dao.HotelEmpDAO;
import hotel.jdbc.DBConn;
import hotel.vo.HotelEmpVO;

public class HotelMain {
	private static String sessioncode;//직원고유번호  ecode
	private static String sessionjob;//adm/emp구별
	private Scanner sc;
	private HotelEmpVO evo;
	private HotelEmpDAO edao;
	
	private HotelDnsMain dmain;
	private HotelCustomerRequestMain crmain;
	private HotelFoodMain fmain;
	private HotelFoodOrderMain fomain;
	private HotelRoomInfoMain rimain;
	private HotelRoomControlMain rcmain;
	private HotelNoticeMain nmain;
	
	public HotelMain() {
		sc=new Scanner(System.in);
		evo=new HotelEmpVO();
		edao=new HotelEmpDAO();
		dmain=new HotelDnsMain();
		crmain=new HotelCustomerRequestMain();
		fmain =new HotelFoodMain();
		fomain=new HotelFoodOrderMain();
		rimain =new HotelRoomInfoMain();
		rcmain =new HotelRoomControlMain();
		nmain =new HotelNoticeMain();
	}
	
	public static void main(String[] args) {
		HotelMain hmain=new HotelMain();
				while(true) {	 
			hmain.mainmenu();
		 }
	}
	
	public void mainmenu(){//로그인과 시스템 종료 메뉴
		System.out.println("==================================================================================================================================================");
		System.out.println("                                                           HOTEL SYSTEM           ");
		System.out.println("==================================================================================================================================================");
		System.out.println("   1.로그인");
		System.out.println("   2.시스템 종료");
		System.out.println();
		System.out.print("   >> 선택:");
		int a=sc.nextInt();
		switch(a) {
		case 1: login(); break;
		case 2:
			
			System.out.println("==================================================================================================================================================");
			System.out.println("      시스템을 종료합니다.     ");
			sc.close();
			//DBConn.close();
			System.exit(0);
		default:
			System.out.println("다시 입력해주세요");
			mainmenu();
		}
	}
	
	public void menuadmin(){//관리자 메뉴
		System.out.println("==================================================================================================================================================");
		System.out.println("                                                             관리자 메뉴          ");
		System.out.println("==================================================================================================================================================");
		System.out.println("   1.객실 정보 관리 화면");
		System.out.println("   2.객실 관리 화면");
		System.out.println("   3.음식 메뉴 관리");
		System.out.println("   4.음식 주문 관리");
		System.out.println("   5.고객 요청");
		System.out.println("   6.DNS");
		System.out.println("   7.직원 관리");
		System.out.println("   8.공지사항");
		System.out.println("   9.로그아웃");
		System.out.println();
		System.out.print("   >> 선택:");
		int a=sc.nextInt();
		switch(a) {
		case 1: rcmain.Roomcontrolmain(); break;
		case 2: rimain.infomain(sessionjob); break;
		case 3: fmain.menu(); break;
		case 4: fomain.menu(sessionjob, sessioncode); break;
		case 5: crmain.menu(sessioncode, sessionjob); break;
		case 6: dmain.dnsmenu(sessioncode, sessionjob); break;
		case 7: empmanagement(); break;
		case 8: nmain.listadm(sessioncode, sessionjob); break;
		case 9: logout(); break;
		default: 
			System.out.println("  다시 입력해 주세요.");
			menuadmin();
			break;
		}
	}
	
	public void menuemp(){//직원 메뉴	
		System.out.println("==================================================================================================================================================");
		System.out.println("                                                              직원 메뉴          ");
		System.out.println("==================================================================================================================================================");
		System.out.println("   1.객실 관리 화면");
		System.out.println("   2.DNS");
		System.out.println("   3.음식 주문 관리");
		System.out.println("   4.고객 요청");
		System.out.println("   5.공지사항");
		System.out.println("   6.로그아웃");
		System.out.println();
		System.out.print("   >> 선택:");
		int a=sc.nextInt();
		switch(a) {
		case 1: rimain.infomain(sessionjob); break;
		case 2: dmain.dnsmenu(sessioncode, sessionjob); break;
		case 3: fomain.menu(sessionjob, sessioncode);  break;
		case 4: crmain.menu(sessioncode, sessionjob); break;
		case 5: nmain.listemp(); break;
		case 6: logout();break;
		default: 
			System.out.println("  다시 입력해 주세요.");
			menuemp();
			break;
		}
		
	}
	
	public void login() {//로그인 메서드(임시) 
		System.out.println("==================================================================================================================================================");
		System.out.println("                                                                 LOGIN                 ");
		System.out.println("==================================================================================================================================================");
		System.out.print("   >> 직원번호 입력:");
		String code=sc.next();		
		System.out.print("   >> 핸드폰 번호 입력:");
		String phone=sc.next();
		
			
		if(edao.login(code,phone).equals("ADM")) {
			System.out.println("   관리자 로그인 ");
			sessioncode=code;
			sessionjob="ADM";
			menuadmin();
		}else if(edao.login(code,phone).equals("EMP")) {
		    System.out.println("   직원 로그인");
		    sessioncode=code;
			sessionjob="EMP";
		    menuemp();
		}else if(edao.login(code,phone).equals("a")){
			System.out.println("   로그인 실패! 다시 입력해주세요");
			login();
		}
		
	}

	public void logout() {//로그아웃
		sessioncode=null;
		sessionjob=null;
		System.out.println("==================================================================================================================================================");
		System.out.println("                                                                LOGOUT                 ");
		System.out.println("==================================================================================================================================================");
		System.out.println("계정이 로그아웃 되었습니다.");
	}
	
	public void empmanagement() {
		System.out.println("==================================================================================================================================================");
		System.out.println("                                                              직원 계정 관리                 ");
		System.out.println("==================================================================================================================================================");
		System.out.println("   1.직원 전체 목록");
		System.out.println("   2.직원 정보 추가");
		System.out.println("   3.뒤로가기");
		System.out.println();
		System.out.println("   >> 선택:");
		int a=sc.nextInt();
		switch(a) {
		case 1:emplist(); break;
		case 2:empjoin(); break;
		case 3:menuadmin(); break;
		default: 
			System.out.println("  다시 입력해 주세요.");
			empmanagement();
			break;
		}
	}
	public void emplist() {//직원 리스트
		
		List<HotelEmpVO> emplist=edao.emplist();
		System.out.println("==================================================================================================================================================");
		System.out.println("                                                            MEMBER LIST                 ");
		System.out.println("==================================================================================================================================================");
		System.out.println(" 직원번호\t이름\t나이\t전화번호\t\t직책");
		System.out.println("--------------------------------------------------------------------------------------------------------------------------------------------------");
		if(emplist.size()>0) {
			for (HotelEmpVO evo : emplist) {
	
				System.out.println(" "+evo.getEcode()+"\t"+evo.getEname()+
						"\t"+evo.getEage()+"\t"+evo.getEphone()+
						"\t"+evo.getEjob());
			}
			System.out.println("==================================================================================================================================================");
				System.out.println("   1.상세보기");
				System.out.println("   2.뒤로가기");
				System.out.println();
				System.out.println("   >> 선택:");
				
				int a=sc.nextInt();
				switch(a) {
				case 1:empdetail();
					   int b=sc.nextInt();
					   switch(b) {
					   case 1:empmodify();break;
					   case 2:empremove();break;
					   case 3:emplist();break;
					   default:
						   System.out.println("   >> 다시 입력해 주세요.");
						   empdetail();
						   break;
					   }
				break;
				case 2:empmanagement(); break;
				default: 
					System.out.println("   >> 다시 입력해 주세요.");
					emplist();
					break;
				}
			
			}else {
				System.out.println("   >> 해당 아이디가 존재하지 않습니다.");
				empmanagement();
			}
	}
	public void empdetail() {//직원 상세보기
		System.out.println("==================================================================================================================================================");
		System.out.println("                                                               MEMBER INFO   ");
		System.out.println("==================================================================================================================================================");
		System.out.print("   >> 조회할 직원번호 : ");
		String ecode=sc.next(); //조회하고 싶은 아이디
		evo=edao.empdetail(ecode);//값을 vo로 넘겨줌
		if(evo != null) {
		System.out.println();
		System.out.println("     직원번호 : "+evo.getEcode());
		System.out.println("     이름 : "+evo.getEname());
		System.out.println("     나이 : "+evo.getEage());
		System.out.println("     성별 : "+evo.getEgender());
		System.out.println("     전화번호 : "+evo.getEphone());
		System.out.println("     이메일 : "+evo.getEemail());
		System.out.println("     직책 : "+evo.getEjob());
		System.out.println("==================================================================================================================================================");
		System.out.println("   1. 정보 수정   2. 정보 삭제   3.뒤로가기");
		System.out.println();
		System.out.print("   >> 선택:");
		}else {
			System.out.println("해당 아이디가 존재하지 않습니다.");
			emplist();
		}
	}
	public void empjoin() {//직원정보 추가
		System.out.println("==================================================================================================================================================");
		System.out.println("                                                            MEMBER JOIN   ");
		System.out.println("==================================================================================================================================================");
		System.out.print("   >> 직원번호(6자리) :");
		String joincode=sc.next();
		System.out.print("   >> 회원이름 :");
		evo.setEname(sc.next());
		System.out.print("   >> 나이 :");
		evo.setEage(sc.nextInt());
		System.out.print("   >> 성별(남/여) :");
		String joingender=sc.next();
		System.out.print("   >> 이메일 :");
		evo.setEemail(sc.next());
		System.out.print("   >> 전화번호 :");
		evo.setEphone(sc.next());	
		System.out.print("   >> 직책(ADM/EMP) :");
		String joinjob=sc.next();
		String joinjob2= joinjob.toUpperCase();
		
		if(joincode.length() == 6 &&( joingender.equals("남") || joingender.equals("여")) && 
				(joinjob2.equals("ADM") || joinjob2.equals("EMP") )) {
			evo.setEcode(joincode);
			evo.setEgender(joingender);
			evo.setEjob(joinjob2);
			int result = edao.empinsert(evo);
			if(result==1) {
				System.out.println("==================================================================================================================================================");
				System.out.println("   >> 회원가입 완료");
				menuadmin();
			}else {
				System.out.println("==================================================================================================================================================");
				System.out.println("   >> 회원가입 실패");
				menuadmin();
			}	
		}else {
			System.out.println("==================================================================================================================================================");
			System.out.println("   >> 다시 입력해 주세요.");
			empjoin();
		}
		
		
		
	}
	
	public void empmodify() {//직원 수정
		System.out.println("==================================================================================================================================================");
		System.out.println("                                                              MEMBER UPDATE   ");
		System.out.println("==================================================================================================================================================");
		System.out.println("   >> 정보를 수정합니다.");		
		System.out.print("   >> 이름 : ");
		evo.setEname(sc.next());
		System.out.print("   >> 나이 : ");
		evo.setEage(sc.nextInt());
		System.out.print("   >> 이메일 : ");
		evo.setEemail(sc.next());
		System.out.print("   >> 전화번호 : ");
		evo.setEphone(sc.next());
		edao.empupdate(evo);
		
		if(evo != null) {
			System.out.println("==================================================================================================================================================");
			System.out.println("   >> 수정되었습니다.");
			emplist();
	}
	}
	public void empremove() {//직원 삭제
		System.out.println("==================================================================================================================================================");
		System.out.print("   >> 해당 정보를 삭제하시겠습니까?(Y/N) : ");
		char yon=sc.next().charAt(0);
		switch(yon) {
		case 'y': case 'Y':
			edao.empdelete(evo.getEcode());
			System.out.println("==================================================================================================================================================");
			System.out.println("   >> 삭제되었습니다.");
			emplist();
		case 'n': case 'N':
			System.out.println("==================================================================================================================================================");
			System.out.println("   >> 목록으로 돌아갑니다.");
			emplist();
		default :
			System.out.println("==================================================================================================================================================");
			System.out.println("   >> 다시 입력해 주세요.");
			empremove();
		}
		
		
	}
	
	
}