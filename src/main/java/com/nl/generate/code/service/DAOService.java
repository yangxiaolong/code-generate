package com.nl.generate.code.service;

import java.util.List;

import com.nl.generate.code.vo.CreateDomainCondition;

public interface DAOService {
	
	public void createDAO(List<CreateDomainCondition> createBeanConditionList,String baseFilePath);
	
}
