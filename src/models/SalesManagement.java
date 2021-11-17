package models;

public class SalesManagement {

	public SalesManagement() {}

	public String backController(String jobCode, String[] data) {
		String message = null;
		switch(jobCode) {
		case "4S":
			 message = this.ctlSales(data);
			
			break;		
		}
	
		return message;
	}

	/* 판매개시 */
	private String ctlSales(String[] data) {
		System.out.print(data[0]);
		return null;	
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
}
