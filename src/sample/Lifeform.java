package sample;

public class Lifeform
{
  boolean alive;
  Position here;
  int neighbors;

  public Lifeform()
  {
    boolean alive  = false;
    Position here = new Position(0,0);
    int neighbors = 0;
  }
  public Lifeform who()
  {
    return this;
  }
}
