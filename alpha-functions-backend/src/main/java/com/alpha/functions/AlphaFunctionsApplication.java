package com.alpha.functions;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.kubernetes.client.openapi.ApiClient;
import io.kubernetes.client.openapi.apis.*;
import io.kubernetes.client.util.Config;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.io.IOException;

@SpringBootApplication
@EnableJpaRepositories(basePackages = {"com.alpha.functions.repositories"})
@EntityScan(basePackages = {"com.alpha.functions.entities"})
public class AlphaFunctionsApplication {

    public static void main(String[] args) {
        SpringApplication.run(AlphaFunctionsApplication.class, args);
    }

    @Bean
    public AppsV1Api kubernetesAppsClient() throws IOException {
        ApiClient client = Config.defaultClient();
        return new AppsV1Api(client);
    }

    @Bean
    public CoreV1Api kubernetesCoreClient() throws IOException {
        ApiClient client = Config.defaultClient();
        return new CoreV1Api(client);
    }

    @Bean
    public AutoscalingV2Api autoScalingClient() throws IOException {
        ApiClient client = Config.defaultClient();
        return new AutoscalingV2Api(client);
    }

    @Bean
    public BatchV1Api batchV1Api() throws IOException {
        ApiClient client = Config.defaultClient();
        return new BatchV1Api(client);
    }
}
