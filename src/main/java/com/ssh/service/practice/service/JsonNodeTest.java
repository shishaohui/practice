package com.ssh.service.practice.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;

public class JsonNodeTest {

	public static void main(String[] args) {

		Integer i = new Double("1.0").intValue();
		System.out.println("i=="+i);
		String json = "{\"score\":\"89\",\"status\":\"PASSED\",\"name\":\"王二\"}";
		ObjectMapper mapper = new ObjectMapper();
		//JSON ----> JsonNode  
		try {
			JsonNode rootNode = mapper.readTree(json);
			System.out.println("rootNode"+rootNode.get("score").toString());
			System.out.println("rootNode.Score"+rootNode.path("score").toString());
			System.out.println("rootNode.Score"+rootNode.get("score").textValue());
		} catch (IOException e) {
			e.printStackTrace();
		}



	}
}
