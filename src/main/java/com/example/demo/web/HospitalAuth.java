package com.example.demo.web;

import java.io.IOException;

import org.json.JSONObject;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class HospitalAuth {

	public static String accesstoken(String code) throws IOException {
		OkHttpClient client = new OkHttpClient().newBuilder().build();
		MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
		RequestBody body = RequestBody.create(mediaType,
				"client_id=0oa2bhv1sIFEXlYMq5d6&client_secret=EDt-AygrzQtXJx5WVSduqHaOInLKadjGIXkRn8PK&grant_type=authorization_code&redirect_uri=http://localhost:10000/authorization&code="
						+ code);
		Request request = new Request.Builder().url("https://dev-3256198.okta.com/oauth2/default/v1/token")
				.method("POST", body).addHeader("Content-type", "application/x-www-form-urlencoded").addHeader("Cookie",
						"DT=DI02uedQfkmTBWqFltUx2eSvA; t=default; JSESSIONID=1BF919778A7AF9312DA5EAAF5A1BE195")
				.build();
		Response response = client.newCall(request).execute();
		String acctokenres = response.body().string();
		System.out.print(acctokenres+"\n");

		JSONObject acc_json = new JSONObject(acctokenres);
		String acctoken=acc_json.getString("access_token");
		System.out.print(acctoken+"\n");
		MediaType mediaType2 = MediaType.parse("application/x-www-form-urlencoded");
		RequestBody body2 = RequestBody.create(mediaType2, "token=" + acctoken + "&token_type_hint=access_token");
		Request request2 = new Request.Builder().url("https://dev-3256198.okta.com/oauth2/default/v1/introspect")
				.method("POST", body2)
				.addHeader("Authorization",
						"Basic MG9hMmJodjFzSUZFWGxZTXE1ZDY6RUR0LUF5Z3J6UXRYSng1V1ZTZHVxSGFPSW5MS2FkakdJWGtSbjhQSw==")
				.addHeader("Content-Type", "application/x-www-form-urlencoded").addHeader("Cookie",
						"DT=DI02uedQfkmTBWqFltUx2eSvA; t=default; JSESSIONID=B5AE51A3E988703773329D11D71984A1")
				.build();
		Response response2 = client.newCall(request2).execute();
		String finalres = response2.body().string();
		System.out.println(finalres);
		return finalres;
	}
}
