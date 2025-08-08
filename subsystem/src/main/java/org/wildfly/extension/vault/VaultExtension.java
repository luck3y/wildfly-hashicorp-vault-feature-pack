/*
 * Copyright The WildFly Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.wildfly.extension.vault;

import static org.jboss.as.controller.descriptions.ModelDescriptionConstants.SUBSYSTEM;

import org.jboss.as.controller.Extension;
import org.jboss.as.controller.ExtensionContext;
import org.jboss.as.controller.ModelVersion;
import org.jboss.as.controller.PathElement;
import org.jboss.as.controller.SubsystemRegistration;
import org.jboss.as.controller.descriptions.StandardResourceDescriptionResolver;
import org.jboss.as.controller.parsing.ExtensionParsingContext;
import org.jboss.as.version.Stability;

public class VaultExtension implements Extension {
    
    public static final String SUBSYSTEM_NAME = "vault";
    public static final String NAMESPACE_1_0 = "urn:wildfly:vault:1.0";

    protected static final PathElement SUBSYSTEM_PATH = PathElement.pathElement(SUBSYSTEM, SUBSYSTEM_NAME);
    private static final Stability FEATURE_STABILITY = Stability.PREVIEW;
    
    private static final ModelVersion CURRENT_MODEL_VERSION = ModelVersion.create(1, 0, 0);
    
    public static StandardResourceDescriptionResolver getResourceDescriptionResolver(final String... keyPrefix) {
        StringBuilder prefix = new StringBuilder("vault");
        for (String kp : keyPrefix) {
            if (prefix.length() > 0) {
                prefix.append('.');
            }
            prefix.append(kp);
        }
        return new StandardResourceDescriptionResolver(prefix.toString(),
                "org.wildfly.extension.vault.LocalDescriptions",
                VaultExtension.class.getClassLoader(),
                true,
                false);
    }
    
    @Override
    public Stability getStability() {
        return FEATURE_STABILITY;
    }

    static PathElement createSubsystemPath() {
        return PathElement.pathElement("subsystem", SUBSYSTEM_NAME);
    }
    
    public static PathElement createPath(String name) {
        return PathElement.pathElement(name);
    }
    
    @Override
    public void initialize(ExtensionContext context) {
        final SubsystemRegistration subsystem = context.registerSubsystem(
                SUBSYSTEM_NAME, CURRENT_MODEL_VERSION);
        
        subsystem.registerSubsystemModel(VaultSubsystemDefinition.INSTANCE);
        subsystem.registerXMLElementWriter(VaultSubsystemParser_1_0.INSTANCE);
    }
    
    @Override
    public void initializeParsers(ExtensionParsingContext context) {
        context.setSubsystemXmlMapping(SUBSYSTEM_NAME, NAMESPACE_1_0, 
                VaultSubsystemParser_1_0.INSTANCE);
    }
}