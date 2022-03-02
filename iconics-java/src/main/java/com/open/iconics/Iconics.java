package com.open.iconics;

import android.content.Context;
import android.text.Editable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.CharacterStyle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.open.iconics.animation.IconicsAnimationProcessor;
import com.open.iconics.typeface.IIcon;
import com.open.iconics.typeface.ITypeface;
import com.open.iconics.utils.GenericsUtil;
import com.open.iconics.utils.IconicsUtils;
import com.open.iconics.utils.StyleContainer;
import com.open.iconics.utils.TextStyleContainer;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

@SuppressWarnings("UnusedReturnValue, WeakerAccess, unused")
public final class Iconics {
    @NonNull
    public static final String TAG = Iconics.class.getSimpleName();

    private static boolean INIT_DONE = false;
    @NonNull
    private static final HashMap<String, ITypeface> FONTS = new HashMap<>();
    @NonNull
    private static final HashMap<String, Class<? extends IconicsAnimationProcessor>> PROCESSORS = new HashMap<>();

    /**
     * initializes the FONTS. This also tries to find all founds automatically via their font file
     */
    public static void init(@NonNull Context ctx) {
        if (!INIT_DONE) {
            String[] fonts = GenericsUtil.getDefinedFonts(ctx);
            for (String fontsClassPath : fonts) {
                try {
                    ITypeface typeface = (ITypeface) Class.forName(fontsClassPath).newInstance();
                    validateFont(typeface);
                    FONTS.put(typeface.getMappingPrefix(), typeface);
                } catch (Exception e) {
                    Log.e(TAG, "Can't init: " + fontsClassPath);
                }
            }
            String[] processors = GenericsUtil.getDefinedProcessors(ctx);
            for (String processorClassPath : processors) {
                try {
                    IconicsAnimationProcessor processor = (IconicsAnimationProcessor) Class.forName(processorClassPath).newInstance();
                    registerProcessor(processor);
                } catch (Exception e) {
                    Log.e(TAG, "Can't init: " + processorClassPath);
                }
            }
            INIT_DONE = true;
        }
    }

    /**
     * this makes sure the FONTS are initialized. If the given fonts Map is empty we set the initialized FONTS on it
     */
    private static HashMap<String, ITypeface> init(@NonNull Context ctx,
                                                   @NonNull HashMap<String, ITypeface> fonts) {
        init(ctx);
        if (fonts == null || fonts.size() == 0) {
            fonts = FONTS;
        }
        return fonts;
    }

    /**
     * This allows to mark the initialization as done, even if `init(Context ctx)` was not called prior.
     * It requires at least one font to be registered manually in the `Application.onCreate()` via `registerFont`.
     */
    public static void markInitDone() {
        if (FONTS.size() == 0) {
            throw new IllegalArgumentException("At least one font needs to be registered first via `registerFont`.");
        } else {
            INIT_DONE = true;
        }
    }

    /**
     * Test if the icon exists in the currently loaded fonts
     *
     * @param context A context to access application resources
     * @param icon    The icon to verify
     * @return true if the icon is available
     */
    public static boolean iconExists(@NonNull Context context, @NonNull String icon) {
        try {
            ITypeface font = findFont(context, icon.substring(0, 3));
            icon = icon.replace("-", "_");
            font.getIcon(icon);
            return true;
        } catch (Exception ignore) {
            return false;
        }
    }

    /**
     * Registers a fonts into the FONTS array for performance
     */
    public static boolean registerFont(@NonNull ITypeface font) {
        validateFont(font);

        FONTS.put(font.getMappingPrefix(), font);
        return true;
    }

    public static void registerProcessor(@NonNull IconicsAnimationProcessor processor) {
        PROCESSORS.put(processor.animationTag(), processor.getClass());
    }

    @Nullable
    public static IconicsAnimationProcessor findProcessor(@NonNull Context ctx,
                                                          @NonNull String animationTag) {
        init(ctx);

        Class<? extends IconicsAnimationProcessor> processorClass = PROCESSORS.get(animationTag);
        if (processorClass != null) {
            try {
                return processorClass.newInstance();
            } catch (IllegalAccessException e) {
                Log.d(Iconics.TAG, "Can't create processor for animation tag " + animationTag, e);
            } catch (InstantiationException e) {
                Log.d(Iconics.TAG, "Can't create processor for animation tag " + animationTag, e);
            }
        }
        return null;
    }

    /**
     * Perform a basic sanity check for a font.
     */
    private static void validateFont(@NonNull ITypeface font) {
//        if (font.getMappingPrefix().length() != 3) {
//            throw new IllegalArgumentException("The mapping prefix of a font must be three characters long.");
//        }
    }

