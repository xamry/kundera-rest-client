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
 * <Prove description of functionality provided by this Type> 
 * @author amresh.singh
 */
public class XMLRESTClient implements RESTClient
{
    
    public static void main(String[] args)
    {       
        
        ClientConfig config = new DefaultClientConfig();
        Client client = Client.create(config);
        
        URI uri = UriBuilder.fromUri("http://localhost:8080/Kundera-Web-Examples").build();
        WebResource service = client.resource(uri);
        
        //Get Application Token
        String applicationToken = getApplicationToken(service);
        
        //Get Session Token
        String sessionToken = getSessionToken(service, applicationToken);
        
        //Insert Record
        insertBook(service, sessionToken);
        
        //Find Record
        String book = findBook(service, sessionToken);
        System.out.println("found book:" + book);
        
        String updatedBook = updateBook(service, sessionToken, book);
        System.out.println("updatedBook:" + updatedBook);
        
        deleteBook(service, sessionToken, updatedBook);     
        
    }

    /**
     * @param service
     * @param sessionToken
     * @param updatedBook
     */
    private static void deleteBook(WebResource service, String sessionToken, String updatedBook)
    {
        WebResource.Builder deleteBuilder = service.path("rest").path("kundera/api/crud/" + sessionToken + "/Book/delete/" + "34523423423423").accept(MediaType.TEXT_PLAIN);              
        String deleteResponse = deleteBuilder.delete(String.class);
        System.out.println(deleteResponse);
    }

    /**
     * @param service
     * @param sessionToken
     * @param book
     */
    private static String updateBook(WebResource service, String sessionToken, String book)
    {
        book = book.replaceAll("Amresh", "Saurabh");        
        System.out.println("Updating Entity... " + book);       
        WebResource.Builder updateBuilder = service.path("rest").path("kundera/api/crud/" + sessionToken + "/Book").type(MediaType.APPLICATION_XML).accept(MediaType.APPLICATION_XML);              
        String updateResponse = updateBuilder.put(String.class, book);
        System.out.println(updateResponse);
        return updateResponse;
    }

    /**
     * @param service
     * @param sessionToken
     */
    private static String findBook(WebResource service, String sessionToken)
    {
        WebResource.Builder findBuilder = service.path("rest").path("kundera/api/crud/" + sessionToken + "/Book/34523423423423").accept(MediaType.APPLICATION_XML);
        //String findResponse = findBuilder.get(ClientResponse.class).toString();
        String bookXML = findBuilder.get(String.class);
        //System.out.println(findResponse);
        System.out.println(bookXML);
        return bookXML;
    }

    /**
     * @param service
     * @param sessionToken
     * @return
     */
    private static String insertBook(WebResource service, String sessionToken)
    {
        System.out.println("Saving Entity...");       
        WebResource.Builder insertBuilder = service.path("rest").path("kundera/api/crud/" + sessionToken + "/Book").type(MediaType.APPLICATION_XML).accept(MediaType.TEXT_PLAIN);        
        StringBuffer sb = new StringBuffer().append("<book><isbn>34523423423423</isbn><author>Amresh</author><publication>Willey</publication></book>");      
        String insertResponse = insertBuilder.post(String.class, sb.toString());
        System.out.println(insertResponse);
        return insertResponse;
    }

    /**
     * @param service
     * @param applicationToken
     * @return
     */
    private static String getSessionToken(WebResource service, String applicationToken)
    {
        System.out.println("Getting Session Token...");        
        WebResource.Builder stBuilder = service.path("rest").path("kundera/api/session/at/" + applicationToken).accept(MediaType.TEXT_PLAIN);
        String stResponse = stBuilder.get(ClientResponse.class).toString();
        String sessionToken = stBuilder.get(String.class);        
        System.out.println("Response: " + stResponse);
        System.out.println("Session Token:" + sessionToken);
        return sessionToken;
    }

    /**
     * @param service
     * @return
     */
    private static String getApplicationToken(WebResource service)
    {
        System.out.println("Getting Application Token...");        
        WebResource.Builder atBuilder = service.path("rest").path("kundera/api/initialize/pu/twissandra").accept(MediaType.TEXT_PLAIN);
        String atResponse = atBuilder.get(ClientResponse.class).toString();
        String applicationToken = atBuilder.get(String.class);        
        System.out.println("Response: " + atResponse);
        System.out.println("Application Token:" + applicationToken);
        return applicationToken;
    }

}
