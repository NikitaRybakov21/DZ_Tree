import java.util.Stack;

public class TreeImpl<E extends Comparable<? super E>> implements  Tree<E> {

    private Node<E> root;
    private int size;
    private int maxLvl;



    public TreeImpl(int mxLvl){
        this.maxLvl = mxLvl;
    }
//////////////////////////////////////////////////////////////////
    private class NodeAndParent{
        private  Node<E> current;
        private  Node<E> parent;
        private int lvl;

    public NodeAndParent(Node<E> current,Node<E> parent,int lvl) {
        this.current = current;
        this.parent = parent;
        this.lvl = lvl;
    }

    }
    @Override
    public boolean contains(E value) {
        NodeAndParent nodeAndParent = doFind(value);
        return nodeAndParent.current != null;
    }

    private NodeAndParent doFind(E value) {
        int lvl = 0;
        Node<E> current = root;
        Node<E> parent = null;

        while (current != null){
            if(current.getValue().equals(value)){
                return new NodeAndParent(current, parent,lvl);
            } else {
                parent = current;
                if(current.isLeftChild(value)){
                    current = current.getLeftChild();
                } else {
                    current = current.getRightChild();
                }
            }
            lvl++;
        }
        return new NodeAndParent(null, parent,lvl);
    }
/////////////////////////////////////////////////////////////////

    @Override
    public boolean isBalanced(Node<E> node) {
        return (node == null) ||
                isBalanced(node.getLeftChild()) &&
                        isBalanced(node.getRightChild()) &&
                        Math.abs(height(node.getLeftChild()) - height(node.getRightChild())) <= 1;
    }

    private int height(Node<E> node) {
        return node == null ? 0 : 1 + Math.max(height(node.getLeftChild()), height(node.getRightChild()));
    }

    @Override
    public Node<E> getRoot() {
        return this.root;
    }

    @Override
    public boolean add(E value) {
        Node<E> node = new Node<>(value);
        NodeAndParent nodeAndParent = doFind(value);

        if(nodeAndParent.current != null){
            return false;
        }

        if(nodeAndParent.lvl > maxLvl){
            return true;
        }
        Node<E> parent = nodeAndParent.parent;

        if(parent == null){
            root = node;
        } else  if(parent.isLeftChild(value)){
            parent.setLeftChild(node);
        } else  {
            parent.setRightChild(node);
        }
        size++;

        return true;
    }

    @Override
    public boolean remove(E value) {
        NodeAndParent nodeAndParent = doFind(value);
        Node<E> removedNode = nodeAndParent.current;
        Node<E> parentNode = nodeAndParent.parent;

        if(removedNode == null){
            return false;
        }

        if (removedNode.isList()) {
            removedLeafNode(removedNode,parentNode);

        } else if(removedNode.hasOnlyOneChild()){
            removedNodeWithOneChild(removedNode,parentNode);
        } else {
            removedNodeWithAllChildren(removedNode, parentNode);
        }
        size--;

        return true;
    }

    private void removedNodeWithAllChildren(Node<E> removedNode, Node<E> parentNode) {
        Node<E> successor = getSuccessor(removedNode);

        if(removedNode == root){
            root = successor;
        } else if(parentNode.isLeftChild(removedNode.getValue())){
            parentNode.setLeftChild(successor);
        } else {
            parentNode.setRightChild(successor);
        }
        successor.setLeftChild(removedNode.getLeftChild());
    }

    private Node<E> getSuccessor(Node<E> removedNode) {
        Node<E> successor = removedNode;
        Node<E> successorParent = null;
        Node<E> current = removedNode.getRightChild();

        while (current != null){
            successorParent = successor;
            successor = current;
            current = current.getLeftChild();
        }
        if(successor != removedNode.getRightChild() && successorParent != null){
            successorParent.setLeftChild(successor.getRightChild());
            successor.setRightChild(removedNode.getRightChild());
        }
        return successor;
    }

    private void removedNodeWithOneChild(Node<E> removedNode, Node<E> parentNode) {
        Node<E> childNode = removedNode.getLeftChild() != null
                ? removedNode.getLeftChild()
                : removedNode.getRightChild();
        if(removedNode == root){
            root = childNode;
        } else if(parentNode.isLeftChild(removedNode.getValue())){
            parentNode.setLeftChild(childNode);
        } else {
            parentNode.setRightChild(childNode);
        }
    }

    private void removedLeafNode(Node<E> removedNode, Node<E> parentNode) {
        if(removedNode == root){
            root = null;
        }
        else if(parentNode.isLeftChild((removedNode.getValue()))){
            parentNode.setLeftChild((null));
        } else {
            parentNode.setLeftChild(null);
        }
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public void display() {

    }

    @Override
    public void traverse(TraversMode mode) {
        switch(mode){
            case IN_ORDER -> inOrder(root);
            case POST_ORDER -> postOrder(root);
            case PRE_ORDER -> preOrder(root);
        }
    }

    private void postOrder(Node<E> current) {
        if(current == null){
            return;
        }
        postOrder(current.getLeftChild());
        postOrder(current.getRightChild());
        System.out.print(current.getValue() + " ");
    }
    private void preOrder(Node<E> current) {
        if(current == null){
            return;
        }
        System.out.print(current.getValue() + " ");
        preOrder(current.getLeftChild());
        preOrder(current.getRightChild());
    }
    private void inOrder(Node<E> current) {
        if(current == null){
            return;
        }
        inOrder(current.getLeftChild());
        System.out.print(current.getValue() + " ");
        inOrder(current.getRightChild());
    }
}

