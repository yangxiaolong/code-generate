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

import com.nl.generate.code.service.BoService;
import com.nl.generate.code.util.DefaultValueUtil;
import com.nl.generate.code.util.FileWriter;
import com.nl.generate.code.vo.BeanPropertyVO;
import com.nl.generate.code.vo.CreateDomainCondition;

@Service
public class BoServiceImpl implements BoService {

//    private static final List<String> arr = Lists.newArrayList("id", "loanAppCode", "entityId", "entityType", "parentEntityId", "parentEntityType");

    @Autowired
    private FileWriter fileWriter;

    private static final String placeholder = "    ";

    private String lineBreak = DefaultValueUtil.LINE_BREAK;

    private String semicolon = DefaultValueUtil.SEMICOLON;

    public void createBean(List<CreateDomainCondition> createBeanConditionList, String baseFilePath) {

        List<BeanPropertyVO> beanPropertyList;

        String filePathTemplate = baseFilePath + File.separator + "%s.java";
        String filePath;
        for (CreateDomainCondition createBeanCondition : createBeanConditionList) {
            beanPropertyList = createBeanCondition.getBeanPropertyList();
            filePath = String.format(filePathTemplate, createBeanCondition.getBoFullName().replace(".",
                    File.separator));
            Map<String, Object> content = new HashMap<>();
            content.put("schema", "\"" + createBeanCondition.getTableComment() + "\"");
            content.put("className", createBeanCondition.getBoName());
            content.put("packageName", createBeanCondition.getBoPackageName());
            content.put("con", createPrivateProperty(beanPropertyList));
            content.put("package", createImportPackage(beanPropertyList));
            try {
                fileWriter.write(content, DefaultValueUtil.TEMPLATE_BO_PATH, filePath);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private String createImportPackage(List<BeanPropertyVO> beanPropertyList) {
        StringBuilder importPackage = new StringBuilder();

        Set<String> importSet = new HashSet<>();

        for (BeanPropertyVO beanProperty : beanPropertyList) {
            if (beanProperty.getDataType().contains("java.lang."))
                continue;
            importSet.add(beanProperty.getDataType());
        }

        for (String importPackageName : importSet) {
            importPackage.append("import ").append(importPackageName).append(semicolon).append(lineBreak);
        }

        return importPackage.toString();
    }

    private String createPrivateProperty(List<BeanPropertyVO> beanPropertyList) {

        StringBuilder propertiesPrivate = new StringBuilder();

        String littlePropertyName;
        String dataType;

        for (BeanPropertyVO beanProperty : beanPropertyList) {
            littlePropertyName = beanProperty.getLittleColumnName();

            /*if (arr.contains(littlePropertyName)) {
                continue;
            }*/

            dataType = beanProperty.getSimpleDataType();
            if (StringUtils.isNotBlank(beanProperty.getColumnComment())) {
                propertiesPrivate.append(placeholder).append("// ").append(beanProperty.getColumnComment())
                        .append(lineBreak);
                propertiesPrivate.append(placeholder).append("@Schema(description = \"").append(beanProperty.getColumnComment())
                        .append("\")").append(lineBreak);
                propertiesPrivate.append(placeholder).append("@ApiModelProperty(\"")
                        .append(beanProperty.getColumnComment())
                        .append("\")").append(lineBreak);
            }


            propertiesPrivate.append(placeholder).append("private ").append(dataType).append(" ")
                    .append(littlePropertyName).append(semicolon).append(lineBreak);
            propertiesPrivate.append(lineBreak);
        }

        return propertiesPrivate.toString() + "";
    }

}
