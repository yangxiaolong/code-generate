package com.nl.generate.code.util;

import java.util.HashMap;
import java.util.Map;

import com.nl.generate.code.vo.ReferenceClassVO;

public class ClassUtil {
	
	public void getDefaultDeclareClass(String className,
			Map<String, ReferenceClassVO> defaultDeclareClassMap, String basePackage) {
		
//		defaultDeclareClassMap.put(className,basePackage+ "."+ className.substring(className.lastIndexOf(".") + 1,className.length()));
//		try {
//			if(className.startsWith("com.nl.common")){
//				Method[] methods = Class.forName(className).getDeclaredMethods();
//				for (Method method : methods) {
//					Type genRetType = method.getGenericReturnType();
//					if (genRetType instanceof ParameterizedType) {
//						String retType = genRetType.toString();
//						
//						if (defaultDeclareClassMap.containsKey(retType))
//							continue;
//						getDefaultDeclareClass(retType, defaultDeclareClassMap,
//									basePackage);
//						
//					}
//				}
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
	}
	
	
	public static void main(String[] arg){
		ClassUtil util = new ClassUtil();
		Map<String, ReferenceClassVO> defaultDeclareClassMap = new HashMap<String,ReferenceClassVO>();
		util.getDefaultDeclareClass("com.nl.common.service.BaseService",defaultDeclareClassMap,"test.service");
		for(Map.Entry<String,ReferenceClassVO> entry:defaultDeclareClassMap.entrySet()){
			System.out.println(entry.getKey()+"……"+entry.getValue());
		}
	}
	
}
