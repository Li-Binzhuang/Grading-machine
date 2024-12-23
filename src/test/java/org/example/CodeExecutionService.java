package org.example;

import org.example.LanguageSuport.LanguageConfigurationLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
@Service
public class CodeExecutionService {

    private static final Logger logger = LoggerFactory.getLogger(CodeExecutionService.class);

    @Autowired
    private LanguageConfigurationLoader configLoader;

    @PostConstruct
    public void init() {
        try {
            configLoader.loadConfigurations();
        } catch (IOException e) {

        }
    }

    public static void main(String[] args) {
        CodeExecutionService codeExecutionService = new CodeExecutionService();
        codeExecutionService.init();
        System.out.println("Code execution service started");    }
}