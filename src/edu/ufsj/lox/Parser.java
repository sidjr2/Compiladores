package edu.ufsj.lox;

import java.util.List;

import javax.swing.text.html.HTMLDocument.HTMLReader.PreAction;

import static edu.ufsj.lox.TokenType.*;


public class Parser {
    
    private final List<Token> tokens;
    private int current = 0; 

    Parser(List<Token> tokens){
        this.tokens = tokens;
    }

    private Expr expression(){
        return equality();
    }

    private Expr equality() {
        Expr expr = comparison();

        while (match(BANG, EQUAL_EQUAL)){
            Token operator = previous();
            Expr right = comparison();
            expr = new Expr.Binary(expr, operator, right);
        }

        return expr;
    }

    private boolean match(TokenType...types){
        for(TokenType type : types){
            if(check(type)){
                advance();
                return true;
            }
        }
        return false;
    }

    private boolean check(TokenType type) {
        if(isAtEnd()) {
            return false;
        }
        return peek().type == type;
    }

    private Token advance(){
        if(!isAtEnd()) {
            current++;
        }
        return previous();
    }

    private boolean isAtEnd() {
        return peek().type == EOF;
    }
    
    private Token peek() {
        return tokens.get(current);
    }
    
    private Token previous() {
        return tokens.get(current - 1);
    }
    
    private Expr comparison() {
        Expr expr = term();

        while(match(GREATER, GREATER_EQUAL, LESS, LESS_EQUAL)) {
            Token operator = previous();
            Expr right = term();
            expr = new Expr.Binary(expr, operator, right);
        }
        return expr;
    }

    /**
     * @return
     */
    private Expr term() {
        Expr expr = factor();

        while(match(MINUS, PLUS)){
            Token operator = previous();
            Expr right = factor();
            expr = new Expr.Binary(expr, operator, right);
        }

        return expr;
    }

    /**
     * 
     */
    private Expr factor() {
        
        Expr expr = Unary();

        while(match(SLASH, STAR)){
            Token operator = previous();
            Expr right = Unary();
            expr = new Expr.Binary(expr, operator, right);
        }
        return expr;

    }

    private Expr Unary() {
       if(match(BANG,MINUS)){
        Token operator = previous();
        Expr right = Unary();
        expr = new Expr.Unary(operator, right);
       }

       return primary();
    }

    private Expr primary() {
        if (match(FALSE)) return new Expr.Literal(false);
        if (match(TRUE)) return new Expr.Literal(true);
        if (match(NIL)) return new Expr.Literal(null);

        if (match(NUMBER, STRING)) {
        return new Expr.Literal(previous().literal);
        }

        if (match(LEFT_PAREN)) {
        Expr expr = expression();
        consume(RIGHT_PAREN, "Expect ')' after expression.");
        return new Expr.Grouping(expr);
    }
    }





}
