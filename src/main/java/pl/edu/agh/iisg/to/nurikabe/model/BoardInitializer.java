package pl.edu.agh.iisg.to.nurikabe.model;

import java.io.*;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

class BoardInitializer {
    private String fileName;
    private final Logger logger = Logger.getLogger(getClass().getName());
    private Board board;

    BoardInitializer() {
        this.fileName = generateFileName();
    }

    private String generateFileName() {
        File dir = new File("./boards");
        File[] boards = dir.listFiles();
        Random rand = new Random();
        File boardFile = null;
        try {
            assert boards != null;
            boardFile = boards[rand.nextInt(boards.length)];
        } catch (NullPointerException e) {
            e.printStackTrace();
            System.exit(1);
        }
        assert boardFile != null;
        return boardFile.toString();
    }

    private Cell createCell(String cellInfo, int i, int j) {
        Cell result = null;
        switch (cellInfo) {
            case "I":
                result = new Cell(true, i, j, null, board.getBoardPresenter());
                break;
            case "W":
                result = new Cell(false, i, j, null, board.getBoardPresenter());
                break;
            default:
                try {
                    int number = Integer.parseInt(cellInfo);
                    result = new Cell(true, i, j, number, board.getBoardPresenter());
                } catch(NumberFormatException e) {
                    logger.log(Level.SEVERE, String.format("Invalid cell value: %s", cellInfo));
                    System.exit(1);
                }
        }
        return result;
    }

    void initializeBoard() {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName))) {
            String line;
            int i = 0;

            line = bufferedReader.readLine();
            board = new Board(Integer.parseInt(line));

            while((line = bufferedReader.readLine()) != null) {
                int j = 0;
                String[] row = line.split(" ");
                for (String cellInfo : row) {
                    Cell cell = createCell(cellInfo, i, j);
                    board.setCell(i, j, cell);
                    j++;
                }
                i++;
            }
        } catch(FileNotFoundException ex) {
            logger.log(Level.SEVERE, "Unable to open file '" + fileName + "'");
            System.exit(1);
        }
        catch(IOException ex) {
            logger.log(Level.SEVERE, "Error reading file '" + fileName + "'");
            System.exit(1);
        }
        board.getBoardPresenter().initView();
    }



}
