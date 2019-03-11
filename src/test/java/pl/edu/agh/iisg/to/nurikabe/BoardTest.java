package pl.edu.agh.iisg.to.nurikabe;

import org.junit.jupiter.api.BeforeAll;
import pl.edu.agh.iisg.to.nurikabe.model.Board;
import static org.mockito.Mockito.mock;


public class BoardTest {
    private Board board;

    @BeforeAll
    public void setup(){
        board = mock(Board.class);
    }

//    @Test
//    public void testValidateWhenBoardIsNotValidReturnsFalse() {
//        //given
//        Mockito.when(Board.Validator.validateCell(mock(Cell.class))).thenReturn(Boolean.FALSE);
//        Cell[][] cells = new Cell[10][10];
//        cells[0][0] = Mockito.any(Cell.class);
//        when(board.getCells()).thenReturn(new Cell[10][10]);
//        when(board.getSize()).thenReturn(10);
//        // when&then
//        assertFalse(board.validate());
//    }
}
