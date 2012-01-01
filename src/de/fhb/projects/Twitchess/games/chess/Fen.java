package de.fhb.projects.Twitchess.games.chess;

import de.fhb.projects.Twitchess.games.chess.figures.Bishop;
import de.fhb.projects.Twitchess.games.chess.figures.Figure;
import de.fhb.projects.Twitchess.games.chess.figures.King;
import de.fhb.projects.Twitchess.games.chess.figures.Knight;
import de.fhb.projects.Twitchess.games.chess.figures.NoFigure;
import de.fhb.projects.Twitchess.games.chess.figures.Pawn;
import de.fhb.projects.Twitchess.games.chess.figures.Queen;
import de.fhb.projects.Twitchess.games.chess.figures.Rook;
import de.fhb.projects.Twitchess.games.chess.player.Color;
import de.fhb.projects.Twitchess.games.chess.player.Player;

public class Fen {
	public static String START_POSITION = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1";
	protected String fen;

	public Fen(String fen) {
		super();
		this.fen = fen;
	}

	public Fen(GameState state) {
		parseFromGameState(state);
	}

	public String getFen() {
		return fen;
	}

	public void setFen(String fen) {
		this.fen = fen;
	}

//	public static void main(String[] args) {
//		String f = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq f6 10 20";
//
//		Fen fen = new Fen(f);
//
//		GameState s = fen.getGameState();
//
//		System.out.println(fen.isValid());
//
//		for (int y = 7; y >= 0; y--) {
//			for (int x = 0; x < 8; x++) {
//				System.out.println(s.getFigure(x, y));
//			}
//		}
//
//		Fen fen2 = new Fen(s);
//
//		System.out.println(fen2.getFen());
//
//	}

	public boolean isValid() {
		if (fen == null || fen.length() == 0)
			return false;

		String rest;
		char c;
		int i, row = 0, column = 0;

		for (i = 0; i < fen.length() && ' ' != fen.charAt(i); i++) {
			c = fen.charAt(i);

			if (isValidLetter(c)) {
				column++;
			} else if (Character.isDigit(c) && c != '0') {
				column += c - '0';
			} else if (c == '/') {
				if (column != 8)
					return false;
				row++;
				column = 0;
			} else
				return false;
		}
		row++;

		if (column != 8 || row != 8 || i == fen.length())
			return false;
		
		
		rest = fen.substring(i + 1);

		return rest
				.matches("[WwBb] ((KQ?k?q?)|(Qk?q?)|(kq?)|(q)|(\\-)) (([a-hA-H][36])|(\\-)) (0|([1-9]\\d*)) [1-9]\\d*");
	}

	protected boolean isValidLetter(char p) {
		char c = Character.toLowerCase(p);

		return c == 'p' || c == 'r' || c == 'n' || c == 'b' || c == 'q'
				|| c == 'k';
	}

	public GameState getGameState() {
		if (!isValid())
			return null;

		char c;
		String rest;
		int i, row = 0, column = 0;
		Player white = new Player(Color.WHITE), black = new Player(Color.BLACK);
		GameState gameState;

		for (i = 0; ' ' != fen.charAt(i); i++) {
			c = fen.charAt(i);

			if (isValidLetter(c)) {

				if (Character.isLowerCase(c))
					addToPlayer(black, c, new Position(column, 7 - row));
				else
					addToPlayer(white, c, new Position(column, 7 - row));

				column++;
			} else if (Character.isDigit(c) && c != '0') {
				column += c - '0';
			} else if (c == '/') {
				row++;
				column = 0;
			}

		}

		gameState = new GameState(white, black);

		rest = fen.substring(i + 1);

		return setAttributes(rest, white, black, gameState);
	}

	protected GameState setAttributes(String rest, Player white, Player black,
			GameState gameState) {
		String[] s = rest.split("\\s+");

		if (s[0].equalsIgnoreCase("b"))
			gameState.setCurrentPlayer(black);
		else
			gameState.setCurrentPlayer(white);

		if (s[1].contains("K"))
			System.out.println("gameState.setWhiteKingCastling()");
		if (s[1].contains("Q"))
			System.out.println("gameState.setWhiteQueenCastling()");
		if (s[1].contains("k"))
			System.out.println("gameState.setBlackKingCastling()");
		if (s[1].contains("q"))
			System.out.println("gameState.setBlackQueenCastling()");

		if (!s[2].contains("-"))
			System.out.println("gameState.setEnPassantPosition(" + s[2] + ")");

		System.out.println("gameState.setHalfMoves(" + Integer.valueOf(s[3])
				+ ")");
		System.out.println("gameState.setMoveCount(" + Integer.valueOf(s[4])
				+ ")");

		return gameState;
	}

	public void parseFromGameState(GameState state) {
		StringBuilder sb = new StringBuilder();
		int counter;

		for (int y = 7; y >= 0; y--) {
			counter = 0;
			for (int x = 0; x < 8; x++) {
				Figure f = state.getFigure(x, y);
				if (f.equals(NoFigure.NO_FIGURE)) {
					counter++;
				} else {
					char c = getCharFromFigure(f);

					if (counter > 0) {
						sb.append("" + counter);
						counter = 0;
					}

					if (f.getColor().equals(Color.WHITE))
						sb.append(Character.toUpperCase(c));
					else
						sb.append(Character.toLowerCase(c));
				}
			}

			if (counter > 0)
				sb.append("" + counter);

			if (y != 0)
				sb.append("/");
		}

		if (state.getCurrentColor() == Color.BLACK)
			sb.append(" b");
		else
			sb.append(" w");

		// TODO implement it
		sb.append(" KQkq - 0 1");

		setFen(sb.toString());
	}

	protected char getCharFromFigure(Figure f) {
		if (f instanceof King)
			return 'k';
		if (f instanceof Queen)
			return 'q';
		if (f instanceof Pawn)
			return 'p';
		if (f instanceof Bishop)
			return 'b';
		if (f instanceof Knight)
			return 'n';
		if (f instanceof Rook)
			return 'r';

		throw new RuntimeException(
				"Error while parsing fen (Figure->Character)");
	}
	
	protected void addToPlayer(Player p, char c, Position pos) {
		Figure f;

		switch (Character.toLowerCase(c)) {
			case 'p' :
				f = new Pawn(pos);
				break;
			case 'r' :
				f = new Rook(pos);
				break;
			case 'b' :
				f = new Bishop(pos);
				break;
			case 'n' :
				f = new Knight(pos);
				break;
			case 'q' :
				f = new Queen(pos);
				break;
			case 'k' :
				f = new King(pos);
				break;
			default :
				throw new RuntimeException("Error while parsing fen");
		}

		f.setColor(p.getColor());
		p.add(f);
	}
}
