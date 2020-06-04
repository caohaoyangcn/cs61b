package es.datastructur.synthesizer;

//Note: This file will not compile until you complete task 1 (es.datastructur.synthesizer.BoundedQueue).
public class GuitarString {
    /** Constants. Do not change. In case you're curious, the keyword final
     * means the values cannot be changed at runtime. */
    private static final int SR = 44100;      // Sampling Rate
    private static final double DECAY = .996; // energy decay factor

    /* Buffer for storing sound data. */
    private BoundedQueue<Double> buffer;

    /*
    The ring buffer feedback mechanism. The ring buffer models
    the medium (a string tied down at both ends) in which the energy
    travels back and forth.

    The length of the ring buffer determines the fundamental
    frequency of the resulting sound.

    Sonically, the feedback mechanism reinforces only the fundamental
    frequency and its harmonics (frequencies at integer multiples of
    the fundamental).

    The energy decay factor (.996 in this case) models
    the slight dissipation in energy as the wave makes a round trip
    through the string.
    */

    /* Create a guitar string of the given frequency.  */
    public GuitarString(double frequency) {


        // Create a buffer with capacity = SR / frequency.
        // For better accuracy, use the Math.round() function before casting.
        // cast the result of this division operation into an int.
        int capacity = (int) Math.round(SR / frequency);

        buffer = new ArrayRingBuffer<>(capacity);

        // The buffer should be initially filled with zeros.
        for (int i = 0; i < capacity; i++) {
            buffer.enqueue((double) 0);
        }
    }


    /* Pluck the guitar string by replacing the buffer with white noise. */
    public void pluck() {
        // Dequeue everything in buffer, and replace with random numbers
        // between -0.5 and 0.5. You can get such a number by using:
        // double r = Math.random() - 0.5;
        //
        // Make sure that your random numbers are different from each
        // other.
        for (int i = 0; i < buffer.capacity(); i++) {
            buffer.dequeue();
        }
        for (int i = 0; i < buffer.capacity(); i++) {
            double r = Math.random() - .5;
            buffer.enqueue(r);
        }

    }

     /*
       The averaging operation. The averaging operation serves as
       a gentle low-pass filter (which removes higher frequencies
       while allowing lower frequencies to pass, hence the name).

       Because it is in the path of the feedback, this has the
       effect of gradually attenuating the higher harmonics
       while keeping the lower ones, which corresponds closely with
       how a plucked guitar string sounds.
     */

    /* Advance the simulation one time step by performing one iteration of
     * the Karplus-Strong algorithm.
     */
    public void tic() {
        // Dequeue the front sample and enqueue a new sample that is
        // the average of the two multiplied by the DECAY factor.

        double front = buffer.dequeue();
        buffer.enqueue((buffer.peek() + front) / 2 * DECAY);
    }

    /* Return the double at the front of the buffer. */
    public double sample() {
        // Return the correct thing.
        return buffer.peek();
    }
}
