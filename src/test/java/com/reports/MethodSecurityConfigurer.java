package com.reports;

import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;

@Profile("test")
// @Configuration
// @EnableGlobalMethodSecurity(prePostEnabled = true)
public class MethodSecurityConfigurer
        extends GlobalMethodSecurityConfiguration {
}
