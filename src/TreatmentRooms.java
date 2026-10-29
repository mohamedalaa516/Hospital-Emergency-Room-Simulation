public class TreatmentRooms {
    private static final int TOTAL_ROOMS = 2;
    private final Semaphore roomsSemaphore;

    public TreatmentRooms(Semaphore roomsSemaphore) {
    }
    public boolean tryAcquireRoom(long timeoutMillis){
    }

    public void releaseRoom() {
    }
}
