package views;

import java.util.Scanner;

import controllers.FrontController;

public class View {

	public View() {
		this.ctlView();
	}

	/* 데이터의 흐름 제어 */
	private void ctlView() {
		FrontController fc = null;
		String title, message = "", jobCode = "";
		int menuCode, subCode;
		String[][] menu = saveMenu();
		title = this.makeTitle();
		String[] userData = null;

		while (true) {

			if (jobCode.equals("")) {

				menuCode = this.ctlMain(title, message, menu);
				if (menuCode == 0) {
					break;
				} else {
					message = "";
				}

				if (menuCode != 4) {
					subCode = this.ctlSub(title, message, menu[menuCode - 1]);
					if (subCode != 0) {
						jobCode = menuCode + "" + subCode;
						message = "";

						/* 서버 서비스 요청 */
						fc = new FrontController();
						message = fc.getRequest(jobCode);
					} else {
						message = "요청이 취소되었습니다.";
					}
				} else {
					boolean posDispCheck = true;
					String[][] finalOrder = null;
					String[][] orders = new String[100][];
					int recordIndex = -1;
					while (true) {
						recordIndex++;
						// 판매화면 이동
						String pos = this.posDisplay(title, orders, posDispCheck);
						if (pos.toUpperCase().equals("Y"))
							break;
						// 서버에 상품코드 전달 후 상품정보 받기
						// 1001,(HOT)아메리카노,2500,1,10
						jobCode = "4S";
						String[] search = {pos};
						fc= new FrontController();
						fc.getRequest(jobCode, search);
						
						
//						String[] order = fc.getRequest(pos).split(",");
//						posDispCheck = false;
//						orders[recordIndex] = order;
					}
					// finalOrder 할당
					finalOrder = new String[recordIndex][];
					// orders --> finalOrder
					for (int i = 0; i < finalOrder.length; i++) {
						finalOrder[i] = orders[i];
						

					}
					
					// 주문데이터 전송
					System.out.print(finalOrder[0]);

				}
			} else {

				if (jobCode.equals("21")) {
					userData = this.regMenu(title, message);
					if (userData != null) {
						jobCode = "2R";
						message = fc.getRequest(jobCode, userData);
						jobCode = "21";
					} else {
						jobCode = "";
						message = "";
					}
				} else if (jobCode.equals("31")) {
					userData = this.ctlRegMember(title, message);
					if (userData != null) {
						jobCode = "3R";
						message = fc.getRequest(jobCode, userData);
						jobCode = "31";
					} else {
						jobCode = "";
						message = "";
					}
				} else if (jobCode.equals("22")) {
					/* 메뉴수정 */
					userData = this.ctlModMenu(title, message);
					if (userData != null) {
						jobCode = "2M";
						message = fc.getRequest(jobCode, userData);
						jobCode = "22";
					}
				} else if (jobCode.equals("23")) {
					/* 메뉴삭제 */
					userData = this.ctlDelMenu(title, message);
					if (userData != null) {
						jobCode = "2D";
						message = fc.getRequest(jobCode, userData);
						jobCode = "23";
					}
				} else if (jobCode.equals("32")) {
					/* 회원수정 */
					userData = this.ctlModMember(title, message);
					if (userData != null) {
						jobCode = "3M";
						message = fc.getRequest(jobCode, userData);
						jobCode = "32";
					}
				} else if (jobCode.equals("33")) {
					/* 회원삭제 */
					userData = this.ctlDelMember(title, message);
					if (userData != null) {
						jobCode = "3D";
						message = fc.getRequest(jobCode, userData);
						jobCode = "33";
					}
				}
			}
		}
	}

