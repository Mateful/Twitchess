package de.fhb.projects.Twitchess.games.chess;

import de.fhb.projects.Twitchess.games.chess.figures.Bishop;
import de.fhb.projects.Twitchess.games.chess.figures.Figure;
import de.fhb.projects.Twitchess.games.chess.figures.King;
import de.fhb.projects.Twitchess.games.chess.figures.Knight;
import de.fhb.projects.Twitchess.games.chess.figures.NoFigure;
import de.fhb.projects.Twitchess.games.chess.figures.Pawn;
import de.fhb.projects.Twitchess.games.chess.figures.Queen;
import de.fhb.projects.Twitchess.games.chess.figures.Rook;
import de.fhb.projects.Twitchess.games.chess.move.Move;
import de.fhb.projects.Twitchess.games.chess.player.Color;
import de.fhb.projects.Twitchess.games.chess.player.Player;

/**
 * Represents a chess GameState as a String. The Fen is built as follows(copied
 * from wikipedia): A FEN record contains six fields. The separator between
 * fields is a space. The fields are:
 * 
 * 1.Piece placement (from white's perspective). Each rank is described,
 * starting with rank 8 and ending with rank 1; within each rank, the contents
 * of each square are described from file a through file h. Following the
 * Standard Algebraic Notation (SAN), each piece is identified by a single
 * letter taken from the standard English names (pawn = "P", knight = "N",
 * bishop = "B", rook = "R", queen = "Q" and king = "K").[1] White pieces are
 * designated using upper-case letters ("PNBRQK") while black pieces use
 * lowercase ("pnbrqk"). Blank squares are noted using digits 1 through 8 (the
 * number of blank squares), and "/" separate ranks. 2.Active color. "w" means
 * white moves next, "b" means black. 3.Castling availability. If neither side
 * can castle, this is "-". Otherwise, this has one or more letters: "K" (White
 * can castle kingside), "Q" (White can castle queenside), "k" (Black can castle
 * kingside), and/or "q" (Black can castle queenside). 4.En passant target
 * square in algebraic notation. If there's no en passant target square, this is
 * "-". If a pawn has just made a two-square move, this is the position "behind"
 * the pawn. This is recorded regardless of whether there is a pawn in position
 * to make an en passant capture. 5.Halfmove clock: This is the number of
 * halfmoves since the last pawn advance or capture. This is used to determine
 * if a draw can be claimed under the fifty-move rule. 6.Fullmove number: The
 * number of the full move. It starts at 1, and is incremented after Black's
 * move.
 * 
 * 
 */
public final class Fen {
	public static String START_POSITION = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1";
	private String fen;
	private GameState gameState;

	public Fen(final String fen) {
		if (isValid(fen)) {
			this.fen = fen;
			gameState = createGameState(fen);
		} else {
			throw new RuntimeException("Invalid fen.");
		}
	}

	public Fen(final GameState gameState) {
		fen = parseFromGameState(gameState);
		this.gameState = gameState;
	}

