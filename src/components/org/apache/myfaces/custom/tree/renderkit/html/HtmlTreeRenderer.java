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
import org.apache.myfaces.custom.tree.HtmlTreeColumn;
import org.apache.myfaces.custom.tree.HtmlTreeImageCommandLink;
import org.apache.myfaces.custom.tree.HtmlTreeNode;
import org.apache.myfaces.custom.tree.IconProvider;
import org.apache.myfaces.custom.tree.TreeNode;
import org.apache.myfaces.renderkit.RendererUtils;
import org.apache.myfaces.renderkit.html.HTML;
import org.apache.myfaces.renderkit.html.HtmlRendererUtils;
import org.apache.myfaces.renderkit.html.HtmlTableRendererBase;
import org.apache.myfaces.util.ArrayUtils;
import org.apache.myfaces.util.StringUtils;

import javax.faces.component.UIColumn;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author <a href="mailto:oliver@rossmueller.com">Oliver Rossmueller </a>
 * @version $Revision$ $Date$
 *
 *          $Log$
 *          Revision 1.12  2004/11/26 12:46:38  oros
 *          cleanup: removed unused iconChild attribute
 *
 *          Revision 1.11  2004/11/26 12:14:09  oros
 *          MYFACES-8: applied tree table patch by David Le Strat
 * 
 *
 */
public class HtmlTreeRenderer extends HtmlTableRendererBase
{

    private static final Integer ZERO = new Integer(0);

    public boolean getRendersChildren()
    {
        return true;
    }

    public void encodeBegin(FacesContext facesContext, UIComponent uiComponent) throws IOException
    {
        // Rendering occurs in encodeEnd.
    }

    public void encodeChildren(FacesContext facesContext, UIComponent component) throws IOException
    {
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

        // Create initial children list from root node facet
        ArrayList childNodes = new ArrayList(1);
        childNodes.add(tree.getRootNode());

        // Render header.
        renderFacet(facesContext, writer, component, true, maxLevel);

        // Render children.
        renderChildren(facesContext, writer, tree, childNodes, maxLevel, tree.getIconProvider());

        // Render footer.
        renderFacet(facesContext, writer, component, false, maxLevel);
        writer.endElement(HTML.TABLE_ELEM);
    }

    /**
     * <p>
     * Overrides super renderFacet to render the {@link HtmlTree} facets.
     * <p>
     * 
     * @param facesContext The facesContext
     * @param writer The writer.
     * @param component The component.
     * @param header Whether there is a header.
     * @param maxLevel The max level for the rendered tree.
     * @throws IOException Throws IOException.
     */
    protected void renderFacet(FacesContext facesContext, ResponseWriter writer, UIComponent component, boolean header, int maxLevel)
            throws IOException
    {
        int colspan = 0;
        boolean hasColumnFacet = false;
        for (Iterator it = component.getChildren().iterator(); it.hasNext();)
        {
            UIComponent uiComponent = (UIComponent) it.next();
            if ((uiComponent.getFamily().equals(UIColumn.COMPONENT_FAMILY))
                    && ((UIColumn) uiComponent).isRendered())
            {
                colspan++;
                if (!hasColumnFacet)
                {
                    hasColumnFacet = header ? ((UIColumn) uiComponent).getHeader() != null : ((UIColumn) uiComponent)
                            .getFooter() != null;
                }
            }
            else if ((uiComponent.getFamily().equals(HtmlTreeColumn.COMPONENT_FAMILY))
                    && ((HtmlTreeColumn) uiComponent).isRendered())
            {
                colspan += maxLevel + 3;
                if (!hasColumnFacet)
                {
                    hasColumnFacet = header ? ((UIColumn) uiComponent).getHeader() != null : ((UIColumn) uiComponent)
                            .getFooter() != null;
                }
            }    
        }

        UIComponent facet = header ? (UIComponent) component.getFacets().get(HEADER_FACET_NAME)
                : (UIComponent) component.getFacets().get(FOOTER_FACET_NAME);
        if (facet != null || hasColumnFacet)
        {
            // Header or Footer present
            String elemName = header ? HTML.THEAD_ELEM : HTML.TFOOT_ELEM;

            HtmlRendererUtils.writePrettyLineSeparator(facesContext);
            writer.startElement(elemName, component);
            if (header)
            {
                String headerStyleClass = getHeaderClass(component);
                if (facet != null)
                    renderTableHeaderRow(facesContext, writer, component, facet, headerStyleClass, colspan);
                if (hasColumnFacet)
                    renderColumnHeaderRow(facesContext, writer, component, headerStyleClass, maxLevel);
            }
            else
            {
                String footerStyleClass = getFooterClass(component);
                if (hasColumnFacet)
                    renderColumnFooterRow(facesContext, writer, component, footerStyleClass, maxLevel);
                if (facet != null)
                    renderTableFooterRow(facesContext, writer, component, facet, footerStyleClass, colspan);
            }
            writer.endElement(elemName);
        }
    }
    
