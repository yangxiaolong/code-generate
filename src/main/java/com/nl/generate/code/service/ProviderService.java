package com.nl.generate.code.service;

import java.util.List;

import com.nl.generate.code.vo.CreateDomainCondition;

public interface ProviderService {
	
	public void createProvider(List<CreateDomainCondition> createBeanConditionList,String filePath);
	
}
