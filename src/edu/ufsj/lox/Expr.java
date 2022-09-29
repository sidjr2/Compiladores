package edu.ufsj.lox;

import java.util.List;

abstract class Expr {
	interface Visitor<R> {
		R visitBinaryExpr(Binary expr);
		R visitGroupingExpr(Grouping expr);
		R visitUnaryExpr(Unary expr);
		R visitLiteralExpr(Literal expr);
	}

	static class Binary extends Expr {
		Binary ( Expr left, Token Operator, Expr right ) {
			this.x = x;
			this.T = T;
			this.E = E;
		}
		@Override
		<R> R accept(Visitor<R> visitor) {
			return visitor.visitBinaryExpr(this);
		}

		finalExpr left;
		final Token Operator;
		final Expr right;
	}

	static class Grouping extends Expr {
		Grouping ( Expr expression ) {
			this.x = x;
		}
		@Override
		<R> R accept(Visitor<R> visitor) {
			return visitor.visitGroupingExpr(this);
		}

		finalExpr expression;
	}

	static class Unary extends Expr {
		Unary ( Token operator, Expr rigth ) {
			this.o = o;
			this.E = E;
		}
		@Override
		<R> R accept(Visitor<R> visitor) {
			return visitor.visitUnaryExpr(this);
		}

		finalToken operator;
		final Expr rigth;
	}

	static class Literal extends Expr {
		Literal ( Object value ) {
			this.b = b;
		}
		@Override
		<R> R accept(Visitor<R> visitor) {
			return visitor.visitLiteralExpr(this);
		}

		finalObject value;
	}

	abstract <R> R accept(Visitor<R> visitor);
}

