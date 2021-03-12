package org.springframework.resolvertype;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GenericInjectTest<A,B> {
	private Service<A, B> abService;
	private Service<A, B> cdService;
	private List<List<String>> list;
	private Map<String, Map<String, Integer>> map;
	private List<String>[] array;

	private HashMap<String, List<String>> method() {
		return null;
	}

	public GenericInjectTest(List<List<String>> list, Map<String, Map<String, Integer>> map) {

	}
}