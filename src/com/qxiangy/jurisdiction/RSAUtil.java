package com.qxiangy.jurisdiction;

import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import javax.crypto.Cipher;
import sun.misc.BASE64Decoder;

@SuppressWarnings("restriction")
public class RSAUtil
{

	public static void main(String[] args) throws Exception
	{

		String appid = "6HY34z8Y";
		String token = "xWLkz9nL";
		String data = appid + token + "1546510720887";
		//		String data="6HY34z8Yq2Tf6bEk1546502145625";
		// BASE64Encoder base64Encoder = new BASE64Encoder();
		// 获取公钥，并以base64格式打印出来
		// String publicKey = base64Encoder.encode(keyPair.getPublic().getEncoded());
		//		String publicKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAvPVIiZODH00Dp9sw6BX22M9tB8Dn61XKD/exw/IatgGzY8Cf16KucmYrJLJWoT5S7NakPCqKHUUMkECANvOIb/Zxn3JzGOVBaQ1CSg0Oz9/n7/MfzPq+NJ19ZM6HAP0NWI+REjbQYoLxf8rf8KJilRpO6NLxMDSdF+0YgXZ13BsdYZOKHlNzWJh6M5UsdZhZIc0/+S0172keyS6OciXNSKCVU2yenWMt/R1Np5+asKn596EpNeCrxL89ksijNPntQvZCEWGUxiVtxdA7SyYiUe8AOF6nKmKasxaEvkGn7NqyC9giEjAIi37Fbz+vkjUEgZkpah6jH9thf7Kuo0BZ+QIDAQAB";
		String publicKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAoUtkD+bie0JvqolO4XPQnAOHVaKsPwCpqx/Jmv9l1B9A9QwC+oG1q0b1w3hBHCbRM0GuljV89+qw9xBOgfXy4boyNMnscspcLq0sTLzcVXRp/k7zOonMXjEnjlxZEFi7i9pr/uCkOtLUHUNt4vwlHY+ChWPD+FKjlN8AhDGDwiz54GpNgbHSWnZyydwCW0tpqgz9sgI4CmUFLNxckoIcitl89fH48smSzrmy2DWKekXmfEgD8uPUhPyK0e2sVXSL94Vtri+LGEKF1ERUw3rwZdvaibEmLIH/PBgoqTL0v0bCZYeLWyCT1wxdUdTR9vyDlb3WelLgkYtZz1MgYcgUyQIDAQAB";
		// System.out.println("公钥：" + publicKey);
		// 获取私钥，并以base64格式打印出来
		// String privateKey = base64Encoder.encode(keyPair.getPrivate().getEncoded());
		String privateKey = "MIIEvwIBADANBgkqhkiG9w0BAQEFAASCBKkwggSlAgEAAoIBAQChS2QP5uJ7Qm+qiU7hc9CcA4dVoqw/AKmrH8ma/2XUH0D1DAL6gbWrRvXDeEEcJtEzQa6WNXz36rD3EE6B9fLhujI0yexyylwurSxMvNxVdGn+TvM6icxeMSeOXFkQWLuL2mv+4KQ60tQdQ23i/CUdj4KFY8P4UqOU3wCEMYPCLPngak2BsdJadnLJ3AJbS2mqDP2yAjgKZQUs3FySghyK2Xz18fjyyZLOubLYNYp6ReZ8SAPy49SE/IrR7axVdIv3hW2uL4sYQoXURFTDevBl29qJsSYsgf88GCipMvS/RsJlh4tbIJPXDF1R1NH2/IOVvdZ6UuCRi1nPUyBhyBTJAgMBAAECggEAOcg+Yov+GtYkFt31ykKpOxrwgrcyKafdIJbUrJhOLH290YuqDYAmSuOy64HLIYCudURUfS9QQ6hqGThYR6qKhl8j2VF1SXJBL9+N/TezX1HDHv29mP0An8XQO+nNZlPDbEilY7uqWgBpSMp9NVHLIhnlUk/5Rp6kR5LUJodugkL07xIPuPD2H08+VJ2nQSvVygtzHGANw6a98npNrBfbzQtWDONHL32z6jrjt1D2hfE1sbxw33ypcYKp4hWRlEfKKDdzM/s8/BJFtnGVxsQZmVYu4tXN2JGPtz3zNT+UZ4ebjT7Nc5/BFMUgRITZNQD7qTxWhRsTq+JTkCaHLR3pOQKBgQDNl7NJk+LNO+PINcVy4jusADJcRmK6mwfMlwCR2cJwAadE1/AuFF7lSCDDPjo59qkFqiy3NEx29+dOCk3X+vdlriEJjJNC3RtkVQCNbCohlbzopLvtPhuvFvllJZiJI8sUapYjG7334OXh49v7z2iRXnXmWKq+RGYNSuuXNbzcFwKBgQDI10K6crJwvLqZnRhI318ATxlQn/4GTZQdgkAxUoUDNiPmxbF+8rAp2hcSbRKFBF46khmG10LDbz+5r5OTyFzZABRBHUmqC8BJz8lIX4RFGpMXCyK0qe4bL/C+o02NWn8GtM5EeniEqk0SA+jEbTUpsTUkedRZ9z7R4zkdW+DCHwKBgQCua3T/hydzVsv4nyXUQXq+gaw3/L7l8YC8YZ9qHYSH4d822Lw7W6zmeDrTaYechVcAJ0+oXgZBKDBkVPmVsqUQ7pm4sDp/D3aduD+vu4RC2fZaqOeGbpquAJ+04CB4uJUhzQty3aZdFb/x1I5dI6ljKvB7a93vbbfmrxkQP9bqIQKBgQCXVINGA4hqo/oiJxmWRyDJFdCoTHVWgHv7JBqET35D5jZ0+z77vZd5YYgC7UaIDbuGfHFZhbcblSa6Hg6fytgS9hlM2umXhFU2CKfMrZP3kceFFHHpGSV9PMk0i82ylZ6jCfdl57YJmFyMF3/7CaKGMK0vN9EGnOKruX9enM0k1wKBgQDEotf+WStCXM5KaOIZ6pyraRLgqacs2Z/JxWexO1927ZvjhQEiuN/sXa5Sw6dvynCgNOcLg2VIAsABMiuxqwKYI0dWgcpLsNXPV1jdWFOLSPdzFU3VZviJ2nbGr5vCzMxS8EUjzcNg6arAODVH52XT8GXsaTKN81nonSXWsK++aQ==";
		// 公钥加密         "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAMQBbM+zlXAwKa/w8kzzHhZfyyOsFo2BjnDefwJiQJWh3Er79UpHEN4qrTwaCPaWSlEvl5NP3E1Li7NcsIFLDmtjLQ537YqijGZZyyp5A3M71q6YIkMiKMMLsJcQBchrCfw6YDtZrlzcSJJsAdfSQOrYYDKdoyKPN1sTcosSViYpAgMBAAECgYBNiFLGhj8itaqH0kPGdJT+PL5aSgRRzWHO+/Q5uSvbBL/jWW9aBmqBt1f3YkURZbnYC39l4j0+xhdox816F3qIBsu8gACnYUGKZtPZIosuuPcCiu8XcJ+GnyfUI08G67XEDnDmXBMeANCsLTP8M8d2MRE8Ev6+Tk33oH/Z7wz8AQJBAOf4glJ103ay5S3OshGs/iEEplcCy1ZGjzPtLawQ9yCIKgxEMr95LRJNNEBnu/+ii5PwwN6kpULRy+MtqrcyZEkCQQDYTy1tgR9kpEMI2TJ3BCB0Y7agxqX5jk/SaBgv0w/9eaWVHcOB0lZDQp2/NlFO46LEiiyymk3tb5LCOhu8R/LhAkEAkn6SbtkSTqfzq9YZBqilZNy238RX/YhZ74Udjsi4CMd1JoH7OoigOiqfkPzx8SMJ2sf0Rabc5zHu/O1eHouoYQJAIpi3WVZXoQ8ssZOboLGGR1dTdG+RSFLcrXh8tdl6Kz5zn1/9TxxHM8bJ2DVl18pM1EaNU5uebyVzQaFtojWXwQJAasvQ+HkguTDQWzNlH/fjfD1fMV4RvhBSpXhV/ZWAYdTxdv7OgPVBL7Ic4qhyyYCf843aYCTHdqqpnYBm//XPBA=="
		System.out.println(data);
		String encryptedBytes = publicEncrypt(data, publicKey);
		System.out.println("公钥加密后：" + encryptedBytes);
		//		// 私钥解密
		String decryptedBytes = privateDecrypt(encryptedBytes, privateKey);
		System.out.println("私钥解密后：" + decryptedBytes);
		//		// 私钥加密
		String privateEncrypt = privateEncrypt("6HY34z8YxWLkz9nL1546512429308", privateKey);
		System.out.println("私钥加密后：" + privateEncrypt);
		// 公钥解密
		//		privateEncrypt="MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAsKGL9rZv2U1o4hEx1HMCmUhU67XLCkOKPfKjnMbZSeBYWahjcVvUU6JPXOZPHMj96Xyl0oVYEVMQlBdSc+FkqPKsIAuy48ijLaYg6I8ijl95GggrIzINIebwZLbK/x5lmsdy+X5iUvGMyP8FPEJRnFZgT8RGslfLNjveOGyAxBJqH8Ah55v3FHnBTkAwcA6OC79JBiwn6ykZC3gt2HMRqMIwYnytJ/iENyWowpLUeGpTjre3PNA0uZfXND/+0NMJdxGq0uIJ9F2vtZEvbgKIVFjmgs8sTMCRMNgusZ3U1C/x7jp7GZgzmWoxL6i/NgrrCWPHOt+ORXaqGqG0bhs7+wIDAQAB";
		String publicDecrypt = publicDecrypt(
				"064de0da25925b6fa027191d09f06a8983d8db70826aafedf3a6665c2af6ccb5dae37751a0bc32b3d4901e6e67b97c9b1a6341f3ffe8c8d334e9a12fbcef19cdbafef121e94f2942c75cd8c579a602c03556f78287ffba85b13fc588c1e2c77119ca33bfb824ee7dba796c0adea91411ae414b38c0c785b64b0412ef3a7b0d06aaac1e6438aff180b1c73a644a585b3ab36fadf6851326fad662afa31959134d2e0588f8747c1efa4849d0a13e8fcc430bac702d42d56a0d8d86dab8072849639286b7e70fcc3b5aa0b022b702d79110d1f08e2cd9c838aa1adab68266929df8660e46fbc10860679bd82c92abccd56c403f3a8c0841a4cdee3624d0b247d20c",
				publicKey);
		System.out.println("公钥解密后：" + publicDecrypt);

	}

