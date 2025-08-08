/*
 * Copyright The WildFly Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.wildfly.extension.vault;

import org.jboss.as.controller.AbstractAddStepHandler;
import org.jboss.as.controller.OperationContext;
import org.jboss.as.controller.OperationFailedException;
import org.jboss.dmr.ModelNode;

public class VaultSubsystemAdd extends AbstractAddStepHandler {
    
    public static final VaultSubsystemAdd INSTANCE = new VaultSubsystemAdd();
    
    private VaultSubsystemAdd() {
        //super(VaultSubsystemDefinition.VAULT_CAPABILITY);
    }
    
    @Override
    protected void performRuntime(OperationContext context, ModelNode operation, ModelNode model)
            throws OperationFailedException {
        // No runtime services to start at subsystem level
        // Individual connections will start their own services
    }
}