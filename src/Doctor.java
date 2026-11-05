import java.util.Random;

public class Doctor implements Runnable {

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
            Patient patient = waitingRoom.fetchNextPatient(doctorId);
            if(patient==null){
                break;
            }
            if(treatmentRooms.requireRoom(doctorId,3000)){
                System.out.println("Doctor"+doctorId + " acquired a treatment room.");
                System.out.println("Doctor"+doctorId + " begins treating " + "P" + patient.getPatientId() + ".");
                treatPatient();
                System.out.println("Doctor"+doctorId + " finished treating "+"P"+patient.getPatientId() + ".");
                treatmentRooms.letRoom();
                System.out.println("Doctor"+doctorId + " released the treatment room.");
                System.out.println("Doctor"+doctorId + " is now available.");
                System.out.println("P"+patient.getPatientId() + " left.");
                Hospital.CountTreated();
            } else{
                System.out.println("Doctor"+doctorId + " could not acquire a treatment room.");
                System.out.println("Doctor"+doctorId + " is sending "+"P"+ patient.getPatientId()+" back to the waiting room.");
                waitingRoom.enterWaitingRoom(patient);
                System.out.println("Doctor"+doctorId + " is now available.");
            }
        }

    }

    private void treatPatient() {
       Random random= new Random();
       try{
           Thread.sleep((random.nextInt(5)+1)*1000);
       } catch (InterruptedException e) {
           throw new RuntimeException(e);
       }
    }


}