	// 生成密钥对
	public static KeyPair genKeyPair(int keyLength) throws Exception
	{
		KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
		keyPairGenerator.initialize(keyLength);
		return keyPairGenerator.generateKeyPair();
	}

	/**
	 * 公钥加密
	 * 
	 * @param content
	 *            待加密字符串
	 * @param publicKey
	 *            公钥
	 * @return 加密后的16进制字符串
	 * @throws Exception
	 */
	public static String publicEncrypt(String content, String publicKey) throws Exception
	{
		Cipher cipher = Cipher.getInstance("RSA");// java默认"RSA"="RSA/ECB/PKCS1Padding"
		cipher.init(Cipher.ENCRYPT_MODE, getPublicKey(publicKey));
		byte[] doFinal = cipher.doFinal(content.getBytes("utf-8"));
		return bytesToHexString(doFinal);
	}

	//	/**
	//	 * 公钥分段加密
	//	 * 
	//	 * @param content
	//	 *            待加密字符串
	//	 * @param publicKey
	//	 *            公钥
	//	 * @return 加密后的16进制字符串
	//	 * @throws Exception
	//	 */
	//	public static String publicEncrypt(String content, String publicKey) throws Exception {
	//		Cipher cipher = Cipher.getInstance("RSA");// java默认"RSA"="RSA/ECB/PKCS1Padding"
	//		cipher.init(Cipher.ENCRYPT_MODE, getPublicKey(publicKey));
	//		
	//		
	//		int inputLen = content.getBytes("utf-8").length;
	//        ByteArrayOutputStream out = new ByteArrayOutputStream();
	//        int offSet = 0;
	//
	//        for(int i = 0; inputLen - offSet > 0; offSet = i * 244) {
	//            byte[] cache;
	//            if(inputLen - offSet > 244) {
	//                cache = cipher.doFinal( content.getBytes("utf-8"), offSet, 244);
	//            } else {
	//                cache = cipher.doFinal( content.getBytes("utf-8"), offSet, inputLen - offSet);
	//            }
	//
	//            out.write(cache, 0, cache.length);
	//            ++i;
	//        }
	//
	//        byte[] encryptedData = out.toByteArray();
	//        out.close();
	//        return bytesToHexString(encryptedData);
	//
	//	}

