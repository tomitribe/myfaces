<%@ page import="org.apache.myfaces.custom.tree.DefaultMutableTreeNode,
                 org.apache.myfaces.custom.tree.model.DefaultTreeModel"%>
<%@ page session="true" contentType="text/html;charset=utf-8"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://myfaces.apache.org/extensions" prefix="x"%>
<html>

<!--
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
//-->

<%@include file="inc/head.inc" %>

<body>

<%
   if (pageContext.getAttribute("treeModel", PageContext.SESSION_SCOPE) == null) {
      DefaultMutableTreeNode root = new DefaultMutableTreeNode("XY");
      DefaultMutableTreeNode a = new DefaultMutableTreeNode("A");
      root.insert(a);
      DefaultMutableTreeNode b = new DefaultMutableTreeNode("B");
      root.insert(b);
      DefaultMutableTreeNode c = new DefaultMutableTreeNode("C");
      root.insert(c);

      DefaultMutableTreeNode node = new DefaultMutableTreeNode("a1");
      a.insert(node);
      node = new DefaultMutableTreeNode("a2 ");
      a.insert(node);
      node = new DefaultMutableTreeNode("b ");
      b.insert(node);

      a = node;
      node = new DefaultMutableTreeNode("x1");
      a.insert(node);
      node = new DefaultMutableTreeNode("x2");
      a.insert(node);

      pageContext.setAttribute("treeModel", new DefaultTreeModel(root), PageContext.SESSION_SCOPE);
   }
%>

<f:view>

    <f:loadBundle basename="org.apache.myfaces.examples.resource.example_messages" var="example_messages"/>

    <x:panelLayout id="page" layout="#{globalOptions.pageLayout}"
            styleClass="pageLayout"
            headerClass="pageHeader"
            navigationClass="pageNavigation"
            bodyClass="pageBody"
            footerClass="pageFooter" >

        <f:facet name="header">
            <f:subview id="header">
                <jsp:include page="inc/page_header.jsp" />
            </f:subview>
        </f:facet>

        <f:facet name="navigation">
            <f:subview id="menu" >
                <jsp:include page="inc/navigation.jsp" />
            </f:subview>
        </f:facet>

        <f:facet name="body">
            <h:panelGroup id="body">

                <x:tree id="tree" value="#{treeModel}"
                        styleClass="tree"
                        nodeClass="treenode"
                        selectedNodeClass="treenodeSelected"
                        expandRoot="true">
                </x:tree>
                <f:verbatim><br></f:verbatim>

            </h:panelGroup>
        </f:facet>

        <%@include file="inc/page_footer.jsp" %>

    </x:panelLayout>

</f:view>

</body>

</html>