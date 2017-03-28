package se.kth.sda.othello.imp;

import java.util.List;
import java.util.Vector;

import se.kth.sda.othello.Othello;
import se.kth.sda.othello.board.Board;
import se.kth.sda.othello.board.Node;
import se.kth.sda.othello.player.Player;

/**
 * Created by robertog on 2/7/17.
 */
public class OthelloImp implements Othello {
    private final Player playerOne;
    private final Player playerTwo;
    private final BoardImp board;
    private Player currentPlayer;

    public OthelloImp(Player one, Player two) {
        this.playerOne = one;
        this.playerTwo = two;
        this.board = new BoardImp();
    }

    @Override
    public Board getBoard() {
        return board;
    }

    @Override
    public List<Node> getNodesToSwap(String playerId, String nodeId) {
        return new Vector<Node>();
    }

    @Override
    public Player getPlayerInTurn() {
        return this.currentPlayer;
    }

    @Override
    public List<Player> getPlayers() {
        List<Player> res = new Vector<Player>();
        res.add(playerOne);
        res.add(playerTwo);
        return res;
    }

    @Override
    public boolean hasValidMove(String playerId) {
        return true;
    }

    @Override
    public boolean isActive() {
        return true;
    }

    @Override
    public boolean isMoveValid(String playerId, String nodeId)throws IllegalStateException {
        int x = Integer.valueOf(nodeId.split(",")[0]);
        int y = Integer.valueOf(nodeId.split(",")[1]);
        if (IsNodeOccupied(x, y))
            return false;

        else if (checkIfAdjacentNodesNotNull(x, y)) {
            if (checkIfOpponentPlayerDiscPresentAdjacentDirections( playerId, nodeId))
                return true;
            else
                return false;
        }

        else
            return false;
    }



    private boolean checkIfOpponentPlayerDiscPresentAdjacentDirections(String playerId_playing, String nodeId )throws IllegalStateException {
        int x = Integer.valueOf(nodeId.split(",")[0]);
        int y = Integer.valueOf(nodeId.split(",")[1]);

        if (!isOpponentPlayerDisc(x + 1, y, playerId_playing) &&
                !isOpponentPlayerDisc(x, y + 1, playerId_playing) &&
                !isOpponentPlayerDisc(x - 1, y, playerId_playing) &&
                !isOpponentPlayerDisc(x, y - 1, playerId_playing) &&
                !isOpponentPlayerDisc(x + 1, y + 1, playerId_playing) &&
                !isOpponentPlayerDisc(x - 1, y - 1, playerId_playing) &&
                !isOpponentPlayerDisc(x - 1, y + 1, playerId_playing) &&
                !isOpponentPlayerDisc(x + 1, y - 1, playerId_playing))

            return false;
        else {
            boolean check_left =false;
            boolean bottom_vertical=false;
            boolean check_right=false;
            boolean top_vertical=false;
            boolean top_left_diagonal=false;
            boolean top_right_diagonal=false;
            boolean bottom_right_diagonal=false;
            boolean bottom_left_diagonal=false;


            if (isOpponentPlayerDisc(x - 1, y, playerId_playing)) {
                check_left = checkOpponentDiscFurtherLeftHorizontal(x - 1, y, playerId_playing);
            }
            if (isOpponentPlayerDisc(x + 1, y, playerId_playing)) {
                check_right = checkOpponentDiscFurtherRightHorizontal(x + 1, y, playerId_playing);
            }
            if (isOpponentPlayerDisc(x, y + 1, playerId_playing)) {
                bottom_vertical = checkOpponentDiscFurtherBottomVertical(x, y + 1, playerId_playing);

            }
            if (isOpponentPlayerDisc(x, y - 1, playerId_playing)) {

                top_vertical = checkOpponentDiscFurtherTopVertical(x, y - 1, playerId_playing);
            }
            if (isOpponentPlayerDisc(x - 1, y - 1, playerId_playing)) {
                top_left_diagonal = checkOpponentDiscFurtherTopLeftDiagonal(x - 1, y - 1, playerId_playing);
            }
            if (isOpponentPlayerDisc(x + 1, y - 1, playerId_playing)) {
                top_right_diagonal = checkOpponentDiscFurtherTopRightDiagonal(x + 1, y - 1, playerId_playing);
            }
            if (isOpponentPlayerDisc(x + 1, y + 1, playerId_playing)) {
                bottom_right_diagonal = checkOpponentDiscFurtherBottomRightDiagonal(x + 1, y + 1, playerId_playing);
            }
            if (isOpponentPlayerDisc(x - 1, y + 1, playerId_playing))
                bottom_left_diagonal = checkOpponentDiscFurtherBottomLeftDiagonal(x - 1, y + 1, playerId_playing);

            if ((check_left == true) ||
                    (bottom_vertical == true)
                    || (check_right == true) || (top_vertical == true) || (top_left_diagonal == true) || (top_right_diagonal == true) ||
                    (bottom_right_diagonal == true) || (bottom_left_diagonal == true))
                return true;
            else
                return false;


        }
    }


