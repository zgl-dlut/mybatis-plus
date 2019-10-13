package com.zgl.mybatis.plus.util;

import lombok.extern.slf4j.Slf4j;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author zgl
 * @date 2019/8/20 下午5:27
 */
@Slf4j
public class StringsUtil {


	private static final Integer BATCH_MAX_SIZE = 200;

	public static final String VERTICAL_LINE_SEPARATE = "\\|";



	/**
	 * 获得数组的全组合（不区分顺序）
	 * @param len
	 * @return
	 */
	public static List<List<String>> getGroupList(String [] len) {
		List<List<String>> list = new ArrayList<>();
		if (len == null || len.length < 1) {
			return list;
		}

		int nCnt = len.length;

		int nBit = (0xFFFFFFFF >>> (32 - nCnt));

		for (int i = 1; i <= nBit; i++) {
			List<String> listTemp = new ArrayList<>();
			for (int j = 0; j < nCnt; j++) {
				if ((i << (31 - j)) >> 31 == -1) {
					listTemp.add(len[j]);
				}
			}
			list.add(listTemp);
		}
		return list;
	}

	public static String getUUID(){
		return UUID.randomUUID().toString().replace("-", "");
	}

	/**
	 * 错误消息拼接
	 * @param pre
	 * @param args
	 * @return
	 */
	public static String getErrorMessage(String pre, Object... args) {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(pre);
		stringBuilder.append(";");
		for (Object errArg : args) {
			stringBuilder.append(errArg).append(",");
		}
		stringBuilder.deleteCharAt(stringBuilder.length() - 1);
		return stringBuilder.toString();
	}

	//首字母转小写
	public static String toLowerCaseFirstOne(String s){
		if(Character.isLowerCase(s.charAt(0))) {
			return s;
		} else {
			return (new StringBuilder()).append(Character.toLowerCase(s.charAt(0))).append(s.substring(1)).toString();
		}
	}

	public static String getDoubleToString(Number value) {
		DecimalFormat df = new DecimalFormat("0.00");
		return df.format(value);
	}
}