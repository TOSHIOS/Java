package hstest;

import java.math.BigInteger;

public class Evidence {

	public static String[] handleAction(String user, String pass, String modulus, String generator, String salt,
			String publicB) {
		return login(user, pass, modulus, generator, salt, publicB);
	}

	private static String[] login(String user, String pass, String modulus, String generator, String salt,
			String publicB) {
		SrpClientSession srpClientSession = new SrpClientSession(modulus, generator);
		BigInteger[] bigIntegers = srpClientSession.step1(user, pass, salt, publicB);
		return new String[] { bigIntegers[0].toString(16), bigIntegers[1].toString(16), };
	}
}
