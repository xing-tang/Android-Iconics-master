package com.open.iconics.iconfont;

import com.open.iconics.typeface.IIcon;
import com.open.iconics.typeface.ITypeface;

/**
 * IconFont
 */
public enum ChangbaFont implements IIcon {

    font_openarrows('\ue610'),
    font_album_num('\ue611'),
    font_close_arrows('\ue612'),
    font_attention('\ue613'),
    font_rightarrows_gray('\ue614'),
    font_singasong('\ue615'),
    font_earphone_num('\ue616'),
    font_edit('\ue617'),
    font_person_num('\ue618'),
    font_share('\ue619'),
    font_liked('\ue61a'),
    font_autostop('\ue61b'),
    font_collected('\ue61c'),
    font_topic('\ue61d'),
    font_createplaylist('\ue61e'),
    font_nextsong('\ue61f'),
    font_canceltop('\ue620'),
    font_lastsong('\ue621'),
    font_playbtn('\ue622'),
    font_tabgift('\ue623'),
    font_cycle('\ue624'),
    font_noexport('\ue625'),
    font_unlike('\ue626'),
    font_gift_gray('\ue627'),
    font_cancelpass('\ue628'),
    font_singleloop('\ue629'),
    font_noupload('\ue62a'),
    font_editlist('\ue62b'),
    font_emoji('\ue62c'),
    font_pass('\ue62d'),
    font_report('\ue62e'),
    font_danmu_on('\ue62f'),
    font_danmu_off('\ue630'),
    font_onrecommend('\ue631'),
    font_noexportring('\ue632'),
    font_ontop('\ue633'),
    font_stopbtn('\ue634'),
    font_messengerbottle('\ue635'),
    font_upload('\ue636'),
    font_songhomepage('\ue637'),
    font_ring('\ue638'),
    font_uncollect('\ue639'),
    font_font('\ue63a'),
    font_random('\ue63b'),
    font_hechang('\ue63c');

    public char character;
    // remember the typeface so we can use it later
    private static ITypeface typeface;

    ChangbaFont(char character) {
        this.character = character;
    }

    @Override
    public String getFormattedName() {
        return "{" + name() + "}";
    }

    @Override
    public char getCharacter() {
        return character;
    }

    @Override
    public String getName() {
        return name();
    }

    @Override
    public ITypeface getTypeface() {
        if (typeface == null) {
            typeface = new ChangbaIconFont();
        }
        return typeface;
    }
}