public class Patient implements Runnable{
    private final int patientId;
    private final WaitingRoom waitingRoom;

    public Patient(int patientId, WaitingRoom waitingRoom) {
        this.patientId=patientId;
        this.waitingRoom=waitingRoom;
    }

    @Override
    public void run() {
        System.out.println("P"+patientId+ " arrived.");
        waitingRoom.enterWaitingRoom(this);

    }

    public int getPatientId() {
        return patientId;
    }
}
