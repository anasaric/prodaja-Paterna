package com.paterna.config;

import org.camunda.bpm.engine.RepositoryService;
import org.camunda.bpm.engine.repository.Deployment;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class CamundaConfig {

    private final RepositoryService repositoryService;

    public CamundaConfig(RepositoryService repositoryService) {
        this.repositoryService = repositoryService;
    }

    @PostConstruct
    public void deployModel() {
        Deployment deployment = repositoryService.createDeployment()
                .addClasspathResource("proces-prodaje-Paterna.bpmn")
                .addClasspathResource("forms/narudzbaKupca.form")
                .name("Proces prodaje Paterna")
                .deploy();
    }
}
