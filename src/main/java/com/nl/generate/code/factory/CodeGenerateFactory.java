package com.nl.generate.code.factory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.nl.generate.code.service.BeanService;
import com.nl.generate.code.service.BoService;
import com.nl.generate.code.service.DAOService;
import com.nl.generate.code.service.DBService;
import com.nl.generate.code.service.MybatiesService;
import com.nl.generate.code.service.ProviderService;
import com.nl.generate.code.service.RepoService;
import com.nl.generate.code.util.NameFormatUtil;
import com.nl.generate.code.vo.BeanPropertyVO;
import com.nl.generate.code.vo.CreateDomainCondition;

@Component
public class CodeGenerateFactory implements InitializingBean {

    @Autowired
    private DBService dbService;

    @Autowired
    private BeanService beanService;

    @Autowired
    private MybatiesService mybatiesService;

    @Autowired
    private RepoService repoService;

    @Autowired
    private DAOService daoService;

    @Autowired
    private ProviderService providerService;

    @Autowired
    private BoService boService;

    @Value("${target.table.name}")
    private String tableName;

    @Value("${target.table.exclude}")
    private String excludeTableName;

    @Value("${target.data.base.name}")
    private String dataBaseName;

    @Value("${target.base.file.path}")
    private String baseFilePath;

    @Value("${target.base.file.mapper.path}")
    private String mapperBaseFilePath;

    @Value("${target.base.file.bo.path}")
    private String boBaseFilePath;

    @Value("${target.package.domain.name}")
    private String domainPackageName;

    @Value("${target.package.dao.name}")
    private String daoPackageName;

    @Value("${target.package.service.name}")
    private String servicePackageName;

    @Value("${target.package.provider.name}")
    private String providerPackageName;

    @Value("${target.package.repo.name}")
    private String repoPackageName;

    @Value("${target.package.bo.name}")
    private String boPackageName;

    @PostConstruct
    public void generateTable() {
        System.out.println(1234);
        tableName = tableName.toLowerCase();
        List<String> tableNames;
        if (tableName.contains("*")) {
            tableNames = dbService.getDBInfo(dataBaseName);
        } else {
            tableNames = Arrays.asList(tableName.split(","));
        }
        if (excludeTableName.trim().length() > 0) {
            String[] excludeTableNames = excludeTableName.split(",");
            tableNames.removeAll(Arrays.asList(excludeTableNames));
        }

        List<CreateDomainCondition> createBeanConditionList = new ArrayList<>();

        for (String table : tableNames) {
            CreateDomainCondition createBeanCondition = new CreateDomainCondition();
            String tableComment = dbService.getTableComment(dataBaseName, table);
            if (!StringUtils.isEmpty(tableComment)) {
                tableComment = tableComment.trim();
            }
            createBeanCondition.setTableComment(tableComment);
            createBeanCondition.setDomainClassName(NameFormatUtil.bigCamelCase(table));
            createBeanCondition.setDomainClassPoName(createBeanCondition.getDomainClassName() + "Po");
            createBeanCondition.setDomainLittleClassName(NameFormatUtil.littleCamelCase(table));
            createBeanCondition.setDomainLittleClassPoName(NameFormatUtil.littleCamelCase(table) + "Po");
            createBeanCondition.setBeanPropertyList(dbService.getTableInfo(table, dataBaseName));
            for (BeanPropertyVO beanProperty : createBeanCondition.getBeanPropertyList()) {
                if (beanProperty.isPrimaryKey()) {
                    createBeanCondition.setPrimaryKey(beanProperty.getColumnName());
                    break;
                }
            }
            createBeanCondition.setTableName(table);
            createBeanCondition.setDomainPackageName(domainPackageName);
            createBeanCondition
                    .setDomainClassFullName(domainPackageName + "." + createBeanCondition.getDomainClassName());
            createBeanCondition
                    .setDomainClassPoFullName(domainPackageName + "." + createBeanCondition.getDomainClassPoName());

            // bo
            createBeanCondition.setServiceName(createBeanCondition.getDomainClassName() + "Service");
            createBeanCondition.setServiceFullName(servicePackageName + "." + createBeanCondition.getServiceName());
            createBeanCondition.setServicePackageName(servicePackageName);

            // service
            createBeanCondition.setServiceName(createBeanCondition.getDomainClassName() + "Service");
            createBeanCondition.setServiceFullName(servicePackageName + "." + createBeanCondition.getServiceName());
            createBeanCondition.setServicePackageName(servicePackageName);
            // dao
            createBeanCondition.setDaoName(createBeanCondition.getDomainClassName() + "Mapper");
            createBeanCondition.setDaoLittleName(NameFormatUtil.littleCamelCase(table) + "Mapper");
            createBeanCondition.setDaoFullName(daoPackageName + "." + createBeanCondition.getDaoName());
            createBeanCondition.setDaoPackageName(daoPackageName);
            // provider
            createBeanCondition.setProviderName(createBeanCondition.getDomainClassName() + "Provider");
            createBeanCondition.setProviderPackageName(providerPackageName);
            createBeanCondition.setProviderFullName(providerPackageName + "." + createBeanCondition.getProviderName());

            // repo
            createBeanCondition.setRepoName(createBeanCondition.getDomainClassName() + "Repository");
            createBeanCondition.setRepoPackageName(repoPackageName);
            createBeanCondition.setRepoFullName(repoPackageName + "." + createBeanCondition.getRepoName());

            // bo
            createBeanCondition.setBoName(createBeanCondition.getDomainClassName() + "Bo");
            createBeanCondition.setBoPackageName(boPackageName);
            createBeanCondition.setBoFullName(boPackageName + "." + createBeanCondition.getBoName());
            createBeanConditionList.add(createBeanCondition);
        }
        // 创建对应的bean对象
        beanService.createBean(createBeanConditionList, baseFilePath);

        // 创建dao
        daoService.createDAO(createBeanConditionList, baseFilePath);

        // 创建mybaties文件
        mybatiesService.createMybaties(createBeanConditionList, mapperBaseFilePath);

        // 创建对应的service
        repoService.createService(createBeanConditionList, baseFilePath);

        // 创建provider
        providerService.createProvider(createBeanConditionList, baseFilePath);

        // 创建bo
        boService.createBean(createBeanConditionList, boBaseFilePath);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        generateTable();
    }

}
