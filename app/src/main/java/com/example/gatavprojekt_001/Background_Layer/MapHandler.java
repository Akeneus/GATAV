package com.example.gatavprojekt_001.Background_Layer;

import android.util.Log;

import org.xml.sax.SAXException;
import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

import java.util.HashMap;

public class MapHandler extends DefaultHandler
{
    private boolean inTileSet, inTile, inLayer, inData, inObjectGroup, inProperties;

    private String currentTileID;
    private String currentObjectGroupName;

    private MapData.MapObject currentObject;
    private MapData.TileSet currentTileSet;
    private MapData.Layer currentLayer;

    private HashMap<String, HashMap<String, String>> currentTileSetProperties;
    private HashMap<String, String> currentLayerProperties;

    private MapData data;

    private char[] buffer;
    private int bufferIndex;
    private int currentX;
    private int currentY;
    private static final int MAX_INT_DECIMAL_LENGTH = 10;

    MapHandler()
    {
        super();
        buffer = new char[MAX_INT_DECIMAL_LENGTH];
        bufferIndex = 0;
        currentX = 0;
        currentY = 0;
    }

    MapData getData()
    {
        return data;
    }

    @Override
    public void startDocument()
    {
        data = new MapData();
    }

    @Override
    public void endDocument()
    {

    }

    @Override
    public void startElement(String namespaceURI, String localName, String qName, Attributes attributes) throws SAXException
    {
        switch (localName)
        {
            case "map":
            {
                if(!(attributes.getValue("orientation").equals("orthogonal")))
                {
                    throw new SAXException("Unsupported orientation. Parse Terminated.");
                }

                data.orientation = attributes.getValue("orientation");
                Log.d("Checking", data.orientation);
                data.height = Integer.parseInt(attributes.getValue("height"));
                data.width = Integer.parseInt(attributes.getValue("width"));
                data.tileWidth = Integer.parseInt(attributes.getValue("tilewidth"));
                data.tileHeight = Integer.parseInt(attributes.getValue("tileheight"));
            }
            break;

            case "tileset":
            {
                inTileSet = true;
                currentTileSet = new MapData.TileSet();
                currentTileSet.firstGID = Integer.parseInt(attributes.getValue("firstgid"));
                currentTileSet.tileWidth = Integer.parseInt(attributes.getValue("tilewidth"));
                currentTileSet.tileHeight = Integer.parseInt(attributes.getValue("tileheight"));
                currentTileSet.tileCount = Integer.parseInt(attributes.getValue("tilecount"));
                currentTileSet.columns = Integer.parseInt(attributes.getValue("columns"));
                currentTileSet.spacing = Integer.parseInt(attributes.getValue("spacing"));
                currentTileSet.name = attributes.getValue("name");
                currentTileSetProperties = new HashMap<>();
            }
            break;

            case "image":
            {
                if(inTileSet)
                {
                    currentTileSet.imageFilename = attributes.getValue("source");
                    currentTileSet.imageWidth = Integer.parseInt(attributes.getValue("width"));
                    currentTileSet.imageHeight = Integer.parseInt(attributes.getValue("height"));
                    data.tileWidth = currentTileSet.imageWidth / currentTileSet.columns;
                    data.tileHeight = currentTileSet.imageHeight / (currentTileSet.tileCount / currentTileSet.columns);
                }
            }
            break;

            case "tile":
            {
                if(inTileSet)
                {
                    inTile = true;
                    currentTileID = attributes.getValue("id");
                }
            }
            break;

            case "properties":
            {
                inProperties = true;

                if (inTile)
                {
                    Log.d("Tile ID", currentTileID);
                    currentTileSetProperties.put(currentTileID, new HashMap<String, String>());
                }
            }
            break;

            case "property":
            {
                if(inTile && inProperties)
                {
                    HashMap <String, String> properties = currentTileSetProperties.get(currentTileID);

                    if(properties != null)
                    {
                        properties.put(attributes.getValue("name"), attributes.getValue("value"));
                    }
                    else
                    {
                        Log.d("Properties NULL", currentTileID);
                    }
                }
                else if(inLayer && inProperties)
                {
                    currentLayerProperties.put(attributes.getValue("name"), attributes.getValue("value"));
                }
            }
            break;

            case "layer":
            {
                inLayer = true;

                currentLayer = new MapData.Layer();
                currentLayer.name = attributes.getValue("name");
                currentLayer.width = Integer.parseInt(attributes.getValue("width"));
                currentLayer.height = Integer.parseInt(attributes.getValue("height"));
                if(attributes.getValue("opacity") != null) currentLayer.opacity = Double.parseDouble(attributes.getValue("opacity"));
                currentLayer.tiles = new long[currentLayer.height][currentLayer.width];

                currentLayerProperties = new HashMap<>();
            }
            break;

            case "data":
            {
                inData = true;
                String encoding = attributes.getValue("encoding");
                if(!encoding.equals("csv"))
                {
                    throw new SAXException("Unsupported encoding. Parse Terminated.");
                }
            }
            break;

            case "objectGroup":
            {
                inObjectGroup = true;
                currentObjectGroupName = attributes.getValue("name");
            }
            break;

            case "object":
            {
                currentObject = new MapData.MapObject();
                currentObject.name = attributes.getValue("name");
                currentObject.type = attributes.getValue("type");
                currentObject.x = Integer.parseInt(attributes.getValue("x"));
                currentObject.y = Integer.parseInt(attributes.getValue("y"));
                currentObject.width = Integer.parseInt(attributes.getValue("width"));
                currentObject.height = Integer.parseInt(attributes.getValue("height"));
                if(inObjectGroup)
                {
                    currentObject.objectGroup = currentObjectGroupName;
                }
                else
                {
                    currentObject.objectGroup = null;
                }
            }
            break;
        }
    }

