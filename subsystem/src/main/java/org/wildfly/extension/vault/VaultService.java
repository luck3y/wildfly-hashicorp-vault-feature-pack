/*
 * Copyright The WildFly Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.wildfly.extension.vault;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.jboss.logging.Logger;
import org.jboss.msc.Service;
import org.jboss.msc.service.ServiceName;
import org.jboss.msc.service.StartContext;
import org.jboss.msc.service.StartException;
import org.jboss.msc.service.StopContext;

import io.github.jopenlibs.vault.Vault;
import io.github.jopenlibs.vault.VaultConfig;
import io.github.jopenlibs.vault.VaultException;
import io.github.jopenlibs.vault.response.LogicalResponse;

public class VaultService implements Service {
    
    private static final Logger logger = Logger.getLogger(VaultService.class);
    
    public static ServiceName createServiceName(String connectionName) {
        return ServiceName.of("vault", "connection", connectionName);
    }
    
    private final String vaultUrl;
    private final String token;
    private final String namespace;
    private final boolean sslVerify;
    
    private Vault vault;
    private final Map<String, Object> secretCache = new ConcurrentHashMap<>();
    
    public VaultService(String vaultUrl, String token, String namespace, boolean sslVerify) {
        this.vaultUrl = vaultUrl;
        this.token = token;
        this.namespace = namespace;
        this.sslVerify = sslVerify;
    }
    
    @Override
    public void start(StartContext context) throws StartException {
        try {
            VaultConfig config = new VaultConfig()
                    .address(vaultUrl)
                    .token(token);
            config.getSslConfig().verify(sslVerify);
                    
            if (namespace != null && !namespace.isEmpty()) {
                config.nameSpace(namespace);
            }
            
            vault = Vault.create(config);
            
            // Test connection
            vault.auth().lookupSelf();
            
            logger.infof("Vault service started successfully, connected to: %s", vaultUrl);
            
        } catch (VaultException e) {
            throw new StartException("Failed to connect to Vault", e);
        }
    }
    
    @Override
    public void stop(StopContext context) {
        vault = null;
        secretCache.clear();
        logger.info("Vault service stopped");
    }
    
    public VaultService getValue() throws IllegalStateException, IllegalArgumentException {
        return this;
    }
    
    /**
     * Retrieve a secret from Vault
     */
    public String getSecret(String path, String key) throws VaultException {
        String cacheKey = path + ":" + key;
        
        // Check cache first
        Object cachedValue = secretCache.get(cacheKey);
        if (cachedValue != null) {
            return (String) cachedValue;
        }
        
        // Fetch from Vault
        LogicalResponse response = vault.logical().read(path);
        if (response.getRestResponse().getStatus() == 200) {
            Map<String, String> data = response.getData();
            String value = data.get(key);
            
            // Cache the value
            if (value != null) {
                secretCache.put(cacheKey, value);
            }
            
            return value;
        }
        
        throw new VaultException("Secret not found: " + path + "/" + key);
    }
    
    /**
     * Store a secret in Vault
     */
    public void putSecret(String path, Map<String, Object> secrets) throws VaultException {
        vault.logical().write(path, secrets);
        
        // Update cache
        for (Map.Entry<String, Object> entry : secrets.entrySet()) {
            String cacheKey = path + ":" + entry.getKey();
            secretCache.put(cacheKey, entry.getValue());
        }
    }
    
    /**
     * Clear cached secrets
     */
    public void clearCache() {
        secretCache.clear();
    }
    
    /**
     * Test vault connection
     */
    public boolean testConnection() {
        try {
            vault.auth().lookupSelf();
            return true;
        } catch (VaultException e) {
            logger.warnf("Vault connection test failed: %s", e.getMessage());
            return false;
        }
    }
}