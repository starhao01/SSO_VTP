package com.example.SSO_APP_VTP.serrvice.daos;

import com.example.SSO_APP_VTP.base.BaseDAO;
import com.example.SSO_APP_VTP.entity.UserData;
import com.example.SSO_APP_VTP.util.Utils;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.hibernate.type.LongType;
import org.hibernate.type.StringType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class UserDAO extends BaseDAO {

    @Autowired
    @Qualifier("coreFactory")
    SessionFactory sessionFactory;

    public Map<String, Object> getUserInfo(String username, String maBuuCuc) throws Exception {
        Query query = getSession(sessionFactory)
                .createSQLQuery("SELECT B.USERID, B.DN_USERID, EMPLOYEE_GROUP_ID, B.FIRSTNAME, B.LASTNAME, B.USERNAME, B.TELEPHONE, B.MA_CHUCDANH, B.MA_BUUCUC, EMAIL, DISPLAYNAME" +
                        ", PASSWORDFORMAT, PASSWORDSALT, VTP.GET_DEPT_CODE(MA_BUUCUC) CHI_NHANH, EMPLOYEECODE" +
                        " FROM ERP_HR.HR_EMPLOYEE A, VTP.SUSERS B WHERE A.USERNAME =  ? AND A.USERNAME = B.USERNAME AND MA_BUUCUC = ?")
                .addScalar("USERID", new LongType())
                .addScalar("DN_USERID", new LongType())
                .addScalar("EMPLOYEE_GROUP_ID", new LongType())
                .addScalar("FIRSTNAME", new StringType())
                .addScalar("LASTNAME", new StringType())
                .addScalar("USERNAME", new StringType())
                .addScalar("TELEPHONE", new StringType())
                .addScalar("EMAIL", new StringType())
                .addScalar("MA_CHUCDANH", new StringType())
                .addScalar("MA_BUUCUC", new StringType())
                .addScalar("DISPLAYNAME", new StringType())
                .addScalar("PASSWORDFORMAT", new StringType())
                .addScalar("PASSWORDSALT", new StringType())
                .addScalar("CHI_NHANH", new StringType())
                .addScalar("EMPLOYEECODE", new StringType())
                .setParameter(0, username)
                .setParameter(1, maBuuCuc);
        List<Object[]> ls = query.list();
        Map<String, Object> result = new HashMap<>();
        if (!Utils.isNullOrEmpty(ls)) {
            Object[] args = ls.get(0);
            UserData userData = new UserData();
            userData.setId((Long) args[0]);
            result.put("userid", args[0]);
            result.put("dn_userid", args[1]);
            result.put("employee_group_id", args[2]);
            result.put("firstname", args[3]);
            result.put("lastname", args[4]);
            result.put("username", args[5]);
            result.put("phone", args[6]);
            result.put("ma_chucdanh", args[7]);
            result.put("ma_buucuc", args[8]);
            result.put("email", args[9]);
            result.put("name", args[10]);
            result.put("PASSWORDFORMAT", args[11]);
            result.put("PASSWORDSALT", args[12]);
            result.put("don_vi", args[13]);
            result.put("manhanvien", args[14]);
        }
        return result;
    }
}
