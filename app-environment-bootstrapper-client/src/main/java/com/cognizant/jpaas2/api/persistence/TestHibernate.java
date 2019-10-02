/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cognizant.jpaas2.api.persistence;

import com.cognizant.jpaas2.api.domain.AppEnvInstance;
import com.cognizant.jpaas2.api.domain.AppEnvTaskOverview;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author 239913
 */
public class TestHibernate {
	
	public static void main(String sr[]) {
		try{
			Session session = HibernateUtil.getCurrentSession();
			Transaction transaction = session.beginTransaction();
			
			AppEnvInstance appEnvInstance = (AppEnvInstance)session.get(AppEnvInstance.class, new Long(20));
			appEnvInstance.setStatus("XXX-X");
			
			AppEnvTaskOverview appEnvTaskOverview = new AppEnvTaskOverview();
			
			appEnvTaskOverview.setLongDesc("XXX-XX");
			appEnvTaskOverview.setShortDesc("XXX-XX");			
			appEnvTaskOverview.setAppEnvInstance(appEnvInstance);
			
			appEnvInstance.getAppEnvTaskOverviews().add(appEnvTaskOverview);
			
			session.saveOrUpdate(appEnvTaskOverview);
		
			
			transaction.commit();
		}catch(Throwable t){
			t.printStackTrace();
		}
	}
	
}
