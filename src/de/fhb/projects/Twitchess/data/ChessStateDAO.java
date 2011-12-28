package de.fhb.projects.Twitchess.data;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class ChessStateDAO implements ChessStateDAOInterface {
	protected Connection connection;
	protected static final String GET_CURRENT_GAME_STATEMENT = "SELECT * FROM chess_game WHERE player = ? AND result is null;";
	protected static final String GET_GAME_STATEMENT = "SELECT * FROM chess_game WHERE player = ?";
	protected static final String INSERT_INTO_TABLE_STATEMENT = "INSERT INTO chess_game("
			+ "player, fen, moves, date, result) values (?, ?, ?, ?, ?);";
	protected static final String UPDATE_STATEMENT = "UPDATE chess_game SET "
			+ "player = ?, fen = ?, moves = ?, date = ?, result = ? WHERE id = ?;";
	protected static final String COUNT_WINS_OF_PLAYER = "SELECT COUNT(*) FROM chess_game WHERE player = ? AND result = 1;";
	protected static final String CREATE_TABLE_STATEMENT = "CREATE TABLE IF NOT EXISTS chess_game ("
			+ "id INTEGER PRIMARY KEY, "
			+ "player TEXT NOT NULL, "
			+ "fen TEXT NOT NULL, "
			+ "moves TEXT NULL, "
			+ "date DATE NULL, "
			+ "result INTEGER NULL);";
	protected static final String TRUNCATE_TABLE_STATEMENT = "DELETE FROM chess_game;";

	public ChessStateDAO(final String filename) throws SQLException,
			ClassNotFoundException {
		super();
		Class.forName("org.sqlite.JDBC");
		connection = DriverManager.getConnection("jdbc:sqlite:" + filename);

		createTable();

		connection.setAutoCommit(true);
	}

	@Override
	public void close() throws SQLException {
		if (connection != null && !connection.isClosed())
			connection.close();
	}

	@Override
	public int countWinsOfPlayer(final String player) throws SQLException {
		ResultSet resultSet;
		int result;
		PreparedStatement countWinsOfPlayer = connection
				.prepareStatement(COUNT_WINS_OF_PLAYER);

		countWinsOfPlayer.clearParameters();
		countWinsOfPlayer.setString(1, player);
		resultSet = countWinsOfPlayer.executeQuery();
		result = resultSet.getInt(1);
		resultSet.close();

		return result;
	}

	public void createTable() throws SQLException {
		PreparedStatement createTable = connection
				.prepareStatement(CREATE_TABLE_STATEMENT);

		createTable.executeUpdate();
	}

	protected ChessStateVO fillChessStateVO(ResultSet resultSet)
			throws SQLException {
		ChessStateVO state;

		state = new ChessStateVO();
		state.setId(resultSet.getInt("id"));
		state.setPlayerName(resultSet.getString("player"));
		state.setFen(resultSet.getString("fen"));
		state.setMoves(resultSet.getString("moves"));
		state.setDate(resultSet.getDate("date"));
		state.setResult((Integer) resultSet.getObject("result"));

		return state;
	}

	@Override
	public List<ChessStateVO> findGameByPlayer(final String playerName)
			throws SQLException {
		ResultSet resultSet = null;
		List<ChessStateVO> result = new ArrayList<ChessStateVO>();
		PreparedStatement getGame = connection
				.prepareStatement(GET_GAME_STATEMENT);

		getGame.clearParameters();
		getGame.setString(1, playerName);
		resultSet = getGame.executeQuery();

		while (resultSet.next()) {
			result.add(fillChessStateVO(resultSet));
		}

		resultSet.close();

		return result;
	}

	@Override
	public List<ChessStateVO> findNotFinishedGameByPlayer(
			final String playerName) throws SQLException {
		ResultSet resultSet = null;
		List<ChessStateVO> result = new ArrayList<ChessStateVO>();

		PreparedStatement getCurrentGame = connection
				.prepareStatement(GET_CURRENT_GAME_STATEMENT);

		getCurrentGame.clearParameters();
		getCurrentGame.setString(1, playerName);
		resultSet = getCurrentGame.executeQuery();

		while (resultSet.next()) {
			result.add(fillChessStateVO(resultSet));
		}

		resultSet.close();

		return result;
	}

	@Override
	public int insertIntoTable(ChessStateVO vo) throws SQLException {
		if (vo == null)
			throw new SQLException("Cannot insert null into table.");

		ResultSet generatedKey;
		PreparedStatement insertIntoChessGame = connection
				.prepareStatement(INSERT_INTO_TABLE_STATEMENT);

		insertIntoChessGame.clearParameters();
		insertIntoChessGame.setString(1, vo.getPlayerName());
		insertIntoChessGame.setString(2, vo.getFen());
		insertIntoChessGame.setString(3, vo.getMoves());
		insertIntoChessGame.setDate(4, vo.getDate());
		insertIntoChessGame.setObject(5, vo.getResult());

		insertIntoChessGame.execute();

		generatedKey = insertIntoChessGame.getGeneratedKeys();
		vo.setId(generatedKey.getInt(1));

		//System.out.println("INSERTED " + vo + "\n    RETURNS " + vo.getId());

		return vo.getId();
	}

	public void showAll() throws SQLException {
		Statement stat = connection.createStatement();
		ResultSet rs = stat.executeQuery("SELECT * FROM chess_game;");
		while (rs.next()) {
			StringBuilder s = new StringBuilder();
			DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			Date date;

			s.append(rs.getInt("id"));
			s.append(" | ");
			s.append(rs.getString("player"));
			s.append(" | ");
			s.append(rs.getString("fen"));
			s.append(" | ");
			s.append(rs.getString("moves"));
			s.append(" | ");
			date = rs.getDate("date");
			s.append(date == null ? null : dateFormat.format(date.getTime()));
			s.append(" | ");
			s.append(rs.getString("result"));

			System.out.println(s.toString());
		}
		rs.close();
	}

	@Override
	public void truncateTable() throws SQLException {
		PreparedStatement truncateTable = connection
				.prepareStatement(TRUNCATE_TABLE_STATEMENT);

		truncateTable.executeUpdate();
	}

	@Override
	public void updateTable(final ChessStateVO vo) throws SQLException {
		if (vo == null)
			throw new SQLException("Cannot insert null into table.");

		PreparedStatement updateChessGame = connection
				.prepareStatement(UPDATE_STATEMENT);

		updateChessGame.clearParameters();
		updateChessGame.setString(1, vo.getPlayerName());
		updateChessGame.setString(2, vo.getFen());
		updateChessGame.setString(3, vo.getMoves());
		updateChessGame.setDate(4, vo.getDate());
		updateChessGame.setObject(5, vo.getResult());
		updateChessGame.setInt(6, vo.getId());

		updateChessGame.execute();
		//System.out.println("UPDATED " + vo);
	}

}