    private boolean checkOpponentDiscFurtherLeftHorizontal(int i, int j,String current_player) {

        int opponent_disc_pos = i;
        while (isOpponentPlayerDisc(opponent_disc_pos, j, current_player)) {
            if(opponent_disc_pos==0) //reached end and only opponent disc till end,not valid
                return false;
            else opponent_disc_pos--;

        }


        if (!(IsNodeOccupied(opponent_disc_pos, j)))//no node at this position so not valid move
            return false;

        else { //not opponent , not null then its current player disc
            // its correct move,set all node from this position to

            for (int current_player_nodes_positions_to_be_changed = opponent_disc_pos;
                 current_player_nodes_positions_to_be_changed <= i; current_player_nodes_positions_to_be_changed++) {
                String nodeId_current_player = NodeImp.format(current_player_nodes_positions_to_be_changed, j);
                Node newNode = new NodeImp(nodeId_current_player, current_player);
                board.setNode(newNode);
            }

        }
        return true;
    }

    private boolean checkOpponentDiscFurtherRightHorizontal(int i, int j, String current_player){
        int opponent_disc_pos = i;
        while (isOpponentPlayerDisc(opponent_disc_pos, j, current_player) ) {
            if( opponent_disc_pos ==7)//reached end and only opponent disc till end,not valid
                return false;
            else opponent_disc_pos++;

        }

        if (!(IsNodeOccupied(opponent_disc_pos, j)))
            return false;//no node at this position so not valid move

        else { //not opponent , not null then its current player disc
            // its correct move,set all node from this position to

            for (int current_player_nodes_positions_to_be_changed = opponent_disc_pos;
                 current_player_nodes_positions_to_be_changed >=i; current_player_nodes_positions_to_be_changed--) {
                String nodeId_current_player = NodeImp.format(current_player_nodes_positions_to_be_changed, j);
                Node newNode = new NodeImp(nodeId_current_player, current_player);
                board.setNode(newNode);
            }

        }
        return true;

    }

    private boolean checkOpponentDiscFurtherTopVertical(int i, int j, String current_player) {
        int opponent_disc_pos = j;
        while (isOpponentPlayerDisc(i, opponent_disc_pos, current_player)) {
            if( opponent_disc_pos ==0)//reached end and only opponent disc till end,not valid
                return false;
            else opponent_disc_pos--;

        }

        if (!(IsNodeOccupied(i, opponent_disc_pos)))
            return false;//no node at this position so not valid move

        else { //not opponent , not null then its current player disc
            // its correct move,set all node from this position to

            for (int current_player_nodes_positions_to_be_changed = opponent_disc_pos;
                 current_player_nodes_positions_to_be_changed <= j; current_player_nodes_positions_to_be_changed++) {
                String nodeId_current_player = NodeImp.format(i, current_player_nodes_positions_to_be_changed);
                Node newNode = new NodeImp(nodeId_current_player, current_player);
                board.setNode(newNode);
            }

        }
        return true;
    }


