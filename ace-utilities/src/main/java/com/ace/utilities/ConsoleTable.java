package com.ace.utilities;

import com.ace.utilities.impl.consoleTable.ConsoleTableImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import com.ace.utilities.impl.consoleTable.table.Cell;


/**
 * @Classname: aaa
 * @Date: 2023/7/19 下午 12:28
 * @Author: kalam_au
 * @Description:
 */


public class ConsoleTable {
    private static final Logger log = LogManager.getLogger(ConsoleTable.class.getName());

    //打印console table
    public static void println(List<String> header, List<String[]> body) {
        List<Object> content = setContent(header, body);
        new ConsoleTableImpl.ConsoleTableBuilder().addHeaders((List<Cell>) content.get(0))
                .addRows((List<List<Cell>>) content.get(1)).build().print(); // default
    }

    public static void println(List<String[]> body) {
        new ConsoleTableImpl.ConsoleTableBuilder().addRows(setBody(body)).build().print();//no header
    }

    private static List<Object> setContent(List<String> header, List<String[]> body) {
        List<Object> content = new ArrayList<>();

        List<Cell> contentHeader = new ArrayList<>();
        for (String h : header) {
            contentHeader.add(new Cell(h));
        }

        List<List<Cell>> contentBody = new ArrayList<>();
        for (String[] ss : body) {
            List<Cell> contentCell = new ArrayList<>();
            for (String s : ss) {
                Cell cell = new Cell(s);
                contentCell.add(cell);
            }
            contentBody.add(contentCell);
        }
        content.add(contentHeader);
        content.add(contentBody);
        return content;
    }
    

    private static List<List<Cell>> setBody(List<String[]> body) {
        List<List<Cell>> contentBody = new ArrayList<>();
        for (String[] ss : body) {
            List<Cell> contentCell = new ArrayList<>();
            for (String s : ss) {
                Cell cell = new Cell(s);
                contentCell.add(cell);
            }
            contentBody.add(contentCell);
        }
        return contentBody;
    }


    public static void main(String[] args) {
        List<String> header = new ArrayList<>();
        header.add("name");
        header.add("email");
        header.add("tel");

        List<String[]> body = new ArrayList<>();
        body.add(new String[]{"kat", "kat@gimal.com", "54321"});
        body.add(new String[]{"ashe", "kaaaat@gimal.com", "90809890"});
        body.add(new String[]{"aaa", "vvvv@gimal.com", "54322341"});

        println(header, body);
        //   println(body);

    }

}

