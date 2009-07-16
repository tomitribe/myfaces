/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.apache.myfaces.application;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.el.ExpressionFactory;
import javax.el.ValueExpression;
import javax.faces.FacesException;
import javax.faces.application.NavigationHandler;
import javax.faces.application.ViewHandler;
import javax.faces.component.UIViewRoot;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.myfaces.config.RuntimeConfig;
import org.apache.myfaces.config.element.NavigationCase;
import org.apache.myfaces.config.element.NavigationRule;
import org.apache.myfaces.shared_impl.util.HashMapUtils;

/**
 * @author Thomas Spiegl (latest modification by $Author$)
 * @author Anton Koinov
 * @version $Revision$ $Date$
 */
public class NavigationHandlerImpl
    extends NavigationHandler
{
    private static final Log log = LogFactory.getLog(NavigationHandlerImpl.class);

    private static final String ASTERISK = "*";

    private Map<String, List<NavigationCase>> _navigationCases = null;
    private List<String> _wildcardKeys = new ArrayList<String>();

    public NavigationHandlerImpl()
    {
        if (log.isTraceEnabled()) log.trace("New NavigationHandler instance created");
    }

    @Override
    public void handleNavigation(FacesContext facesContext, String fromAction, String outcome)
    {
        if (outcome == null)
        {
            // stay on current ViewRoot
            return;
        }

        NavigationCase navigationCase = getNavigationCase(facesContext, fromAction, outcome);

        if (navigationCase != null)
        {
            if (log.isTraceEnabled())
            {
                log.trace("handleNavigation fromAction=" + fromAction + " outcome=" + outcome +
                          " toViewId =" + navigationCase.getToViewId() +
                          " redirect=" + navigationCase.isRedirect());
            }
            if (navigationCase.isRedirect())
            { 
                //&& (!PortletUtil.isPortletRequest(facesContext)))
                // Spec section 7.4.2 says "redirects not possible" in this case for portlets
                //But since the introduction of portlet bridge and the 
                //removal of portlet code in myfaces core 2.0, this condition
                //no longer applies
                
                ExternalContext externalContext = facesContext.getExternalContext();
                ViewHandler viewHandler = facesContext.getApplication().getViewHandler();
                String redirectPath = viewHandler.getActionURL(facesContext, navigationCase.getToViewId());
                
                // JSF 2.0 Spec call Flash.setRedirect(true) to notify Flash scope and take proper actions
                externalContext.getFlash().setRedirect(true);
                try
                {
                    externalContext.redirect(externalContext.encodeActionURL(redirectPath));
                }
                catch (IOException e)
                {
                    throw new FacesException(e.getMessage(), e);
                }
            }
            else
            {
                ViewHandler viewHandler = facesContext.getApplication().getViewHandler();
                //create new view
                String newViewId = navigationCase.getToViewId();
                UIViewRoot viewRoot = viewHandler.createView(facesContext, newViewId);
                facesContext.setViewRoot(viewRoot);
                facesContext.renderResponse();
            }
        }
        else
        {
            // no navigationcase found, stay on current ViewRoot
            if (log.isTraceEnabled())
            {
                log.trace("handleNavigation fromAction=" + fromAction + " outcome=" + outcome +
                          " no matching navigation-case found, staying on current ViewRoot");
            }
        }
    }


    /**
     * Returns the navigation case that applies for the given action and outcome
     */
    public NavigationCase getNavigationCase(FacesContext facesContext, String fromAction, String outcome)
    {
        String viewId = facesContext.getViewRoot().getViewId();
        Map<String, List<NavigationCase>> casesMap = getNavigationCases(facesContext);
        NavigationCase navigationCase = null;

        List<? extends NavigationCase> casesList = casesMap.get(viewId);
        if (casesList != null)
        {
            // Exact match?
            navigationCase = calcMatchingNavigationCase(facesContext, casesList, fromAction, outcome);
        }

        if (navigationCase == null)
        {
            // Wildcard match?
            for (String fromViewId : getSortedWildcardKeys())
            {
                if (fromViewId.length() > 2)
                {
                    String prefix = fromViewId.substring(0, fromViewId.length() - 1);
                    if (viewId != null && viewId.startsWith(prefix))
                    {
                        casesList = casesMap.get(fromViewId);
                        if (casesList != null)
                        {
                            navigationCase = calcMatchingNavigationCase(facesContext, casesList, fromAction, outcome);
                            if (navigationCase != null) break;
                        }
                    }
                }
                else
                {
                    casesList = casesMap.get(fromViewId);
                    if (casesList != null)
                    {
                        navigationCase = calcMatchingNavigationCase(facesContext, casesList, fromAction, outcome);
                        if (navigationCase != null) break;
                    }
                }
            }
        }
        
        return navigationCase;
    }

    /**
     * Returns the view ID that would be created for the given action and outcome
     */
    public String getViewId(FacesContext context, String fromAction, String outcome)
    {
        return this.getNavigationCase(context, fromAction, outcome).getToViewId();
    }

    /**
     * TODO
     * Invoked by the navigation handler before the new view component is created.
     * @param viewId The view ID to be created
     * @return The view ID that should be used instead. If null, the view ID passed
     * in will be used without modification.
     */
    public String beforeNavigation(String viewId)
    {
        return null;
    }

    private NavigationCase calcMatchingNavigationCase(FacesContext context, List<? extends NavigationCase> casesList, String actionRef, 
                                                      String outcome)
    {
        for (NavigationCase caze : casesList)
        {
            String cazeOutcome = caze.getFromOutcome();
            String cazeActionRef = caze.getFromAction();
            String cazeIf = caze.getIf();
            ExpressionFactory expFactory = context.getApplication().getExpressionFactory();
            boolean ifMatches = false;

            // JSF 2.0: support conditional navigation via <if>.
            
            // Use for later cases.
            
            if (cazeIf != null) {
                ValueExpression ifExpr = expFactory.createValueExpression (context.getELContext(), caze.getIf(), Boolean.class);
                Boolean value = (Boolean) ifExpr.getValue (context.getELContext());
                
                ifMatches = value.booleanValue();
            }
            
            if (cazeActionRef != null) {
                if (cazeOutcome != null) {
                    if ((actionRef != null) && (outcome != null) && cazeActionRef.equals (actionRef) &&
                        cazeOutcome.equals (outcome)) {
                        // First case: match if <from-action> matches action and <from-outcome> matches outcome.
                        // Caveat: evaluate <if> if available.
                        
                        if (cazeIf != null) {
                            return (ifMatches ? caze : null);
                        }
                        
                        else {
                            return caze;
                        }
                    }
                }
                
                else {
                    if ((actionRef != null) && cazeActionRef.equals (actionRef)) {
                        // Third case: if only <from-action> specified, match against action.
                        // Caveat: if <if> is available, evaluate.  If not, only match if outcome is not null.
                        
                        if (cazeIf != null) {
                            return (ifMatches ? caze : null);
                        }
                        
                        else {
                            return ((outcome != null) ? caze : null);
                        }
                    }
                }
            }
            
            else {
                if (cazeOutcome != null) {
                    if ((outcome != null) && cazeOutcome.equals (outcome)) {
                        // Second case: if only <from-outcome> specified, match against outcome.
                        // Caveat: if <if> is available, evaluate.
                        
                        if (cazeIf != null) {
                            return (ifMatches ? caze : null);
                        }
                        
                        else {
                            return caze;
                        }
                    }
                }
            }
            
            // Fourth case: anything else matches if outcome is not null or <if> is specified.
            
            if (outcome != null) {
                // Again, if <if> present, evaluate.
                
                if (cazeIf != null) {
                    return (ifMatches ? caze : null);
                }
                
                else {
                    return caze;
                }
            }
            
            if ((cazeIf != null) && ifMatches) {
                return caze;
            }
        }
        
        return null;
    }

    private List<String> getSortedWildcardKeys()
    {
        return _wildcardKeys;
    }

    private Map<String, List<NavigationCase>> getNavigationCases(FacesContext facesContext)
    {
        ExternalContext externalContext = facesContext.getExternalContext();
        RuntimeConfig runtimeConfig = RuntimeConfig.getCurrentInstance(externalContext);

        if (_navigationCases == null || runtimeConfig.isNavigationRulesChanged())
        {
            synchronized(this)
            {
                if (_navigationCases == null || runtimeConfig.isNavigationRulesChanged())
                {
                    Collection<? extends NavigationRule> rules = runtimeConfig.getNavigationRules();
                    int rulesSize = rules.size();
                    
                    Map<String, List<NavigationCase>> cases = 
                        new HashMap<String, List<NavigationCase>>(HashMapUtils.calcCapacity(rulesSize));
                    
                    List<String> wildcardKeys = new ArrayList<String>();

                    for (NavigationRule rule : rules)
                    {
                        String fromViewId = rule.getFromViewId();

                        //specification 7.4.2 footnote 4 - missing fromViewId is allowed:
                        if (fromViewId == null)
                        {
                            fromViewId = ASTERISK;
                        }
                        else
                        {
                            fromViewId = fromViewId.trim();
                        }

                        List<NavigationCase> list = cases.get(fromViewId);
                        if (list == null)
                        {
                            list = new ArrayList<NavigationCase>(rule.getNavigationCases());
                            cases.put(fromViewId, list);
                            if (fromViewId.endsWith(ASTERISK))
                            {
                                wildcardKeys.add(fromViewId);
                            }
                        }
                        else
                        {
                            list.addAll(rule.getNavigationCases());
                        }
                    }
                    
                    Collections.sort(wildcardKeys, new KeyComparator());

                    synchronized (cases)
                    {
                        // We do not really need this sychronization at all, but this
                        // gives us the peace of mind that some good optimizing compiler
                        // will not rearrange the execution of the assignment to an
                        // earlier time, before all init code completes
                        _navigationCases = cases;
                        _wildcardKeys = wildcardKeys;

                        runtimeConfig.setNavigationRulesChanged(false);
                    }
                }
            }
        }
        return _navigationCases;
    }

    private static final class KeyComparator implements Comparator<String>
    {
        public int compare(String s1, String s2)
        {
            return -s1.compareTo(s2);
        }
    }
}