    private boolean checkOpponentDiscFurtherBottomVertical(int i, int j,String current_player) {

        int opponent_disc_pos = j;
        while (isOpponentPlayerDisc(i, opponent_disc_pos, current_player)) {
            if( opponent_disc_pos ==7)//reached end and only opponent disc till end,not valid
                return false;
            else opponent_disc_pos++;
        }

        if (!(IsNodeOccupied(i, opponent_disc_pos)))
            return false;//no node at this position so not valid move

        else { //not opponent , not null then its current player disc
            // its correct move,set all node from this position to

            for (int current_player_nodes_positions_to_be_changed = opponent_disc_pos;
                 current_player_nodes_positions_to_be_changed >= j; current_player_nodes_positions_to_be_changed--) {
                String nodeId_current_player = NodeImp.format(i, current_player_nodes_positions_to_be_changed);
                Node newNode = new NodeImp(nodeId_current_player, current_player);
                board.setNode(newNode);
            }

        }
        return true;
    }

    private boolean checkOpponentDiscFurtherTopLeftDiagonal(int i, int j, String current_player){
        int opponent_disc_pos_x = i;
        int opponent_disc_pos_y = j;
        while (isOpponentPlayerDisc(opponent_disc_pos_x, opponent_disc_pos_y, current_player) ) {
            if( opponent_disc_pos_x ==0 )//reached end and only opponent disc till end,not valid
                return false;
            else {
                opponent_disc_pos_x--;
                opponent_disc_pos_y--;
            }

        }

        if (!(IsNodeOccupied(opponent_disc_pos_x, opponent_disc_pos_y)))
            return false;//no node at this position so not valid move

        else { //not opponent , not null then its current player disc
            // its correct move,set all node from this position to

            for (int from_disc_x_pos = opponent_disc_pos_x;
                 from_disc_x_pos <= i; from_disc_x_pos++) {

                String nodeId_current_player = NodeImp.format(opponent_disc_pos_x, opponent_disc_pos_y);
                Node newNode = new NodeImp(nodeId_current_player, current_player);
                board.setNode(newNode);
                opponent_disc_pos_x++;
                opponent_disc_pos_y++;
            }

        }

        return true;

    }


    private boolean checkOpponentDiscFurtherTopRightDiagonal(int i, int j, String current_player){
        int opponent_disc_pos_x = i;
        int opponent_disc_pos_y = j;
        while (isOpponentPlayerDisc(opponent_disc_pos_x, opponent_disc_pos_y, current_player) ) {
            if( opponent_disc_pos_x ==7 )//reached end and only opponent disc till end,not valid
                return false;
            else {
                opponent_disc_pos_x++;
                opponent_disc_pos_y--;
            }

        }

        if (!(IsNodeOccupied(opponent_disc_pos_x, opponent_disc_pos_y)))
            return false;//no node at this position so not valid move

        else { //not opponent , not null then its current player disc
            // its correct move,set all node from this position to

            for (int from_disc_x_pos = opponent_disc_pos_x;
                 from_disc_x_pos >= i; from_disc_x_pos--) {

                String nodeId_current_player = NodeImp.format(opponent_disc_pos_x, opponent_disc_pos_y);
                Node newNode = new NodeImp(nodeId_current_player, current_player);
                board.setNode(newNode);
                opponent_disc_pos_x--;
                opponent_disc_pos_y++;
            }

        }

        return true;

    }



    private boolean checkOpponentDiscFurtherBottomRightDiagonal(int i, int j, String current_player){
        int opponent_disc_pos_x = i;
        int opponent_disc_pos_y = j;
        while (isOpponentPlayerDisc(opponent_disc_pos_x, opponent_disc_pos_y, current_player) ) {
            if( opponent_disc_pos_x ==7 )//reached end and only opponent disc till end,not valid
                return false;
            else {
                opponent_disc_pos_x++;
                opponent_disc_pos_y++;
            }

        }

        if (!(IsNodeOccupied(opponent_disc_pos_x, opponent_disc_pos_y)))
            return false;//no node at this position so not valid move

        else { //not opponent , not null then its current player disc
            // its correct move,set all node from this position to

            for (int from_disc_x_pos = opponent_disc_pos_x;
                 from_disc_x_pos >= i; from_disc_x_pos--) {

                String nodeId_current_player = NodeImp.format(opponent_disc_pos_x, opponent_disc_pos_y);
                Node newNode = new NodeImp(nodeId_current_player, current_player);
                board.setNode(newNode);
                opponent_disc_pos_x--;
                opponent_disc_pos_y--;
            }

        }

        return true;

    }


