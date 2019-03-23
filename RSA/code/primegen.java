import java.math.*;
import java.security.SecureRandom;

public class primegen {
	
	public static BigInteger makePrime(int bitLen){
		SecureRandom random = new SecureRandom();
		BigInteger n = null;
		boolean isPrime = false;
		
		BigInteger two = BigInteger.valueOf(2);
		
		while(!isPrime)
		{
			n = new BigInteger(bitLen, random);
			System.out.println("N : " + n);
			if (!n.mod(two).equals(BigInteger.ZERO)) // if odd number
				isPrime = primecheck.MillerRabinTest(n); // Miller-Rabin Primality Test
			
		}
		
		return n;
	}
}

/**
public static boolean check_prime(BigInteger n) {
		if (!n.mod(two).equals(BigInteger.ZERO)) // if odd number
			isPrime = MillerRabinTest(n); // Miller-Rabin Primality Test

		return isPrime;
	}

*/