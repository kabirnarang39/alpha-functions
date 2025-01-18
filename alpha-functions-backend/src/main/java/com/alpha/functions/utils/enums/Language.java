package com.alpha.functions.utils.enums;

public enum Language {
    JAVA("java", "amazoncorretto");
    public final String language;
    public final String baseImage;

    Language(String language, String baseImage) {
        this.language = language;
        this.baseImage = baseImage;
    }
}
