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
import java.util.stream.Collectors;

@Repository
@Primary
public class JdbcContactDao implements ContactDao {

    private static final String GET_CONTACTS_SQL = "" +
            "SELECT * " +
            "FROM contacts";

    private static final String GET_CONTACT_BY_ID_SQL = "" +
            "SELECT * " +
            "FROM contacts " +
            "WHERE id = ?";

    private static final String CREATE_CONTACT_SQL = "" +
            "INSERT INTO contacts(name, surname, phoneNumber, email) " +
            "VALUES(?, ?, ?, ?)";

    private static final String CHANGE_CONTACT_SQL = "" +
            "UPDATE contacts " +
            "SET ? = ? " +
            "WHERE id = ?";

    private static final String DELETE_CONTACT_BY_ID_SQL = "" +
            "DELETE FROM contacts " +
            "WHERE id = ?";


    private static final RowMapper<Contact> CONTACT_ROW_MAPPER  =
            (rs, i) -> new Contact(rs.getLong("id"), rs.getString("name"),
                    rs.getString("surname"), rs.getLong("phoneNumber"),
                    rs.getString("email"));

    private final JdbcTemplate jdbcTemplate;

    public JdbcContactDao(JdbcTemplate jdbcTemplate) {
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
        String keyForSql = "";

        switch (key) {
            case "name":
                keyForSql = "name";
                break;
            case "surname":
                keyForSql = "surname";
                break;
            case "phoneNumber":
                keyForSql = "phoneNumber";
                break;
            case "email":
                keyForSql = "email";
                break;
            default:
                throw new Exception("Parameter not found");

        }
        int countChangesRow = jdbcTemplate.update(
                CHANGE_CONTACT_SQL,
                keyForSql,
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

    @Override
    public boolean createContactsBatch(List<Contact> contactList) {
        List<Object[]> args = contactList.stream()
                .map(contact -> new Object[] {
                        contact.getName(),
                        contact.getSurname(),
                        contact.getPhoneNumber(),
                        contact.getEmail()
                })
                .collect(Collectors.toList());

        int[] res = jdbcTemplate.batchUpdate(CREATE_CONTACT_SQL, args);
        boolean flag = true;
        for (int i = 0; i < res.length; i++) {
            if (res[i] <= 0 && res[i] != -2) {
                flag = false;
            }
        }

        return flag;
    }
}