	/**
	 * 私钥解密
	 * 
	 * @param content
	 *            公钥加密的16进制字符串
	 * @param privateKey
	 *            私钥
	 * @return
	 * @throws Exception
	 */
	public static String privateDecrypt(String content, String privateKey) throws Exception
	{
		Cipher cipher = Cipher.getInstance("RSA");
		cipher.init(Cipher.DECRYPT_MODE, getPrivateKey(privateKey));
		byte[] doFinal = cipher.doFinal(hexStringToBytes(content));
		return new String(doFinal, "utf-8");
	}

	//	/**
	//	 * 私钥分段解密
	//	 * 
	//	 * @param content
	//	 *            公钥加密的16进制字符串
	//	 * @param privateKey
	//	 *            私钥
	//	 * @return
	//	 * @throws Exception
	//	 */
	//	public static String privateDecrypt(String content, String privateKey) throws Exception {
	//		Cipher cipher = Cipher.getInstance("RSA");
	//		cipher.init(Cipher.DECRYPT_MODE, getPrivateKey(privateKey));
	//		int inputLen = hexStringToBytes(content).length;
	//        ByteArrayOutputStream out = new ByteArrayOutputStream();
	//        int offSet = 0;
	//
	//        for(int i = 0; inputLen - offSet > 0; offSet = i * 256) {
	//            byte[] cache;
	//            if(inputLen - offSet > 256) {
	//                cache = cipher.doFinal(hexStringToBytes(content), offSet, 256);
	//            } else {
	//                cache = cipher.doFinal(hexStringToBytes(content), offSet, inputLen - offSet);
	//            }
	//
	//            out.write(cache, 0, cache.length);
	//            ++i;
	//        }
	//
	//        byte[] decryptedData = out.toByteArray();
	//        out.close();
	//        return new String(decryptedData, "utf-8");
	//
	//	}

