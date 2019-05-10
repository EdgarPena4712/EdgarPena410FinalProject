package FinalProj;
import java.util.Scanner;

import FinalProj.dbConnect;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
public class FrontEnd {

	public static void main(String[] args) {
	Scanner obj = new Scanner(System.in);
	FrontEnd func = new FrontEnd();
	String userFunction;
	System.out.println("Which page do you wish to see? (admin, client, employee)");
	String page = obj.nextLine();
	
	//admin
	if (page.equalsIgnoreCase("admin")) {
	System.out.println("What do you want to do?"
			+ " (add book,remove book, add employee, remove employee)");
	userFunction = obj.nextLine();
	if (userFunction.equalsIgnoreCase("add book")) {
		//SQL function that adds book
		System.out.println("Type in the book's information in this format (title,author,publisher)");
		String book_data = obj.nextLine();
		String[] split_data = book_data.split(",");
		func.add_book(split_data[0],split_data[1], split_data[2]);
	}
	else if (userFunction.equalsIgnoreCase("remove book")) {
		//SQL function that removes book
		System.out.println("Type in the book_id");
		int book_id = obj.nextInt();
		func.remove_book(book_id);
		
	}
	else if (userFunction.equalsIgnoreCase("add employee")) {
		//SQL function that adds employee
		System.out.println("Type in the employee's information in this format (email,password)");
		String emp_data = obj.nextLine();
		String[] split_data =  emp_data.split(",");
		func.add_employee(split_data[0],split_data[1]);
	}
	else if (userFunction.equalsIgnoreCase("remove employee")) {
		//SQL function that removes employee
		System.out.println("Type in the employee's id");
		int emp_id = obj.nextInt();
		func.remove_employee(emp_id);
	}
	else {
		System.out.print("You typed something incorrectly");
	}
	}
	//client
	if (page.equalsIgnoreCase("client")) {
		System.out.println("sign in/log in"
				+ "(write email-address)");
		String customerMail = obj.nextLine();
		//if statement that checks if email is in borrowers db
		//if not, adds email
		System.out.println("What do you want to do?"
				+ "(find book,rent book, return book");
		String cusFunction = obj.nextLine();
		if (cusFunction.equalsIgnoreCase("find book")) {
			//SQL function that finds the book's ID number 
			System.out.println("Type in the book's title");
			String title = obj.nextLine();
			func.find_book(title);
		}
		else if (cusFunction.equalsIgnoreCase("rent book")) {
			//SQL function that adds bookID and dateBorrowed
			System.out.println("Type in the book's id and your customer id in this format (book_id,cus_id)");
			String rent_data = obj.nextLine();
			String[] split_data = rent_data.split(",");
			func.rent_book(Integer.parseInt(split_data[0]), Integer.parseInt(split_data[1]));
		}
		else if (cusFunction.equalsIgnoreCase("return book")) {
			//SQL function that changes return status to 'Y'
			//and CheckedOut to 'N'
			System.out.println("Type in the book's id and your customer id in this format (book_id,cus_id)");
			String return_data = obj.nextLine();
			String[] split_data = return_data.split(",");
			func.return_book(Integer.parseInt(split_data[0]), Integer.parseInt(split_data[1]));
		}
		else {
			System.out.println("You typed something incorrectly");
		}
		
	}
	
	//employee
	if (page.equalsIgnoreCase("employee")) {
		System.out.println("log in"
				+ "(write email-address)");
		String empMail = obj.nextLine();
		//If statement that checks if email is in Employee table
		//If not returns "Employee no longer works here"
		System.out.println("What do you want to do?"
				+ "(clock-in,clock-out)");
		String empFunction = obj.nextLine();
		if (empFunction.equalsIgnoreCase("clock-in")) {
			//Sql statement that returns password of that employee to variable word
			String word = func.find_password(empMail);
			System.out.println("enter password:");
			String pass = obj.nextLine();
			if (pass.equalsIgnoreCase(word)) {
				System.out.println("type your employee ID");
				int emp_id = obj.nextInt();
				//SQL statement that changes sign in time to current date and time
				func.clock_in(emp_id);
			}
			else {
				System.out.println("Incorrect password");
			}
			
		}
		else if (empFunction.equalsIgnoreCase("clock-out")) {
			//SQl statement that changes the Sign out time to current date and time
			System.out.println("type your employee ID");
			int emp_id = obj.nextInt();
			func.clock_out(emp_id);
		}
		else {
			System.out.println("You typed something incorrectly");
		}
		
		
	}

	}
public void add_book(String title, String author, String publisher) {
	dbConnect obj_conn = new dbConnect();
	Connection conn = obj_conn.get_connection();
	PreparedStatement stat = null;
	try {
		String query = "insert into books (title, author, publisher,checkedout) values (?,?,?,'N')";
		stat = conn.prepareStatement(query);
		stat.setString(1, title);
		stat.setString(2, author);
		stat.setString(3, publisher);
		System.out.println(stat);
		stat.executeUpdate();
	} catch (Exception e) {
		System.out.println(e);
	}
}

public void remove_book(int book_id) {
	dbConnect obj_conn = new dbConnect();
	Connection conn = obj_conn.get_connection();
	PreparedStatement stat = null;
	try {
		String query="delete from books where book_id = ?";
		stat = conn.prepareStatement(query);
		stat.setInt(1,book_id);
		System.out.println(stat);
		stat.executeUpdate();
	} catch (Exception e) {
		System.out.println(e);
	}	
}

public void add_employee(String email, String password) {
	dbConnect obj_conn = new dbConnect();
	Connection conn = obj_conn.get_connection();
	PreparedStatement stat = null;
	try {
		String query = "insert into employees (email, password) values (?,?)";
		stat = conn.prepareStatement(query);
		stat.setString(1, email);
		stat.setString(2, password);
		System.out.println(stat);
		stat.executeUpdate();
	} catch (Exception e) {
		System.out.println(e);
	}
}

public void remove_employee(int emp_id) {
	dbConnect obj_conn = new dbConnect();
	Connection conn = obj_conn.get_connection();
	PreparedStatement stat = null;
	try {
		String query="delete from employees where emp_id = ?";
		stat = conn.prepareStatement(query);
		stat.setInt(1,emp_id);
		System.out.println(stat);
		stat.executeUpdate();
	} catch (Exception e) {
		System.out.println(e);
	}	
}

public void find_book (String title) {
	dbConnect obj_conn = new dbConnect();
	Connection conn =obj_conn.get_connection();
	PreparedStatement stat = null;
	ResultSet rs = null;
	try {
		String query="select * from books where title = ?";
		stat = conn.prepareStatement(query);
		stat.setString(1, title);
		System.out.println(stat);
		rs=stat.executeQuery();
		while(rs.next()){
		System.out.println("book_id -"+rs.getString("book_id"));
		System.out.println("title -"+rs.getString("title"));
		System.out.println("author -"+rs.getString("author"));
		System.out.println("publisher -"+rs.getString("publisher"));
		System.out.println("checkedOut -"+rs.getString("CheckedOut"));
		System.out.println("---------------");
		}	
	} catch(Exception e) {
		System.out.println(e);
	}
}

public void rent_book (int book_id, int cus_id) {
	dbConnect obj_conn=new dbConnect();
	Connection conn=obj_conn.get_connection();
	PreparedStatement stat=null;
	try {
		String query="update borrowers set book_id=?, date_borrowed=now(), return_status='N' where cus_id=?";
		stat=conn.prepareStatement(query);
		stat.setInt(1, book_id);
		stat.setInt(2, cus_id);
		System.out.println(stat);
		stat.executeUpdate();
		query ="update books set CheckedOut='Y' where book_id=?";
		stat = conn.prepareStatement(query);
		stat.setInt(1, book_id);
		System.out.println(stat);
		stat.executeUpdate();
	} catch (Exception e) {
		System.out.println(e);
	}
}

public void return_book (int book_id, int cus_id) {
	dbConnect obj_conn=new dbConnect();
	Connection conn=obj_conn.get_connection();
	PreparedStatement stat=null;
	try {
		String query="update borrowers set book_id= null, date_borrowed=null, return_status='Y' where cus_id=?";
		stat=conn.prepareStatement(query);
		stat.setInt(1, book_id);
		stat.setInt(2, cus_id);
		System.out.println(stat);
		stat.executeUpdate();
		query ="update books set CheckedOut='Y' where book_id=?";
		stat = conn.prepareStatement(query);
		stat.setInt(1, book_id);
		System.out.println(stat);
		stat.executeUpdate();
	} catch (Exception e) {
		System.out.println(e);
	}
	
}

public void clock_in (int emp_id){
		dbConnect obj_conn=new dbConnect();
		Connection conn=obj_conn.get_connection();
		PreparedStatement stat=null;
		try {
			String query="update employees set si=now() where emp_id=?";
			stat=conn.prepareStatement(query);
			stat.setInt(1, emp_id);
			System.out.println(stat);
			stat.executeUpdate();
		} catch (Exception e) {
			System.out.println(e);
		}
	
}

public void clock_out (int emp_id){
	dbConnect obj_conn=new dbConnect();
	Connection conn=obj_conn.get_connection();
	PreparedStatement stat=null;
	try {
		String query="update employees set so=now() where emp_id=?";
		stat=conn.prepareStatement(query);
		stat.setInt(1, emp_id);
		System.out.println(stat);
		stat.executeUpdate();
	} catch (Exception e) {
		System.out.println(e);
	}

}
public String find_password (String email) {
	dbConnect obj_conn = new dbConnect();
	Connection conn =obj_conn.get_connection();
	PreparedStatement stat = null;
	ResultSet rs = null;
	try {
		String query="select password from employees where email = ?";
		stat = conn.prepareStatement(query);
		stat.setString(1, email);
		System.out.println(stat);
		rs=stat.executeQuery();
		while(rs.next()){
		System.out.println("password -"+rs.getString("password"));
		System.out.println("---------------");
		return email;
		}	
	} catch(Exception e) {
		System.out.println(e);
	}
	return email;
}

}
