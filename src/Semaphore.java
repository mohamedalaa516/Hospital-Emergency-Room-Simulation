public class Semaphore {

    private int value;
    Semaphore(int value){
        this.value=value;
    }

    public synchronized void P(){
        value--;
        if(value<0){
            try {
                wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public synchronized boolean tryAcquire(long waitingTime){
       if(value>0){
          value--;
          return true;
       }
       long endTime=System.currentTimeMillis()+waitingTime;
       while(waitingTime>0){
           try {
               wait(waitingTime);
           } catch (InterruptedException e) {
               throw new RuntimeException(e);
           }
          waitingTime=endTime-System.currentTimeMillis();
           if(value>0){
               value--;
               return true;
           }
           if(waitingTime<=0)return false;


       }
       return false;
    }



    public synchronized  void V(){
        value++;
        notify();

    }

}