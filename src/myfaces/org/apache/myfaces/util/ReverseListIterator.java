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
package org.apache.myfaces.util;

import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * @author Manfred Geiler (latest modification by $Author$)
 * @version $Revision$ $Date$
 * $Log$
 * Revision 1.3  2004/10/13 11:51:01  matze
 * renamed packages to org.apache
 *
 * Revision 1.2  2004/07/01 22:05:15  mwessendorf
 * ASF switch
 *
 * Revision 1.1  2004/05/17 14:28:28  manolito
 * new configuration concept
 *
 */
public class ReverseListIterator
        implements Iterator
{
    //private static final Log log = LogFactory.getLog(ReverseListIterator.class);

    private int _cursor;
    private List _list;

    public ReverseListIterator(List list)
    {
        _list = list;
        _cursor = list.size() - 1;
    }

    public boolean hasNext()
    {
        return _cursor >= 0;
    }

    public Object next()
    {
        if (_cursor < 0)
        {
            throw new NoSuchElementException();
        }
        return _list.get(_cursor--);
    }

    public void remove()
    {
        throw new UnsupportedOperationException();
    }

}
