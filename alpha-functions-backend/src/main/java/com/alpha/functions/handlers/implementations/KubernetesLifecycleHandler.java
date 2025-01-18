package com.alpha.functions.handlers.implementations;

import com.alpha.functions.entities.AlphaFunction;
import com.alpha.functions.handlers.KubernetesHandler;
import com.alpha.functions.utils.constants.ApplicationConstants;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.kubernetes.client.custom.Quantity;
import io.kubernetes.client.openapi.ApiException;
import io.kubernetes.client.openapi.apis.AppsV1Api;
import io.kubernetes.client.openapi.apis.AutoscalingV2Api;
import io.kubernetes.client.openapi.apis.BatchV1Api;
import io.kubernetes.client.openapi.apis.CoreV1Api;
import io.kubernetes.client.openapi.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class KubernetesLifecycleHandler extends KubernetesHandler {

    @Autowired
    AppsV1Api k8sAppClient;

    @Autowired
    CoreV1Api k8sCoreClient;

    @Autowired
    AutoscalingV2Api k8sAutoscalingClient;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    BatchV1Api k8sBatchV1Api;


    @Override
    public void namespace() {
        try {
            CoreV1Api.APIreadNamespaceRequest readNamespaceRequest = k8sCoreClient.readNamespace(ApplicationConstants.ALPHA_FUNCTION_DEFAULT_NAMESPACE);
            V1Namespace v1Namespace = readNamespaceRequest.execute();
            if (v1Namespace == null) {
                V1ObjectMeta metadata = new V1ObjectMeta();
                metadata.setName(ApplicationConstants.ALPHA_FUNCTION_DEFAULT_NAMESPACE);
                V1Namespace namespace = new V1Namespace();
                namespace.setApiVersion(ApplicationConstants.ALPHA_FUNCTION_NAMESPACE_VERSION);
                namespace.setKind(ApplicationConstants.ALPHA_FUNCTION_NAMESPACE_KIND);
                namespace.setMetadata(metadata);
                CoreV1Api.APIcreateNamespaceRequest namespaceRequest = k8sCoreClient.createNamespace(namespace);
                namespaceRequest.execute();
            }
        } catch (ApiException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void job(AlphaFunction alphaFunction) {
        List<V1EnvVar> environmentVariables = new ArrayList<>();
        try {
            List<Map<String, String>> envVars = objectMapper.readValue(alphaFunction.getEnvVariables(), List.class);
            if (alphaFunction.getData() != null) {
                V1EnvVar payload = new V1EnvVar();
                payload.setName(ApplicationConstants.ALPHA_FUNCTION_JOB_ENTRY_PAYLOAD_KEY);
                payload.setValue(objectMapper.writeValueAsString(alphaFunction.getData()));
                environmentVariables.add(payload);
            }
            for (Map<String, String> envVar : envVars) {
                V1EnvVar envV = new V1EnvVar();
                envV.setName(envVar.get("key"));
                envV.setValue(envVar.get("value"));
                environmentVariables.add(envV);
            }
            V1Job job = new V1Job()
                    .metadata(new V1ObjectMeta()
                            .name(alphaFunction.getAlphaName() + "-" + UUID.randomUUID())
                            .namespace(ApplicationConstants.ALPHA_FUNCTION_DEFAULT_NAMESPACE))
                    .spec(new V1JobSpec()
                            .ttlSecondsAfterFinished(ApplicationConstants.ALPHA_FUNCTION_JOB_TTL_AFTER_FINISHED)
                            .backoffLimitPerIndex(alphaFunction.getMaxRetries())
                            .completionMode(ApplicationConstants.ALPHA_FUNCTION_JOB_COMPLETION_MODE)
                            .completions(alphaFunction.isParallelExecutionEnabled() ? alphaFunction.getParallelReplicas() : 1)
                            .parallelism(alphaFunction.isParallelExecutionEnabled() ? alphaFunction.getParallelReplicas() : 1)
                            .activeDeadlineSeconds(alphaFunction.getTimeout() != 0 ? alphaFunction.getTimeout() : 30)
                            .template(new V1PodTemplateSpec()
                                    .metadata(new V1ObjectMeta()
                                            .putLabelsItem(ApplicationConstants.ALPHA_FUNCTION_DEFAULT_TAG_KEY, alphaFunction.getAlphaName()))
                                    .spec(new V1PodSpec()
                                            .restartPolicy(ApplicationConstants.ALPHA_FUNCTION_JOB_RESTART_POLICY)
                                            .addContainersItem(new V1Container()
                                                    .name(alphaFunction.getAlphaName())
                                                    .image(alphaFunction.getRepositoryUserName() + "/" + alphaFunction.getAlphaName() + ":" + ApplicationConstants.TAG_LATEST)
                                                    .env(environmentVariables)
                                                    .resources(new V1ResourceRequirements()
                                                            .putLimitsItem("cpu", new Quantity(alphaFunction.getMaximumCpu()))
                                                            .putLimitsItem("memory", new Quantity(alphaFunction.getMaximumMemory()))
                                                            .putRequestsItem("cpu", new Quantity(alphaFunction.getMinimumCpu()))
                                                            .putRequestsItem("memory", new Quantity(alphaFunction.getMinimumMemory())))))));

            BatchV1Api.APIcreateNamespacedJobRequest jobRequest = k8sBatchV1Api.createNamespacedJob(ApplicationConstants.ALPHA_FUNCTION_DEFAULT_NAMESPACE, job);
            jobRequest.execute();
            System.out.println("Job created successfully: " + alphaFunction.getAlphaName());
        } catch (Exception e) {
            throw new RuntimeException("Failed to create Job: " + alphaFunction.getAlphaName(), e);
        }
    }

    @Override
    public Object logs() {
        return null;
    }
}
