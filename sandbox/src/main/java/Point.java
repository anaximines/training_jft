/**
 * Created by anaximines on 18/07/16.
 */

public class Point {
  public double x; //the point on x-axis
  public double y; //the point on y-axis

  public double distance(Point point2){
    return Math.sqrt(Math.pow((x-point2.x),2) + Math.pow((y - point2.y),2));
  }
}
