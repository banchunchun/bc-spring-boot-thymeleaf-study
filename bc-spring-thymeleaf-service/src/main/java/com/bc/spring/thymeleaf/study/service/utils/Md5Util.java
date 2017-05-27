package com.bc.spring.thymeleaf.study.service.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Description: MD5算法加密
 * @author banchun
 * @Version V 2.0
 * @Created at 2013-10-16 下午06:25:02
 * @Modified by
 *  on 
 */
public class Md5Util {

	/**
	 * 小写MD5
	 * @param inStr
	 * @return
	 */
	public static String ToMD5OnL(String inStr) {
		MessageDigest md5 = null;
		try {
			md5 = MessageDigest.getInstance("MD5");
		} catch (Exception e) {
			System.out.println(e.toString());
			e.printStackTrace();
			return "";
		}
		char[] charArray = inStr.toCharArray();
		byte[] byteArray = new byte[charArray.length];

		for (int i = 0; i < charArray.length; i++)
			byteArray[i] = (byte) charArray[i];

		byte[] md5Bytes = md5.digest(byteArray);

		StringBuffer hexValue = new StringBuffer();

		for (int i = 0; i < md5Bytes.length; i++) {
			int val = ((int) md5Bytes[i]) & 0xff;
			if (val < 16)
				hexValue.append("0");
			hexValue.append(Integer.toHexString(val));
		}

		return hexValue.toString();
	}
	
	/**
	 * 大写MD5
	 * @param inStr
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws UnsupportedEncodingException
	 */
	public static String ToMD5OnU(String inStr){

		StringBuffer hexString = null;
		byte[] defaultBytes = inStr.getBytes();
		try {
			MessageDigest algorithm = MessageDigest.getInstance("MD5");
			algorithm.reset();
			algorithm.update(defaultBytes);
			byte messageDigest[] = algorithm.digest();

			hexString = new StringBuffer();
			for (int i = 0; i < messageDigest.length; i++) {
				if (Integer.toHexString(0xFF & messageDigest[i]).length() == 1) {
					hexString.append(0);
				}
				hexString.append(Integer.toHexString(0xFF & messageDigest[i]));
			}
			messageDigest.toString();

			inStr = hexString + "";
		} catch (NoSuchAlgorithmException nsae) {

		}
		
		return hexString.toString().toUpperCase();

	}
	
	/**
	 * 得到加盐后的密文
	 * @param name 用户名
	 * @param pwd 密码
	 * @return
	 */
	public static String getPwd(String name, String pwd){
		return Md5Util.ToMD5OnL(pwd+"{"+name+"}");
	}
	
	
	  /** 
     * 加密解密算法 执行一次加密，两次解密 
     */   
    public static String convertMD5(String inStr){
  
        char[] a = inStr.toCharArray();  
        for (int i = 0; i < a.length; i++){  
            a[i] = (char) (a[i] ^ 't');  
        }  
        String s = new String(a);
        return s;  
  
    }  
    
    
    public static String hexString(byte[] bytes){
        StringBuffer hexValue = new StringBuffer();
  
        for (int i = 0; i < bytes.length; i++) {  
            int val = ((int) bytes[i]) & 0xff;  
            if (val < 16)  
                hexValue.append("0");  
            hexValue.append(Integer.toHexString(val));
        }  
        return hexValue.toString();  
    }  
    
    
    public static byte[] eccrypt(String info) throws NoSuchAlgorithmException {
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        byte[] srcBytes = info.getBytes();  
        //使用srcBytes更新摘要  
        md5.update(srcBytes);  
        //完成哈希计算，得到result  
        byte[] resultBytes = md5.digest();  
        return resultBytes;  
    }  
    
    /**
	 * gb/mc接口md5加密方法
	 * @param source 源字符串
	 * @return 加密后的字符
	 * @throws NoSuchAlgorithmException
	 */
	public static String apiSignMd5(String source){
		try {
			char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',  
			        'a', 'b', 'c', 'd', 'e', 'f' };	
			MessageDigest md = MessageDigest.getInstance("MD5");
			 
			md.update(source.getBytes("UTF-8"));
			byte[] tmp = md.digest();
			char[] str = new char[16 * 2];
			
			int k = 0;
			for (int i = 0; i < 16; i++) {
				byte byte0 = tmp[i];
				str[k++] = hexDigits[byte0 >>> 4 & 0xf];
				str[k++] = hexDigits[byte0 & 0xf];
			}
			return new String(str);
		} catch (Exception e) {
			return null;
		}  
	}

	
	public static void main(String[] args) {

		String msg = "314233";

		byte[] resultBytes = null;
		try {
			resultBytes = eccrypt(msg);
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		System.out.println("密文是：" + hexString(resultBytes));
//		System.out.println("明文是：" + msg);


		String inStr = "dept";

	    String jiami = convertMD5(inStr);
//	    System.out.println("加密的：" + jiami);
//	    System.out.println("解密的：" + convertMD5(jiami));
//		System.out.println("小写："+ Md5Util.ToMD5OnL(inStr));
//		System.out.println("大写："+ Md5Util.ToMD5OnU(inStr));
//		System.out.println(Md5Util.ToMD5OnL("123456{dept}"));
		//System.out.println(Md5Util.ToMD5OnL("II4vMCqu{admin}"));

		//System.out.println("apiSignMd5:"+apiSignMd5("123"));
		//System.out.println(apiSignMd5("7606cd21532922466fe19dbdb98867c4" + apiSignMd5("A001"+"goodbabymamahao")));

		System.out.println(Md5Util.getPwd("banchun1","123456"));
	}


}
