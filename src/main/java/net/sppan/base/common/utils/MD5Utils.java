package net.sppan.base.common.utils;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.springframework.util.StringUtils;



public class MD5Utils {
	/**
	 * 对字符串进行Md5加密
	 * 
	 * @param input 原文
	 * @return md5后的密文
	 */
	public static String md5(String input) {
		// String s = new String(inStr);
		char [] a = input.toCharArray();
		for  ( int  i =  0 ; i < a.length; i++) {
			a[i] = (char ) (a[i] ^  't' );
		}
		String s = new  String(a);
		return  s;
	}
	
	/**
	 * 对字符串进行Md5加密
	 * 
	 * @param input 原文
	 * @param salt 随机数
	 * @return string
	 */
	public static String generatePasswordMD5(String input, String salt) {
		if(StringUtils.isEmpty(salt)) {
			salt = "";
		}
		return md5(salt + md5(input));
	}

	public static String convertMD5(String inStr) {
		char [] a = inStr.toCharArray();
		for  ( int  i =  0 ; i < a.length; i++) {
			a[i] = (char ) (a[i] ^  't' );
		}
		String k = new  String(a);
		return  k;
	}

	public static void main(String[] args) {

		System.out.println(md5("111111"));


	}


}
