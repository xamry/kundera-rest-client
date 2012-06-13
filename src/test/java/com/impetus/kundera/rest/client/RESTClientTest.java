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

import junit.framework.TestCase;

/**
 * <Prove description of functionality provided by this Type>
 * 
 * @author amresh.singh
 */
public class RESTClientTest extends TestCase
{

    protected void setUp() throws Exception
    {
        super.setUp();
    }

    protected void tearDown() throws Exception
    {
        super.tearDown();
    }

   /* public void testXML()
    {
        RESTClient restClient = RESTClientFactory.getRESTClient("XML");

        // Initialize REST Client
        restClient.initialize("http://localhost:8080/Kundera-Web-Examples");

        // Get Application Token
        String applicationToken = restClient.getApplicationToken();

        // Get Session Token
        String sessionToken = restClient.getSessionToken(applicationToken);

        // Insert Record
        restClient.insertBook(sessionToken, "<book><isbn>34523423423423</isbn><author>Amresh</author><publication>Willey</publication></book>");

        // Find Record
        String bookXML = restClient.findBook(sessionToken);
        System.out.println("found book:" + bookXML);

        // Update Record

        String updatedBookXML = restClient.updateBook(sessionToken, bookXML);
        System.out.println("updatedBook:" + updatedBookXML);

        // Delete Record
        restClient.deleteBook(sessionToken, updatedBookXML);

        // Close Session

        restClient.closeSession(sessionToken);

        // Close Application
        restClient.closeApplication(applicationToken);
    }*/
    
    public void testJSON()
    {
        RESTClient restClient = RESTClientFactory.getRESTClient("JSON");

        // Initialize REST Client
        restClient.initialize("http://localhost:8080/Kundera-Web-Examples");

        // Get Application Token
        String applicationToken = restClient.getApplicationToken();

        // Get Session Token
        String sessionToken = restClient.getSessionToken(applicationToken);

        // Insert Record
        restClient.insertBook(sessionToken, "{\"isbn\":\"2222\",\"author\":\"Kuldeep\", \"publication\":\"McGraw\"}");
        
        
        // Find Record
        String bookXML = restClient.findBook(sessionToken);
        System.out.println("found book:" + bookXML);

        // Update Record

        String updatedBookXML = restClient.updateBook(sessionToken, bookXML);
        System.out.println("updatedBook:" + updatedBookXML);

        // Delete Record
        restClient.deleteBook(sessionToken, updatedBookXML);

        // Close Session

        restClient.closeSession(sessionToken);

        // Close Application
        restClient.closeApplication(applicationToken);
    }

}
