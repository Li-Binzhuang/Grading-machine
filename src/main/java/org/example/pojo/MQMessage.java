package org.example.pojo;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

@Data
@ToString
public class MQMessage implements Serializable {
    public Integer id;
    public String code;
    public Integer problemId;
    public String codeType;
}
