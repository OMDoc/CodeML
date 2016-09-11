/*************************************************************************

         name: betaConversion.pl (Chapter 2)
      version: March 11, 1998
  description: Implementation of Beta-Conversion
      authors: Patrick Blackburn & Johan Bos
 
*************************************************************************/

:- module(betaConversion,[betaConvertList/2,betaConvert/2]).

:- ensure_loaded(comsemOperators).

:- use_module(comsemPredicates,[compose/3,substitute/4]).


/*========================================================================
   Beta-Conversion
========================================================================*/



betaConvert(Var,Result):-
	var(Var), !, 
	Result=Var.

betaConvert(Functor @ Arg,Result):-
	compound(Functor),
	betaConvert(Functor,lambda(X,Formula)), !,   
	substitute(Arg,X,Formula,BetaConverted), 
	betaConvert(BetaConverted,Result).

betaConvert(Formula,Result):-
	compose(Formula,Functor,Formulas),
	betaConvertList(Formulas,ResultFormulas),
	compose(Result,Functor,ResultFormulas).

betaConvertList([],[]).

betaConvertList([Formula|Others],[Result|ResultOthers]):-
	betaConvert(Formula,Result),
	betaConvertList(Others,ResultOthers).
