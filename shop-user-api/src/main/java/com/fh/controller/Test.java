package com.fh.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author xiezhuang
 * @ClassName Test
 * @date 2020/7/10 22:52
 */
public class Test {
    public static void main(String[] args) throws InterruptedException {
    	Thread.sleep(3000L);
    	}
    public void test1(){
    	System.out.println(1111);
	}
	public String test2(){

    	return "1";
	}
	public Map<String,Object> teste3(){
    	Map<String,Object> map = new HashMap<>();
    	map.put("aaa","111");
    	return map;
	}
	public List<Map<String,Object>> test4(){
    	List<Map<String,Object>> list = new ArrayList<>();
    	return list;
	}
}
