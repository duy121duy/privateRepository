package com.example.chatbox.entity;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class Version {
    private int version;
    private Map<File, String> fileStringMap;

    public Version(int version, Map<File, String> fileStringMap) {
        this.version = version;
        this.fileStringMap = fileStringMap;
    }

    public Version() {
        fileStringMap = new HashMap<>();
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public Map<File, String> getFileStringMap() {
        return fileStringMap;
    }

    public void setFileStringMap(Map<File, String> fileStringMap) {
        this.fileStringMap = fileStringMap;
    }
}
