/*
 * Copyright The WildFly Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.wildfly.extension.vault;

import static org.jboss.as.controller.PersistentResourceXMLDescription.builder;

import org.jboss.as.controller.PersistentResourceXMLDescription;
import org.jboss.as.controller.PersistentResourceXMLParser;
import org.wildfly.extension.vault.model.VaultConnectionDefinition;

public class VaultSubsystemParser_1_0 extends PersistentResourceXMLParser {
    
    public static final VaultSubsystemParser_1_0 INSTANCE = new VaultSubsystemParser_1_0();

    public static final String NAMESPACE = "urn:wildfly:hashicorp-vault:1.0";

    private static final PersistentResourceXMLDescription xmlDescription;

    static {
        xmlDescription = builder(VaultExtension.SUBSYSTEM_PATH, NAMESPACE)
                .addAttributes(
                        VaultConnectionDefinition.URL,
                        VaultConnectionDefinition.AUTH_METHOD,
                        VaultConnectionDefinition.SSL_VERIFY,
                        VaultConnectionDefinition.TOKEN,
                        VaultConnectionDefinition.NAMESPACE
                )
                .build();
    }

    @Override
    public PersistentResourceXMLDescription getParserDescription() {
        return xmlDescription;
    }
}