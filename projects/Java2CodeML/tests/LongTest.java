import java.io.*;

class LongTest {
	public boolean check (int a) {
		int res = 0;
		for (int i = 0; i < a; i++) {
			res++;
		}
	
		switch (res) {
			case 5:
				return true;

			default:
				return false;
		}
	}
	
	public static void main (String[] args) {
		LongTest lt = new LongTest();
		int a = 5;
		
		if (lt.check(a) == true) {
			System.out.println("Success!");
		}
	}
}