	/**
	 * 私钥加密
	 * 
	 * @param content
	 *            待加密字符串
	 * @param privateKey
	 *            私钥
	 * @return 加密后的16进制字符串
	 * @throws Exception
	 */
	public static String privateEncrypt(String content, String privateKey) throws Exception
	{
		Cipher cipher = Cipher.getInstance("RSA");// java默认"RSA"="RSA/ECB/PKCS1Padding"
		cipher.init(Cipher.ENCRYPT_MODE, getPrivateKey(privateKey));
		byte[] doFinal = cipher.doFinal(content.getBytes("utf-8"));
		return bytesToHexString(doFinal);
	}

	//	
	//	/**
	//	 * 私钥分段加密
	//	 * 
	//	 * @param content
	//	 *            待加密字符串
	//	 * @param privateKey
	//	 *            私钥
	//	 * @return 加密后的16进制字符串
	//	 * @throws Exception
	//	 */
	//	public static String privateEncrypt(String content, String privateKey) throws Exception {
	//		Cipher cipher = Cipher.getInstance("RSA");// java默认"RSA"="RSA/ECB/PKCS1Padding"
	//		cipher.init(Cipher.ENCRYPT_MODE, getPrivateKey(privateKey));
	//		int inputLen = content.getBytes("utf-8").length;
	//		System.out.println(inputLen);
	//        ByteArrayOutputStream out = new ByteArrayOutputStream();
	//        int offSet = 0;
	//        for(int i = 0; inputLen - offSet > 0; offSet = i * 244) {
	//            byte[] cache;
	//            if(inputLen - offSet > 244) {
	//                cache = cipher.doFinal( content.getBytes("utf-8"), offSet, 244);
	//            } else {
	//                cache = cipher.doFinal( content.getBytes("utf-8"), offSet, inputLen - offSet);
	//            }
	//
	//            out.write(cache, 0, cache.length);
	//            ++i;
	//        }
	//
	//        byte[] encryptedData = out.toByteArray();
	//       
	//		return bytesToHexString(encryptedData);
	//	}
	/**
	 * 公钥解密
	 * 
	 * @param content
	 *            私钥加密的16进制字符串
	 * @param publicKey
	 *            公钥
	 * @return
	 * @throws Exception
	 */
	public static String publicDecrypt(String content, String publicKey) throws Exception
	{
		Cipher cipher = Cipher.getInstance("RSA");
		cipher.init(Cipher.DECRYPT_MODE, getPublicKey(publicKey));
		byte[] doFinal = cipher.doFinal(hexStringToBytes(content));
		return new String(doFinal, "utf-8");
	}

