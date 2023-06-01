package curtin.edu.assignment;
import java.io.Serializable;
import java.util.Random;

public class GameData implements Serializable
{

    private static GameData instance = null;
    private static Settings settings;
    private static MapElement[][] map;
    private int money;
    private int gameTime = 0;

    private static final int[] GRASS = {R.drawable.ic_grass1, R.drawable.ic_grass2,
            R.drawable.ic_grass3, R.drawable.ic_grass4};

    public static GameData startGame()
    {
        if(instance == null){
            instance = new GameData();
        }
        return instance;
    }

    public GameData()
    {

    }

    public GameData(Settings set,MapElement[][] grid, int cash, int time)
    {
        settings = set;
        map = grid;
        money = cash;
        gameTime = time;
    }

    public static Settings getSettings() {
        return settings;
    }

    public static void setSettings(Settings settings) {
        GameData.settings = settings;
    }

    public static MapElement[][] getMap() {
        return map;
    }

    public static void setMap(MapElement[][] inMap) {
        map = inMap;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int inMoney) {
        money = inMoney;
    }

    public int getGameTime() {
        return gameTime;
    }

    public void setGameTime(int inGameTime) {
        gameTime = inGameTime;
    }

    public static MapElement[][] createMap()
    {
        MapElement[][] newMap = new MapElement[settings.getMapHeight()][settings.getMapWidth()];
        for (int i = 0; i < settings.getMapHeight(); i++)
        {
            for (int j = 0; j < settings.getMapWidth(); j++)
            {
                Random rnd = new Random();
                newMap[i][j] = new MapElement();
                newMap[i][j].setGroundTile(GRASS[rnd.nextInt(GRASS.length)]);
                newMap[i][j].setStructure(new Utility(R.drawable.invisible, ""));
            }

        }

        return newMap;
    }
}
