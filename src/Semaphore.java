public class Semaphore 
{
    private int value;

    public Semaphore(int initialValue) 
    {
        if (initialValue < 0) 
        {
            throw new IllegalArgumentException("Initial value cannot be negative.");
        }
        this.value = initialValue;
    }

    public synchronized void P() throws InterruptedException
    {
        while (value == 0)
        {
            wait();
        }
        value--;
    }

    public synchronized boolean tryAcquire(long timeoutMillis)

    {

        if (timeoutMillis < 0) 
        {
            throw new IllegalArgumentException("Timeout cannot be negative.");
        }

        if (value > 0) 
        {
            value--;
            return true;
        }

        if (timeoutMillis == 0) 
        {
            return false;
        }

        long startTime = System.nanoTime();
        long timeoutNanos = timeoutMillis * 1_000_000L;
        long remainingTime = timeoutNanos;

        while (value == 0 && remainingTime > 0) 
        {
            long millis = remainingTime / 1_000_000L;
            int nanos = (int) (remainingTime % 1_000_000L);
            try {
                wait(millis, nanos);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            long elapsedTime = System.nanoTime() - startTime;
            remainingTime = timeoutNanos - elapsedTime;
        }

        if (value > 0)
        {
            value--;
            return true;
        }

        return false;
    }

    public synchronized int getValue()
    {
        return value;
    }

    public synchronized void V() 
    {
        value++;
        notifyAll();
    }
}
