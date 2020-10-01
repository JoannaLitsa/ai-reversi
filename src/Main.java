import java.util.Scanner;

public class Main
{
    public static void main(String[] args)
    {
        Scanner in = new Scanner(System.in);

        while(true)
        {
            System.out.printf("(1) CPU vs CPU\n(2) Player vs CPU\nInput: ");

            int input = in.nextInt();
            if(input == 1)
            {
                GamePlayer XPlayer = new GamePlayer(8, Board.X);
                GamePlayer OPlayer = new GamePlayer(8, Board.O);
                Board board = new Board();

                System.out.println("X's turn");
                System.out.printf("X : %d\nO : %d\n", board.XDisks, board.ODisks);
                board.print();
                while(!board.isTerminal())
                {
                    switch (board.getLastLetterPlayed())
                    {
                        //If X played last, then 0 plays now
                        case Board.X:
                            Move OMove = OPlayer.MiniMax(board);
                            if(OMove == board.getLastMove())
                            {
                                System.out.println("No available moves for player O");
                                board.setLastLetterPlayed(Board.O);
                            }
                            else
                                board.makeMove(OMove.getRow(), OMove.getCol(), Board.O);
                            break;
                        //If O played last, then X plays now
                        case Board.O:
                            Move XMove = XPlayer.MiniMax(board);
                            if(XMove == board.getLastMove())
                            {
                                System.out.println("No available moves for player X");
                                board.setLastLetterPlayed(Board.X);
                            }
                            else
                                board.makeMove(XMove.getRow(), XMove.getCol(), Board.X);
                            break;
                        default:
                            break;
                    }
                    System.out.println((board.getLastLetterPlayed() == 1 ? 'O' : 'X')+"'s turn");
                    System.out.printf("X : %d\nO : %d\n", board.XDisks, board.ODisks);
                    board.print();
                }

                if (board.ODisks<board.XDisks)
                    System.out.println("Player X wins!");
                else if (board.ODisks> board.XDisks)
                    System.out.println("Player O wins!");
                else
                    System.out.println("It's a tie!");

            }
            else if(input == 2)
            {
                System.out.print("Select depth: ");
                int depth = in.nextInt();
                int playerLetter;
                while(true){

                    System.out.print("Go first? (y/n): ");
                    char turn = in.next().charAt(0);

                    if(turn == 'y'){
                        playerLetter=Board.X;
                        break;
                    }
                    else if(turn =='n'){
                        playerLetter=Board.O;
                        break;
                    }
                    else
                        continue;
                }

                GamePlayer computer = new GamePlayer(depth, -playerLetter);
                Board board = new Board();

                while(!board.isTerminal())
                {
                    // Print who's playing, disks and board
                    System.out.println((board.getLastLetterPlayed() == 1 ? 'O' : 'X')+"'s turn");
                    System.out.printf("X : %d\nO : %d\n", board.XDisks, board.ODisks);
                    board.print();

                    //Player's turn
                    if (board.getLastLetterPlayed()==-playerLetter)
                    {
                        if(board.getChildren(playerLetter).isEmpty())
                        {
                            System.out.println("No available moves for player "+ ((playerLetter>0)?"X":"O"));
                            board.setLastLetterPlayed(playerLetter);
                            continue;
                        }
                        while(true)
                        {
                            System.out.print("Move: ");
                            int x = Integer.parseInt(in.next()) - 1;
                            int y = in.next().toUpperCase().charAt(0) - 65;

                            if(board.isValidMove(x, y))
                            {
                                board.makeMove(x, y, playerLetter);
                                break;
                            }
                            else
                                System.out.println("Invalid move");
                        }
                    }
                    //Computer's turn
                    else if (board.getLastLetterPlayed()==playerLetter)
                    {
                        Move move = computer.MiniMax(board);
                        if(move==board.getLastMove())
                        {
                            System.out.println("No available moves for player "+ ((-playerLetter>0)?"X":"O"));
                            board.setLastLetterPlayed(-playerLetter);
                            continue;
                        }
                        board.makeMove(move.getRow(), move.getCol(), -playerLetter);
                    }
                }

                if (board.ODisks<board.XDisks)
                    System.out.println("Player X wins!");
                else if (board.ODisks> board.XDisks)
                    System.out.println("Player O wins!");
                else
                    System.out.println("It's a tie!");

            }
        }
    }
}