    private boolean checkOpponentDiscFurtherBottomLeftDiagonal(int i, int j, String current_player){
        int opponent_disc_pos_x = i;
        int opponent_disc_pos_y = j;
        while (isOpponentPlayerDisc(opponent_disc_pos_x, opponent_disc_pos_y, current_player) ) {
            if( opponent_disc_pos_x ==0 )//reached end and only opponent disc till end,not valid
                return false;
            else {
                opponent_disc_pos_x--;
                opponent_disc_pos_y++;
            }

        }

        if (!(IsNodeOccupied(opponent_disc_pos_x, opponent_disc_pos_y)))
            return false;//no node at this position so not valid move

        else { //not opponent , not null then its current player disc
            // its correct move,set all node from this position to

            for (int from_disc_x_pos = opponent_disc_pos_x;
                 from_disc_x_pos <= i; from_disc_x_pos++) {

                String nodeId_current_player = NodeImp.format(opponent_disc_pos_x, opponent_disc_pos_y);
                Node newNode = new NodeImp(nodeId_current_player, current_player);
                board.setNode(newNode);
                opponent_disc_pos_x++;
                opponent_disc_pos_y--;
            }

        }

        return true;

    }


    private boolean isOpponentPlayerDisc(int x, int y,String playerId_playing) {
        //check out of border position
        if (x<0||x>7)
            return false;
        if(y<0||y>7)
            return false;
        String nodeId = NodeImp.format(x,y);
        Node node = board.getNode(nodeId);
        String id_player = node.getOccupantPlayerId();
        if(id_player != null)
            return (!(id_player.equals(playerId_playing)));
        else
            return false;

    }

    public boolean checkIfAdjacentNodesNotNull(int x,int y) {

            if (IsNodeOccupied(x + 1, y))
                return true;

            else if ((IsNodeOccupied(x - 1, y)))
                return true;

            else if ((IsNodeOccupied(x, y + 1)))

                return true;

            else if ((IsNodeOccupied(x - 1, y - 1)))

                return true;

            else if ((IsNodeOccupied(x - 1, y + 1)))

                return true;

            else if ((IsNodeOccupied(x + 1, y - 1)))

                return true;

            else if ((IsNodeOccupied(x + 1, y + 1)))

                return true;

            else if ((IsNodeOccupied(x + 1, y - 1)))

                return true;
            else
                return false;

    }





    private boolean IsNodeOccupied(int x, int y) {
        //check out of border position
            if (x<0||x>7)
              return false;
            if(y<0||y>7)
                return false;

                String nodeId = NodeImp.format(x, y);
                Node node = board.getNode(nodeId);
                String id_player = node.getOccupantPlayerId();
                if (id_player == null)
                    return false;
                else
                    return true;
            }


    private void swapPlayer() {
        if (currentPlayer == playerOne)
            currentPlayer = playerTwo;
        else
            currentPlayer = playerOne;
    }

    @Override
    public List<Node> move() {
        if (currentPlayer.getType() != Player.Type.COMPUTER)
            throw new IllegalStateException("Current player is not a computer");

        swapPlayer();

        return new Vector<Node>();
    }

    @Override
    public List<Node> move(String playerId, String nodeId) throws IllegalStateException {
        if (currentPlayer.getType() != Player.Type.HUMAN)
            throw new IllegalStateException("Current player is not a human");
        if (!isMoveValid(playerId, nodeId))
            throw new IllegalStateException("Invalid move");
        if (playerId != currentPlayer.getId())
            throw new IllegalStateException("Invalid player ID");

        List<Node> res = new Vector<Node>();
        Node newNode = new NodeImp(nodeId, playerId);
        board.setNode(newNode);
        res.add(newNode);

        swapPlayer();

        return res;
    }

    @Override
    public void start() {
        // TODO: choose a random player
        currentPlayer = playerOne;
    }

    @Override
    public void start(String playerId) {
        // TODO: choose the player with the right ID
        currentPlayer = playerOne;
    }
}