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
package org.apache.myfaces.custom.tree.renderkit.html;

import org.apache.myfaces.custom.tree.HtmlTree;
import org.apache.myfaces.custom.tree.HtmlTreeImageCommandLink;
import org.apache.myfaces.custom.tree.HtmlTreeNode;
import org.apache.myfaces.custom.tree.IconProvider;
import org.apache.myfaces.renderkit.RendererUtils;
import org.apache.myfaces.renderkit.html.HTML;
import org.apache.myfaces.renderkit.html.HtmlRenderer;
import org.apache.myfaces.renderkit.html.HtmlRendererUtils;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


/**
 * @author <a href="mailto:oliver@rossmueller.com">Oliver Rossmueller</a>
 * @version $Revision$ $Date$
 *          $Log$
 *          Revision 1.10  2004/10/13 11:50:58  matze
 *          renamed packages to org.apache
 *
 *          Revision 1.9  2004/07/18 21:36:25  o_rossmueller
 *          fix #991740: getResourceURL for tree image urls
 *
 *          Revision 1.8  2004/07/01 21:53:10  mwessendorf
 *          ASF switch
 *
 *          Revision 1.7  2004/05/27 15:06:39  manolito
 *          bugfix: node labels not rendered any more
 *
 *          Revision 1.6  2004/05/12 02:27:43  o_rossmueller
 *          fix #951896: tree component works for JAVASCRIPT=false, too
 *
 *          Revision 1.5  2004/05/10 01:24:51  o_rossmueller
 *          added iconClass attribute
 *
 *          Revision 1.4  2004/05/04 12:19:13  o_rossmueller
 *          added icon provider
 *
 *          Revision 1.3  2004/04/23 19:09:34  o_rossmueller
 *          state transition magic
 *          <p/>
 *          Revision 1.2  2004/04/22 12:57:39  o_rossmueller
 *          fixed leaf node layout
 *          <p/>
 *          Revision 1.1  2004/04/22 10:20:24  manolito
 *          tree component
 */
