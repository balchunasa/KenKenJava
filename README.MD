KenKen Puzzles are text files formatted accordingly:

int (dimensions of square puzzle)
int (number of bolded groups)
<group 1>
<group 2>
<group 3>...

Where <group i> is formatted as row col row col row col ... operator total
The labeled cell is the first one provided, and the rows and columns are 0 indexed.
Operators are +, -, *, /. Subtraction and division only have two operands to avoid issues with order of operations.

To play, run KenKenDriver with an argument of the name of the puzzle to play!
e.g. java KenKenDriver ./puzzles/puzzle1