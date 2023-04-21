package com.nl.generate.code.service;

import java.util.List;

import com.nl.generate.code.vo.BeanPropertyVO;

public interface DBService {

    List<BeanPropertyVO> getTableInfo(String tableName, String databaseName);

    List<String> getDBInfo(String databaseName);

    String getTableComment(String tableName, String dataBaseName);

}
