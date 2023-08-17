package hotel.main;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Scanner;
import hotel.dao.HotelRoomInfoDAO;
import hotel.vo.HotelRoomControlVO;
import hotel.vo.HotelRoomInfoVO;

public class HotelRoomInfoMain {//RoomDB 방관련 입니다~~
	private static Scanner sc;
	private static String sessionemp;
	private HotelRoomInfoVO rivo;
	private HotelRoomInfoDAO ridao;
	public static int session=0;
	public HotelRoomInfoMain(){
		sc = new Scanner(System.in);
		rivo = new HotelRoomInfoVO();
		ridao = new HotelRoomInfoDAO();
	}
	public void checkin(){//체크인 접수
	System.out.print("   1. 객실 호수:");
		rivo.setRno(sc.nextInt());
	System.out.print("   2. 예약자명:");
		rivo.setCname(sc.next());
	System.out.print("   3. 전화번호:");
		rivo.setCphone(sc.next());
	System.out.print("   4. 이메일:");
		rivo.setCemail(sc.next());
	System.out.print("   5. 퇴실일( 예시-> yyyt-mm-dd ) :");
		String now =sc.next(); //yyyy-mm-dd 형식을 지켜야 함
		java.sql.Date date = java.sql.Date.valueOf(now);
		rivo.setCout_date(date);	
	int result = ridao.checkin(rivo,date);
		if(result == 1) {
			System.out.println("   >> 체크인 완료");
			infomain(sessionemp);
		} 
		else{
			System.out.println("   >> 체크인 실패");
			infomain(sessionemp);
		}
	}
	
	public void check_out_rs(String str) {//체크아웃 과정
		sessionemp = str;
		System.out.print("   >> 체크아웃할 객실 호수:");
		int num = sc.nextInt();

		System.out.println("==================================================================================================================================================");
		System.out.println("                                                              체크아웃 폼");
		System.out.println("==================================================================================================================================================");
		
		
		ArrayList<HotelRoomInfoVO> rcvList = ridao.checkout_select(num);
		int rno=num;
		if(rcvList.size() > 0) {	//등록된 방이 있으면 화면에 표시
			System.out.println("| 방번호 | 이름 |  퇴실예정일  | 룸서비스 가격 |");
			for(int i=0 ; i<rcvList.size() ; i++) {
				rivo = rcvList.get(i);
				System.out.println("| "+ rivo.getRno() + " | " + rivo.getCname() + " | " + 
						 rivo.getCout_date()+ "  |  " + rivo.getRoomservice_total());
			}
		}
		System.out.println("   >> 퇴실 처리 하시겠습니까?");
		System.out.print("   1.결제 처리");
		System.out.print("   2.뒤로 가기");
		int ck_num=sc.nextInt();
		switch (ck_num) {
		case 1:
			checkout(rno);
			break;
		case 2:
			infomain(str);
			break;
		default:
			System.out.print("   >> 재입력:");
			check_out_rs(sessionemp);
			break;
		}
	}

	public void checkout(int num) {//체크아웃 접수
		int result = ridao.checkout(num);
		if(result == 1) {
			System.out.println("   >> 체크아웃 완료");
			infomain(sessionemp);
		} else {
			System.out.println("   >> 체크아웃 실패");
			infomain(sessionemp);
		}
	}
	
