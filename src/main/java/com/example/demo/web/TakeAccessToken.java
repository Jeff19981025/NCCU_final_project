package com.example.demo.web;

import java.io.IOException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import org.json.*;

public class TakeAccessToken {
	private static OkHttpClient getUnsafeOkHttpClient() {
		try {
			// Create a trust manager that does not validate certificate chains
			final TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
				@Override
				public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType)
						throws CertificateException {
				}

				@Override
				public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType)
						throws CertificateException {
				}

				@Override
				public java.security.cert.X509Certificate[] getAcceptedIssuers() {
					return new java.security.cert.X509Certificate[] {};
				}
			} };

			KeyStore keyStore = readKeyStore(); // your method to obtain KeyStore
			KeyManagerFactory keyManagerFactory = KeyManagerFactory
					.getInstance(KeyManagerFactory.getDefaultAlgorithm());
			keyManagerFactory.init(keyStore, "changeit".toCharArray());
			// Install the all-trusting trust manager
			final SSLContext sslContext = SSLContext.getInstance("SSL");
			sslContext.init(keyManagerFactory.getKeyManagers(), trustAllCerts, new java.security.SecureRandom());
			// Create an ssl socket factory with our all-trusting manager
			final SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();

			OkHttpClient.Builder builder = new OkHttpClient.Builder();
			builder.sslSocketFactory(sslSocketFactory, (X509TrustManager) trustAllCerts[0]);
			builder.hostnameVerifier(new HostnameVerifier() {
				@Override
				public boolean verify(String hostname, SSLSession session) {
					return true;
				}
			});

			OkHttpClient okHttpClient = builder.build();
			return okHttpClient;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public String takeaccesstoken(String authcode) throws IOException {
		boolean test = false;
		System.out.println("hello, start to take access token!!!\n");

		OkHttpClient client = getUnsafeOkHttpClient();
		if (test)
			System.out.println("1\n");
		MediaType mediaType1 = MediaType.parse("application/x-www-form-urlencoded");
		RequestBody acc_token_body = RequestBody.create(mediaType1, "code=" + authcode
				+ "&redirect_uri=https://www.google.com&client_id=NCCUclient5&grant_type=authorization_code");
		Request acc_token_request = new Request.Builder()
				.url("https://112.121.74.27:48082/dcv.tina/AuthCodeDemo/oauth/token").method("POST", acc_token_body)
				.addHeader("Authorization", "Basic TkNDVWNsaWVudDU6VGtORFZXTnNhV1Z1ZERVPQ==")
				.addHeader("Content-Type", "application/x-www-form-urlencoded").build();
		if (test)
			System.out.println("3\n");
		Response acc_token_response = client.newCall(acc_token_request).execute();
		if (test)
			System.out.println("4\n");
		String acc_token_string = acc_token_response.body().string();
		if (test)
			System.out.println(acc_token_string);
		JSONObject acc_json = new JSONObject(acc_token_string);
		String acc = "Bearer" + acc_json.getString("access_token");
		String ref = acc_json.getString("refresh_token");
		System.out.println("acc: " + acc + "ref: " + ref);

		Request sign_block_request = new Request.Builder()
				.url("https://112.121.74.27:48082/dcv.tina/tsmpu/getSignBlock").method("GET", null)
				.addHeader("Authorization", acc).build();
		Response sign_block_response = client.newCall(sign_block_request).execute();
		String sign_block_str = sign_block_response.body().string();
		System.out.println(sign_block_str);
		JSONObject sign_block_json = new JSONObject(sign_block_str);
		String sign_block = sign_block_json.getJSONObject("Res_getSignBlock").getString("signBlock");
		System.out.println(sign_block);

		MediaType mediaType2 = MediaType.parse("application/json");
		RequestBody req_body = RequestBody.create(mediaType2,
				"{\"accountNo\":\"1\",\"startDate\":\"1\",\"endDate\":\"1\",\"limitsInPage\":\"1\",\"pageToken\":\"1\" }");
		Request final_request = new Request.Builder()
				.url("https://112.121.74.27:48082/dcv.tina/tsmpc/demandDeposit/transactionDetails")
				.method("POST", req_body).addHeader("Authorization", acc).addHeader("Content-Type", "application/json")
				.addHeader("signCode", sign_block).build();

		Response final_response = client.newCall(final_request).execute();

		return final_response.body().string();

	}

	public static KeyStore readKeyStore()
			throws KeyStoreException, NoSuchAlgorithmException, CertificateException, IOException {
		KeyStore ks = KeyStore.getInstance(KeyStore.getDefaultType());

		// get user password and file input stream
		char[] password = { 'c', 'h', 'a', 'n', 'g', 'e', 'i', 't' };

		java.io.FileInputStream fis = null;
		try {
			fis = new java.io.FileInputStream(
					"/Users/caisonglin/Documents/workspace-spring-tool-suite-4-4.8.0.RELEASE/SpringBootMvc/src/main/resources/static/client7.chain.p12");
			ks.load(fis, password);
		} finally {
			if (fis != null) {
				fis.close();
			}
		}
		return ks;
	}
}