    @Override
    public void endElement(String namespaceURI, String localName, String qName)
    {
        switch(localName)
        {
            case "map": break;

            case "tileset":
            {
                inTileSet = false;
                currentTileSet.properties = currentTileSetProperties;
                currentTileSetProperties = null;
                data.tileSets.add(currentTileSet);
                currentTileSet = null;
            }
            break;

            case "tile":
            {
                inTile = false;
                currentTileID = "-1";
            }
            break;

            case "properties": inProperties = false; break;

            case "layer":
            {
                inLayer = false;
                currentLayer.properties = currentLayerProperties;
                data.layers.add(currentLayer);
                currentLayer = null;
            }
            break;

            case "data":
            {
                inData = false;

                if (bufferIndex > 0)
                {
                    currentLayer.tiles[currentY][currentX] = Long.parseLong(new String(buffer, 0, bufferIndex));
                }

                bufferIndex = 0;
                currentX = 0;
                currentY = 0;
            }
            break;

            case "objectGroup": inObjectGroup = false; break;

            case "object": data.objects.add(currentObject); break;
        }
    }

    @Override
    public void characters(char[] ch, int start, int length)
    {
        if (inData)
        {
            for(int i = 0; i < length; i++)
            {
                if(ch[start+i] <= '9' && ch[start+i] >= '0')
                {
                    buffer[bufferIndex] = ch[start+i];
                    bufferIndex++;
                }
                else
                {
                    String nextNumber = new String(buffer, 0, bufferIndex);
                    if(!nextNumber.trim().equals("") && (bufferIndex != 0))
                    {
                        currentLayer.tiles[currentY][currentX] = Long.parseLong(nextNumber);
                        bufferIndex = 0;

                        if(currentX < (currentLayer.width - 1))
                        {
                            currentX++;
                        }
                        else if(currentY < (currentLayer.height - 1))
                        {
                            currentX = 0;
                            currentY++;
                        }
                    }

                }

            }
        }

    }
}
