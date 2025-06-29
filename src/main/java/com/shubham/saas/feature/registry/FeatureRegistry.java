package com.shubham.saas.feature.registry;

import com.shubham.saas.feature.model.Feature;
import com.shubham.saas.feature.strategy.FeatureStrategy;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class FeatureRegistry {
    private final Map<Feature, FeatureStrategy> strategyMap;

    public FeatureRegistry(List<FeatureStrategy> strategies) {
        this.strategyMap = strategies.stream()
                .collect(Collectors.toMap(FeatureStrategy::getFeature, s -> s));
    }

    public Optional<FeatureStrategy> getStrategy(Feature feature){
        return Optional.ofNullable(strategyMap.get(feature));
    }
}