	/* 판매화면 제어 메서드 */
	private String posDisplay(String title, String[][] orders, boolean check) {
		String userData = "Y";
		int countOrder = this.countRecord(orders);

		while (true) {
			if (check) {
				// 프로그램 타이틀 출력
				this.display(title);
				// !orders[0][0].equals("") >> message 출력 >> 장바구니(1)
				if (orders[0]!=null) {
					this.display(" 장바구니(" + countOrder + ")");
				}
				if (countOrder > 0) {
					this.display(
							" --------------------------------------------------\n"
							+ "  순번     코드          상품        가격   수량   할인율\n"
							+ " --------------------------------------------------\n");
				}
				if (userData.equals("Y")) {
					this.display(" [ GOODS SEARCH ] : ");
				} else {
					this.display(" ---------------------------------- Sure?(y/n) : ");
				}
				userData = this.menuInput().toUpperCase();
				break;
			} else {
				/*
				 * orders[countOrder-1] [0] [1] [2] [4] 10000001 (HOT)아메리카노 2500 0%
				 */
				this.display("         " + orders[countOrder - 1][0] + "\t" + orders[countOrder - 1][1] + "\t"
						+ orders[countOrder - 1][2] + "\t" + orders[countOrder - 1][4] + "%\n");
				while (true) {
					this.display(" ____________________________________ QUANTITY : ");
					String qty = this.menuInput();
					if (this.toInt(qty)) {
						orders[countOrder - 1][3] = qty;
						break;
					}
				}

				this.display(" ____________________________________ ADD?(Y/N): ");
				userData = this.menuInput().toUpperCase();
				check = true;
			}
		}

		// 상품코드 사용자 입력 >> return
		// ctlView >> orders

		return userData;
	}

	/* orders에 저장된 레코드 개수 파악 */
	private int countRecord(String[][] orders) {
		int recordIndex;
		for (recordIndex = 0; recordIndex < orders.length; recordIndex++) {
			if (orders[recordIndex] == null)
				break;
		}

		return recordIndex;
	}

	private String[] ctlModMenu(String title, String menuList) {
		String[] item = { "코드 ", "이름 ", "가격 ", "상태 ", "분류 ", "할인 " };
		String[] modMenu = null;

		this.display(title + "\n");
		this.display(this.makeMenuTitle("메뉴 리스트"));

		for (int itemIndex = 0; itemIndex < item.length; itemIndex++) {
			if (itemIndex == 0) {
				this.display(" ");
			}
			this.display(item[itemIndex] + "\t");
			if (itemIndex == 1) {
				this.display("\t");
			}
		}

		this.display("\n ---------------------------------------------------\n");
		this.display("\n" + menuList);
		this.display(" ---------------------------------------------------\n\n");

		this.display(" 메뉴항목을 수정하시겠습니까?(y/n) : ");
		if (this.menuInput().toUpperCase().equals("Y")) {
			modMenu = new String[3];
			this.display(" 메뉴코드\t: ");
			modMenu[0] = this.menuInput();
			this.display(" 판매가격\t: ");
			modMenu[1] = this.menuInput();
			this.display(" 할인율\t: ");
			modMenu[2] = this.menuInput();
		}

		return modMenu;
	}

	private String[] ctlDelMenu(String title, String menuList) {
		String[] item = { "코드 ", "이름 ", "가격 ", "상태 ", "분류 ", "할인 " };
		String[] delMenu = null;

		this.display(title + "\n");
		this.display(this.makeMenuTitle("메뉴 리스트"));

		for (int itemIndex = 0; itemIndex < item.length; itemIndex++) {
			if (itemIndex == 0) {
				this.display(" ");
			}
			this.display(item[itemIndex] + "\t");
			if (itemIndex == 1) {
				this.display("\t");
			}
		}

		this.display("\n ---------------------------------------------------\n");
		this.display("\n" + menuList);
		this.display(" ---------------------------------------------------\n\n");

		this.display(" 메뉴항목을 삭제하시겠습니까?(y/n) : ");
		if (this.menuInput().toUpperCase().equals("Y")) {
			delMenu = new String[1];
			this.display(" 메뉴코드\t: ");
			delMenu[0] = this.menuInput();
		}

		return delMenu;
	}

	private String[] ctlModMember(String title, String memberList) {
		String[] item = { "코드 ", "이름 ", "전번 " };
		String[] modMember = null;

		this.display(title + "\n");
		this.display(this.makeMenuTitle("회원 리스트"));

		for (int itemIndex = 0; itemIndex < item.length; itemIndex++) {
			if (itemIndex == 0) {
				this.display(" ");
			}
			this.display(item[itemIndex] + "\t");
			if (itemIndex == 1) {
				this.display("\t");
			}
		}

		this.display("\n ---------------------------------------------------\n");
		this.display("\n" + memberList);
		this.display(" ---------------------------------------------------\n\n");

		this.display(" 회원정보를 수정하시겠습니까?(y/n) : ");
		if (this.menuInput().toUpperCase().equals("Y")) {
			modMember = new String[2];
			this.display(" 회원코드\t: ");
			modMember[0] = this.menuInput();
			this.display(" 전화번호\t: ");
			modMember[1] = this.menuInput();
		}

		return modMember;
	}

