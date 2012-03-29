////MapEdgeTest_GeoPointConst.java
///**
// * Copyright 2012 desilva
// * 
// * Licensed under the Apache License, Version 2.0 (the "License");
// * you may not use this file except in compliance with the License.
// * You may obtain a copy of the License at
// * 
// *     http://www.apache.org/licenses/LICENSE-2.0
// *    
// * Unless required by applicable law or agreed to in writing, software
// * distributed under the License is distributed on an "AS IS" BASIS,
// * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// * See the License for the specific language governing permissions and
// * limitations under the License.
// */
//package com.mapper.map.test;
//
//import com.google.android.maps.GeoPoint;
//import com.mapper.map.MapEdge;
//import com.mapper.util.MapUtilities;
//
///**
// * @author desilva
// *
// */
//public class MapEdgeTest_LatLongConst extends MapEdgeTest
//{
//
//     /* (non-Javadoc)
//     * @see junit.framework.TestCase#setUp()
//     */
//     protected void setUp() throws Exception
//     {
//         super.setUp();
//         
//         this.edgePoint1 = new GeoPoint(MapNodeTest.KELLER_LAT, 
//                                        MapNodeTest.KELLER_LONG);
//         
//         this.edgePoint2 = new GeoPoint(MapNodeTest.ARCH_LAT, 
//                                        MapNodeTest.ARCH_LONG);
//         
//         //TODO -- Fix this.  I don't know what it initializes to.
//         this.edgeUniqueId = 10;
//         
//         //TODO -- Not sure why it takes in an int.  It should take in a double.
//         this.edgeUnderTest = new MapEdge(
//                 (int) MapUtilities.convertToStdCoordinate(MapNodeTest.KELLER_LAT),
//                 (int) MapUtilities.convertToStdCoordinate(MapNodeTest.KELLER_LONG),
//                 (int) MapUtilities.convertToStdCoordinate(MapNodeTest.ARCH_LAT),
//                 (int) MapUtilities.convertToStdCoordinate(MapNodeTest.ARCH_LONG));
//     }
//    
//     /* (non-Javadoc)
//     * @see junit.framework.TestCase#tearDown()
//     */
//     protected void tearDown() throws Exception
//     {
//         super.tearDown();
//         
//         this.edgeUnderTest = null;
//         this.edgePoint1 = null;
//         this.edgePoint2 = null;
//     }
//}
