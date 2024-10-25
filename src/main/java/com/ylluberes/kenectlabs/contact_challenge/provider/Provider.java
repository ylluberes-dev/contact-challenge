package com.ylluberes.kenectlabs.contact_challenge.provider;


import java.util.Arrays;

public enum Provider {
    KENECT_LABS("KENECT_LABS");
    private final String sourceName;

    Provider(String sourceName) {
        this.sourceName = sourceName;
    }

    public static Provider fromValue(String providerName) {
        return Arrays.stream(Provider.values())
                .filter(provider -> provider.sourceName.equals(providerName))
                .findFirst()
                .orElse(null);
    }
}
