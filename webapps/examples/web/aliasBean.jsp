<%@ page import="java.util.Random"%>
<%@ page session="false" contentType="text/html;charset=utf-8"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://myfaces.apache.org/extensions" prefix="x"%>
<html>

<%@include file="inc/head.inc" %>

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

<body>

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

				<f:verbatim>
					<p>
						Let's suppose you have a subform you use often but with different beans.<br/>
						The aliasBean allows you to design the subform with a fictive bean
						and to include it in all the pages where you use it.
						You just need to make an alias to the real bean named after the fictive bean before invoking the fictive bean.
					</p>
					<p>
						In this example, the customerAddress bean is a managed bean, but the address bean isn't defined anywhere.<br/>
						After the aliasBean tag, we can use #{address.*} in place of #{custommerAddress.*}, so making it possible to have
						a generic address subforms (ok, this one it a bite simple form, but here is the idea).
					</p>
				</f:verbatim>
				

				<h:form>
					<x:aliasBean sourceBean="#{customerAddress}" alias="#{address}"/>
					<f:subview id="simulatedIncludedSubform">
							<%-- The next tag could be inserted by an %@ include or jsp:include --%>
	                        <h:inputText value="#{address}"/>
					</f:subview>

					<h:commandButton/>
				</h:form>

            </h:panelGroup>
        </f:facet>

        <%@include file="inc/page_footer.jsp" %>

    </x:panelLayout>

</f:view>

</body>

</html>