/*
 * This file is generated by jOOQ.
 */
package jooq.tables.records;


import jooq.tables._User;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record6;
import org.jooq.Row6;
import org.jooq.impl.UpdatableRecordImpl;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class _UserRecord extends UpdatableRecordImpl<_UserRecord> implements Record6<Long, String, String, String, String, String> {

    private static final long serialVersionUID = 1L;

    /**
     * Setter for <code>public._user.id</code>.
     */
    public void setId(Long value) {
        set(0, value);
    }

    /**
     * Getter for <code>public._user.id</code>.
     */
    public Long getId() {
        return (Long) get(0);
    }

    /**
     * Setter for <code>public._user.email</code>.
     */
    public void setEmail(String value) {
        set(1, value);
    }

    /**
     * Getter for <code>public._user.email</code>.
     */
    public String getEmail() {
        return (String) get(1);
    }

    /**
     * Setter for <code>public._user.first_name</code>.
     */
    public void setFirstName(String value) {
        set(2, value);
    }

    /**
     * Getter for <code>public._user.first_name</code>.
     */
    public String getFirstName() {
        return (String) get(2);
    }

    /**
     * Setter for <code>public._user.last_name</code>.
     */
    public void setLastName(String value) {
        set(3, value);
    }

    /**
     * Getter for <code>public._user.last_name</code>.
     */
    public String getLastName() {
        return (String) get(3);
    }

    /**
     * Setter for <code>public._user.password</code>.
     */
    public void setPassword(String value) {
        set(4, value);
    }

    /**
     * Getter for <code>public._user.password</code>.
     */
    public String getPassword() {
        return (String) get(4);
    }

    /**
     * Setter for <code>public._user.role</code>.
     */
    public void setRole(String value) {
        set(5, value);
    }

    /**
     * Getter for <code>public._user.role</code>.
     */
    public String getRole() {
        return (String) get(5);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    @Override
    public Record1<Long> key() {
        return (Record1) super.key();
    }

    // -------------------------------------------------------------------------
    // Record6 type implementation
    // -------------------------------------------------------------------------

    @Override
    public Row6<Long, String, String, String, String, String> fieldsRow() {
        return (Row6) super.fieldsRow();
    }

    @Override
    public Row6<Long, String, String, String, String, String> valuesRow() {
        return (Row6) super.valuesRow();
    }

    @Override
    public Field<Long> field1() {
        return _User._USER.ID;
    }

    @Override
    public Field<String> field2() {
        return _User._USER.EMAIL;
    }

    @Override
    public Field<String> field3() {
        return _User._USER.FIRST_NAME;
    }

    @Override
    public Field<String> field4() {
        return _User._USER.LAST_NAME;
    }

    @Override
    public Field<String> field5() {
        return _User._USER.PASSWORD;
    }

    @Override
    public Field<String> field6() {
        return _User._USER.ROLE;
    }

    @Override
    public Long component1() {
        return getId();
    }

    @Override
    public String component2() {
        return getEmail();
    }

    @Override
    public String component3() {
        return getFirstName();
    }

    @Override
    public String component4() {
        return getLastName();
    }

    @Override
    public String component5() {
        return getPassword();
    }

    @Override
    public String component6() {
        return getRole();
    }

    @Override
    public Long value1() {
        return getId();
    }

    @Override
    public String value2() {
        return getEmail();
    }

    @Override
    public String value3() {
        return getFirstName();
    }

    @Override
    public String value4() {
        return getLastName();
    }

    @Override
    public String value5() {
        return getPassword();
    }

    @Override
    public String value6() {
        return getRole();
    }

    @Override
    public _UserRecord value1(Long value) {
        setId(value);
        return this;
    }

    @Override
    public _UserRecord value2(String value) {
        setEmail(value);
        return this;
    }

    @Override
    public _UserRecord value3(String value) {
        setFirstName(value);
        return this;
    }

    @Override
    public _UserRecord value4(String value) {
        setLastName(value);
        return this;
    }

    @Override
    public _UserRecord value5(String value) {
        setPassword(value);
        return this;
    }

    @Override
    public _UserRecord value6(String value) {
        setRole(value);
        return this;
    }

    @Override
    public _UserRecord values(Long value1, String value2, String value3, String value4, String value5, String value6) {
        value1(value1);
        value2(value2);
        value3(value3);
        value4(value4);
        value5(value5);
        value6(value6);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached _UserRecord
     */
    public _UserRecord() {
        super(_User._USER);
    }

    /**
     * Create a detached, initialised _UserRecord
     */
    public _UserRecord(Long id, String email, String firstName, String lastName, String password, String role) {
        super(_User._USER);

        setId(id);
        setEmail(email);
        setFirstName(firstName);
        setLastName(lastName);
        setPassword(password);
        setRole(role);
        resetChangedOnNotNull();
    }
}
