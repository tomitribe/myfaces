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
package org.apache.myfaces.custom.stylesheet;

import java.io.IOException;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

import org.apache.myfaces.renderkit.html.HtmlRenderer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author mwessendorf (latest modification by $Author$) 
 * @version $Revision$ $Date$ 
 * $Log$
 * Revision 1.2  2004/10/13 11:50:58  matze
 * renamed packages to org.apache
 *
 * Revision 1.1  2004/08/18 15:36:09  mwessendorf
 * added a new Stylesheet-Component
 * 
*/

public class StylesheetRenderer extends HtmlRenderer {

	 private static final Log log = LogFactory.getLog(StylesheetRenderer.class);

	 public void encodeEnd(FacesContext context, UIComponent component)
        throws IOException {

        if ((context == null) || (component == null)) {
            throw new NullPointerException();
        }
        Stylesheet stylesheet = (Stylesheet) component;

        ResponseWriter writer = context.getResponseWriter();
        writer.startElement("link", component);
        writer.writeAttribute("rel", "stylesheet", null);
        writer.writeAttribute("type", "text/css", null);
        writer.writeURIAttribute
            ("href",
             context.getExternalContext().getRequestContextPath()+stylesheet.getPath(),
			 "path");
        writer.endElement("link");
    }
}