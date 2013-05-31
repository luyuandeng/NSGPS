package com.gzeport.app.gps.dao;


import java.sql.SQLException;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.gzeport.app.gps.pojo.BaseFuncmodel;
import com.gzeport.app.gps.pojo.BaseUser;
import com.gzeport.logistics.common.util.Encrypt;

public abstract class AbstractDAO extends HibernateDaoSupport{
	/**
	 * @功能: 验证用户是否有效和是否拥有访问权限 
	 * @编码: luyd luyuandeng@gzeport.com  Dec 17, 2012  2:21:35 PM
	 */
	@SuppressWarnings("unchecked")
	public boolean ValidateUser(final String userCode,final String password,final String funcId){
		 boolean bool=false;
		 final BaseUser buser ;
		List ulist = this.getHibernateTemplate().find("from BaseUser  user where user.userCode = ?", userCode);
		if(ulist!=null&&ulist.size()>0){
			buser =(BaseUser) ulist.get(0);	
		}else{
			buser =null;
		}
		 if(buser!=null){
			 Encrypt encrypt = new Encrypt();
			 String entryPwd=encrypt.encryptPWD(password);
			 if(entryPwd.equals( buser.getUserPwd())&&buser.getIsUse().equals("1")){
				 List <BaseFuncmodel>l = (List) getHibernateTemplate().execute(new HibernateCallback(){
						public Object doInHibernate(Session session) throws HibernateException, SQLException {
							String sql = "select *  from (select distinct tb.FUNC_ID"
								+" from portal.T_BASE_FUNCMODEL tb,"
							+" (select r.FUNC_ID FUNC_ID, f.FUNC_CODE FUNC_CODE"
							+" from portal.T_ROLE_FUNCMODEL r, portal.T_BASE_FUNCMODEL f"
									+"	 where (r.ROLE_ID in"
									+" 	 (select userrole.ROLE_ID    as ROLE_ID   from portal.T_USER_ROLE userrole, portal.T_BASE_ROLE role"
									+"   where (role .ROLE_ENABLE = 1 and  userrole.ROLE_ID = role.ROLE_ID)"  
								    +"   and (userrole.USER_ID = ?)))"
		                  +"  and f.FUNC_ID = r.FUNC_ID) ttb"
		                  +"  where tb.FUNC_ID = ttb.FUNC_ID) dd"
		                  +"  where dd.func_id = ?";
							Query query1 = session.createSQLQuery(sql);
							query1.setLong(0, buser.getUserId());
							query1.setString(1, funcId);
							List list1 =query1.list();
							return list1;
						}});
				 if(l!=null&&l.size()>0){
					 bool=true;
				 }else{
					 bool=false;
				 }
			 }else{
				bool=false;
			 }
		 }
		return bool;
	}

}
