public class Patient implements Runnable {
  private final int patientId;
  private final Hospital hospital;
  private final boolean shutDownSignal;

  public Patient(int patientId, Hospital hospital) {
    this.patientId = patientId;
    this.hospital = hospital;
    this.shutDownSignal = false;
  }

  private Patient() {
    this.patientId = -1;
    this.hospital = null;
    this.shutDownSignal = true;
  }

  public static Patient createShutdownSignal() {
    return new Patient();
  }

  public boolean isShutdownSignal() {
    return shutDownSignal;
  }

  @Override
  public void run() {
    System.out.println("P" + patientId + " arrived.");
    hospital.getWaitingRoom().enterWaitingRoom(this);
  }

  public int getPatientId() {
    return patientId;
  }
}
