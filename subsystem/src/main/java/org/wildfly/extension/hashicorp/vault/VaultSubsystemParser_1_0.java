/*
- * Copyright The WildFly Authors
- * SPDX-License-Identifier: Apache-2.0
- */
package org.wildfly.extension.hashicorp.vault;

import static org.jboss.as.controller.PersistentResourceXMLDescription.builder;

import org.jboss.as.controller.PersistentResourceXMLDescription;
import org.jboss.as.controller.PersistentResourceXMLParser;

public class VaultSubsystemParser_1_0 extends PersistentResourceXMLParser {
    
    public static final VaultSubsystemParser_1_0 INSTANCE = new VaultSubsystemParser_1_0();

    public static final String NAMESPACE = "urn:wildfly:hashicorp-vault:experimental:1.0";

    private static final PersistentResourceXMLDescription xmlDescription;

    static {
        xmlDescription = builder(VaultExtension.SUBSYSTEM_PATH, NAMESPACE)
                .addAttributes(
                        VaultSubsystemDefinition.HOST_NAME_DEF,
                        VaultSubsystemDefinition.AUTH_METHOD_DEF,
                        VaultSubsystemDefinition.TLS_VERIFY_DEF,
                        VaultSubsystemDefinition.NAMESPACE_DEF
                )
                .build();
    }

    @Override
    public PersistentResourceXMLDescription getParserDescription() {
        return xmlDescription;
    }
}
