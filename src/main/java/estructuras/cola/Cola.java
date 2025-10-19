package estructuras.cola;

//Define cuando inicia(head), cuando termina(tail), y la extension de la cola(size)
public class Cola {
    private Nodo head; //
    private Nodo tail; //
    private int size;  //

    //El constructor de la cola, que ejecuta una cola vacia al empezar
    public Cola() {
        head = null;
        tail = null;
        size = 0;
    }

    //Agregamos valores(nodos) a la cola, actualizando head y tail durante el proceso
    public void enqueue(int valor) {
        Nodo nuevo = new Nodo(valor);
        if (isEmpty()) {
            head = nuevo;
            tail = nuevo;
        } else {
            tail.sig = nuevo;
            tail = nuevo;
        }
        size++;
    }

    //Sirve para eliminar valores de la cola
    public void dequeue() {
        if (!isEmpty()) {
            head = head.sig;
            size--;
            if (head == null) {
                tail = null;
            }
        } else {
            System.out.println("Cola vacía");
        }
    }

    //Busqueda de los elementos de la cola, es decir no los elimina
    public int peek() {
        if (!isEmpty()) {
            return head.valor;
        } else {
            System.out.println("Cola vacía");
            return -1;
        }
    }

    //Determina si la cola tiene elementos o está vacia
    public boolean isEmpty() {
        return head == null;
    }

    //Indica el tamaño de la cola
    public int size() {
        return size;
    }

    //Muestra los valores de la cola de forma ordenada, siguiendo el principio FIFO
    public void printQueue() {
        Nodo actual = head;//Marca el inicio de la cola
        while (actual != null) {
            System.out.print(actual.valor + " ");
            actual = actual.sig;
        }
        System.out.println();
    }
}