	private String[] ctlDelMember(String title, String memberList) {
		String[] item = { "코드 ", "이름 ", "전번 " };
		String[] delMember = null;

		this.display(title + "\n");
		this.display(this.makeMenuTitle("회원 리스트"));

		for (int itemIndex = 0; itemIndex < item.length; itemIndex++) {
			if (itemIndex == 0) {
				this.display(" ");
			}
			this.display(item[itemIndex] + "\t");
			if (itemIndex == 1) {
				this.display("\t");
			}
		}

		this.display("\n ---------------------------------------------------\n");
		this.display("\n" + memberList);
		this.display(" ---------------------------------------------------\n\n");

		this.display(" 회원정보를 수정하시겠습니까?(y/n) : ");
		if (this.menuInput().toUpperCase().equals("Y")) {
			delMember = new String[1];
			this.display(" 회원코드\t: ");
			delMember[0] = this.menuInput();
		}

		return delMember;
	}

	private String[] ctlRegMember(String title, String memberList) {
		String[] item = { "코드 ", "이름 ", "전번 " };
		String[] regMember = new String[item.length];

		this.display(title + "\n");
		this.display(this.makeMenuTitle("회원리스트"));

		for (int itemIndex = 0; itemIndex < item.length; itemIndex++) {
			if (itemIndex == 0) {
				this.display(" ");
			}
			this.display(item[itemIndex] + "\t");
			if (itemIndex == 1) {
				this.display("\t");
			}
		}

		this.display("\n ---------------------------------------------------\n");
		this.display("\n" + memberList);
		this.display(" ---------------------------------------------------\n\n");

		this.display(" 회원등록을 하시겠습니까?(y/n) : ");
		if (this.menuInput().toUpperCase().equals("Y")) {
			while (true) {
				this.display(this.makeMenuTitle("등록할 회원"));

				for (int index = 0; index < item.length; index++) {
					this.display(item[index] + ": ");
					regMember[index] = this.menuInput();
				}

				this.display("________________________________ CONFIRM?(Y/N) : ");
				if (this.menuInput().toUpperCase().equals("Y")) {
					break;
				}
			}
		} else {
			regMember = null;
		}

		return regMember;
	}

	private String[] regMenu(String title, String message) {
		String[] item = { "코드 ", "이름 ", "가격 ", "상태 ", "분류 ", "할인 " };
		String[] regMenu = new String[item.length];

		this.display(title + "\n");
		this.display(this.makeMenuTitle("등록된 메뉴"));
		for (int itemIndex = 0; itemIndex < item.length; itemIndex++) {
			if (itemIndex == 0) {
				this.display(" ");
			}
			this.display(item[itemIndex] + "\t");
			if (itemIndex == 1) {
				this.display("\t");
			}
		}
		this.display("\n ---------------------------------------------------\n");
		this.display("\n" + message);
		this.display(" ---------------------------------------------------\n\n");

		this.display(" 메뉴등록을 하시겠습니까?(y/n) : ");
		if (this.menuInput().toUpperCase().equals("Y")) {
			while (true) {
				this.display(this.makeMenuTitle("등록할 메뉴"));

				for (int index = 0; index < item.length; index++) {
					this.display(item[index] + ": ");
					regMenu[index] = this.menuInput();
				}

				this.display("________________________________ CONFIRM?(Y/N) : ");
				if (this.menuInput().toUpperCase().equals("Y")) {
					break;
				}
			}
		} else {
			regMenu = null;
		}

		return regMenu;
	}

	/* 메인화면 제어 및 사용자 데이터 수집 */
	private int ctlMain(String title, String message, String[][] menu) {
		int menuCode;
		while (true) {
			this.display(title);
			this.display(message + "\n\n");
			this.display(this.makeMenuTitle("MAIN"));
			this.display(this.makeMenu(menu));
			this.display(" _____________________________________ SELECT : ");
			menuCode = this.userInput();
			if (menuCode >= 0 && menuCode <= menu.length) {
				break;
			} else {
				message = "메뉴는 0 ~ " + menu.length + "범위이어야 합니다.";
			}
		}
		return menuCode;
	}

