package com.ed522.svgmassconvert;

import javafx.application.Application;

public class Launcher {
    public static void main(String[] args) {
        if (args.length == 1 && args[0].equals("--about")) {
            System.out.println("(c) ed522 2026 - MIT License. See LICENSE for licensing info");
            System.out.println("A copy may also be found at https://opensource.org/license/mit");
            System.out.println();
            System.out.println("This project is dependent on the following works:");
            System.out.println(" - OpenJDK: GPL v2 w/ Classpath exception");
            System.out.println(" - OpenJFX: GPL v2 w/ Classpath exception");
            System.out.println(" - JSVG: MIT License");
            System.out.println(" - Maven: Apache license v2");
            System.out.println();
            System.out.println("The licenses may be found here:");
            System.out.println(" > OpenJDK and OpenJFX: https://openjdk.org/legal/gplv2+ce.html");
            System.out.println(" > JSVG: https://opensource.org/license/mit");
            System.out.println(" > Maven: https://www.apache.org/licenses/LICENSE-2.0");
        } else {
            System.out.println("Launching - use --about for copyright");
            Application.launch(ConvertApplication.class, args);
        }
    }
}
