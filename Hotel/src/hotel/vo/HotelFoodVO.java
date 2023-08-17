package hotel.vo;

//음식메뉴관리
public class HotelFoodVO {

	private int foodNum;		//음식메뉴번호 - PK
	private String foodName;		//음식메뉴이름
	private int foodCategory;		//음식메뉴 카테고리
	private int price;			//음식메뉴가격
	
	
	// 생성자
	public HotelFoodVO() {}
	
	
	// 메뉴번호 제외한 생성자 (메뉴번호 시퀀스 처리할 경우)
	public HotelFoodVO(String foodName, int foodCategory, int price) {
		super();
		this.foodName = foodName;
		this.foodCategory = foodCategory;
		this.price = price;
	}
	

	public HotelFoodVO(int foodNum, String foodName, int foodCategory, int price) {
		super();
		this.foodNum = foodNum;
		this.foodName = foodName;
		this.foodCategory = foodCategory;
		this.price = price;
	}
	
	
	// getter, setter 메서드

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


	public int getFoodCategory() {
		return foodCategory;
	}


	public void setFoodCategory(int foodCategory) {
		this.foodCategory = foodCategory;
	}


	public int getPrice() {
		return price;
	}


	public void setPrice(int price) {
		this.price = price;
	}
	
	
}