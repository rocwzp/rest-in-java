package com.sivalabs.springmvcrest.repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.sivalabs.springmvcrest.entities.User;

/**
 * @author Siva
 *
 */
@Repository
public class UserRepositoryImpl implements UserRepository
{
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Override
	public User findByEmail(String email)
	{
		String sql = "select user_id, username, password, firstname, lastname, email, phone, dob from users where email=?";
		try {
			return jdbcTemplate.queryForObject(sql, new Object[]{email}, new UserMapper());
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
		
	}

	@Override
	public User findByUserName(String userName)
	{
		String sql = "select user_id, username, password, firstname, lastname, email, phone, dob from users where username=?";
		try {
			return jdbcTemplate.queryForObject(sql, new Object[]{userName}, new UserMapper());
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	@Override
	public void save(final User user)
	{
		final String sql = "insert into users(username, password, firstname, lastname, email, phone, dob) values(?,?,?,?,?,?,?)";
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(new PreparedStatementCreator()
		{
			
			@Override
			public PreparedStatement createPreparedStatement(Connection conn)
					throws SQLException
			{
				PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				pstmt.setString(1, user.getUserName());
				pstmt.setString(2, user.getPassword());
				pstmt.setString(3, user.getFirstName());
				pstmt.setString(4, user.getLastName());
				pstmt.setString(5, user.getEmail());
				pstmt.setString(6, user.getPhone());
				if(user.getDob() == null){
					pstmt.setString(7, null);
				} else {
					pstmt.setDate(7, new java.sql.Date(user.getDob().getTime()));
				}
				return pstmt;
			}
		}, keyHolder);
		user.setId(keyHolder.getKey().intValue());
		
	}

	@Override
	public User findOne(Integer userId)
	{
		String sql = "select user_id, username, password, firstname, lastname, email, phone, dob from users where user_id=?";
		try {
			return jdbcTemplate.queryForObject(sql, new Object[]{userId}, new UserMapper());
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<User> findAll()
	{
		String sql = "select user_id, username, password, firstname, lastname, email, phone, dob from users ";
		return jdbcTemplate.query(sql, new UserMapper());
	}

}

class UserMapper implements RowMapper<User>
{

	@Override
	public User mapRow(ResultSet rs, int arg1) throws SQLException
	{
		User user = new User();
		user.setId(rs.getInt("user_id"));
		user.setUserName(rs.getString("username"));
		user.setPassword(rs.getString("password"));
		user.setFirstName(rs.getString("firstname"));
		user.setLastName(rs.getString("lastname"));
		user.setEmail(rs.getString("email"));
		user.setPhone(rs.getString("phone"));
		user.setDob(rs.getDate("dob"));
		
		return user;
	}
	
}
