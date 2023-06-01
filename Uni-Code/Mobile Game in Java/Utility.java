package curtin.edu.assignment;

public class Utility extends Structure
{
    public int drawableId;
    public String description;
    public String classID = "Utility";

    public Utility(int inDrawableID, String inDescription)
    {
        drawableId = inDrawableID;
        description = inDescription;
    }
    public int getImageId(){ return drawableId; }
    public String getDescription() {
        return description;
    }

    public void setDescription(String inDescription)
    {
        description = inDescription;
    }

    public String getClassID()
    {
        return classID;
    }

}
