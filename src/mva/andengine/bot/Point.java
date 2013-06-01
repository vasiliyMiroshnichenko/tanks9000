package mva.andengine.bot;

/**
 * Created with IntelliJ IDEA.
 * User: vasiliy
 * Date: 23.04.13
 * Time: 3:14
 * To change this template use File | Settings | File Templates.
 */
public class Point {
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    private int x;
    private int y;
    public Point(int x1,int y1){
       x=x1;
       y = y1;
   }
}
