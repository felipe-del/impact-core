package com.impact.core.module.mail.payload;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ComposedMail {
    private String to;

    private String subject;

    private String template;

    private List<MetaData> metaData;

    private List<String> imageNames;
}