    /**
     * return all registered FONTS
     */
    @NonNull
    public static Collection<ITypeface> getRegisteredFonts(@NonNull Context ctx) {
        init(ctx);
        return FONTS.values();
    }

    /**
     * tries to find a font by its key in all registered FONTS
     */
    @Nullable
    public static ITypeface findFont(@NonNull Context ctx, @NonNull String key) {
        init(ctx);
        return FONTS.get(key);
    }

    /**
     * fetches the font from the Typeface of an IIcon
     */
    @NonNull
    public static ITypeface findFont(@NonNull IIcon icon) {
        return icon.getTypeface();
    }

    private Iconics() {
        // Prevent instantiation
    }

    /**
     * Creates a new SpannableStringBuilder and will iterate over the textSpanned once and copy over
     * all characters, it will also directly replace icon font placeholders with the correct mapping.
     * Afterwards it will apply the styles
     */
    @NonNull
    public static Spanned style(@NonNull Context ctx, @NonNull Spanned textSpanned) {
        return style(ctx, null, textSpanned, null, null);
    }

    /**
     * Creates a new SpannableStringBuilder and will iterate over the textSpanned once and copy over
     * all characters, it will also directly replace icon font placeholders with the correct mapping.
     * Afterwards it will apply the styles
     */
    @NonNull
    public static Spanned style(@NonNull Context ctx,
                                @Nullable HashMap<String, ITypeface> fonts,
                                @NonNull Spanned textSpanned,
                                @Nullable List<CharacterStyle> styles,
                                @Nullable HashMap<String, List<CharacterStyle>> stylesFor) {
        fonts = init(ctx, fonts);

        //find all icons which should be replaced with the iconFont
        TextStyleContainer textStyleContainer = IconicsUtils.findIcons(textSpanned, fonts);

        //create spannableString to set the spans on
        SpannableString sb = SpannableString.valueOf(textStyleContainer.spannableStringBuilder);

        //set all the icons and styles
        IconicsUtils.applyStyles(ctx, sb, textStyleContainer.styleContainers, styles, stylesFor);

        return sb;
    }

    /**
     * Iterates over the editable once and replace icon font placeholders with the correct mapping.
     * Afterwards it will apply the styles
     */
    public static void styleEditable(@NonNull Context ctx, @NonNull Editable editable) {
        styleEditable(ctx, null, editable, null, null);
    }

    /**
     * Iterates over the editable once and replace icon font placeholders with the correct mapping.
     * Afterwards it will apply the styles
     */
    public static void styleEditable(@NonNull Context ctx,
                                     @Nullable HashMap<String, ITypeface> fonts,
                                     @NonNull Editable textSpanned,
                                     @Nullable List<CharacterStyle> styles,
                                     @Nullable HashMap<String, List<CharacterStyle>> stylesFor) {
        fonts = init(ctx, fonts);

        //find all icons which should be replaced with the iconFont
        List<StyleContainer> styleContainers = IconicsUtils.findIconsFromEditable(textSpanned, fonts);

        //set all the icons and styles
        IconicsUtils.applyStyles(ctx, textSpanned, styleContainers, styles, stylesFor);
    }

    public static class IconicsBuilderString {
        @NonNull
        private Context ctx;
        @NonNull
        private Spanned text;
        @NonNull
        private List<CharacterStyle> withStyles;
        @NonNull
        private HashMap<String, List<CharacterStyle>> withStylesFor;
        @NonNull
        private List<ITypeface> fonts;

        public IconicsBuilderString(@NonNull Context ctx,
                                    @NonNull List<ITypeface> fonts,
                                    @NonNull Spanned text,
                                    @NonNull List<CharacterStyle> styles,
                                    @NonNull HashMap<String, List<CharacterStyle>> stylesFor) {
            this.ctx = ctx;
            this.fonts = fonts;
            this.text = text;
            this.withStyles = styles;
            this.withStylesFor = stylesFor;
        }

        @NonNull
        public Spanned build() {
            HashMap<String, ITypeface> mappedFonts = new HashMap<>();
            for (ITypeface font : fonts) {
                mappedFonts.put(font.getMappingPrefix(), font);
            }
            return Iconics.style(ctx, mappedFonts, text, withStyles, withStylesFor);
        }
    }

    public static class IconicsBuilderView {
        @NonNull
        private Context ctx;
        @NonNull
        private TextView view;
        @NonNull
        private List<CharacterStyle> withStyles;
        @NonNull
        private HashMap<String, List<CharacterStyle>> withStylesFor;
        @NonNull
        private List<ITypeface> fonts;

