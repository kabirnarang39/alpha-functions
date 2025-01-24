# alpha-functions-frontend Helm Chart

This Helm chart is designed to deploy the **alpha-functions-frontend** application to a Kubernetes cluster.

## Prerequisites

- Kubernetes 1.16+
- Helm 3.x+
- A running Kubernetes cluster

## Installing the Chart

To install the chart with default settings (no ingress):

```bash
helm repo add alpha-functions-frontend https://kabirnarang39.github.io/alpha-functions/alpha-functions-deployment/helm/alpha-functions-frontend/
helm install alpha-functions-frontend alpha-functions-frontend/alpha-functions-frontend