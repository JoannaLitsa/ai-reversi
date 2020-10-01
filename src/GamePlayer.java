import java.util.ArrayList;
import java.util.Random;

public class GamePlayer
{
    private int maxDepth;
    private int playerLetter;

    public GamePlayer()
    {
        maxDepth = 2;
        playerLetter = Board.X;
    }

    public GamePlayer(int maxDepth, int playerLetter)
    {
        this.maxDepth = maxDepth;
        this.playerLetter = playerLetter;
    }

    public Move MiniMax(Board board)
    {
        if (playerLetter == Board.X)
        {
            return max(new Board(board), 0, Integer.MIN_VALUE, Integer.MAX_VALUE);
        }
        else
        {
            return min(new Board(board), 0, Integer.MIN_VALUE, Integer.MAX_VALUE);
        }
    }

    public Move max(Board board, int depth, int alpha, int beta) {
        if ((board.isTerminal()) || (depth == maxDepth)) {
            Move lastMove = new Move(board.getLastMove().getRow(), board.getLastMove().getCol(), board.evaluate());
            return lastMove;
        }

        ArrayList<Board> children = new ArrayList<Board>(board.getChildren(Board.X));
        if (children.isEmpty())
            return board.getLastMove();

        Random r = new Random();
        Move maxMove = new Move(Integer.MIN_VALUE);
        for (Board child : children) {
            Move move = min(child, depth + 1, alpha, beta);

            if (move.getValue() >= alpha) {
                if(move.getValue()==alpha)
                {
                    if (r.nextInt(2)==0)
                    {
                        maxMove.setRow(child.getLastMove().getRow());
                        maxMove.setCol(child.getLastMove().getCol());
                        maxMove.setValue(move.getValue());
                    }
                }
                else {
                    maxMove.setRow(child.getLastMove().getRow());
                    maxMove.setCol(child.getLastMove().getCol());
                    maxMove.setValue(move.getValue());
                    alpha = move.getValue();
                }
            }

            if(alpha>=beta)
                break;
        }
        return maxMove;
    }

    public Move min(Board board, int depth, int alpha, int beta)
    {
        if(board.isTerminal() || depth==maxDepth)
        {
            Move lastMove = new Move(board.getLastMove().getRow(), board.getLastMove().getCol(), board.evaluate());
            return lastMove;
        }

        ArrayList<Board> children = new ArrayList<Board>(board.getChildren(Board.O));
        if (children.isEmpty())
            return board.getLastMove();

        Random r = new Random();
        Move minMove = new Move(Integer.MAX_VALUE);
        for(Board child: children)
        {
            Move move = max(child, depth + 1, alpha, beta);

            if(move.getValue() <= beta)
            {
                if (move.getValue()==beta)
                {
                    if (r.nextInt(2)==0){
                        minMove.setRow(child.getLastMove().getRow());
                        minMove.setCol(child.getLastMove().getCol());
                        minMove.setValue(move.getValue());
                    }
                }
                else {
                    minMove.setRow(child.getLastMove().getRow());
                    minMove.setCol(child.getLastMove().getCol());
                    minMove.setValue(move.getValue());
                    beta = move.getValue();
                }
            }
            if(alpha>=beta)
                break;
        }
        return minMove;
    }


}
