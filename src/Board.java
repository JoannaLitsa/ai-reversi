import java.util.ArrayList;

public class Board {
    public static final int X = 1;
    public static final int O = -1;
    public static final int EMPTY = 0;

    //Immediate move that lead to this board
    private Move lastMove;

    /* Variable containing who played last; whose turn resulted in this board
     * Even a new Board has lastLetterPlayed value; it denotes which player will play first
     */
    private int lastLetterPlayed;

    public int XDisks;
    public int ODisks;
    private int[][] gameBoard;

    public Board() {
        lastMove = new Move();
        lastLetterPlayed = O;
        gameBoard = new int[8][8];
        initBoard();
    }

    public Board(Board board) {
        lastMove = board.lastMove;
        lastLetterPlayed = board.lastLetterPlayed;
        XDisks = board.XDisks;
        ODisks = board.ODisks;
        gameBoard = new int[8][8];
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                gameBoard[i][j] = board.gameBoard[i][j];
            }
        }
    }

    public Move getLastMove() {
        return lastMove;
    }

    public int getLastLetterPlayed() {
        return lastLetterPlayed;
    }

    public int[][] getGameBoard() {
        return gameBoard;
    }

    public void setLastMove(Move lastMove) {
        this.lastMove.setRow(lastMove.getRow());
        this.lastMove.setCol(lastMove.getCol());
        this.lastMove.setValue(lastMove.getValue());
    }

    public void setLastLetterPlayed(int lastLetterPlayed) {
        this.lastLetterPlayed = lastLetterPlayed;
    }

    public void setGameBoard(int[][] gameBoard) {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                this.gameBoard[i][j] = gameBoard[i][j];
            }
        }
    }

    private void initBoard()
    {
        gameBoard[3][3]=O;
        gameBoard[4][4]=O;
        gameBoard[3][4]=X;
        gameBoard[4][3]=X;
        ODisks = 2;
        XDisks = 2;
    }

    //Make a move; it places and captures disks
    public void makeMove(int row, int col, int letter)
    {
        gameBoard[row][col] = letter;

        int moves = 0;
        // Check left
        for(int j = col - 1; j >= 0; j--)
        {
            if(gameBoard[row][j] == EMPTY)
                break;

            if(gameBoard[row][j] == -lastLetterPlayed)
            {
                if(j != col - 1)
                    while(++j < col)
                    {
                        gameBoard[row][j] = letter;
                        moves++;
                    }
                break;
            }
        }

        // Check right
        for(int j = col + 1; j < 8; j++)
        {
            if(gameBoard[row][j] == EMPTY)
                break;

            if(gameBoard[row][j] == -lastLetterPlayed)
            {
                if(j != col + 1)
                    while(--j > col)
                    {
                        gameBoard[row][j] = letter;
                        moves++;
                    }
                break;
            }
        }

        // Check up
        for(int i = row - 1; i >= 0; i--)
        {
            if(gameBoard[i][col] == EMPTY)
                break;

            if(gameBoard[i][col] == -lastLetterPlayed)
            {
                if(i != row - 1)
                    while(++i < row)
                    {
                        gameBoard[i][col] = letter;
                        moves++;
                    }
                break;
            }
        }

        // Check down
        for(int i = row + 1; i < 8; i++)
        {
            if(gameBoard[i][col] == EMPTY)
                break;

            if(gameBoard[i][col] == -lastLetterPlayed)
            {
                if(i != row + 1)
                    while(--i > row)
                    {
                        gameBoard[i][col] = letter;
                        moves++;
                    }
                break;
            }
        }

        {
            int i, j;

            // Check diagonally up/left
            for(i = row - 1, j = col - 1; (i >= 0) && (j >= 0); i--, j--)
            {
                if(gameBoard[i][j] == EMPTY)
                    break;

                if(gameBoard[i][j] == -lastLetterPlayed)
                {
                    if((i != row - 1) && (j != col - 1))
                        while((++i < row) && (++j < col))
                        {
                            gameBoard[i][j] = letter;
                            moves++;
                        }
                    break;
                }
            }

            // Check diagonally down/right
            for(i = row + 1, j = col + 1; (i < 8) && (j < 8); i++, j++)
            {
                if(gameBoard[i][j] == EMPTY)
                    break;

                if(gameBoard[i][j] == -lastLetterPlayed)
                {
                    if((i != row + 1) && (j != col + 1))
                        while((--i > row) && (--j > col))
                        {
                            gameBoard[i][j] = letter;
                            moves++;
                        }
                    break;
                }
            }

            // Check diagonally up/right
            for(i = row - 1, j = col + 1; (i >= 0) && (j < 8); i--, j++)
            {
                if(gameBoard[i][j] == EMPTY)
                    break;

                if(gameBoard[i][j] == -lastLetterPlayed)
                {
                    if((i != row - 1) && (j != col + 1))
                        while((++i < row) && (--j > col))
                        {
                            gameBoard[i][j] = letter;
                            moves++;
                        }
                    break;
                }
            }

            // Check diagonally down/left
            for(i = row + 1, j = col - 1; (i < 8) && (j >= 0); i++, j--)
            {
                if(gameBoard[i][j] == EMPTY)
                    break;

                if(gameBoard[i][j] == -lastLetterPlayed)
                {
                    if((i != row + 1) && (j != col - 1))
                        while((--i > row) && (++j < col))
                        {
                            gameBoard[i][j] = letter;
                            moves++;
                        }
                    break;
                }
            }
        }

        if(letter == X)
        {
            XDisks += moves + 1;
            ODisks -= moves;
        }
        else
        {
            ODisks += moves + 1;
            XDisks -= moves;
        }

        lastMove = new Move(row, col);
        lastLetterPlayed = letter;
    }

    public boolean isValidMove(int row, int col)
    {
        if ((row < 0) || (col < 0) || (row > 7) || (col > 7))
        {
            return false;
        }

        else if(gameBoard[row][col] != EMPTY)
        {
            return false;
        }

        // Check left
        for(int j = col - 1; j >= 0; j--)
        {
            if(gameBoard[row][j] == EMPTY)
                break;

            if(gameBoard[row][j] == -lastLetterPlayed)
            {
                if(j != col - 1)
                    return true;
                break;
            }
        }

        // Check right
        for(int j = col + 1; j < 8; j++)
        {
            if(gameBoard[row][j] == EMPTY)
                break;

            if(gameBoard[row][j] == -lastLetterPlayed)
            {
                if(j != col + 1)
                    return true;
                break;
            }
        }

        // Check up
        for(int i = row - 1; i >= 0; i--)
        {
            if(gameBoard[i][col] == EMPTY)
                break;

            if(gameBoard[i][col] == -lastLetterPlayed)
            {
                if(i != row - 1)
                    return true;
                break;
            }
        }

        // Check down
        for(int i = row + 1; i < 8; i++)
        {
            if(gameBoard[i][col] == EMPTY)
                break;

            if(gameBoard[i][col] == -lastLetterPlayed)
            {
                if(i != row + 1)
                    return true;
                break;
            }
        }

        {
            int i, j;

            // Check diagonally up/left
            for(i = row - 1, j = col - 1; (i >= 0) && (j >= 0); i--, j--)
            {
                if(gameBoard[i][j] == EMPTY)
                    break;

                if(gameBoard[i][j] == -lastLetterPlayed)
                {
                    if((i != row - 1) && (j != col - 1))
                        return true;
                    break;
                }
            }

            // Check diagonally down/right
            for(i = row + 1, j = col + 1; (i < 8) && (j < 8); i++, j++)
            {
                if(gameBoard[i][j] == EMPTY)
                    break;

                if(gameBoard[i][j] == -lastLetterPlayed)
                {
                    if((i != row + 1) && (j != col + 1))
                        return true;
                    break;
                }
            }

            // Check diagonally up/right
            for(i = row - 1, j = col + 1; (i >= 0) && (j < 8); i--, j++)
            {
                if(gameBoard[i][j] == EMPTY)
                    break;

                if(gameBoard[i][j] == -lastLetterPlayed)
                {
                    if((i != row - 1) && (j != col + 1))
                        return true;
                    break;
                }
            }

            // Check diagonally down/left
            for(i = row + 1, j = col - 1; (i < 8) && (j >= 0); i++, j--)
            {
                if(gameBoard[i][j] == EMPTY)
                    break;

                if(gameBoard[i][j] == -lastLetterPlayed)
                {
                    if((i != row + 1) && (j != col - 1))
                        return true;
                    break;
                }
            }
        }
        return false;
    }

    public ArrayList<Board> getChildren (int letter)
    {
        ArrayList<Board> children = new ArrayList<Board>();
        for (int row=0; row < 8; row++)
        {
            for (int col=0; col < 8; col++)
            {
                if(isValidMove(row, col))
                {
                    Board child = new Board(this);
                    child.makeMove(row, col, letter);
                    children.add(child);
                }

            }
        }
        return children;
    }

    public int evaluate()
    {
        int sum=0;

        for (int i=0; i<8; i++)
        {
            for (int j=0; j<8; j++)
            {
                sum = sum + gameBoard[i][j];
                //check for A squares
                if ((i==0 &&(j==2 || j==5)) || (i==7 &&(j==2 || j==5)) || (j==0 &&(i==2 || i==5)) || (j==7 &&(i==2 ||i==5))) {
                    sum = sum + gameBoard[i][j];
                }
                //check for B squares
                if ((i==0 &&(j==3 || j==4)) || (i==7 &&(j==3 || j==4)) || (j==0 &&(i==3 || i==4)) || (j==7 &&(i==3 ||i==4)))
                {
                    sum = sum + gameBoard[i][j];
                }
                //check for C squares
                if ((i==0 &&(j==1 || j==6)) || (i==7 &&(j==1 || j==6)) || (j==0 &&(i==1 || i==6)) || (j==7 &&(i==1 ||i==6)))
                {
                    sum = sum - gameBoard[i][j]*3;
                }
                //check for X squares
                if ((i==1 && j==1) || (i==6 && j==1) || (i==1 && j==6) || (i==6 && j==6))
                {
                    sum = sum - gameBoard[i][j]*3;
                }
                //corners
                if ((i==0 && j==0) || (i==7 && j==7) || (i==0 && j==7) || (i==7 && j==0))
                {
                    sum = sum + gameBoard[i][j]*5;
                }

            }
        }

        return sum;
    }

    public boolean isTerminal()
    {
        if(XDisks == 0 && ODisks == 0)
            return true;

        if((XDisks + ODisks) == 64)
            return true;

        if(getChildren(X).isEmpty() && getChildren(O).isEmpty())
            return true;

        return false;
    }

    public void print()
    {
        System.out.println("   A B C D E F G H ");
        for (int row=0; row < 8; row++)
        {
            System.out.print((row+1) +"  ");
            for (int col=0; col < 8; col++)
            {
                if(isValidMove(row, col))
                {
                    System.out.print("+ ");
                    continue;
                }
                switch (gameBoard[row][col])
                {
                    case X:
                        System.out.print("X ");
                        break;
                    case O:
                        System.out.print("O ");
                        break;
                    case EMPTY:
                        System.out.print("- ");
                        break;
                    default:
                        break;
                }
            }
            System.out.println(" " + (row+1));
        }
        System.out.println("   A B C D E F G H ");
        System.out.println("*******************");
    }
}
