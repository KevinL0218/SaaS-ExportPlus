package cn.itcast.dao.system;

import cn.itcast.domain.system.Email;

import java.util.List;

public interface EmailDao {
    List<Email> findByUserId(String userId);

    void saveEmail(Email email);

    void deleteEmail(String emailId);
}
