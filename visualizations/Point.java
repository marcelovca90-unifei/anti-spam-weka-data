public class Point
{
    double x;
    double y;
    int i;
    boolean plot;

    public Point(Point parent)
    {
        this.x = parent.x;
        this.y = parent.y;
        this.i = -1;
        this.plot = true;
    }

    public Point(String x, String y, int i)
    {
        this.x = Double.parseDouble(x);
        this.y = Double.parseDouble(y);
        this.i = i;
        this.plot = true;
    }

    public String toGnuplotString()
    {
        return String.format("%s\t%s\n", x, y);
    }

    @Override
    public String toString()
    {
        return String.format("[x=%.3f, y=%.3f, i=%d]", x, y, i);
    }

    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        long temp;
        temp = Double.doubleToLongBits(x);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(y);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        Point other = (Point) obj;
        if (Double.doubleToLongBits(x) != Double.doubleToLongBits(other.x)) return false;
        if (Double.doubleToLongBits(y) != Double.doubleToLongBits(other.y)) return false;
        return true;
    }
}
