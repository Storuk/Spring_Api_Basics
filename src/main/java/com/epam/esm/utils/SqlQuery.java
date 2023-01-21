package com.epam.esm.utils;

public class SqlQuery {
    public static class Tag {
        public static final String CREATE_TAG = "INSERT INTO tag (name) VALUES(?)";
        public static final String DELETE_TAG = "DELETE  FROM tag WHERE id=?";
        public static final String GET_ALL_TAGS = "SELECT * FROM tag";
        public static final String GET_TAG_BY_ID = "SELECT * FROM tag WHERE id = ?";
        public static final String IS_TAG_EXISTS = "SELECT EXISTS(SELECT * FROM tag WHERE name=?)";
        public static final String GET_TAG_ID_BY_NAME = "SELECT id FROM tag WHERE name = ?";
    }

    public static class GiftCertificate {
        public static final String GET_ALL_CERTIFICATES = "SELECT * FROM gift_certificate";
        public static final String GET_CERTIFICATE_BY_ID = "SELECT * FROM gift_certificate WHERE id=?";
        public static final String CREATE_CERTIFICATE = "INSERT INTO gift_certificate(name,description,price,duration,create_date,last_update_date) VALUES(?,?,?,?,?,?)";
        public static final String DELETE_CERTIFICATE = "DELETE  FROM gift_certificate WHERE id=?";
        public static final String GET_CERTIFICATE_ID = "SELECT id FROM gift_certificate WHERE name = ? AND description = ? AND price = ? AND duration = ?";
        public static final String IS_GIFT_CERTIFICATE_EXIST = "SELECT EXISTS(SELECT * FROM gift_certificate WHERE name=? AND description = ? AND price = ? AND duration = ?)";
    }

    public static class TagGiftCertificate {
        public static final String GET_GIFT_CERTIFICATE_TAGS_BY_TAG_NAME = "SELECT  gc.id , gc.name , gc.description, gc.price, gc.duration ,gc.create_date ,gc.last_update_date FROM gift_certificate_tag gct  JOIN tag t JOIN gift_certificate gc  WHERE gc.id = gct.gift_certificate_id AND t.id = gct.tag_id AND t.name = ?";
        public static final String GET_GIFT_CERTIFICATE_TAGS_BY_PART_OF_DESCRIPTION = "SELECT id, name, description, price, duration, create_date, last_update_date FROM gift_certificate WHERE description LIKE ?";
        public static final String GET_ALL_TAGS_BY_GIFT_CERTIFICATE_ID = "SELECT  t.id,t.name FROM gift_certificate_tag gct  JOIN tag t  WHERE gct.gift_certificate_id = ? AND t.id = gct.tag_id";
        public static final String CREATE_CERTIFICATE_TAG_RELATION = "INSERT INTO gift_certificate_tag (gift_certificate_id,tag_id) VALUES (?,?)";
        public static final String DELETE_CERTIFICATE_TAG_RELATION = "DELETE FROM gift_certificate_tag WHERE gift_certificate_id = ?";
        public static String sortByName(String method) {
            return String.format("SELECT  id, name, description, price, duration, create_date, last_update_date FROM  gift_certificate  ORDER BY name %s", method);
        }
        public static String sortByNameAndDate(String method1, String method2) {
            return String.format("SELECT  id, name, description, price, duration, create_date, last_update_date FROM  gift_certificate ORDER BY name %s, create_date %s", method1, method2);
        }
    }
}
