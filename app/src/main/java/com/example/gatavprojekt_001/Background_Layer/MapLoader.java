package com.example.gatavprojekt_001.Background_Layer;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import java.io.IOException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

public class MapLoader
{

    public static Bitmap CreateBitmap(MapData data, Context context, int startLayer, int endLayer)
    {
        try
        {
            AssetManager assetManager = context.getAssets();

            Bitmap map = Bitmap.createBitmap(data.width * data.tileWidth, data.height * data.tileHeight, Bitmap.Config.ARGB_8888);

            Bitmap[] tileSets = new Bitmap[data.tileSets.size()];

            for(int i = 0; i < tileSets.length; i++)
            {
                tileSets[i] = BitmapFactory.decodeStream(assetManager.open(data.tileSets.get(i).imageFilename));
            }

            Canvas mapCanvas = new Canvas(map);

            long currentGID;
            Long localGID;
            Integer currentTilesetIndex;
            Rect source = new Rect(0, 0, 0, 0);
            Rect destination = new Rect(0, 0, 0, 0);

            for(int i = startLayer; i < endLayer; i++)
            {
                for(int j = 0; j < data.layers.get(i).height; j++)
                {
                    for(int k = 0; k < data.layers.get(i).width; k++)
                    {
                        currentGID = data.getGIDat(k, j, i);
                        localGID = data.getLocalGID(currentGID);

                        if(localGID == null)
                        {
                            Log.d("GID", "transparent");
                            Log.d("TileGrid", String.valueOf(currentGID));
                        }

                        currentTilesetIndex = data.getTileSetIndex(currentGID);

                        if (localGID != null)
                        {
                            source.top = (localGID.intValue() / ((data.tileSets.get(currentTilesetIndex).imageWidth) / data.tileWidth)) * data.tileHeight;
                            source.left = (localGID.intValue() % ((data.tileSets.get(currentTilesetIndex).imageWidth) / data.tileWidth )) * data.tileWidth;
                            source.bottom = source.top + data.tileHeight;
                            source.right = source.left + data.tileWidth;


                            destination.top = j * data.tileHeight;
                            destination.left = k * data.tileWidth;
                            destination.bottom = destination.top + data.tileHeight;
                            destination.right = destination.left + data.tileWidth;


                            mapCanvas.drawBitmap(tileSets[currentTilesetIndex], source, destination, new Paint());
                        }
                    }
                }
            }
            return map;
        }
        catch (IOException exception)
        {
            Log.d("IOException", exception.toString());
        }

        return null;
    }

    public static MapData ReadTMXFile(String filename, Context context)
    {
        MapData result = null;

        try
        {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser parser = factory.newSAXParser();
            XMLReader reader = parser.getXMLReader();
            MapHandler handler = new MapHandler();

            reader.setContentHandler(handler);

            AssetManager assetManager = context.getAssets();

            reader.parse(new InputSource(assetManager.open(filename)));

            result = handler.getData();
        }
        catch (ParserConfigurationException parserConfigurationException)
        {
            Log.e("SAX XML", "sax parse error", parserConfigurationException);
        }
        catch(SAXException saxException)
        {
            Log.e("SAX XML", "sax error", saxException);
        }
        catch(IOException ioexception)
        {
            Log.e("SAX XML", "sax parse io error", ioexception);
        }

        return result;
    }
}
