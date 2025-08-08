/*
 * Copyright The WildFly Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.wildfly.extension.vault.model;

import org.jboss.as.controller.AbstractRemoveStepHandler;
import org.jboss.as.controller.OperationContext;
import org.jboss.as.controller.OperationFailedException;
import org.jboss.dmr.ModelNode;
import org.wildfly.extension.vault.VaultService;

public class VaultConnectionRemove extends AbstractRemoveStepHandler {
    
    public static final VaultConnectionRemove INSTANCE = new VaultConnectionRemove();
    
    @Override
    protected void performRuntime(OperationContext context, ModelNode operation, ModelNode model)
            throws OperationFailedException {
        
        String name = context.getCurrentAddressValue();
        context.removeService(VaultService.createServiceName(name));
    }
}