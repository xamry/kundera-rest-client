package com.impetus.kundera.rest.client;

import java.io.InputStream;
import java.net.URI;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;

import com.impetus.kundera.rest.common.StreamUtils;
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
        System.out.println("\n\nGetting Application Token...");
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
        System.out.println("\n\nClosing Application for Token:" + applicationToken);
        WebResource.Builder atBuilder = webResource.path("rest").path("kundera/api/application/" + applicationToken)
                .accept(MediaType.TEXT_PLAIN);
        String response = atBuilder.delete(String.class);
        System.out.println("Application Closure Response: " + response);
    }

    @Override
    public String getSessionToken(String applicationToken)
    {
        System.out.println("\n\nGetting Session Token...");
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

        System.out.println("\n\nClosing Session for Token:" + sessionToken);
        WebResource.Builder stBuilder = webResource.path("rest").path("kundera/api/session/" + sessionToken)
                .accept(MediaType.TEXT_PLAIN);
        String response = stBuilder.delete(String.class);
        System.out.println("Session Deletion Response: " + response);
    }
    

    @Override
    public String insertBook(String sessionToken, String bookJSON)
    {
        System.out.println("\n\nInserting Entity...");
        WebResource.Builder insertBuilder = webResource.path("rest").path("kundera/api/crud/" + sessionToken + "/Book")
                .type(MediaType.APPLICATION_XML).accept(MediaType.APPLICATION_OCTET_STREAM);
        StringBuffer sb = new StringBuffer()
                .append(bookJSON);
        ClientResponse insertResponse = (ClientResponse)insertBuilder.post(ClientResponse.class, sb.toString());
        System.out.println("Response From Insert Book: " + insertResponse);
        return insertResponse.toString();
    }

    @Override
    public String findBook(String sessionToken)
    {
        System.out.println("\n\nFinding Entity...");
        WebResource.Builder findBuilder = webResource.path("rest")
                .path("kundera/api/crud/" + sessionToken + "/Book/34523423423423").accept(MediaType.APPLICATION_XML);
        
        ClientResponse findResponse = (ClientResponse)findBuilder.get(ClientResponse.class);
        
        InputStream is = findResponse.getEntityInputStream();
        String bookJSON = StreamUtils.toString(is);  
        
        System.out.println("Found Entity:" + bookJSON);
        return bookJSON;
    }

    @Override
    public String updateBook(String sessionToken, String oldBookJSON)
    {
        System.out.println("\n\nUpdating Entity... " + oldBookJSON);
        oldBookJSON = oldBookJSON.replaceAll("Amresh", "Saurabh");        
        WebResource.Builder updateBuilder = webResource.path("rest").path("kundera/api/crud/" + sessionToken + "/Book")
                .type(MediaType.APPLICATION_XML).accept(MediaType.APPLICATION_XML);
        ClientResponse updateResponse = updateBuilder.put(ClientResponse.class, oldBookJSON);
        InputStream is = updateResponse.getEntityInputStream();
        String updatedBook = StreamUtils.toString(is);
        System.out.println("Updated Book: " + updatedBook);
        return updatedBook;
    }

    @Override
    public void deleteBook(String sessionToken, String updatedBook)
    {
        System.out.println("\n\nDeleting Entity... " + updatedBook);
        WebResource.Builder deleteBuilder = webResource.path("rest")
                .path("kundera/api/crud/" + sessionToken + "/Book/delete/" + "34523423423423")
                .accept(MediaType.TEXT_PLAIN);
        ClientResponse deleteResponse = (ClientResponse) deleteBuilder.delete(ClientResponse.class);
        System.out.println("Delete Response:" + deleteResponse.getStatus());
    }

}
