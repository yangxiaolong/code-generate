package com.nl.generate.code.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.Map;
import java.util.Properties;

import javax.annotation.PostConstruct;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

@Component
public class FileWriter implements ApplicationContextAware, InitializingBean {

    private VelocityEngine velocityEngine;

    private final String CONTENT_ENCODING = "UTF-8";

    private ApplicationContext applicationContext;


    public void init() throws Exception {
        Resource resource = applicationContext.getResource("classpath:template");
        Properties properties = new Properties();
        properties.setProperty("resource.loader", "file");
        properties.setProperty("file.resource.loader.description", "Velocity File Resource Loader");
        properties.setProperty("file.resource.loader.path", resource.getURL().getPath());
        properties.setProperty("file.resource.loader.cache", "true");
        properties.setProperty("file.resource.loader.modificationCheckInterval", "30");
        properties.setProperty("runtime.log.logsystem.class", "org.apache.velocity.runtime.log.Log4JLogChute");
        properties.setProperty("runtime.log.logsystem.log4j.logger", "org.apache.velocity");
        properties.setProperty("directive.set.null.allowed", "true");
        velocityEngine = new VelocityEngine();
        velocityEngine.init(properties);
    }

    public void write(Map<String, Object> content, String templatePath, String filePath) throws Exception {
        File file = new File(filePath);
        if (!file.exists()) {
            if (!file.getParentFile().exists()) file.getParentFile().mkdirs();
            file.createNewFile();
        }

        VelocityContext context = new VelocityContext();
        for (Map.Entry<String, Object> entry : content.entrySet()) {
            context.put(entry.getKey(), entry.getValue());
        }
        Template template = velocityEngine.getTemplate(templatePath, CONTENT_ENCODING);
        FileOutputStream outStream = new FileOutputStream(file);
        BufferedWriter bufferWriter = new BufferedWriter(new OutputStreamWriter(outStream));
        template.merge(context, bufferWriter);
        bufferWriter.flush();
        bufferWriter.close();
        outStream.close();
    }

    public void setApplicationContext(ApplicationContext applicationContext)
            throws BeansException {
        this.applicationContext = applicationContext;
    }


    @Override
    public void afterPropertiesSet() throws Exception {
        init();
    }
}

