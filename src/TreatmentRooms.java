public class TreatmentRooms {
  private Semaphore semaphore = new Semaphore(2);

    public TreatmentRooms() {
    }
    public boolean requireRoom(int doctorId,long timeoutMillis){
        System.out.println("Doctor"+doctorId + " is trying to acquire a treatment room.");
        return semaphore.tryAcquire(timeoutMillis);
    }

  public boolean requireRoom(int doctorId, long timeoutMillis) {
    System.out.println("Doctor " + doctorId + " is trying to acquire a treatment room.");
    return semaphore.tryAcquire(timeoutMillis);
  }

  public void letRoom() {
    semaphore.V();
  }
}
