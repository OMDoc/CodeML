%!
% stamp.pro
% Put a stamp onto upper left corner of the page.
%
% $Log$
% Revision 1.1  2003/11/21 13:05:34  albu
% added missing file
%
% Revision 1.1  2001/07/14 13:01:01  stwa
% *** empty log message ***
%
% Revision 1.1  2001/05/03 09:44:14  albu
% *** empty log message ***
%
% Revision 1.1  1998/01/06 14:08:14  niehren
% initial version
%
% Revision 1.1  91/02/17  01:51:12  schwarze
% Initial revision
% 

/inch { 72 mul } def
/mm { inch 25.4 div } def

/StampText (Draft) def
%/StampDate (12.01.91) def

/StampSize 24 def
/StampFont /Helvetica-Bold findfont StampSize scalefont def
/DateSize 10 def
/DateFont /Helvetica-Bold findfont DateSize scalefont def
/Gray .5 def
/LMargin 10 mm def
/TMargin 10 mm def
%/PaperHeight 297 mm def
/PaperHeight 280 mm def
/Angle 30 def

/Randomize { rand 2147483647 div .6 mul .7 add mul cvi } def 

/bop-hook {
    gsave

    /TheAngle Angle Randomize def
    /TheLMargin LMargin Randomize def 
    /TheTMargin TMargin Randomize def 

    StampFont setfont
    /StampWidth StampText stringwidth pop def

    TheLMargin StampSize TheAngle sin mul add % x
    PaperHeight TheTMargin sub StampWidth TheAngle sin mul sub 
    StampSize TheAngle cos mul sub % y

    translate
    TheAngle rotate
    Gray setgray
    0 0 moveto StampText show

    userdict /StampDate known { 
	DateFont setfont
	StampDate stringwidth pop 
	StampWidth exch sub 2 div DateSize -1.2 mul moveto
	StampDate show
    } if

    StampWidth 2 div dup StampSize 0.2 mul translate

    1.35 mul dup StampSize 1.35 mul scale
    newpath 1 exch div setlinewidth 0 0 1 0 360 arc stroke

    grestore
} def

%bop-hook
%showpage

