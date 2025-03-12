package org.example.listener.VO;

import java.io.Serializable;

public enum CodeType implements Serializable {
    CPP("cpp"),
    JAVA("java"),
    PYTHON("python"),
    GOLANG("go"),
    C("c"),
    RUST("rust"),
    JAVASCRIPT("javascript");

    CodeType(String type) {

    }
}
