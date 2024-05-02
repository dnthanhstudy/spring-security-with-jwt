package com.thanhstudy.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestApi {

	@GetMapping("/api/test")
	public void test() {
		System.out.println("Success");
	}
}
