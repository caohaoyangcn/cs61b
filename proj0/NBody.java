public class NBody {
    public static double readRadius(String file_name){
        In in = new In(file_name);
        int N = in.readInt();
        return in.readDouble();
    }

    public static Body[] readBodies(String file_name){
        In in = new In(file_name);
        int N = in.readInt();
        double R = in.readDouble();
        Body[] bodies = new Body[N];
        for(int i=0; i<N; i++){
            double xP = in.readDouble();
            double yP = in.readDouble();
            double xV = in.readDouble();
            double yV = in.readDouble();
            double m = in.readDouble();
            String img_name = in.readString();
            bodies[i] = new Body(xP, yP, xV, yV, m, img_name);
        }
        return bodies;
    }

    public static void main(String[] args){
        /* Collecting all needed inputs */
        double T = Double.parseDouble(args[0]);
        double dt = Double.parseDouble(args[1]);
        String filename = args[2];
        double R = readRadius(filename);
        Body[] bodies = readBodies(filename);

        /* Drawing the background */
        StdDraw.enableDoubleBuffering();
        StdDraw.setScale(-R, R);
        StdDraw.picture(0, 0, "images/starfield.jpg");
        StdDraw.show();
        for(Body body: bodies){
            body.draw();
        }

        double discretT = 0;
        while (discretT < T){
            int size = bodies.length;
            double[] XForces = new double[size];
            double[] YForces = new double[size];
            for(int i=0; i<size; i++){
                XForces[i] = bodies[i].calcNetForceExertedByX(bodies);
                YForces[i] = bodies[i].calcNetForceExertedByY(bodies);
            }
            for(int i=0; i<size; i++){
                bodies[i].update(dt, XForces[i], YForces[i]);
            }
            StdDraw.picture(0, 0, "images/starfield.jpg");
            for(Body body: bodies){
                body.draw();
            }
            StdDraw.show();
            StdDraw.pause(10);
            discretT += dt;
        }
        StdOut.printf("%d\n", bodies.length);
        StdOut.printf("%.2e\n", radius);
        for (Body body : bodies) {
            StdOut.printf("%11.4e %11.4e %11.4e %11.4e %11.4e %12s\n",
                    body.xxPos, body.yyPos, body.xxVel,
                    body.yyVel, body.mass, body.imgFileName);
        }
    }
}
