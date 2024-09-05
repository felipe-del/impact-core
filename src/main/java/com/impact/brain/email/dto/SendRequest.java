package com.impact.brain.email.dto;

import java.util.List;

/**
 * @author Isaac F. B. C.
 * @since 9/5/2024 - 10:54 AM
 */
public class SendRequest {

    private String to;

    private String subject;

    private String template;

    private List<MetaData> metaData;

    public SendRequest() {
        super();
    }

    public SendRequest(String to, String subject, String template, List<MetaData> metaData) {
        super();
        this.to = to;
        this.subject = subject;
        this.template = template;
        this.metaData = metaData;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    public List<MetaData> getMetaData() {
        return metaData;
    }

    public void setMetaData(List<MetaData> metaData) {
        this.metaData = metaData;
    }
}
