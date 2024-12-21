package org.example.pojo;
import lombok.Data;

@Data
public class Problems {
    class Case {
        String input;
        String output;
    }
    Long id;
    Case[] testCases;
}
