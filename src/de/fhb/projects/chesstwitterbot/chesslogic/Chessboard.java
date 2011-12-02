package de.fhb.projects.chesstwitterbot.chesslogic;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.fhb.projects.chesstwitterbot.chesslogic.figures.Bishop;
import de.fhb.projects.chesstwitterbot.chesslogic.figures.Figure;
import de.fhb.projects.chesstwitterbot.chesslogic.figures.King;
import de.fhb.projects.chesstwitterbot.chesslogic.figures.Knight;
import de.fhb.projects.chesstwitterbot.chesslogic.figures.Pawn;
import de.fhb.projects.chesstwitterbot.chesslogic.figures.Queen;
import de.fhb.projects.chesstwitterbot.chesslogic.figures.Rook;

public class Chessboard {
	// private Figure[][] board;
	private Map<Position, Figure> board;
	private Color playerToMove;
	private Figure enPassant;
	private boolean kingCastling[];
	private boolean queenCastling[];

	public Chessboard() {
		clearBoard();
		setStartingPosition();
	}

	private void clearBoard() {
		setBoard(new HashMap<Position, Figure>());
		playerToMove = Color.WHITE;
		setEnPassant(null);

	}

	private void setStartingPosition() {
		setKingCastling(new boolean[] { true, true });
		setQueenCastling(new boolean[] { true, true });

		playerToMove = Color.WHITE;

		setFigure(Rook.class, Color.WHITE, Position.getPosition(0, 0));
		setFigure(Rook.class, Color.WHITE, Position.getPosition(7, 0));
		setFigure(Rook.class, Color.BLACK, Position.getPosition(0, 7));
		setFigure(Rook.class, Color.BLACK, Position.getPosition(7, 7));

		setFigure(Knight.class, Color.WHITE, Position.getPosition(1, 0));
		setFigure(Knight.class, Color.WHITE, Position.getPosition(6, 0));
		setFigure(Knight.class, Color.BLACK, Position.getPosition(1, 7));
		setFigure(Knight.class, Color.BLACK, Position.getPosition(6, 7));

		setFigure(Bishop.class, Color.WHITE, Position.getPosition(2, 0));
		setFigure(Bishop.class, Color.WHITE, Position.getPosition(5, 0));
		setFigure(Bishop.class, Color.BLACK, Position.getPosition(2, 7));
		setFigure(Bishop.class, Color.BLACK, Position.getPosition(5, 7));

		setFigure(Queen.class, Color.WHITE, Position.getPosition(3, 0));
		setFigure(Queen.class, Color.BLACK, Position.getPosition(3, 7));

		setFigure(King.class, Color.WHITE, Position.getPosition(4, 0));
		setFigure(King.class, Color.BLACK, Position.getPosition(4, 7));

		for (int i = 0; i < 8; i++) {
			setFigure(Pawn.class, Color.WHITE, Position.getPosition(i, 1));
			setFigure(Pawn.class, Color.BLACK, Position.getPosition(i, 6));
		}

		// setEnPassant(getFigureAtPosition(4, 3));
	}

