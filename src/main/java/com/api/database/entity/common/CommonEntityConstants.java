package com.api.database.entity.common;

public class CommonEntityConstants {


	private CommonEntityConstants() {
		throw new IllegalStateException("Utility class");
	}

	// Table name
	public static final String USER_ACCOUNT_ENTITY = "user_account";
	public static final String ROLE_GROUP_ENTITY = "role_group";
	public static final String FORGOT_PASSWORD_ENTITY = "forgot_password";

	// Column name
	public static final String ID = "id";
	public static final String CREATED_AT = "created_at";
	public static final String UPDATED_AT = "updated_at";
	public static final String CREATED_BY = "created_by";
	public static final String UPDATED_BY = "updated_by";
	public static final String DELETE_FLAG = "delete_flag";

	public static final String USERNAME = "username";
	public static final String PASSWORD = "password";
	public static final String ROLE_GROUP_ID = "role_group_id";
	public static final String EMAIL = "email";
	public static final String PHONE_NUMBER = "phone_number";
	public static final String START_DATE = "start_date";
	public static final String RESIGN_DATE = "resign_date";
	public static final String RSPASSWORD_TOKEN = "rspassword_token";
	public static final String RSPASSWORD_EXPTIME = "rspassword_exptime";


	public static final String USER_ACCOUNT_ID = "user_account_id";
	public static final String ORIGIN = "origin";
	public static final String TOKEN = "token";
	public static final String EXPIRED_DATE = "expired_date";
	public static final String STATUS = "status";
}
