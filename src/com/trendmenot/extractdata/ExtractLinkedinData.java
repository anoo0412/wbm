/*

Copyright 2011 LinkedIn Corporation

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.

*/
package com.trendmenot.extractdata;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.scribe.builder.ServiceBuilder;
import org.scribe.builder.api.LinkedInApi;
import org.scribe.model.OAuthRequest;
import org.scribe.model.Response;
import org.scribe.model.Token;
import org.scribe.model.Verb;
import org.scribe.oauth.OAuthService;

import com.linkedin.sample.AuthHandler;

public class ExtractLinkedinData {

    private static String API_KEY = "riaeitpjq92k";
    private static String API_SECRET = "WkYRYhGdqZo9FbCK";

    public static void main(String[] args) {

        extractData("www.linkedin.com%2Fin%2Fsumatheja");
    }

	public static String extractData(String publicURL) {
		Token accessToken = null;

        OAuthService service = new ServiceBuilder()
                                .provider(LinkedInApi.class)
                                .scope("r_fullprofile")
                                .apiKey(API_KEY)
                                .apiSecret(API_SECRET)
                                .build();

        try{
            File file = new File("../standalone/deployments/service.dat");

            if(file.exists()){
                //if the file exists we assume it has the AuthHandler in it - which in turn contains the Access Token
                ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(file));
                AuthHandler authHandler = (AuthHandler) inputStream.readObject();
                accessToken = authHandler.getAccessToken();
            } else {
                System.out.println("There is no stored Access token we need to make one");
                //In the constructor the AuthHandler goes through the chain of calls to create an Access Token
                AuthHandler authHandler = new AuthHandler(service);
                ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream("service.dat"));
                outputStream.writeObject( authHandler);
                outputStream.close();
                accessToken = authHandler.getAccessToken();
            }

        }catch (Exception e){
            System.out.println("Threw an exception when serializing: " + e.getClass() + " :: " + e.getMessage());
        }

        /*
         * We are all done getting access - time to get busy getting data
         *************************************/

        /**************************
         *
         * Querying the LinkedIn API
         *
         **************************/

        System.out.println();
        String url = "https://api.linkedin.com/v1/people/url=http%3A%2F%2F"+publicURL.replaceAll("/", "%2F")+":(id,first-name,last-name,headline,picture-url,summary,date-of-birth,main-address,industry,specialties,interests,phone-numbers,im-accounts,public-profile-url,honors,associations,member-url-resources,positions,educations,languages,skills,location:(country:(code)))";
        String current;
		try {
			current = new java.io.File( "." ).getCanonicalPath();
			System.out.println("Current dir:"+current);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        OAuthRequest request = new OAuthRequest(Verb.GET, url);
        service.signRequest(accessToken, request);
        Response response = request.send();
        System.out.println(response.getBody());
        return response.getBody();
	}
	
}
