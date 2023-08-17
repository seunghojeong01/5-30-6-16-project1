package hotel.main;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import hotel.dao.HotelRoomControlDAO;
import hotel.vo.HotelRoomControlVO;

public class HotelRoomControlMain {
	private static Scanner sc;
	private HotelRoomControlVO rcvo;
	private HotelRoomControlDAO rcdao;
	public static int session=0;
	public HotelRoomControlMain() {
		sc = new Scanner(System.in);
		rcvo = new HotelRoomControlVO();
		rcdao = new HotelRoomControlDAO();
	}
	
	public void room_list(){//객실 전체 목록 조회
		System.out.println("==================================================================================================================================================");
		System.out.println("                                                              객실 전체 조회");
		System.out.println("==================================================================================================================================================");
		
		ArrayList<HotelRoomControlVO> rcvList = rcdao.room_list();
		
		if(rcvList.size() > 0) {	//등록된 방이 있으면 화면에 표시
			System.out.println("방번호 |  가격  | 방 타입 | 최대인원 |");
			for(int i=0 ; i<rcvList.size() ; i++) {
				rcvo = rcvList.get(i);
				System.out.println(rcvo.getRno() + " | " + rcvo.getRoom_price() + " | " + 
						rcvo.getRoom_type()+ " | " + rcvo.getMax_value() + " | ");
			}		
			System.out.println();				
		} else { 
			System.out.println("   >> 등록된 방이 없습니다.");
		}
		Roomcontrolmain();//메인메뉴 표시
	}
	public void search_room() {//개별 객실 조회
		System.out.print("   >> 조회할 객실 입력:");
		int num=sc.nextInt();
		
		ArrayList<HotelRoomControlVO> rcvList = rcdao.search_room(num);	
		System.out.println("방번호 |  가격  |  방 타입  | 최대인원");
		if(rcvList.size() > 0) {
			rcvo = rcvList.get(0);
			System.out.println(rcvo.getRno() + " | " + rcvo.getRoom_price() + " | " + 
					rcvo.getRoom_type()+ " | " + rcvo.getMax_value());	
			
			System.out.println("");		
			System.out.println("   1:해당 객실 정보 수정");
			System.out.println("   2:해당 객실 삭제");
			System.out.println("   3:뒤로가기");
			
			switch (sc.nextInt()) {
			case 1:
				update_room(rcvo.getRno());
				break;
			case 2:
				delete_room(rcvo.getRno());
				break;
			case 3:
				Roomcontrolmain();
				break;
			default:
				search_room();
				System.out.println("   >> 다시 입력");
				break;
			}
		}
		else {
			System.out.println("   >> 해당 객실이 존재하지 않음");
			Roomcontrolmain();
		}
	}
	public void update_room(int rno) {	//객실 정보 수정
		System.out.print("   >> 수정할 방 가격:");
			rcvo.setRoom_price(sc.nextInt());
		System.out.print("   >> 수정할 최대 인원 수:");
			rcvo.setMax_value(sc.nextInt());
		boolean result = rcdao.update_room(rno,rcvo.getRoom_price(),rcvo.getMax_value());
		if(result == true) {
			System.out.println("   >> 수정 완료");
			Roomcontrolmain();
		} else {
			System.out.println("   >> 수정 실패");
			Roomcontrolmain();
		}
	}
	public void delete_room(int rno) {
		boolean result = rcdao.delete_room(rno);
		if(result == true) {
			System.out.println("   >> 룸 삭제 완료");
			Roomcontrolmain();
		} else {
			System.out.println("   >> 룸 삭제 실패");
			Roomcontrolmain();
		}
	}

	public void insert_room() {

		System.out.println("   ((객실 타입))  1.single  |  2.double  |  3.luxury");
		System.out.print("   1. 객실 타입:");
		int num = sc.nextInt();
		if (num == 1) {
			rcvo.setRoom_type("single  ");
		}
		else if (num == 2) {
			rcvo.setRoom_type("double  ");
		}
		else if (num == 3) {
			rcvo.setRoom_type("luxury =>");
		}
		else {
			System.out.println("   >> 다시 입력하세요");
			insert_room();
		}
		System.out.println("   2. 객실 가격:");
		rcvo.setRoom_price(sc.nextInt());
		System.out.println("   3. 최대 수용 인원:");
		rcvo.setMax_value(sc.nextInt());
		int result = rcdao.insert_room(rcvo);
		if (result == 1) {
			System.out.println("   >> 룸 추가 완료");
			Roomcontrolmain();
		} else {
			System.out.println("   >> 룸 추가 실패");
			Roomcontrolmain();
		}
	}	
	
	public void Roomcontrolmain() {
		HotelRoomInfoMain hm = new HotelRoomInfoMain();
		HotelMain hmain = new HotelMain();
		System.out.println("==================================================================================================================================================");
		System.out.println("                                                              객실 정보 관리");
		System.out.println("==================================================================================================================================================");
		
		System.out.println("   1. 객실 전체 목록 조회");
		System.out.println("   2. 개별 객실 조회");
		System.out.println("   3. 객실 추가");
		System.out.println("   4. 뒤로가기");
		System.out.println();
		System.out.print("   >> 선택 :");
		int num = sc.nextInt();	
		switch (num) {
		case 1:	
			room_list();
			break;
		case 2:
			search_room();
			break;
		case 3:
			insert_room();
			break;
		case 4:
			hmain.menuadmin();
			break;
		default:
			System.out.println("   >> 다시 입력하세요.");
			Roomcontrolmain();
			break;
		}
	}
}