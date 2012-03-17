//SkywayEdgeTest_GeoPointConst.java
/**
 * Copyright 2012 desilva
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

import com.google.android.maps.GeoPoint;
import com.mapper.map.SkywayEdge;

/**
 * @author desilva
 *
 */
public class SkywayEdgeTest_GeoPointConst extends SkywayEdgeTest
{

     /* (non-Javadoc)
     * @see junit.framework.TestCase#setUp()
     */
     protected void setUp() throws Exception
     {
         super.setUp();
         
         this.edgePoint1 = new GeoPoint(SkywayNodeTest.KELLER_LAT, 
                                        SkywayNodeTest.KELLER_LONG);
         
         this.edgePoint2 = new GeoPoint(SkywayNodeTest.ARCH_LAT, 
                                        SkywayNodeTest.ARCH_LONG);
         
         this.edgeUniqueId = 10;
         
         this.edgeUnderTest = new SkywayEdge(this.edgeUniqueId, 
                                             this.edgePoint1, 
                                             this.edgePoint2);
     }
    
     /* (non-Javadoc)
     * @see junit.framework.TestCase#tearDown()
     */
     protected void tearDown() throws Exception
     {
         super.tearDown();
         
         this.edgeUnderTest = null;
         this.edgePoint1 = null;
         this.edgePoint2 = null;
     }
}
