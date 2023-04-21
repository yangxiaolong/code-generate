package com.nl.generate.code.service.impl;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nl.generate.code.service.MybatiesService;
import com.nl.generate.code.util.DefaultValueUtil;
import com.nl.generate.code.util.FileWriter;
import com.nl.generate.code.vo.CreateDomainCondition;

@Service
public class MybatiesServiceImpl implements MybatiesService {

    @Autowired
    private FileWriter fileWriter;

    public void createMybaties(List<CreateDomainCondition> createBeanConditionList, String baseFilePath) {

        String filePathTemplate = baseFilePath + File.separator + "%s.xml";

        String filePath;

        for (CreateDomainCondition createBeanCondition : createBeanConditionList) {
            filePath = String.format(filePathTemplate, createBeanCondition.getDaoName());
            Map<String, Object> content = new HashMap<String, Object>();
            content.put("beanInfo", createBeanCondition);
            try {
                fileWriter.write(content, DefaultValueUtil.TEMPLATE_MYBATIES_PATH, filePath);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

}
