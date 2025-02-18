package com.bank.guibank.util;

import javafx.scene.control.TextField;

public class NumericField extends TextField {
    @Override
    public void replaceText(int start, int end, String text)
    {
        if (validate(text))
        {
            super.replaceText(start, end, text);
        }
    }

    @Override
    public void replaceSelection(String text)
    {
        if (validate(text))
        {
            super.replaceSelection(text);
        }
    }

    private boolean validate(String text)
    {
        return text.matches("-?(([1-9][0-9]*)|0)?(\\.[0-9]*)?");
    }
}