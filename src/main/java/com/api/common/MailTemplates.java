package com.api.common;

import lombok.experimental.UtilityClass;

@UtilityClass
public class MailTemplates {

	public static final String CHANGE_PWD = """
			<p style="text-align: center; font-size: 18px; font-weight: 600;">ANONYMOUS COMPANY</p>
			    <p style="text-align: center; margin-top: 5px; margin-bottom: 15px;">Reset password</p>
			    <hr style="border: 2px solid red;">
			    <p >Hi {user},</p>
			    <br>
			    <p>Forgot your password ?</p>
			    <p>We received a request to reset password for your account.</p>
			    <br>
			    <p>To reset your password, click on the button below: </p>
			   \s
			    <a href='{linkChangePwd}'>
			        <button style="background-color: #007BFF; border-radius: 5px; color: white; padding: 15px; border: none; cursor: pointer;">Reset password</button>
			    </a>
			    <p>Or copy and paste the URL into your browser:</p>
			    {linkChangePwd}
			    <p style='color: red;'>note: The link will expire 5 minutes after you receive this email</p>
			    <p style='color: red;'>If you do not initiate this request, please do not click on button or access link</p>
			    <br>
			    <hr>
			""";

	public static final String NEW_USER = """
			<p style="text-align: center; font-size: 18px; font-weight: 600;">WAREHOUSE</p>
			    <p style="text-align: center; margin-top: 5px; margin-bottom: 15px;">Account info</p>
			    <hr style="border: 2px solid red;">
			    <p >Hi {email},</p>
			    <br>
			    <p>We have sent to you information of login account</p>
			    <p>email: {email}</p>
			    <p>password: {password} </p>
			   \s
			    <p>Please check your info by login to system: </p> {BASE_URL}\s
			    <br>
			    <hr>
			""";

	public static final String FOOTER = """
			<br>
			<p>This email sent by:</p>
			<p><b>WAREHOUSE SYSTEM</b></p>
			<p>HA NOI, VIET NAM</p>
			""";
}
