public class Patient implements Runnable{
    private final int patientId;
    private final WaitingRoom waitingRoom;
    private final Semaphore treatmentCompleted;

    public Patient(int patientId, WaitingRoom waitingRoom, Semaphore treatmentCompleted) {

    }

    @Override
    public void run() {

    }

    public void notifyTreatmentFinished() {

    }

    public int getPatientId() {
    }
}
