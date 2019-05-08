package com.example.gatavprojekt_001.Background_Layer;

import java.util.ArrayList;
import java.util.HashMap;

public class MapData {

    private static final long GID_MASK = 0x3FFFFFFF;

    static class TileSet
    {
        public String name;

        int firstGID;
        int tileWidth, tileHeight;

        String imageFilename;
        int imageWidth, imageHeight;

        HashMap<String, HashMap<String, String>> properties;
    }

    static class Layer
    {
        public String name;

        long[][] tiles;

        int width, height;
        double opacity;

        HashMap<String, String> properties;
    }

    static class MapObject
    {
        String name;
        String type;
        int x, y;
        int width, height;
        String objectGroup;
    }

    long getGIDat(int x, int y, int layerIndex)
    {
        return ((layers.get(layerIndex).tiles[y][x]) & GID_MASK);
    }

    Long getLocalGID(long GID)
    {
        Long result = null;
        long currentFirstGID;

        for(int i = tileSets.size() - 1; i >= 0; i--)
        {
            currentFirstGID = tileSets.get(i).firstGID;
            if(currentFirstGID <= GID)
            {
                result = GID - currentFirstGID;
                break;
            }
        }

        return result; //GID is not valid: result = null
    }

    Integer getTileSetIndex(long GID)
    {
        Integer result = null;
        long currentFirstGID;

        for(int i = tileSets.size() - 1; i >= 0; i--)
        {
            currentFirstGID = tileSets.get(i).firstGID;
            if(currentFirstGID <= GID)
            {
                result = i;
                break;
            }
        }

        return result;
    }

    public String name;
    int height, width;
    int tileWidth, tileHeight;
    String orientation;

    ArrayList<TileSet> tileSets;
    ArrayList<MapObject> objects;
    public ArrayList<Layer> layers;

    MapData()
    {
        tileSets = new ArrayList<>();
        objects = new ArrayList<>();
        layers = new ArrayList<>();
    }
}
