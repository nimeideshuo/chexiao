package com.sunwuyou.swymcx.print;

import android.os.Environment;
import android.util.Xml;

import com.sunwuyou.swymcx.app.MyApplication;
import com.sunwuyou.swymcx.utils.MLog;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;
import org.xmlpull.v1.XmlSerializer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by admin on
 * 2018/8/6.
 * content
 */
public class ModeHelper {
    private int bodytype;
    private String name;
    private List<HashMap<String, String>> textViews;

    public ModeHelper() {
        super();
    }

    public int getBodytype() {
        return this.bodytype;
    }

    public String getName() {
        return this.name;
    }

    public List<HashMap<String, String>> getTextViews() {
        return this.textViews;
    }

    private File makeXmlFile() {
        String v1 = String.valueOf(Environment.getExternalStorageDirectory().getAbsolutePath()) + File.separator + "swy/swycx" + File.separator + "printmode" + File.separator;
        File v2 = new File(v1);
        if(!v2.exists()) {
            v2.mkdirs();
        }

        File v0 = new File(String.valueOf(v1) + "extra.xml");
        if(v0.exists()) {
            v0.delete();
        }

        MLog.d(v0.getAbsolutePath());
        return v0;
    }

    public void parse() throws IOException {
        File v0 = new File(String.valueOf(Environment.getExternalStorageDirectory().getAbsolutePath()) + File.separator + "swy/swycx" + File.separator + "printmode" + File.separator + "extra.xml");
        if(v0.exists()) {
            this.parse(new FileInputStream(v0));
        }
        else {
            this.parse(MyApplication.getInstance().getAssets().open("systemmode1.xml"));
        }
    }
    /**
     * 解析模板
     *
     * @param input
     */
    private void parse(InputStream input) {
        HashMap<String, String> hashMap = new HashMap<>();
        try {
            this.textViews = new ArrayList<>();
            XmlPullParser parser = XmlPullParserFactory.newInstance().newPullParser();
            parser.setInput(input, "utf-8");
            int eventType = parser.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {
                switch (eventType) {
                    case XmlPullParser.START_DOCUMENT:
                        MLog.d("START_DOCUMENT  eventType  " + eventType);
                        break;
                    case XmlPullParser.START_TAG:

                        String name = parser.getName();
                        if ("root".equals(name)) {
                            MLog.d("start");
                            bodytype = Integer.parseInt(parser.getAttributeValue(0));
                            break;
                        }
                        if (name.equals("text")) {
                            String text = parser.nextText();
                            hashMap.put(name, text);
                            break;
                        }
                        if (name.equals("garity")) {
                            String garity = parser.nextText();
                            hashMap.put(name, garity);
                            break;
                        }
                        if (name.equals("marginleft")) {
                            String marginleft = parser.nextText();
                            hashMap.put(name, marginleft);
                            break;
                        }
                        if (name.equals("margintop")) {
                            String margintop = parser.nextText();
                            hashMap.put(name, margintop);
                            break;
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        MLog.d("END_TAG  eventType  " + eventType);
                        if (hashMap != null && hashMap.size() > 0) {
                            textViews.add(hashMap);
                        }
                        hashMap = new HashMap<String, String>();
                        break;
                    case XmlPullParser.END_DOCUMENT:
                        MLog.d("END_DOCUMENT  eventType  " + eventType);
                        break;
                }
                eventType = parser.next();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void parseExtra() throws IOException {
        if(new File(String.valueOf(Environment.getExternalStorageDirectory().getAbsolutePath()) + File.separator + "swy/swycx" + File.separator + "printmode" + File.separator + "extra.xml").exists()) {
            this.parse(MyApplication.getInstance().getAssets().open("systemmode1.xml"));
        }
    }

    public void parseSystem() throws IOException {
        this.parse(MyApplication.getInstance().getAssets().open("systemmode1.xml"));
    }

    public void write(int arg10, List<ETextView> arg11) throws IllegalArgumentException, IllegalStateException, IOException {
        String v8 = null;
        XmlSerializer v3 = Xml.newSerializer();
        FileOutputStream v2 = new FileOutputStream(this.makeXmlFile());
        v3.setOutput(v2, "UTF-8");
        v3.startDocument("UTF-8", true);
        v3.startTag(v8, "root");
        v3.attribute(v8, "type", String.valueOf(arg10));

        for (int i = 0; i < arg11.size(); i++) {
            HashMap<String,String> v1 = arg11.get(i).getParam();
            v3.startTag(v8, "textview");
            v3.startTag(v8, "text");
            v3.text(v1.get("text"));
            v3.endTag(v8, "text");
            v3.startTag(v8, "garity");
            v3.text(v1.get("garity"));
            v3.endTag(v8, "garity");
            v3.startTag(v8, "marginleft");
            v3.text(v1.get("marginleft"));
            v3.endTag(v8, "marginleft");
            v3.startTag(v8, "margintop");
            v3.text(v1.get("margintop"));
            v3.endTag(v8, "margintop");
            v3.endTag(v8, "textview");
        }
        v3.endTag(v8, "root");
        v3.endDocument();
        v2.flush();
        v2.close();
    }


}
