import java.util.LinkedList;
import java.util.Queue;

public class WaitingRoom {
    private final int capacity;
    private final Queue<Patient> queue = new LinkedList<>();

    Semaphore spaces;
    Semaphore elements;
    Semaphore mutex;

    public WaitingRoom(int capacity) {
        this.capacity = capacity;
        spaces = new Semaphore(capacity);
        elements = new Semaphore(0);
        mutex = new Semaphore(1);
    }

    public boolean isFull() {
        return queue.size() == capacity;
    }

    public void enterWaitingRoom(Patient patient) {

        boolean isAcquired;

        isAcquired = spaces.tryAcquire(0);

        if (isAcquired){

            mutex.P();
            System.out.println("P" + patient.getPatientId() + " entered waiting room");

            queue.add(patient);

            mutex.V();
            elements.V();
        }   
        else
            System.out.println("the waiting room is full\nP" + patient.getPatientId() + " is waiting outside");
        
        
    }

    public Patient fetchNextPatient() {
        elements.P();
        mutex.P();

        Patient p = queue.remove();
        mutex.V();
        spaces.V();

        return p;
    }
    public void returnPatient(Patient patient) throws InterruptedException {
        return; // to be continued
    }
}
