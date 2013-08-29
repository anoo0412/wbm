package com.pipl.extract;

import java.io.IOException;
import java.net.URISyntaxException;

import com.pipl.api.data.containers.Record;
import com.pipl.api.data.fields.Job;
import com.pipl.api.search.SearchAPIError;
import com.pipl.api.search.SearchAPIRequest;
import com.pipl.api.search.SearchAPIResponse;

public class ExtractPiplData
{
	public static void main(String[] args) throws SearchAPIError, IOException, URISyntaxException
	{
		

		SearchAPIRequest request = new SearchAPIRequest.Builder()
		                                               .firstName("prasanna kumar")
		                                               .lastName("chittari")
		                                               .email("prasanna.chittari@gmail.com")
		                                               .country("IN")
		                                               .apiKey("pnhc4pjc257wq68jsrjpdp6y")
		                                               .build();
		SearchAPIResponse response = request.send();
		for (Record j : response.getRecords())
		System.out.println(j.getSource().getUrl()+" " + j.getQueryPersonMatch());
	}
}
