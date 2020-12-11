package ru.ezhov.dictionary.view.window;

import javax.swing.*;

public interface WindowsGenerator {
    /**
     * Важно! Реализация генератора должна сама отвечать за то,
     * когда необходимо менять окно и необходимо ли вообще.
     */
    JWindow generate();
}
