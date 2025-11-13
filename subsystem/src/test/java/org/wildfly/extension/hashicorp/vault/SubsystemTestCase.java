/*
 * Copyright The WildFly Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.wildfly.extension.hashicorp.vault;

import static org.wildfly.extension.hashicorp.vault.VaultExtension.SUBSYSTEM_NAME;
import static org.wildfly.extension.hashicorp.vault.VaultExtension.VaultSubsystemSchema.CURRENT;

import java.util.EnumSet;

import org.jboss.as.subsystem.test.AbstractSubsystemSchemaTest;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

@RunWith(Parameterized.class)
public class SubsystemTestCase extends AbstractSubsystemSchemaTest<VaultExtension.VaultSubsystemSchema> {

    @Parameterized.Parameters
    public static Iterable<VaultExtension.VaultSubsystemSchema> parameters() {
        return EnumSet.allOf(VaultExtension.VaultSubsystemSchema.class);
    }

    public SubsystemTestCase(VaultExtension.VaultSubsystemSchema schema) {
        super(SUBSYSTEM_NAME, new VaultExtension(), schema, CURRENT);
    }

}
