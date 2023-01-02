package com.elsprage.words.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Source {
    private String original;
    private String medium;
    private String small;
}