	//	/**
	//	 * 公钥分段解密
	//	 * 
	//	 * @param content
	//	 *            私钥加密的16进制字符串
	//	 * @param publicKey
	//	 *            公钥
	//	 * @return
	//	 * @throws Exception
	//	 */
	//	public static String publicDecrypt(String content, String publicKey) throws Exception {
	//		Cipher cipher = Cipher.getInstance("RSA");
	//		cipher.init(Cipher.DECRYPT_MODE, getPublicKey(publicKey));
	//		int inputLen = hexStringToBytes(content).length;
	//        ByteArrayOutputStream out = new ByteArrayOutputStream();
	//        int offSet = 0;
	//
	//        for(int i = 0; inputLen - offSet > 0; offSet = i * 256) {
	//            byte[] cache;
	//            if(inputLen - offSet > 256) {
	//                cache = cipher.doFinal(hexStringToBytes(content), offSet, 256);
	//            } else {
	//                cache = cipher.doFinal(hexStringToBytes(content), offSet, inputLen - offSet);
	//            }
	//
	//            out.write(cache, 0, cache.length);
	//            ++i;
	//        }
	//
	//        byte[] decryptedData = out.toByteArray();
	//        out.close();
	//		return new String(decryptedData, "utf-8");
	//	}

	/**
	 * 将base64编码后的公钥字符串转成PublicKey实例
	 * 
	 * @param publicKey
	 * @return
	 * @throws Exception
	 */
	private static PublicKey getPublicKey(String publicKey) throws Exception
	{
		BASE64Decoder decoder = new BASE64Decoder();
		byte[] keyBytes = decoder.decodeBuffer(publicKey);
		X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		return keyFactory.generatePublic(keySpec);
	}

	/**
	 * 将base64编码后的私钥字符串转成PrivateKey实例
	 * 
	 * @param privateKey
	 * @return
	 * @throws Exception
	 */
	private static PrivateKey getPrivateKey(String privateKey) throws Exception
	{
		BASE64Decoder decoder = new BASE64Decoder();
		byte[] keyBytes = decoder.decodeBuffer(privateKey);
		PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		return keyFactory.generatePrivate(keySpec);
	}

	/**
	 * 将byte数组转换为表示16进制值的字符串
	 * 
	 * @param src
	 * @return
	 */
	public static String bytesToHexString(byte[] src)
	{
		StringBuilder stringBuilder = new StringBuilder("");
		if (src == null || src.length <= 0) { return null; }
		for (int i = 0; i < src.length; i++)
		{
			int v = src[i] & 0xFF;
			String hv = Integer.toHexString(v);
			if (hv.length() < 2)
			{
				stringBuilder.append(0);
			}
			stringBuilder.append(hv);
		}
		return stringBuilder.toString();
	}

	/**
	 * 将表示16进制值的字符串转换为byte数组
	 * 
	 * @param hexString
	 * @return
	 */
	public static byte[] hexStringToBytes(String hexString)
	{
		if (hexString == null || hexString.equals("")) { return null; }
		hexString = hexString.toUpperCase();
		int length = hexString.length() / 2;
		char[] hexChars = hexString.toCharArray();
		byte[] d = new byte[length];
		for (int i = 0; i < length; i++)
		{
			int pos = i * 2;
			d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));
		}
		return d;
	}

	private static byte charToByte(char c)
	{
		return (byte) "0123456789ABCDEF".indexOf(c);
	}
}
