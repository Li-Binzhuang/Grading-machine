package org.example.domain.run;
public class PythonRunCode extends DefaultRunCode{
    @Override
    public String prepareLanguageRunArg() {
        return "python3";
    }
}
