import java.math.*;
import java.util.*;

public class rsa_main {
	public static void main(String[] args) {
		BigInteger primeCheck;
		BigInteger msg, en_msg;
		int primeGen;
		int i;
		// 1. primegen 2. primecheck 3. en/decrypt 4. exit

		Scanner input = new Scanner(System.in);
		Scanner input_primcheck = new Scanner(System.in);
		Scanner input_msg = new Scanner(System.in);

		primegen primG = new primegen();
		primecheck prim = new primecheck();
		rsa callRsa = new rsa();

		for (;;) {
			System.out.println("Menu : 1. Prime Number Generator  2. Check Prime Number   3. En/Decrypt   4. Exit");
			System.out.print("Enter the menu number : ");
			i = input.nextInt();

			// primegen
			if (i == 1) {
				System.out.print("Enter bit number: ");
				primeGen = input.nextInt();

				long before_time = System.currentTimeMillis();
				
				long after_time = System.currentTimeMillis();
				long process_time = after_time - before_time;
				process_time = process_time * 1 / 1000;

				System.out.println(process_time + "sec");
				System.out.println("Prime Number : " + primG.makePrime(primeGen));

			}

			// Prime Check
			if (i == 2) {

				System.out.print("primecheck : ");
				primeCheck = input_primcheck.nextBigInteger();
				System.out.println(prim.check_prime(primeCheck));
			}

			// Keygen & Encrypt & Decrypt
			if (i == 3) {
				System.out.print("Keygen : ");
				BigInteger num_1 = input.nextBigInteger();
				System.out.println();
				BigInteger num_2 = input.nextBigInteger();

				callRsa.setP(num_1);
				callRsa.setQ(num_2);
				callRsa.makeOthersKey();
				System.out.println("keygen : " + callRsa.callP() + " "
						+ callRsa.callQ());
				System.out.println("Public key : (" + callRsa.callN() + ", "
						+ callRsa.callE() + ")");
				System.out.println("Private key : (" + callRsa.callN() + ", "
						+ callRsa.callD() + ")");

				System.out.print("Encrypt : " + callRsa.callN() + " "
						+ callRsa.callE() + " ");
				msg = input_msg.nextBigInteger();
				System.out.println(callRsa.encrypt(msg));

				// en_msg = callRsa.encrypt(msg);
				System.out.print("Decrypt : " + callRsa.callN() + " "
						+ callRsa.callD() + " ");
				en_msg = input_msg.nextBigInteger();
				System.out.println(callRsa.decrypt(en_msg));

			}

			if (i == 4) {
				System.out.println("End of program");
				break;
			}
			
		}

		// callRsa.makeKeyPairP(primeGen);
		// callRsa.makeKeyPairQ(primeGen);

		input.close();
		input_primcheck.close();
		input_msg.close();
	}
}