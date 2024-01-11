package website.hehe.utils;

import lombok.Getter;

public class ClassLevelUtils {

    @Getter
    public enum ClassLevel {
        MD("MD"),
        JH("JH"),
        SH("SH");

        private final String level;

        ClassLevel(String level) {
            this.level = level;
        }
    }

    public static ClassLevel parseClassLevel(int l) {
        if (l == 0) {
            return ClassLevel.MD;
        } else if (l == 1) {
            return ClassLevel.JH;
        } else if (l == 2) {
            return ClassLevel.SH;
        } else {
            throw new RuntimeException("Wrong class level");
        }
    }
}