	private void setFigure(Class<? extends Figure> type, Color color,
			Position position) {
		try {
			board.put(
					position,
					type.getConstructor(
							new Class[] { Position.class, Color.class })
							.newInstance(position, color));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Color getPlayerToMove() {
		return playerToMove;
	}

	public void setPlayerToMove(Color playerToMove) {
		this.playerToMove = playerToMove;
	}

	public Map<Position, Figure> getBoard() {
		return board;
	}

	private void setBoard(Map<Position, Figure> board) {
		this.board = board;
	}

	public Figure getEnPassant() {
		return enPassant;
	}

	public void setEnPassant(Figure enPassant) {
		this.enPassant = enPassant;
	}

	public boolean[] getKingCastling() {
		return kingCastling;
	}

	public void setKingCastling(boolean kingCastling[]) {
		this.kingCastling = kingCastling;
	}

	public boolean[] getQueenCastling() {
		return queenCastling;
	}

	public void setQueenCastling(boolean queenCastling[]) {
		this.queenCastling = queenCastling;
	}

	public Figure getFigureAtPosition(int x, int y) {
		return board.get(Position.getPosition(x, y));
	}

	public Figure getFigureAtPosition(Position position) {
		return board.get(position);
	}

	public List<RelativeMove> generateNaiveMoves() {
		List<RelativeMove> moves = new ArrayList<RelativeMove>();

		for (Figure f : board.values())
			if (isItFiguresTurn(f))
				moves.addAll(f.generateMoves(this));

		return moves;
	}

	private boolean isItFiguresTurn(Figure f) {
		return f != null && f.getColor() == getPlayerToMove();
	}

	public void doMove(RelativeMove m) {
		Figure f = board.get(m.getStart());

		if (f != null) {
			board.remove(m.getStart());
			board.put(m.getDestination(), f);
			f.setPosition(m.getDestination());
			playerToMove = f.getColor().getInverse();
		}

	}

	public boolean isCheckAfterMove(RelativeMove m) {
		HashMap<Position, Figure> boardCopy = new HashMap<Position, Figure>();
		List<RelativeMove> moves = new ArrayList<RelativeMove>();
		boolean isCheck = false;
		Color playerToMoveCopy = playerToMove;

		for (Figure f : board.values())
			boardCopy.put(f.getPosition(), (Figure) f.clone());

		doMove(m);

		moves = generateNaiveMoves();

		for (int i = 0; i < moves.size() && !isCheck; i++) {
			Figure temp = getFigureAtPosition(moves.get(i).getDestination());
			if (temp instanceof King && temp.getColor() == playerToMoveCopy)
				isCheck = true;
		}

		
		// undo move
		board = boardCopy;
		playerToMove = playerToMoveCopy;

		return isCheck;
	}

	public String toString() {
		char[][] output = new char[8][8];

		for (Figure f : board.values()) {

			if (f instanceof King) {
				output[f.getPosition().getX()][f.getPosition().getY()] = 'k';
			} else if (f instanceof Queen) {
				output[f.getPosition().getX()][f.getPosition().getY()] = 'q';
			} else if (f instanceof Knight) {
				output[f.getPosition().getX()][f.getPosition().getY()] = 'n';
			} else if (f instanceof Bishop) {
				output[f.getPosition().getX()][f.getPosition().getY()] = 'b';
			} else if (f instanceof Pawn) {
				output[f.getPosition().getX()][f.getPosition().getY()] = 'p';
			} else if (f instanceof Rook) {
				output[f.getPosition().getX()][f.getPosition().getY()] = 'r';
			}

			if (f != null && f.getColor() == Color.WHITE)
				output[f.getPosition().getX()][f.getPosition().getY()] = ("" + output[f
						.getPosition().getX()][f.getPosition().getY()])
						.toUpperCase().charAt(0);

		}

		StringBuilder sb = new StringBuilder();
		for (int y = 7; y >= 0; y--) {
			for (int x = 0; x < 8; x++) {
				if (output[x][y] != 0) {
					sb.append(output[x][y]);
					sb.append(' ');
				} else
					sb.append("  ");
			}
			sb.append("\n");
		}

		return sb.toString();
	}

	public static void main(String[] args) {
		Chessboard b = new Chessboard();

		InputStreamReader inputStreamReader = new InputStreamReader(System.in);
		BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
		String s;

		while (true) {
			List<RelativeMove> moves = b.generateNaiveMoves();

			System.out.println(b);
			System.out.println("move count: " + moves.size());

			for (int i = 0; i < moves.size(); i++)
				System.out.println((i) + ": " + moves.get(i) + " check: "
						+ b.isCheckAfterMove(moves.get(i)));
				

			try {
				s = bufferedReader.readLine();

				b.doMove(moves.get(Integer.parseInt(s)));
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
	}

}
