package edu.java.curso;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class RestClient {
	  OkHttpClient client = new OkHttpClient();
	  public static final MediaType JSON
      = MediaType.parse("application/json; charset=utf-8");

	  public String get(String url) throws IOException {
	    Request request = new Request.Builder()
	        .url(url)
	        .header("Accept", "application/json")
	        .build();
	    
	    try (Response response = client.newCall(request).execute()) {
	      return response.body().string();
	    }
	  }
	  
	  public String post(String url, String json) throws IOException {
		  	RequestBody body = RequestBody.create(JSON, json);
		    Request request = new Request.Builder()
		        .url(url)
		        .post(body)
		        .build();
		    
		    try (Response response = client.newCall(request).execute()) {
		      return response.body().string();
		    }
		  }

	  public static void main(String[] args) throws IOException {
		  RestClient example = new RestClient();
		//String response = example.run("http://localhost:8080/EjemploRest_3/webresources/ejemplo/query?from=5&to=25&orderBy=b&orderBy=f");
		String response = example.get("http://localhost:8080/EjemploRest_3/webresources/ejemplo/personas");

		System.out.println(response);

		Type listType = new TypeToken<ArrayList<Persona>>(){}.getType();
			  
		Gson gson = new GsonBuilder().create();
		List<Persona> personas = gson.fromJson(response, listType);

		System.out.println("nombre: " + personas.get(0).getNombre());
		System.out.println("apellido: " + personas.get(0).getApellido()); 

		Persona persona = new Persona(29, "Roberto", "Carlos");
		gson = new GsonBuilder().create();
		  
		response = example.post("http://localhost:8080/EjemploRest_3/webresources/ejemplo/personas", gson.toJson(persona));
		System.out.println(response);
	  }
}
