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
package org.apache.myfaces.custom.tree.model;

import org.apache.myfaces.custom.tree.DefaultMutableTreeNode;
import org.apache.myfaces.custom.tree.MutableTreeNode;
import org.apache.myfaces.custom.tree.TreeNode;

import java.util.Iterator;
import java.util.Collection;
import java.util.LinkedList;


/**
 * @author <a href="mailto:oliver@rossmueller.com">Oliver Rossmueller</a>
 * @version $Revision$ $Date$
 *          $Log$
 *          Revision 1.7  2004/10/13 11:50:58  matze
 *          renamed packages to org.apache
 *
 *          Revision 1.6  2004/09/01 18:32:57  mwessendorf
 *          Organize Imports
 *
 *          Revision 1.5  2004/08/15 15:28:04  o_rossmueller
 *          new model listener handling to get modified from events which occur outside the scope of a tree request
 *
 *          Revision 1.4  2004/07/01 21:53:04  mwessendorf
 *          ASF switch
 *
 *          Revision 1.3  2004/05/05 00:18:56  o_rossmueller
 *          various fixes/modifications in model event handling and tree update
 *
 *          Revision 1.2  2004/05/04 00:28:17  o_rossmueller
 *          model event handling
 *
 *          Revision 1.1  2004/04/22 10:20:24  manolito
 *          tree component
 *
 */
