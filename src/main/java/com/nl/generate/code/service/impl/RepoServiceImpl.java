package com.nl.generate.code.service.impl;

import java.io.File;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nl.generate.code.service.RepoService;
import com.nl.generate.code.util.DefaultValueUtil;
import com.nl.generate.code.util.FileWriter;
import com.nl.generate.code.util.NameFormatUtil;
import com.nl.generate.code.vo.CreateDomainCondition;

@Service
public class RepoServiceImpl implements RepoService {

	@Autowired
	private FileWriter fileWriter;

	private String lineBreak = DefaultValueUtil.LINE_BREAK;

	private String semicolon = DefaultValueUtil.SEMICOLON;

	public void createService(List<CreateDomainCondition> createDomainConditionList, String baseFilePath) {

		if (createDomainConditionList.size() == 0 || createDomainConditionList == null)
			return;

		String filePathTemplate = baseFilePath + File.separator + "%s.java";

		for (CreateDomainCondition createBeanCondition : createDomainConditionList) {
			String filePath = String.format(filePathTemplate, createBeanCondition.getRepoFullName().replace(".",
					File.separator));
			Map<String, Object> implContent = new HashMap<String, Object>();
			implContent.put("repoName", createBeanCondition.getRepoName());
			implContent.put("packageName", createBeanCondition.getRepoPackageName());
			implContent.put("domainClass", createBeanCondition.getDomainClassName());
			implContent.put("domainClassPo", createBeanCondition.getDomainClassPoName());
			implContent.put("domainClassBo", createBeanCondition.getBoName());
			implContent.put("daoName", createBeanCondition.getDaoName());
			implContent.put("daoLittleName", createBeanCondition.getDaoLittleName());
			implContent.put("domainLittleBo", NameFormatUtil.littleCamelCase(createBeanCondition.getBoName()));
			implContent.put("domainLittlePo", NameFormatUtil.littleCamelCase(createBeanCondition.getDomainClassName()));
			implContent.put("keyClass", "Integer");
			implContent.put("package", createImportPackage(createBeanCondition));

			try {
				fileWriter.write(implContent, DefaultValueUtil.TEMPLATE_REPO_PATH, filePath);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	private String createImportPackage(CreateDomainCondition createBeanCondition) {
		StringBuffer importPackage = new StringBuffer();

		Set<String> importSet = new HashSet<String>();
		importSet.add(createBeanCondition.getDomainClassPoFullName());
		importSet.add(createBeanCondition.getDaoFullName());
		importSet.add(createBeanCondition.getBoFullName());
		for (String importPackageName : importSet) {
			importPackage.append("import ").append(importPackageName).append(semicolon).append(lineBreak);
		}

		return importPackage.toString();
	}

	public void getDefaultDeclareClass(String className, Map<String, String> defaultDeclareClassMap,
			String basePackage) {
		defaultDeclareClassMap.put(className,
				basePackage + "." + className.substring(className.lastIndexOf(".") + 1, className.length()));
		try {
			Method[] methods = Class.forName(className).getDeclaredMethods();
			for (Method method : methods) {
				Type genRetType = method.getGenericReturnType();
				if (genRetType instanceof ParameterizedType) {
					String retType = genRetType.toString();
					if (defaultDeclareClassMap.containsKey(retType))
						continue;
					getDefaultDeclareClass(retType, defaultDeclareClassMap, basePackage);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public String genericMethod(Method method, Set<String> parameterizedTypeList, String basePackagePath) {
		StringBuffer sb = new StringBuffer();
		int mod = method.getModifiers() & Modifier.methodModifiers();
		if (mod != 0) {
			sb.append(Modifier.toString(mod)).append(' ');
		}
		TypeVariable<?>[] typeparms = method.getTypeParameters();
		if (typeparms.length > 0) {
			boolean first = true;
			sb.append('<');
			for (TypeVariable<?> typeparm : typeparms) {
				if (!first)
					sb.append(',');
				sb.append(typeparm.toString());
				first = false;
			}
			sb.append("> ");
		}
		Type genRetType = method.getGenericReturnType();
		if (genRetType instanceof ParameterizedType) {
			String retType = genRetType.toString();
			parameterizedTypeList.add(retType);
			sb.append(basePackagePath).append(".")
					.append(retType.substring(retType.lastIndexOf(".") + 1, retType.length()));
		} else {
			sb.append(((genRetType instanceof Class<?>) ? getTypeName((Class<?>) genRetType) : genRetType.toString()))
					.append(' ');
		}

		return sb.toString();
	}

	private static String getTypeName(Class<?> type) {
		if (type.isArray()) {
			try {
				Class<?> cl = type;
				int dimensions = 0;
				while (cl.isArray()) {
					dimensions++;
					cl = cl.getComponentType();
				}
				StringBuffer sb = new StringBuffer();
				sb.append(cl.getName());
				for (int i = 0; i < dimensions; i++) {
					sb.append("[]");
				}
				return sb.toString();
			} catch (Throwable e) { /* FALLTHRU */
			}
		}
		return type.getName();
	}

	// public static void main(String[] arg) {
	// try {
	// Method[] methods = Class.forName(
	// "com.nl.common.service.BaseService").getDeclaredMethods();
	// StringBuffer content = new StringBuffer();
	// Set<String> parameterizedTypeList = new HashSet<String>();
	// for (Method method : methods) {
	// System.out.println(generic(method, parameterizedTypeList,
	// "xx.x"));
	// }
	// } catch (SecurityException | ClassNotFoundException e1) {
	// e1.printStackTrace();
	// }
	// }

}
