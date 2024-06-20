package com.protocol.edm.logo;

import static com.protocol.edm.log.Color.*;

/**
 * line length = 78
 */
public class ApplicationInfo {
    public static String getLogo() {
        StringBuilder logo = new StringBuilder();
        logo.append("\n");
        logo.append(blue + "######" + exit + "                                                                              " + red + "######" + exit + "\n");
        logo.append(blue + "######" + exit + "                                                                              " + red + "######" + exit + "\n");
        logo.append(blue + "######" + exit + "              " + yellow + "   # " + exit + "                                                           " + red + "######" + exit + "\n");
        logo.append(blue + "######" + exit + "              " + yellow + " ### " + exit + "                                                           " + red + "######" + exit + "\n");
        logo.append(blue + "######" + exit + "              " + yellow + " #   " + exit + "    ########  #######     ### ## ### ## ###                " + red + "######" + exit + "\n");
        logo.append(blue + "######" + exit + "                      ##        ###   ###   ###    ###    ###                 " + red + "######" + exit + "\n");
        logo.append(blue + "######" + exit + "                     ##        ###     ##  ###    ###    ###                  " + red + "######" + exit + "\n");
        logo.append(blue + "######" + exit + "                    ########  ###     ##  ###    ###    ###                   " + red + "######" + exit + "\n");
        logo.append(blue + "######" + exit + "                   ##        ###     ##  ###    ###    ###                    " + red + "######" + exit + "\n");
        logo.append(blue + "######" + exit + "                  ##        ###   ###   ###    ###    ###                     " + red + "######" + exit + "\n");
        logo.append(blue + "######" + exit + "                 ########  #######     ###    ###    ###  " + yellow + "    #               " + red + "######" + exit + "\n");
        logo.append(blue + "######" + exit + "                                                          " + yellow + "  ###               " + red + "######" + exit + "\n");
        logo.append(blue + "######" + exit + "                                                          " + yellow + "  #                 " + red + "######" + exit + "\n");
        logo.append(blue + "######" + exit + "                                  started!!                                   " + red + "######" + exit + "\n");
        logo.append(blue + "######" + exit + "                                                                              " + red + "######" + exit + "\n");
        return logo.toString();
    }

    public static String addCurrApplicationInfo(String info) {
        return "###### " + String.format("%70s", info) + "        ######";
    }
}
