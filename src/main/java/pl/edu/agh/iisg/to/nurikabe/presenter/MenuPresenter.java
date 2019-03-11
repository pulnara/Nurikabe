package pl.edu.agh.iisg.to.nurikabe.presenter;

import pl.edu.agh.iisg.to.nurikabe.model.Nurikabe;
import pl.edu.agh.iisg.to.nurikabe.view.MenuView;

public class MenuPresenter {

    public static void initializeMenu() {
        MenuView menuView = new MenuView();
        menuView.init();
    }

    public static void startGame(String playerName) {
        Nurikabe.startGame(playerName);
    }
}
