package hotel.main;

import java.util.List;
import java.util.Scanner;

import hotel.dao.HotelDnsDAO;
import hotel.vo.HotelDnsVO;

public class HotelDnsMain {
	private Scanner sc;
	private HotelDnsDAO ddao;
	private HotelDnsVO dvo;
	private static String dnscode;
	private static String dnsjob;
	
	
	public HotelDnsMain(){
		sc=new Scanner(System.in);
		ddao=new HotelDnsDAO();
		dvo=new HotelDnsVO();
	}

	public void dnsmenu(String sessioncode,String sessionjob) {
		dnscode=sessioncode;
		dnsjob=sessionjob;
		HotelMain hmain=new HotelMain();
		System.out.println("==================================================================================================================================================");
		System.out.println("                                                                 DNS MENU");
		System.out.println("==================================================================================================================================================");
		System.out.println("   1. DNS 등록");
		System.out.println("   2. DNS 전체목록");
		System.out.println("   3. 뒤로가기");
		System.out.println();
		System.out.print("   >> 선택:");
		int a=sc.nextInt();
		switch(a) {
		case 1:dnsjoin();break;
		case 2:dnslist();break;
			
		case 3:
			if(dnsjob.equals("ADM")) {//로그인 한 사람이 관리자라면
				hmain.menuadmin();
			}else if(dnsjob.equals("EMP")) {//로그인 한 사람이 직원이라면
				hmain.menuemp();
			}break;
		default :
			System.out.println("   >> 다시 입력해 주세요.");
			dnsmenu(dnscode,dnsjob);
		}
		
	}
	
	public void dnsjoin() {
		System.out.println("==================================================================================================================================================");
		System.out.println("                                                                  DNS 등록");
		System.out.println("==================================================================================================================================================");
		System.out.print("   >> 객실 호수 입력 :");
		int roomnum=sc.nextInt();
		sc.nextLine();
		System.out.print("   >> 등록사유:");
		dvo.setDreason(sc.nextLine());
		
		dvo.setEcode(dnscode);
		
		if(roomnum >=1001 && roomnum <= 1010) {
		dvo.setRno(roomnum);	
		boolean result = ddao.dnsinsert(dvo);
		
		if(result==true) {
			System.out.println("   >> 등록완료");
			dnsmenu(dnscode,dnsjob);
		}else {
			System.out.println("   >> 등록실패");
			dnsmenu(dnscode,dnsjob);
		}	
		}else {
		System.out.println("   >> 다시 입력해 주세요.");
		dnsjoin();
		}
	}
	
	public void dnslist() {
		System.out.println("==================================================================================================================================================");
		System.out.println("                                                                DNS LIST");
		System.out.println("==================================================================================================================================================");
		List<HotelDnsVO> dvolist=ddao.dnslist();
		
		if(dvolist.size()>0) {
			System.out.println(" 방번호\t작성자\t작성일자\t\t사유");
			System.out.println("--------------------------------------------------------------------------------------------------------------------------------------------------");
			for (HotelDnsVO dvo : dvolist) {
				System.out.println(" "+dvo.getRno()+"\t"+dvo.getEname()+"\t"+dvo.getDnsdate()+"\t"+dvo.getDreason());			
			} 
			System.out.println("==================================================================================================================================================");
			System.out.println("1. 상세보기   2. 뒤로가기  ");
			System.out.print("   >> 선택:");
			int a=sc.nextInt();
			switch(a) {
			case 1: dnsdetail();break;
			case 2: dnsmenu(dnscode,dnsjob);break;
			default :
				System.out.println("   >> 다시 입력해 주세요.");
				dnslist();
				}
			}else {
				System.out.println("   >> 작성된 내용이 없습니다.");
				dnsmenu(dnscode,dnsjob);
			}
		}
	
	public void dnsdetail() {
		System.out.println("==================================================================================================================================================");
		System.out.println("                                                             DNS DETAIL");
		System.out.println("==================================================================================================================================================");
		System.out.print("   >> 조회 할 방번호:");
		int no=sc.nextInt();
		dvo=ddao.dnsdetail(no);
		if(dvo != null) {
			//System.out.println("     등록번호 : "+dvo.getDno());
			System.out.println("     객실 호수 : "+dvo.getRno());
			System.out.println("     사유 : "+dvo.getDreason());
			System.out.println("     작성자 : "+dvo.getEname());
			System.out.println("     직원번호 : "+dvo.getEcode());
			System.out.println("     등록날짜 : "+dvo.getDnsdate());
			System.out.println("==================================================================================================================================================");
			System.out.println(" 1. 정보 수정   2. 정보 삭제   3.뒤로가기");
			System.out.println();
			System.out.print("   >> 선택:");
			int b=sc.nextInt();
			switch(b) {
			case 1: dnsmodify();break;
			case 2: dnsremove();break;
			case 3: dnslist();break;
			default :
				System.out.println("==================================================================================================================================================");
				System.out.println("   >> 다시 입력해 주세요.");
				dnsdetail();
		}
			}else {
				System.out.println("==================================================================================================================================================");
				System.out.println("   >> 해당 아이디가 존재하지 않습니다.");
				dnslist();
			}
	}
	
	public void dnsmodify() {
		System.out.println("==================================================================================================================================================");
		System.out.println("                                                                   DNS UPDATE");
		System.out.println("==================================================================================================================================================");
		
		System.out.print("   >> 객실 호수를 입력하세요: ");
		dvo.setRno(sc.nextInt());
		System.out.print("   >> 수정할 내용을 입력하세요: ");
		sc.nextLine();
		dvo.setDreason(sc.nextLine());
		System.out.println();
		dvo.setEcode(dnscode);
		
		ddao.dnsupdate(dvo);
		
		if(dvo != null) {
			System.out.println("==================================================================================================================================================");
			System.out.println("   >> 수정되었습니다.");	
		}
		dnslist();
	}
	public void dnsremove() {
		System.out.println("==================================================================================================================================================");
		System.out.print("   >> 해당 정보를 삭제하시겠습니까?(Y/N) : ");
		char yon=sc.next().charAt(0);
		switch(yon) {
		case 'y': case 'Y':
			ddao.dnsdelete(dvo.getRno());
			System.out.println("==================================================================================================================================================");
			System.out.println("   >> 삭제되었습니다.");
			dnslist();
		case 'n': case 'N':
			System.out.println("==================================================================================================================================================");
			System.out.println("   >> 목록으로 돌아갑니다.");
			dnslist();
		default :
			System.out.println("==================================================================================================================================================");
			System.out.println("   >> 다시 입력해 주세요.");
			dnsremove();
		}
	}
}