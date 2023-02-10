package com.github.robertvolkmann.demo;

import java.io.PrintWriter;

public class TableRowWriter {

    private final PrintWriter out;

    public TableRowWriter(PrintWriter out) {
        this.out = out;
    }

    public void printRow(Object... columns) {
        out.println("<tr>");

        for (var column : columns) {
            out.println("<td>" + column + "</td>");
        }

        out.println("</tr>");
    }

}
