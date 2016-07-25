import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Created by anaximines on 25/07/16.
 */
public class PointTests {
  @Test
  public void distanceBetweenPointsOfOneQuadrant(){
    Point p1 = new Point (4.0, 2.0);
    Point p2 = new Point (4.0, 5.0);

    Assert.assertEquals(p1.distance(p2), 3.0);
  }

  @Test
  public void distanceBetweenPointsOfDifferentQuadrants(){
    Point p1 = new Point(-1.5, 3.5);
    Point p2 = new Point(1.5, -3.5);

    Assert.assertEquals(p1.distance(p2), 7.615773105863909);
  }

  @Test
  public void distanceBetweenPointAndOrigin(){
    Point p1 = new Point ();
    Point p2 = new Point (-3.0, -3.33);

    Assert.assertEquals(p1.distance(p2), 4.482064256567503);
  }

  @Test
  public void onePointDistance(){
    Point p1 = new Point (3.8, -4.0);

    Assert.assertEquals(p1.distance(p1), 0.0);
  }

  @Test
  public void distanceBetweenPointsOnX(){
    Point p1 = new Point (2.53, 0.0);
    Point p2 = new Point (-5.47, 0.0);

    Assert.assertEquals(p1.distance(p2), 8.0);
  }

  @Test
  public void distanceBetweenPointsOnY(){
    Point p1 = new Point (0.0, 4.0);
    Point p2 = new Point (0.0, -4.5);

    Assert.assertEquals(p1.distance(p2), 8.5);
  }
}
