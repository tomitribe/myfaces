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
package org.apache.myfaces.config;

import java.util.Iterator;

/**
 * Subsumes several unmarshalled faces config objects and presents a simple interface
 * to the combined configuration data.
 *
 * @author Manfred Geiler (latest modification by $Author$)
 * @version $Revision$ $Date$
 * $Log$
 * Revision 1.2  2004/10/13 11:50:59  matze
 * renamed packages to org.apache
 *
 * Revision 1.1  2004/07/07 00:25:05  o_rossmueller
 * tidy up config/confignew package (moved confignew classes to package config)
 *
 * Revision 1.3  2004/07/01 22:05:09  mwessendorf
 * ASF switch
 *
 * Revision 1.2  2004/06/16 23:02:23  o_rossmueller
 * merged confignew_branch
 *
 * Revision 1.1.2.1  2004/06/16 01:25:52  o_rossmueller
 * refactorings: FactoryFinder, decorator creation, dispenser (removed reverse order)
 * bug fixes
 * additional tests
 *
 * Revision 1.1  2004/05/17 14:28:27  manolito
 * new configuration concept
 *
 */
public interface FacesConfigDispenser
{
    /**
     * Add another unmarshalled faces config object.
     * @param facesConfig unmarshalled faces config object
     */
    public void feed(Object facesConfig);

    /**
     * Add another ApplicationFactory class name
     * @param factoryClassName a class name
     */
    public void feedApplicationFactory(String factoryClassName);

    /**
     * Add another FacesContextFactory class name
     * @param factoryClassName a class name
     */
    public void feedFacesContextFactory(String factoryClassName);

    /**
     * Add another LifecycleFactory class name
     * @param factoryClassName a class name
     */
    public void feedLifecycleFactory(String factoryClassName);

    /**
     * Add another RenderKitFactory class name
     * @param factoryClassName a class name
     */
    public void feedRenderKitFactory(String factoryClassName);



    /** @return Iterator over ApplicationFactory class names */
    public Iterator getApplicationFactoryIterator();

    /** @return Iterator over FacesContextFactory class names */
    public Iterator getFacesContextFactoryIterator();

    /** @return Iterator over LifecycleFactory class names */
    public Iterator getLifecycleFactoryIterator();

    /** @return Iterator over RenderKit factory class names */
    public Iterator getRenderKitFactoryIterator();


    /** @return Iterator over ActionListener class names (in reverse order!) */
    public Iterator getActionListenerIterator();

    /** @return the default render kit id */
    public String getDefaultRenderKitId();

    /** @return Iterator over message bundle names (in reverse order!) */
    public String getMessageBundle();

    /** @return Iterator over NavigationHandler class names */
    public Iterator getNavigationHandlerIterator();

    /** @return Iterator over ViewHandler class names */
    public Iterator getViewHandlerIterator();

    /** @return Iterator over StateManager class names*/
    public Iterator getStateManagerIterator();

    /** @return Iterator over PropertyResolver class names */
    public Iterator getPropertyResolverIterator();

    /** @return Iterator over VariableResolver class names  */
    public Iterator getVariableResolverIterator();

    /** @return the default locale name */
    public String getDefaultLocale();

    /** @return Iterator over supported locale names */
    public Iterator getSupportedLocalesIterator();


    /** @return Iterator over all defined component types */
    public Iterator getComponentTypes();

    /** @return component class that belongs to the given component type */
    public String getComponentClass(String componentType);


    /** @return Iterator over all defined converter ids */
    public Iterator getConverterIds();

    /** @return Iterator over all classes with an associated converter  */
    public Iterator getConverterClasses();

    /** @return converter class that belongs to the given converter id */
    public String getConverterClassById(String converterId);

    /** @return converter class that is associated with the given class name  */
    public String getConverterClassByClass(String className);


    /** @return Iterator over all defined validator ids */
    public Iterator getValidatorIds();

    /** @return validator class name that belongs to the given validator id */
    public String getValidatorClass(String validatorId);


    /**
     * @return Iterator over {@link org.apache.myfaces.config.element.ManagedBean ManagedBean}s
     */
    public Iterator getManagedBeans();

    /**
     * @return Iterator over {@link org.apache.myfaces.config.element.NavigationRule NavigationRule}s
     */
    public Iterator getNavigationRules();



    /** @return Iterator over all defined renderkit ids */
    public Iterator getRenderKitIds();

    /** @return renderkit class name for given renderkit id */
    public String getRenderKitClass(String renderKitId);

    /**
     * @return Iterator over {@link org.apache.myfaces.config.element.Renderer Renderer}s for the given renderKitId
     */
    public Iterator getRenderers(String renderKitId);


    /**
     * @return Iterator over {@link javax.faces.event.PhaseListener} implementation class names 
     */
    public Iterator getLifecyclePhaseListeners();
}
