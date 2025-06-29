package com.shubham.saas.feature.strategy;

import com.shubham.saas.feature.model.Feature;
import com.shubham.saas.feature.model.FeatureContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class AuditingFeatureStrategy implements FeatureStrategy{
    @Override
    public Feature getFeature() {
        return Feature.AUDITING;
    }

    @Override
    public void execute(FeatureContext ctx) {
        log.info("Auditing Feature: " + ctx.get("action", String.class));
    }
}