public class DefaultTreeModel
        implements TreeModel
{

    private TreeNode root;
    private LinkedList listeners = new LinkedList();


    public DefaultTreeModel()
    {
        this(new DefaultMutableTreeNode("Root"));
    }


    public DefaultTreeModel(TreeNode root)
    {
        this.root = root;
    }


    public Object getRoot()
    {
        return root;
    }


    public Object getChild(Object parent, int index)
    {
        return ((TreeNode)parent).getChildAt(index);
    }


    public int getChildCount(Object parent)
    {
        return ((TreeNode)parent).getChildCount();
    }


    public boolean isLeaf(Object node)
    {
        return ((TreeNode)node).isLeaf();
    }


    public void valueForPathChanged(TreePath path, Object newValue)
    {
        MutableTreeNode node = (MutableTreeNode)path.getLastPathComponent();

        node.setUserObject(newValue);
    }


    public int getIndexOfChild(Object parent, Object child)
    {
        return ((TreeNode)parent).getIndex((TreeNode)child);
    }


    public Collection getTreeModelListeners()
    {
        return listeners;
    }


    /**
     * Invoke this method after you've changed how node is to be
     * represented in the tree.
     */
    public void nodeChanged(TreeNode node)
    {
        if (listeners.isEmpty())
        {
            // nobody cares
            return;
        }

        if (node != null)
        {
            TreeNode parent = node.getParent();

            if (parent != null)
            {
                int index = parent.getIndex(node);
                if (index != -1)
                {
                    int[] childIndices = new int[1];

                    childIndices[0] = index;
                    nodesChanged(parent, childIndices);
                }
            }
            else if (node == getRoot())
            {
                nodesChanged(node, null);
            }
        }
    }


    /**
     * Invoke this method after you've changed how the children identified by
     * childIndicies are to be represented in the tree.
     */
    public void nodesChanged(TreeNode node, int[] childIndices)
    {
        if (listeners.isEmpty())
        {
            // nobody cares
            return;
        }

        if (node != null)
        {
            if (childIndices != null)
            {
                int count = childIndices.length;

                if (count > 0)
                {
                    Object[] children = new Object[count];

                    for (int i = 0; i < count; i++)
                    {
                        children[i] = node.getChildAt(childIndices[i]);
                    }
                    fireTreeNodesChanged(this, getPathToRoot(node), childIndices, children);
                }
            }
            else if (node == root)
            {
                fireTreeNodesChanged(this, getPathToRoot(node), null, null);
            }
        }
    }


    /**
     * Invoke this method if you've totally changed the children of
     * node and its childrens children...  This will post a
     * treeStructureChanged event.
     */
    public void nodeStructureChanged(TreeNode node)
    {
        if (listeners.isEmpty())
        {
            // nobody cares
            return;
        }

        if (node != null)
        {
            fireTreeStructureChanged(this, getPathToRoot(node), null, null);
        }
    }


    /**
     * Invoke this method after you've inserted some TreeNodes into
     * node.  childIndices should be the index of the new elements and
     * must be sorted in ascending order.
     */
    public void nodesWereInserted(TreeNode node, int[] childIndices)
    {
        if (listeners.isEmpty())
        {
            // nobody cares
            return;
        }
        if (node != null && childIndices != null && childIndices.length > 0)
        {
            int cCount = childIndices.length;
            Object[] newChildren = new Object[cCount];

            for (int counter = 0; counter < cCount; counter++)
            {
                newChildren[counter] = node.getChildAt(childIndices[counter]);
            }
            fireTreeNodesInserted(this, getPathToRoot(node), childIndices,
                                  newChildren);
        }
    }


    /**
     * Invoke this method after you've removed some TreeNodes from
     * node.  childIndices should be the index of the removed elements and
     * must be sorted in ascending order. And removedChildren should be
     * the array of the children objects that were removed.
     */
    public void nodesWereRemoved(TreeNode node, int[] childIndices, Object[] removedChildren)
    {
        if (listeners.isEmpty())
        {
            // nobody cares
            return;
        }
        if (node != null && childIndices != null)
        {
            fireTreeNodesRemoved(this, getPathToRoot(node), childIndices, removedChildren);
        }
    }


    /**
     * Collect all parent nodes up to the root node.
     *
     * @param node the TreeNode to get the path for
     */
    public TreeNode[] getPathToRoot(TreeNode node)
    {
        return getPathToRoot(node, 0);
    }


    /**
     * Recursivly collect parent nodes up the the root node.
     *
     * @param node  the TreeNode to get the path for
     * @param depth number of steps already taken towards the root (on recursive calls)
     * @return an array giving the path from the root to the specified node
     */
    protected TreeNode[] getPathToRoot(TreeNode node, int depth)
    {
        TreeNode[] answer;

        if (node == null)
        {
            if (depth == 0)
            {
                // nothing to do
                return null;
            }
            else
            {
                // end recursion
                answer = new TreeNode[depth];
            }
        }
        else
        {
            // recurse
            depth++;

            if (node == root)
            {
                // we found the root node
                answer = new TreeNode[depth];
            }
            else
            {
                answer = getPathToRoot(node.getParent(), depth);
            }
            answer[answer.length - depth] = node;
        }
        return answer;
    }


    /**
     * Notify all listeners of a node change.
     *
     * @param source       the node being changed
     * @param path         the path to the root node
     * @param childIndices the indices of the changed elements
     * @param children     the changed elements
     */
    protected void fireTreeNodesChanged(Object source, Object[] path, int[] childIndices, Object[] children)
    {
        TreeModelEvent event = null;
        for (Iterator iterator = listeners.iterator(); iterator.hasNext();)
        {
            TreeModelListener listener = (TreeModelListener)iterator.next();
            if (event == null)
            {
                event = new TreeModelEvent(source, path, childIndices, children);
            }
            listener.treeNodesChanged(event);
        }
    }


    /**
     * Notify all listeners of structure change.
     *
     * @param source       the node where new elements are being inserted
     * @param path         the path to the root node
     * @param childIndices the indices of the new elements
     * @param children     the new elements
     */
    protected void fireTreeNodesInserted(Object source, Object[] path, int[] childIndices, Object[] children)
    {
        TreeModelEvent event = null;
        for (Iterator iterator = listeners.iterator(); iterator.hasNext();)
        {
            TreeModelListener listener = (TreeModelListener)iterator.next();
            if (event == null)
            {
                event = new TreeModelEvent(source, path, childIndices, children);
            }
            listener.treeNodesInserted(event);
        }
    }


    /**
     * Notify all listeners of structure change.
     *
     * @param source       the node where elements are being removed
     * @param path         the path to the root node
     * @param childIndices the indices of the removed elements
     * @param children     the removed elements
     */
    protected void fireTreeNodesRemoved(Object source, Object[] path, int[] childIndices, Object[] children)
    {
        TreeModelEvent event = null;
        for (Iterator iterator = listeners.iterator(); iterator.hasNext();)
        {
            TreeModelListener listener = (TreeModelListener)iterator.next();
            if (event == null)
            {
                event = new TreeModelEvent(source, path, childIndices, children);
            }
            listener.treeNodesRemoved(event);
        }
    }


    /**
     * Notify all listeners of structure change.
     *
     * @param source       the node where the tree model has changed
     * @param path         the path to the root node
     * @param childIndices the indices of the affected elements
     * @param children     the affected elements
     */
    protected void fireTreeStructureChanged(Object source, Object[] path, int[] childIndices, Object[] children)
    {
        TreeModelEvent event = null;
        for (Iterator iterator = listeners.iterator(); iterator.hasNext();)
        {
            TreeModelListener listener = (TreeModelListener)iterator.next();
            if (event == null)
            {
                event = new TreeModelEvent(source, path, childIndices, children);
            }
            listener.treeStructureChanged(event);
        }
    }


    /**
     * Notify all listeners of structure change.
     *
     * @param source the node where the tree model has changed
     * @param path   the path to the root node
     */
    protected void fireTreeStructureChanged(Object source, TreePath path)
    {
        TreeModelEvent event = null;
        for (Iterator iterator = listeners.iterator(); iterator.hasNext();)
        {
            TreeModelListener listener = (TreeModelListener)iterator.next();
            if (event == null)
            {
                event = new TreeModelEvent(source, path);
            }
            listener.treeStructureChanged(event);
        }
    }

}
