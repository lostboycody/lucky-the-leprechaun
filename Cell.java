public class Cell
{
  public static Color color;
  private String imageFileName;
  
  public Cell()
  {
    color = new Color(11, 214, 85);
    imageFileName = null;
  }
  
  public static void setColor(Color c)
  {
    color = c;
  }
  
  public Color getColor()
  {
    return color;
  }
  
  public String getImageFileName()
  {
    return imageFileName;
  }
  
  public void setImageFileName(String fileName)
  {
    imageFileName = fileName;
  }
}