	public static boolean isValid(final String fen) {
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

	public static boolean isValidLetter(final char p) {
		char c = Character.toLowerCase(p);

		return c == 'p' || c == 'r' || c == 'n' || c == 'b' || c == 'q'
				|| c == 'k';
	}

	public static GameState createGameState(String fen) {
		String position = fen.split(" ", 2)[0];
		String attributes = fen.split(" ", 2)[1];
		Player white = new Player(Color.WHITE), black = new Player(Color.BLACK);
		GameState gameState;

		setPlayerFigures(position, white, black);
		gameState = new GameState(white, black);
		setGameStateAttributes(attributes, white, black, gameState);
		return gameState;
	}

	private static void setPlayerFigures(String position, Player white,
			Player black) {
		char c;
		for (int i = 0, row = 0, column = 0; i < position.length(); i++) {
			c = position.charAt(i);
			if (isValidLetter(c)) {
				if (Character.isLowerCase(c)) {
					addToPlayer(black, c, new Position(column, 7 - row));
				} else {
					addToPlayer(white, c, new Position(column, 7 - row));
				}
				column++;
			} else if (Character.isDigit(c) && c != '0') {
				column += c - '0';
			} else if (c == '/') {
				row++;
				column = 0;
			}
		}
	}

	private static void setGameStateAttributes(final String rest,
			final Player white, final Player black, final GameState gameState) {
		String[] s = rest.split("\\s+");

		setCurrentColor(gameState, s);
		setCastling(gameState, s);
		setLastMove(gameState, s);
		setTurnCounters(gameState, s);
	}

	private static void setCurrentColor(final GameState gameState, String[] s) {
		if (s[0].equalsIgnoreCase("b")) {
			gameState.setCurrentColor(Color.BLACK);
		} else {
			gameState.setCurrentColor(Color.WHITE);
		}
	}

	private static void setCastling(final GameState gameState, String[] s) {
		if (s[1].contains("K")) {
			gameState.setWhiteCastleKingSide(true);
		} else {
			gameState.setWhiteCastleKingSide(false);
		}

		if (s[1].contains("Q")) {
			gameState.setWhiteCastleQueenSide(true);
		} else {
			gameState.setWhiteCastleQueenSide(false);
		}

		if (s[1].contains("k")) {
			gameState.setBlackCastleKingSide(true);
		} else {
			gameState.setBlackCastleKingSide(false);
		}

		if (s[1].contains("q")) {
			gameState.setBlackCastleQueenSide(true);
		} else {
			gameState.setBlackCastleQueenSide(false);
		}
	}

	private static void setLastMove(final GameState gameState, String[] s) {
		if (!s[2].contains("-")) {
			Position p = ChessboardPositionToArrayPosition
					.parseChessboardPosition(s[2]);
			Position start, dest;
			Move m;

			if (p.y == 5) {
				start = new Position(p.x, p.y + 1);
				dest = new Position(p.x, p.y - 1);
			} else if (p.y == 2) {
				start = new Position(p.x, p.y - 1);
				dest = new Position(p.x, p.y + 1);
			} else {
				throw new RuntimeException(
						"Error while parsing EnPassant-Position in Fen");
			}

			m = new Move(start, dest);
			gameState.setLastMove(m);
		}
	}

	private static void setTurnCounters(final GameState gameState, String[] s) {
		gameState.setHalfMoveClock(Integer.valueOf(s[3]));
		gameState.setFullMoveNumber(Integer.valueOf(s[4]));
	}

	private String parseFromGameState(final GameState state) {
		StringBuilder sb = new StringBuilder();

		sb.append(getPositionString(state));
		sb.append(" ");
		sb.append(getCurrentColorString(state));
		sb.append(" ");
		sb.append(getCastlingString(state));
		sb.append(" ");
		sb.append(getEnPassantString(state));
		sb.append(" ");
		sb.append(getMoveCounterString(state));

		return sb.toString();
	}

	private String getMoveCounterString(final GameState state) {
		StringBuilder sb = new StringBuilder();
		sb.append(state.getHalfMoveClock() + " ");
		sb.append(state.getFullMoveNumber());
		return sb.toString();
	}

	private String getEnPassantString(final GameState state) {
		// we don't save enpassant field in GameState
		return "-";
	}

	private String getCastlingString(final GameState state) {
		StringBuilder sb = new StringBuilder();
		boolean castlingRights = false;
		if (state.canWhiteCastleKingSide()) {
			sb.append("K");
			castlingRights = true;
		}
		if (state.canWhiteCastleQueenSide()) {
			sb.append("Q");
			castlingRights = true;
		}
		if (state.canBlackCastleKingSide()) {
			sb.append("k");
			castlingRights = true;
		}
		if (state.canBlackCastleQueenSide()) {
			sb.append("q");
			castlingRights = true;
		}
		if (!castlingRights) {
			sb.append("-");
		}
		return sb.toString();
	}

	private String getCurrentColorString(final GameState state) {
		if (state.getCurrentColor() == Color.BLACK)
			return "b";
		else
			return "w";
	}

	private String getPositionString(final GameState state) {
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
					if (f.getColor().equals(Color.WHITE)) {
						sb.append(Character.toUpperCase(c));
					} else {
						sb.append(Character.toLowerCase(c));
					}
				}
			}
			if (counter > 0) {
				sb.append("" + counter);
			}
			if (y != 0) {
				sb.append("/");
			}
		}
		return sb.toString();
	}

	private char getCharFromFigure(final Figure f) {
		if (f instanceof King) {
			return 'k';
		} else if (f instanceof Queen) {
			return 'q';
		} else if (f instanceof Pawn) {
			return 'p';
		} else if (f instanceof Bishop) {
			return 'b';
		} else if (f instanceof Knight) {
			return 'n';
		} else if (f instanceof Rook) {
			return 'r';
		} else {
			throw new RuntimeException(
					"Error while parsing fen (Figure->Character)");
		}
	}

	private static void addToPlayer(final Player p, final char c,
			final Position pos) {
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

		p.add(f);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + fen.hashCode();
		return result;
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Fen other = (Fen) obj;
		return fen.equals(other.fen);
	}

	public GameState getGameState() {
		return gameState;
	}

	public String getFen() {
		return fen;
	}

	@Override
	public String toString() {
		return fen;
	}
}
