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
package org.apache.myfaces.taglib.html;

import javax.faces.component.html.HtmlSelectOneListbox;

/**
 * @author Martin Marinschek (latest modification by $Author$)
 * @version $Revision$ $Date$
 * $Log$
 * Revision 1.9  2004/10/13 11:51:01  matze
 * renamed packages to org.apache
 *
 * Revision 1.8  2004/07/01 22:05:05  mwessendorf
 * ASF switch
 *
 * Revision 1.7  2004/04/05 11:04:56  manolito
 * setter for renderer type removed, no more default renderer type needed
 *
 * Revision 1.6  2004/04/01 12:57:43  manolito
 * additional extended component classes for user role support
 *
 */
public class HtmlSelectOneListboxTag
        extends HtmlSelectListboxTagBase
{
    public String getComponentType()
    {
        return HtmlSelectOneListbox.COMPONENT_TYPE;
    }

    public String getRendererType()
    {
        return "javax.faces.Listbox";
    }
}
