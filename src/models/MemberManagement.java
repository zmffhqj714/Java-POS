package models;

public class MemberManagement {

	private DataAccessObject dao;

	public MemberManagement() {

	}

	public String backController(String jobCode) {
		String message = null;
		switch(jobCode) {
		case "31":
			message = this.ctlReadMember();
			break;
		case "32":
			message =this.ctlReadMember();
			break;
		case "33":
			message =this.ctlReadMember();
			break;
		}
		return message;
	}

	public String backController(String jobCode, String[] data) {
		String message = null;
		switch(jobCode) {
		case "3R":
			message = this.ctlRegMember(data);
			break;
		case "3M":
			message = this.ctlModMenu(data);
			break;
		case "3D":
			message = this.ctlDelMenu(data);
			break;
		}
		return message;

	}

	//회원정보읽기

	private String ctlReadMember() {
		dao = new DataAccessObject();
		return this.toStringFromArray(dao.getMember());
	}

	private String toStringFromArray(String[][] data) {
		StringBuffer sb = new StringBuffer();

		for(int recordIndex=0; recordIndex<data.length; recordIndex++) {
			sb.append(" ");
			for(int colIndex=0; colIndex<data[recordIndex].length; colIndex++) {
				sb.append(data[recordIndex][colIndex]);

				if(colIndex != data[recordIndex].length - 1) {
					sb.append("\t");
					if(colIndex == 1 && data[recordIndex][colIndex].length()<6) {
						sb.append("\t");
					}
				}
			}
			sb.append("\n");
		}


		return sb.toString();
	}

	/* 회원정보등록 */
	private String ctlRegMember(String[] memberInfo) {
		String message = null;
		dao = new DataAccessObject();
		if(dao.setMember(memberInfo)) {
			message = this.toStringFromArray(dao.getMember());
		}else {
			message = "회원등록작업이 실패하였습니다.\n다시 등록해 주시기 바랍니다.";
		}

		return message;
	}

	/* 회원정보수정 */
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
	/* db파일에  저장되어 있는 모든 멤버를 2차원배열로 가져오기 */
	private String ctlDelMenu(String[] data) {
		String[][] list;
		dao = new DataAccessObject();
		list = dao.getMember();

		String[][] newList = new String[list.length-1][list[0].length];
		boolean check = true;
		for(int recordIndex=0;recordIndex<list.length-1;recordIndex++) {

			if(!list[recordIndex][0].equals(data[0])) {
				newList[(check)?recordIndex:recordIndex-1] = list[recordIndex]; 
			}else {
				check = false;
			}

		}

		return (dao.setMember(newList))? this.toStringFromArray(dao.getMember()):"회원정보를 삭제하는데 실패하였습니다. 다시 시도하시겠습니까?";
	}


}

