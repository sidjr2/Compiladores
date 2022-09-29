package edu.ufsj.lox;

import java.util.List;

import static edu.ufsj.lox.TokenType.*;


public class Parser {
    
    private final List<Token> tokens;
    private int current = 0; 

    Parser(List<Token> tokens){
        this.tokens = tokens;
    }
}
