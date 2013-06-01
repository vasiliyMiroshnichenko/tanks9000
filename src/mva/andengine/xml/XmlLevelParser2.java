package mva.andengine.xml;

import java.util.LinkedList;

import mva.andengine.R;
import mva.andengine.Refer;
import org.xmlpull.v1.XmlPullParser;
import android.content.res.XmlResourceParser;
import android.util.Log;
import android.util.Pair;
import org.xmlpull.v1.XmlPullParserException;

public class XmlLevelParser2 {

    private XmlResourceParser parser;

    private int width;
    private int height;
    private int type;
    private int botCount;
    private int sameTimeBot;

    private LinkedList<Cube> bricks;
    private int eagleX;
    private int eagleY;


    public XmlLevelParser2(int level) {
        parser = (XmlResourceParser) Refer._this.getResources().getXml(R.xml.levels);
        bricks = new LinkedList<Cube>();
        try {
            parser.next();
            parser.next();
            int eventType = parser.getEventType();

            out : while (eventType != XmlResourceParser.END_DOCUMENT ) {
                String tag = parser.getName();
                if (tag == null) {
                    parser.next();
                    return;
                }

                if (tag.equalsIgnoreCase("level") && eventType == XmlPullParser.START_TAG && parser.getAttributeIntValue(0, 0) == level) {
                    parser.next();

                    width = parser.getAttributeIntValue(0, 0);
                    height = parser.getAttributeIntValue(1, 0);
                    type = parser.getAttributeIntValue(2, 20);
                    botCount = parser.getAttributeIntValue(3, 10);
                    sameTimeBot = parser.getAttributeIntValue(4, 2);

                    parser.next();
                    parser.next();

                    while (parser.getName().equalsIgnoreCase("brick")) {
                        if (parser.getEventType() == XmlPullParser.START_TAG) {
                            try {
                                bricks.add(new Cube(parser.getAttributeIntValue(0, -1), parser.getAttributeIntValue(1, -1),parser.getAttributeIntValue(2, 0)));
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                        parser.next();
                    }
                    break out;
                }
                eventType = parser.next();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int getSameTimeBot() {
        return sameTimeBot;
    }

    public int getBotCount() {
        return botCount;
    }

    public int getLevelCount() {
        parser = (XmlResourceParser)Refer._this.getResources().getXml(R.xml.levels);
        int count = 0;
        try {
            parser.next();
            parser.next();

            while (parser.getEventType() != XmlPullParser.END_DOCUMENT) {
                if (parser.getName().equalsIgnoreCase("level") && parser.getEventType() == XmlPullParser.START_TAG) {
                    count++;
                }
                parser.next();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return 0;
        }

        return count;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getFieldType() {
        return type;
    }

    public LinkedList<Cube> getBricks() {
        return bricks;
    }
}
