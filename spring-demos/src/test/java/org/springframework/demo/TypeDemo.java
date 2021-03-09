package org.springframework.demo;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

public class TypeDemo<T> {

	// GenericArrayType：组件类型为 "类型变量的数组"
	public T[] a;
	// GenericArrayType：组件类型为 "参数化类型的数组"
	public List<?>[] b;

	// ParameterizedType："参数化类型"
	// List<? extends Object>携带的"? extends Object"
	// 即通配符表达式，也就是WildcardType
	public List<? extends Object> c;

	// Class：普通类型
	public List d;

	// 类型变量
	public T e;

	public static void main(String[] args)  throws Exception {

		Field field = TypeDemo.class.getDeclaredField("c");
		Type type = field.getGenericType();

		if (type instanceof ParameterizedType) {
			ParameterizedType parameterizedType = (ParameterizedType) type;
			Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
			for (Type actualTypeArg : actualTypeArguments)
				System.out.println(actualTypeArg.getClass());
		}

		field = TypeDemo.class.getDeclaredField("a");
		type = field.getGenericType();

		field = TypeDemo.class.getDeclaredField("b");
		type = field.getGenericType();

		field = TypeDemo.class.getDeclaredField("c");
		type = field.getGenericType();

		field = TypeDemo.class.getDeclaredField("d");
		type = field.getGenericType();


		field = TypeDemo.class.getDeclaredField("e");
		type = field.getGenericType();

	}

}