        public IconicsBuilderView(@NonNull Context ctx,
                                  @NonNull List<ITypeface> fonts,
                                  @NonNull TextView view,
                                  @NonNull List<CharacterStyle> styles,
                                  @NonNull HashMap<String, List<CharacterStyle>> stylesFor) {
            this.ctx = ctx;
            this.fonts = fonts;
            this.view = view;
            this.withStyles = styles;
            this.withStylesFor = stylesFor;
        }

        public void build() {
            HashMap<String, ITypeface> mappedFonts = new HashMap<>();
            for (ITypeface font : fonts) {
                mappedFonts.put(font.getMappingPrefix(), font);
            }

            //DO NOT STYLE EDITABLE (comes from EditText) as this causes bad issues with the cursor!
            /*
            if (view.getText() instanceof Editable) {
                Iconics.styleEditable(ctx, mappedFonts, (Editable) view.getText(), withStyles, withStylesFor);
            } else
            */
            if (view.getText() instanceof Spanned) {
                view.setText(Iconics.style(ctx, mappedFonts, (Spanned) view.getText(), withStyles, withStylesFor));
            } else {
                view.setText(Iconics.style(ctx, mappedFonts, new SpannableString(view.getText()), withStyles, withStylesFor));
            }

            if (view instanceof Button) {
                view.setAllCaps(false);
            }
        }
    }

    public static class IconicsBuilder {
        @NonNull
        private final List<CharacterStyle> styles = new LinkedList<>();
        @NonNull
        private final HashMap<String, List<CharacterStyle>> stylesFor = new HashMap<>();
        @NonNull
        private final List<ITypeface> fonts = new LinkedList<>();
        @NonNull
        private Context ctx;

        public IconicsBuilder() {
        }

        @NonNull
        public IconicsBuilder ctx(@NonNull Context ctx) {
            this.ctx = ctx;
            return this;
        }

        @NonNull
        public IconicsBuilder style(@NonNull CharacterStyle... styles) {
            if (styles != null && styles.length > 0) {
                Collections.addAll(this.styles, styles);
            }
            return this;
        }

        /**
         * this method allows you to apply additional styles on icons , just provide all
         * CharacterStyles you want to apply on the given IIcon
         */
        @NonNull
        public IconicsBuilder styleFor(@NonNull IIcon styleFor,
                                       @NonNull CharacterStyle... styles) {
            return styleFor(styleFor.getName(), styles);
        }

        /**
         * this method allows you to apply additional styles on icons , just provide all
         * CharacterStyles you want to apply on the given icon (by it's name faw-android)
         */
        @NonNull
        public IconicsBuilder styleFor(@NonNull String styleFor,
                                       @NonNull CharacterStyle... styles) {
            styleFor = styleFor.replace("-", "_");

            if (!stylesFor.containsKey(styleFor)) {
                this.stylesFor.put(styleFor, new LinkedList<>());
            }

            if (styles != null && styles.length > 0) {
                for (CharacterStyle style : styles) {
                    this.stylesFor.get(styleFor).add(style);
                }
            }
            return this;
        }

        /**
         * adds additional fonts which should be used to apply the icons
         */
        @NonNull
        public IconicsBuilder font(@NonNull ITypeface font) {
            this.fonts.add(font);
            return this;
        }

        /**
         * defines where the icons should be applied to
         */
        @NonNull
        public IconicsBuilderString on(@NonNull Spanned on) {
            return new IconicsBuilderString(ctx, fonts, on, styles, stylesFor);
        }

        /**
         * defines where the icons should be applied to
         */
        @NonNull
        public IconicsBuilderString on(@NonNull String on) {
            return on(new SpannableString(on));
        }

        /**
         * defines where the icons should be applied to
         */
        @NonNull
        public IconicsBuilderString on(@NonNull CharSequence on) {
            return on(on.toString());
        }

        /**
         * defines where the icons should be applied to
         */
        @NonNull
        public IconicsBuilderString on(@NonNull StringBuilder on) {
            return on(on.toString());
        }

        /**
         * defines where the icons should be applied to
         */
        @NonNull
        public IconicsBuilderView on(@NonNull TextView on) {
            return new IconicsBuilderView(ctx, fonts, on, styles, stylesFor);
        }

        /**
         * defines where the icons should be applied to
         */
        @NonNull
        public IconicsBuilderView on(@NonNull Button on) {
            return new IconicsBuilderView(ctx, fonts, on, styles, stylesFor);
        }
    }
}
