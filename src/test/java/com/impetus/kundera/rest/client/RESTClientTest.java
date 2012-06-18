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

import javax.ws.rs.core.MediaType;

import junit.framework.TestCase;

/**
 * Test case for {@link RESTClient} 
 * @author amresh.singh
 */
public class RESTClientTest extends TestCase
{

    private static final String WS_URL = "http://localhost:8080/Kundera-Web-Examples";
    RESTClient restClient;
    
    String applicationToken = null;
    String sessionToken = null;     
    
    String bookStr;
    String pk;
    

    protected void setUp() throws Exception
    {
        super.setUp();
        restClient = new RESTClientImpl();        
        
    }

    protected void tearDown() throws Exception
    {
        super.tearDown();
    }
    
    public void test() {
        crud(MediaType.APPLICATION_XML);
        crud(MediaType.APPLICATION_JSON);
        
    }

    public void crud(String mediaType)
    {     
        if(MediaType.APPLICATION_XML.equals(mediaType)) {
            bookStr = "<book><isbn>34523423423423</isbn><author>Amresh</author><publication>Willey</publication></book>";
            pk = "34523423423423";
        } else if(MediaType.APPLICATION_JSON.equals(mediaType)) {
            bookStr = "{book:{\"isbn\":\"2222\",\"author\":\"Kuldeep\", \"publication\":\"McGraw\"}}";
            pk = "2222";
        } else {
            fail("Incorrect Media Type:" + mediaType);
            return;
        }       
        
        // Initialize REST Client
        restClient.initialize(WS_URL, mediaType);

        // Get Application Token
        applicationToken = restClient.getApplicationToken();

        // Get Session Token        
        sessionToken = restClient.getSessionToken(applicationToken);
        
        
        // Insert Record        
        restClient.insertBook(sessionToken, bookStr);
        
        // Find Record
        String foundBook = restClient.findBook(sessionToken, pk);
        System.out.println("Before book " + foundBook);
        if(MediaType.APPLICATION_JSON.equals(mediaType)) {
            foundBook = "{book:" + foundBook + "}";
        }       
        
        System.out.println("found book:" + foundBook);

        // Update Record        
        String updatedBook = restClient.updateBook(sessionToken, foundBook);
        System.out.println("updatedBook:" + updatedBook);

        // Delete Record        
        restClient.deleteBook(sessionToken, updatedBook, pk);

        // Close Session
        restClient.closeSession(sessionToken);

        // Close Application        
        restClient.closeApplication(applicationToken);
    }  
  
}
