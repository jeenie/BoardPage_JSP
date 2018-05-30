package p201632003;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ArticleDAO {
	public static List<Article> findAll(int currentPage, int pageSize, String ss, String st, String od) throws Exception {
		String sql = "call article_findAll(?, ?, ?, ?, ?)";
		try (Connection connection = DB.getConnection("bbs2");
				PreparedStatement statement = connection.prepareStatement(sql)) {
			statement.setInt(1, (currentPage - 1) * pageSize); // firstRecordIndex 
			statement.setInt(2, pageSize); // pageSize 
			statement.setString(3, ss); // 조회 방법 
			statement.setString(4, st + "%"); // 검색 문자열 
			statement.setString(5, od); // 정렬 순서
			try (ResultSet resultSet = statement.executeQuery()) {
				ArrayList<Article> list = new ArrayList<Article>();
				while (resultSet.next()) {
					Article article = new Article();
					article.setId(resultSet.getInt("id"));
					article.setNo(resultSet.getInt("no"));
					article.setBoardName(resultSet.getString("boardName"));
					article.setUserName(resultSet.getString("name"));
					article.setWriteTime(resultSet.getTimestamp("writeTime"));
					article.setTitle(resultSet.getString("title"));
					article.setBody(resultSet.getString("body").replaceAll("<(/)?([a-zA-Z]*)(\\s[a-zA-Z]*=[^>]*)?(\\s)*(/)?>", ""));
					list.add(article);
				}
				return list;
			}
		}
	}

	public static int count(String ss, String st) throws Exception {
		String sql = "call article_count(?, ?)";
		try (Connection connection = DB.getConnection("bbs2");
				PreparedStatement statement = connection.prepareStatement(sql)) {
			statement.setString(1, ss); // 조회 방법 
			statement.setString(2, st + "%"); // 검색 문자열
			try (ResultSet resultSet = statement.executeQuery()) {
				if (resultSet.next())
					return resultSet.getInt(1);
			}
		}
		return 0;
	}

	public static Article findOne(int id) throws Exception {
		String sql = "SELECT a.*, b.boardName, u.name "
				+ "FROM article a LEFT JOIN Board b On a.boardId = b.id " + "LEFT JOIN User u On a.userID = u.id "
				+ "WHERE a.id =?";
		try (Connection connection = DB.getConnection("bbs2");
				PreparedStatement statement = connection.prepareStatement(sql)) {
			statement.setInt(1, id);
			try (ResultSet resultSet = statement.executeQuery()) {
				if (resultSet.next()) {
					Article article = new Article();
					article.setId(resultSet.getInt("id"));
					article.setTitle(resultSet.getString("title"));
					article.setBody(resultSet.getString("body").replaceAll("<(/)?([a-zA-Z]*)(\\s[a-zA-Z]*=[^>]*)?(\\s)*(/)?>", ""));
					article.setBoardName(resultSet.getString("boardName"));
					article.setUserId(resultSet.getInt("userId"));
					article.setUserName(resultSet.getString("name"));
					article.setWriteTime(resultSet.getTimestamp("writeTime"));
					article.setNotice(resultSet.getBoolean("notice"));

					return article;
				}
			}
			return null;
		}
	}

	public static void update(Article article) throws Exception {
		String sql = "UPDATE article SET title=?, body=?, userID=?, notice=? " + " WHERE id = ?";
		try (Connection connection = DB.getConnection("bbs2");
				PreparedStatement statement = connection.prepareStatement(sql)) {
			statement.setString(1, article.getTitle());
			statement.setString(2, article.getBody());
			statement.setInt(3, article.getUserId());
			statement.setBoolean(4, article.isNotice());
			statement.setInt(5, article.getId());
			statement.executeUpdate();
		}
	}

	public static void insert(Article article) throws Exception {
		String sql = "INSERT article (no, title, body, userId, boardId, notice, writeTime)" + " VALUES (?, ?, ?, ?, ?, ?, ?)";
		try (Connection connection = DB.getConnection("student1");
				PreparedStatement statement = connection.prepareStatement(sql)) {
			statement.setInt(1, article.getNo());
			statement.setString(2, article.getTitle());
			statement.setString(3, article.getBody());
			statement.setInt(4, article.getUserId());
			statement.setInt(5, article.getBoardId());
			statement.setBoolean(6, article.isNotice());
			statement.setTimestamp(7, article.getWriteTime());
			statement.executeUpdate();
		}
	}

	public static void delete(int id) throws Exception {
		String sql = "DELETE FROM article WHERE id = ?";
		try (Connection connection = DB.getConnection("bbs2");
				PreparedStatement statement = connection.prepareStatement(sql)) {
			statement.setInt(1, id);
			statement.executeUpdate();
		}
	}
}
