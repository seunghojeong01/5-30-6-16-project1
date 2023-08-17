package hotel.vo;

import java.util.Date;

//공지사항에 대한 정보를 저장하는 HotelNoticeVO 클래스
public class HotelNoticeVO {

	private int num; //공지사항 등록번호
	private String ecode; //sessionid (공지사항작성자)
	private String ename; //작성자 이름
	private String title; //공지사항제목
	private String content; //공지사항내용
	private Date noticeDate; //공지등록날짜
	
	public HotelNoticeVO() {
		
	}

	public HotelNoticeVO(int num, String ecode, String title, String content, Date noticeDate) {
		super();
		this.num = num;
		this.title = title;
		this.content = content;
		this.noticeDate = noticeDate;
		this.ecode = ecode;
		this.ename = ename;
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

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getNoticeDate() {
		return noticeDate;
	}

	public void setNoticeDate(Date noticeDate) {
		this.noticeDate = noticeDate;
	}

	public String getEname() {
		return ename;
	}

	public void setEname(String ename) {
		this.ename = ename;
	}
	

}