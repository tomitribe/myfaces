/*
 * Copyright 2004 The Apache Software Foundation.
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
package org.apache.myfaces.config.element;

import java.util.Iterator;

/**
 * @author Manfred Geiler (latest modification by $Author$)
 * @version $Revision$ $Date$
 * $Log$
 * Revision 1.2  2004/10/13 11:50:59  matze
 * renamed packages to org.apache
 *
 * Revision 1.1  2004/07/07 00:25:04  o_rossmueller
 * tidy up config/confignew package (moved confignew classes to package config)
 *
 * Revision 1.2  2004/07/01 22:05:04  mwessendorf
 * ASF switch
 *
 * Revision 1.1  2004/05/17 14:28:26  manolito
 * new configuration concept
 *
 */
public interface ListEntries
{
    // <!ELEMENT list-entries    (value-class?, (null-value|value)*)>

    public String getValueClass();

    /**
     * @return Iterator over {@link ListEntry} entries
     */
    public Iterator getListEntries();
    
}
