package com.example.android.BluetoothChat.Interpreters;

import android.test.AndroidTestCase;
import android.test.AndroidTestRunner;

import com.example.android.BluetoothChat.Interpreters.LexicalAnalyzer;

import junit.framework.TestCase;

import org.junit.Test;
import org.junit.runner.JUnitCore;
import org.junit.runner.RunWith;

import java.util.ArrayList;

/**
 * Created by tekitoh on 15/10/27.
 */
public class LexicalAnalyzerTest extends TestCase {

    public void main(String[] args) {
        JUnitCore.main(LexicalAnalyzerTest.class.getName());
    }

    public void testLexString() throws Exception {
        assertEquals(true, true);

        LexicalAnalyzer lexicalAnalyzer = new LexicalAnalyzer("前進");
        lexicalAnalyzer.lexString();
        ArrayList<TokenStorage> tokens = lexicalAnalyzer.getTokens();

        assertEquals(tokens.get(0).getNumber(), LexicalAnalyzer.AHEAD);
    }
}