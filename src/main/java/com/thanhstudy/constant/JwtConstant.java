package com.thanhstudy.constant;

public class JwtConstant {

	public static final long ACCESS_TOKEN_VALIDITY_SECONDS = 3600000;//1h
	//public static final long ACCESS_TOKEN_VALIDITY_SECONDS = 30000;

	public static final String SIGNING_KEY = "daf66e01593f61a15b857cf433aae03a005812b31234e149036bcc8dee755dbb";

	public static final String TOKEN_PREFIX = "Bearer ";

	public static final String HEADER_STRING = "Authorization";
}
