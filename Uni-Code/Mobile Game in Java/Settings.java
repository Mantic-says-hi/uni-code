package curtin.edu.assignment;

import java.io.Serializable;
//Author Matthew Matar
//Last mod 31 / 10 / 2019
//Inital conditions for settings
public class Settings implements Serializable
{
    public int    mapWidth;
    public int    mapHeight;
    public int    initialMoney;
    public int    familySize;
    public int    shopSize;
    public int    salary;
    public int    serviceCost;
    public int    houseBuildingCost;
    public int    commBuildingCost;
    public int    roadBuildingCost;
    public double taxRate;

    public Settings()
    {
        mapWidth          = 50;
        mapHeight         = 10;
        initialMoney      = 1000;
        familySize        = 4;
        shopSize          = 6;
        salary            = 10;
        taxRate           = 0.3;
        serviceCost       = 2;
        houseBuildingCost = 100;
        commBuildingCost  = 500;
        roadBuildingCost  = 20;
    }

    //Was made for database, never used
    public Settings(int inMapWidth,int inMapHeight,int inInitialMoney)
    {
        mapWidth          = inMapWidth;
        mapHeight         = inMapHeight;
        initialMoney      = inInitialMoney;
        familySize        = 4;
        shopSize          = 6;
        salary            = 10;
        taxRate           = 0.3;
        serviceCost       = 2;
        houseBuildingCost = 100;
        commBuildingCost  = 500;
        roadBuildingCost  = 20;
    }

    public void setMapWidth(int inMapWidth)
    {
        mapWidth = inMapWidth;
    }

    public void setMapHeight(int inMapHeight)
    {
        mapHeight = inMapHeight;
    }

    public void setInitialMoney(int inInitialMoney)
    {
        initialMoney = inInitialMoney;
    }

    public int getSalary() { return salary; }

    public int getMapWidth() { return mapWidth; }

    public int getShopSize() { return shopSize; }

    public int getMapHeight() { return mapHeight; }

    public int getServiceCost() { return serviceCost; }

    public int getFamiliySize() { return familySize; }

    public int getInitialMoney() { return initialMoney; }

    public int getCommBuildingCost() { return commBuildingCost;}

    public int getRoadBuildingCost() { return roadBuildingCost; }

    public int getHouseBuildingCost() { return houseBuildingCost; }

    public double getTaxRate() { return taxRate; }
}
