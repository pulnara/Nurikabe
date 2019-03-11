package pl.edu.agh.iisg.to.nurikabe.command;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class CommandRegistry {

    private ObservableList<Command> commandStack = FXCollections
            .observableArrayList();

    private ObservableList<Command> commandStackRedo = FXCollections
            .observableArrayList();

    public void executeCommand(Command command) {
        command.execute();
        commandStackRedo.clear();
        commandStack.add(command);
    }

    public void redo() {
        if(!commandStackRedo.isEmpty()) {
            Command command = commandStackRedo.remove(commandStackRedo.size() - 1);
            command.redo();
            commandStack.add(command);
        }

    }

    public void undo() {
        if(!commandStack.isEmpty()) {
            Command command = commandStack.remove(commandStack.size() - 1);
            command.undo();
            commandStackRedo.add(command);
        }
    }

    public ObservableList<Command> getCommandStack() {
        return commandStack;
    }
}