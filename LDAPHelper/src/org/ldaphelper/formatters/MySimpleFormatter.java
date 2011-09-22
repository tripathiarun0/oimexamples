/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.ldaphelper.formatters;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.LogRecord;
import java.util.logging.SimpleFormatter;

/**
 *
 * this is a java logging formatter if you would rather use that.
 */
public class MySimpleFormatter extends SimpleFormatter {

    @Override
    public String format(LogRecord record)
    {
        //System.out.println("FORMATTER");

        Date today = Calendar.getInstance().getTime();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh.mm.ss");
        String mydate = formatter.format(today);


        StringBuffer sb = new StringBuffer();

        String lineSep = System.getProperty("line.seperator");

        sb.append(mydate)
          .append(" ")
          .append(record.getLevel())
          .append(" ")
          .append(record.getSourceClassName())
          .append(" ")
          .append(record.getMessage())
          .append("\r\n");

        if (record.getThrown() != null) {
            try {
                StringWriter sw = new StringWriter();
                PrintWriter pw = new PrintWriter(sw);
                record.getThrown().printStackTrace(pw);
                pw.close();
                sb.append(sw.toString());
            } catch (Exception ex) {
                // ignore
            }
        }

        return sb.toString();

    }


}
