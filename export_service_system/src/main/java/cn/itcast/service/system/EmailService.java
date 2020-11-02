package cn.itcast.service.system;

import cn.itcast.domain.system.Email;

import java.util.List;

public interface EmailService {
    List<Email> findByUserId(String userId);

    void saveEmail(Email email);

    void deleteEmail(String emailId);
}
