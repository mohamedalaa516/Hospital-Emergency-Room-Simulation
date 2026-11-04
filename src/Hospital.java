public class Hospital {
  private int treatedPatients;
  private int waitingRoomCapacity;
  private int doctorNumber;
  private int patientNumber;
  private WaitingRoom waitingRoom;
  private TreatmentRooms treatmentRooms;

  public void setRoomCapacity(int waitingRoomCapacity) {
    this.waitingRoomCapacity = waitingRoomCapacity;
  }

  public void setDoctors(int doctorNumber) {
    this.doctorNumber = doctorNumber;
  }

  public void setPatients(int patientNumber) {
    this.patientNumber = patientNumber;
  }

  public WaitingRoom getWaitingRoom() {
    return waitingRoom;
  }

  public TreatmentRooms getTreatmentRooms() {
    return treatmentRooms;
  }

  public synchronized void countTreated() {
    treatedPatients++;
  }

  public synchronized boolean hasPatients() {
    return treatedPatients < patientNumber;
  }

  public static void main(String[] args) throws InterruptedException {

    Hospital hospital = new Hospital();

    if (args.length == 0 || args.length % 2 != 0) {
      System.out.println(
          "Usage: java Hospital --patients <number> --doctors <number> --capacity <number>");
      return;
    }

    try {
      for (int i = 0; i < args.length; i += 2) {

        String flag = args[i];
        int value = Integer.parseInt(args[i + 1]);

        if (value <= 0) {
          System.out.println("Values must be greater than zero.");
          return;
        }

        switch (flag) {
          case "--patients":
            hospital.setPatients(value);
            break;

          case "--doctors":
            hospital.setDoctors(value);
            break;

          case "--capacity":
            hospital.setRoomCapacity(value);
            break;

          default:
            System.out.println("Unknown flag: " + flag);
            return;
        }
      }

    } catch (NumberFormatException e) {
      System.out.println("Please enter valid numbers.");
      return;
    }

    if (hospital.patientNumber == 0
        || hospital.doctorNumber == 0
        || hospital.waitingRoomCapacity == 0) {

      System.out.println("Missing required arguments.");
      return;
    }

    // Create shared resources
    hospital.waitingRoom = new WaitingRoom(hospital.waitingRoomCapacity);

    hospital.treatmentRooms = new TreatmentRooms();

    Thread[] patientThreads = new Thread[hospital.patientNumber];
    Thread[] doctorThreads = new Thread[hospital.doctorNumber];

    // Create patients
    for (int i = 0; i < patientThreads.length; i++) {
      Patient patient = new Patient(i + 1, hospital.waitingRoom);
      patientThreads[i] = new Thread(patient);
      patientThreads[i].start();
    }

    // Create doctors
    for (int i = 0; i < doctorThreads.length; i++) {
      Doctor doctor = new Doctor(i + 1, hospital);
      doctorThreads[i] = new Thread(doctor);
      doctorThreads[i].start();
    }

    for (Thread patient : patientThreads) {
      patient.join();
    }

    for (Thread doctor : doctorThreads) {
      doctor.join();
    }

    System.out.println("All patients have been treated.");
    System.out.println("Simulation completed.");
  }
}
