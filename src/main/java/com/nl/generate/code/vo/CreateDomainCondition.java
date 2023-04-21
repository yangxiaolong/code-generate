package com.nl.generate.code.vo;

import java.util.ArrayList;
import java.util.List;

public class CreateDomainCondition {

	private String tableComment;

	private String tableName;// test_1

	private String domainPackageName;// test.domain

	private String domainClassName;// Test1

	private String domainLittleClassName;// test1

	private String domainClassPoName;// Test1Po

	private String domainLittleClassPoName;// test1Po

	private String domainClassFullName;// test.domain.Test1

	private String domainClassPoFullName;// test.domain.Test1Po

	private String boName;// TestBo

	private String boFullName;// test.bo.TestBo

	private String boPackageName;// test.bo

	private String serviceName;// TestService

	private String serviceFullName;// test.service.TestService

	private String servicePackageName;// test.service

	private String repoName;// TestRepo

	private String repoFullName;// test.repo.TestRepo

	private String repoPackageName;// test.repo

	private String daoName;// TestDAO

	private String daoLittleName;// testDAO

	private String daoPackageName;// test.dao

	private String daoFullName;// test.dao.TestDAO

	private String providerName;// TestProvider

	private String providerPackageName;// test.dao

	private String providerFullName;// test.dao.TestProvider

	private String primaryKey;// primaryKey

	private List<BeanPropertyVO> beanPropertyList = new ArrayList<BeanPropertyVO>();

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getDomainPackageName() {
		return domainPackageName;
	}

	public void setDomainPackageName(String domainPackageName) {
		this.domainPackageName = domainPackageName;
	}

	public String getDomainClassName() {
		return domainClassName;
	}

	public void setDomainClassName(String domainClassName) {
		this.domainClassName = domainClassName;
	}

	public String getDomainLittleClassName() {
		return domainLittleClassName;
	}

	public void setDomainLittleClassName(String domainLittleClassName) {
		this.domainLittleClassName = domainLittleClassName;
	}

	public List<BeanPropertyVO> getBeanPropertyList() {
		return beanPropertyList;
	}

	public void setBeanPropertyList(List<BeanPropertyVO> beanPropertyList) {
		this.beanPropertyList = beanPropertyList;
	}

	public String getDomainClassFullName() {
		return domainClassFullName;
	}

	public void setDomainClassFullName(String domainClassFullName) {
		this.domainClassFullName = domainClassFullName;
	}

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public String getServicePackageName() {
		return servicePackageName;
	}

	public void setServicePackageName(String servicePackageName) {
		this.servicePackageName = servicePackageName;
	}

	public String getServiceFullName() {
		return serviceFullName;
	}

	public void setServiceFullName(String serviceFullName) {
		this.serviceFullName = serviceFullName;
	}

	public String getDaoName() {
		return daoName;
	}

	public void setDaoName(String daoName) {
		this.daoName = daoName;
	}

	public String getDaoPackageName() {
		return daoPackageName;
	}

	public void setDaoPackageName(String daoPackageName) {
		this.daoPackageName = daoPackageName;
	}

	public String getDaoFullName() {
		return daoFullName;
	}

	public void setDaoFullName(String daoFullName) {
		this.daoFullName = daoFullName;
	}

	public String getProviderName() {
		return providerName;
	}

	public void setProviderName(String providerName) {
		this.providerName = providerName;
	}

	public String getProviderPackageName() {
		return providerPackageName;
	}

	public void setProviderPackageName(String providerPackageName) {
		this.providerPackageName = providerPackageName;
	}

	public String getProviderFullName() {
		return providerFullName;
	}

	public void setProviderFullName(String providerFullName) {
		this.providerFullName = providerFullName;
	}

	public String getDomainClassPoName() {
		return domainClassPoName;
	}

	public void setDomainClassPoName(String domainClassPoName) {
		this.domainClassPoName = domainClassPoName;
	}

	public String getDomainClassPoFullName() {
		return domainClassPoFullName;
	}

	public void setDomainClassPoFullName(String domainClassPoFullName) {
		this.domainClassPoFullName = domainClassPoFullName;
	}

	public String getDomainLittleClassPoName() {
		return domainLittleClassPoName;
	}

	public void setDomainLittleClassPoName(String domainLittleClassPoName) {
		this.domainLittleClassPoName = domainLittleClassPoName;
	}

	public String getDaoLittleName() {
		return daoLittleName;
	}

	public void setDaoLittleName(String daoLittleName) {
		this.daoLittleName = daoLittleName;
	}

	public String getPrimaryKey() {
		return primaryKey;
	}

	public void setPrimaryKey(String primaryKey) {
		this.primaryKey = primaryKey;
	}

	public String getRepoName() {
		return repoName;
	}

	public void setRepoName(String repoName) {
		this.repoName = repoName;
	}

	public String getRepoFullName() {
		return repoFullName;
	}

	public void setRepoFullName(String repoFullName) {
		this.repoFullName = repoFullName;
	}

	public String getRepoPackageName() {
		return repoPackageName;
	}

	public void setRepoPackageName(String repoPackageName) {
		this.repoPackageName = repoPackageName;
	}

	public String getBoName() {
		return boName;
	}

	public void setBoName(String boName) {
		this.boName = boName;
	}

	public String getBoFullName() {
		return boFullName;
	}

	public void setBoFullName(String boFullName) {
		this.boFullName = boFullName;
	}

	public String getBoPackageName() {
		return boPackageName;
	}

	public void setBoPackageName(String boPackageName) {
		this.boPackageName = boPackageName;
	}

	public String getTableComment() {
		return tableComment;
	}

	public void setTableComment(String tableComment) {
		this.tableComment = tableComment;
	}
}
