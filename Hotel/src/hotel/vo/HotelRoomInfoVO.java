package hotel.vo;

import java.util.Date;

public class HotelRoomInfoVO {
	private int cno;
	private String cname;
	private String cphone;
	private String cemail;
	private Date cin_date;
	private Date cout_date;
	private String cin_date1;
	private String cout_date1;
	private int roomservice_total;
	private int rno;
	private String check_in;
	private String dns_check;
	private String room_clean;
	

	public String getCheck_in() {
		return check_in;
	}
	public void setCheck_in(String check_in) {
		this.check_in = check_in;
	}
	public String getDns_check() {
		return dns_check;
	}
	public void setDns_check(String dns_check) {
		this.dns_check = dns_check;
	}
	public String getRoom_clean() {
		return room_clean;
	}
	public void setRoom_clean(String room_clean) {
		this.room_clean = room_clean;
	}
	public Date getCin_date() {
		return cin_date;
	}
	public void setCin_date(Date cin_date) {
		this.cin_date = cin_date;
	}
	public Date getCout_date() {
		return cout_date;
	}
	public void setCout_date(Date cout_date) {
		this.cout_date = cout_date;
	}
	public int getCno() {
		return cno;
	}
	public void setCno(int cno) {
		this.cno = cno;
	}
	public String getCname() {
		return cname;
	}
	public void setCname(String cname) {
		this.cname = cname;
	}
	public String getCphone() {
		return cphone;
	}
	public void setCphone(String cphone) {
		this.cphone = cphone;
	}
	public String getCemail() {
		return cemail;
	}
	public void setCemail(String cemail) {
		this.cemail = cemail;
	}
	public int getRoomservice_total() {
		return roomservice_total;
	}
	public void setRoomservice_total(int roomservice_total) {
		this.roomservice_total = roomservice_total;
	}
	public int getRno() {
		return rno;
	}
	public void setRno(int rno) {
		this.rno = rno;
	}
	public String getCin_date1() {
		return cin_date1;
	}
	public void setCin_date1(String cin_date1) {
		this.cin_date1 = cin_date1;
	}
	public String getCout_date1() {
		return cout_date1;
	}
	public void setCout_date1(String cout_date1) {
		this.cout_date1 = cout_date1;
	}

}
