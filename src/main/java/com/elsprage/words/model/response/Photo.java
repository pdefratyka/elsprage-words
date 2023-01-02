package com.elsprage.words.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Photo {
    private Long id;
    private int width;
    private int height;
    private String url;
    private String photographer;
    private Source src;
}
