package com.impetus.kundera.rest.client;

import java.net.URI;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;

/**
 * Copyright 2012 Impetus Infotech.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

/**
 * <Prove description of functionality provided by this Type> 
 * @author amresh.singh
 */
public class JSONRESTClient implements RESTClient
{
    
    private WebResource webResource = null;

    @Override
    public void initialize(String urlString)
    {
        ClientConfig config = new DefaultClientConfig();
        Client client = Client.create(config);

        URI uri = UriBuilder.fromUri(urlString).build();
        webResource = client.resource(uri);
    } 

    @Override
    public String getApplicationToken()
    {
        System.out.println("Getting Application Token...");
        WebResource.Builder atBuilder = webResource.path("rest").path("kundera/api/application/pu/twissandra")
                .accept(MediaType.TEXT_PLAIN);
        String atResponse = atBuilder.get(ClientResponse.class).toString();
        String applicationToken = atBuilder.get(String.class);
        System.out.println("Response: " + atResponse);
        System.out.println("Application Token:" + applicationToken);
        return applicationToken;
    }

    @Override
    public void closeApplication(String applicationToken)
    {
        System.out.println("Closing Application for Token:" + applicationToken);
        WebResource.Builder atBuilder = webResource.path("rest").path("kundera/api/application/" + applicationToken)
                .accept(MediaType.TEXT_PLAIN);
        String response = atBuilder.delete(String.class);
        System.out.println("Application Closure Response: " + response);
    }

    @Override
    public String getSessionToken(String applicationToken)
    {
        System.out.println("Getting Session Token...");
        WebResource.Builder stBuilder = webResource.path("rest").path("kundera/api/session/at/" + applicationToken)
                .accept(MediaType.TEXT_PLAIN);
        String stResponse = stBuilder.get(ClientResponse.class).toString();
        String sessionToken = stBuilder.get(String.class);
        System.out.println("Response: " + stResponse);
        System.out.println("Session Token:" + sessionToken);
        return sessionToken;
    }

    @Override
    public void closeSession(String sessionToken)
    {

        System.out.println("Closing Session for Token:" + sessionToken);
        WebResource.Builder stBuilder = webResource.path("rest").path("kundera/api/session/" + sessionToken)
                .accept(MediaType.TEXT_PLAIN);
        String response = stBuilder.delete(String.class);
        System.out.println("Session Deletion Response: " + response);
    }

    @Override
    public String insertBook(String sessionToken, String bookJSON)
    {
        System.out.println("Saving Entity...");
        WebResource.Builder insertBuilder = webResource.path("rest").path("kundera/api/crud/" + sessionToken + "/Book")
                .type(MediaType.APPLICATION_JSON).accept(MediaType.TEXT_PLAIN);
        StringBuffer sb = new StringBuffer()
                .append(bookJSON);
        String insertResponse = insertBuilder.post(String.class, sb.toString());
        System.out.println(insertResponse);
        return insertResponse;
    }

    @Override
    public String findBook(String sessionToken)
    {
        WebResource.Builder findBuilder = webResource.path("rest")
                .path("kundera/api/crud/" + sessionToken + "/Book/34523423423423").accept(MediaType.APPLICATION_JSON);

        String bookJSON = findBuilder.get(String.class);
        // System.out.println(findResponse);
        System.out.println(bookJSON);
        return bookJSON;
    }

    @Override
    public String updateBook(String sessionToken, String oldBookJSON)
    {
        oldBookJSON = oldBookJSON.replaceAll("Amresh", "Saurabh");
        System.out.println("Updating Entity... " + oldBookJSON);
        WebResource.Builder updateBuilder = webResource.path("rest").path("kundera/api/crud/" + sessionToken + "/Book")
                .type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);
        String updateResponse = updateBuilder.put(String.class, oldBookJSON);
        System.out.println(updateResponse);
        return updateResponse;
    }

    @Override
    public void deleteBook(String sessionToken, String updatedBook)
    {
        WebResource.Builder deleteBuilder = webResource.path("rest")
                .path("kundera/api/crud/" + sessionToken + "/Book/delete/" + "34523423423423")
                .accept(MediaType.TEXT_PLAIN);
        String deleteResponse = deleteBuilder.delete(String.class);
        System.out.println(deleteResponse);
    } 

}
