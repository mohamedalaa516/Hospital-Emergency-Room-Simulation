import java.util.Random;

public class Doctor extends Thread {

    private final int doctorId;
    private final WaitingRoom waitingRoom;
    private final TreatmentRooms treatmentRooms;

    public Doctor(int doctorId, WaitingRoom waitingRoom, TreatmentRooms treatmentRooms) {
        this.doctorId=doctorId;
        this.waitingRoom=waitingRoom;
        this.treatmentRooms=treatmentRooms;
    }

    @Override
    public void run() {
        while(Hospital.hasPatients()){
            Patient patient = waitingRoom.fetchNextPatient();
            System.out.println(doctorId + " is assigned to "+ patient.getPatientId() + ".");
            if(treatmentRooms.requireRoom(doctorId,3000)){
                System.out.println(doctorId + " acquired a treatment room.");
                System.out.println(doctorId + " begins treating " + patient.getPatientId() + ".");
                treatPatient();
                System.out.println(doctorId + " finished treating "+patient.getPatientId() + ".");
                treatmentRooms.letRoom();
                System.out.println(doctorId + " released the treatment room.");
                System.out.println(doctorId + " is now available.");
                System.out.println(patient.getPatientId() + " left.");
                Hospital.CountTreated();
            }
        }

    }

    private void treatPatient() {
       Random random= new Random();
       try{
           Thread.sleep(random.nextInt(5)*1000);
       } catch (InterruptedException e) {
           throw new RuntimeException(e);
       }
    }

    public int getDoctorId() {
        return doctorId;
    }
}
