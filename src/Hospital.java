import java.util.Scanner;

public class Hospital {
  private int waitingRoomCapacity;
  private int doctorNumber;
  private int patientNumber;
  private WaitingRoom waitingRoom;
  private TreatmentRooms treatmentRooms;
  private CountDownLatch allPatientsTreated;

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
    allPatientsTreated.countDown();
  }

  public void waitForAllPatients() throws InterruptedException {
    allPatientsTreated.await();
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


    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter Waiting Room Capcity: ");
        int waitingRoomCapcity = scanner.nextInt();
        System.out.println("Enter Number of Doctors: ");
        int NoOfDoctor = scanner.nextInt();
        System.out.println("Enter Number of Patients: ");
        NumOfPatients = scanner.nextInt();

        WaitingRoom waitingRoom=new WaitingRoom(waitingRoomCapcity);
        TreatmentRooms treatmentRooms=new TreatmentRooms();

        Thread[] patientThreads = new Thread[NumOfPatients];
        Patient[] patients = new Patient[NumOfPatients];
        for (int i = 0; i < NumOfPatients; i++) {
            patients[i] = new Patient(i + 1, waitingRoom);
            patientThreads[i] = new Thread(patients[i]);
        }
        System.out.println("Patients arriving:");
        for (int i = 0; i < NumOfPatients; i++) {
            System.out.print("P" + patients[i].getPatientId()+" " );
        }
        System.out.println();

        for (int i = 0; i < NumOfPatients; i++) {
            patientThreads[i].start();
        }

        Thread[] doctorThreads = new Thread[NoOfDoctor];
        for (int i = 0; i < NoOfDoctor; i++) {
            doctorThreads[i] = new Thread(new Doctor(i + 1, waitingRoom, treatmentRooms));
            doctorThreads[i].start();
        }

        while (Hospital.hasPatients()) {
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        waitingRoom.StopRemainingDoctors(NoOfDoctor);

        for (int i = 0; i < NoOfDoctor; i++) {
            try {
                doctorThreads[i].join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        System.out.println("All patients have been treated.");
        System.out.println("Simulation completed.");


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
    hospital.allPatientsTreated = new CountDownLatch(hospital.patientNumber);
    hospital.waitingRoom = new WaitingRoom(hospital.waitingRoomCapacity);

    hospital.treatmentRooms = new TreatmentRooms();
    Thread[] patientThreads = new Thread[hospital.patientNumber];
    Thread[] doctorThreads = new Thread[hospital.doctorNumber];
    for (int i = 0; i < patientThreads.length; i++) {
      Patient patient = new Patient(i + 1, hospital);
      patientThreads[i] = new Thread(patient);
      patientThreads[i].start();
    }

    for (int i = 0; i < doctorThreads.length; i++) {
      Doctor doctor = new Doctor(i + 1, hospital);
      doctorThreads[i] = new Thread(doctor);
      doctorThreads[i].start();
    }

    for (Thread patient : patientThreads) {
      patient.join();
    }
    hospital.waitForAllPatients();
    hospital.getWaitingRoom().shutdown(hospital.doctorNumber);
    for (Thread doctor : doctorThreads) {
      doctor.join();
    }
    System.out.println("All patients have been treated.");
    System.out.println("Simulation completed.");
  }
}
