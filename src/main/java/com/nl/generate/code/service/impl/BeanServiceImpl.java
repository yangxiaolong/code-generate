package com.nl.generate.code.service.impl;

import java.io.File;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nl.generate.code.service.BeanService;
import com.nl.generate.code.util.DefaultValueUtil;
import com.nl.generate.code.util.FileWriter;
import com.nl.generate.code.vo.BeanPropertyVO;
import com.nl.generate.code.vo.CreateDomainCondition;

@Service
public class BeanServiceImpl implements BeanService {

    private static final List<String> arr = Lists.newArrayList("createTime", "updateTime", "biTimestamp");

    @Autowired
    private FileWriter fileWriter;

    private String placeholder = "    ";

    private String lineBreak = DefaultValueUtil.LINE_BREAK;

    private String semicolon = DefaultValueUtil.SEMICOLON;

    public void createBean(List<CreateDomainCondition> createBeanConditionList, String baseFilePath) {

        String className;
        List<BeanPropertyVO> beanPropertyList;

        String filePathTemplate = baseFilePath + File.separator + "%s.java";
        String filePath;
        for (CreateDomainCondition createBeanCondition : createBeanConditionList) {
            beanPropertyList = createBeanCondition.getBeanPropertyList();
            className = createBeanCondition.getDomainClassPoName();
            filePath = String.format(filePathTemplate,
                    createBeanCondition.getDomainClassPoFullName().replace(".", File.separator));
            Map<String, Object> content = new HashMap<String, Object>();
            content.put("className", className);
            content.put("tableName", createBeanCondition.getTableName());
            content.put("packageName", createBeanCondition.getDomainPackageName());
            content.put("con", createPrivateProperty(beanPropertyList, createBeanCondition.getPrimaryKey()));
            content.put("package", createImportPackage(beanPropertyList));
            try {
                fileWriter.write(content, DefaultValueUtil.TEMPLATE_BEAN_PATH, filePath);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private String createImportPackage(List<BeanPropertyVO> beanPropertyList) {
        StringBuffer importPackage = new StringBuffer();

        Set<String> importSet = new HashSet<String>();

        for (BeanPropertyVO beanProperty : beanPropertyList) {
            if (beanProperty.getDataType().indexOf("java.lang.") > -1)
                continue;
            importSet.add(beanProperty.getDataType());
        }

        for (String importPackageName : importSet) {
            importPackage.append("import ").append(importPackageName).append(semicolon).append(lineBreak);
        }

        return importPackage.toString();
    }

    private String createPrivateProperty(List<BeanPropertyVO> beanPropertyList, String primaryKey) {

        StringBuffer propertiesPrivate = new StringBuffer();

        StringBuffer propertisSetGet = new StringBuffer();

        String littlePropertyName = null;
        String bigPropertyName = null;
        String dataType = null;

        for (BeanPropertyVO beanProperty : beanPropertyList) {
            littlePropertyName = beanProperty.getLittleColumnName();
            bigPropertyName = beanProperty.getBigColumnName();
            dataType = beanProperty.getSimpleDataType();
            if (StringUtils.isNotBlank(beanProperty.getColumnComment())) {
                propertiesPrivate.append(placeholder).append("// ").append(beanProperty.getColumnComment())
                        .append(lineBreak);
            }
            propertiesPrivate.append(placeholder).append("@Field(");
            String de = "";
            if (arr.contains(littlePropertyName)) {
                de = ", creatDefaultValue = \"now()\", isUpdate = false";
            }

            propertiesPrivate.append("column = \"" + beanProperty.getColumnName() + "\"" + de);

            if (beanProperty.getColumnName().equals(primaryKey)) propertiesPrivate.append(", isPrimaryKey = true");
            propertiesPrivate.append(")").append(lineBreak);


            propertiesPrivate.append(placeholder).append("private ").append(dataType).append(" ")
                    .append(littlePropertyName).append(semicolon).append(lineBreak);
            propertiesPrivate.append(lineBreak);
//			propertisSetGet.append(placeholder).append("public ").append(dataType).append(" ").append("get")
//					.append(bigPropertyName).append("() {").append(lineBreak);
//			propertisSetGet.append(placeholder).append(placeholder).append("return ").append(littlePropertyName)
//					.append(semicolon).append(lineBreak);
//			propertisSetGet.append(placeholder).append("}").append(lineBreak);
//			propertisSetGet.append(lineBreak);
//			propertisSetGet.append(placeholder).append("public void set").append(bigPropertyName).append("(")
//					.append(dataType).append(" ").append(littlePropertyName).append(")").append(" {").append(lineBreak);
//			propertisSetGet.append(placeholder).append(placeholder).append("this.").append(littlePropertyName)
//					.append(" = ").append(littlePropertyName).append(semicolon).append(lineBreak);
//			propertisSetGet.append(placeholder).append("}").append(lineBreak);
//			propertisSetGet.append(lineBreak);
        }

        return propertiesPrivate.toString() + propertisSetGet.toString();
    }

}
