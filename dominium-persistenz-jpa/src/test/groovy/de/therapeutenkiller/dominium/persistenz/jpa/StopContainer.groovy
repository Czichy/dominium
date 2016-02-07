package de.therapeutenkiller.dominium.persistenz.jpa

import org.spockframework.runtime.extension.IMethodInterceptor
import org.spockframework.runtime.extension.IMethodInvocation

/**
 * Created by matthias on 05.02.16.
 */
class StopContainer implements IMethodInterceptor {
    private final DeltaspikeExtension deltaspikeExtension

    StopContainer(DeltaspikeExtension deltaspikeExtension) {
        this.deltaspikeExtension = deltaspikeExtension
    }

    @Override
    void intercept(IMethodInvocation iMethodInvocation) throws Throwable {
        this.deltaspikeExtension.afterMethod()
    }
}