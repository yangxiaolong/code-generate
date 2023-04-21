package com.nl.generate.code.service;

import java.util.List;

import com.nl.generate.code.vo.CreateDomainCondition;

public interface BeanService {
	
	public void createBean(List<CreateDomainCondition> createBeanConditionList,String baseFilePath);
	
}
