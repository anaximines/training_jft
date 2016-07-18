package task2;

import com.sun.tools.doclets.formats.html.SourceToHTMLConverter;

/**
 * Created by anaximines on 18/07/16.
 */
public class PointDistance {

  public static void main(String[] args) {
    Point p1 = new Point();
    Point p2 = new Point();

    p1.x = 2;
    p1.y = 5;

    p2.x = 4;
    p2.y = 5;

    System.out.print("Расстояние между точкой с координатами (" + p1.x + ";" + p1.y + ") и точкой с координатами (" + p2.x + ";" + p2.y + ") равно: " );
    System.out.println(distance(p1, p2) + ".");

    p1.x = 0;
    p1.y = 0;

    System.out.print("Расстояние от точки с координатами (" + p1.x + ";" + p1.y + ") ");
    System.out.print("до точки с координатами (" + p1.x + ";" + p1.y + ") равно " );
    System.out.println(p1.distance(p1) + ".");

    p1.x = -4;
    p1.y = -6;

    p2.x = 0;
    p2.y = 10;

    System.out.print("Расстояние от точки с координатами (" + p1.x + ";" + p1.y + ") ");
    System.out.print("до точки с координатами (" + p2.x + ";" + p2.y + ") равно " );
    System.out.println(p1.distance(p2) + ".");

    p2.x = -8;
    p2.y = -12;

    System.out.print("Расстояние от точки с координатами (" + p1.x + ";" + p1.y + ") ");
    System.out.print("до точки с координатами (" + p2.x + ";" + p2.y + ") равно " );
    System.out.println(p1.distance(p2) + ".");
  }

  public static double distance(Point p1, Point p2){
    return Math.sqrt(Math.pow((p1.x-p2.x),2) + Math.pow((p1.y - p2.y),2));
  }
}

