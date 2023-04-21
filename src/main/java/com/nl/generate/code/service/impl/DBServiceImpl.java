package com.nl.generate.code.service.impl;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.nl.generate.code.service.DBService;
import com.nl.generate.code.util.NameFormatUtil;
import com.nl.generate.code.util.SqlTypeUtil;
import com.nl.generate.code.vo.BeanPropertyVO;

@Service
public class DBServiceImpl implements DBService {

    @Value("${mysql.url}")
    private String url;

    @Value("${mysql.username}")
    private String username;

    @Value("${mysql.password}")
    private String password;

    private Class<?> driver;

    public List<BeanPropertyVO> getTableInfo(String tableName,
                                             String databaseName) {
        try {
            if (driver == null) driver = Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(url, username, password);
            String sql = "select column_name ,data_type,column_comment,0,0,character_maximum_length,is_nullable nullable,column_key from information_schema.columns where table_name =  '"
                    + tableName + "' " + "and table_schema =  '" + databaseName + "'";
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            List<BeanPropertyVO> beanPropertyList = new ArrayList<BeanPropertyVO>();
            while (rs.next()) {
                BeanPropertyVO beanProperty = new BeanPropertyVO();

                String columnName = rs.getString(1);
                String type = rs.getString(2);
                String comment = rs.getString(3);
                String precision = rs.getString(4);
                String scale = rs.getString(5);
                String columnKey = rs.getString(8);
                //String charmaxLength = rs.getString(6) == null ? "" : rs.getString(6);
                type = SqlTypeUtil.getType(type, precision, scale);

                beanProperty.setColumnName(columnName);
                beanProperty.setLittleColumnName(NameFormatUtil.littleCamelCase(columnName));
                beanProperty.setBigColumnName(NameFormatUtil.bigCamelCase(columnName));
                beanProperty.setDataType(type);
                beanProperty.setSimpleDataType(type.substring(type.lastIndexOf(".") + 1, type.length()));
                beanProperty.setColumnComment(comment);
                beanProperty.setColumnKey(columnKey);
                beanPropertyList.add(beanProperty);
            }
            con.close();
            return beanPropertyList;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<String> getDBInfo(String databaseName) {
        List<String> dbInfoList = new ArrayList<String>();
        try {
            Connection con = DriverManager.getConnection(url, username, password);
            if (driver == null) driver = Class.forName("com.mysql.cj.jdbc.Driver");
            String sql = "select table_name from information_schema.`tables` where table_schema = '" + databaseName + "'";
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                dbInfoList.add(rs.getString(1));
            }
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dbInfoList;
    }

    @Override
    public String getTableComment(String dataBaseName, String tableName) {

        List<String> dbInfoList = new ArrayList<String>();
        Connection con = null;
        try {
            if (driver == null) driver = Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(url, username, password);
            String sql = "select TABLE_COMMENT from information_schema.`TABLES` " +
                    "where TABLE_SCHEMA = '" + dataBaseName + "' and TABLE_NAME = '" + tableName + "'";
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                return rs.getString(1);
            }
        } catch (Exception e) {
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            e.printStackTrace();
        }
        return null;
    }

}
