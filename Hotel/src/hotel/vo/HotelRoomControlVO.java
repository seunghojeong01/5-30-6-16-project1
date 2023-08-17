package hotel.vo;

public class HotelRoomControlVO {
	private int rno;
	private String room_type;
	private String room_clean;
	private String dns_check;//equal로 비교해야될듯?
	private String check_in;//equal로 비교해야될듯?
	private int room_price;
	private int max_value;
	
	
	public int getRno() {
		return rno;
	}
	public void setRno(int rno) {
		this.rno = rno;
	}
	public String getRoom_type() {
		return room_type;
	}
	public void setRoom_type(String room_type) {
		this.room_type = room_type;
	}
	public String getRoom_clean() {
		return room_clean;
	}
	public void setRoom_clean(String room_clean) {
		this.room_clean = room_clean;
	}
	public String getDns_check() {
		return dns_check;
	}
	public void setDns_check(String dns_check) {
		this.dns_check = dns_check;
	}
	public String getCheck_in() {
		return check_in;
	}
	public void setCheck_in(String check_in) {
		this.check_in = check_in;
	}
	public int getRoom_price() {
		return room_price;
	}
	public void setRoom_price(int room_price) {
		this.room_price = room_price;
	}
	public int getMax_value() {
		return max_value;
	}
	public void setMax_value(int max_value) {
		this.max_value = max_value;
	}
	
}
