#!/bin/bash

# Gen ALex
java -cp ../../libs/jlex.jar JLex.Main AnalizadorLexicoTiny.l &&
mv AnalizadorLexicoTiny.l.java AnalizadorLexicoTiny.java &&

# Gen Asint
java -cp ../../libs/cup.jar java_cup.Main -parser AnalizadorSintacticoTiny -dump_states -symbols ClaseLexica -nopositions AnalizadorSintacticoTiny.cup

