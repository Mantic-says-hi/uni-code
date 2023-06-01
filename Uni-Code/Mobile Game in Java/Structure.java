package curtin.edu.assignment;

import java.io.Serializable;
//Author Matthew Matar
//Last mod 31 / 10 / 2019
//4 Types of structure, Utility (invisible structure and demolisher),
//Residential and Road
//Each one sets it's own class ID on creation to it's name
public abstract class Structure implements Serializable
{
    public int imageId;
    public String description;
    public String classID = "Default";

    public Structure(int inDrawableID, String inDescription)
    {
        imageId = inDrawableID;
        description = inDescription;
    }

    public Structure()
    {
        imageId = 0;
    }

    public int getImageId(){ return imageId; }

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