	public void check_roomlist() {//전체 객실 현황 조회
		System.out.println("==================================================================================================================================================");
		System.out.println("                                                          전체 객실 현황 조회");
		System.out.println("==================================================================================================================================================");
		
		
		ArrayList<HotelRoomInfoVO> rcvList = ridao.check_roomlist();
		if(rcvList.size() > 0) {	//등록된 방이 있으면 화면에 표시
			System.out.println("| 방번호 | 이름  |   입실일    |    퇴실예정일   |  청소 현황 | 체크인유무 | DNS유무 ");
			System.out.println("--------------------------------------------------------------------------------------------------------------------------------------------------");
			for(int i=0 ; i<rcvList.size() ; i++) {	
				rivo = rcvList.get(i);
				
				System.out.println("| "+ rivo.getRno() + " | " + rivo.getCname() + " | " + 
						rivo.getCin_date1() + " | " + rivo.getCout_date1()+ "  |  " + rivo.getRoom_clean()
						+ "   | " + rivo.getCheck_in() + "    | " + rivo.getDns_check());
			}					
		} else { 
			System.out.println("   >> 등록된 방이 없습니다.");
		}
		infomain(sessionemp);//메인메뉴 표시	
	}
	public void check_room() {//객실별 현황 조회
		System.out.print("   >> 객실 번호:");
		int num=sc.nextInt();
		
		System.out.println("==================================================================================================================================================");
		System.out.println("                                                          각 객실별 현황 조회");
		System.out.println("==================================================================================================================================================");
		
		ArrayList<HotelRoomInfoVO> rcvList = ridao.check_room(num);
		
		if(rcvList.size() > 0) {	//등록된 방이 있으면 화면에 표시
			System.out.println("| 방번호 | 이름  |   입실일    |    퇴실예정일   |  청소 현황 | 체크인유무 | DNS유무 ");
			System.out.println("--------------------------------------------------------------------------------------------------------------------------------------------------");
			for(int i=0 ; i<rcvList.size() ; i++) {
				rivo = rcvList.get(i);
				System.out.println("| "+ rivo.getRno() + " | " + rivo.getCname() + " | " + 
						rivo.getCin_date1() + " | " + rivo.getCout_date1()+ "  |  " + rivo.getRoom_clean()
						+ "   | " + rivo.getCheck_in() + "    | " + rivo.getDns_check());
			}
			System.out.println();
			System.out.println("   1. 객실 청소 상태 변경");	
			System.out.println("   2. 뒤로 가기");
			System.out.println();
			System.out.print("   >> 선택 :");
			int num2=sc.nextInt();
			switch (num2) {
			case 1:		
				clean_update(num,sessionemp);
				break;
			case 2:
				infomain(sessionemp);
			default:
				System.out.println("   >> 잘못 입력 하셨습니다");
				infomain(sessionemp);
				break;
			}
		} else { 
			System.out.println("   >> 등록된 방이 없습니다.");
		}
		infomain(sessionemp);//메인메뉴 표시	
	}
	public void check_res(){//입실가능객실 목록 조회
		
		System.out.println("==================================================================================================================================================");
		System.out.println("                                                          입실 가능 객실 조회");
		System.out.println("==================================================================================================================================================");
		
		ArrayList<HotelRoomInfoVO> rcrList = ridao.check_res();
		
		if(rcrList.size() > 0) {	//등록된 방이 있으면 화면에 표시
			System.out.println("| 방번호 | 체크인 유무 |");
			for(int i=0 ; i<rcrList.size() ; i++) {
				rivo = rcrList.get(i);
				System.out.println("  "+rivo.getRno() + " |    " + rivo.getCheck_in());
			}		
			System.out.println("");				
		} else { 
			System.out.println("   >> 예약가능한 방이 없습니다.");
		}
		infomain(sessionemp);	
	}
	public void clean_update(int num,String str) {//객실 변경
	sessionemp=str;
	int rno=num;
	String clean_status = null;
	System.out.println();
	System.out.print("   1.dirty  ");
	System.out.print("   2.clean  ");
	System.out.print("   3.inspect");
	System.out.println();
	System.out.print("   >> 선택:");
	int clean_int = sc.nextInt();
	if(clean_int==1) {
		clean_status="dirty";
	}
	else if(clean_int==2) {
		clean_status="clean";
	}	
	else if(clean_int==3) {
		clean_status="inspect";
	}	
	else {
		System.out.println("   >> 다시 입력하세요.");
		clean_update(rno,sessionemp);
	}
	int result = ridao.clean_update(num,clean_status);
	if(result == 1) {
		System.out.println("   >> 객실 청소상태 변경완료");
	} else {
		System.out.println("   >> 객실 청소상태 변경실패");
		}
	infomain(sessionemp);
	}
	public void room_change() {//객실 변경
		System.out.print("   >> 현재 입실된 객실 호수:");
		int num1=sc.nextInt();
		System.out.print("   >> 이동할 객실 호수:");
		int num2=sc.nextInt();
	boolean result = ridao.room_change(num1,num2);
	if(result == true) {
		System.out.println("   >> 객실 변경 완료");
	} else {
		System.out.println("   >> 객실 변경 실패");
		}
	infomain(sessionemp);	
	}	
	public void infomain(String stremp){//객실 관리
		sessionemp = stremp;
		HotelRoomInfoMain sm = new HotelRoomInfoMain();
		HotelMain hmain = new HotelMain();
		System.out.println("==================================================================================================================================================");
		System.out.println("                                                                   객실 관리 메뉴");
		System.out.println("==================================================================================================================================================");
		System.out.println("   1. 체크인 접수");
		System.out.println("   2. 체크아웃 접수");
		System.out.println("   3. 전체 객실 현황 조회");
		System.out.println("   4. 객실 별 현황 조회");
		System.out.println("   5. 입실 가능한 객실 목록");
		System.out.println("   6. 객실 변경");
		System.out.println("   7. 뒤로가기");
		System.out.println();
		System.out.print("   >> 선택 :");
		int num = sc.nextInt();	
		switch (num) {
		case 1:	
			checkin();
			break;
		case 2:
			check_out_rs(sessionemp);
			break;
		case 3:
			check_roomlist();
			break;
		case 4:	
			check_room();
			break;
		case 5:
			check_res();
			break;
		case 6:
			room_change();
			break;
		case 7:
			if(sessionemp.equals("ADM")){
				hmain.menuadmin();
			}
			else{
				hmain.menuemp();
			}
			break;
		default:
			System.out.println("   >> 다시 입력하세요");
			infomain(sessionemp);
			break;
		}
	}	
}
