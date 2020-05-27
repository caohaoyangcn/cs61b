public class Body {

    public double xxPos;
    public double yyPos;
    public double xxVel;
    public double yyVel;
    public double mass;
    public String imgFileName;
    static final double G_CONSTANT = 6.67e-11;

    public Body(double xP, double yP, double xV, double yV,
                double m, String img) {
        xxPos = xP;
        yyPos = yP;
        xxVel = xV;
        yyVel = yV;
        mass = m;
        imgFileName = img;
    }

    public Body(Body b) {
        xxPos = b.xxPos;
        yyPos = b.yyPos;
        xxVel = b.xxVel;
        yyVel = b.yyVel;
        mass = b.mass;
        imgFileName = b.imgFileName;
    }

    public double calcDistance(Body another) {
        double dx = another.xxPos - xxPos;
        double dy = another.yyPos - yyPos;
        return Math.sqrt(dx*dx + dy*dy);
    }

    public double calcForceExertedBy(Body another) {
        double dist = calcDistance(another);
        return another.mass * mass * G_CONSTANT / (dist*dist);
    }

    public double calcForceExertedByX(Body another) {
        double dx = another.xxPos - xxPos;
        return calcForceExertedBy(another) * dx / calcDistance(another);
    }

    public double calcForceExertedByY(Body another) {
        double dy = another.yyPos - yyPos;
        return calcForceExertedBy(another) * dy / calcDistance(another);
    }

    public double calcNetForceExertedByX(Body[] bodies) {
        double net = 0;
        for (Body body: bodies) {
            if (this.equals(body)) continue;
            net += calcForceExertedByX(body);
        }
        return net;
    }

    public double calcNetForceExertedByY(Body[] bodies) {
        double net = 0;
        for (Body body: bodies) {
            if (this.equals(body)) continue;
            net += calcForceExertedByY(body);
        }
        return net;
    }

    public void update(double dt, double fx, double fy) {
        xxVel += dt * (fx/mass);
        yyVel += dt * (fy/mass);
        xxPos += xxVel * dt;
        yyPos += yyVel * dt;
    }

    public void draw() {
        String fullPath = "images/" + imgFileName;
        StdDraw.picture(xxPos, yyPos, fullPath);
        StdDraw.show();
    }
}
