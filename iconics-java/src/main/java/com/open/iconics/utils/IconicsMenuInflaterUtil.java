package com.open.iconics.utils;

import android.content.Context;
import android.content.res.XmlResourceParser;
import android.util.AttributeSet;
import android.util.Xml;
import android.view.Menu;
import android.view.MenuInflater;

import androidx.annotation.NonNull;

import com.open.iconics.IconicsDrawable;
import com.open.iconics.context.IconicsAttrsApplier;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.HashMap;

public class IconicsMenuInflaterUtil {

    /**
     * Menu tag name in XML.
     */
    private static final String XML_MENU = "menu";

    /**
     * Item tag name in XML.
     */
    private static final String XML_ITEM = "item";

    /*
     * Default menu inflater
     * Uses the IconicsImageView styleable tags to get the iconics data of menu items
     */
    public static void inflate(@NonNull MenuInflater inflater,
                               @NonNull Context context,
                               int menuId,
                               @NonNull Menu menu) {
        inflate(inflater, context, menuId, menu, false);
    }

    /*
     * Inflates an menu by resource id and uses the styleable tags to get the iconics data of menu
     * items
     *
     * By default, menus don't show icons for sub menus, but this can be enabled via reflection
     * So use this function if you want that sub menu icons are checked as well
     */
    public static void inflate(@NonNull MenuInflater inflater,
                               @NonNull Context context,
                               int menuId,
                               @NonNull Menu menu,
                               boolean checkSubMenus) {
        inflater.inflate(menuId, menu);
        parseXmlAndSetIconicsDrawables(context, menuId, menu, checkSubMenus);
    }

    /**
     * Uses the styleable tags to get the iconics data of menu items. Useful for set icons into
     * {@code BottomNavigationView}
     * <p>
     * By default, menus don't show icons for sub menus, but this can be enabled via reflection
     * So use this function if you want that sub menu icons are checked as well
     */
    public static void parseXmlAndSetIconicsDrawables(@NonNull Context context,
                                                      int menuId,
                                                      @NonNull Menu menu,
                                                      boolean checkSubMenus) {
        try {
            XmlResourceParser parser = context.getResources().getXml(menuId);
            AttributeSet attrs = Xml.asAttributeSet(parser);
            parseMenu(context, attrs, parser, menu, checkSubMenus);
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Uses the styleable tags to get the iconics data of menu items. Useful for set icons into
     * {@code BottomNavigationView}
     * <p>
     * By default, menus don't show icons for sub menus, but this can be enabled via reflection
     * So use this function if you want that sub menu icons are checked as well
     */
    public static void parseXmlAndSetIconicsDrawables(@NonNull Context context,
                                                      int menuId,
                                                      @NonNull Menu menu) {
        parseXmlAndSetIconicsDrawables(context, menuId, menu, false);
    }

    private static void parseMenu(@NonNull Context context,
                                  @NonNull AttributeSet attrs,
                                  @NonNull XmlPullParser parser,
                                  @NonNull Menu menu,
                                  boolean checkSubMenus) throws XmlPullParserException, IOException {
        int eventType = parser.getEventType();
        String tagName;
        boolean lookingForEndOfUnknownTag = false;
        String unknownTagName = null;

        // This loop will skip to the menu start tag
        do {
            if (eventType == XmlPullParser.START_TAG) {
                tagName = parser.getName();
                if (XML_MENU.equals(tagName)) {
                    // Go to next tag
                    eventType = parser.next();
                    break;
                }

                throw new RuntimeException("Expecting menu, got " + tagName);
            }
            eventType = parser.next();
        } while (eventType != XmlPullParser.END_DOCUMENT);

        boolean reachedEndOfMenu = false;
        while (!reachedEndOfMenu) {
            switch (eventType) {
                case XmlPullParser.START_TAG:
                    if (lookingForEndOfUnknownTag) {
                        break;
                    }
                    tagName = parser.getName();
                    switch (tagName) {
                        case XML_ITEM:
                            HashMap<String, String> attrsMap = new HashMap<>();
                            for (int i = 0; i < parser.getAttributeCount(); i++) {
                                attrsMap.put(parser.getAttributeName(i), parser.getAttributeValue(i));
                            }
                            IconicsDrawable icon = IconicsAttrsApplier.getIconicsDrawable(context, attrs);
                            if (icon != null) {
                                String idAsString = attrsMap.get("id").replace("@", "");

                                // If the id is not in literal format, look it up using the name.
                                if (idAsString.startsWith("+id/")) {
                                    idAsString = idAsString.replace("+id/", "");
                                }

                                int id = context.getResources().getIdentifier(idAsString, "id", context.getPackageName());
                                menu.findItem(id).setIcon(icon);
                            }
                            break;

                        case XML_MENU:
                            // TODO: maybe we must pass in the sub menu in this case, not sure if the function menu.findItem(id) will search through sub items
                            if (checkSubMenus) {
                                parseMenu(context, attrs, parser, menu, true);
                            }
                            break;

                        default:
                            lookingForEndOfUnknownTag = true;
                            unknownTagName = tagName;
                            break;
                    }
                    break;

                case XmlPullParser.END_TAG:
                    tagName = parser.getName();
                    if (lookingForEndOfUnknownTag && tagName.equals(unknownTagName)) {
                        lookingForEndOfUnknownTag = false;
                        unknownTagName = null;
                    } else if (XML_MENU.equals(tagName)) {
                        reachedEndOfMenu = true;
                    }
                    break;

                case XmlPullParser.END_DOCUMENT:
                    throw new RuntimeException("Unexpected end of document");
            }
            eventType = parser.next();
        }
    }
}