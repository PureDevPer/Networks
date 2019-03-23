import java.math.*;
import java.security.SecureRandom;

public class primecheck {
	public static boolean isPrime = false;

	// for opration, convert 0,1,2 to bigInteger
	private static BigInteger zero = BigInteger.valueOf(0);
	private static BigInteger one = BigInteger.valueOf(1);
	private static BigInteger two = BigInteger.valueOf(2);

	public final boolean check_prime(BigInteger n) {
		if (!n.mod(two).equals(BigInteger.ZERO)) // if odd number
			isPrime = MillerRabinTest(n); // Miller-Rabin Primality Test

		return isPrime;
	}

	/**
	 * MillerRabinTest
	 * 
	 * To improve accuracy of primality test. It repeat witness function many
	 * times. a basic number of iteration is 5.
	 * 
	 * true is prime, false is composite
	 */

	public static boolean MillerRabinTest(BigInteger n) {
		for (int i = 0; i < 10; i++) {
			BigInteger a = RandomSmallerN(n); // a is smaller than n

			if (classify_prime(a, n)) // to classify prime, composite
				return false;
		}

		return true;
	}

	/**
	 * classify_prime Function
	 * 
	 * To define whether the number is composite.
	 * 
	 * @param a
	 * @param n
	 * 
	 *            true is composite, false is prime
	 */
	public static boolean classify_prime(BigInteger a, BigInteger n) {
		BigInteger nsubtract1 = n.subtract(one); // n-1
		BigInteger u = nsubtract1; // u = n-1

		// get u, n - 1 = 2^t*u
		// a^n-1 = (a^u)^2^t
		int t = 0; // t of 2^t
		while (u.mod(two).equals(zero)) {
			u = u.divide(two);
			t++;
		}

		BigInteger x0 = null;
		BigInteger x1 = ModulaExponentiation(a, u, n);
		;

		for (int i = 0; i < t; i++) {
			x0 = x1;
			x1 = x0.pow(2).mod(n);

			if (x1.equals(one) && !x0.equals(one) && !x0.equals(nsubtract1))
				return true;
		}

		if (!x1.equals(one))
			return true;

		return false;
	}

	/**
	 * RandomSmallerN Function
	 * 
	 * To make the random number that is smaller than n. So, it makes random bit
	 * length smaller than bit length of n. Then it makes ramdom number of which
	 * length is ramdom bit number.
	 * 
	 * @param n
	 *            : random number
	 * @return
	 */

	public static BigInteger RandomSmallerN(BigInteger n) {
		// if a is zero or a > n, try to again
		BigInteger a;
		do {
			SecureRandom random = new SecureRandom();
			int sn = random.nextInt(n.bitLength()); // random bit length
			a = new BigInteger(sn, random); // random number
		} while (a.equals(zero) || a.compareTo(n) == 1);

		return a;
	}

	/**
	 * ModulaExponentiation Function
	 * 
	 * To get fast modula for exponentiation. d = a^b mod n. ex) b is 19, a^19 =
	 * a^10011 then, a^10 = a^2 mod n, (a^10)^2 mod n, (a^100)^2*a = a^1000*a =
	 * a^1001 mod n repeatly
	 * 
	 * @param a
	 * @param b
	 * @param n
	 * @return d = a^b mod n
	 */
	public static BigInteger ModulaExponentiation(BigInteger a, BigInteger b, BigInteger n) {
		BigInteger d = one;
		String bin = b.toString(2); // int to binary

		for (int i = 0; i < bin.length(); i++) {
			d = d.pow(2).mod(n); // d^2 mod n

			if (bin.charAt(i) == '1') // binary value = 1
				d = d.multiply(a).mod(n); // d*a mod n
		}

		return d;
	}

}
