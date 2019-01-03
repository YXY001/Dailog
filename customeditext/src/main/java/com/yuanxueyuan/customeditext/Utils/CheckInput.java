package com.yuanxueyuan.customeditext.Utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

public class CheckInput {

	// 过滤特殊字符
	public static String StringFilter(String str) throws PatternSyntaxException {
		// 只允许字母和数字
		// String regEx = "[^a-zA-Z0-9]";
		// 清除掉所有特殊字符
		String regEx = "[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……& ;*（）——+|{}【】‘；：\"”“’。，、？]";

		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(str);
		return m.replaceAll("").trim();
	}

	public static boolean CheckName(String str) { // 调用位置：修改用戶名
		String regex = "^[a-zA-Z0-9_-\u4E00-\u9FA5]+$";
		Pattern pattern = Pattern.compile(regex);
		Matcher match = pattern.matcher(str);
		return match.matches();

	}

	public static boolean InputName(String str) { // 添加用户，用户名不允许为中文
		String regex = "^[^\u4e00-\u9fa5]{0,}$";// 非中文
		Pattern pattern = Pattern.compile(regex);
		Matcher match = pattern.matcher(str);
		return match.matches();

	}

	public static boolean CheckEmail(String str) { // 调用位置：注册、修改email
		String regex = "^[A-Za-z0-9]+([._\\-]*[a-z0-9])*@([a-z0-9]+[-a-z0-9]*[a-z0-9]+.){1,63}[a-z0-9]+$";
		Pattern pattern = Pattern.compile(regex);
		Matcher match = pattern.matcher(str);
		return match.matches();
	}

	public static boolean CheckSpecAsc(String str) { // 调用位置：修改设备名称
		String regex = "^[\\s\\w\\u4e00-\\u9fa5，。,.?？！!@#^&*()-_=+-—]+[^/]{0,32}$";// {}[]:;|'<>,.?/\"
		Pattern pattern = Pattern.compile(regex);
		Matcher match = pattern.matcher(str);
		return match.matches();
		// return true;
	}

	public static boolean CheckIdea(String str) { // 调用位置：意见反馈
		String regex = "^[\\w\\u4e00-\\u9fa5，。,.?？！!]+$";// {}[]:;|'<>,.?/\"
		Pattern pattern = Pattern.compile(regex);
		Matcher match = pattern.matcher(str);
		return match.matches();

	}

	public static boolean CheckLetterAndNum(String str) {
		String regex = "^[a-zA-Z0-9]+$";
		Pattern pattern = Pattern.compile(regex);
		Matcher match = pattern.matcher(str);
		return match.matches();
	}

	public static boolean CheckPassword(String str) {
		String regex = "^[a-zA-Z0-9`~!@#$%^&*()-=+_]+$";// {}[]:;|'<>,.?/\"
		Pattern pattern = Pattern.compile(regex);
		Matcher match = pattern.matcher(str);
		return match.matches();
	}

	public static boolean CheckNum(String str) {
		String regex = "^[0-9]+$";
		Pattern pattern = Pattern.compile(regex);
		Matcher match = pattern.matcher(str);
		return match.matches();
	}

	public static boolean CheckIP(String str) {
		String regex = "(2[5][0-5]|2[0-4]\\d|1\\d{2}|\\d{1,2})\\.(25[0-5]|2[0-4]\\d|1\\d{2}|\\d{1,2})\\.(25[0-5]|2[0-4]\\d|1\\d{2}|\\d{1,2})\\.(25[0-5]|2[0-4]\\d|1\\d{2}|\\d{1,2})";
		Pattern pattern = Pattern.compile(regex);
		Matcher match = pattern.matcher(str);
		return match.matches();
	}

	public static boolean checkDeviceId(String str) {
		if (str == null || "".equals(str)) {
			return false;
		}
		if (str.length() != 22) {
			return false;
		}
		return true;
	}

	public static boolean checkPhoneLength(String str) {
		if (str == null || "".equals(str)) {
			return false;
		}
		if (str.length() > 11) {
			return false;
		}
		return CheckNum(str);
	}

	// 判断字符串中是否包含中文
	public static boolean checkChinese(String str) {
		String string = null;
		try {
			string = URLDecoder.decode(str, "UTF-8"); // 为避免出现乱码时可能是其他字符而可以通过校验的情况
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String regex = ".*?[\u4E00-\u9FFF]+.*";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(string);
		return matcher.matches();
	}

	//判断是否有表情
	public static boolean isEmoji(String string) throws PatternSyntaxException  {
		Pattern p = Pattern.compile("[\ud83c\udc00-\ud83c\udfff]|[\ud83d\udc00-\ud83d\udfff]|[\u2600-\u27ff]",
				Pattern.UNICODE_CASE | Pattern.CASE_INSENSITIVE);
		Matcher m = p.matcher(string);
		return m.find();
	}


	// 判断是否是手机号码
	public static boolean checkPhoneNum(String str) throws PatternSyntaxException {
		String regExp = "^(13[0-9]|14[579]|15[0-3,5-9]|16[6]|17[0135678]|18[0-9]|19[89])\\d{8}$";
		Pattern p = Pattern.compile(regExp);
		Matcher m = p.matcher(str);
		return m.matches();
	}

	// 判断是否中文加数字加英文
	public static boolean checkInputChannelName(String str) throws PatternSyntaxException {
		String regEx = "^[a-zA-Z0-9\u4E00-\u9FA5，。,.?？！!\\s—]+$";

		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(str);
		return m.matches();
	}

	public static boolean isDigits(String str) {
		return str.matches("[-+]?[0-9]*");
	}

	/**
	 * 验证输入的身份证号是否合法
	 */
	public static boolean isLegalId(String id){
		if (id == null || (id.length() != 15 && id.length() != 18)) {
			return false;
		}

		return id.toUpperCase().matches("(^\\d{15}$)|(^\\d{17}([0-9]|X)$)");
	}


	//校验用户名，企业名称，允许输入中文/字母/数字/个别特殊符号（— - #.等）
	public static boolean isTrueCaption(String str) {
		String regex = "[\\u4e00-\\u9fa5A-Za-z0-9\\_\\.\\-\\#]+";
		return str.matches(regex);
	}

	//校验座机号及手机号
	public static boolean isTruePhone(String str) {
		String regex = "(^(0[0-9]{2,3}\\-)?([1-9][0-9]{7})+(\\-[0-9]{1,4})?$)|(^0?[1][345789][0-9]{9}$)";
		return str.matches(regex);
	}

	//校验人员编号 字母加数字
	public static boolean isTrueSerialNumber(String str) {
		String regex = "[A-Za-z0-9]+";
		return str.matches(regex);
	}

	//校验信用代码
	public static boolean isTrueCreditCode(String str) {
		String regex = "[1-9A-GY]{1}[1239]{1}[1-5]{1}[0-9]{5}[0-9A-Z]{10}";
		return str.matches(regex);
	}
}
