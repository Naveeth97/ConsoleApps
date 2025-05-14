package banksys;

import java.util.*;

public class BankApp {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Scanner scan = new Scanner(System.in);
		
		BankSys bankSystem = new BankSys();
		
		System.out.println("==================== Welcome to Zoho Bank ==================");
		
		int choice;
		
		do {
			
			System.out.println("1. Register New Account");
			System.out.println("2. Already existing user. Login ? ");
			System.out.println("3. Exit");
			
			System.out.println("Enter the valid choice : ");
			
			choice = scan.nextInt();
			
			switch (choice) {
			
			
			case 1:
				
				bankSystem.registerAccount();
				break;
				
			case 2:
				
				bankSystem.loginAccount();
				break;
				
			case 3:
				
				System.out.println("Thanks for using Zoho app..!");
				return;
				
			}
			
			
		} while (choice != 3);
		
		scan.close();

	}

}
