package models;

import java.text.SimpleDateFormat;
import java.util.Date;

public class StoreManagement {
	private Date d;
	private DataAccessObject dao;
	
	public StoreManagement() {
		// 1. 현재 시스템 날짜와 시간을 취득  yyyyMMddHHmmss
		d = new Date();
		dao = new DataAccessObject();
	}

	public String backController(String jobCode) {
		String message = null;
		switch(jobCode) {
		case "11":
			message = this.storeOpen();
			break;
		case "12":
			this.ctlStoreClose();
			break;
		case "13":
			this.ctlTodaySales();
			break;
		case "14":
			this.ctlSalesAnalisis();
			break;
		}
		return message;
	}

	/*jobCode :: 11  >> 매장오픈 처리 */
	private String storeOpen() {
		boolean response;
		//  1-1. yyyyMMdd & HHmmss
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		// 2. 파일 저장
		//  2-1. 파일 접근 클래스 호출 :: DataAccessObject.class
		// 3. 매장오픈이 처리되었는지 안되었는지 응답 받기  true ::오픈성공 false:오픈실패
		response = dao.setStoreState(sdf.format(d).substring(0, 8), 
				sdf.format(d).substring(8), 1);
			System.out.print(response);
			
		// 4. View에 전달에 메세지 리턴
		return (response)? "매장이 오픈되었습니다." : "매장 오픈이 실패하였습니다.";
	}

	/*jobCode :: 12  >> 매장클로즈 처리 */
	private void ctlStoreClose() {
		
	}

	/*jobCode :: 13  >> 금일 매출 현황 처리 */
	private void ctlTodaySales() {

	}

	/*jobCode :: 14  >> 매출 통계 처리 */
	private void ctlSalesAnalisis(){

	}
}
