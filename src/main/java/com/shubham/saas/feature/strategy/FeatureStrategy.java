package com.shubham.saas.feature.strategy;

import com.shubham.saas.feature.model.Feature;
import com.shubham.saas.feature.model.FeatureContext;

public interface FeatureStrategy {
    Feature getFeature();
    void execute(FeatureContext ctx);
}
