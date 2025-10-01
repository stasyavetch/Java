package org.example.entity;

import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.util.List;

@Repository
@Primary
public class JdbcAccountDao implements ContactDao {

    private static final String GET_CONTACTS_SQL = "" +
            "SELECT *" +
            "FROM contacts";

    private static final String GET_CONTACT_BY_ID_SQL = "" +
            "SELECT *" +
            "FROM contacts" +
            "WHERE id = ?";

    private static final String CREATE_CONTACT_SQL = "" +
            "INSERT INTO contacts" +
            "VALUES(?, ?, ?, ?)";

    private static final String CHANGE_CONTACT_SQL = "" +
            "UBDATE contacts" +
            "SET ? = ?" +
            "WHERE id = ?";

    private static final String DELETE_CONTACT_BY_ID_SQL = "" +
            "DELETE FROM contacts" +
            "WHERE id = ?";

    private static final RowMapper<Contact> CONTACT_ROW_MAPPER  =
            (rs, i) -> new Contact(rs.getLong("id"), rs.getString("name"),
                    rs.getString("surname"), rs.getLong("phoneNumber"),
                    rs.getString("email"));

    private final JdbcTemplate jdbcTemplate;

    public JdbcAccountDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public List<Contact> getContacts() {
        return jdbcTemplate.query(
                GET_CONTACTS_SQL,
                CONTACT_ROW_MAPPER
        );
    }

    @Override
    public Contact getContactById(Long idContact) {
        return jdbcTemplate.queryForObject(
                GET_CONTACT_BY_ID_SQL,
                CONTACT_ROW_MAPPER,
                idContact
        );
    }

    @Override
    public void createContact(String name, String surname, long phoneNumber, String email) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(
                connection -> {
                    PreparedStatement ps = connection.prepareStatement(
                            CREATE_CONTACT_SQL, new String[] {"id"});
                    ps.setString(1, name);
                    ps.setString(2, surname);
                    ps.setLong(3, phoneNumber);
                    ps.setString(4, email);
                    return ps;
                },
                keyHolder
        );
    }

    @Override
    public boolean changeContact(Long idContact, String key, String newValue) throws Exception {
        int countChangesRow = jdbcTemplate.update(
                CHANGE_CONTACT_SQL,
                key,
                newValue,
                idContact
        );

        if(countChangesRow != 1)
            return false;
        else
            return true;
    }

    @Override
    public boolean deleteContactById(Long idContact) {
        int countChangesRow = jdbcTemplate.update(
                DELETE_CONTACT_BY_ID_SQL,
                idContact
        );

        if(countChangesRow != 1)
            return false;
        else
            return true;
    }
}
