/*
 * Copyright The WildFly Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.wildfly.extension.vault.model;

import org.jboss.as.controller.AbstractAddStepHandler;
import org.jboss.as.controller.OperationContext;
import org.jboss.as.controller.OperationFailedException;
import org.jboss.dmr.ModelNode;
import org.jboss.msc.service.ServiceBuilder;
import org.wildfly.extension.vault.VaultService;

public class VaultConnectionAdd extends AbstractAddStepHandler {
    
    public static final VaultConnectionAdd INSTANCE = new VaultConnectionAdd();
    
    @Override
    protected void performRuntime(OperationContext context, ModelNode operation, ModelNode model)
            throws OperationFailedException {
        
        //String name = context.getCurrentAddressValue();
        String url = VaultConnectionDefinition.URL.resolveModelAttribute(context, model).asString();
        String token = VaultConnectionDefinition.TOKEN.resolveModelAttribute(context, model).asStringOrNull();
        //String authMethod = VaultConnectionDefinition.AUTH_METHOD.resolveModelAttribute(context, model).asString();
        String namespace = VaultConnectionDefinition.NAMESPACE.resolveModelAttribute(context, model).asStringOrNull();
        boolean sslVerify = VaultConnectionDefinition.SSL_VERIFY.resolveModelAttribute(context, model).asBoolean();
        
        VaultService vaultService = new VaultService(url, token, namespace, sslVerify);
        
        ServiceBuilder<?> serviceBuilder = context.getCapabilityServiceTarget()
                                            .addService().setInstance(vaultService);        
        serviceBuilder.install();
    }
}