package org.example.pojo;

import lombok.Data;
import lombok.ToString;
import org.example.VO.CodeType;

import java.io.Serializable;
/**
 * @author laoli
 * @Description: 消息队列消息体
 */

@Data
@ToString
public class MQMessage implements Serializable {
    public Integer userId;//用户id
    public String code;//用户提交的代码
    public Integer problemId;//题目id
    public CodeType codeType;//代码类型
}
