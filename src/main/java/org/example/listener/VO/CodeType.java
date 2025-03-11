package org.example.listener.VO;

import lombok.Data;

import java.io.Serializable;

public enum CodeType implements Serializable {
    CPP(1),
    JAVA(2),
    PYTHON(3),
    GOLANG(4),
    C(5),
    RUST(6),
    JAVASCRIPT(7);

    CodeType(int i) {

    }
}
