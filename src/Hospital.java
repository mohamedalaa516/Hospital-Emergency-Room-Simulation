import java.util.Scanner;

public class Hospital {

    private static int NumOfPatients;
    private static int treatedPatients = 0;

    public static synchronized void CountTreated() {
        treatedPatients++;
    }

    public static synchronized boolean hasPatients() {
        if (NumOfPatients > treatedPatients) {
            return true;
        } else {
            return false;
        }
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

        Thread[] doctorThreads = new Thread[NoOfDoctor];
        for (int i = 0; i < NoOfDoctor; i++) {
            doctorThreads[i] = new Thread(new Doctor(i + 1, waitingRoom, treatmentRooms));
            doctorThreads[i].start();
        }

        for (int i = 0; i < NumOfPatients; i++) {
            patientThreads[i].start();
            try{
                Thread.sleep(85);
            }catch (InterruptedException e){
                throw new RuntimeException(e);
            }
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



    }
}



