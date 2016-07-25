/**
 * Created by anaximines on 18/07/16.
 */

public class Point {
  public double x; //the point on x-axis
  public double y; //the point on y-axis

  public Point () {
    this.x = 0.0;
    this.y = 0.0;
  }

  public Point (double x, double y) {
    this.x = x;
    this.y = y;
  }

  public double distance(Point point2){
    return Math.sqrt(Math.pow((x-point2.x),2) + Math.pow((y - point2.y),2));
  }
}
