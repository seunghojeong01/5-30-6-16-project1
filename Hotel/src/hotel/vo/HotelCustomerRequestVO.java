package hotel.vo;

import java.util.Date;

public class HotelCustomerRequestVO {

	private int num; //요청사항 등록번호
	private int category; //카테고리 항목
	private int rno; //객실호수
	private String reqMessage; //요청사항
	private Date reqDate; //요청일
	private String ecode; //sessionid (고객요청작성자)
	private String ename; //작성자이름
	
	public HotelCustomerRequestVO() {
		
	}
	
	public HotelCustomerRequestVO(int num, int category, int rno, String reqMessage, String ecode, Date reqDate) {
		super();
		this.num = num;
		this.category = category;
		this.rno = rno;
		this.reqMessage = reqMessage;
		this.ecode = ecode;
		this.ename = ename;
		this.reqDate = reqDate;
	}

	public String getEcode() {
		return ecode;
	}

	public void setEcode(String ecode) {
		this.ecode = ecode;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public int getCategory() {
		return category;
	}

	public void setCategory(int category) {
		this.category = category;
	}

	public int getRno() {
		return rno;
	}

	public void setRno(int rno) {
		this.rno = rno;
	}

	public String getReqMessage() {
		return reqMessage;
	}

	public void setReqMessage(String reqMessage) {
		this.reqMessage = reqMessage;
	}

	public Date getReqDate() {
		return reqDate;
	}

	public void setReqDate(Date reqDate) {
		this.reqDate = reqDate;
	}

	public String getEname() {
		return ename;
	}

	public void setEname(String ename) {
		this.ename = ename;
	}

}
 