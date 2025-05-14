package banksys;

import java.util.*;

public class BankSys {
	
	static Scanner scan = new Scanner(System.in);
	
	Map<Integer, Customer> customerDetail = new HashMap<Integer, Customer>();
	
	static int customerId = 1000;
	
	public void registerAccount() {
		
		System.out.println("Enter your Name : ");
		String name = scan.next().toUpperCase();
		
		Random random = new Random();
		int randomId = random.nextInt();
		
		System.out.println("Enter your password : ");
		String password = scan.next();

		while (validatePassword(password)) {
			
			System.out.println("Please re-enter your password : ");
			password = scan.next();
		}
		
		password = encryptPassword(password);
		// all the above condition satisfied, we can create a new user
		Customer newUser = new Customer(name, customerId++, random.nextInt(), password);
		customerDetail.put(randomId, newUser);
		
	}
	
	public boolean validatePassword(String password) {
		
		if (password.length() < 6) {
			return true;
		}
		
		int lowerCase = 0;
		int upperCase = 0;
		int numerics = 0;
		
		for (char ch : password.toCharArray()) {
			
			if (ch >= 97 && ch <= 122) {
				lowerCase++;
			} else if (ch >= 65 && ch <= 90) {
				upperCase++;
			} else if (ch >= 48 && ch <= 57) {
				numerics++;
			}
		}
		
		return lowerCase < 2 || upperCase < 2 || numerics < 2;
		
	}
	
	// convert the original password into encrypted one.
	public String encryptPassword(String password) {
		
		String encryptPass = "";
		
		for (char ch : password.toCharArray()) {
			
			encryptPass += (ch == 'z' ? "a" : ch == 'Z' ? "A" : Character.toString(ch + 1));
			
		}
		
		return encryptPass;
		
	}
	
	private void passwordChange(int randomId) {
		
		while (true) {
		    String newPass = scan.next();
		    if (!validatePassword(newPass)) {
		        System.out.println("Invalid format. Try again:");
		        continue;
		    }
		    newPass = encryptPassword(newPass);
		    if (customerDetail.get(randomId).passwords.contains(newPass)) {
		        System.out.println("Used before. Choose a new password.");
		    } else {
		        // Update passwords
		        List<String> list = customerDetail.get(randomId).passwords;
		        if (list.size() >= 3) list.remove(0);
		        list.add(newPass);
		        break;
		    }
		}
		
	}
	
	public void loginAccount() {
		
		System.out.println("Enter your Id : ");
		int randomId = scan.nextInt();
		
		while (!checkRandomId(randomId)) {
			
			System.out.println("Please re-enter the id : ");
			randomId = scan.nextInt();
		}
		
		System.out.println("Enter your password : ");
		
		
		String password = encryptPassword(scan.next());
		
		while (!checkPassword(password, randomId)) {
			
			System.out.println("Please re-enter the password : ");
			password = encryptPassword(scan.next());
		}
		
		int choice;
		
		do {
			
			System.out.println("1. ATM Withdraw");
			System.out.println("2. Cash Deposit");
			System.out.println("3. Transfer Amount to another person ");
			System.out.println("4. Change Password ");
			
			choice = scan.nextInt();
			
			switch (choice) {
			
			case 1:
				ATMWithdraw(randomId);
				break;
				
			case 2:
				cashDeposit(randomId);
				break;
			case 3:
				transferAmount(randomId);
				break;
				
			case 4:
				passwordChange(randomId);
				break;
				
			case 5:
				
				showTopNRecord();
				break;
				
			case -1:
				System.out.println("Logged out Successfully...");
				return;
				
			}
		} while (choice != -1);
		
	}
	
	private void showTopNRecord() {
		
		// apply bubble sort to sort the top n records based on the salary
		int size = customerDetail.size();
		
		int[] highBalance = new int[size];
		
		int idx = 0;
		
		for (int randomId : customerDetail.keySet()) {
			highBalance[idx++] = randomId;
		}
		
		
		for (int i = 0; i < size; i++) {
			
			for (int j = i + 1; j < size - i; j++) {
				
				if (customerDetail.get(highBalance[j - 1]).balance > 
						customerDetail.get(highBalance[j]).balance) {
					
					int tempRandomId = highBalance[j];
					highBalance[j] = highBalance[j - 1];
					highBalance[j - 1] = tempRandomId;
				}
			}
		}
		
		System.out.printf("%-20s %-20s", "Account No", "Balance");
		
		for (int i = 0; i < size; i++) {
			
			System.out.printf("%-20d %-20d", customerDetail.get(highBalance[i]).accountNumber,
						customerDetail.get(highBalance[i]).balance);
		}
		
	}
	
	private void cashDeposit(int randomId) {
		
		System.out.println("Enter the amount of cash will be deposited : ");
		int amount = scan.nextInt();
		
		customerDetail.get(randomId).balance += amount;
		
		customerDetail.get(randomId).transHistory.add(new TransactionHistory("Cash Deposit", 
				customerDetail.get(randomId).balance, amount));
	}
	
	private void ATMWithdraw(int randomId) {
		
		System.out.println("Enter the amount : ");
		float amount = scan.nextInt();
		
		float savingAmount = customerDetail.get(randomId).balance;
		
		if ((savingAmount - amount) > 1000f) {
			customerDetail.get(randomId).balance = savingAmount - amount;
		} else {
			System.out.println("Balance is not sufficient. ");
			return;
		}
		
		customerDetail.get(randomId).transHistory.add(new TransactionHistory("Cash Withdraw", 
				customerDetail.get(randomId).balance, amount));
		
	}
	
	private void transferAmount(int randomId) {
		
		System.out.println("Enter the Id, you want to transfer");
		
		int receiverId = scan.nextInt();
		
		if (!customerDetail.containsKey(receiverId)) {
			System.out.println("Receiver Id is not found..");
			return;
		}
		
		System.out.println("Enter the amount to transfer : ");
		float amount = scan.nextInt();
		
		float savingAmount = customerDetail.get(randomId).balance;
		
		if ((savingAmount - amount) > 1000f) {
			
			customerDetail.get(randomId).balance = savingAmount - amount;
			customerDetail.get(receiverId).balance += amount;
			
		} else {
			
			System.out.println("Balance is not sufficient. ");
			return;
		}
		
		customerDetail.get(randomId).transHistory.add(new TransactionHistory("Amt Transfer to "+receiverId,  
				customerDetail.get(randomId).balance, amount));
		
		customerDetail.get(receiverId).transHistory.add(new TransactionHistory("Amt Credited from"+randomId, 
				customerDetail.get(receiverId).balance, amount));
		
	}
	
	private boolean checkRandomId(int randomId) {
		
		return customerDetail.containsKey(randomId);
	}
	
	private boolean checkPassword(String password, int randomId) {
		
		String OriginalPassword = customerDetail.get(randomId).passwords.get(customerDetail.get(randomId).passwords.size() - 1);
		return password.equals(OriginalPassword);
		
	}
 
}
