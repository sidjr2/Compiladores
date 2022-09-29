package edu.ufsj.lox;

enum TokenType {

	//Token de unico caractere.
    LEFT_PAREN, RIGHT_PAREN, LEFT_BRACE, RIGHT_BRACE,
	COMMA, DOT, MINUS, PLUS, SEMICOLON, SLASH, STAR,
	
	//Token de Um ou Dois caractere.
	BANG, BANG_EQUAL,
	EQUAL, EQUAL_EQUAL,
	GREATER, GREATER_EQUAL, LESS, LESS_EQUAL,
	
	//Literais.
	IDENTIFIER, STRING, NUMBER,
	
	//Reservado
	AND, CLASS, ELSE, FALSE, FUN, FOR, IF, NIL,
	OR, PRINT, RETURN, SUPER, THIS, TRUE,
	VAR, WHILE,
    
    //fim do arquivo
    EOF
}