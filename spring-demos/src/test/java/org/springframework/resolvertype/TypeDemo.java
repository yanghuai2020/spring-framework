package org.springframework.resolvertype;

import org.springframework.core.ResolvableType;
import org.springframework.util.ClassUtils;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

public class TypeDemo<T, V> {

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

	public Map<T, V> f ;

	public List<List<T>> g;


	public static void main(String[] args) throws Exception {
		test3();
		//test1();
		//test2();
	}

	public static void demo1() {
		//如下得到字段级别的ResolvableType:
		ResolvableType resolvableType = ResolvableType.forField(ReflectionUtils.findField(GenericInjectTest.class, "cdService"));
		// 得到Service<C, D>第0个位置上的泛型实参类型，即C
		resolvableType.getGeneric(0).resolve();

		//比如List<List<String>> list是一种嵌套的泛型用例，我们可以通过如下操作获取嵌套的String类型:
		resolvableType = ResolvableType.forField(ReflectionUtils.findField(GenericInjectTest.class, "list"));
		resolvableType.getGeneric(0).getGeneric(0).resolve();
		// 更简单的写法
		resolvableType.getGeneric(0, 0).resolve();


		//比如Map<String, Map<String, Integer>> map我们想得到Integer，可以使用：
		resolvableType = ResolvableType.forField(ReflectionUtils.findField(GenericInjectTest.class, "map"));
		resolvableType.getGeneric(1).getGeneric(1).resolve();
		// 更简单的写法
		resolvableType.getGeneric(1, 1).resolve();

		//得到method方法的返回值HashMap<String, List<String>> method()，然后获得Map中List中的String泛型实参:
		resolvableType = ResolvableType.forMethodReturnType(ReflectionUtils.findMethod(GenericInjectTest.class, "method"));
		resolvableType.getGeneric(1, 0).resolve();

		//如下可以获得构造器第1个参数(Map<String, Map<String, Integer>>)中的Integer：
		resolvableType = ResolvableType.forConstructorParameter(ClassUtils.getConstructorIfAvailable(GenericInjectTest.class, List.class, Map.class), 1);
		resolvableType.getGeneric(1, 0).resolve();

		//对于List<String>[] array，如下获取List的泛型实参String：
		resolvableType = ResolvableType.forField(ReflectionUtils.findField(GenericInjectTest.class, "array"));
		resolvableType.isArray();    // 判断是否是数组
		resolvableType.getComponentType().getGeneric(0).resolve();


		//自定义泛型类型
		// 相当于创建一个List<String>类型
		resolvableType = ResolvableType.forClassWithGenerics(List.class, String.class);
		// 相当于创建一个List<String>[]类型数组
		resolvableType = ResolvableType.forArrayComponent(resolvableType);
		// 获得List<String>中String的类型信息
		resolvableType.getComponentType().getGeneric(0).resolve();


		//泛型等价比较
		ResolvableType resolvableType1 = ResolvableType.forClassWithGenerics(List.class, Integer.class);
		ResolvableType resolvableType2 = ResolvableType.forClassWithGenerics(List.class, String.class);
		resolvableType1.isAssignableFrom(resolvableType2);


		//


		//





	}




	public static void test3() {
		ParameterizedType parameterizedType = (ParameterizedType) ABService.class.getGenericInterfaces()[0];
		Type genericType = parameterizedType.getActualTypeArguments()[1];

		ResolvableType resolvableType = ResolvableType.forClass(ABService.class);
		Type t = resolvableType.as(Service.class).getGeneric(1).resolve();
	}


	public static void test1() throws NoSuchFieldException {
		Field field;
		Type type;

		field = TypeDemo.class.getDeclaredField("a");
		type = field.getGenericType();
		System.out.println("type of a: " + type.getClass());
		System.out.println("typename of a: " + type.getTypeName());
		if (type instanceof GenericArrayType) {
			GenericArrayType genericArrayType = (GenericArrayType) type;
			Type t = genericArrayType.getGenericComponentType();
			System.out.println("type of a's component: " + t.getClass());
		}
		System.out.println("");

		field = TypeDemo.class.getDeclaredField("b");
		type = field.getGenericType();
		System.out.println("type of b: " + type.getClass());
		System.out.println("typename of b: " + type.getTypeName());
		if (type instanceof GenericArrayType) {
			GenericArrayType genericArrayType = (GenericArrayType) type;
			Type t = genericArrayType.getGenericComponentType();
			System.out.println("type of b's component: " + t.getClass());
		}
		System.out.println("");

		field = TypeDemo.class.getDeclaredField("c");
		type = field.getGenericType();
		System.out.println("type of c: " + type.getClass());
		System.out.println("typename of c: " + type.getTypeName());
		if (type instanceof ParameterizedType) {
			ParameterizedType parameterizedType = (ParameterizedType) type;
			Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
			for (Type actualTypeArg : actualTypeArguments) {
				System.out.println("type of c's component: " + actualTypeArg.getClass());
			}
		}
		System.out.println("");

		field = TypeDemo.class.getDeclaredField("d");
		type = field.getGenericType();
		System.out.println("type of d: " + type.getClass());
		System.out.println("typename of d: " + type.getTypeName());
		System.out.println("");

		field = TypeDemo.class.getDeclaredField("e");
		type = field.getGenericType();
		System.out.println("type of e: " + type.getClass());
		System.out.println("typename of e: " + type.getTypeName());

	}



	public static void test2()  throws Exception {

		ResolvableType.forClass(ABService.class).as(Service.class).getGeneric(1).resolve();


		Field field = TypeDemo.class.getDeclaredField("c");
		Type type = field.getGenericType();

		System.out.println("============================================================");

		if (type instanceof ParameterizedType) {
			ParameterizedType parameterizedType = (ParameterizedType) type;
			Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
			for (Type actualTypeArg : actualTypeArguments)
				System.out.println(actualTypeArg.getClass());
		}

		field = TypeDemo.class.getDeclaredField("a");
		type = field.getGenericType();
		System.out.println(type.getClass() +"  "+ type.getTypeName());

		field = TypeDemo.class.getDeclaredField("b");
		type = field.getGenericType();
		System.out.println(type.getClass() +"  "+ type.getTypeName());

		field = TypeDemo.class.getDeclaredField("c");
		type = field.getGenericType();
		System.out.println(type.getClass() +"  "+ type.getTypeName());

		field = TypeDemo.class.getDeclaredField("d");
		type = field.getGenericType();
		System.out.println(type.getClass() +"  "+ type.getTypeName());


		field = TypeDemo.class.getDeclaredField("e");
		type = field.getGenericType();
		System.out.println(type.getClass() +"  "+ type.getTypeName());


		field = TypeDemo.class.getDeclaredField("f");
		type = field.getGenericType();
		System.out.println(type.getClass() +"  "+ type.getTypeName());


		field = TypeDemo.class.getDeclaredField("g");
		type = field.getGenericType();
		System.out.println(type.getClass() +"  "+ type.getTypeName());

		System.out.println("============================================================");

	}

}
