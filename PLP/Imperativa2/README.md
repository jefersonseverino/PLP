**BNF**

Programa ::= Comando

Comando ::= Atribuicao
       | ComandoDeclaracao
       | While
       | IfThenElse
       | IO
       | Comando “;” Comando
       | Skip
       | ChamadaProcedimento

Skip ::=

Atribuicao ::= Id “:=” Expressao

Expressao ::= Valor
       | ExpUnaria | ExpBinaria | Id | Expressao "." Id

Valor ::= ValorConcreto

ValorConcreto ::= ValorInteiro
       | ValorBooleano
       | ValorString
       | ValorTimestamp
       | ValorDuration

ValorTimestamp ::= "@" ValorInteiro "-" ValorInteiro "-" ValorInteiro
                    "T" ValorInteiro ":" ValorInteiro ":" ValorInteiro [ ValorTimezone ] 

ValorDuration ::= ValorInteiro "h" [ ValorInteiro "m" [ ValorInteiro "s" ] ]

ValorTimezone ::= ValorString
                | ("+" | "-") ValorInteiro ":" ValorInteiro

ExpUnaria ::= “-“ Expressao
       | “not” Expressao
       | “length” Expressao

ExpBinaria ::= Expressao “+” Expressao
       | Expressao “-“ Expressao
       | Expressao “and” Expressao
       | Expressao “or” Expressao
       | Expressao “==” Expressao
       | Expressao “++” Expressao
       | Expressao "<" Expressao 
       | Expressao ">" Expressao  
       | Expressao "!=" Expressao  
       | Expressao ">=" Expressao  
       | Expressao "<=" Expressao 
       | Expressao "*" Expressao  
       | Expressao "/" Expressao 
       | Expressao "in" ValorTimezone

ComandoDeclaracao ::= “{“ Declaracao “;” Comando “}”

Declaracao ::= DeclaracaoVariavel
       | DeclaracaoProcedimento
       | DeclaracaoComposta

DeclaracaoVariavel ::= “var” Id “=” Expressao

DeclaracaoComposta ::= Declaracao “,” Declaracao

DeclaracaoProcedimento ::= “proc” Id “(“ [ ListaDeclaracaoParametro ] “)” “{“ Comando “}”

ListaDeclaracaoParametro ::= Tipo Id
       | Tipo Id “,” ListaDeclaracaoParametro

Tipo ::= “string” | “int” | “boolean” 

While ::= “while” Expressao “do” Comando

IfThenElse ::= “if” Expressao “then” Comando “else” Comando

IO ::= “write” “(“ Expressao “)”
       | “read” “(“ Id “)”

ChamadaProcedimento ::= “call” Id “(“ [ ListaExpressao ] “)”

ListaExpressao ::= Expressao | Expressao, ListaExpressao
