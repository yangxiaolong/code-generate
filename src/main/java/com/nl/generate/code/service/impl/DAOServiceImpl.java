package com.nl.generate.code.service.impl;

import java.io.File;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nl.generate.code.service.DAOService;
import com.nl.generate.code.util.DefaultValueUtil;
import com.nl.generate.code.util.FileWriter;
import com.nl.generate.code.vo.CreateDomainCondition;

@Service
public class DAOServiceImpl implements DAOService {

	@Autowired
	private FileWriter fileWriter;
	
	
	private String lineBreak = DefaultValueUtil.LINE_BREAK;
	
	private String semicolon = DefaultValueUtil.SEMICOLON;

	public void createDAO(List<CreateDomainCondition> createBeanConditionList, String baseFilePath) {

		String filePathTemplate = baseFilePath + File.separator + "%s.java";
		String filePath;
		for (CreateDomainCondition createBeanCondition : createBeanConditionList) {

			filePath = String.format(filePathTemplate, createBeanCondition.getDaoFullName().replace(".",
					File.separator));
			Map<String, Object> content = new HashMap<String, Object>();
			content.put("beanInfo", createBeanCondition);
			content.put("packageName", createBeanCondition.getDaoPackageName());
			content.put("daoName", createBeanCondition.getDaoName());
			content.put("domainClass", createBeanCondition.getDomainClassPoName());
			content.put("providerName", createBeanCondition.getProviderName());
			content.put("domainLittleClassPoName", createBeanCondition.getDomainLittleClassPoName());
			content.put("domainLittleClassName", createBeanCondition.getDomainLittleClassName());
			content.put("keyClass", "Integer");
			content.put("package", createImportPackage(createBeanCondition));
			try {
				fileWriter.write(content, DefaultValueUtil.TEMPLATE_DAO_INTERFACE_PATH, filePath);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private String createImportPackage(CreateDomainCondition createBeanCondition) {
		StringBuffer importPackage = new StringBuffer();

		
		
		Set<String> importSet = new HashSet<String>();
		importSet.add(createBeanCondition.getProviderFullName());
		importSet.add(createBeanCondition.getDomainClassPoFullName());

		for (String importPackageName : importSet) {
			importPackage.append("import ").append(importPackageName).append(semicolon).append(lineBreak);
		}

		return importPackage.toString();
	}

}