	/* 서브메뉴화면 제어 및 사용자 데이터 수집 */
	private int ctlSub(String title, String message, String[] subMenu) {
		int subCode;
		while (true) {
			this.display(title);
			this.display(message + "\n\n");

			this.display(this.makeMenuTitle(subMenu[0]));
			this.display(this.makeMenu(subMenu));
			this.display(" _____________________________________ SELECT : ");

			subCode = this.userInput();
			if (subCode >= 0 && subCode < subMenu.length) {
				break;
			} else {
				message = "메뉴는 0 ~ " + (subMenu.length - 1) + "범위이어야 합니다.";
			}
		}
		return subCode;
	}

	private String makeMenuTitle(String text) {
		StringBuffer menuTitle = new StringBuffer();
		int startSpace = (21 - text.length()) / 2;
		int endSpace = ((21 - text.length()) % 2 == 1) ? startSpace + 1 : startSpace;
		menuTitle.append(" [");
		for (int space = 0; space < startSpace; space++) {
			menuTitle.append(" ");
		}
		menuTitle.append(text);
		for (int space = 0; space < endSpace; space++) {
			menuTitle.append(" ");
		}
		menuTitle.append("]");
		menuTitle.append("__________________________\n\n");

		return menuTitle.toString();
	}

	private String makeMenu(String[] subMenu) {
		StringBuffer buffer = new StringBuffer();

		for (int colIndex = 1; colIndex < subMenu.length; colIndex++) {
			if (colIndex == 1) {
				buffer.append("  ");
			}
			buffer.append(colIndex + ". " + subMenu[colIndex]);
			if (colIndex == subMenu.length - 1) {
				buffer.append("\n\n");
			} else {
				buffer.append("   ");
			}
		}

		return buffer.toString();
	}

	private String makeMenu(String[][] menu) {
		StringBuffer buffer = new StringBuffer();

		for (int rowIndex = 0; rowIndex < menu.length; rowIndex++) {
			if (rowIndex == 0) {
				buffer.append("  ");
			}
			buffer.append(rowIndex + 1 + ". " + menu[rowIndex][0]);
			if (rowIndex == menu.length - 1) {
				buffer.append("\n\n");
			} else {
				buffer.append("   ");
			}
		}

		return buffer.toString();
	}

	private String[][] saveMenu() {
		String[][] menu = { { "매장관리", "매장오픈", "매장클로즈", "금일매출현황", "매출통계" },
				{ "상품관리", "메뉴등록", "메뉴수정", "메뉴삭제", "굿즈등록", "굿즈수정", "굿즈삭제" }, { "회원관리", "회원등록", "회원수정", "회원삭제" },
				{ "판매관리" } };
		return menu;
	}

	private String makeTitle() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("\n\n\n\n+++++++++++++++++++++++++++++++++++++++++++++++++++++\n\n");
		buffer.append("           Point Of Sales SYSTEM v1.0\n\n");
		buffer.append("                              Designed by HoonZzang\n");
		buffer.append("+++++++++++++++++++++++++++++++++++++++++++++++++++++\n");
		return buffer.toString();
	}

	private void display(String text) {
		System.out.print(text);
	}

	private int userInput() {
		Scanner sc = new Scanner(System.in);
		int menuCode;
		while (true) {
			String data = sc.next();
			if (this.toInt(data)) {
				menuCode = Integer.parseInt(data);
				break;
			}
		}
		return menuCode;
	}

	private String menuInput() {
		return (new Scanner(System.in)).next();
	}

	private boolean toInt(String data) {
		boolean isDigit = true;
		try {
			Integer.parseInt(data);
		} catch (Exception e) {
			isDigit = false;
		}
		return isDigit;
	}
}

/*
 * Access Modifier : 접근제한자 public : 모든 클래스(메서드)의 접근을 허용 default : 접근제한자를 생략 ::
 * 같은 패키지 안에 있는 클래스사이에서는 public 다른 패키지에 있는 클래스사이에서는 private 을 적용 protected : 자식
 * 클래스만 접근을 허용 private : 모든 클래스(메서드)의 접근을 제한
 */
