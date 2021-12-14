public class GridNode {
    private Point position;
    private Point parent;
    private int g = 0;
    private double h = 0;
    private double f = 0;


    public GridNode(Point position, int g, double h, Point parent){
        this.g = g; //no distance from the start
        this.h = h; // heuristic distance from the gaol
        this.f = g + h;
        this.position = position;
        this.parent = parent;

    }

    public Point getCurrent(){ return this.position;}

    public Point getPrior(){ return this.parent;}

    public int getG(){ return this.g;}

    public double getH(){ return this.h;}

    public double getF(){ return this.f;}

//    public boolean equals(Object other){
//        return other instanceof Node && this.position.equals(((Node)other).position);
//    }
}
