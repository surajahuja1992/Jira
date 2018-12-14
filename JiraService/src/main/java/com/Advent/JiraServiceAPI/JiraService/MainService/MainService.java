package com.Advent.JiraServiceAPI.JiraService.MainService;

import java.io.File;

import org.apache.tomcat.util.codec.binary.Base64;
import org.json.simple.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
@Service
public class MainService {
	public void createIssue() {
    	RestTemplate restTemplate=new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		String requestURL = "https://host/rest/api/latest/issue/";
		String plainCreds = "USERNAME:PASSWORD";
		byte[] plainCredsBytes = plainCreds.getBytes();
		byte[] base64CredsBytes = Base64.encodeBase64(plainCredsBytes);
		String base64Creds = new String(base64CredsBytes);

		headers.add("Authorization", "Basic " + base64Creds);
		System.setProperty("jsse.enableSNIExtension", "false");
		headers.setContentType(MediaType.APPLICATION_JSON);
		JSONObject jsonObject=new JSONObject();
		JSONObject child=new JSONObject();
		child.put("summary", "Test Issue");
		child.put( "description", "Test along with attachment");
		JSONObject projectChild=new JSONObject();
		projectChild.put("key", "PROJECT KEY");
		child.put("project",projectChild);
		JSONObject issuetypeChild=new JSONObject();
		issuetypeChild.put( "name", "ISSUE NAME");
		child.put( "issuetype",issuetypeChild);
		jsonObject.put("fields",child );
		System.out.println(jsonObject.toString());
		HttpEntity<String> entity = new HttpEntity<String>("", headers);
		jsonObject.put("headers", headers);
		 HttpEntity request = new HttpEntity(jsonObject, headers);
		ResponseEntity<String> response = restTemplate.postForEntity( requestURL, request, String.class );
    	System.out.println(request);
    	System.out.println(response.getBody());
    }
	
	public void createComment() {
    	RestTemplate restTemplate=new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		String requestURL = "https://host/rest/api/latest/issue/ISSUE ID/comment";
		String plainCreds = "USERNAME:PASSWORD";
		byte[] plainCredsBytes = plainCreds.getBytes();
		byte[] base64CredsBytes = Base64.encodeBase64(plainCredsBytes);
		String base64Creds = new String(base64CredsBytes);

		headers.add("Authorization", "Basic " + base64Creds);
		System.setProperty("jsse.enableSNIExtension", "false");
		headers.setContentType(MediaType.APPLICATION_JSON);
		JSONObject jsonObject=new JSONObject();
		System.out.println(jsonObject.toString());
		HttpEntity<String> entity = new HttpEntity<String>("", headers);
		jsonObject.put("body", "Comment Via Service");
		 HttpEntity request = new HttpEntity(jsonObject, headers);
		ResponseEntity<String> response = restTemplate.postForEntity( requestURL, request, String.class );
    	System.out.println(request);    	
    }
	
	public void addAttachment(File file) throws Exception{
		String userCredentials = "USERNAME:PASSWORD";
		String basicAuth = "Basic " + new String(java.util.Base64.getEncoder().encode(userCredentials.getBytes()));

	    final String address = "https://HOST/rest/api/2/issue/ISSUE ID/attachments";
	    final OkHttpClient okHttpClient = new OkHttpClient();
	    final RequestBody formBody = new MultipartBody.Builder().setType(MultipartBody.FORM).addFormDataPart("file", file.getName(),RequestBody.create(okhttp3.MediaType.parse("text/plain"), file)).build();
	    final Request request = new Request.Builder().url(address).post(formBody).addHeader("X-Atlassian-Token", "no-check").addHeader("Authorization", basicAuth).build();
	    final Response response = okHttpClient.newCall(request).execute();
	    System.out.println(response.code() + " => " + response.body().string());
	}
}