package com.nl.generate.code.service;

import java.util.List;

import com.nl.generate.code.vo.CreateDomainCondition;


public interface MybatiesService {
	
	public void createMybaties(List<CreateDomainCondition> createBeanConditionList,String filePath);
	
}
