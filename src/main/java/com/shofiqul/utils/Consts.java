package com.shofiqul.utils;

public class Consts {
	public static final String ROLE_SUPER_ADMIN = "ROLE_SUPER_ADMIN";
	public static final String ROLE_ADMIN = "ROLE_ADMIN";
	public static final String ROLE_INSTRUCTOR = "ROLE_INSTRUCTOR";
	public static final String ROLE_USER = "ROLE_USER";
	
	public static final String COMMA_WITH_OR_WITHOUT_SPACE = "\s*,\s*";
	
	public static final String[] WHITELIST_APIS = {"/v1/user/authenticate", "/v1/user/register", "/v1/user/register/instructor"};
	public static final String[] OPEN_COURSE_APIS = {"/v1/course/all", "/v1/course/instructor/**"};
}
