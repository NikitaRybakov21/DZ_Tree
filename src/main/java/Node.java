
public class Node<T extends Comparable<? super T>> {

    private final T value;
    private Node<T> leftChild;
    private Node<T> rightChild;

    public Node(T value) {
        this.value = value;
    }

    public T getValue() {
        return value;
    }

    public Node<T> getLeftChild() {
        return leftChild;
    }

    public Node<T> getRightChild() {
        return rightChild;
    }

    public void setLeftChild(Node<T> leftChild) {
        this.leftChild = leftChild;
    }

    public void setRightChild(Node<T> rightChild) {
        this.rightChild = rightChild;
    }

    public boolean isLeftChild(T value){
        return  value.compareTo(getValue()) < 0;
    }

    public boolean isList(){
        return leftChild == null && rightChild == null;
    }

    public boolean hasOnlyOneChild() {
        return (leftChild != null && rightChild == null) || (leftChild == null && rightChild != null);
    }
}

