package org.example.domain.VO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Result<T> {

    private T data;//响应数据
    private String message;//响应消息
    private Status status;//响应状态
    //快速返回操作成功响应结果(带响应数据)
    public static <E> Result<E> success(E data, String message,Status status) {
        return new Result<>(data, "操作成功",status );
    }

    //快速返回操作成功响应结果
    public static Result success() {
        return new Result(0, "操作成功", null);
    }

    public static<E> Result<E> error( E data,String message,Status status) {
        return new Result(data, message, status);
    }
}
