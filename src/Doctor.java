import java.util.Random;

public class Doctor implements Runnable {

  private final int doctorId;
  private final Hospital hospital;

  public Doctor(int doctorId, Hospital hospital) {
    this.doctorId = doctorId;
    this.hospital = hospital;
  }

  @Override
  public void run() {
    while (hospital.hasPatients()) {
      Patient patient = hospital.getWaitingRoom().fetchNextPatient();
      System.out.println(
          "Doctor " + doctorId + " is assigned to " + "P" + patient.getPatientId() + ".");
      if (hospital.getTreatmentRooms().requireRoom(doctorId, 3000)) {
        System.out.println("Doctor " + doctorId + " acquired a treatment room.");
        System.out.println(
            "Doctor " + doctorId + " begins treating " + "P" + patient.getPatientId() + ".");
        treatPatient();
        System.out.println(
            "Doctor " + doctorId + " finished treating " + "P" + patient.getPatientId() + ".");
        hospital.getTreatmentRooms().letRoom();
        System.out.println("Doctor " + doctorId + " released the treatment room.");
        System.out.println("Doctor " + doctorId + " is now available.");
        System.out.println("P" + patient.getPatientId() + " left.");
        hospital.countTreated();
      } else {
        System.out.println("Doctor " + doctorId + " could not acquire a treatment room.");
        System.out.println(
            "Doctor "
                + doctorId
                + " is sending "
                + "P"
                + patient.getPatientId()
                + " back to the waiting room.");
        hospital.getWaitingRoom().enterWaitingRoom(patient);
        System.out.println("Doctor " + doctorId + " is now available.");
      }
    }
  }

  private void treatPatient() {
    Random random = new Random();

    try {
      Thread.sleep(random.nextInt(5) * 1000);
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
    }
  }
}
