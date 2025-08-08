/*
 * Copyright The WildFly Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.wildfly.extension.vault;

import org.jboss.as.controller.SimpleResourceDefinition;
import org.jboss.as.controller.capability.RuntimeCapability;
import org.jboss.as.controller.registry.ManagementResourceRegistration;
import org.wildfly.extension.vault.model.VaultConnectionDefinition;

public class VaultSubsystemDefinition extends SimpleResourceDefinition {
    
    public static final VaultSubsystemDefinition INSTANCE = new VaultSubsystemDefinition();
    
    public static final RuntimeCapability<Void> VAULT_CAPABILITY =
            RuntimeCapability.Builder.of("org.wildfly.vault")
                    .build();
    
    private VaultSubsystemDefinition() {
        super(new Parameters(VaultExtension.createSubsystemPath(),
                VaultExtension.getResourceDescriptionResolver())
                .setAddHandler(VaultSubsystemAdd.INSTANCE)
                .setRemoveHandler(VaultSubsystemRemove.INSTANCE)
                .setCapabilities(VAULT_CAPABILITY));
    }
    
    @Override
    public void registerChildren(ManagementResourceRegistration resourceRegistration) {
        resourceRegistration.registerSubModel(VaultConnectionDefinition.INSTANCE);
    }
}