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
package org.apache.myfaces.custom.tree;

/**
 * Defines the requirements for a tree node object that can change -- by adding or removing
 * child nodes, or by changing the contents of a user object stored in the node.
 * (inspired by javax.swing.tree.MutableTreeNode).
 *
 * @author <a href="mailto:oliver@rossmueller.com">Oliver Rossmueller</a>
 * @version $Revision$ $Date$
 *          $Log$
 *          Revision 1.3  2004/10/13 11:50:58  matze
 *          renamed packages to org.apache
 *
 *          Revision 1.2  2004/07/01 21:53:07  mwessendorf
 *          ASF switch
 *
 *          Revision 1.1  2004/04/22 10:20:23  manolito
 *          tree component
 *
 */
public interface MutableTreeNode
        extends TreeNode
{

    /**
     * Add the given child to the children of this node.
     * This will set this node as the parent of the child using {#setParent}.
     */
    void insert(MutableTreeNode child);


    /**
     * Add the given child to the children of this node at index.
     * This will set this node as the parent of the child using {#setParent}.
     */
    void insert(MutableTreeNode child, int index);


    /**
     * Remove the child at the given index.
     */
    void remove(int index);


    /**
     * Remove the given node.
     */
    void remove(MutableTreeNode node);


    /**
     * Sets the user object of this node.
     */
    void setUserObject(Object object);


    /**
     * Remove this node from its parent.
     */
    void removeFromParent();


    /**
     * Set the parent node.
     */
    void setParent(MutableTreeNode parent);
}
