import java.util.LinkedList;
import java.util.Queue;

// *

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
    if (!spaces.tryAcquire(0)) {
      System.out.println("waiting room is full\nP" + patient.getPatientId() + " is waiting");
      spaces.P();
    }
    mutex.P();
    System.out.println("P" + patient.getPatientId() + " entered waiting room");
    queue.add(patient);
    mutex.V();
    elements.V();
  }

  public Patient fetchNextPatient() {
    elements.P();
    mutex.P();
    Patient p = queue.remove();
    mutex.V();
    spaces.V();
    return p;
  }

  public void shutdown(int doctorCount) {
    for (int i = 0; i < doctorCount; i++) {
      spaces.P();
      mutex.P();
      queue.add(Patient.createShutdownSignal());
      mutex.V();
      elements.V();
    }
  }

  public void returnPatient(Patient patient) throws InterruptedException {
    return; // to be continued
  }
}
