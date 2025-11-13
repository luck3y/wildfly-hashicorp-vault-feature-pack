/*
 * Copyright The WildFly Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.wildfly.extension.hashicorp.vault;

import static org.jboss.as.controller.descriptions.ModelDescriptionConstants.SUBSYSTEM;

import org.jboss.as.controller.ExtensionContext;
import org.jboss.as.controller.ModelVersion;
import org.jboss.as.controller.PathElement;
import org.jboss.as.controller.SubsystemModel;
import org.jboss.as.controller.SubsystemRegistration;
import org.jboss.as.controller.SubsystemSchema;
import org.jboss.as.controller.descriptions.StandardResourceDescriptionResolver;
import org.jboss.as.controller.persistence.xml.ResourceXMLParticleFactory;
import org.jboss.as.controller.persistence.xml.SubsystemResourceRegistrationXMLElement;
import org.jboss.as.controller.persistence.xml.SubsystemResourceXMLSchema;
import org.jboss.as.controller.xml.VersionedNamespace;
import org.jboss.as.version.Stability;
import org.jboss.staxmapper.IntVersion;
import org.wildfly.subsystem.SubsystemConfiguration;
import org.wildfly.subsystem.SubsystemExtension;
import org.wildfly.subsystem.SubsystemPersistence;

/**
 * WildFly extension that provides HashiCorp Vault Support
 *
 */
public final class VaultExtension extends SubsystemExtension<VaultExtension.VaultSubsystemSchema> {

    /**
     * The name of our subsystem within the model.
     */
    static final String SUBSYSTEM_NAME = "hashicorp-vault";
    private static final Stability FEATURE_STABILITY = Stability.EXPERIMENTAL;
    static final ModelVersion CURRENT_MODEL_VERSION = ModelVersion.create(1, 0, 0);

    static final PathElement SUBSYSTEM_PATH = PathElement.pathElement(SUBSYSTEM, SUBSYSTEM_NAME);
    //private static final String RESOURCE_NAME = VaultExtension.class.getPackage().getName() + ".LocalDescriptions";
    
    public VaultExtension() {
        super(SubsystemConfiguration.of(SUBSYSTEM_NAME, VaultSubsystemModel.CURRENT, VaultSubsystemRegistrar::new),
                SubsystemPersistence.of(VaultSubsystemSchema.CURRENT));
    }

    /**
     * Model for the 'vault' subsystem.
     */
    public enum VaultSubsystemModel implements SubsystemModel {
        VERSION_1_0_0(1, 0, 0),
        ;

        static final VaultSubsystemModel CURRENT = VERSION_1_0_0;

        private final ModelVersion version;

        VaultSubsystemModel(int major, int minor, int micro) {
            this.version = ModelVersion.create(major, minor, micro);
        }

        @Override
        public ModelVersion getVersion() {
            return this.version;
        }
    }

    @Override
    public Stability getStability() {
        return FEATURE_STABILITY;
    }
    
    public static PathElement createPath(String name) {
        return PathElement.pathElement(name);
    }

    public void initialize(ExtensionContext context) {
        final SubsystemRegistration subsystem = context.registerSubsystem(SUBSYSTEM_NAME, CURRENT_MODEL_VERSION);
        subsystem.registerSubsystemModel(new VaultSubsystemDefinition());
        subsystem.registerXMLElementWriter(new VaultSubsystemParser_1_0());
    }

    public enum VaultSubsystemSchema implements SubsystemResourceXMLSchema<VaultSubsystemSchema> {

        VERSION_1_0_0(1, 0, FEATURE_STABILITY);

        static final VaultSubsystemSchema CURRENT = VERSION_1_0_0;

        private final VersionedNamespace<IntVersion, VaultSubsystemSchema> namespace;
        private final ResourceXMLParticleFactory factory = ResourceXMLParticleFactory.newInstance(this);

        VaultSubsystemSchema(int major, int minor, Stability stability) {
            this(major, minor, stability, false);
        }
        VaultSubsystemSchema(int major, int minor, Stability stability, boolean legacy) {
            this.namespace = SubsystemSchema.createSubsystemURN(SUBSYSTEM_NAME, stability, new IntVersion(major, minor));
        }

        @Override
        public VersionedNamespace<IntVersion, VaultSubsystemSchema> getNamespace() {
            return this.namespace;
        }

        @Override
        public Stability getStability() {
           return this.getNamespace().getStability();
        }

        @Override
        public SubsystemResourceRegistrationXMLElement getSubsystemXMLElement() {
            return this.factory.subsystemElement(VaultSubsystemRegistrar.REGISTRATION).build();
        }
    }
}
