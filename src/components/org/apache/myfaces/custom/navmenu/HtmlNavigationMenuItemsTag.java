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
package net.sourceforge.myfaces.custom.navmenu;

import net.sourceforge.myfaces.taglib.UIComponentTagBase;

/**
 * @author Thomas Spiegl (latest modification by $Author$)
 * @version $Revision$ $Date$
 * $Log$
 * Revision 1.1  2004/09/14 13:38:01  royalts
 * removed check on urlPattern
 *
 */
public class HtmlNavigationMenuItemsTag
        extends UIComponentTagBase
{
    //private static final Log log = LogFactory.getLog(HtmlNavigationMenuItemsTag.class);

    public String getComponentType()
    {
        return "javax.faces.SelectItems";
    }

    public String getRendererType()
    {
        return null;
    }

    // UISelectItems attributes
    // --> binding, id, value already handled by UIComponentTagBase

}