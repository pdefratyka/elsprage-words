package com.elsprage.words.external.api;

import lombok.Data;


@Data
public abstract class WebClientProperties {
    private String url;
    private String apiName;
}
