package curtin.edu.assignment;
//Author Matthew Matar
//Last mod 31 / 10 / 2019
//Part of incomplete database
public class DataSchema
{
        public static class GameTable
        {
            public static final String NAME = "game";
            public static class Cols
            {
                public static final String MONEY = "money";
                public static final String TIME  = "time";
            }
        }

        public static class SettingTable
        {
            public static final String NAME = "setting";
            public static class Cols
            {
                public static final String INMONEY = "inmoney";
                public static final String WIDTH = "width";
                public static final String HEIGHT = "height";
            }
        }

        public static class MapTable
        {
            public static final String NAME = "map";
            public static class Cols
            {
                public static final String TILE = "tile";
            }
        }

        public static class StructureTable
        {
            public static final String NAME = "struc";
            public static class Cols
            {
                public static final String IMAGE = "image";
                public static final String DESC = "description";
            }
        }
}
