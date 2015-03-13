import java.time.*;

public class Subscriber {

   private int port;
   private int secs;
   
   private Instant start;
   private Instant end;


   public Subscriber(int port, int secs) {
      this.port = port;
      this.secs = secs;

      start = Instant.now();
      end = start.plusSeconds(secs);
   }


   // Getters
   public int getPort(){ return this.port; }
   public int getSecs(){ return this.secs; }
   public Instant getStart(){ return this.start; }
   public Instant getEnd(){ return this.end; }
   
}
