import java.math.BigInteger;


public class rsa_gcd {
	public static BigInteger gcdEuclid(BigInteger a, BigInteger b) {
		if (b.equals(BigInteger.ZERO))
			return a;
		else
			return gcdEuclid(b, a.mod(b));
	}

	public static BigInteger[] PlusEuclid(BigInteger a, BigInteger b){
		if(b.equals(BigInteger.ZERO))
			return new BigInteger[] { a, BigInteger.ONE, BigInteger.ZERO };
		
		BigInteger[] values = PlusEuclid(b, a.mod(b));
		BigInteger d = values[0];	// d
		BigInteger x = values[2];	// x = y'
		BigInteger y = values[1].subtract(a.divide(b).multiply(values[2])); // y = x' - (a/b)*y'
		
		return new BigInteger[] { d, x, y };		
	}
}
