package models;

public class MenuManagement {
	private DataAccessObject dao;

	public MenuManagement() {

	}

	public String backController(String jobCode) {
		String  message = null;
		switch(jobCode) {
		case "21": case "22": case "23":
			message = this.ctlReadMenu();
			break;
		case "24":
			this.ctlRegGoods();
			break;
		case "25":
			this.ctlModGoods();
			break;
		case "26":
			this.ctlDelGoods();
			break;
		}
		return message;
	}

	public String backController(String jobCode, String[] data) {
		String message = null;

		switch(jobCode) {
		case "2R":
			message = this.ctlRegMenu(data);
			break;
		case "2M":
			message = this.ctlModMenu(data);

			break;
		case "2D":
			message = this.ctlDelMenu(data);
			break;
		}
		return message;
	}



	/* 2차원 배열 --> String */
	private String toStringFromArray(String[][] menuList) {
		StringBuffer sb = new StringBuffer();

		for(int recordIndex=0; recordIndex<menuList.length; recordIndex++) {
			sb.append(" ");
			for(int colIndex=0; colIndex<menuList[recordIndex].length; colIndex++) {
				if(colIndex == 3) {
					sb.append(menuList[recordIndex][colIndex].equals("1")? "가능": "불가");
				}else {
					sb.append(menuList[recordIndex][colIndex]);
				}

				if(colIndex != menuList[recordIndex].length - 1) {
					sb.append("\t");
					if(colIndex == 1 && menuList[recordIndex][colIndex].length()<6) {
						sb.append("\t");
					}
				}
			}
			sb.append("\n");
		}
		return sb.toString();
	}

	String menuList = null;

	/* 메뉴읽기 */
	private String ctlReadMenu() {
		dao = new DataAccessObject();		
		return this.toStringFromArray(dao.getMenu());
	}


	/* 메뉴등록 */
	private String ctlRegMenu(String[] menuData) {

		dao = new DataAccessObject();
		// DAO에 메뉴등록 요청
		if(dao.setMenu(menuData)) {
			// DAO에 등록된 메뉴 읽기 요청
			menuList = this.toStringFromArray(dao.getMenu());
		}else {
			menuList = "메뉴등록작업이 실패하였습니다. 다시 한번 입력해주세요";
		}

		// 등록메뉴를 리턴
		return menuList;
	}

	/* 메뉴 수정 */
	private String ctlModMenu(String[] data) {
		dao = new DataAccessObject();

		String[][] list = dao.getMenu();

		for(int recordIndex=0; recordIndex<list.length; recordIndex++ ) {
			if(data[0].equals(list[recordIndex][0])){
				list[recordIndex][2] = data[1];
				list[recordIndex][5] = data[2];
				break;


			}
		}
		return (dao.setMenu(list))? this.toStringFromArray(dao.getMenu()):"메뉴수정에 실패하였습니다. 다시 시도해 주세요";
	}
	/* 메뉴 삭제 */
	/* db파일에  저장되어 있는 모든 메뉴를 2차원배열로 가져오기 */
	private String ctlDelMenu(String[] data) {
		 String[][] list;
		 dao = new DataAccessObject();
		list = dao.getMenu();
		
		String[][] newList = new String[list.length-1][list[0].length];
		boolean check = true;
		for(int recordIndex=0;recordIndex<list.length-1;recordIndex++) {
			
			if(!list[recordIndex][0].equals(data[0])) {
				newList[(check)?recordIndex:recordIndex-1] = list[recordIndex]; 
			}else {
				check = false;
			}

		}

		return (dao.setMenu(newList))? this.toStringFromArray(dao.getMenu()):"메뉴를 삭제하는데 실패하였습니다. 다시 시도하시겠습니까?";
	}









	/* 굿즈등록 */
	private void ctlRegGoods() {

	}

	/* 굿즈정보수정 */
	private void ctlModGoods() {

	}

	/* 굿즈 삭제 */
	private void ctlDelGoods() {

	}
}
