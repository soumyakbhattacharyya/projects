package link.rekruter.utility;

import java.security.Key;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

/**
 * 
 * helps in encrypting and decrypting a text
 *
 */
public class EncryptionUtil {

	private static final String ALGO = "AES";
	/**
	 * AES algorithm needs a 16 bytes long key to encrypt incoming payload
	 */
	private static final byte[] keyValue = new byte[] { 'P', 'a', 'y', 'm', 'a', 't', 'r', '4', 'i', 'x', 's', 'e', 'c', 'r', 'e', 't' };

	/**
	 * Encrypt a string with AES algorithm.
	 *
	 * @param payload is a string
	 * @return the encrypted string
	 */
	public static String encrypt(String payload) throws Exception {
		Key key = generateKey();
		Cipher c = Cipher.getInstance(ALGO);
		c.init(Cipher.ENCRYPT_MODE, key);
		byte[] encVal = c.doFinal(payload.getBytes());
		return Base64.getEncoder().encodeToString(encVal);
	}

	/**
	 * Decrypt a string with AES algorithm.
	 *
	 * @param encryptedPayload is a string
	 * @return the decrypted string
	 */
	public static String decrypt(String encryptedPayload) throws Exception {
		Key key = generateKey();
		Cipher c = Cipher.getInstance(ALGO);
		c.init(Cipher.DECRYPT_MODE, key);
		byte[] decordedValue = Base64.getDecoder().decode(encryptedPayload);
		byte[] decValue = c.doFinal(decordedValue);
		return new String(decValue);
	}

	/**
	 * Generate a new encryption key.
	 */
	private static Key generateKey() throws Exception {
		return new SecretKeySpec(keyValue, ALGO);
	}

	public static void main(String[] args) throws Exception {
		System.out.println(encrypt("hello world"));
		System.out.println(decrypt("wHtRC1gIpVluPbscvEZaHw=="));
	}

}
