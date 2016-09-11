/* Java2CodeML */

import java.io.*;
import java.util.*;

import org.apache.commons.cli.*;

public class Java2CodeML
{
	public static void main(String[] args) {
		CommandLineParser clp = new PosixParser();
		CommandLine cl;

		Options options = new Options();
		options.addOption( "p", "presentation", false, "print presentation markup" );
		options.addOption( "r", "raw-code", false, "print raw code" );
		options.addOption( "h", "help", false, "display this help and exit" );

		try {
    			cl = clp.parse(options, args);
			String[] leftargs = cl.getArgs();

			if (leftargs.length != 1 || cl.hasOption("h")) {
				HelpFormatter formatter = new HelpFormatter();
				formatter.printHelp("java Java2CodeML FILE", options);
				System.exit(0);	
			}

			String fileName = leftargs[0];

			boolean wantPres = cl.hasOption("p");
			boolean wantRaw = cl.hasOption("r");

			if (!wantPres && !wantRaw) {
				wantPres = wantRaw = true;
			}
			
			System.out.println("<?xml version=\"1.0\"?>");
			System.out.println("<code type=\"multi\"><semantics>");

			if (wantPres) {
				Presentation presentation = new Presentation(fileName);
				System.out.println(presentation.getMarkup());	
			}

			if (wantRaw) {
				RawCode rawcode = new RawCode(fileName);
				System.out.println(rawcode.getMarkup());
			}
			System.out.println("</semantics></code>");
			
				
		} catch(Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
			System.exit(1);
		}
	}
}
