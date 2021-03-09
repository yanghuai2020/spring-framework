package org.springframework.demo;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class ApplicationContextDemo {
	public static void main(String[] args) {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
		context.scan("org.springframework.context.annotation6");
		context.refresh();
	}
}
