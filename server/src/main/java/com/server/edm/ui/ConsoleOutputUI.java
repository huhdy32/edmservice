package com.server.edm.ui;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class ConsoleOutputUI implements OutputUI {
    public static final String black    = "\u001B[30m" ;
    public static final String red      = "\u001B[31m" ;
    public static final String green    = "\u001B[32m" ;
    public static final String yellow   = "\u001B[33m" ;
    public static final String blue     = "\u001B[34m" ;
    public static final String purple   = "\u001B[35m" ;
    public static final String cyan     = "\u001B[36m" ;
    public static final String white     = "\u001B[37m" ;

    public static final String exit     = "\u001B[0m" ;
    private final BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(System.out));

    @Override
    public void printLogo() {
        final StringBuilder sb = new StringBuilder();
        appendLogo(sb);
        print(sb.toString());
    }

    @Override
    public void printInfo(final String info) {
        print(info);
    }

    private void print(final String data) {
        try {
            bufferedWriter.write(data);
            bufferedWriter.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void appendLogo(final StringBuilder data) {
        data.append("\n");
        data.append(blue + "######" + exit + "                                                                              " + red + "######" + exit + "\n");
        data.append(blue + "######" + exit + "                                                                              " + red + "######" + exit + "\n");
        data.append(blue + "######" + exit + "              " + yellow + "   # "+exit+"                                                           " + red + "######" + exit + "\n");
        data.append(blue + "######" + exit + "              " + yellow + " ### "+exit+"                                                           " + red + "######" + exit + "\n");
        data.append(blue + "######" + exit + "              " + yellow + " #   "+exit+"    ########  #######     ### ## ### ## ###                " + red + "######" + exit + "\n");
        data.append(blue + "######" + exit + "                      ##        ###   ###   ###    ###    ###                 " + red + "######" + exit + "\n");
        data.append(blue + "######" + exit + "                     ##        ###     ##  ###    ###    ###                  " + red + "######" + exit + "\n");
        data.append(blue + "######" + exit + "                    ########  ###     ##  ###    ###    ###                   " + red + "######" + exit + "\n");
        data.append(blue + "######" + exit + "                   ##        ###     ##  ###    ###    ###                    " + red + "######" + exit + "\n");
        data.append(blue + "######" + exit + "                  ##        ###   ###   ###    ###    ###                     " + red + "######" + exit + "\n");
        data.append(blue + "######" + exit + "                 ########  #######     ###    ###    ###  " + yellow + "    #               " + red + "######" + exit + "\n");
        data.append(blue + "######" + exit + "                                                          " + yellow + "  ###               " + red + "######" + exit + "\n");
        data.append(blue + "######" + exit + "                                                          " + yellow + "  #                 " + red + "######" + exit + "\n");
        data.append(blue + "######" + exit + "                                  started!!                                   " + red + "######" + exit + "\n");
        data.append(blue + "######" + exit + "                                                                              " + red + "######" + exit + "\n");
        data.append("\n");
    }
}
