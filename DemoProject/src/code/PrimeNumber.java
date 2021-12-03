package code;

import java.util.Scanner;

public class PrimeNumber {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner sc=new Scanner(System.in);  
		int num=sc.nextInt();
		boolean flag = false;
		for(int i=2;i<=num/2;++i) {
			if(num % i==0) {
				flag=true;
				break;
			}
		}
		if(!flag)
			System.out.println("Prime Number");
		else
			System.out.println("Not Prime");

	}

}
