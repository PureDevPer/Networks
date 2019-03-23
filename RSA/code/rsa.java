import java.math.*;
import java.security.SecureRandom;
import java.util.*;

public class rsa {
	private static BigInteger p; // p is prime
	private static BigInteger q; // q is prime

	private static BigInteger n; // n = p*q

	private static BigInteger e; // public key
	private static BigInteger d; // private key

	public static void makeKeyPairP(int bitLen) {
		p = primegen.makePrime(bitLen);
		System.out.println("P : " + p);
	}

	public static void makeKeyPairQ(int bitLen)
	{
		q = primegen.makePrime(bitLen);
		System.out.println("Q : " + q);
	}
	
	public void setP(BigInteger num1)
	{
		p = num1;
	}
	
	public void setQ(BigInteger num2)
	{
		q = num2;
	}
			
		Scanner a = new Scanner(System.in);
	public void makeOthersKey()
	{
		n = p.multiply(q); // n = pq
		BigInteger phi = p.subtract(BigInteger.ONE).multiply(
				q.subtract(BigInteger.ONE)); // phi(n)=(p-1)(q-1)

		e = makePublicKey(phi); // Create public key
		d = makePrivateKey(phi); // Create private key
	}
	
	
	public static BigInteger makePublicKey(BigInteger phi) {
		SecureRandom random = new SecureRandom();
		BigInteger a, b;
		long c = 2;
		// if e, phi are not relatively prime, e > phi, try to again
		BigInteger pubKey = null; // this is e, public key
		do {
			
			c = c + 1;
			a = BigInteger.valueOf(c);
			pubKey = a;
			//int sn = random.nextInt(phi.bitLength()); // create random number
			//pubKey = new BigInteger(sn, random);
		} while (!rsa_gcd.gcdEuclid(pubKey, phi).equals(BigInteger.ONE)
				|| pubKey.compareTo(phi) == 1);

		return pubKey;
	}

	public BigInteger makePrivateKey(BigInteger phi) {
		BigInteger[] value = rsa_gcd.PlusEuclid(e, phi); // for calculate
		// inverse e
		BigInteger x = value[1]; // Using e^-1 = x

		return x.mod(phi);
	}

	
	
	
	public BigInteger encrypt(BigInteger msg) {
		return primecheck.ModulaExponentiation(msg, e, n);
	}

	/**
	 * Decrypt Function
	 * 
	 * TODO To decrypte encrypting message m = c^d mod n using
	 * ModulaExponentiation for fast calculation
	 * 
	 * @param c
	 * @return decrypting message
	 */
	public BigInteger decrypt(BigInteger c) {
		return primecheck.ModulaExponentiation(c, d, n);
	}

	
	public BigInteger callP() {
		return p;
	}

	public BigInteger callQ() {
		return q;
	}

	public BigInteger callN() {
		return n;
	}

	public BigInteger callE() {
		return e;
	}

	public BigInteger callD() {
		return d;
	}
}
