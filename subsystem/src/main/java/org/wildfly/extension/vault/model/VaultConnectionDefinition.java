/*
 * Copyright The WildFly Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.wildfly.extension.vault.model;

import java.util.Arrays;
import java.util.Collection;

import org.jboss.as.controller.AttributeDefinition;
import org.jboss.as.controller.SimpleAttributeDefinitionBuilder;
import org.jboss.as.controller.SimpleResourceDefinition;
import org.jboss.as.controller.registry.AttributeAccess;
import org.jboss.dmr.ModelType;
import org.wildfly.extension.vault.VaultExtension;

public class VaultConnectionDefinition extends SimpleResourceDefinition {
    
    public static final VaultConnectionDefinition INSTANCE = new VaultConnectionDefinition();
    
    public static final AttributeDefinition URL = SimpleAttributeDefinitionBuilder
            .create("url", ModelType.STRING)
            .setRequired(true)
            .setFlags(AttributeAccess.Flag.RESTART_RESOURCE_SERVICES)
            .build();
            
    public static final AttributeDefinition TOKEN = SimpleAttributeDefinitionBuilder
            .create("token", ModelType.STRING)
            .setRequired(false)
            .setFlags(AttributeAccess.Flag.RESTART_RESOURCE_SERVICES)
            .build();
            
    public static final AttributeDefinition AUTH_METHOD = SimpleAttributeDefinitionBuilder
            .create("auth-method", ModelType.STRING)
            .setRequired(false)
            .setDefaultValue(new org.jboss.dmr.ModelNode("token"))
            .setFlags(AttributeAccess.Flag.RESTART_RESOURCE_SERVICES)
            .build();
            
    public static final AttributeDefinition NAMESPACE = SimpleAttributeDefinitionBuilder
            .create("namespace", ModelType.STRING)
            .setRequired(false)
            .setFlags(AttributeAccess.Flag.RESTART_RESOURCE_SERVICES)
            .build();
            
    public static final AttributeDefinition SSL_VERIFY = SimpleAttributeDefinitionBuilder
            .create("ssl-verify", ModelType.BOOLEAN)
            .setRequired(false)
            .setDefaultValue(new org.jboss.dmr.ModelNode(true))
            .setFlags(AttributeAccess.Flag.RESTART_RESOURCE_SERVICES)
            .build();
    
    private VaultConnectionDefinition() {
        super(new Parameters(
                VaultExtension.createPath("connection"),
                VaultExtension.getResourceDescriptionResolver("connection"))
                .setAddHandler(VaultConnectionAdd.INSTANCE)
                .setRemoveHandler(VaultConnectionRemove.INSTANCE));
    }
    
    public Collection<AttributeDefinition> getAttributes() {
        return Arrays.asList(URL, TOKEN, AUTH_METHOD, NAMESPACE, SSL_VERIFY);
    }
}