package link.rekruter.utility;

import org.junit.Test;

/**
 * Unit test for encryption util
 */
public class EncryptionUtilTest {
	@Test
	public void testEncryption() throws Exception {

		String encryptedString = EncryptionUtil.encrypt("hello world");
		assert "hello world".equals(EncryptionUtil.decrypt(encryptedString));

	}
}
