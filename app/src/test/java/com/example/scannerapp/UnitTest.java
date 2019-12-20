package com.example.scannerapp;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */


public class UnitTest {
    @Test
    public void barcode_isCorrect(String barcode) {

        assertTrue(this.checkValidTrackingNumber(barcode));
    }
    public boolean checkValidTrackingNumber(String tn){
        String digits = tn.substring(2);
        if ((tn.startsWith("EXP") || tn.startsWith("INT") || tn.startsWith("ECO")) && (this.isInteger(digits) && digits.length() == 9)) {
            return true;
        }
        return false;
    }
    public boolean isInteger( String input ) {
        try {
            Integer.parseInt( input );
            return true;
        }
        catch( Exception e ) {
            return false;
        }
    }
}