public class UnisexBathroom {
    int maxPeople = 3;
    boolean femalePresent = false;
    boolean malePresent = false;
    int size = 0;

    public void maleUseBathroom(String name) throws InterruptedException {
        synchronized(this) {
            while(size == maxPeople || femalePresent) {
                wait();
            }
            malePresent = true;
            size++;
        }
        useBathroom(name);
        synchronized(this) {
            size--;
            if(size == 0) {
                malePresent = false;
            }
            notifyAll();
        }

    }

    public void femaleUseBathroom(String name) throws InterruptedException {
        synchronized(this) {
            while(size == maxPeople || malePresent) {
                wait();
            }
            femalePresent = true;
            size++;
        }
        useBathroom(name);
        synchronized(this) {
            size--; 
            if(size == 0) femalePresent = false;
            this.notifyAll();
        }
    }

    public void useBathroom(String name) throws InterruptedException {
        System.out.println(name + " using Bathroom ");
        Thread.sleep(1000);
        System.out.println(name + " done using Bathroom");      
    }
} 