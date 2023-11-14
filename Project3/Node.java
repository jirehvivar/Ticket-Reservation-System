public class Node<T> {
    private Node<T> next;
    private Node<T> down;
    private T payLoad;
    private Node<T> prev;
    public Node(){
        
    }

    public Node(T data, Node<T> nextR, Node<T> downC, Node<T> prev) {
        // overloader constructor
        this.payLoad = data;
        next = nextR;
        down = downC;
        this.prev = prev;
    }
    // Mutators
    public void setNext(Node<T> next) {
        this.next = next;
    }

    public void setDown(Node<T> down) {
        this.down = down;
    }

    public void setPayload(T data){
        this.payLoad=data;
    }

    public void setPrev(Node<T> prev){
        this.prev=prev;
    }

    // Accessors

    public Node<T> getNext() {
        return next;
    }

    public Node<T> getDown() {
        return down;
    }

    public T getPayload(){
        return payLoad;
    }
    public Node<T> getPrev(){
        return prev;
    }
    
    @Override
    public String toString(){
        return  ((Seat)payLoad).toString() ;
    }
}
