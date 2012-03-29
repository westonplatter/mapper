//NodeDBTest.java
/**
 * Copyright 2012 Jared Swanson
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 *    
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.mapper.map.test;

import java.util.ArrayList;

import com.mapper.map.MapNode;
import com.mapper.map.NodeDB;

import android.test.AndroidTestCase;

/**
 * @author Jared
 *
 */
public class NodeDBTest extends AndroidTestCase
{

    /* (non-Javadoc)
     * @see android.test.AndroidTestCase#setUp()
     */
    protected void setUp() throws Exception
    {
        super.setUp();
    }

    /* (non-Javadoc)
     * @see android.test.AndroidTestCase#tearDown()
     */
    protected void tearDown() throws Exception
    {
        super.tearDown();
    }

    /**
     * Test method for {@link com.mapper.map.NodeDB#NodeDB(java.util.ArrayList)}.
     */
    public void testNodeDB()
    {
        
        NodeDB nodeDbTest = new NodeDB(null);
        ArrayList<MapNode> nodeList = nodeDbTest.getNodeList();
        
        assertNotNull(nodeList);
        assertEquals(0, nodeList.size());
        
    }
}
