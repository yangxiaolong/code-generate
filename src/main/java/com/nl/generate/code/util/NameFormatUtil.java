package com.nl.generate.code.util;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

public class NameFormatUtil {

	public static String bigCamelCase(String name) {
		
		if(name==null)return null;
		
		String[] split = name.split("_");
		
		List<String> nameList = Arrays.asList(split); 
		
		String newName = formatHump(nameList);
		
		if(StringUtils.isEmpty(newName)) return name;
		
		return newName;
	}

	public static String littleCamelCase(String name) {
		
		if(name==null)return null;
		
		String[] split = name.split("_");
		
		List<String> nameList = Arrays.asList(split);
		
		String newName = formatHump(nameList.subList(1, nameList.size()));
		
		return split[0].substring(0, 1).toLowerCase()+split[0].substring(1,split[0].length())+ newName;
	}
	
	private static String formatHump(List<String> nameList){
		StringBuffer sb = new StringBuffer();
		String na = null;
		String tempName = null;
		if (nameList.size() > 0) {
			for (int i = 0; i < nameList.size(); i++) {
				na = nameList.get(i);
				tempName = na.substring(0, 1).toUpperCase()+ na.substring(1, na.length());
				sb.append(tempName);
			}
			return sb.toString();
		}
		return "";
	}

	public static void main(String[] args) {
		
		String f = NameFormatUtil.bigCamelCase("aa_cd_ddd");
		
		System.out.println(f);
	}

}
