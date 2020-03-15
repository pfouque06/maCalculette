package maCalculette;

import java.util.LinkedHashSet;

import javaTools.GetOpts;
import javaTools.Logger;

public class Main {

	// globals
	// logger
	static Logger logger;

	// public global variables
	public static boolean logging = false;

	static String[] optionArray = {
	"##### DO NOT FORGET FOLLOWING HEADER LINE !! #####",
	"TYPE:KEY:KEYWORD:VALUENAME:VALUETYPE:DETAIL:ACTION:",
	"F:h:help:usage:-:prints this help message:true:",
	"F:L:log:logging:boolean:set logging mode (to console/terminal):true:",
	};
	public static GetOpts options;

	public static boolean setOptions(String[] pArgs) {
		// System.out.println("optionTable=\n"+options.optionTable_toString());
		// parse options pArgs
		if (!options.setOptionList(pArgs)) {
			System.out.println("Parsing error, please retry or use -h, --help to get usage ...");
			return false;
		}
		// System.out.println("optionList=\n"+options.optionList_toString());
		// set options
		if (!setOptions(options.getOptionList())) {
			System.out.println(options.getUsage()); // display usage is requested
			return false;
		}
		// System.out.println("options:" + optsToString());
		return true;
	}

	public static boolean setOptions(LinkedHashSet<String[]> pList) {
		// Loop on each options of pList
		for (String[] fields : pList) {
			// System.out.println("fields="+fields.toString() );
			switch (fields[2]) {
			case "logging": // System.out.println("logging to System.out");
				logging = fields[3].equals("true");
				break;
			case "usage":
				return false;
			default:
				System.err.println("Error: option > valuename unknown");
			}
		}
		return true;
	}

	public static String optsToString() {

		String buffer = "";

		buffer += "logging: " + (logging ? "ON" : "OFF");
		return buffer;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		// initiate getOpts options and parse args :
		options = new GetOpts(optionArray, args);
		// check options status and set options according to args parsing
		if ( ! setOptions(options.getOptionList()) ) {
			System.out.println(options.getUsage()); // display usage is requested
			return;
		}

		// init logger
		logger = new Logger(logging ? "syso" : "");
		logger.logging("options:" + optsToString());

		Fenetre frame = new Fenetre();
	}

}
