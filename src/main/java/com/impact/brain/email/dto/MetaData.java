package com.impact.brain.email.dto;

/**
 * @author Isaac F. B. C.
 * @since 9/5/2024 - 10:54 AM
 */
public class MetaData {

    private String key;

    private String value;

    public MetaData() {
        super();
    }

    public MetaData(String key, String value) {
        super();
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }


}
