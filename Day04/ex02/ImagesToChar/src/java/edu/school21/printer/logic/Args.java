package edu.school21.printer.logic;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;

@Parameters(separators = "=")
public class Args {

    @Parameter(names = "--white", required = true)
    private String white;

    @Parameter(names = "--black", required = true)
    private String black;

    public String getWhite() {
        return white;
    }

    public String getBlack() {
        return black;
    }
}