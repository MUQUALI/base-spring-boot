package com.api.common;

import lombok.experimental.UtilityClass;

@UtilityClass
public class OpenAPIConstants {
	
	public static class Dashboard {
		private Dashboard() {
			throw new IllegalStateException("Utility class");
		}

		public static final String TAG_NAME = "Dashboard";
		public static final String DESCRIPTION = "APIs for dashboard screen";
	}
}
