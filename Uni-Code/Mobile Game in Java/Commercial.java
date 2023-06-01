package curtin.edu.assignment;

public class Commercial extends Structure
{
    public int drawableId;
    public String description;
    public String classID;

    public Commercial(int inDrawableID, String inDescription)
    {
        drawableId = inDrawableID;
        description = inDescription;
        classID = "Commercial";
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
