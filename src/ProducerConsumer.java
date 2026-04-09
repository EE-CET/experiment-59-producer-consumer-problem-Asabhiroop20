class SharedResource {
    int item;
    boolean available = false;

    synchronized void produce(int value) {
        try {
            while (available) {
                wait();
            }
            item = value;
            System.out.println("Produced: " + item);
            available = true;
            notify();
        } catch (InterruptedException e) {
        }
    }

    synchronized void consume() {
        try {
            while (!available) {
                wait();
            }
            System.out.println("Consumed: " + item);
            available = false;
            notify();
        } catch (InterruptedException e) {
        }
    }
}

class Producer extends Thread {
    SharedResource resource;

    Producer(SharedResource resource) {
        this.resource = resource;
    }

    public void run() {
        for (int i = 1; i <= 5; i++) {
            resource.produce(i);
        }
    }
}

class Consumer extends Thread {
    SharedResource resource;

    Consumer(SharedResource resource) {
        this.resource = resource;
    }

    public void run() {
        for (int i = 1; i <= 5; i++) {
            resource.consume();
        }
    }
}

public class ProducerConsumer {
    public static void main(String[] args) {
        SharedResource resource = new SharedResource();

        Producer p = new Producer(resource);
        Consumer c = new Consumer(resource);

        p.start();
        c.start();
    }
}