public class HtmlTreeRenderer
    extends HtmlRenderer
{

    private static final Integer ZERO = new Integer(0);


    public boolean getRendersChildren()
    {
        return true;
    }


    public void encodeChildren(FacesContext facesContext, UIComponent component) throws IOException
    {
        //super.encodeChildren(facesContext, component);
        // children are rendered in encodeEnd
    }


    public void encodeEnd(FacesContext facesContext, UIComponent component) throws IOException
    {
        RendererUtils.checkParamValidity(facesContext, component, HtmlTree.class);
        ResponseWriter writer = facesContext.getResponseWriter();
        HtmlTree tree = (HtmlTree) component;

        HtmlRendererUtils.writePrettyLineSeparator(facesContext);
        writer.startElement(HTML.TABLE_ELEM, null);
        HtmlRendererUtils.renderHTMLAttributes(writer, tree, HTML.TABLE_PASSTHROUGH_ATTRIBUTES);
        writer.writeAttribute(HTML.BORDER_ATTR, ZERO, null);
        writer.writeAttribute(HTML.CELLSPACING_ATTR, ZERO, null);
        writer.writeAttribute(HTML.CELLPADDING_ATTR, ZERO, null);

        int maxLevel = tree.getRootNode().getMaxChildLevel();

        // create initial children list from root node facet
        ArrayList childNodes = new ArrayList(1);
        childNodes.add(tree.getRootNode());

        renderChildren(facesContext, writer, tree, childNodes, maxLevel, tree.getIconProvider());

        HtmlRendererUtils.writePrettyLineSeparator(facesContext);
        writer.endElement(HTML.TABLE_ELEM);
        super.encodeEnd(facesContext, component);
    }


    protected void renderChildren(FacesContext facesContext,
                                  ResponseWriter writer,
                                  HtmlTree tree,
                                  List children,
                                  int maxLevel,
                                  IconProvider iconProvider) throws IOException
    {
        String iconClass = tree.getIconClass();

        for (Iterator it = children.iterator(); it.hasNext();)
        {
            HtmlTreeNode child = (HtmlTreeNode) it.next();
            if (!child.isRendered())
            {
                continue;
            }
            HtmlRendererUtils.writePrettyLineSeparator(facesContext);

            writer.startElement(HTML.TR_ELEM, null);

            int[] layout = child.getLayout();

            // tree lines
            for (int i = 0; i < layout.length - 1; i++)
            {
                int state = layout[i];
                writer.startElement(HTML.TD_ELEM, null);
                String url = getLayoutImage(tree, state);

                if ((url != null) && (url.length() > 0))
                {
                    writer.startElement(HTML.IMG_ELEM, child);

                    String src;
                    if (url.startsWith(HTML.HREF_PATH_SEPARATOR))
                    {
                        String path = facesContext.getExternalContext().getRequestContextPath();
                        src = path + url;
                    } else
                    {
                        src = url;
                    }
                    //Encode URL
                    //Although this is an url url, encodeURL is no nonsense, because the
                    //actual url url could also be a dynamic servlet request:
                    src = facesContext.getExternalContext().encodeResourceURL(src);
                    writer.writeAttribute(HTML.SRC_ATTR, src, null);
                    writer.writeAttribute(HTML.BORDER_ATTR, ZERO, null);

                    HtmlRendererUtils.renderHTMLAttributes(writer, child, HTML.IMG_PASSTHROUGH_ATTRIBUTES);

                    writer.endElement(HTML.IMG_ELEM);
                }
                writer.endElement(HTML.TD_ELEM);

            }

            // command link
            writer.startElement(HTML.TD_ELEM, null);
            int state = layout[layout.length - 1];
            String url = getLayoutImage(tree, state);

            if (state == HtmlTreeNode.CHILD || state == HtmlTreeNode.CHILD_FIRST || state == HtmlTreeNode.CHILD_SINGLE || state == HtmlTreeNode.CHILD_LAST)
            {
                // no action, just img


                writeImageElement(url, facesContext, writer, child);

            } else
            {
                HtmlTreeImageCommandLink expandCollapse = (HtmlTreeImageCommandLink) child.getExpandCollapseCommand(facesContext);
                expandCollapse.setImage(getLayoutImage(tree, layout[layout.length - 1]));

                expandCollapse.encodeBegin(facesContext);
                expandCollapse.encodeEnd(facesContext);
            }
            writer.endElement(HTML.TD_ELEM);


            int labelColSpan = maxLevel - child.getLevel() + 1;

            // node icon
            if (iconProvider != null)
            {
                url = iconProvider.getIconUrl(child.getUserObject(), child.getChildCount(), child.isLeaf(facesContext));
            } else
            {
                if (!child.isLeaf(facesContext))
                {
                    // todo: icon provider
                    url = "images/tree/folder.gif";
                } else
                {
                    url = null;
                }
            }

            if ((url != null) && (url.length() > 0))
            {
                writer.startElement(HTML.TD_ELEM, null);
                if (iconClass != null) {
                    writer.writeAttribute(HTML.CLASS_ATTR, iconClass, null);
                }
                writeImageElement(url, facesContext, writer, child);
                writer.endElement(HTML.TD_ELEM);
            } else
            {
                // no icon, so label has more room
                labelColSpan++;
            }


            // node label
            writer.startElement(HTML.TD_ELEM, null);
            writer.writeAttribute(HTML.COLSPAN_ATTR, new Integer(labelColSpan), null);
            if (child.isSelected() && tree.getSelectedNodeClass() != null)
            {
                writer.writeAttribute(HTML.CLASS_ATTR, tree.getSelectedNodeClass(), null);
            } else if (!child.isSelected() && tree.getNodeClass() != null)
            {
                writer.writeAttribute(HTML.CLASS_ATTR, tree.getNodeClass(), null);
            }
            child.encodeBegin(facesContext);
            child.encodeEnd(facesContext);
            writer.endElement(HTML.TD_ELEM);

            writer.endElement(HTML.TR_ELEM);

            if (child.getChildCount() > 0)
            {
                renderChildren(facesContext, writer, tree, child.getChildren(), maxLevel, iconProvider);
            }
        }
    }


    private void writeImageElement(String url, FacesContext facesContext, ResponseWriter writer, HtmlTreeNode child)
        throws IOException
    {
        writer.startElement(HTML.IMG_ELEM, child);
        String src =facesContext.getApplication().getViewHandler().getResourceURL(facesContext, url);
//        if (url.startsWith(HTML.HREF_PATH_SEPARATOR))
//        {
//            String path = facesContext.getExternalContext().getRequestContextPath();
//            src = path + url;
//        } else
//        {
//            src = url;
//        }
//        //Encode URL
//        src = facesContext.getExternalContext().encodeResourceURL(src);
        writer.writeAttribute(HTML.SRC_ATTR, src, null);
        writer.writeAttribute(HTML.BORDER_ATTR, ZERO, null);

        HtmlRendererUtils.renderHTMLAttributes(writer, child, HTML.IMG_PASSTHROUGH_ATTRIBUTES);

        writer.endElement(HTML.IMG_ELEM);
    }


    protected String getLayoutImage(HtmlTree tree, int state)
    {
        switch (state)
        {
            case HtmlTreeNode.OPEN:
                return tree.getIconNodeOpenMiddle();
            case HtmlTreeNode.OPEN_FIRST:
                return tree.getIconNodeOpenFirst();
            case HtmlTreeNode.OPEN_LAST:
                return tree.getIconNodeOpenLast();
            case HtmlTreeNode.OPEN_SINGLE:
                return tree.getIconNodeOpen();
            case HtmlTreeNode.CLOSED:
                return tree.getIconNodeCloseMiddle();
            case HtmlTreeNode.CLOSED_FIRST:
                return tree.getIconNodeCloseFirst();
            case HtmlTreeNode.CLOSED_LAST:
                return tree.getIconNodeCloseLast();
            case HtmlTreeNode.CLOSED_SINGLE:
                return tree.getIconNodeClose();
            case HtmlTreeNode.CHILD:
                return tree.getIconChildMiddle();
            case HtmlTreeNode.CHILD_FIRST:
                return tree.getIconChildFirst();
            case HtmlTreeNode.CHILD_LAST:
                return tree.getIconChildLast();
            case HtmlTreeNode.CHILD_SINGLE:
                return tree.getIconChild();
            case HtmlTreeNode.LINE:
                return tree.getIconLine();
            case HtmlTreeNode.EMPTY:
                return tree.getIconNoline();
            default:
                return tree.getIconNoline();
        }
    }
}
