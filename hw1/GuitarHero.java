import edu.princeton.cs.algs4.StdAudio;
import es.datastructur.synthesizer.GuitarString;


public class GuitarHero {
    private static final double[] frequencies = new double[37];
    private static GuitarString[] gs;
    public static final String keyboard = "q2we4r5ty7u8i9op-[=zxdcfvgbnjmk,.;/' ";

    public static void initializeGs() {
        gs = new GuitarString[37];
        for (int i = 0; i < 37; i++) {
            frequencies[i] = 440 * Math.pow(2, (double) (i - 24) / 12);
            gs[i] = new GuitarString(frequencies[i]);
        }
    }

    public static void main(String[] args) {

        initializeGs();

        while (true) {
            if (StdDraw.hasNextKeyTyped()) {
                char key = StdDraw.nextKeyTyped();
                String val = String.valueOf(key);
                if (keyboard.contains(val)) {
                    int index = keyboard.indexOf(val);
                    gs[index].pluck();
                }
            }

            double sample = 0;
            for (GuitarString s: gs) {
                sample += s.sample();
            }
            StdAudio.play(sample);

            for (GuitarString s: gs) {
                s.tic();
            }
        }
    }
}
