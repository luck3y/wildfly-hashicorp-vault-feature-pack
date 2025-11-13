/*
 * Copyright The WildFly Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.wildfly.extension.hashicorp.vault;

import org.jboss.as.controller.ReloadRequiredWriteAttributeHandler;
import org.jboss.as.controller.SimpleAttributeDefinition;
import org.jboss.as.controller.SimpleAttributeDefinitionBuilder;
import org.jboss.as.controller.SimpleResourceDefinition;
import org.jboss.as.controller.operations.common.GenericSubsystemDescribeHandler;
import org.jboss.as.controller.registry.AttributeAccess;
import org.jboss.as.controller.registry.ManagementResourceRegistration;
import org.jboss.dmr.ModelNode;
import org.jboss.dmr.ModelType;

public class VaultSubsystemDefinition extends SimpleResourceDefinition {

    static final String HOST="host-name";
    static final String AUTH_METHOD="auth-method";
    static final String TLS_VERIFY="tls-verify";
    static final String TOKEN="token";
    static final String NAMESPACE="namespace";

    // TODO: add additional attributes for the subsystem here
    protected static final SimpleAttributeDefinition HOST_NAME_DEF =
            new SimpleAttributeDefinitionBuilder(HOST, ModelType.STRING)
                    .setRequired(false)
                    .setDefaultValue(new ModelNode(HOST))
                    .setAllowExpression(true)
                    .setXmlName(HOST)
                    .setFlags(AttributeAccess.Flag.RESTART_ALL_SERVICES)
                    .build();

    protected static final SimpleAttributeDefinition AUTH_METHOD_DEF =
            new SimpleAttributeDefinitionBuilder(AUTH_METHOD, ModelType.STRING)
                    .setRequired(false)
                    .setDefaultValue(new ModelNode("token"))
                    .setAllowExpression(true)
                    .setXmlName(AUTH_METHOD)
                    .setFlags(AttributeAccess.Flag.RESTART_ALL_SERVICES)
                    .build();

    protected static final SimpleAttributeDefinition TLS_VERIFY_DEF =
            new SimpleAttributeDefinitionBuilder(TLS_VERIFY, ModelType.BOOLEAN)
                    .setRequired(false)
                    .setDefaultValue(new ModelNode(true))
                    .setAllowExpression(true)
                    .setXmlName(TLS_VERIFY)
                    .setFlags(AttributeAccess.Flag.RESTART_ALL_SERVICES)
                    .build();

    protected static final SimpleAttributeDefinition NAMESPACE_DEF =
            new SimpleAttributeDefinitionBuilder(NAMESPACE, ModelType.STRING)
                    .setRequired(false)
                    .setAllowExpression(true)
                    .setXmlName(NAMESPACE)
                    .setFlags(AttributeAccess.Flag.RESTART_ALL_SERVICES)
                    .build();

    protected static final SimpleAttributeDefinition TOKEN_DEF =
            new SimpleAttributeDefinitionBuilder(TOKEN, ModelType.STRING)
                    .setRequired(false)
                    .setAllowExpression(true)
                    .setXmlName(TOKEN)
                    .setFlags(AttributeAccess.Flag.RESTART_ALL_SERVICES)
                    .build();

    protected VaultSubsystemDefinition() {
        super(VaultExtension.SUBSYSTEM_PATH,
                VaultSubsystemRegistrar.RESOLVER,
                VaultSubsystemAdd.INSTANCE,
                VaultSubsystemRemove.INSTANCE);
    }

    /**
     * {@inheritDoc}
     * Registers an add operation handler or a remove operation handler if one was provided to the constructor.
     */
    @Override
    public void registerOperations(ManagementResourceRegistration registration) {
        super.registerOperations(registration);
        registration.registerOperationHandler(GenericSubsystemDescribeHandler.DEFINITION, GenericSubsystemDescribeHandler.INSTANCE);

    }

    @Override
    public void registerAttributes(ManagementResourceRegistration resourceRegistration) {
        resourceRegistration.registerReadWriteAttribute(HOST_NAME_DEF, null, new ReloadRequiredWriteAttributeHandler(HOST_NAME_DEF));
        resourceRegistration.registerReadWriteAttribute(AUTH_METHOD_DEF, null, new ReloadRequiredWriteAttributeHandler(AUTH_METHOD_DEF));
        resourceRegistration.registerReadWriteAttribute(NAMESPACE_DEF, null, new ReloadRequiredWriteAttributeHandler(NAMESPACE_DEF));
        resourceRegistration.registerReadWriteAttribute(TLS_VERIFY_DEF, null, new ReloadRequiredWriteAttributeHandler(TLS_VERIFY_DEF));
        resourceRegistration.registerReadWriteAttribute(TOKEN_DEF, null, new ReloadRequiredWriteAttributeHandler(TOKEN_DEF));
        //TODO: other definitions here...
    }
}