    protected void renderColumnHeaderRow(FacesContext facesContext, ResponseWriter writer, UIComponent component,
            String headerStyleClass, int maxLevel) throws IOException
    {
        renderColumnHeaderOrFooterRow(facesContext, writer, component, headerStyleClass, true, maxLevel);
    }

    protected void renderColumnFooterRow(FacesContext facesContext, ResponseWriter writer, UIComponent component,
            String footerStyleClass, int maxLevel) throws IOException
    {
        renderColumnHeaderOrFooterRow(facesContext, writer, component, footerStyleClass, false, maxLevel);
    }
    
    private void renderColumnHeaderOrFooterRow(FacesContext facesContext, ResponseWriter writer, UIComponent component,
            String styleClass, boolean header, int maxLevel) throws IOException
    {
        HtmlRendererUtils.writePrettyLineSeparator(facesContext);
        writer.startElement(HTML.TR_ELEM, component);
        for (Iterator it = component.getChildren().iterator(); it.hasNext();)
        {
            UIComponent uiComponent = (UIComponent) it.next();
            if ((uiComponent.getFamily().equals(UIColumn.COMPONENT_FAMILY))
                    && ((UIColumn) uiComponent).isRendered())
            {
                if (header)
                {
                    renderColumnHeaderCell(facesContext, writer, (UIColumn) uiComponent, styleClass, 0);
                }
                else
                {
                    renderColumnFooterCell(facesContext, writer, (UIColumn) uiComponent, styleClass, 0);
                }
            }
            else if ((uiComponent.getFamily().equals(HtmlTreeColumn.COMPONENT_FAMILY))
                    && ((HtmlTreeColumn) uiComponent).isRendered())
            {
                if (header)
                {
                    renderColumnHeaderCell(facesContext, writer, (UIColumn) uiComponent, styleClass, maxLevel + 3);
                }
                else
                {
                    renderColumnFooterCell(facesContext, writer, (UIColumn) uiComponent, styleClass, maxLevel + 3);
                }
            }        
        }
        writer.endElement(HTML.TR_ELEM);
    }

    /**
     * <p>
     * Renders the children.
     * </p>
     * 
     * @param facesContext The facesContext.
     * @param writer The writer.
     * @param tree The tree component.
     * @param children The children to render.
     * @param maxLevel The maximum level.
     * @param iconProvider The icon provider.
     * @throws IOException Throws an IOException.
     */
    protected void renderChildren(FacesContext facesContext, ResponseWriter writer, HtmlTree tree, List children,
            int maxLevel, IconProvider iconProvider) throws IOException
    {
        renderChildren(facesContext, writer, tree, children, maxLevel, iconProvider, 0);
    }

    /**
     * <p>
     * Renders the children given the rowClassIndex.
     * </p>
     * 
     * @param facesContext The facesContext.
     * @param writer The writer.
     * @param tree The tree component.
     * @param children The children to render.
     * @param maxLevel The maximum level.
     * @param iconProvider The icon provider.
     * @param rowClassIndex The row class index.
     * @throws IOException Throws an IOException.
     */
    protected void renderChildren(FacesContext facesContext, ResponseWriter writer, HtmlTree tree, List children,
            int maxLevel, IconProvider iconProvider, int rowClassIndex) throws IOException
    {
        String rowClasses = tree.getRowClasses();
        String columnClasses = tree.getColumnClasses();

        String[] rowClassesArray = (rowClasses == null) ? ArrayUtils.EMPTY_STRING_ARRAY : StringUtils.trim(StringUtils
                .splitShortString(rowClasses, ','));
        int rowClassesCount = rowClassesArray.length;

        String[] columnClassesArray = (columnClasses == null) ? ArrayUtils.EMPTY_STRING_ARRAY : StringUtils
                .trim(StringUtils.splitShortString(columnClasses, ','));
        int columnClassesCount = columnClassesArray.length;
        int columnClassIndex = 0;

        for (Iterator it = children.iterator(); it.hasNext();)
        {
            HtmlTreeNode child = (HtmlTreeNode) it.next();

            if (!child.isRendered())
            {
                continue;
            }
            HtmlRendererUtils.writePrettyLineSeparator(facesContext);

            writer.startElement(HTML.TR_ELEM, null);

            if (rowClassIndex < rowClassesCount)
            {
                writer.writeAttribute(HTML.CLASS_ATTR, rowClassesArray[rowClassIndex], null);
            }
            if (rowClassesCount > 0)
            {
                rowClassIndex++;
                rowClassIndex = rowClassIndex % rowClassesCount;
            }

            if (null != tree.getVar())
            {
                facesContext.getExternalContext().getRequestMap().put(tree.getVar(),
                        ((TreeNode) child.getUserObject()).getUserObject());
            }

            List componentChildren = tree.getChildren();
            if ((null != componentChildren) && (componentChildren.size() > 0))
            {
                for (int j = 0, size = tree.getChildCount(); j < size; j++)
                {
                    UIComponent componentChild = (UIComponent) componentChildren.get(j);
                    if ((componentChild.getFamily().equals(UIColumn.COMPONENT_FAMILY))
                            && ((UIColumn) componentChild).isRendered())
                    {
                        writer.startElement(HTML.TD_ELEM, tree);
                        if (columnClassIndex < columnClassesCount)
                        {
                            writer.writeAttribute(HTML.CLASS_ATTR, columnClassesArray[columnClassIndex], null);
                        }
                        if (columnClassesCount > 0)
                        {
                            columnClassIndex++;
                            columnClassIndex = columnClassIndex % columnClassesCount;
                        }
                        RendererUtils.renderChild(facesContext, componentChild);
                        writer.endElement(HTML.TD_ELEM);
                    }
                    else if ((componentChild.getFamily().equals(HtmlTreeColumn.COMPONENT_FAMILY))
                            && ((HtmlTreeColumn) componentChild).isRendered())
                    {
                        renderTreeColumnChild(facesContext, writer, componentChild, tree, child, maxLevel, iconProvider);
                    }
                }
            }
            else
            {
                renderTreeColumnChild(facesContext, writer, null, tree, child, maxLevel, iconProvider);
            }

            writer.endElement(HTML.TR_ELEM);

            if (child.getChildCount() > 0)
            {
                renderChildren(facesContext, writer, tree, child.getChildren(), maxLevel, iconProvider, rowClassIndex);
                if (rowClassesCount > 0)
                {
                    rowClassIndex += (child.getChildCount() % rowClassesCount);
                    rowClassIndex = rowClassIndex % rowClassesCount;
                }
            }
        }
    }

