package com.nl.generate.code.service.impl;

import java.io.File;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nl.generate.code.service.ProviderService;
import com.nl.generate.code.util.DefaultValueUtil;
import com.nl.generate.code.util.FileWriter;
import com.nl.generate.code.vo.CreateDomainCondition;

@Service
public class ProviderServiceImpl implements ProviderService {

	@Autowired
	private FileWriter fileWriter;

	private String lineBreak = DefaultValueUtil.LINE_BREAK;

	private String semicolon = DefaultValueUtil.SEMICOLON;

	public void createProvider(List<CreateDomainCondition> createBeanConditionList, String baseFilePath) {

		String filePathTemplate = baseFilePath + File.separator + "%s.java";

		String filePath;

		for (CreateDomainCondition createBeanCondition : createBeanConditionList) {
			filePath = String.format(filePathTemplate, createBeanCondition.getProviderFullName().replace(".",
					File.separator));
			Map<String, Object> content = new HashMap<String, Object>();
			content.put("beanInfo", createBeanCondition);
			content.put("providerName", createBeanCondition.getProviderName());
			content.put("packageName", createBeanCondition.getProviderPackageName());
			content.put("domainClassPo", createBeanCondition.getDomainClassPoName());
			content.put("domainClass", createBeanCondition.getDomainClassName());
			content.put("package", createImportPackage(createBeanCondition));
			try {
				fileWriter.write(content, DefaultValueUtil.TEMPLATE_PROVIDER_PATH, filePath);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	private String createImportPackage(CreateDomainCondition createBeanCondition) {
		StringBuffer importPackage = new StringBuffer();

		Set<String> importSet = new HashSet<String>();
		importSet.add(createBeanCondition.getDomainClassPoFullName());
		for (String importPackageName : importSet) {
			importPackage.append("import ").append(importPackageName).append(semicolon).append(lineBreak);
		}

		return importPackage.toString();
	}
}
