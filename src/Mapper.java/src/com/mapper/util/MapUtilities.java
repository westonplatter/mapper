//MapUtilities.java
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
package com.mapper.util;

/**
 * This class contains static methods to be used as utilities throughout the 
 * project.  This group of utilities will be useful for map locations.
 * 
 * @author desilva
 *
 */
public class MapUtilities
{
    private static final double E6_MULTIPLIER = 1E6;
    
    /**
     * Converts a latitude or longitude coordinate to an E6 latitude or 
     *      longitude coordinate.
     * @param coordinate  the latitude or longitude coordinate to convert.
     * @return  the E6 form of the coordinate.
     */
    public static int convertToE6Coordinate(double coordinate)
    {
        return (int)(coordinate * E6_MULTIPLIER);
    }
    
    /**
     * Converts an E6 latitude or longitude coordinate to a standard latitude or 
     *      longitude coordinate.
     * @param e6Coordinate  the E6 latitude or longitude coordinate to convert.
     * @return  the standard decimal form of the coordinate.
     */
    public static double convertToStdCoordinate(int e6Coordinate)
    {
        return ((double) e6Coordinate)/ E6_MULTIPLIER;
    }
}
