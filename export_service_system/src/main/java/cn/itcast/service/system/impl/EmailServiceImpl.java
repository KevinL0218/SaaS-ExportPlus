package cn.itcast.service.system.impl;

import cn.itcast.dao.system.EmailDao;
import cn.itcast.domain.system.Email;
import cn.itcast.service.system.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmailServiceImpl implements EmailService {
    @Autowired
    private EmailDao emailDao;

    @Override
    public List<Email> findByUserId(String userId) {
        return emailDao.findByUserId(userId);
    }

    @Override
    public void saveEmail(Email email) {
        emailDao.saveEmail(email);
    }

    @Override
    public void deleteEmail(String emailId) {
        emailDao.deleteEmail(emailId);
    }
}
