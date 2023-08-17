package hotel.vo;

import java.util.Date;

//음식 주문 관리
public class HotelFoodOrderVO {
	

	// 음식메뉴번호를 활용해서 (join) price를 끌어쓸 수 있게 하기
	private int foodOrderNo;		//주문번호 - PK : 시퀀스
	private int rno;						//음식 주문 객실		f?
	private int foodCategory;		//음식 카테고리 
	private int foodNum;			//음식메뉴번호 - 음식메뉴테이블의 FK		f?
	private String foodName;		//주문 메뉴 - 메뉴번호를 통해 뽑아낼 수 있음
	private int foodAmount;		//음식 주문 수량
	private int price; 					//주문가격 -> 나중에 비용산정을 위해서..!
	// ecode를 활용해서 직원이름 뽑아냄 (join)
	private String ecode;				//주문받은 직원의 고유번호 (main에서 입력받나? session에서 뽑아오나?)   f?
	private Date foodDate;			//음식주문일
	private String eName;
	
//수정 시 수정할만한 내용 : 객실, 카테고리, 음식메뉴번호, 수량, 가격, ecode
	//main에서 입력받을 것은
	//객실호수, 음식카테고리번호, 음식메뉴번호, 수량
	
	
	//생성자
	public HotelFoodOrderVO() {}
	
	
	//main에서 입력받을 것만 들어간 생성자
	
	
	//자동입력될 속성을 제외한 생성자
	
	public HotelFoodOrderVO(int rno, int foodCategory, int foodNum, int foodAmount, String ecode) {
		super();
		this.rno = rno;
		this.foodCategory = foodCategory;
		this.foodNum = foodNum;
		this.foodAmount = foodAmount;
		this.ecode = ecode;
	}


	//전체항목 생성자
	public HotelFoodOrderVO(int foodOrderNo, int rno, int foodCategory, int foodNum, String foodName, int foodAmount,
			int price, String ecode, Date foodDate, String eName) {
		super();
		this.foodOrderNo = foodOrderNo;
		this.rno = rno;
		this.foodCategory = foodCategory;
		this.foodNum = foodNum;
		this.foodName = foodName;
		this.foodAmount = foodAmount;
		this.price = price;
		this.ecode = ecode;
		this.foodDate = foodDate;
		this.eName = eName;
	}
	
	

	
	// getter, setter 메서드
	
	public int getFoodOrderNo() {
		return foodOrderNo;
	}



	public void setFoodOrderNo(int foodOrderNo) {
		this.foodOrderNo = foodOrderNo;
	}


	public int getRno() {
		return rno;
	}


	public void setRno(int rno) {
		this.rno = rno;
	}


	public int getFoodCategory() {
		return foodCategory;
	}


	public void setFoodCategory(int foodCategory) {
		this.foodCategory = foodCategory;
	}


	public int getFoodNum() {
		return foodNum;
	}


	public void setFoodNum(int foodNum) {
		this.foodNum = foodNum;
	}


	public String getFoodName() {
		return foodName;
	}


	public void setFoodName(String foodName) {
		this.foodName = foodName;
	}


	public int getFoodAmount() {
		return foodAmount;
	}


	public void setFoodAmount(int foodAmount) {
		this.foodAmount = foodAmount;
	}


	public int getPrice() {
		return price;
	}


	public void setPrice(int price) {
		this.price = price;
	}


	public String getEcode() {
		return ecode;
	}


	public void setEcode(String ecode) {
		this.ecode = ecode;
	}


	public Date getFoodDate() {
		return foodDate;
	}


	public void setFoodDate(Date foodDate) {
		this.foodDate = foodDate;
	}


	public String getEName() {
		return eName;
	}


	public void setEName(String eName) {
		this.eName = eName;
	}

	
}