package com.nl.generate.code.dao;

import java.util.List;

import com.nl.generate.code.vo.TableInfoVO;

public interface TableInfoDAO {

	public List<TableInfoVO> getTableInfo(String tableName, String databaseName);

}
