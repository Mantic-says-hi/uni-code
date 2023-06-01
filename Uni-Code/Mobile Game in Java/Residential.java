package curtin.edu.assignment;

public class Residential extends Structure
{
    public int drawableId;
    public String description;
    public String classID;

    public Residential(int inDrawableID, String inDescription)
    {
        drawableId = inDrawableID;
        description = inDescription;
        classID = "Residential";
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