package player;

import java.util.Observable;
import structure.Case;

public class PlayerHuman extends Player {

    public PlayerHuman(Case c) {
        super(c);
    }

    @Override
    public void update(Observable obs, Object obj) {
        if(!isPlayerTurn(obj)) {
            return;
        }
        casesAvailable(null);
    }
    
}
