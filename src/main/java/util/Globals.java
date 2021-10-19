package util;

import employee.Employee;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Contains all the utility functions
 */
public class Globals {

    public static final BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
    public static Employee loggedIn;

  /**
   * Prints the given values in a table format
   *
   * @param headers contains column headers of the table.
   * @param table contains the contents of table.
   * @return a table in the string format.
   */
  public static String printTable(ArrayList<String> headers, ArrayList<ArrayList<String>> table) {

        ArrayList<Integer> maxLength = new ArrayList<>();

        for (String header : headers) {
            maxLength.add(header.length());
        }

        for (ArrayList<String> temp : table) {
            for (int j = 0; j < temp.size(); j++) {
                //If the table content was longer then current maxLength - update it
                if (temp.get(j).length() > maxLength.get(j)) {
                    maxLength.set(j, temp.get(j).length());
                }
            }
        }

        StringBuilder sb = new StringBuilder();
        StringBuilder sbRowSep = new StringBuilder();
        String padder = "";
        String rowSeperator;

        int TABLEPADDING = 4;
        for(int i = 0; i < TABLEPADDING; i++){
            padder += " ";
        }

        //Create the rowSeparator
        for (Integer integer : maxLength) {
            sbRowSep.append("|");
            for (int j = 0; j < integer + (TABLEPADDING * 2); j++) {
//                char SEPARATOR_CHAR = '-';
                sbRowSep.append("-");
            }
        }
        sbRowSep.append("|");
        rowSeperator = sbRowSep.toString();

        sb.append(rowSeperator);
        sb.append("\n");
        //HEADERS
        sb.append("|");
        for(int i = 0; i < headers.size(); i++){
            sb.append(padder);
            sb.append(headers.get(i));
            //Fill up with empty spaces
            for(int k = 0; k < (maxLength.get(i)-headers.get(i).length()); k++){
                sb.append(" ");
            }
            sb.append(padder);
            sb.append("|");
        }
        sb.append("\n");
        sb.append(rowSeperator);
        sb.append("\n");

        //BODY
        for (ArrayList<String> tempRow : table) {
            //New row
            sb.append("|");
            for (int j = 0; j < tempRow.size(); j++) {
                sb.append(padder);
                sb.append(tempRow.get(j));
                //Fill up with empty spaces
                for (int k = 0; k < (maxLength.get(j) - tempRow.get(j).length()); k++) {
                    sb.append(" ");
                }
                sb.append(padder);
                sb.append("|");
            }
            sb.append("\n");
            sb.append(rowSeperator);
            sb.append("\n");
        }
        return sb.toString();
    }

}