    /**
     * <p>
     * Render the column where the tree is displayed.
     * </p>
     * 
     * @param facesContext The facesContext.
     * @param writer The writer.
     * @param component The component that will contain the tree. Null for
     *            default tree or {@link HtmlTreeColumn}for table rendering.
     * @param tree The tree,
     * @param child The tree node child.
     * @param maxLevel The max number of levels.
     * @param iconProvider The iconProvider.
     * @throws IOException Throws IOException.
     */
    protected void renderTreeColumnChild(FacesContext facesContext, ResponseWriter writer, UIComponent component,
            HtmlTree tree, HtmlTreeNode child, int maxLevel, IconProvider iconProvider) throws IOException
    {
        String iconClass = tree.getIconClass();
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
                }
                else
                {
                    src = url;
                }
                //Encode URL
                //Although this is an url url, encodeURL is no nonsense,
                // because the
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

        if (state == HtmlTreeNode.CHILD || state == HtmlTreeNode.CHILD_FIRST || state == HtmlTreeNode.CHILD_LAST)
        {
            // no action, just img
            writeImageElement(url, facesContext, writer, child);
        }
        else
        {
            HtmlTreeImageCommandLink expandCollapse = (HtmlTreeImageCommandLink) child
                    .getExpandCollapseCommand(facesContext);
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
        }
        else
        {
            if (!child.isLeaf(facesContext))
            {
                // todo: icon provider
                url = "images/tree/folder.gif";
            }
            else
            {
                url = null;
            }
        }

        if ((url != null) && (url.length() > 0))
        {
            writer.startElement(HTML.TD_ELEM, null);
            if (iconClass != null)
            {
                writer.writeAttribute(HTML.CLASS_ATTR, iconClass, null);
            }
            writeImageElement(url, facesContext, writer, child);
            writer.endElement(HTML.TD_ELEM);
        }
        else
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
        }
        else if (!child.isSelected() && tree.getNodeClass() != null)
        {
            writer.writeAttribute(HTML.CLASS_ATTR, tree.getNodeClass(), null);
        }

        List componentChildren = null;
        if (null != component)
        {
            componentChildren = component.getChildren();
        }
        if ((null != componentChildren) && (componentChildren.size() > 0))
        {
            for (int k = 0; k < componentChildren.size(); k++)
            {
                RendererUtils.renderChild(facesContext, (UIComponent) componentChildren.get(k));
            }
        }
        else
        {
            child.encodeBegin(facesContext);
            child.encodeEnd(facesContext);
        }
        writer.endElement(HTML.TD_ELEM);
    }

    private void writeImageElement(String url, FacesContext facesContext, ResponseWriter writer, HtmlTreeNode child)
            throws IOException
    {
        writer.startElement(HTML.IMG_ELEM, child);
        String src = facesContext.getApplication().getViewHandler().getResourceURL(facesContext, url);
        //        if (url.startsWith(HTML.HREF_PATH_SEPARATOR))
        //        {
        //            String path =
        // facesContext.getExternalContext().getRequestContextPath();
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
        case HtmlTreeNode.LINE:
            return tree.getIconLine();
        case HtmlTreeNode.EMPTY:
            return tree.getIconNoline();
        default:
            return tree.getIconNoline();
        }
    }
}