public class TreatmentRooms {
    private static final int TOTAL_ROOMS = 2;
    //private final Semaphore roomsSemaphore;

    public TreatmentRooms(Semaphore roomsSemaphore) {
    }
    public boolean tryAcquireRoom(long timeoutMillis){
        return true;
    }

    public void releaseRoom() {
    }

    public boolean requireRoom(int doctorId) {
        return true;
    }

    public void letRoom() {
    }
}
