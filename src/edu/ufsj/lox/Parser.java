package edu.ufsj.lox;

import java.util.List;
import static edu.ufsj.lox.TokenType.*;


public class Parser {
	private static class ParseError extends RuntimeException{}
	
	private final List<Token> tokens;
	private int current = 0;
	
	Parser(List<Token> tokens) {
		this.tokens = tokens;
	}
	
	private Expr expression() {
		return equality();
	}
	
	private Expr equality() {
		Expr expr = comparison();
		
		while(match(BANG_EQUAL, EQUAL_EQUAL)) {
			Token operator = previous();
			Expr right = comparison();
			expr = new Expr.Binary(expr, operator, right);
		}
		
		return expr;
	}
	
	private boolean match(TokenType... types) {
		for(TokenType type : types) {
			if(check(type)) {
				advance();
				return true;
			}
		}
		return false;
	}
	
	private boolean check(TokenType type) {
		if(isAtEnd()) return false;
		return peek().type == type;
	}
	
	private Token advance() {
		if(!isAtEnd()) current++;
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
	
	private Expr term() {
		Expr expr = factor();
		
		while(match(MINUS, PLUS)) {
			Token operator = previous();
			Expr right = factor();
			expr = new Expr.Binary(expr, operator, right);
		}
		return expr;
	}
	
	private Expr factor() {
		Expr expr = unary();
		
		while(match(SLASH, STAR)) {
			Token operator = previous();
			Expr right = unary();
			expr = new Expr.Binary(expr, operator, right);
		}
		
		return expr;
	}
	
	private Expr unary() {
		if(match(BANG, MINUS)) {
			Token operator = previous();
			Expr right = unary();
			return new Expr.Binary(null, operator, right);
		}
		
		return primary();
	}
	
	private Expr Ternary() {
		Expr expr = or();
		
		if(match(QUESTION)) {
			Token leftOperator = previous();
			Expr meio = expression();
			Token rightOperator = consume(COLON, "Expected ':' in ternary operator.");
			Expr right = expression();
			expr = new Expr.Ternary(expr, leftOperator, meio, rightOperator, right);
		}
		
		return expr;
	}
	
	private Expr or() {
		Expr expr = expression();
		
		while(match(TokenType.OR)) {
			Token operator = previous();
			Expr right = and();
			expr = new Expr.Logical(expr, operator, right);
		}
		
		return expr;
	}
	
	private Expr and() {
		Expr expr = equality();
		
		while(match(TokenType.AND)) {
			Token operator = previous();
			Expr right = equality();
			expr = new Expr.Logical(expr, operator, right);
		}
		
		return expr;
	}
	
	private Expr primary() {
		if(match(FALSE)) return new Expr.Literal(false);
		if(match(TRUE)) return new Expr.Literal(true);
		if(match(NIL)) return new Expr.Literal(null);
		
		if(match(NUMBER, STRING)) {
			return new Expr.Literal(previous().literal);
		}
		
		if(match(LEFT_PAREN)) {
			Expr expr = expression();
			consume(RIGHT_PAREN, "Expect ')' after expression.");
			return new Expr.Grouping(expr);
		}
		
		throw error(peek(), "Expect expression");
	}
	
	private Token consume(TokenType token, String message) {
		if(check(token))
			return advance();
		
		throw error(peek(), message);
	}
	
	private ParseError error(Token token, String message) {
		Lox.error(token, message);
		return new ParseError();
	}
	
	private void synchronize() {
		advance();
		
		while(!isAtEnd()) {
			if(previous().type == TokenType.SEMICOLON) return;
			
			switch(peek().type) {
			case CLASS:
			case FUN:
			case VAR:
			case FOR:
			case IF:
			case WHILE:
			case PRINT:
			case RETURN:
			return;
				default:
					break;
			}
			
			advance();
		}
	}
	
	Expr parse() {
		try {
			return expression();
		} catch (ParseError error) {
			return null;
		}
	}
	

}
