package com.alpha.functions.handlers;

import com.alpha.functions.entities.AlphaFunction;

public abstract class KubernetesHandler {

    private void preHandle() {
    }

    public void handleKubernetesProcess(AlphaFunction alphaFunction) {
        preHandle();
        namespace();
        job(alphaFunction);
        postHandle();
    }

    private void postHandle() {
    }

    public abstract void namespace();

    public abstract void job(AlphaFunction alphaFunction);

    public abstract Object logs();
}
