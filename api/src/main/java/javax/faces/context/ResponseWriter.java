/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package javax.faces.context;

import javax.faces.component.UIComponent;
import java.io.IOException;
import java.io.Writer;

/**
 * see Javadoc of <a href="http://java.sun.com/j2ee/javaserverfaces/1.1_01/docs/api/index.html">JSF Specification</a>
 *
 * @author Manfred Geiler (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public abstract class ResponseWriter
        extends Writer
{
    public abstract String getContentType();

    public abstract String getCharacterEncoding();

    public abstract void flush()
            throws IOException;

    public abstract void startDocument()
            throws IOException;

    public abstract void endDocument()
            throws IOException;

    public abstract void startElement(String name,
                                      UIComponent component)
            throws IOException;

    public abstract void endElement(String name)
            throws IOException;

    public abstract void writeAttribute(String name,
                                        Object value,
                                        String property)
            throws IOException;

    public abstract void writeURIAttribute(String name,
                                           Object value,
                                           String property)
            throws IOException;

    public abstract void writeComment(Object comment)
            throws IOException;

    public abstract void writeText(Object text,
                                   String property)
            throws IOException;

    public abstract void writeText(char[] text,
                                   int off,
                                   int len)
            throws IOException;

    public abstract ResponseWriter cloneWithWriter(Writer writer);
}
