package pl.edu.agh.iisg.to.nurikabe.command;

public interface Command {
    void execute();

    void undo();

    void redo();
}