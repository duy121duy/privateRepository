package com.example.chatbox.entity;

import java.util.Map;

public class VersionForm {
    private int version;
    private Map<String, String> stringStringMap;

    public VersionForm(int version, Map<String, String> stringStringMap) {
        this.version = version;
        this.stringStringMap = stringStringMap;
    }

    public VersionForm() {
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public Map<String, String> getStringStringMap() {
        return stringStringMap;
    }

    public void setStringStringMap(Map<String, String> stringStringMap) {
        this.stringStringMap = stringStringMap;
    }
}
