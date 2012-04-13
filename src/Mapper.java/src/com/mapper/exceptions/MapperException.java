//MapperException.java
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
package com.mapper.exceptions;

/**
 * This class declares a common exception type for extension within the project.
 * 
 * @author desilva
 *
 */
@SuppressWarnings("serial")
public class MapperException extends Exception
{

    /**
     * 
     */
    public MapperException()
    {
    }

    /**
     * @param detailMessage
     */
    public MapperException(String detailMessage)
    {
        super(detailMessage);
    }

    /**
     * @param throwable
     */
    public MapperException(Throwable throwable)
    {
        super(throwable);
    }

    /**
     * @param detailMessage
     * @param throwable
     */
    public MapperException(String detailMessage, Throwable throwable)
    {
        super(detailMessage, throwable);
    }

}
