package models;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class DataAccessObject {

	public DataAccessObject() {

	}

	//매장상태
	boolean setStoreState(String date, String time, int state) {
		boolean response = false;
		File file = 
				new File("D:\\RestService\\HoonZzang\\pos\\src\\datafile\\storestate.txt");
		try {
			FileWriter writer = new FileWriter(file, true);
			BufferedWriter buffer = new BufferedWriter(writer);

			buffer.write(date + "|" + time + "|" + state);
			buffer.newLine();
			buffer.close();
			response = true;
		} catch (IOException e) {

		}
		return response;
	}

	private int countRecord(String path) {
		int count = 0;
		File file = new File(path);
		try {
			FileReader reader = new FileReader(file);
			BufferedReader buffer = new BufferedReader(reader);
			//1001|아메리카노|2000|1|HOT|10
			while((buffer.readLine()) != null) {
				count++;
			}
			buffer.close();
		}catch(IOException e) {

		}
		return count;
	}

	//메뉴
	public boolean setMenu(String[] data) {
		boolean check = false;
		File file = new File("C:\\REST\\workspace\\pos-jin\\src\\data\\메뉴파일.txt");
		FileWriter writer = null;
		BufferedWriter buffer = null;

		try {
			writer = new FileWriter(file, true);
			buffer = new BufferedWriter(writer);

			for(int colIndex=0; colIndex<data.length; colIndex++) {
				buffer.write(data[colIndex]);
				if(colIndex != data.length-1) {
					buffer.write("|");
				}
			}
			buffer.newLine();
			check = true;
		}catch(IOException e) {

		} 
		finally {
			try {buffer.close();} catch (IOException e) {}
		}

		return check;
	}

	public boolean setMenu(String[][] data) {
		boolean check = false;
		File file = new File("C:\\REST\\workspace\\pos-jin\\src\\data\\메뉴파일.txt");
		FileWriter writer = null;
		BufferedWriter buffer = null;
		StringBuffer line = new StringBuffer();

		try {
			writer = new FileWriter(file);
			buffer = new BufferedWriter(writer);

			for(int recordIndex=0; recordIndex<data.length; recordIndex++) {
				for(int colIndex=0; colIndex<data[0].length; colIndex++) {
					line.append(data[recordIndex][colIndex]);
					line.append((colIndex != data[recordIndex].length-1)? "|" : "\n");	
				}
			}
			buffer.write(line.toString());
			check = true;
		}catch(IOException e) {

		} 
		finally {
			try {buffer.close();} catch (IOException e) {}
		}

		return check;
	}

	public String[][] getMenu(){
		String[][] menuList = new String[this.countRecord("C:\\REST\\workspace\\pos-jin\\src\\data\\메뉴파일.txt")][];
		String menu = null;
		String[] menuInfo = null;

		int index = -1;
		File file = new File("C:\\REST\\workspace\\pos-jin\\src\\data\\메뉴파일.txt");
		try {
			FileReader reader = new FileReader(file);
			BufferedReader buffer = new BufferedReader(reader);
			//1001|아메리카노|2000|1|HOT|10
			while((menu = buffer.readLine()) != null) {
				index++;
				menuInfo = menu.split("\\|");
				menuList[index] = menuInfo;
			}
			buffer.close();
		}catch (IOException e) {
			menuList = null;	
		}

		return menuList;
	}

	//멤버

	public String[][] getMember(){
		/* Service Class 요청에 따른 회원 리스트를 이차원 배열로 작성 후 리턴 */
		String[][] memberList = new String[this.countRecord("C:\\REST\\workspace\\pos-jin\\src\\data\\멤버파일")][];
	
		String[] memberInfo = null;
		File file = new File("C:\\REST\\workspace\\pos-jin\\src\\data\\멤버파일");
		FileReader reader = null;
		BufferedReader buffer = null;
		String line;
		int recordIndex = -1;
		try {
			reader = new FileReader(file);
			buffer = new BufferedReader(reader);
			while((line = buffer.readLine()) != null) {
				recordIndex++;
				String[] member = line.split("\\|");
				memberList[recordIndex] = member;
				buffer.close();
			}
		} catch (Exception e) {

		}

		return memberList;
	}


	public boolean setMember(String[][] memberInfo){
		boolean check = false;
		File file = new File("C:\\REST\\workspace\\pos-jin\\src\\data\\멤버파일.txt");
		FileWriter writer = null;
		BufferedWriter buffer = null;
		StringBuffer line = new StringBuffer();

		try {
			writer = new FileWriter(file, false);
			buffer = new BufferedWriter(writer);

			for(int recordIndex=0; recordIndex<memberInfo.length; recordIndex++) {
				for(int colIndex=0; colIndex<memberInfo[0].length; colIndex++) {
					line.append(memberInfo[recordIndex][colIndex]);
					line.append((colIndex != memberInfo[recordIndex].length-1)? "|" : "\n");	
				}
			}
			buffer.write(line.toString());
			check = true;
		}catch(IOException e) {

		} 
		finally {
			try {buffer.close();} catch (IOException e) {}
		}

		return check;
	}
	
	
	public boolean setMember(String[] memberInfo){
		boolean check = false;
		File file = new File("C:\\REST\\workspace\\pos-jin\\src\\data\\멤버파일.txt");
		FileWriter writer = null;
		BufferedWriter buffer = null;

		try {
			writer = new FileWriter(file, true);
			buffer = new BufferedWriter(writer);

			for(int colIndex=0; colIndex<memberInfo.length; colIndex++) {
				buffer.write(memberInfo[colIndex]);
				if(colIndex != memberInfo.length-1) {
					buffer.write("|");
				}
			}
			buffer.newLine();
			check = true;
		}catch(IOException e) {

		} 
		finally {
			try {buffer.close();} catch (IOException e) {}
		}

		return check;
	}
	
	public boolean setOrder(String[][] data) {
		boolean check = false;
		File file = new File("C:\\REST\\workspace\\pos-jin\\src\\data\\주문파일.txt");
		FileWriter writer = null;
		BufferedWriter buffer = null;
		StringBuffer line = new StringBuffer();

		try {
			writer = new FileWriter(file);
			buffer = new BufferedWriter(writer);

			for(int recordIndex=0; recordIndex<data.length; recordIndex++) {
				for(int colIndex=0; colIndex<data[0].length; colIndex++) {
					line.append(data[recordIndex][colIndex]);
					line.append((colIndex != data[recordIndex].length-1)? "|" : "\n");	
				}
			}
			buffer.write(line.toString());
			check = true;
		}catch(IOException e) {

		} 
		finally {
			try {buffer.close();} catch (IOException e) {}
		}

		return check;
	}

	public String[][] getOrder(){
		String[][] oderList = new String[this.countRecord("C:\\REST\\workspace\\pos-jin\\src\\data\\주문파일.txt")][];
		String oder = null;
		String[] oderInfo = null;

		int index = -1;
		File file = new File("C:\\REST\\workspace\\pos-jin\\src\\data\\주문파일.txt");
		try {
			FileReader reader = new FileReader(file);
			BufferedReader buffer = new BufferedReader(reader);
			//1001|아메리카노|2000|1|HOT|10
			while((oder = buffer.readLine()) != null) {
				index++;
				oderInfo = oder.split("\\|");
				oderList[index] = oderInfo;
			}
			buffer.close();
		}catch (IOException e) {
			oderList = null;	
		}

		return oderList;
	}

	}




/* File >> 사용할 파일의 경로와 이름 지정
 * FileReader, FileWriter
 * BufferedReader, BufferedWriter
 * */
