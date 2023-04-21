package com.nl.generate.code.service;

import java.util.List;

import com.nl.generate.code.vo.CreateDomainCondition;

public interface RepoService {
	
	public void createService(List<CreateDomainCondition> createDomainConditionList,String filePath);
	
}
