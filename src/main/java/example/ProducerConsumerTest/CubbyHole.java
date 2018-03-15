package example.ProducerConsumerTest;

public class CubbyHole {
              private int contents;
              private boolean available = false;
public synchronized int get() {
              while (available == false) {
                  try { // wait for Producer to put value
                        wait();
                       } catch (InterruptedException e) {
                  }
              }
              System.out.println(available);
              available = false;
              // notify Producer that value has been retrieved
              notifyAll();
              return contents;
          }
public synchronized void put(int value) {
              while (available == true) {
                  try { // wait for Consumer to get value
                        wait();
                        } catch (InterruptedException e) {
                  }
              }
              contents = value;
              System.out.println(available);
              available = true;
              // notify Consumer that value has been set
              notifyAll();
          }           

}
