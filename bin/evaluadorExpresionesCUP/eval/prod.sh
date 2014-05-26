#!/bin/bash
java -cp ../../../libs/cup.jar java_cup.Main -parser AnalizadorSintacticoTiny -dump_states -symbols ClaseLexica -nopositions Eval.cup 

