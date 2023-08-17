package hotel.vo;

import java.util.Date;

public class HotelDnsVO {
	private int rno;//방번호
	private String dreason;//DNS사유
	private String ecode;//등록자번호
	private Date dnsdate;//DNS등록 날짜
	private String ename;//등록자 이름
	public HotelDnsVO() {}
	
	public int getRno() {
		return rno;
	}
	public void setRno(int rno) {
		this.rno = rno;
	}
	public String getDreason() {
		return dreason;
	}
	public void setDreason(String dreason) {
		this.dreason = dreason;
	}
	public String getEcode() {
		return ecode;
	}
	public void setEcode(String ecode) {
		this.ecode = ecode;
	}
	public Date getDnsdate() {
		return dnsdate;
	}
	public void setDnsdate(Date dnsdate) {
		this.dnsdate = dnsdate;
	}
	public String getEname() {
		return ename;
	}
	public void setEname(String ename) {
		this.ename = ename;
	}
		
	

}