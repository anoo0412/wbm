package com.trendmenot.extractdata;

import facebook4j.Facebook;
import facebook4j.FacebookException;
import facebook4j.FacebookFactory;
import facebook4j.Group;
import facebook4j.ResponseList;
import facebook4j.User;
import facebook4j.auth.AccessToken;
import facebook4j.internal.org.json.JSONArray;
import facebook4j.internal.org.json.JSONException;
import facebook4j.internal.org.json.JSONObject;

public class ExtractFacebookData {

	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		extractFB();
		
	}

	public  static void extractFB() {
		Facebook facebook = new FacebookFactory().getInstance();
		// Single FQL
		
		facebook.setOAuthAppId("479475402117555", "7f55764ef47bec1fb9352ac502a4c388");
		facebook.setOAuthAccessToken(new AccessToken("AAACEdEose0cBAAFB2cGiGDZBKOTiPNpaSdYJAgfIxNk3nAw0F3puEX84vCiXPKvnqwpjUZCikBGLdZB9tfFgofZC4gJuadS8gglWI1ZAtGQZDZD",null));
		
		
		
		//facebook.setOAuthPermissions("offline_access,publish_Stream,user_photos,publish_checkins,photo_upload,manage_pages,publish_actions,friends_actions");
		try {
			ResponseList<Group> groups = facebook.searchGroups("infosys");
			for(Group group: groups)
				{
					//System.out.println(group.getName());
				}
		
			ResponseList<User> users = facebook.searchUsers("Paulo Coelho");
			//System.out.println(facebook.search("Anusha"));
			for(User user: users)
				{
					System.out.println(user.getName());
					System.out.println(user.getBio());
					System.out.println(user.getBirthday());
				}
			
			String query = "SELECT uid2 FROM friend WHERE uid1=me()";
			JSONArray jsonArray = facebook.executeFQL(query);
			System.out.println(jsonArray.length());
			/**for (int i = 0; i < jsonArray.length(); i++) {
			    JSONObject jsonObject = jsonArray.getJSONObject(i);
			    System.out.println(jsonObject.get("uid2"));
			    query = "SELECT first_name,last_name FROM user WHERE uid="+jsonObject.get("uid2");
			    JSONArray jsonArray1 = facebook.executeFQL(query);
			    
			    for (int j = 0; j < jsonArray1.length(); j++) {
			    	JSONObject jsonObject1 = jsonArray1.getJSONObject(j);
			    	System.out.println(jsonObject1.get("first_name"));
			    	System.out.println(jsonObject1.get("last_name"));
			    }
			}**/
			
			query = "SELECT about_me,pic, work from user where uid in (SELECT id FROM profile WHERE name='sumatheja dasararaju')";
			jsonArray = facebook.executeFQL(query);
			System.out.println(jsonArray.length());
			for (int i = 0; i < jsonArray.length(); i++) {
			    JSONObject jsonObject = jsonArray.getJSONObject(i);
			    System.out.println(jsonObject.get("about_me"));
			    System.out.println(jsonObject.get("pic"));
			    System.out.println(jsonObject.get("work"));
			}
			
		} catch (FacebookException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
}
