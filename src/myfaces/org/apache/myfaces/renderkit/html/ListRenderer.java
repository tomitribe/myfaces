/**
 * MyFaces - the free JSF implementation
 * Copyright (C) 2003  The MyFaces Team (http://myfaces.sourceforge.net)
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
 */
package net.sourceforge.myfaces.renderkit.html;

import net.sourceforge.myfaces.component.UIPanel;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import java.io.IOException;
import java.util.Iterator;

/**
 * TODO: description
 * @author Thomas Spiegl (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class ListRenderer
        extends HTMLRenderer
{
    public static final String TYPE = "List";

    public static final String ACTUAL_ROW = ListRenderer.class.getName() + ".actualRow";
    public static final String LAST_COMPONENT = ListRenderer.class.getName() + ".lastComponent";


    public boolean supportsComponentType(UIComponent uiComponent)
    {
        return uiComponent instanceof javax.faces.component.UIPanel;
    }

    public boolean supportsComponentType(String s)
    {
        return s.equals(javax.faces.component.UIPanel.TYPE);
    }

    public String getRendererType()
    {
        return TYPE;
    }

    public void encodeBegin(FacesContext context, UIComponent uicomponent)
            throws IOException
    {
        ResponseWriter writer = context.getResponseWriter();

        writer.write("<table");
        String style = (String)uicomponent.getAttribute(UIPanel.CLASS_ATTR);
        if (style != null && style.length() > 0)
        {
            writer.write(" class=\"");
            writer.write(style);
            writer.write("\"");
        }
        writer.write(">\n");
    }

    public void encodeChildren(FacesContext context, UIComponent uicomponent)
        throws IOException
    {
        int i = 0;
        for (Iterator children = uicomponent.getChildren(); children.hasNext();)
        {
            UIComponent childComponent = (UIComponent)children.next();
            String rendererType = childComponent.getRendererType();

            // check renderer types
            if (i == 0)
            {
                String headerStyle = (String)uicomponent.getAttribute(UIPanel.HEADER_CLASS_ATTR);
                if (headerStyle != null && headerStyle.length() > 0)
                {
                    // first component should have renderer of type Group
                    if (!rendererType.equals(GroupRenderer.TYPE))
                    {
                        throw new IllegalArgumentException("Illegal UIComponent! If Attribute headerClass is set, the first nested UIComponent " +
                                                           "must hava renderer type " + GroupRenderer.TYPE);

                    }
                }
            }
            if (!rendererType.equals(DataRenderer.TYPE) &&
                !rendererType.equals(GroupRenderer.TYPE))
            {
                throw new IllegalArgumentException("Illegal UIComponent! UIComponent nested within a panel component list " +
                                                   "must have renderer type in (" + DataRenderer.TYPE + ", " + GroupRenderer.TYPE + ")");

            }

            // set actual row
            uicomponent.setAttribute(ACTUAL_ROW, new Integer(i));

            // is Component the last Component?
            if (!children.hasNext())
            {
                uicomponent.setAttribute(LAST_COMPONENT, Boolean.TRUE);
            }

            // childComponent may read/write ACTUAL_ROW attribute
            encodeComponent(context, childComponent);

            // if ACTUAL_ROW = i, then goto next row
            Integer actualRow = (Integer)uicomponent.getAttribute(ACTUAL_ROW);
            if (actualRow != null && actualRow.intValue() == i)
            {
                i++;
            }
        }
    }

    public void encodeEnd(FacesContext context, UIComponent uicomponent)
            throws IOException
    {
        ResponseWriter writer = context.getResponseWriter();
        writer.write("</table>\n");
    }
}
