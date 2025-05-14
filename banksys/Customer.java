package banksys;

import java.util.*;

public class Customer {
	
	String name;
	int customerId;
	int accountNumber;
	float balance;
	List<String> passwords = new ArrayList<String>();
	List<TransactionHistory> transHistory = new ArrayList<TransactionHistory>();
	
	public Customer(String name, int customerId, int accountNumber, String password) {
		
		this.name = name;
		this.customerId = customerId;
		this.accountNumber = accountNumber;
		balance = 1000f;
		passwords.add(password);
	}

}

class TransactionHistory {
	
	String transactionType;
	float balance;
	float cash;
	
	public TransactionHistory(String transactionType, float balance, float cash) {
		
		this.transactionType = transactionType;
		this.balance = balance;
		this.cash = cash;
	}
	
}
