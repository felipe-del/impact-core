package com.impact.core.module.mail.payload;

import lombok.*;

import java.util.List;

/**
 * Class representing a composed email with template and metadata.
 * <p>
 * This class is used for creating a composed email request, which includes the recipient, subject,
 * email template, metadata, and images. The email is composed based on a predefined template
 * with optional metadata and images.
 * </p>
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ComposedMail {

    /**
     * The recipient's email address.
     * <p>
     * This field specifies the recipient of the composed email.
     * </p>
     */
    private String to;

    /**
     * The subject of the email.
     * <p>
     * This field specifies the subject line for the composed email.
     * </p>
     */
    private String subject;

    /**
     * The template name for the email.
     * <p>
     * This field specifies the email template to be used. The template will be populated with
     * metadata and possibly images.
     * </p>
     */
    private String template;

    /**
     * Metadata for the email template.
     * <p>
     * This list contains key-value pairs of metadata that will be injected into the template.
     * </p>
     */
    private List<MetaData> metaData;

    /**
     * List of image names to be included in the email.
     * <p>
     * This list contains the names of images that may be referenced within the email template.
     * </p>
     */
    private List<String> imageNames;
}
