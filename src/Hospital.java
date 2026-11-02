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


    }
}



