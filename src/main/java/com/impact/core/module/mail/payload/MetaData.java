package com.impact.core.module.mail.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Class representing metadata key-value pairs for email templates.
 * <p>
 * This class is used for injecting dynamic metadata into email templates. It stores a key-value
 * pair where the key represents the metadata field and the value holds the corresponding data
 * to be inserted into the email template.
 * </p>
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MetaData {

    /**
     * The key of the metadata.
     * <p>
     * This field represents the name or identifier of the metadata. It is used as a reference
     * to inject dynamic content into the email template.
     * </p>
     */
    private String key;

    /**
     * The value of the metadata.
     * <p>
     * This field holds the value associated with the metadata key. The value is used as the
     * actual content that will be injected into the email template.
     * </p>
     */
    private String value;
}
