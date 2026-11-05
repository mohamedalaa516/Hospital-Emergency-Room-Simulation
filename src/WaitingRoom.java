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

    public void enterWaitingRoom(Patient patient) {
        mutex.P();
        if(queue.size()>= capacity){
            System.out.println("The waiting room is full.");
            System.out.println("P"+patient.getPatientId()+" is waiting outside for an available seat.");
        }
        mutex.V();
        spaces.P();
        mutex.P();
        queue.add(patient);
        System.out.println("P" + patient.getPatientId() + " entered waiting room");
        mutex.V();
        elements.V(); 

    }
  }

    public Patient fetchNextPatient() {
        elements.P();
        mutex.P();
        Patient p = queue.poll();
        mutex.V();
        if(p!=null){
            spaces.V();
        }


        return p;
    }
    public void StopRemainingDoctors(int doctorcount){
        for(int i=0;i<doctorcount;i++){
            elements.V();
        }
    }
}
